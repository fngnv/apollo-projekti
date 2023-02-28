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


public class OmaMoottori extends Moottori{
	
	private Saapumisprosessi saapumisprosessi;
	private Random random = new Random();
	
	//Tulosten laskemiseen tarvittavat muuttujat
	
	
	public OmaMoottori(IKontrolleri kontrolleri){ // UUSI

		super(kontrolleri); //UUSI
		
		palvelupisteet = new Palvelupiste[7];

		palvelupisteet[0] = new Palvelupiste(new Normal(10, 60), tapahtumalista, TapahtumanTyyppi.DEP1); //portsari
		palvelupisteet[1] = new Palvelupiste(new Normal(60, 180), tapahtumalista, TapahtumanTyyppi.DEP2); //saapumisnarikka
		palvelupisteet[2] = new Palvelupiste(new Normal(60, 600), tapahtumalista, TapahtumanTyyppi.DEP3); //baaritiski
		palvelupisteet[3] = new Palvelupiste(new Normal(10, 1800), tapahtumalista, TapahtumanTyyppi.DEP4); //tanssilattia		
		palvelupisteet[4] = new Palvelupiste(new Normal(180, 300), tapahtumalista, TapahtumanTyyppi.DEP5); //karaoke
		palvelupisteet[5] = new Palvelupiste(new Normal(10, 3600), tapahtumalista, TapahtumanTyyppi.DEP6); //istuminen tai vessa
		palvelupisteet[6] = new Palvelupiste(new Normal(60, 300), tapahtumalista, TapahtumanTyyppi.DEP7); //poistumisnarikka

		saapumisprosessi = new Saapumisprosessi(new Negexp(15, 5), tapahtumalista, TapahtumanTyyppi.ARR1, kontrolleri);
	}

	
	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}
	
	
	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat

		Asiakas a;
		switch (t.getTyyppi()){
			
		case ARR1:
			palvelupisteet[0].lisaaJonoon(new Asiakas());
			kontrolleri.visualisoiAsiakas(0);
			//jos halutaan rajoittaa asiakkaiden määrää, tänne vois laittaa if(Asiakas.getMaara() < maxMaara)
			//tai jotain semmosta
			if(Asiakas.getKokonaismaara() < 1000) {
				saapumisprosessi.generoiSeuraava();
			}
			//kontrolleri.visualisoiAsiakas();
			break;
		//portsari
		case DEP1:
			a = palvelupisteet[0].otaJonosta();
			kontrolleri.poistaAsiakkaanVisualisointi(0);
			palvelupisteet[1].lisaaJonoon(a);
			kontrolleri.visualisoiAsiakas(1);
			break;
		//narikka 1
		case DEP2:
			a = palvelupisteet[1].otaJonosta();
			kontrolleri.poistaAsiakkaanVisualisointi(1);
			//a.setPalvelupisteenSaapumisaika(Kello.getInstance().getAika());
			int seuraava = random.nextInt(5 - 2) + 2;	
			palvelupisteet[seuraava].lisaaJonoon(a);
			kontrolleri.visualisoiAsiakas(seuraava);
			//kontrolleri.poistaAsiakkaanVisualisointi();
			//a.setPalvelupisteenPoistumisaika(Kello.getInstance().getAika());
			//double palvelunKesto = a.getPalvelupisteenPoistumisaika() - a.getPalvelupisteenSaapumisaika();
			//suureet.setpalveluaika(palvelunKesto);
			break;
		//baaritiski
		case DEP3:
			a = palvelupisteet[2].otaJonosta();
			kontrolleri.poistaAsiakkaanVisualisointi(2);
			int valinta = random.nextInt(10 - 1) + 1;
			
			if(valinta == 3) {
				palvelupisteet[3].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(3);
			} else if(valinta == 4) {
				palvelupisteet[4].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(4);
			} else if(valinta == 5) {
				palvelupisteet[5].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(5);
			} else {
				palvelupisteet[6].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(6);
			}
			break;
		//tanssilattia
		case DEP4:
			a = palvelupisteet[3].otaJonosta();
			kontrolleri.poistaAsiakkaanVisualisointi(3);
			valinta = random.nextInt(10 - 1) + 1;
			
			if(valinta == 2) {
				palvelupisteet[2].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(2);
			} else if(valinta == 4) {
				palvelupisteet[4].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(4);
			} else if(valinta == 5) {
				palvelupisteet[5].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(5);
			} else {
				palvelupisteet[6].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(6);
			}
			break;
		//karaoke
		case DEP5:
			a = palvelupisteet[4].otaJonosta();
			kontrolleri.poistaAsiakkaanVisualisointi(4);
			valinta = random.nextInt(10 - 1) + 1;
			
			if(valinta == 2) {
				palvelupisteet[2].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(2);
			} else if(valinta == 3) {
				palvelupisteet[3].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(3);
			} else if(valinta == 5) {
				palvelupisteet[5].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(5);
			} else {
				palvelupisteet[6].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(6);
			}
			break;
		//istuminen
		case DEP6:
			a = palvelupisteet[5].otaJonosta();
			kontrolleri.poistaAsiakkaanVisualisointi(5);
			valinta = random.nextInt(10 - 1) + 1;
			
			if(valinta == 2) {
				palvelupisteet[2].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(2);
			} else if(valinta == 3) {
				palvelupisteet[3].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(3);
			} else if(valinta == 4) {
				palvelupisteet[4].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(4);
			} else {
				palvelupisteet[6].lisaaJonoon(a);
				kontrolleri.visualisoiAsiakas(6);
			}
			break;
		//poistuminen
		case DEP7:
			a = palvelupisteet[6].otaJonosta();
			kontrolleri.poistaAsiakkaanVisualisointi(6);
			a.setPoistumisaika(Kello.getInstance().getAika());
			a.raportti();
			break;
		}	
	}
	
	@Override
	protected void tulokset() {
		//Muuttujat joita tarvitaan suureiden laskemiseen
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
		
		// UUTTA graafista
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
		//Seuraava metodi lähettää simulaation tulokset kontrollerille
		kontrolleri.naytaTulokset(pAsiakkaat, lapimenoaikojenKA, throughput, serviceTimeTulokset, responseTimeTulokset, keskJononpituusTulokset);
	}

	
}
