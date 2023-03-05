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



public class SimulaattorinGUI extends Application implements ISimulaattorinUI{
	IDatantallenusDAO datantallennus = new Datantallennus();

	//Kontrollerin esittely (tarvitaan käyttöliittymässä)
	private IKontrolleri kontrolleri;

	// Käyttöliittymäkomponentit:
	private TextField aika;
	private TextField viive;
	private TextField asiakkaat;
	private Label tulos;
	private Label aikaLabel;
	private Label viiveLabel;
	private Label asiakkaatLabel;
	private Label tulosLabel;
	private GridPane tulokset;
	private TableView<Tulokset> tulostaulukko;
	
	private Button kaynnistaButton;
	private Button aiemmatAjotBtn;

	private IVisualisointi portsari;
	private IVisualisointi narikka1;
	private IVisualisointi baaritiski;
	private IVisualisointi tanssilattia;
	private IVisualisointi karaoke;
	private IVisualisointi istuminen;
	private IVisualisointi narikka2;
	
	private IVisualisointi[] visualisoinnit;


	@Override
	public void init(){
		
		Trace.setTraceLevel(Level.INFO);
		
		kontrolleri = new Kontrolleri(this);
	}

	@Override
	public void start(Stage primaryStage) {
		// Käyttöliittymän rakentaminen
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
	            	if(Integer.parseInt(asiakkaat.getText()) < 500) {
	        			Alert alert = new Alert(AlertType.ERROR);
	        			alert.setTitle("Error");
	        			alert.setHeaderText("Liian pieni asiakasmäärä");
	        			alert.setContentText("Pienin sallittu asiakasmäärä on 500");
	        			
	        			alert.showAndWait();
	        		} else {
	        			kontrolleri.kaynnistaSimulointi();
		                kaynnistaButton.setDisable(true);
	        		}	             
	            }
	        });
			
			aiemmatAjotBtn = new Button();
			aiemmatAjotBtn.setText("Aiemmat ajot");
			aiemmatAjotBtn.setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {
			        Taulukko taulukko = new Taulukko();
			        try {
						taulukko.naytaTaulukko();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			});

			aikaLabel = new Label("Simulointiaika: ");
			aikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        aika = new TextField("25200"); //Oletus aukioloaika(25200 sek = 7 t). Käyttäjä saa vaihtaa
	        aika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        aika.setPrefWidth(150);

	        viiveLabel = new Label("Viive: ");
			viiveLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        viive = new TextField("0");
	        viive.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        viive.setPrefWidth(150);
	        
	        asiakkaatLabel = new Label("Asiakkaiden enimmäismäärä: ");
	        asiakkaatLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        asiakkaat = new TextField("1200");
	        asiakkaat.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        asiakkaat.setPrefWidth(150);
	                	        
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
	        hBox.setPadding(new Insets(15, 12, 15, 12)); // marginaalit ylä, oikea, ala, vasen
	        hBox.setSpacing(10);   // noodien välimatka 10 pikseliä
	        
	        VBox oikeapuolinenBox = new VBox();
	        oikeapuolinenBox.setPadding(new Insets(15, 12, 15, 12));
	        oikeapuolinenBox.setSpacing(10);
	        
	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.TOP_LEFT);
	        grid.setVgap(10);
	        grid.setHgap(5);

	        grid.add(aikaLabel, 0, 0);   // sarake, rivi
	        grid.add(aika, 1, 0);          // sarake, rivi
	        grid.add(viiveLabel, 0, 1);      // sarake, rivi
	        grid.add(viive, 1, 1);             // sarake, rivi
	        grid.add(asiakkaatLabel, 0, 2);
	        grid.add(asiakkaat, 1, 2);         // sarake, rivi
	        grid.add(tulosLabel, 0, 3);      // sarake, rivi
	        grid.add(tulos, 1, 3);
	        grid.add(kaynnistaButton,0, 4);  // sarake, rivi
	        grid.add(aiemmatAjotBtn, 1, 4);
	        
	        portsari = new Visualisointi(400,40);
	        narikka1 = new Visualisointi(400, 40);
	        baaritiski = new Visualisointi(400, 40);
	        tanssilattia = new Visualisointi(400, 40);
	        karaoke = new Visualisointi(400, 40);
	        istuminen = new Visualisointi(400, 40);
	        narikka2 = new Visualisointi(400, 40);
	        
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
	        oikeapuolinenBox.getChildren().addAll(pLabel, (Canvas)portsari, n1Label, (Canvas)narikka1, bLabel, (Canvas)baaritiski, tLabel, (Canvas)tanssilattia,
	        		kLabel, (Canvas)karaoke, iLabel, (Canvas)istuminen, n2Label, (Canvas)narikka2);
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


	//Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

	@Override
	public double getAika(){
		return Double.parseDouble(aika.getText());
	}
	
	public int getAsiakkaat() {
		//jos ei toimi, yritä laittaa se onClickin kohdalle
		
			return Integer.parseInt(asiakkaat.getText());
		
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

	//Metodi saa simuloinnin tulokset ja asettaa ne käyttöliittymään: osan grid panen sisään ja 
	//palvelupistekohtaiset tulokset taulukkoon
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
		
		for(Tulokset tulos : data) {
			System.out.println(tulos);
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
		
		try {
			datantallennus.saveSimulationResults(pAsiakkaat, aikojenKA, throughput, serviceTime, responseTime, jononpituus);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			datantallennus.printSimulationResults();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			datantallennus.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public IVisualisointi[] getVisualisoinnit() {
		 return visualisoinnit;
	}
	
	
	// JavaFX-sovelluksen (käyttöliittymän) käynnistäminen

	public static void main(String[] args) {
		launch(args);
	}

	//Dataa jonka käytetään taulukossa
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

		public static void annetutArvot() {
			// TODO Auto-generated method stub
			
		}

		public String getPalveluPiste() {
			return palveluPiste.get();
		}

		public void setPalveluPiste(String piste) {
			this.palveluPiste.set(piste);
		}

		public String getServTime() {
			return servTime.get();
		}

		public void setServTime(double servTime) {
			this.servTime.set(Double.toString(servTime));
		}

		public String getRespTime() {
			return respTime.get();
		}

		public void setRespTime(double respTime) {
			this.respTime.set(Double.toString(respTime));;
		}

		public String getJononpituus() {
			return jononpituus.get();
		}

		public void setJononpituus(double jononpituus) {
			this.jononpituus.set(Double.toString(jononpituus));;
		}
		
		@Override
		public String toString() {
			return palveluPiste + " " + servTime + " " + respTime + " " + jononpituus;
		}
		
	}
	
}
