package notifications;

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
import main.ATDProgram;
import main.Customer;
import main.Fuel;
import main.Invoice;
import main.Invoice.PayMethod;
import main.MaintenanceSession;
import main.Part;
import main.Product;
import main.ProductSupplier;
import main.Reservation;

public class Notification extends Stage {
	public enum notificationStyle {CONFIRM, NOTIFY, ENDSESSION, PRODUCTS, CUSTOMER, TANK, PARKING, MAINTENANCE, TYPE, PAY, SUPPLIER}
	private ComboBox<Product> partSelector = new ComboBox<Product>();
	private ComboBox<Product> fuelSelector = new ComboBox<Product>();
	private ComboBox<Customer> customerSelector = new ComboBox<Customer>();
	private ComboBox<MaintenanceSession> maintenanceSessionSelector = new ComboBox<MaintenanceSession>();
	private ComboBox<Reservation> reservationSelector = new ComboBox<Reservation>();
	private ComboBox<String> typeSelector = new ComboBox<String>();
	private ComboBox<PayMethod> paymentSelector = new ComboBox<PayMethod>();
	private TextField 
	input 	= new TextField(),
	name 	= new TextField("Naam"),
	address = new TextField("Adres"),
	postal 	= new TextField("Postcode"),
	place 	= new TextField("Plaats");
	private Button annuleren = new Button("annuleren"), ok = new Button("Opslaan");
	private Label melding = new Label();
	private String keuze;
	private VBox notification = new VBox(10,melding);
	private HBox buttonBox = new HBox(10,annuleren,ok);
	private notificationStyle stijl;

	public Notification(ATDProgram controller,String message, notificationStyle nwstijl) {
		super(StageStyle.UNDECORATED);
		initOwner(controller.getStage());
		initModality(Modality.WINDOW_MODAL);
		this.stijl = nwstijl;
		this.setResizable(false);
		ok.setPrefWidth(100);
		ok.setOnAction(e -> {
			if(stijl ==notificationStyle.CONFIRM || stijl == notificationStyle.NOTIFY){
				keuze = "confirm";
				this.hide();
			}
			else if(stijl == notificationStyle.TANK || stijl == notificationStyle.ENDSESSION){
				if(getSelected() != null && getInput() != 0){
					keuze = "confirm";
					this.hide();
				}
			}
			else if(getSelected() != null){
				keuze = "confirm";
				this.hide();
			}
		});
		annuleren.setPrefWidth(100);
		annuleren.setOnAction(e -> {
			keuze = "cancel";
			this.hide();
		});
		switch(stijl){
			case PRODUCTS: {
				setTitle("Product toevoegen");
				melding.setText("Selecteer een product:");
				ok.setText("Toevoegen");
				for (Product product : controller.getProducts())
					if(product instanceof Part)	partSelector.getItems().addAll(product);
				notification.getChildren().addAll(partSelector,buttonBox);
				break;
			}
			case TYPE: {
				setTitle("ProductType opslaan");
				melding.setText("Selecteer het type product:");
				typeSelector.getItems().addAll("Benzine","Onderdeel");
				notification.getChildren().addAll(typeSelector,buttonBox);
				break;
			}
			case CUSTOMER: {
				setTitle("Klant toevoegen");
				melding.setText("Selecteer een klant:");
				ok.setText("Toevoegen");
				customerSelector.getItems().addAll(controller.getCustomers());
				notification.getChildren().addAll(customerSelector,buttonBox);
				break;
			}
			case TANK: {
				setTitle("Tanksessie aanmaken");
				melding.setText("Vul de gegevens in:");
				ok.setText("Toevoegen");
				for (Product product : controller.getProducts())
					if(product instanceof Fuel)	fuelSelector.getItems().addAll(product);
				notification.getChildren().addAll(input,fuelSelector,buttonBox);
				break;
			}
	
			case PARKING: {
				setTitle("Reservering toevoegen");
				melding.setText("Selecteer een reservering:");
				ok.setText("Toevoegen");
				reservationSelector.getItems().addAll(controller.getReservations());
				notification.getChildren().addAll(reservationSelector,buttonBox);
				break;
			}
	
			case MAINTENANCE: {
				setTitle("Klus toevoegen");
				melding.setText("Selecteer een klus:");
				ok.setText("Toevoegen");				
				for (MaintenanceSession session : controller.getMaintenanceSessions())
					if(session.isFinished()) maintenanceSessionSelector.getItems().add(session);
				notification.getChildren().addAll(maintenanceSessionSelector,buttonBox);
				break;
			}
			case ENDSESSION : {
				setTitle("Klus afsluiten");
				melding.setText("Vul aantal gewerkte uren in:");
				ok.setText("Afsluiten");
				notification.getChildren().addAll(input,buttonBox);
				break;
			}
			case PAY : {
				setTitle("Factuur afhandelen");
				melding.setText("Kies uw betaalmethode:");
				ok.setText("Betalen");
				paymentSelector.getItems().addAll(Invoice.PayMethod.values());
				notification.getChildren().addAll(paymentSelector, buttonBox);				
				break;
			}
			case CONFIRM: {
				this.setTitle("Bevestigen");
				melding.setText(message);
				notification.getChildren().addAll(buttonBox);
				ok.setText("Ok");
				break;
				}
			case NOTIFY: {
				this.setTitle("Melding");
				melding.setText(message);
				ok.setText("ok");
				buttonBox.getChildren().remove(annuleren);
				notification.getChildren().addAll(buttonBox);
				break;
			}
			case SUPPLIER: {
				this.setTitle("Leverancier aanmaken");
				melding.setText("Vul de gegevens in");
				notification.getChildren().addAll(name,address,postal,place,buttonBox);
				break;
			}
			default: ;
		}
		notification.setAlignment(Pos.CENTER);
		buttonBox.setAlignment(Pos.CENTER);
		notification.setStyle("-fx-background-color: #e6e6e6; -fx-border-color: darkgray;-fx-border: solid;");
		notification.setPadding(new Insets(20));
		Scene scene = new Scene(notification);
		this.setScene(scene);
	}
	public String getKeuze() {
		return keuze;
	}
	public Object getSelected(){
		switch(stijl) {
		case MAINTENANCE:	return maintenanceSessionSelector.getSelectionModel().getSelectedItem();
		case TANK:			return fuelSelector.getSelectionModel().getSelectedItem();
		case PARKING:		return reservationSelector.getSelectionModel().getSelectedItem();
		case TYPE:			return typeSelector.getSelectionModel().getSelectedItem();
		case CUSTOMER:		return customerSelector.getSelectionModel().getSelectedItem();
		case PRODUCTS:		return partSelector.getSelectionModel().getSelectedItem();
		case PAY:			return paymentSelector.getSelectionModel().getSelectedItem();
		case SUPPLIER:		return new ProductSupplier(name.getText(), address.getText(), postal.getText(), place.getText());
		default : return null;
		}
	}
	public int getInput() {
		if(!input.getText().isEmpty())
			return Integer.parseInt(input.getText());
		else return 0;
	}
}
