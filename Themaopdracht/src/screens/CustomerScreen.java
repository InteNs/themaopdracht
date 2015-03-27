package screens;
import java.time.LocalDate;

import main.Customer;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomerScreen extends HBox {
	private double
			spacingBoxes = 10,
			widthLabels = 120;
	private boolean isChanging = false;
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
			emailContent = new Label("-"), 
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
	private ListView<Customer> 
			customers = new ListView<Customer>();
	private VBox
			leftBox = new VBox(20),
			rightBox = new VBox(20);
	private HBox 
			customerDetails = new HBox(spacingBoxes), 
			mainButtonBox = new HBox(spacingBoxes), 
			searchFieldBox = new HBox(spacingBoxes), 
			mainBox = new HBox(spacingBoxes);
	public CustomerScreen() {
		
		//CustomerDetails
		customerDetails.getChildren().addAll(
				new VBox(20,
						new HBox(20,name,		nameContent,		nameTextField),
						new HBox(20,address,	addressContent,		addressTextField),
						new HBox(20,postal,		postalContent,		postalTextField),
						new HBox(20,place,		placeContent,		placeTextField),
						new HBox(20,dateOfBirth,dateOfBirthContent,	dateOfBirthDatePicker),
						new HBox(20,email,		emailContent,		emailTextField),
						new HBox(20,phone,		phoneContent,		phoneTextField),
						new HBox(20,bank,		bankContent,		bankTextField),
						new HBox(20,blackList,	blackListContent,	blackListCheckBox),	
						new HBox(20,cancelCustomer,saveCustomer)
						));
		customerDetails.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border: solid;");
		customerDetails.setPrefSize(450, 520-15);
		customerDetails.setPadding(new Insets(20));
		setVisibility(true, false, false);
		for (Node node1 : ((VBox)customerDetails.getChildren().get(0)).getChildren()) {
			if(((HBox)node1).getChildren().size()>2)((Label)((HBox)node1).getChildren().get(0)).setMinWidth(widthLabels);
		}
		
		cancelCustomer.setPrefSize(150, 50);
		cancelCustomer.setOnAction(e -> {
				setVisibility(true, false, false);
		});
		
		saveCustomer.setPrefSize(150, 50);
		saveCustomer.setOnAction(e -> {
			if(isChanging){
				Customer c  = customers.getSelectionModel().getSelectedItem();
				c.setName(nameTextField.getText());
				c.setPlace(placeTextField.getText());
				c.setBankAccount(bankTextField.getText());
				c.setDateOfBirth(dateOfBirthDatePicker.getValue());
				c.setEmail(emailTextField.getText());
				c.setPostal(postalTextField.getText());
				c.setTel(phoneTextField.getText());
				c.setAdress(addressTextField.getText());
				c.setOnBlackList(blackListCheckBox.isSelected());
				setVisibility(true, false, false);
			}
			else{
				customers.getItems().add(new Customer(
						nameTextField.getText(),
						placeTextField.getText(),
						bankTextField.getText(),
						dateOfBirthDatePicker.getValue(),
						emailTextField.getText(),
						postalTextField.getText(),
						phoneTextField.getText(),
						addressTextField.getText(),
						blackListCheckBox.isSelected()
						));
				setVisibility(true, false, false);
			}
		});
		
		customers.setPrefSize(450, 520);
		customers.getSelectionModel().selectedItemProperty().addListener(e->{
			Customer c = customers.getSelectionModel().getSelectedItem();
			nameContent.setText(c.getName());
			placeContent.setText(c.getPlace());
			bankContent.setText(c.getBankAccount());
			dateOfBirthContent.setText(c.getDateOfBirth().toString());
			emailContent.setText(c.getEmail());
			postalContent.setText(c.getPostal());
			phoneContent.setText(c.getTel());
			addressContent.setText(c.getAdress());
			if(c.isOnBlackList())blackListContent.setText("ja");
			else blackListContent.setText("nee");
		});
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
			setVisibility(false, true, true);
			isChanging = false;
		});
		changeCustomerButton.setPrefSize(150, 50);
		changeCustomerButton.setOnAction(e -> {
			Customer c = customers.getSelectionModel().getSelectedItem();
			nameTextField.setText(c.getName());
			placeTextField.setText(c.getPlace());
			bankTextField.setText(c.getBankAccount());
			dateOfBirthDatePicker.setValue(c.getDateOfBirth());
			emailTextField.setText(c.getEmail());
			postalTextField.setText(c.getPostal());
			phoneTextField.setText(c.getTel());
			addressTextField.setText(c.getAdress());
			blackListCheckBox.setSelected(c.isOnBlackList());
			setVisibility(true, true, true);	
			isChanging = true;
		});
		removeCustomerButton.setPrefSize(150, 50);
		removeCustomerButton.setOnAction(e->{
			customers.getItems().remove(customers.getSelectionModel().getSelectedItem());
		});
		//Make & merge left & right
		leftBox.getChildren().addAll (customers,searchFieldBox,mainButtonBox);
		rightBox.getChildren().addAll(customerDetails);
		mainBox.getChildren().addAll (leftBox,rightBox);
		mainBox.setSpacing(20);
		mainBox.setPadding(new Insets(20));
		this.getChildren().add(mainBox);
	}
	private void setVisibility(boolean setDetailsVisible, boolean setTextFieldsVisible, boolean setButtonsVisible) {
		cancelCustomer.setVisible(setButtonsVisible);
		saveCustomer.setVisible(setButtonsVisible);	
		for (Node node1 : ((VBox)customerDetails.getChildren().get(0)).getChildren()) {
			HBox box = (HBox) node1;
			if(box.getChildren().size()>2){
				Node input = box.getChildren().get(2);
				Label content = ((Label)box.getChildren().get(1));
				input.setVisible(setTextFieldsVisible);
				content.setVisible(setDetailsVisible);
				if(!setTextFieldsVisible){
					if(input instanceof TextField){
						((TextField)input).setPrefWidth(0);
						((TextField)input).clear();
					}
					if(input instanceof DatePicker){
						((DatePicker)input).setPrefWidth(0);
						((DatePicker)input).setValue(null);
					}
					if(input instanceof CheckBox){
						((CheckBox)input).setPrefSize(0,0);
						((CheckBox)input).setSelected(false);
					}
					content.setPrefWidth(widthLabels*2);
					email.setMinWidth(widthLabels);
				}
				else if (!setDetailsVisible) {
					if(input instanceof TextField)	((TextField)input).setPrefWidth(widthLabels*2);
					if(input instanceof DatePicker)	((DatePicker)input).setPrefWidth(widthLabels*2);
					if(input instanceof CheckBox)	((CheckBox)input).setPrefSize(25,25);
					content.setPrefWidth(0);
					email.setMinWidth(widthLabels-5);
				}			
				else if (setDetailsVisible || setTextFieldsVisible) {
					if(input instanceof TextField)	((TextField)input).setPrefWidth(widthLabels);
					if(input instanceof DatePicker)	((DatePicker)input).setPrefWidth(widthLabels);
					if(input instanceof CheckBox)	((CheckBox)input).setPrefSize(25,25);
					content.setPrefWidth(widthLabels);
					email.setMinWidth(widthLabels);
				}
			}
		}	
	}
}
	