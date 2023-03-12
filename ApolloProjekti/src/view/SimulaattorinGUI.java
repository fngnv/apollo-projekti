package view;

import java.sql.SQLException;
import java.text.DecimalFormat;
import controller.*;
//import simu.model.Tulokset;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.Datantallennus;
import simu.model.IDatantallenusDAO;
import simu.model.OmaMoottori;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

/**
 * Luokka rakentaa käyttöliittymän ja käsittele käyttäjän toiminnatS
 * 
 * @author 
 * Täydentänyt Vera Finogenova ja Silja Mattila
 */
public class SimulaattorinGUI extends Application implements ISimulaattorinUI{
	IDatantallenusDAO datantallennus = new Datantallennus();

	private IKontrolleri kontrolleri;

	private TextField aika, viive, minValiaika, maxValiaika;
	private Label tulos, aikaLabel, viiveLabel, tulosLabel, minValiaikaLabel, maxValiaikaLabel;
	private GridPane tulokset;
	private TableView<Tulokset> tulostaulukko;
	private Label plkm = new Label(), n1lkm = new Label(), blkm = new Label(),
				tlkm = new Label(), klkm = new Label(), ilkm = new Label(), n2lkm = new Label();
	
	private Label[] lkmLabelit = new Label[] {
			plkm, n1lkm, blkm, tlkm, klkm, ilkm, n2lkm
	};
	
	private Button kaynnistaButton, aiemmatAjotBtn, hidastaButton, nopeutaButton;

	private IVisualisointi portsari, narikka1, baaritiski, tanssilattia, karaoke, istuminen, narikka2;
	
	private IVisualisointi[] visualisoinnit;


