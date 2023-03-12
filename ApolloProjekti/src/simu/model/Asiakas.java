package simu.model;

import simu.framework.Kello;
import simu.framework.Trace;

/**
 * Luokka kuvaa Asiakas-olioiden ominaisuudet ja toiminnot
 * 
 * @author 
 * Täydentänyt Vera Finogenova
 */
public class Asiakas {
	private double saapumisaika;
	private double poistumisaika;
	private int id;
	private static int i = 1;
	/**
	 * Asiakkaiden oleskeluaikojen summa
	 */
	private static long sum = 0;
	/**
	 * Oleskeluaikojen keskiarvo
	 */
	private static double keskiarvo = 0;
	/**
	 * Palveltujen asiakkaiden määrä
	 */
	private static int asiakasMaara;
	/**
	 * Saapuneiden asiakkaiden määrä
	 */
	private static int kokonaismaara = 0;
	
	//Tarvitaan suureiden laskemiseen
	//Jonottamisen lopetus -muuttujaa ei tarvita, koska se on sama kuin palvelun lopetus
	/**
	 * Aika jolloin asiakas on saapunut jonoon. Tarvitaan suureiden laskemiseen
	 */
	private double jonottamisenAloitus; 
	/**
	 * Aika jolloin asiakas on päässyt palvelutiskille. Tarvitaan suureiden laskemiseen
	 */
	private double palvelunAloitus;
	/**
	 * Aika, jolloin asiakas on lähtenyt palvelutiskiltä ja jonosta. Tarvitaan suureiden laskemiseen
	 */
	private double palvelunLopetus;
	
	
	public Asiakas(){
	    id = i++;
	    kokonaismaara ++;
	    
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas:" + id + ":"+saapumisaika);
	}
	
	public static int getKokonaismaara() {
		return kokonaismaara;
	}

	public double getPoistumisaika() {
		return poistumisaika;
	}

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	
	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}
	
	/**
	 * Asettaa parametrilla saadun arvon palvelun aloitusajaksi
	 * 
	 * @param aloitusaika palvelun aloitusaika
	 */
	public void setPalvelunAloitus(double aloitusaika) {
		palvelunAloitus = aloitusaika;
	}

	/**
	 * Palauttaa palvelunAloitus -muuttujan arvon
	 * 
	 * @return palvelun aloitusaika
	 */
	public double getPalvelunAloitus() {
		return palvelunAloitus;
	}
	
	/**
	 * Asettaa parametrilla saadun arvon jonottamisen aloitusajaksi
	 * 
	 * @param aloitusaika jonottamisen aloitusaika
	 */
	public void setJonottamisenAloitus(double aloitusaika) {
		jonottamisenAloitus = aloitusaika;
	}
	
	/**
	 * Palauttaa jonottamisenAloitus -muuttujan arvon
	 * 
	 * @return jonottamisen aloitusaika
	 */
	public double getJonottamisenAloitus() {
		return jonottamisenAloitus;
	}

	/**
	 * Palauttaa palvelunLopetus -muuttujan arvon
	 * 
	 * @return palvelun lopetusaika, sama kuin jonottamisen lopetusaika
	 */
	public double getPalvelunLopetus() {
		return palvelunLopetus;
	}

	/**
	 * Asettaa parametrilla saadun arvon palvelun lopetusajaksi, joka on sama kuin jonottamisen lopetusaika
	 * 
	 * @param palvelunLopetus palvelun lopetusaika
	 */
	public void setPalvelunLopetus(double palvelunLopetus) {
	    this.palvelunLopetus = palvelunLopetus;
	}
	
	/**
	 * Metodi laskee ja palauttaa palvelupisteen jonossa vietetyn ajan, johon kuuluu myös palvelun saaminen
	 * 
	 * @return jonossa vietetty aika
	 */
	public double jonossaVietettyAika() {
		return palvelunLopetus - jonottamisenAloitus;
	}
	
	//Palvelussa vietetyn ajan laskeminen
	/**
	 * Metodi laskee ja palauttaa palvelutiskillä vietetyn ajan
	 * 
	 * @return palvelutiskillä vietetty aika
	 */
	public double palvelupisteessaVietettyAika() {
		return palvelunLopetus - palvelunAloitus;
	}
	
	public int getId() {
		return id;
	}
	
	/**
	 * Palauttaa palveltujen asiakkaiden määrän
	 * 
	 * @return palveltujen asiakkaiden määrä
	 */
	public static int getAsiakasLkm() {
		return asiakasMaara;
	}
	
	/**
	 * Palauttaa palveltujen asiakkaiden palveluaikojen keskiarvon
	 * 
	 * @return palveluaikojen keskiarvo
	 */
	public static double getKeskiarvo() {
		return keskiarvo;
	}
	
	public void raportti(){
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui:" +saapumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui:" +poistumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi:" +(poistumisaika-saapumisaika));
		asiakasMaara++;
		sum += (poistumisaika - saapumisaika);
		keskiarvo = sum/asiakasMaara;
		System.out.println("Poistuneita asiakkaita "+ asiakasMaara);
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo "+ keskiarvo);
	}

}
