package simu.model;

import java.sql.SQLException;

public interface IDatantallenusDAO {
	
	public void saveSimulationResults(int pAsiakkaat, double aikojenKA, double throughput, double[] serviceTime, double[] responseTime, double[] jononpituus) throws SQLException;
	public void printSimulationResults() throws SQLException;
	public void close() throws SQLException;
}
