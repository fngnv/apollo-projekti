package simu.framework;
import controller.IKontrolleri;
import eduni.distributions.*;
import simu.model.TapahtumanTyyppi;
public class Saapumisprosessi {
	
	private ContinuousGenerator generaattori;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi tyyppi;
	private IKontrolleri kontrolleri;

	public Saapumisprosessi(ContinuousGenerator g, Tapahtumalista tl, TapahtumanTyyppi tyyppi, IKontrolleri kontrolleri){
		this.generaattori = g;
		this.tapahtumalista = tl;
		this.tyyppi = tyyppi;
		this.kontrolleri = kontrolleri;
	}

	public void generoiSeuraava(){
		Tapahtuma t = new Tapahtuma(tyyppi, Kello.getInstance().getAika()+generaattori.sample());
		tapahtumalista.lisaa(t);
		//kontrolleri.visualisoiAsiakas();
	}

}
