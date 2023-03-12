package controller;

/**
 * Rajapinta sisältää kontrollerin toteuttamat metodit
 * 
 * @author 
 * 
 * täydentänyt Vera Finogenova
 */
public interface IKontrolleri {
	
		// Rajapinta, joka tarjotaan  käyttöliittymälle:
	
		public void kaynnistaSimulointi();
		public void nopeuta();
		public void hidasta();
		
		// Rajapinta, joka tarjotaan moottorille:
		
		public void naytaLoppuaika(double aika);
		public void visualisoiAsiakas(int jononNro);
		/**
		 * Metodi poistaa asiakkaan visualisointi jonosta, jonka numeron saa parametrina
		 * 
		 * @param jononNro jonon, josta poistetaan asiakkaan visualisointi, numero
		 */
		public void poistaAsiakkaanVisualisointi(int jononNro);
		/**
		 * Metodi saa simulointitulokset parametreina ja näyttää niitä käyttöliittymässä
		 * 
		 * @param palvellutAsiakkaat palveluiden asiakkaiden kokonaismäärä
		 * @param lMenoKA läpimenoaikojen keskiarvo
		 * @param throughput simuloitavan järjestelmän kokonaissuoritusteho
		 * @param serviceTime kaikkien palvelupisteiden service time -arvot taulukkona
		 * @param responseTime kaikkien palvelupisteiden responce time -arvot taulukkona
		 * @param jononpituus kaikkien palvelupisteiden keskimääräiset jononpituudet taulukkona
		 */
		public void naytaTulokset(int palvellutAsiakkaat, double lMenoKA, double throughput, double[] serviceTime, double[] responseTime, double[] jononpituus);
		
		/**
		 * Metodi lähettää käyttöliittymälle ilmoitus siitä, että ensimmäisenä parametrina saadun palvelupisteen kohdalla
		 * täytyy tulostaa sen tämänhetkinen jononpituus
		 * 
		 * @param pPiste numero, jolla palvelupiste löytyy taulukosta (eli indeksi)
		 * @param pituus tämänhetkinen jononpituus
		 */
		public void naytaJononpituus(int pPiste, int pituus);
		
		/**
		 * Metodi palauttaa saapumisväliaikojen minimiarvo
		 * 
		 * @return saapumisväliaikojen minimiarvo
		 */
		public int getMinValiaika();
		
		/**
		 * Metodi palauttaa saapumisväliaikojen maksimiarvo
		 * 
		 * @return saapumisväliaikojen maksimiarvo
		 */
		public int getMaxValiaika();

}
