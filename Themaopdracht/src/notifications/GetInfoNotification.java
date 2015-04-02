package notifications;

import java.util.ArrayList;

import main.ATDProgram;
import main.ATDProgram.notificationStyle;
import main.Customer;
import main.Fuel;
import main.MaintenanceSession;
import main.Part;
import main.Product;
import main.ProductSupplier;
import main.RefuelSession;
import main.Reservation;
import main.Stock;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GetInfoNotification extends Stage {
	private ComboBox<Product> partSelector = new ComboBox<Product>();
	private ComboBox<Product> fuelSelector = new ComboBox<Product>();
	private ComboBox<Customer> customerSelector = new ComboBox<Customer>();
	private ComboBox<MaintenanceSession> maintenanceSessionSelector = new ComboBox<MaintenanceSession>();
	private ComboBox<RefuelSession> refuelSelector = new ComboBox<RefuelSession>();
	private ComboBox<Reservation> reservationSelector = new ComboBox<Reservation>();
	private ComboBox<String> typeSelector = new ComboBox<String>();
	private TextField hoursMechanic = new TextField(), amountFuel = new TextField();
	private Button annuleren, ok;
	private Label melding;
	private String keuze;
	private VBox notification;
	private ATDProgram controller;
	private Stock stock;
	private notificationStyle stijl;

	public GetInfoNotification(Stage currentStage, String bericht,
			ATDProgram controller, ATDProgram.notificationStyle stijl) {
		super(StageStyle.UTILITY);
		initOwner(currentStage);
		initModality(Modality.WINDOW_MODAL);
		this.controller = controller;
		this.stijl = stijl;
		this.setResizable(false);
		melding = new Label(bericht);
		for (Product product : controller.getProducts()){
			if(product instanceof Part)	partSelector.getItems().addAll(product);
		}
		for (Product product : controller.getProducts()){
			if(product instanceof Fuel)	fuelSelector.getItems().addAll(product);
		}
		//partSelector.getItems().addAll(controller.getProducts());
		customerSelector.getItems().addAll(controller.getCustomers());
		maintenanceSessionSelector.getItems().addAll(
				controller.getMaintenanceSessions());
		typeSelector.getItems().addAll("Benzine","Onderdeel");
		// reservationSelector.getItems().addAll(controller.getReservations());
		// refuelSelector.getItems().addAll(controller.getRefuels());

		if (stijl == ATDProgram.notificationStyle.PRODUCTS) {
			this.setTitle("Product toevoegen aan klus.");
			notification = new VBox(10, new HBox(new VBox(10, melding,
					partSelector, new HBox(10, annuleren = new Button(
							"Annuleren"), ok = new Button("Toevoegen")))));
			annuleren.setPrefWidth(100);
			annuleren.setOnAction(e -> {
				keuze = "cancel";
				this.hide();
			});
			ok.setPrefWidth(100);
			ok.setOnAction(e -> {
				keuze = "confirm";
				this.hide();
			});
		}
		if (stijl == ATDProgram.notificationStyle.TYPE) {
			this.setTitle("ProductType selecteren");
			notification = new VBox(10, new HBox(new VBox(10, melding,
					typeSelector, new HBox(10, annuleren = new Button(
							"Annuleren"), ok = new Button("Selecteren")))));
			annuleren.setPrefWidth(100);
			annuleren.setOnAction(e -> {
				keuze = "cancel";
				this.hide();
			});
			ok.setPrefWidth(100);
			ok.setOnAction(e -> {
				keuze = "confirm";
				this.hide();
			});
		}
		if (stijl == ATDProgram.notificationStyle.CUSTOMER) {
			this.setTitle("Product toevoegen aan klus.");
			notification = new VBox(10, new HBox(new VBox(10, melding,
					customerSelector, new HBox(10, annuleren = new Button(
							"Annuleren"), ok = new Button("Toevoegen")))));
			annuleren.setPrefWidth(100);
			annuleren.setOnAction(e -> {
				keuze = "cancel";
				this.hide();
			});
			ok.setPrefWidth(100);
			ok.setOnAction(e -> {
				keuze = "confirm";
				this.hide();
			});
		}

		if (stijl == ATDProgram.notificationStyle.TANK) {
			this.setTitle("Product toevoegen aan klus.");
			notification = new VBox(10, new HBox(new VBox(10, melding, amountFuel,
					fuelSelector, new HBox(10, annuleren = new Button(
							"Annuleren"), ok = new Button("Toevoegen")))));
			annuleren.setPrefWidth(100);
			annuleren.setOnAction(e -> {
				keuze = "cancel";
				this.hide();
			});
			ok.setPrefWidth(100);
			ok.setOnAction(e -> {
				keuze = "confirm";
				this.hide();
			});
		}

		if (stijl == ATDProgram.notificationStyle.PARKING) {
			this.setTitle("Product toevoegen aan klus.");
			notification = new VBox(10, new HBox(new VBox(10, melding,
					reservationSelector, new HBox(10, annuleren = new Button(
							"Annuleren"), ok = new Button("Toevoegen")))));
			annuleren.setPrefWidth(100);
			annuleren.setOnAction(e -> {
				keuze = "cancel";
				this.hide();
			});
			ok.setPrefWidth(100);
			ok.setOnAction(e -> {
				keuze = "confirm";
				this.hide();
			});
		}

		if (stijl == ATDProgram.notificationStyle.MAINTENANCE) {
			this.setTitle("Product toevoegen aan klus.");
			notification = new VBox(10, new HBox(new VBox(10, melding,
					maintenanceSessionSelector, new HBox(10,
							annuleren = new Button("Annuleren"),
							ok = new Button("Toevoegen")))));
			annuleren.setPrefWidth(100);
			annuleren.setOnAction(e -> {
				keuze = "cancel";
				this.hide();
			});
			ok.setPrefWidth(100);
			ok.setOnAction(e -> {
				keuze = "confirm";
				this.hide();
			});
		}

		if (stijl == ATDProgram.notificationStyle.ENDSESSION) {
			this.setTitle("Product toevoegen aan klus.");
			notification = new VBox(10, new HBox(new VBox(10, melding,
					hoursMechanic, new HBox(10, annuleren = new Button(
							"Annuleren"), ok = new Button("Toevoegen")))));
			annuleren.setPrefWidth(100);
			annuleren.setOnAction(e -> {
				keuze = "cancel";
				this.hide();
			});
			ok.setPrefWidth(100);
			ok.setOnAction(e -> {
				keuze = "confirm";
				this.hide();
			});
		}

		notification.setAlignment(Pos.CENTER);
		notification.setPadding(new Insets(20));

		Scene scene = new Scene(notification);
		this.setScene(scene);

	}

	public String getKeuze() {
		return keuze;
	}

	public Object getSelected(){
		if(stijl == ATDProgram.notificationStyle.MAINTENANCE){
			return maintenanceSessionSelector.getSelectionModel().getSelectedItem();
		}
		if(stijl == ATDProgram.notificationStyle.TANK){
			return fuelSelector.getSelectionModel().getSelectedItem();
		}
		if(stijl == ATDProgram.notificationStyle.PARKING){
			return reservationSelector.getSelectionModel().getSelectedItem();
		}
		if(stijl == ATDProgram.notificationStyle.TYPE){
			return typeSelector.getSelectionModel().getSelectedItem();
		}
		return partSelector.getSelectionModel().getSelectedItem();
	}

		public int getHours() {
			return Integer.parseInt(hoursMechanic.getText());
		}
		public int getFuelAmount(){
			return Integer.parseInt(amountFuel.getText());
		}
	}
