package simu.model;

import java.sql.*;

import javafx.scene.control.TextField;


public class Datantallennus implements IDatantallenusDAO{
	
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

	    public void saveSimulationResults(double simulointiaika, int valiaikaMin, int valiaikaMax, double aikaViive, int pAsiakkaat, double aikojenKA, double throughput, double[] serviceTime, double[] responseTime, double[] jononpituus) throws SQLException {
	    	// Valmistellaan SQL-lauseke datan tallentamiseksi
	        PreparedStatement stmt = conn.prepareStatement("INSERT INTO simulaatiotulokset (simulointiaika, valiaikaMin, valiaikaMax, aikaViive, pAsiakkaat, aikojenKA, throughput, serviceTime1, serviceTime2, serviceTime3, serviceTime4, serviceTime5, serviceTime6, serviceTime7, responseTime1, responseTime2, responseTime3, responseTime4, responseTime5, responseTime6, responseTime7, jononpituus1, jononpituus2, jononpituus3, jononpituus4, jononpituus5, jononpituus6, jononpituus7) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

	     // Asetetaan parametrien arvot SQL-lausekkeeseen
	        stmt.setDouble(1, simulointiaika);
	        stmt.setInt(2, valiaikaMin);
	        stmt.setInt(3, valiaikaMax);
	        stmt.setDouble(4, aikaViive);
	        stmt.setInt(5, pAsiakkaat);
	        stmt.setDouble(6, aikojenKA);
	        stmt.setDouble(7, throughput);
	        for (int i = 0; i < 7; i++) {
	            stmt.setDouble(8 + i, serviceTime[i]);
	            stmt.setDouble(15 + i, responseTime[i]);
	            stmt.setDouble(22 + i, jononpituus[i]);
	        }

	     // Suoritetaan SQL-lauseke datan tallentamiseksi
	        stmt.executeUpdate();
	    }
		
		public void printSimulationResults() throws SQLException {
			// Valmistellaan SQL-lauseke datan noutamiseksi
		    Statement stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT * FROM simulaatiotulokset");

		 // Tulostetaan 
		    System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s\n",
		            "ID", "simulointiaika", "valiaikaMin", "valiaikaMax", "aikaViive", "pAsiakkaat", "aikojenKA", "throughput", "serviceTime1", "serviceTime2", "serviceTime3", "serviceTime4", "serviceTime5", "serviceTime6", "serviceTime7",
		            "responseTime1", "responseTime2", "responseTime3", "responseTime4", "responseTime5", "responseTime6", "responseTime7", "jononpituus1", "jononpituus2", "jononpituus3",
		            "jononpituus4", "jononpituus5", "jononpituus6", "jononpituus7");

		    // Printataan data
		    while (rs.next()) {
		    	System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f%-15.8f\n",
		    		    rs.getInt("id"), rs.getInt("simulointiaika"), rs.getInt("valiaikaMin"), rs.getInt("valiaikaMax"), rs.getInt("aikaViive"), rs.getInt("pAsiakkaat"), rs.getDouble("aikojenKA"), rs.getDouble("throughput"),
		    		    rs.getDouble("serviceTime1"), rs.getDouble("serviceTime2"), rs.getDouble("serviceTime3"), rs.getDouble("serviceTime4"), rs.getDouble("serviceTime5"),
		    		    rs.getDouble("serviceTime6"), rs.getDouble("serviceTime7"), rs.getDouble("responseTime1"), rs.getDouble("responseTime2"), rs.getDouble("responseTime3"),
		    		    rs.getDouble("responseTime4"), rs.getDouble("responseTime5"), rs.getDouble("responseTime6"), rs.getDouble("responseTime7"), rs.getDouble("jononpituus1"),
		    		    rs.getDouble("jononpituus2"), rs.getDouble("jononpituus3"), rs.getDouble("jononpituus4"), rs.getDouble("jononpituus5"), rs.getDouble("jononpituus6"),
		    		    rs.getDouble("jononpituus7"));
		    }

		    
		    rs.close();
		    stmt.close();
		}

	    public void close() throws SQLException {
	        conn.close();
	    }
	}
