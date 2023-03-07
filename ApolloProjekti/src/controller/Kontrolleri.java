package controller;

import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;
import view.ISimulaattorinUI;

public class Kontrolleri implements IKontrolleri{   // UUSI
	
	private IMoottori moottori; 
	private ISimulaattorinUI ui;
	
	public Kontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
	}

	
	// Moottorin ohjausta:
		
	@Override
	public void kaynnistaSimulointi() {
		moottori = new OmaMoottori(this); // luodaan uusi moottorisäie jokaista simulointia varten
		moottori.setSimulointiaika(ui.getAika());
		moottori.setViive(ui.getViive());
		for(int i = 0; i < ui.getVisualisoinnit().length; i++) {
			ui.getVisualisoinnit()[i].tyhjennaNaytto();
		}
		//ui.getVisualisoinnit().tyhjennaNaytto();
		((Thread)moottori).start();
	}
	
	@Override
	public void hidasta() { // hidastetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*1.5));
	}

	@Override
	public void nopeuta() { // nopeutetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*0.5));
	}
	
	
	
	// Simulointitulosten välittämistä käyttöliittymään.
	// Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:
		
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
	
	public int getMinValiaika() {
		return ui.getMinValiaika();
	}
	
	public int getMaxValiaika() {
		return ui.getMaxValiaika();
	}

	@Override
	public void poistaAsiakkaanVisualisointi(int jononNro) {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisoinnit()[jononNro].poistaAsiakas();
			}
		});
	}
	
	//Lähettää simuloinnin tulokset käyttöliittymään
	@Override
	public void naytaTulokset(int palvellutAsiakkaat, double lapimenoaikojenKA,
			double throughput, double[] serviceTime, double[] responceTime, double[] jononpituus) {
		Platform.runLater(()->ui.setTulokset(palvellutAsiakkaat, lapimenoaikojenKA, throughput, serviceTime, responceTime, jononpituus));
	}
	
	//Asettaa jonojen pituudeet käyttöliittymälle
	@Override
	public void naytaJononpituus(int pPiste, int pituus) {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.tulostaJononpituus(pPiste, pituus);
			}
		});
	}
}
