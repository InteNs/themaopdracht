package oldfiles;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerScreen extends Application {
	private Button newCostumerButton, changeCostumerButton,
			removeCustomerButton, cancelNewCustomer, saveNewCustomer;
	private TextField searchTextField, nameTextField, addressTextField,
			postalTextField, placeTextField, emailTextField, phoneTextField,
			bankTextField;
	private DatePicker dateOfBirthDatePicker;
	private CheckBox blackListCheckBox;
	private Label 
			name = new Label("Naam: "),
			nameContent, 
			address = new Label("Adres: "), 
			addressContent, 
			postal = new Label("Postcode: "),
			postalContent, 
			place = new Label("Plaats: "), 
			placeContent,
			dateOfBirth = new Label("Geboortedatum: "), 
			dateOfBirthContent,
			email = new Label("Email: "), 
			emailContent, 
			phone = new Label("Telefoonnummer: "), 
			phoneContent, 
			bank = new Label("Rekeningnummer: "), 
			bankContent, 
			blackList = new Label("Blacklist: "), 
			blackListContent;
	private ListView Customers;

	private VBox leftBox, rightBox;
	private HBox customerDetails, mainButtonBox, searchFieldBox, mainBox;
	private String b;

	public CustomerScreen(Stage mainStage) {
		
		//CustomerDetails
		customerDetails = new HBox(
				new VBox(
						new HBox(
								name,
								nameContent),
						new HBox(
								address,
								addressContent),
						new HBox(
								postal,
								postalContent),
						new HBox(
								place,
								placeContent),
						new HBox(
								dateOfBirth,
								dateOfBirthContent),
						new HBox(
								email,
								emailContent),
						new HBox(
								phone,
								phoneContent),
						new HBox(
								bank,
								bankContent),
						new HBox(
								blackList,
								blackListContent)						
						)
				);
		customerDetails.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border: solid;");
		
		//SearchField
		searchFieldBox = new HBox(searchTextField = new TextField("Zoek..."));
		
		//Buttons Add, Change & Remove
		mainButtonBox = new HBox(
				newCostumerButton = new Button("Nieuwe Klant"),
				changeCostumerButton = new Button("Aanpassen"),
				removeCustomerButton = new Button("Verwijderen")
				);
		
		//Make & merge left & right
		
		mainBox = new HBox(
				leftBox = new VBox(
						Customers,
						searchFieldBox,
						mainButtonBox),
				rightBox = new VBox(
						customerDetails));
		
		Scene mainScene = new Scene(mainBox, 1024, 655);
		mainStage.setScene(mainScene);
		mainStage.setTitle("AutoTotaalDienst");
		mainStage.setResizable(false);
		mainStage.show();

	}

	public String getString() {
		return b;
	}

	@Override
	public void start(Stage arg0) throws Exception {
		launch();
		
	}
}
