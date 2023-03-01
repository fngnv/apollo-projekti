package simu.framework;

import java.util.PriorityQueue;

public class Tapahtumalista {
	private PriorityQueue<Tapahtuma> lista = new PriorityQueue<Tapahtuma>();
	
	public Tapahtumalista(){
	 
	}
	
	public Tapahtuma poista(){
		return lista.remove();
	}
	
	public void lisaa(Tapahtuma t){
		lista.add(t);
	}
	
	public double getSeuraavanAika(){
		if(lista.peek() != null) {
			return lista.peek().getAika();
		} else {
			return Kello.getInstance().getAika();
		}
	}
	
	
}
