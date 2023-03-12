package controller;

import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;
import view.ISimulaattorinUI;
/**
 * Luokka toimii rajapinnan ja moottorin valisenä yhteydenpitäjänä
 * 
 * @author 
 * Täydentänyt Vera Finogenova
 *
 */
public class Kontrolleri implements IKontrolleri{
	
	private IMoottori moottori; 
	private ISimulaattorinUI ui;
	
	public Kontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
	}
		
	@Override
	public void kaynnistaSimulointi() {
		moottori = new OmaMoottori(this);
		moottori.setSimulointiaika(ui.getAika());
		moottori.setViive(ui.getViive());
		for(int i = 0; i < ui.getVisualisoinnit().length; i++) {
			ui.getVisualisoinnit()[i].tyhjennaNaytto();
		}
		((Thread)moottori).start();
	}
	
	@Override
	public void hidasta() { 
		moottori.setViive((long)(moottori.getViive()*1.5));
	}

	@Override
	public void nopeuta() { 
		moottori.setViive((long)(moottori.getViive()*0.5));
	}
		
	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(()->ui.setLoppuaika(aika)); 
	}

	
	@Override
	public void visualisoiAsiakas(int jononNro) {
		Platform.runLater(new Runnable(){
			public void run(){
				ui.getVisualisoinnit()[jononNro].uusiAsiakas();
			}
		});
	}
	
	/**
	 * Metodi palauttaa seed-arvon, jonka käytetään saapumisväliaikojen generoinissa, jonka käyttääjä syöttää käyttöliittymälle
	 * (käyttöliittymässä se on nimellä saapumisien vähimmäisväliaika).
	 * 
	 * @return arvo, josta tulee saapumisväliaikageneraattorin seed
	 */
	public int getMinValiaika() {
		return ui.getMinValiaika();
	}
	
	/**
	 * Metodi palauttaa mean-arvon, jonka käytetään saapumisväliaikojen generoinnissa, jonka käyttäjä syöttää käyttöliittymälle
	 *  (käyttölittymässä nimellä saapumisien enimmäisväliaika)
	 *  
	 *  @return arvo, josta tulee saapumisväliaikageneraattorin mean
	 */
	public int getMaxValiaika() {
		return ui.getMaxValiaika();
	}

	/**
	 * Metodi poistaa asiakkaan visualisointi jonosta, jonka indeksi se saa parametrina, silloin kun asiakas on poistunut jonosta
	 * 
	 * @param jononNro jono, josta pitää poista asiakkaan visualisointi
	 */
	@Override
	public void poistaAsiakkaanVisualisointi(int jononNro) {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisoinnit()[jononNro].poistaAsiakas();
			}
		});
	}
	
	/**
	 * Saa parametrina simuloinnin tulokset ja laittaa niitä näkyville, kutsumalla SimulaattorinUI-luokan metodi joka asettaa tulokset päänäkymälle
	 * 
	 * @param palvellutAsiakkaat palveluiden asiakkaiden kokonaismäärä
	 * @param lMenoKA läpimenoaikojen keskiarvo
	 * @param throughput simuloitavan järjestelmän kokonaissuoritusteho
	 * @param serviceTime kaikkien palvelupisteiden service time -arvot taulukkona
	 * @param responseTime kaikkien palvelupisteiden responce time -arvot taulukkona
	 * @param jononpituus kaikkien palvelupisteiden keskimääräiset jononpituudet taulukkona
	 */
	@Override
	public void naytaTulokset(int palvellutAsiakkaat, double lapimenoaikojenKA,
			double throughput, double[] serviceTime, double[] responceTime, double[] jononpituus) {
		Platform.runLater(()->ui.setTulokset(palvellutAsiakkaat, lapimenoaikojenKA, throughput, serviceTime, responceTime, jononpituus));
	}
	
	//Asettaa jonojen pituudeet käyttöliittymälle
	/**
	 * Laittaa näkyville jonojen pituudet kutsumalla käyttöliittymäluokan metodia, joka asettaa pituukset päänäkymälle 
	 * 
	 * @param pPiste palvelupiste, jonka kohdalle asetetaan pituus
	 * @param pituus asetettava pituus
	 */
	@Override
	public void naytaJononpituus(int pPiste, int pituus) {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.tulostaJononpituus(pPiste, pituus);
			}
		});
	}
}
