package simu.model;

import java.sql.SQLException;

/**
*
* @author Silja Mattila
*/

/**
 * Tämä rajapinta sisältää metodit Datantallennus-luokalle.
*/

public interface IDatantallenusDAO {
	
	 /**
	 * Tallentaa simulaatiotulokset tietokantaan.
	 * @param simulointiaika simulaatioaika
	 * @param valiaikaMin minimiaika kahden saapumisen välillä
	 * @param valiaikaMax maksimiaika kahden saapumisen välillä
	 * @param aikaViive viive ennen palvelua
	 * @param pAsiakkaat palveltavien asiakkaiden määrä
	 * @param aikojenKA asiakaspalveluaikojen keskiarvo
	 * @param throughput palveluiden määrä yksikköä kohden aikayksikköä kohden
	 * @param serviceTime palveluaikojen jakauman tilastot
	 * @param responseTime palvelun vastausaikojen jakauman tilastot
	 * @param jononpituus jonon pituuden jakauman tilastot
	 * @throws SQLException, jos tietokantavirhe
	 */
    public void saveSimulationResults(double simulointiaika, int valiaikaMin, int valiaikaMax, double aikaViive, int pAsiakkaat, double aikojenKA, double throughput, double[] serviceTime, double[] responseTime, double[] jononpituus) throws SQLException;
    
    /**
     * Tulostaa simulaatiotulokset tietokannasta.
     * @throws SQLException, jos tietokantavirhe
    */
    public void printSimulationResults() throws SQLException;
   
    /**
     * Sulkee tietokantayhteyden.
     * @throws SQLException, jos tietokantavirhe
    */
    public void close() throws SQLException;
}
