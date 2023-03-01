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
		moottori.setAsiakasmaara(ui.getAsiakkaat());
		for(int i = 0; i < ui.getVisualisoinnit().length; i++) {
			ui.getVisualisoinnit()[i].tyhjennaNaytto();
		}
		//ui.getVisualisoinnit().tyhjennaNaytto();
		((Thread)moottori).start();
	}
	
	@Override
	public void hidasta() { // hidastetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*1.10));
	}

	@Override
	public void nopeuta() { // nopeutetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*0.9));
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

	@Override
	public void poistaAsiakkaanVisualisointi(int jononNro) {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisoinnit()[jononNro].poistaAsiakas();
			}
		});
	}
	
	
	//Lähettää simuloinnin tulokset käyttöliittymään
	public void naytaTulokset(int palvellutAsiakkaat, double lapimenoaikojenKA,
			double throughput, double[] serviceTime, double[] responceTime, double[] jononpituus) {
		Platform.runLater(()->ui.setTulokset(palvellutAsiakkaat, lapimenoaikojenKA, throughput, serviceTime, responceTime, jononpituus));
	}
}
