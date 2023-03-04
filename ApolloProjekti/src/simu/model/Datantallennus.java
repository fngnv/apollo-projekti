package simu.model;

import java.sql.*;


public class Datantallennus {
	
	 private Connection conn;

	 public Datantallennus() {
			final String URL = "jdbc:mariadb://localhost:3306/tulokset";
			final String USERNAME = "olso";
			final String PWD = "olso";
			
			try {
				conn = DriverManager.getConnection(URL, USERNAME, PWD);
			}catch (SQLException e) {
					printSQLExceptions("Datantallennus()", e);
				System.exit(-1);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	    private void printSQLExceptions(String string, SQLException e) {
		// TODO Auto-generated method stub
		
	}
		public void saveSimulationResults(int pAsiakkaat, double aikojenKA, double throughput, double[] serviceTime, double[] responseTime, double[] jononpituus) throws SQLException {
	        // Prepare the statement for inserting data
	        PreparedStatement stmt = conn.prepareStatement("INSERT INTO simulaatiotulokset (pAsiakkaat, aikojenKA, throughput, serviceTime1, serviceTime2, serviceTime3, serviceTime4, serviceTime5, serviceTime6, serviceTime7, responseTime1, responseTime2, responseTime3, responseTime4, responseTime5, responseTime6, responseTime7, jononpituus1, jononpituus2, jononpituus3, jononpituus4, jononpituus5, jononpituus6, jononpituus7) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

	        // Set the values of the parameters in the statement
	        stmt.setInt(1, pAsiakkaat);
	        stmt.setDouble(2, aikojenKA);
	        stmt.setDouble(3, throughput);
	        for (int i = 0; i < 7; i++) {
	            stmt.setDouble(4 + i, serviceTime[i]);
	            stmt.setDouble(11 + i, responseTime[i]);
	            stmt.setDouble(18 + i, jononpituus[i]);
	        }

	        // Execute the statement to insert the data
	        stmt.executeUpdate();
	    }

	    public void close() throws SQLException {
	        conn.close();
	    }
	}
