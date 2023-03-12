package view;

/**
 * 
 * Rajapinta määrittää SimulaattorinGUI-luokan metodit
 *
 */
public interface ISimulaattorinUI {
	
	// Syötteet, jotka kontrolleri välittää Moottorille
	
	/**
	 * Palauttaa simulointiajan, jonka käyttäjä syöttää
	 * 
	 * @return simulointiaika
	 */
	public double getAika();
	/**
	 * Palauttaa viiveen, jonka käyttäjä syöttää
	 * 
	 * @return viive
	 */
	public long getViive();
	/**
	 * Palauttaa Negexp generaattorin tarvitseman seedin (käyttöliittymässä nimellä saapumisien vähimmäisväliaika), jonka käyttäjä syöttää tekstikenttään 
	 * 
	 * @return minimiväliaika käyttäjän näkökulmasta, seed Negexp generaattoria varten ohjelman näkökulmasta
	 */
	public int getMinValiaika();
	/**
	 * Palauttaa Negexp generaattorin tarvitseman mean-arvon (Käyttöliittymässä nimellä saapumisien enimmäisväliaika), jonka käyttäjä syöttää tekstikenttään
	 * 
	 * @return maksimiväliaika käyttäjän näkökulmasta, Negexp generaattorin mean ohjelman näkökulmasta
	 */
	public int getMaxValiaika();
	
	//Moottorin tulokset, jotka kontrolleri antaa käyttöliittymälle
	
	/**
	 * Metodi asettaa simulaation päättymisajan käyttöliittymään
	 * 
	 * @param aika simulaation päättymisaika
	 */
	public void setLoppuaika(double aika);
	/**
	 * Asettaa simuloinnin tulokset käyttöliittymään
	 * 
	 * @param pAsiakkaat simulaation aikana palvellut asiakkaat
	 * @param aikojenKA läpimenoaikojen keskiarvo
	 * @param throughput koko järjestelmän suoritusteho
	 * @param serviceTime kaikkien palvelupisteiden keskimääräiset palveluajat taulukkona
	 * @param responseTime kaikkien palvelupisteiden keskimääräiset läpimenoajat taulukkona
	 * @param jononpituus kaikkien palvelupisteiden keskimääräiset jonojenpituudet taulukkona
	 */
	public void setTulokset(int pAsiakkaat, double aikojenKA, double throughput, double[] serviceTime, double[] responseTime, double[] jononpituus);
	/**
	 * Tulostaa palvelupisteen ajantasasinen jononpituus labeliin, joka on kyseisen jonon visualisoinnin oikealla puolella
	 * 
	 * @param labelNro labelin, johon tulostetaan, indeksi taulukossa
	 * @param pituus tämänhetkinen jononpituus
	 */
	public void tulostaJononpituus(int labelNro, int pituus);
	
	/**
	 * Palauttaa visualisointi-luokan oliot taulukkona 
	 *   
	 * @return visualisointi-luokan oliot taulukkona
	 */
	public IVisualisointi[] getVisualisoinnit();

}
