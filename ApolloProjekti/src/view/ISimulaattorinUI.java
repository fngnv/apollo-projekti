package view;

public interface ISimulaattorinUI {
	
	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	public double getAika();
	public long getViive();
	public int getAsiakkaat();
	
	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa 
	public void setLoppuaika(double aika);
	public void setTulokset(int pAsiakkaat, double aikojenKA, double throughput, double[] serviceTime, double[] responseTime, double[] jononpituus);
	public void tulostaJononpituus(int labelNro, int pituus);
	
	// Kontrolleri tarvitsee  
	public IVisualisointi[] getVisualisoinnit();

}
