package simu.model;

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
	//Suureet suureet = new Suureet();
	
	
	public OmaMoottori(IKontrolleri kontrolleri){ // UUSI

		super(kontrolleri); //UUSI
		
		/*
		 * palvelupisteet = new Palvelupiste[3];
		 * 
		 * palvelupisteet[0]=new Palvelupiste(new Normal(10,6), tapahtumalista,
		 * TapahtumanTyyppi.DEP1); palvelupisteet[1]=new Palvelupiste(new Normal(10,10),
		 * tapahtumalista, TapahtumanTyyppi.DEP2); palvelupisteet[2]=new
		 * Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP3);
		 * 
		 * saapumisprosessi = new Saapumisprosessi(new Negexp(15,5), tapahtumalista,
		 * TapahtumanTyyppi.ARR1);
		 */
		palvelupisteet = new Palvelupiste[7];

		palvelupisteet[0] = new Palvelupiste(new Normal(10, 60), tapahtumalista, TapahtumanTyyppi.DEP1);
		palvelupisteet[1] = new Palvelupiste(new Normal(30, 80), tapahtumalista, TapahtumanTyyppi.DEP2);
		palvelupisteet[2] = new Palvelupiste(new Normal(60, 300), tapahtumalista, TapahtumanTyyppi.DEP3);
		palvelupisteet[3] = new Palvelupiste(new Normal(180, 100), tapahtumalista, TapahtumanTyyppi.DEP4);
		palvelupisteet[4] = new Palvelupiste(new Normal(10, 400), tapahtumalista, TapahtumanTyyppi.DEP5);
		palvelupisteet[5] = new Palvelupiste(new Normal(10, 450), tapahtumalista, TapahtumanTyyppi.DEP6);
		palvelupisteet[6] = new Palvelupiste(new Normal(60, 80), tapahtumalista, TapahtumanTyyppi.DEP7);

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
				saapumisprosessi.generoiSeuraava();
				kontrolleri.visualisoiAsiakas();
			break;
		//portsari
		case DEP1:
			a = palvelupisteet[0].otaJonosta();
			palvelupisteet[1].lisaaJonoon(a);
			break;
		//narikka 1
		case DEP2:
			a = palvelupisteet[1].otaJonosta();
			//a.setPalvelupisteenSaapumisaika(Kello.getInstance().getAika());
			int seuraava = random.nextInt(5 - 2) + 2;	
			palvelupisteet[seuraava].lisaaJonoon(a);
			//kontrolleri.poistaAsiakkaanVisualisointi();
			//a.setPalvelupisteenPoistumisaika(Kello.getInstance().getAika());
			//double palvelunKesto = a.getPalvelupisteenPoistumisaika() - a.getPalvelupisteenSaapumisaika();
			//suureet.setpalveluaika(palvelunKesto);
			break;
		//baaritiski
		case DEP3:
			a = palvelupisteet[2].otaJonosta();
			int valinta = random.nextInt(10 - 1) + 1;
			
			if(valinta == 3) {
				palvelupisteet[3].lisaaJonoon(a);
			} else if(valinta == 4) {
				palvelupisteet[4].lisaaJonoon(a);
			} else if(valinta == 5) {
				palvelupisteet[5].lisaaJonoon(a);
			} else {
				palvelupisteet[6].lisaaJonoon(a);
			}
			break;
		//tanssilattia
		case DEP4:
			a = palvelupisteet[3].otaJonosta();
			valinta = random.nextInt(10 - 1) + 1;
			
			if(valinta == 2) {
				palvelupisteet[2].lisaaJonoon(a);
			} else if(valinta == 4) {
				palvelupisteet[4].lisaaJonoon(a);
			} else if(valinta == 5) {
				palvelupisteet[5].lisaaJonoon(a);
			} else {
				palvelupisteet[6].lisaaJonoon(a);
			}
			break;
		//karaoke
		case DEP5:
			a = palvelupisteet[4].otaJonosta();
			valinta = random.nextInt(10 - 1) + 1;
			
			if(valinta == 2) {
				palvelupisteet[2].lisaaJonoon(a);
			} else if(valinta == 3) {
				palvelupisteet[3].lisaaJonoon(a);
			} else if(valinta == 5) {
				palvelupisteet[5].lisaaJonoon(a);
			} else {
				palvelupisteet[6].lisaaJonoon(a);
			}
			break;
		//istuminen
		case DEP6:
			a = palvelupisteet[5].otaJonosta();
			valinta = random.nextInt(10 - 1) + 1;
			
			if(valinta == 2) {
				palvelupisteet[2].lisaaJonoon(a);
			} else if(valinta == 3) {
				palvelupisteet[3].lisaaJonoon(a);
			} else if(valinta == 4) {
				palvelupisteet[4].lisaaJonoon(a);
			} else {
				palvelupisteet[6].lisaaJonoon(a);
			}
			break;
		//poistuminen
		case DEP7:
			a = palvelupisteet[6].otaJonosta();
			a.setPoistumisaika(Kello.getInstance().getAika());
			a.raportti();
			break;
		}	
	}
	
	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Palvellut asiakkaat: " + Asiakas.getAsiakasLkm());
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo: " + Asiakas.getKeskiarvo());
		/**
		System.out.println("Palvelupisteen 1 keskimääräinen palveluaika: ");
		System.out.println("Palvelupisteen 2 keskimääräinen palveluaika: ");
		System.out.println("Palvelupisteen 3 keskimääräinen palveluaika: ");
		System.out.println("Palvelupisteen 4 keskimääräinen palveluaika: ");
		System.out.println("Palvelupisteen 5 keskimääräinen palveluaika: ");
		System.out.println("Palvelupisteen 6 keskimääräinen palveluaika: ");
		System.out.println("Palvelupisteen 7 keskimääräinen palveluaika: ");
		System.out.println("Palvelupisteen 1 keskimääräinen läpimenoaika: ");
		System.out.println("Palvelupisteen 2 keskimääräinen läpimenoaika: ");
		System.out.println("Palvelupisteen 3 keskimääräinen läpimenoaika: ");
		System.out.println("Palvelupisteen 4 keskimääräinen läpimenoaika: ");
		System.out.println("Palvelupisteen 5 keskimääräinen läpimenoaika: ");
		System.out.println("Palvelupisteen 6 keskimääräinen läpimenoaika: ");
		System.out.println("Palvelupisteen 7 keskimääräinen läpimenoaika: ");
		System.out.println("Palvelupisteen 1 keskimääräinen jononpituus: ");
		System.out.println("Palvelupisteen 2 keskimääräinen jononpituus: ");
		System.out.println("Palvelupisteen 3 keskimääräinen jononpituus: ");
		System.out.println("Palvelupisteen 4 keskimääräinen jononpituus: ");
		System.out.println("Palvelupisteen 5 keskimääräinen jononpituus: ");
		System.out.println("Palvelupisteen 6 keskimääräinen jononpituus: ");
		System.out.println("Palvelupisteen 7 keskimääräinen jononpituus: ");
		*/
		// VANHAA tekstipohjaista
		// System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		// System.out.println("Tulokset ... puuttuvat vielä");
		
		// UUTTA graafista
		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
	}

	
}
