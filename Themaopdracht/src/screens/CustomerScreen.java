package screens;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.ATDProgram;
import main.Customer;
import notifications.Notification;

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
	private ComboBox<String> 
			filterSelector = new ComboBox<String>();
	private ArrayList<ListItem> content = new ArrayList<ListItem>();
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
	private ListView<ListItem> 
			listView = new ListView<ListItem>();
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
		//geef alle labels een bepaalde grootte
		for (Node node1 : ((VBox)detailsBox.getChildren().get(0)).getChildren()) {
			if(((HBox)node1).getChildren().size()>2)((Label)((HBox)node1).getChildren().get(0)).setMinWidth(widthLabels);
		}
		//cancelbutton
		cancelButton.setPrefSize(150, 50);
		cancelButton.setOnAction(e -> {
				setVisibility(true, false, false);
		});
		//savebutton
		saveButton.setPrefSize(150, 50);
		saveButton.setOnAction(e -> {
			Notification changeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u deze wijzigingen wilt doorvoeren?", ATDProgram.notificationStyle.CONFIRM);
			changeConfirm.showAndWait();
			save();
			Notification changeNotify = new Notification(controller.getStage(), "Wijzigingen zijn doorgevoerd.", ATDProgram.notificationStyle.NOTIFY);
			changeNotify.showAndWait();
		});
		//listview
		listView.setPrefSize(450, 520);
		for (Customer customer : controller.getCustomers()) {
			listView.getItems().add(new ListItem(customer));
		}
		refreshList();
		listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue!=null)selectedCustomer = newValue.getCustomer();
			else selectedCustomer = oldValue.getCustomer();
			selectedListEntry();
		});
		//SearchField
		searchFieldBox = new HBox(10,searchInput = new TextField("Zoek..."),filterSelector);
		searchInput.setPrefSize(310, 50);
		searchInput.setOnMouseClicked(e -> {
			if (searchInput.getText().equals("Zoek...")) {
				searchInput.clear();
			} else
				searchInput.selectAll();
		});
		searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
				search(oldValue, newValue);
		});
		//filter
		filterSelector.setPrefSize(150, 50);
		filterSelector.getItems().addAll("Filter: Geen", "Filter: Service", "Filter: Onderhoud");
		filterSelector.getSelectionModel().selectFirst();
		filterSelector.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			listView.getItems().clear();
			if(newValue.intValue()==0){
				for (Customer customer : controller.getCustomers()) {
					listView.getItems().add(new ListItem(customer));
				}
			}
			if(newValue.intValue()==1){
				for (Customer customer : controller.getRemindList(false)) {
					listView.getItems().add(new ListItem(customer));
				}
			}
			if(newValue.intValue()==2){
				for (Customer customer : controller.getRemindList(true)) {
					listView.getItems().add(new ListItem(customer));
				}
			}
			refreshList();
			if (!searchInput.getText().equals("Zoek..."))search(null, searchInput.getText());
		});
		
		
		//main Buttons
		mainButtonBox.getChildren().addAll(
				newButton,
				changeButton,
				removeButton
				);
		//NewButton
		newButton.setPrefSize(150, 50);
		newButton.setOnAction(e -> {
			setVisibility(true, false, false);
			setVisibility(false, true, true);
			isChanging = false;
		});
		//ChangeButton
		changeButton.setPrefSize(150, 50);
		changeButton.setOnAction(e -> {
			isChanging = true;
			change();
		});
		//RemoveButton
		removeButton.setPrefSize(150, 50);
		removeButton.setOnAction(e->{
			Notification removeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u deze klant wilt verwijderen?", ATDProgram.notificationStyle.CONFIRM);
			removeConfirm.showAndWait();
			if (removeConfirm.getKeuze() == "confirm"){
				listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
				controller.addorRemoveCustomer(selectedCustomer, true);
				Notification removeNotify = new Notification(controller.getStage(), "Klant is verwijderd.", ATDProgram.notificationStyle.NOTIFY);
				removeNotify.showAndWait();
				selectedCustomer = null;
			}
		});
		//Make & merge left & right
		leftBox.getChildren().addAll (listView,searchFieldBox,mainButtonBox);
		rightBox.getChildren().addAll(detailsBox);
		mainBox.getChildren().addAll (leftBox,rightBox);
		mainBox.setSpacing(20);
		mainBox.setPadding(new Insets(20));
		this.getChildren().add(mainBox);
	}
	 
	/**
	 * vult alle gegevens in de TextFields om aan te passen
	 */
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
		setVisibility(true, true, true);	
		isChanging = true;
	}
	/**
	 * slaat het nieuwe of aangepaste product op
	 */
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
			refreshList();
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
			listView.getItems().add(new ListItem(newCustomer));
			setVisibility(true, false, false);
		}
	}
	private void refreshList(){
		content.clear();
		content.addAll(listView.getItems());
		listView.getItems().clear();
		listView.getItems().addAll(content);
		for (ListItem listItem : listView.getItems()) {
			listItem.refresh();
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
			listView.getItems().addAll(content);
		}
		listView.getItems().clear();
		for (ListItem entry : content) {
			if (entry.getCustomer().getName().contains(newVal)
					|| entry.getCustomer().getPostal().contains(newVal)
					|| entry.getCustomer().getEmail().contains(newVal)) {
				listView.getItems().add(entry);
			}
		}
	}
	public class ListItem extends HBox{
		private Label itemNameLabel = new Label(),itemPostalLabel = new Label(),itemEmailLabel = new Label();
		private Customer customer;
		public ListItem(Customer customer){
			this.customer = customer;
			refresh();
			setSpacing(5);
			getChildren().addAll(
					itemNameLabel,
					new Separator(Orientation.VERTICAL),
					itemPostalLabel,
					new Separator(Orientation.VERTICAL),
					itemEmailLabel);
			((Label)getChildren().get(0)).setPrefWidth(120);
			((Label)getChildren().get(2)).setPrefWidth(100);
			((Label)getChildren().get(4)).setPrefWidth(200);
				
			
		}
		public void refresh(){
			itemNameLabel.setText(customer.getName());
			itemPostalLabel.setText(customer.getPostal());
			itemEmailLabel.setText(customer.getEmail());
		}
		public Customer getCustomer(){
			return customer;
		}
	}
}

	