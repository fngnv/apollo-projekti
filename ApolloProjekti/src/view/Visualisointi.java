package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Luokka piirtää ja poistaa asiakkaiden visualisoinnit
 * 
 * @author 
 * Täydentänyt Vera Finogenova
 *
 */
public class Visualisointi extends Canvas implements IVisualisointi{

	private GraphicsContext gc;
	
	double i = 0;
	double j = 10;
	
	
	public Visualisointi(int w, int h) {
		super(w, h);
		gc = this.getGraphicsContext2D();
		tyhjennaNaytto();
	}
	

	public void tyhjennaNaytto() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void uusiAsiakas() {
		gc.setFill(Color.ORANGE);
		gc.fillOval(i,j,10,10);
		
		i = (i + 10) % this.getWidth();
		if (i==0) j+=10;			
	}
	
	/**
	 * metodi piilottaa asiakkaan visualisointi silloin kun se on lähtenyt pois jonosta
	 */
	@Override
	public void poistaAsiakas() {
		gc.setFill(Color.WHITE);
		gc.fillOval(i,j,10,10);
		
		i = (i - 10) % this.getWidth();
	}
	
}
