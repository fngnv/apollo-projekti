package controller;

public interface IKontrolleri {
	
		// Rajapinta, joka tarjotaan  käyttöliittymälle:
	
		public void kaynnistaSimulointi();
		public void nopeuta();
		public void hidasta();
		
		// Rajapinta, joka tarjotaan moottorille:
		
		public void naytaLoppuaika(double aika);
		public void visualisoiAsiakas(int jononNro);
		public void poistaAsiakkaanVisualisointi(int jononNro);
		public void naytaTulokset(double throughput, double[] serviceTime, double[] responseTime, double[] jononpituus);

}
