package simu.model;

import java.util.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;
	private Random random = new Random();

	public OmaMoottori() {

		palvelupisteet = new Palvelupiste[7];

		palvelupisteet[0] = new Palvelupiste(new Normal(10, 60), tapahtumalista, TapahtumanTyyppi.DEP1);
		palvelupisteet[1] = new Palvelupiste(new Normal(60, 180), tapahtumalista, TapahtumanTyyppi.DEP2);
		palvelupisteet[2] = new Palvelupiste(new Normal(1, 600), tapahtumalista, TapahtumanTyyppi.DEP3);
		palvelupisteet[3] = new Palvelupiste(new Normal(60, 600), tapahtumalista, TapahtumanTyyppi.DEP4);
		palvelupisteet[4] = new Palvelupiste(new Normal(180, 300), tapahtumalista, TapahtumanTyyppi.DEP5);
		palvelupisteet[5] = new Palvelupiste(new Normal(10, 1800), tapahtumalista, TapahtumanTyyppi.DEP6);
		palvelupisteet[6] = new Palvelupiste(new Normal(10, 3600), tapahtumalista, TapahtumanTyyppi.DEP7);

		saapumisprosessi = new Saapumisprosessi(new Negexp(15, 5), tapahtumalista, TapahtumanTyyppi.ARR1);

	}

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) { // B-vaiheen tapahtumat

		Asiakas a;
		switch (t.getTyyppi()) {

		case ARR1:
			palvelupisteet[0].lisaaJonoon(new Asiakas());
			saapumisprosessi.generoiSeuraava();
			break;
		//portsari
		case DEP1:
			a = palvelupisteet[0].otaJonosta();
			palvelupisteet[1].lisaaJonoon(a);
			break;
		//narikka 1
		case DEP2:
			a = palvelupisteet[1].otaJonosta();
			int seuraava = random.nextInt(5 - 2) + 2;
			
			palvelupisteet[seuraava].lisaaJonoon(a);
			break;
		//baaritiski
		case DEP3:
			a = palvelupisteet[2].otaJonosta();
			int valinta = random.nextInt(4 - 1) + 1;
			
			if(valinta == 1) {
				palvelupisteet[3].lisaaJonoon(a);
			} else if(valinta == 2) {
				palvelupisteet[4].lisaaJonoon(a);
			} else if(valinta == 3) {
				palvelupisteet[5].lisaaJonoon(a);
			} else if(valinta == 4) {
				palvelupisteet[6].lisaaJonoon(a);
			}
			break;
		//tanssilattia
		case DEP4:
			a = palvelupisteet[3].otaJonosta();
			valinta = random.nextInt(4 - 1) + 1;
			
			if(valinta == 1) {
				palvelupisteet[2].lisaaJonoon(a);
			} else if(valinta == 2) {
				palvelupisteet[4].lisaaJonoon(a);
			} else if(valinta == 3) {
				palvelupisteet[5].lisaaJonoon(a);
			} else if(valinta == 4) {
				palvelupisteet[6].lisaaJonoon(a);
			}
			break;
		//karaoke
		case DEP5:
			a = palvelupisteet[4].otaJonosta();
			valinta = random.nextInt(4 - 1) + 1;
			
			if(valinta == 1) {
				palvelupisteet[2].lisaaJonoon(a);
			} else if(valinta == 2) {
				palvelupisteet[3].lisaaJonoon(a);
			} else if(valinta == 3) {
				palvelupisteet[5].lisaaJonoon(a);
			} else if(valinta == 4) {
				palvelupisteet[6].lisaaJonoon(a);
			}
			break;
		//istuminen
		case DEP6:
			a = palvelupisteet[5].otaJonosta();
			valinta = random.nextInt(4 - 1) + 1;
			
			if(valinta == 1) {
				palvelupisteet[2].lisaaJonoon(a);
			} else if(valinta == 2) {
				palvelupisteet[3].lisaaJonoon(a);
			} else if(valinta == 3) {
				palvelupisteet[4].lisaaJonoon(a);
			} else if(valinta == 4) {
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
		System.out.println("Tulokset ... puuttuvat vielä");
	}

}