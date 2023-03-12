package simu.model;

import java.text.DecimalFormat;
import java.util.Random;
import controller.IKontrolleri;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;

/**
 * Luokka pitää simulaatiota toiminnassa
 * 
 * @author 
 * Täydenntänyt Vera Finogenova
 */
public class OmaMoottori extends Moottori{
	
	private Saapumisprosessi saapumisprosessi;
	private Random random = new Random();
	
	public OmaMoottori(IKontrolleri kontrolleri){ // UUSI

		super(kontrolleri); //UUSI
		
		palvelupisteet = new Palvelupiste[7];

		palvelupisteet[0] = new Palvelupiste(new Normal(10, 60), tapahtumalista, TapahtumanTyyppi.DEP1); //portsari
		palvelupisteet[1] = new Palvelupiste(new Normal(30, 180), tapahtumalista, TapahtumanTyyppi.DEP2); //saapumisnarikka
		palvelupisteet[2] = new Palvelupiste(new Normal(60, 600), tapahtumalista, TapahtumanTyyppi.DEP3); //baaritiski
		palvelupisteet[3] = new Palvelupiste(new Normal(10, 1800), tapahtumalista, TapahtumanTyyppi.DEP4); //tanssilattia		
		palvelupisteet[4] = new Palvelupiste(new Normal(180, 300), tapahtumalista, TapahtumanTyyppi.DEP5); //karaoke
		palvelupisteet[5] = new Palvelupiste(new Normal(10, 3600), tapahtumalista, TapahtumanTyyppi.DEP6); //istuminen tai vessa
		palvelupisteet[6] = new Palvelupiste(new Normal(30, 300), tapahtumalista, TapahtumanTyyppi.DEP7); //poistumisnarikka

		saapumisprosessi = new Saapumisprosessi(new Negexp(kontrolleri.getMaxValiaika(), kontrolleri.getMinValiaika()), tapahtumalista, TapahtumanTyyppi.ARR1, kontrolleri);
	}

	
	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); 
	}
	
	
	@Override
	protected void suoritaTapahtuma(Tapahtuma t){ 

		Asiakas a;
		switch (t.getTyyppi()){
			
		case ARR1:
			palvelupisteet[0].lisaaJonoon(new Asiakas());
			kontrolleri.visualisoiAsiakas(0);
			kontrolleri.naytaJononpituus(0, palvelupisteet[0].getJononPituus());
				saapumisprosessi.generoiSeuraava();
			break;
		//portsari, indeksi 0
		case DEP1:
			a = palvelupisteet[0].otaJonosta();
			kontrolleri.poistaAsiakkaanVisualisointi(0);
			kontrolleri.naytaJononpituus(0, palvelupisteet[0].getJononPituus());
			palvelupisteet[1].lisaaJonoon(a);
			kontrolleri.visualisoiAsiakas(1);
			kontrolleri.naytaJononpituus(1, palvelupisteet[1].getJononPituus());
			break;
		//narikka 1, indeksi 1
		case DEP2:
			a = palvelupisteet[1].otaJonosta();
			kontrolleri.poistaAsiakkaanVisualisointi(1);
			kontrolleri.naytaJononpituus(1, palvelupisteet[1].getJononPituus());
			int seuraavaDep2 = random.nextInt(5 - 2) + 2;			
			palvelupisteet[seuraavaDep2].lisaaJonoon(a);
			kontrolleri.visualisoiAsiakas(seuraavaDep2);
			kontrolleri.naytaJononpituus(seuraavaDep2, palvelupisteet[seuraavaDep2].getJononPituus());
			break;
		//baaritiski, indeksi 2
		case DEP3:
			a = palvelupisteet[2].otaJonosta();
			kontrolleri.poistaAsiakkaanVisualisointi(2);
			kontrolleri.naytaJononpituus(2, palvelupisteet[2].getJononPituus());
			int seuraavaDep3 = generoiValinta(2);
			palvelupisteet[seuraavaDep3].lisaaJonoon(a);
			kontrolleri.visualisoiAsiakas(seuraavaDep3);
			kontrolleri.naytaJononpituus(seuraavaDep3, palvelupisteet[seuraavaDep3].getJononPituus());
			break;
		//tanssilattia, indeksi 3
		case DEP4:
			a = palvelupisteet[3].otaJonosta();
			kontrolleri.poistaAsiakkaanVisualisointi(3);
			kontrolleri.naytaJononpituus(3, palvelupisteet[3].getJononPituus());
			int seuraavaDep4 = generoiValinta(3);
			palvelupisteet[seuraavaDep4].lisaaJonoon(a);
			kontrolleri.visualisoiAsiakas(seuraavaDep4);
			kontrolleri.naytaJononpituus(seuraavaDep4, palvelupisteet[seuraavaDep4].getJononPituus());
			break;
		//karaoke, indeksi 4
		case DEP5:
			a = palvelupisteet[4].otaJonosta();
			kontrolleri.poistaAsiakkaanVisualisointi(4);
			kontrolleri.naytaJononpituus(4, palvelupisteet[4].getJononPituus());
			int seuraavaDep5 = generoiValinta(4);
			palvelupisteet[seuraavaDep5].lisaaJonoon(a);
			kontrolleri.visualisoiAsiakas(seuraavaDep5);
			kontrolleri.naytaJononpituus(seuraavaDep5, palvelupisteet[seuraavaDep5].getJononPituus());
			break;
		//istuminen, indeksi 5
		case DEP6:
			a = palvelupisteet[5].otaJonosta();
			kontrolleri.poistaAsiakkaanVisualisointi(5);
			kontrolleri.naytaJononpituus(5, palvelupisteet[5].getJononPituus());
			int seuraavaDep6 = generoiValinta(5);
			palvelupisteet[seuraavaDep6].lisaaJonoon(a);
			kontrolleri.visualisoiAsiakas(seuraavaDep6);
			kontrolleri.naytaJononpituus(seuraavaDep6, palvelupisteet[seuraavaDep6].getJononPituus());
			break;
		//poistuminen, indeksi 6
		case DEP7:
			a = palvelupisteet[6].otaJonosta();
			kontrolleri.poistaAsiakkaanVisualisointi(6);
			kontrolleri.naytaJononpituus(6, palvelupisteet[6].getJononPituus());
			a.setPoistumisaika(Kello.getInstance().getAika());
			a.raportti();
			break;
		}	
	}
	
	/**
	 * Metodi sattunaisesti valitsee mihin asiakas menee seuraavaksi, silloin kun on useita vaihtoehtoa ja palauttaa seuraavan palvelupisteen numeron.
	 * Asiakas ei voi valita sen palvelupisteen, jossa hän on asken käynyt, vaan hänen on pakko valita joku toinen
	 * 
	 * @param nykSijainti palvelupiste, jolla asikakas on silloin kun sen on tehtävä päätös
	 * @return seuraava palvelupiste
	 */
	public int generoiValinta(int nykSijainti) {
		int valinta;
		
		do {
			valinta = random.nextInt(10 - 1) + 1;
		} while(valinta == nykSijainti);
		
		
		if(valinta > 6 || valinta < 2) {
			valinta = 6;
		}
		
		return valinta;
	}
	
	@Override
	protected void tulokset() {
		//Muuttujat joita tarvitaan tuloksien laskemiseen
		double aukioloaika = Kello.getInstance().getAika();
		int pAsiakkaat = Asiakas.getAsiakasLkm();
		double lapimenoaikojenKA = Asiakas.getKeskiarvo();
		
		System.out.println("Simulointi päättyi kello " + aukioloaika);
		System.out.println("Palvellut asiakkaat: " + pAsiakkaat);
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo: " + lapimenoaikojenKA);
		
		//Suoritustehon laskeminen
		double throughput = pAsiakkaat / aukioloaika;
		
		//Taulukko, johon tallennetaan kaikkien palvelupisteiden service time -tulokset
		double[] serviceTimeTulokset = new double[palvelupisteet.length];
		
		//Taulukko, johon tallennetaan kaikkien palvelupisteiden response time -tulokset
		double[] responseTimeTulokset = new double[palvelupisteet.length];
		
		//Taulukko, johon tallennetaan palvelupisteiden keskimääräiset jononpituudet
		double[] keskJononpituusTulokset = new double[palvelupisteet.length];
		
		for(int i = 0; i < palvelupisteet.length; i++) {
			serviceTimeTulokset[i] = palvelupisteet[i].getServiceTime();
			responseTimeTulokset[i] = palvelupisteet[i].getResponseTime();
			keskJononpituusTulokset[i] = palvelupisteet[i].getWaitingTime() / aukioloaika;
		}
		
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
		
		//Seuraava rivi lähettää simulaation tulokset kontrollerille
		kontrolleri.naytaTulokset(pAsiakkaat, lapimenoaikojenKA, throughput, serviceTimeTulokset, responseTimeTulokset, keskJononpituusTulokset);
	}

	
}
