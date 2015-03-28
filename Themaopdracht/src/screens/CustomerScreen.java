package screens;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import main.ATDProgram;
import main.Customer;

public class CustomerScreen extends HBox {
	private ATDProgram controller;
	private Customer selectedCustomer;
	private double
			spacingBoxes = 10,
			widthLabels = 120;
	private boolean isChanging = false;
	private Button 
			newButton = new Button("Nieuw"), 
			changeButton = new Button("Aanpassen"), 
			removeButton = new Button("Verwijderen"), 
			cancelButton = new Button("Annuleren"),
			saveButton = new Button("Opslaan");
	private DatePicker 
			dateOfBirthInput = new DatePicker();
	private CheckBox 
			blackListInput = new CheckBox();
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
			searchInput = new TextField(), 
			nameInput = new TextField(), 
			addressInput = new TextField(),
			postalInput = new TextField(), 
			placeInput = new TextField(), 
			emailInput = new TextField(), 
			phoneInput = new TextField(),
			bankInput = new TextField();
	private ListView<Customer> 
			listView = new ListView<Customer>();
	private VBox
			leftBox = new VBox(20),
			rightBox = new VBox(20);
	private HBox 
			detailsBox = new HBox(spacingBoxes), 
			mainButtonBox = new HBox(spacingBoxes), 
			searchFieldBox = new HBox(spacingBoxes), 
			mainBox = new HBox(spacingBoxes);
	public CustomerScreen(ATDProgram controller) {
		this.controller = controller;
		//CustomerDetails
		detailsBox.getChildren().addAll(
				new VBox(20,
						new HBox(20,name,		nameContent,		nameInput),
						new HBox(20,address,	addressContent,		addressInput),
						new HBox(20,postal,		postalContent,		postalInput),
						new HBox(20,place,		placeContent,		placeInput),
						new HBox(20,dateOfBirth,dateOfBirthContent,	dateOfBirthInput),
						new HBox(20,email,		emailContent,		emailInput),
						new HBox(20,phone,		phoneContent,		phoneInput),
						new HBox(20,bank,		bankContent,		bankInput),
						new HBox(20,blackList,	blackListContent,	blackListInput),	
						new HBox(20,cancelButton,saveButton)
						));
		detailsBox.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border: solid;");
		detailsBox.setPrefSize(450, 520-15);
		detailsBox.setPadding(new Insets(20));
		setVisibility(true, false, false);
		for (Node node1 : ((VBox)detailsBox.getChildren().get(0)).getChildren()) {
			if(((HBox)node1).getChildren().size()>2)((Label)((HBox)node1).getChildren().get(0)).setMinWidth(widthLabels);
		}
		cancelButton.setPrefSize(150, 50);
		cancelButton.setOnAction(e -> {
				setVisibility(true, false, false);
		});
		saveButton.setPrefSize(150, 50);
		saveButton.setOnAction(e -> {
			save();
		});
		listView.setPrefSize(450, 520);
		listView.getItems().addAll(controller.getCustomers());
		listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue!=null)selectedCustomer = newValue;
			else selectedCustomer = oldValue;
			selectedListEntry();
		});
		//SearchField
		searchFieldBox = new HBox(searchInput = new TextField("Zoek..."));
		searchInput.setPrefSize(470, 50);
		searchInput.setOnMouseClicked(e -> {
			if (searchInput.getText().equals("Zoek...")) {
				searchInput.clear();
			} else
				searchInput.selectAll();
		});
		searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
				search(oldValue, newValue);
		});
		
		
		//Buttons Add, Change & Remove
		mainButtonBox.getChildren().addAll(
				newButton,
				changeButton,
				removeButton
				);
		newButton.setPrefSize(150, 50);
		newButton.setOnAction(e -> {
			setVisibility(true, false, false);
			setVisibility(false, true, true);
			isChanging = false;
		});
		changeButton.setPrefSize(150, 50);
		changeButton.setOnAction(e -> {
			isChanging = true;
			change();
		});
		removeButton.setPrefSize(150, 50);
		removeButton.setOnAction(e->{
			listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
		});
		//Make & merge left & right
		leftBox.getChildren().addAll (listView,searchFieldBox,mainButtonBox);
		rightBox.getChildren().addAll(detailsBox);
		mainBox.getChildren().addAll (leftBox,rightBox);
		mainBox.setSpacing(20);
		mainBox.setPadding(new Insets(20));
		this.getChildren().add(mainBox);
	}
	private void change(){
		nameInput.setText(selectedCustomer.getName());
		placeInput.setText(selectedCustomer.getPlace());
		bankInput.setText(selectedCustomer.getBankAccount());
		dateOfBirthInput.setValue(selectedCustomer.getDateOfBirth());
		emailInput.setText(selectedCustomer.getEmail());
		postalInput.setText(selectedCustomer.getPostal());
		phoneInput.setText(selectedCustomer.getTel());
		addressInput.setText(selectedCustomer.getAdress());
		blackListInput.setSelected(selectedCustomer.isOnBlackList());
		listView.getItems().clear();
		listView.getItems().addAll(controller.getCustomers());
		setVisibility(true, true, true);	
		isChanging = true;
	}
	private void save(){
		if(isChanging){
			selectedCustomer.setName(nameInput.getText());
			selectedCustomer.setPlace(placeInput.getText());
			selectedCustomer.setBankAccount(bankInput.getText());
			selectedCustomer.setDateOfBirth(dateOfBirthInput.getValue());
			selectedCustomer.setEmail(emailInput.getText());
			selectedCustomer.setPostal(postalInput.getText());
			selectedCustomer.setTel(phoneInput.getText());
			selectedCustomer.setAdress(addressInput.getText());
			selectedCustomer.setOnBlackList(blackListInput.isSelected());
			setVisibility(true, false, false);
		}
		else{
			Customer newCustomer = new Customer(
					nameInput.getText(),
					placeInput.getText(),
					bankInput.getText(),
					dateOfBirthInput.getValue(),
					emailInput.getText(),
					postalInput.getText(),
					phoneInput.getText(),
					addressInput.getText(),
					blackListInput.isSelected()
					);
			controller.addorRemoveCustomer(newCustomer, false);
			listView.getItems().add(newCustomer);
			setVisibility(true, false, false);
		}
	}
	private void selectedListEntry(){
		if(selectedCustomer.getName()!=null)nameContent.setText(selectedCustomer.getName());
		if(selectedCustomer.getPlace()!=null)placeContent.setText(selectedCustomer.getPlace());
		if(selectedCustomer.getBankAccount()!=null)bankContent.setText(selectedCustomer.getBankAccount());
		if(selectedCustomer.getDateOfBirth() != null) dateOfBirthContent.setText(selectedCustomer.getDateOfBirth().toString());
		if(selectedCustomer.getEmail()!= null)emailContent.setText(selectedCustomer.getEmail());
		if(selectedCustomer.getPostal()!=null)postalContent.setText(selectedCustomer.getPostal());
		if(selectedCustomer.getTel()!=null)phoneContent.setText(selectedCustomer.getTel());
		if(selectedCustomer.getAdress()!=null)addressContent.setText(selectedCustomer.getAdress());
		if(selectedCustomer.isOnBlackList())blackListContent.setText("ja");
		else blackListContent.setText("nee");
	}
	private void setVisibility(boolean setDetailsVisible, boolean setTextFieldsVisible, boolean setButtonsVisible) {
		cancelButton.setVisible(setButtonsVisible);
		saveButton.setVisible(setButtonsVisible);	
		for (Node node1 : ((VBox)detailsBox.getChildren().get(0)).getChildren()) {
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
	public void search(String oldVal, String newVal) {
		if (oldVal != null && (newVal.length() < oldVal.length())) {
			listView.getItems().clear();
			listView.getItems().addAll(controller.getCustomers());
		}
		listView.getItems().clear();
		for (Customer entry : controller.getCustomers()) {
			if (entry.getName().contains(newVal)
					|| entry.getPostal().contains(newVal)
					|| entry.getEmail().contains(newVal)) {
				listView.getItems().add(entry);
			}
		}
	}

}

	