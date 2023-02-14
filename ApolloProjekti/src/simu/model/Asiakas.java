package simu.model;

import simu.framework.Kello;
import simu.framework.Trace;


// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
	private double saapumisaika;
	private double poistumisaika;
	//private double palvelupSaapumisaika;
	//private double palvelupPoistumisaika;
	private int id;
	private static int i = 1;
	private static long sum = 0;
	private static double keskiarvo = 0;
	private static int asiakasMaara;
	
	public Asiakas(){
	    id = i++;
	    
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas:" + id + ":"+saapumisaika);
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
	public double getPalvelupisteenPoistumisaika() {
		return palvelupPoistumisaika;
	}

	public void setPalvelupisteenPoistumisaika(double palvelupPoistumisaika) {
		this.palvelupPoistumisaika = palvelupPoistumisaika;
	}

	public double getPalvelupisteenSaapumisaika() {
		return palvelupSaapumisaika;
	}

	public void setPalvelupisteenSaapumisaika(double palvelupSaapumisaika) {
		this.palvelupSaapumisaika = palvelupSaapumisaika;
	}
	*/
	public int getId() {
		return id;
	}
	
	public static int getAsiakasLkm() {
		return asiakasMaara;
	}
	
	public static double getKeskiarvo() {
		return keskiarvo;
	}
	
	public void raportti(){
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui:" +saapumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui:" +poistumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi:" +(poistumisaika-saapumisaika));
		sum += (poistumisaika-saapumisaika);
		asiakasMaara++;
		keskiarvo = sum/asiakasMaara;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti "+ keskiarvo);
		System.out.println("Poistuneita asiakkaita "+ asiakasMaara);
		double keskiarvo = sum/id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo "+ keskiarvo);
	}

}