	@Override
	public void init(){
		
		Trace.setTraceLevel(Level.INFO);
		
		kontrolleri = new Kontrolleri(this);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
			        Platform.exit();
			        System.exit(0);
			    }
			});
						
			
			primaryStage.setTitle("Simulaattori");

			kaynnistaButton = new Button();
			kaynnistaButton.setText("Käynnistä simulointi");
			kaynnistaButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	        		kontrolleri.kaynnistaSimulointi();
		            kaynnistaButton.setDisable(true);             
	            }
	        });
			
			hidastaButton = new Button();
			hidastaButton.setText("Hidasta");
			hidastaButton.setOnAction(e -> kontrolleri.hidasta());

			nopeutaButton = new Button();
			nopeutaButton.setText("Nopeuta");
			nopeutaButton.setOnAction(e -> kontrolleri.nopeuta());
			
			aiemmatAjotBtn = new Button();
			aiemmatAjotBtn.setText("Aiemmat ajot");
			aiemmatAjotBtn.setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {
			        Taulukko taulukko = new Taulukko();
			        try {
						taulukko.naytaTaulukko();
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
			    }
			});

			aikaLabel = new Label("Simulointiaika: ");
			aikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        aika = new TextField("25200"); //Oletus aukioloaika(25200 sek = 7 t). Käyttäjä voi vaihtaa
	        aika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        aika.setPrefWidth(150);

	        viiveLabel = new Label("Viive: ");
			viiveLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        viive = new TextField("0");
	        viive.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        viive.setPrefWidth(150);
	        
	        minValiaikaLabel = new Label("Saapumisien vähimmäisväliaika: ");
	        minValiaikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        minValiaika = new TextField("10");
	        minValiaika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        minValiaika.setPrefWidth(150);
	        
	        maxValiaikaLabel = new Label("Saapumisien enimmäisväliaika: ");
	        maxValiaikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        maxValiaika = new TextField("30");
	        maxValiaika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        maxValiaika.setPrefWidth(150);
	                	        
	        tulosLabel = new Label("Kokonaisaika:");
			tulosLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        tulos = new Label();
	        tulos.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        tulos.setPrefWidth(150);
	        
	        tulostaulukko = new TableView<Tulokset>();
	        tulostaulukko.setMinWidth(430);
	        tulostaulukko.setMaxHeight(200);
	        tulostaulukko.setVisible(false);
	        
	        tulokset = new GridPane();
	        tulokset.setPadding(new Insets(15, 12, 15, 12));
	        tulokset.setAlignment(Pos.BOTTOM_LEFT);
	        tulokset.setVgap(10);
	        tulokset.setHgap(5);
	        
	        HBox hBox = new HBox();
	        hBox.setPadding(new Insets(15, 12, 15, 12)); 
	        hBox.setSpacing(10); 
	        
	        VBox oikeapuolinenBox = new VBox();
	        oikeapuolinenBox.setPadding(new Insets(15, 12, 15, 12));
	        oikeapuolinenBox.setSpacing(10);
	        
	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.TOP_LEFT);
	        grid.setVgap(10);
	        grid.setHgap(5);

	        grid.add(aikaLabel, 0, 0);  
	        grid.add(aika, 1, 0);      
	        grid.add(viiveLabel, 0, 1);   
	        grid.add(viive, 1, 1);    
	        grid.add(minValiaikaLabel, 0, 2);
	        grid.add(minValiaika, 1, 2); 
	        grid.add(maxValiaikaLabel, 0, 3);
	        grid.add(maxValiaika, 1, 3); 
	        grid.add(tulosLabel, 0, 4);
	        grid.add(tulos, 1, 4);
	        grid.add(kaynnistaButton,0, 6);
	        grid.add(aiemmatAjotBtn, 1, 6);
	        grid.add(nopeutaButton, 0, 5); 
	        grid.add(hidastaButton, 1, 5); 
	        
	        portsari = new Visualisointi(400,40);
	        narikka1 = new Visualisointi(400, 40);
	        baaritiski = new Visualisointi(400, 40);
	        tanssilattia = new Visualisointi(400, 40);
	        karaoke = new Visualisointi(400, 40);
	        istuminen = new Visualisointi(400, 40);
	        narikka2 = new Visualisointi(400, 40);
	        
	        HBox pBox = new HBox();
	        HBox n1Box = new HBox();
	        HBox bBox = new HBox();
	        HBox tBox = new HBox();
	        HBox kBox = new HBox();
	        HBox iBox = new HBox();
	        HBox n2box = new HBox();
	        
	        pBox.getChildren().addAll((Canvas)portsari, plkm);
	        n1Box.getChildren().addAll((Canvas)narikka1, n1lkm);
	        bBox.getChildren().addAll((Canvas)baaritiski, blkm);
	        tBox.getChildren().addAll((Canvas)tanssilattia, tlkm);
	        kBox.getChildren().addAll((Canvas)karaoke, klkm);
	        iBox.getChildren().addAll((Canvas)istuminen, ilkm);
	        n2box.getChildren().addAll((Canvas)narikka2, n2lkm);
	        
	        Label pLabel = new Label("Portsari");
	        Label n1Label = new Label("Saapumisnarikka");
	        Label bLabel = new Label("Baaritiski");
	        Label tLabel = new Label("Tanssilattia");
	        Label kLabel = new Label("Karaoke");
	        Label iLabel = new Label("Istuminen (tai wc)");
	        Label n2Label = new Label("Poistumisnarikka");
	        
	        pLabel.setFont(Font.font("Roboto", FontWeight.NORMAL, 20));
	        n1Label.setFont(Font.font("Roboto", FontWeight.NORMAL, 20));
	        bLabel.setFont(Font.font("Roboto", FontWeight.NORMAL, 20));
	        tLabel.setFont(Font.font("Roboto", FontWeight.NORMAL, 20));
	        kLabel.setFont(Font.font("Roboto", FontWeight.NORMAL, 20));
	        iLabel.setFont(Font.font("Roboto", FontWeight.NORMAL, 20));
	        n2Label.setFont(Font.font("Roboto", FontWeight.NORMAL, 20));
	        
	        VBox vasenpuolinenBox = new VBox();
	        vasenpuolinenBox.setPadding(new Insets(15, 12, 15, 12));
	        vasenpuolinenBox.setSpacing(10);
	        

	        // Täytetään boxit:
	        oikeapuolinenBox.getChildren().addAll(pLabel, pBox, n1Label, n1Box, bLabel, bBox, tLabel, tBox,
	        		kLabel, kBox, iLabel, iBox, n2Label, n2box);
	        oikeapuolinenBox.setBorder(new Border(new BorderStroke(Color.ORANGE, 
	                BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
	        vasenpuolinenBox.getChildren().addAll(grid, tulokset, tulostaulukko);
	        hBox.getChildren().addAll(vasenpuolinenBox, oikeapuolinenBox);
	        
	        visualisoinnit = new IVisualisointi[] {
	        		portsari, narikka1, baaritiski, tanssilattia, karaoke, istuminen, narikka2
	        };
	        
	        Scene scene = new Scene(hBox);
	        primaryStage.setScene(scene);
	        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	        primaryStage.setX(screenBounds.getMinX());
	        primaryStage.setY(screenBounds.getMinY());
	        primaryStage.setWidth(screenBounds.getWidth());
	        primaryStage.setHeight(screenBounds.getHeight());
	        primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Palauttaa simulointiajan, jonka käyttäjä syöttää tekstikenttään
	 * 
	 * @return simulointiaika
	 */
	@Override
	public double getAika(){
		return Double.parseDouble(aika.getText());
	}
	
	/**
	 * Palauttaa Negexp generaattorin tarvitseman seedin (käyttöliittymässä nimellä saapumisien vähimmäisväliaika), jonka käyttäjä syöttää tekstikenttään 
	 * 
	 * @return minimiväliaika käyttäjän näkökulmasta, seed Negexp generaattoria varten ohjelman näkökulmasta
	 */
	@Override
	public int getMinValiaika() {
		return Integer.parseInt(minValiaika.getText());		
	}

	/**
	 * Palauttaa Negexp generaattorin tarvitseman mean-arvon (Käyttöliittymässä nimellä saapumisien enimmäisväliaika), jonka käyttäjä syöttää tekstikenttään
	 * 
	 * @return maksimiväliaika käyttäjän näkökulmasta, Negexp generaattorin mean ohjelman näkökulmasta
	 */
	@Override
	public int getMaxValiaika() {
		return Integer.parseInt(minValiaika.getText());
	}
	
	@Override
	public long getViive(){
		return Long.parseLong(viive.getText());
	}

	@Override
	public void setLoppuaika(double aika){
		 DecimalFormat formatter = new DecimalFormat("#0.00");
		 this.tulos.setText(formatter.format(aika));
	}

	/**
	 * Metodi saa simuloinnin tulokset ja asettaa ne käyttöliittymään: osan grid panen sisään ja palvelupistekohtaiset tulokset taulukkoon (TableWiew)
	 * 
	 * @param pAsiakkaat simulionnin aikana palvellut asiakkaat
	 * @param aikojenKA läpimenoaikojen  keskiarvo
	 * @param throughput koko järjestelmän suoritusteho
	 * @param serviceTime kaikkien palvelupisteiden service time tulokset
	 * @param responseTime kaikkien palvelupisteiden response time tulokset
	 * @param jononpituus kaikkien palvelupisteiden keskimääräiset jononpituudet
	 */
	@Override
	public void setTulokset(int pAsiakkaat, double aikojenKA, double throughput, double[] serviceTime, double[] responseTime, double[] jononpituus) {
		DecimalFormat f = new DecimalFormat("#####.##");
		
		String[] palvelupisteet = new String[] {
				"Portsari", "Narikka1", "Baaritiski", "Tanssilattia", "Karaoke", "Istuminen", "Narikka2"
		};
		
		ObservableList<Tulokset> data = FXCollections.observableArrayList();
		
		for(int i = 0; i < palvelupisteet.length; i++) {
			data.add(new Tulokset(palvelupisteet[i], f.format(serviceTime[i]), f.format(responseTime[i]), f.format(jononpituus[i])));
		}
		
		Label tuloksetLabel = new Label("Tulokset");
		tuloksetLabel.setFont(Font.font("Roboto", FontWeight.BOLD, 25));
		Label throughputLabel = new Label("Apollon kokonaissuoritusteho: ");
		throughputLabel.setFont(Font.font("Roboto", FontWeight.BOLD, 15));
		Label palvellutLabel = new Label("Palvellut asiakkaat: ");
		palvellutLabel.setFont(Font.font("Roboto", FontWeight.BOLD, 15));
		Label aikojenKALabel = new Label("Asiakkaiden läpimenoaikojen keskiarvo");
		aikojenKALabel.setFont(Font.font("Roboto", FontWeight.BOLD, 15));
		
		tulokset.add(tuloksetLabel, 0, 0);
		tulokset.add(throughputLabel, 0, 1);
		tulokset.add(new Label(f.format(throughput)), 1, 1);
		tulokset.add(palvellutLabel, 0, 2);
		tulokset.add(new Label(Integer.toString(pAsiakkaat)), 1, 2);
		tulokset.add(aikojenKALabel, 0, 3);
		tulokset.add(new Label(f.format(aikojenKA)), 1, 3);
		
		tulostaulukko.setEditable(true);
		
		TableColumn pPisteColumn = new TableColumn("Palvelupiste");
		TableColumn sTimeColumn = new TableColumn("Kesk. palveluaika");
		TableColumn rTimeColumn = new TableColumn("Kesk. läpimenoaika");
		TableColumn jPituusColumn = new TableColumn("Kesk. jononpituus");
		
		pPisteColumn.setMinWidth(90);
		sTimeColumn.setMinWidth(110);
		rTimeColumn.setMinWidth(115);
		jPituusColumn.setMinWidth(110);
		
		pPisteColumn.setCellValueFactory(new PropertyValueFactory<Tulokset, String>("palveluPiste"));
		sTimeColumn.setCellValueFactory(new PropertyValueFactory<Tulokset, String>("servTime"));
		rTimeColumn.setCellValueFactory(new PropertyValueFactory<Tulokset, String>("respTime"));
		jPituusColumn.setCellValueFactory(new PropertyValueFactory<Tulokset, String>("jononpituus"));
		

		tulostaulukko.setItems(data);
		tulostaulukko.getColumns().addAll(pPisteColumn, sTimeColumn, rTimeColumn, jPituusColumn);
		
		tulostaulukko.setVisible(true);
		
		double simulointiaika = Double.parseDouble(aika.getText());
		int valiaikaMin = Integer.parseInt(minValiaika.getText());
		int valiaikaMax = Integer.parseInt(maxValiaika.getText());
		double aikaViive = Double.parseDouble(viive.getText());
		
		try {
			datantallennus.saveSimulationResults(simulointiaika, valiaikaMin, valiaikaMax, aikaViive, pAsiakkaat, aikojenKA, throughput, serviceTime, responseTime, jononpituus);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			datantallennus.printSimulationResults();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			datantallennus.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Palauttaa canvakset, joihin piirretään asiakkaita, taulukkona
	 * 
	 * @return kaikki canvakset taulukkona
	 */
	@Override
	public IVisualisointi[] getVisualisoinnit() {
		 return visualisoinnit;
	}
	
	/**
	 * Laittaa parametrina saadun labelin kohdalle sen viereiseen jonon ajantasainen jononpituus
	 * 
	 * @param labelNro label johon tulostetaan pituus
	 * @param pituus ajantasainen pituus
	 */
	@Override
	public void tulostaJononpituus(int labelNro, int pituus) {
		lkmLabelit[labelNro].setText(Integer.toString(pituus));
	}

	/**
	 * Luokka sisältää dataa jonka käytetään TableWiew:ssä ja metodeja joita TableWiew vaatii datan käsittelyyn
	 * 
	 * @author Vera Finogenova
	 *
	 */
	public static class Tulokset {
		private SimpleStringProperty palveluPiste;
		private SimpleStringProperty servTime;
		private SimpleStringProperty respTime;
		private SimpleStringProperty jononpituus;
		
		public Tulokset(String piste, String sTime, String rTime, String jPituus) {
			palveluPiste = new SimpleStringProperty(piste);
			servTime = new SimpleStringProperty(sTime);
			respTime = new SimpleStringProperty(rTime);
			jononpituus = new SimpleStringProperty(jPituus);
		}

		/**
		 * Palauttaa palvelupisteen nimen
		 * 
		 * @return palvelupisteen nimi
		 */
		public String getPalveluPiste() {
			return palveluPiste.get();
		}

		/**
		 * Asettaa nimi palvelupisteelle
		 * 
		 * @param piste palvelupisteen nimi
		 */
		public void setPalveluPiste(String piste) {
			this.palveluPiste.set(piste);
		}

		/**
		 * Palauttaa palvelupisteen service time 
		 * 
		 * @return palvelupisteen service time 
		 */
		public String getServTime() {
			return servTime.get();
		}

		/**
		 * Asettaa palvelupisteen service time
		 * 
		 * @param servTime service time
		 */
		public void setServTime(double servTime) {
			this.servTime.set(Double.toString(servTime));
		}

		/**
		 * Palauttaa palvelupisteen response time
		 * 
		 * @return palvelupisteen response time
		 */
		public String getRespTime() {
			return respTime.get();
		}

		/**
		 * Asettaa palvelupisteen responce time
		 * 
		 * @param respTime responce time
		 */
		public void setRespTime(double respTime) {
			this.respTime.set(Double.toString(respTime));;
		}

		/**
		 * Palaauttaa palvelupisteen kesk. jononpituus
		 * 
		 * @return palvelupisteen jononpituus
		 */
		public String getJononpituus() {
			return jononpituus.get();
		}

		/**
		 * Asettaa palvelupisteen kesk.jononpituus
		 * 
		 * @param jononpituus palvelupisteen jononpituus
		 */
		public void setJononpituus(double jononpituus) {
			this.jononpituus.set(Double.toString(jononpituus));;
		}
		
		@Override
		public String toString() {
			return palveluPiste + " " + servTime + " " + respTime + " " + jononpituus;
		}
		
	}
	
}
