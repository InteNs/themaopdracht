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
import main.ATDProgram.notificationStyle;
import main.Customer;
import main.Fuel;
import main.MaintenanceSession;
import main.Part;
import main.Product;
import main.Reservation;

public class GetInfoNotification extends Stage {
	private ComboBox<Product> partSelector = new ComboBox<Product>();
	private ComboBox<Product> fuelSelector = new ComboBox<Product>();
	private ComboBox<Customer> customerSelector = new ComboBox<Customer>();
	private ComboBox<MaintenanceSession> maintenanceSessionSelector = new ComboBox<MaintenanceSession>();
	private ComboBox<Reservation> reservationSelector = new ComboBox<Reservation>();
	private ComboBox<String> typeSelector = new ComboBox<String>();
	private TextField input = new TextField();
	private Button annuleren = new Button("annuleren"), ok = new Button("Opslaan");
	private Label melding = new Label();
	private String keuze;
	private VBox notification = new VBox(10,melding);
	private HBox buttonBox = new HBox(10,ok,annuleren);
	private notificationStyle stijl;

	public GetInfoNotification(ATDProgram controller, ATDProgram.notificationStyle nwstijl) {
		super(StageStyle.UTILITY);
		initOwner(controller.getStage());
		initModality(Modality.WINDOW_MODAL);
		this.stijl = nwstijl;
		this.setResizable(false);
		ok.setPrefWidth(100);
		ok.setOnAction(e -> {
			keuze = "confirm";
			this.hide();
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
				for (Product product : controller.getProducts()){
					if(product instanceof Part)	partSelector.getItems().addAll(product);
				}
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
			default: ;
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
		switch(stijl) {
		case MAINTENANCE:	return maintenanceSessionSelector.getSelectionModel().getSelectedItem();
		case TANK:			return fuelSelector.getSelectionModel().getSelectedItem();
		case PARKING:		return reservationSelector.getSelectionModel().getSelectedItem();
		case TYPE:			return typeSelector.getSelectionModel().getSelectedItem();
		case CUSTOMER:		return customerSelector.getSelectionModel().getSelectedItem();
		case PRODUCTS:		return partSelector.getSelectionModel().getSelectedItem();
		default : return null;
		}
	}
	public int getInput() {
		return Integer.parseInt(input.getText());
	}
}
