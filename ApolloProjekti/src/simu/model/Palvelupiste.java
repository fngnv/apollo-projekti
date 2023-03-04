package simu.model;

import java.util.LinkedList;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {

	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus
	
	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi; 
	
	//Tuloksien laskemiseen tarvittavat muuttujat
	private double serviceTime;
	private double busyTime = 0;
	private static int palveltujenMaara = 0; //palveltujen määrä on sama kuin completed count
	private double responseTime;
	private double waitingTime = 0;
	
	
	//JonoStartegia strategia; //optio: asiakkaiden järjestys
	
	private boolean varattu = false; 


	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
				
	}


	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa
		a.setJonottamisenAloitus(Kello.getInstance().getAika());
		jono.add(a);
		
	}

	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		jono.peek().setPalvelunLopetus(Kello.getInstance().getAika());
		busyTime += jono.peek().palvelupisteessaVietettyAika();
		waitingTime += jono.peek().jonossaVietettyAika();
		palveltujenMaara++;
		return jono.poll();
	}

	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
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
		// TODO Auto-generated method stub
		return jono.size();
	}
	
	//Laskee palvelupisteen keskimääräisen palveluajan ja palauttaa sen. Jonottamiset eivät ole laskettu mukaan
	public double getServiceTime() {
		serviceTime = busyTime / palveltujenMaara;
		return serviceTime;
	}
	
	//Laskee palvelupisteen keskimääräisen läpimenoajan ja palauttaa sen. Jonottamiset ovat laskettu mukaan
	public double getResponseTime() {
		responseTime = waitingTime / palveltujenMaara;
		if (responseTime >= 0) {
	        return responseTime;
	    } else {
	        return 0.0;
	    }
	}
	
	//Palauttaa kaikkien palvelupisteessä oleskeluaikojen summan (mm. jonottaminen)
	public double getWaitingTime() {
		return waitingTime;
	}


	public int getPalveltujenMaara() {
		return palveltujenMaara;
	}

}
