package simu.model;

import java.util.LinkedList;
import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;

/**
 * Luokka kuvaa palvelupiste-olioiden ominaisuudet ja toiminnot
 * 
 * @author
 * Täydentänyt Vera Finogenova
 *
 */
public class Palvelupiste {

	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus
	
	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi; 
	
	/**
	 * Palvelupisteen service time -arvo
	 */
	private double serviceTime;
	/**
	 * Palvelupisteen busy time arvo
	 */
	private double busyTime = 0;
	/**
	 * Palvelupistekohtainen palveltujen asiakkaiden määrä, completed count
	 */
	private static int palveltujenMaara = 0;
	/**
	 * Palvelupisteen response time -arvo
	 */
	private double responseTime;
	/**
	 * Palvelupisteen waiting time -arvo
	 */
	private double waitingTime = 0;
	
	private boolean varattu = false; 


	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
				
	}


	public void lisaaJonoon(Asiakas a){
		a.setJonottamisenAloitus(Kello.getInstance().getAika());
		jono.add(a);
		
	}

	public Asiakas otaJonosta(){
		varattu = false;
		jono.peek().setPalvelunLopetus(Kello.getInstance().getAika());
		busyTime += jono.peek().palvelupisteessaVietettyAika();
		waitingTime += jono.peek().jonossaVietettyAika();
		palveltujenMaara++;
		return jono.poll();
	}

	public void aloitaPalvelu(){
		varattu = true;
		jono.peek().setPalvelunAloitus(Kello.getInstance().getAika());
		double palveluaika = generator.sample();
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));
	}


	public boolean onVarattu(){
		return varattu;
	}


	public boolean onJonossa(){
		return jono.size() != 0;
	}


	public int getJononPituus() {
		return jono.size();
	}
	
	/**
	 * Laskee palvelupisteen keskimääräisen palveluajan ja palauttaa sen. Jonottamiset eivät ole laskettu mukaan
	 * 
	 * @return palvelupisteen keskimääräinen palveluaika
	 */
	public double getServiceTime() {
		serviceTime = busyTime / palveltujenMaara;
		return serviceTime;
	}
	
	/**
	 * Laskee palvelupisteen keskimääräisen läpimenoajan ja palauttaa sen. Jonottamiset ovat laskettu mukaan
	 * 
	 * @return palvelupisteen kieskimääräinen läpimenoaika
	 */
	public double getResponseTime() {
		responseTime = waitingTime / palveltujenMaara;
		if (responseTime >= 0) {
	        return responseTime;
	    } else {
	        return 0.0;
	    }
	}
	
	/**
	 * Palauttaa palvelupisteessä käyneiden asiakkaiden oleskeluaikojen summan
	 * 
	 * @return palvelupisteessä oleskeluaikojen summa
	 */
	public double getWaitingTime() {
		return waitingTime;
	}

	/**
	 * Palauttaa palvelupisteessä käyneiden palveltujen asiakkaiden määrän
	 * 
	 * @return palvelupistekohtainen palveltujen asiakkaiden määrä
	 */
	public int getPalveltujenMaara() {
		return palveltujenMaara;
	}

}
