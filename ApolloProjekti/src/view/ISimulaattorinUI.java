package view;

public interface ISimulaattorinUI {
	
	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	public double getAika();
	public long getViive();
	
	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa 
	public void setLoppuaika(double aika);
	public void setTulokset(double throughput, double[] serviceTime, double[] responseTime, double[] jononpituus);
	
	// Kontrolleri tarvitsee  
	public IVisualisointi[] getVisualisoinnit();

}
