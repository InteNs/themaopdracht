package Screens;
import javafx.application.Application;
import javafx.geometry.Insets;
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
	private String 
			b;
	private double
			spacingBoxes = 10,
			widthLabels = 120;
	private Button 
			newCustomerButton = new Button("Nieuw"), 
			changeCustomerButton = new Button("Aanpassen"), 
			removeCustomerButton = new Button("Verwijderen"), 
			cancelCustomer = new Button("Annuleren"),
			saveCustomer = new Button("Opslaan");
	private DatePicker 
			dateOfBirthDatePicker = new DatePicker();
	private CheckBox 
			blackListCheckBox = new CheckBox();
	private Label 
			name = new Label("Naam: "),
			nameContent = new Label("-"), 
			address = new Label("Adres: "), 
			addressContent = new Label("-"),
			postal = new Label("Postcode: "),
			postalContent = new Label("-"),
			place = new Label("Plaats: "), 
			placeContent = new Label("-"),
			dateOfBirth = new Label("Geboortedatum: "), 
			dateOfBirthContent = new Label("-"),
			email = new Label("Email: "), 
			emailContent = new Label("jorritmeulenbeld@icloud.com"), 
			phone = new Label("Telefoonnummer: "), 
			phoneContent = new Label("-"), 
			bank = new Label("Rekeningnummer: "), 
			bankContent = new Label("-"), 
			blackList = new Label("Blacklist: "), 
			blackListContent = new Label("-");
	private TextField 
			searchTextField = new TextField(), 
			nameTextField = new TextField(), 
			addressTextField = new TextField(),
			postalTextField = new TextField(), 
			placeTextField = new TextField(), 
			emailTextField = new TextField(), 
			phoneTextField = new TextField(),
			bankTextField = new TextField();
	private ListView 
			customers = new ListView<>();
	private VBox
			leftBox = new VBox(20),
			rightBox = new VBox(20);
	private HBox 
			customerDetails = new HBox(spacingBoxes), 
			mainButtonBox = new HBox(spacingBoxes), 
			searchFieldBox = new HBox(spacingBoxes), 
			mainBox = new HBox(spacingBoxes);

	@Override
	public void start(Stage stage) throws Exception {
		
		//CustomerDetails
		customerDetails.getChildren().addAll(
				new VBox(20,
						new HBox(20,
								name,
								nameContent,
								nameTextField),
						new HBox(20,
								address,
								addressContent,
								addressTextField),
						new HBox(20,
								postal,
								postalContent,
								postalTextField),
						new HBox(20,
								place,
								placeContent,
								placeTextField),
						new HBox(20,
								dateOfBirth,
								dateOfBirthContent,
								dateOfBirthDatePicker),
						new HBox(20,
								email,
								emailContent,
								emailTextField),
						new HBox(20,
								phone,
								phoneContent,
								phoneTextField),
						new HBox(20,
								bank,
								bankContent,
								bankTextField),
						new HBox(20,
								blackList,
								blackListContent,
								blackListCheckBox),	
						new HBox(20,
								cancelCustomer,
								saveCustomer)
						)
				);
		customerDetails.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border: solid;");
		customerDetails.setPrefSize(450, 520-15);
		customerDetails.setPadding(new Insets(20));
		visability(true, false, false);
		
		name.setMinWidth(widthLabels);
		address.setMinWidth(widthLabels);
		postal.setMinWidth(widthLabels);
		place.setMinWidth(widthLabels);
		dateOfBirth.setMinWidth(widthLabels);
		email.setMinWidth(widthLabels);
		phone.setMinWidth(widthLabels);
		bank.setMinWidth(widthLabels);
		blackList.setMinWidth(widthLabels);
		
		cancelCustomer.setPrefSize(150, 50);
		cancelCustomer.setOnAction(e -> {
				visability(true, false, false);
		});
		
		saveCustomer.setPrefSize(150, 50);
		saveCustomer.setOnAction(e -> {
			
		});
		
		customers.setPrefSize(450, 520);
		
		//SearchField
		searchFieldBox = new HBox(searchTextField = new TextField("Zoek..."));
		searchTextField.setPrefSize(470, 50);
		
		//Buttons Add, Change & Remove
		mainButtonBox.getChildren().addAll(
				newCustomerButton,
				changeCustomerButton,
				removeCustomerButton
				);
		newCustomerButton.setPrefSize(150, 50);
		newCustomerButton.setOnAction(e -> {
			visability(false, true, true);	
		});
		changeCustomerButton.setPrefSize(150, 50);
		changeCustomerButton.setOnAction(e -> {
			visability(true, true, true);	
		});
		removeCustomerButton.setPrefSize(150, 50);
		
		//Make & merge left & right
		leftBox.getChildren().addAll(
				customers,
				searchFieldBox,
				mainButtonBox);
		rightBox.getChildren().add(
				customerDetails);
		mainBox.getChildren().addAll(
				leftBox,
				rightBox);
		mainBox.setSpacing(20);
		mainBox.setPadding(new Insets(20));
		
		Scene mainScene = new Scene(mainBox, 1024, 655);
		stage.setScene(mainScene);
		stage.setTitle("Customer");
		stage.setResizable(false);
		stage.show();

	}

	public String getString() {
		return b;
	}
	
	public void visability(boolean setDetailsVisible, boolean setTextFieldsVisible, boolean setButtonsVisible) {
		nameTextField.setVisible(setTextFieldsVisible);
		addressTextField.setVisible(setTextFieldsVisible);
		postalTextField.setVisible(setTextFieldsVisible);
		placeTextField.setVisible(setTextFieldsVisible);
		dateOfBirthDatePicker.setVisible(setTextFieldsVisible);
		emailTextField.setVisible(setTextFieldsVisible);
		phoneTextField.setVisible(setTextFieldsVisible);
		bankTextField.setVisible(setTextFieldsVisible);
		blackListCheckBox.setVisible(setTextFieldsVisible);
		
		nameContent.setVisible(setDetailsVisible);
		addressContent.setVisible(setDetailsVisible);
		postalContent.setVisible(setDetailsVisible);
		placeContent.setVisible(setDetailsVisible);
		dateOfBirthContent.setVisible(setDetailsVisible);
		emailContent.setVisible(setDetailsVisible);
		phoneContent.setVisible(setDetailsVisible);
		bankContent.setVisible(setDetailsVisible);
		blackListContent.setVisible(setDetailsVisible);
		
		cancelCustomer.setVisible(setButtonsVisible);
		saveCustomer.setVisible(setButtonsVisible);	
		
		if (!setTextFieldsVisible) {
			nameTextField.setPrefWidth(0);
			addressTextField.setPrefWidth(0);
			postalTextField.setPrefWidth(0);
			placeTextField.setPrefWidth(0);
			blackListCheckBox.setPrefWidth(0);
			emailTextField.setPrefWidth(0);
			phoneTextField.setPrefWidth(0);
			bankTextField.setPrefWidth(0);
			blackListCheckBox.setPrefSize(0, 0);
			
			email.setMinWidth(widthLabels);
			
			nameContent.setPrefWidth(widthLabels*2);
			addressContent.setPrefWidth(widthLabels*2);
			postalContent.setPrefWidth(widthLabels*2);
			placeContent.setPrefWidth(widthLabels*2);
			dateOfBirthContent.setPrefWidth(widthLabels*2);
			emailContent.setPrefWidth(widthLabels*2);
			phoneContent.setPrefWidth(widthLabels*2);
			bankContent.setPrefWidth(widthLabels*2);
			blackListContent.setPrefWidth(widthLabels*2);
		} else if (!setDetailsVisible) {
			nameTextField.setPrefWidth(widthLabels*2);
			addressTextField.setPrefWidth(widthLabels*2);
			postalTextField.setPrefWidth(widthLabels*2);
			placeTextField.setPrefWidth(widthLabels*2);
			dateOfBirthDatePicker.setPrefWidth(widthLabels*2);
			emailTextField.setPrefWidth(widthLabels*2);
			phoneTextField.setPrefWidth(widthLabels*2);
			bankTextField.setPrefWidth(widthLabels*2);
			blackListCheckBox.setPrefSize(25, 25);
			
			email.setMinWidth(widthLabels-5);
			
			nameContent.setPrefWidth(0);
			addressContent.setPrefWidth(0);
			postalContent.setPrefWidth(0);
			placeContent.setPrefWidth(0);
			dateOfBirthContent.setPrefWidth(0);
			emailContent.setPrefWidth(0);
			phoneContent.setPrefWidth(0);
			bankContent.setPrefWidth(0);
			blackListContent.setPrefWidth(0);			
		} else if (setDetailsVisible || setTextFieldsVisible) {
			nameTextField.setPrefWidth(widthLabels);
			addressTextField.setPrefWidth(widthLabels);
			postalTextField.setPrefWidth(widthLabels);
			placeTextField.setPrefWidth(widthLabels);
			dateOfBirthDatePicker.setPrefWidth(widthLabels);
			emailTextField.setPrefWidth(widthLabels);
			phoneTextField.setPrefWidth(widthLabels);
			bankTextField.setPrefWidth(widthLabels);
			blackListCheckBox.setPrefSize(25, 25);
			
			email.setMinWidth(widthLabels);
			
			nameContent.setPrefWidth(widthLabels);
			addressContent.setPrefWidth(widthLabels);
			postalContent.setPrefWidth(widthLabels);
			placeContent.setPrefWidth(widthLabels);
			dateOfBirthContent.setPrefWidth(widthLabels);
			emailContent.setPrefWidth(widthLabels);
			phoneContent.setPrefWidth(widthLabels);
			bankContent.setPrefWidth(widthLabels);
			blackListContent.setPrefWidth(widthLabels);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
	