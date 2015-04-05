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
import main.Fuel;
import main.Part;
import notifications.GetInfoNotification;
import notifications.Notification;

public class CustomerScreen extends HBox {
	private ATDProgram controller;
	private Customer selectedCustomer;
	private ComboBox<String> filterSelector = new ComboBox<String>();
	private ArrayList<ListRegel> content = new ArrayList<ListRegel>();
	private ListView<ListRegel> itemList = new ListView<ListRegel>();
	private CheckBox blackListContent = new CheckBox();
	private DatePicker datecontent = new DatePicker();
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
	private Label 
			nameLabel = new Label("Naam:"),
			addressLabel = new Label("Adres:"),
			postalLabel = new Label("Postcode:"),
			placeLabel = new Label("Plaats:"),
			dateLabel = new Label("Geboortedatum:"),
			emailLabel = new Label("Email:"),
			phoneLabel = new Label("Telefoonnummer:"),
			bankLabel = new Label("Banknummer:"),
			blackListLabel = new Label("Blacklist:");
	private TextField 
			searchContent = new TextField(),
			nameContent = new TextField(),
			addressContent = new TextField(),
			postalContent = new TextField(),
			placeContent = new TextField(),
			emailContent = new TextField(),
			phoneContent = new TextField(),
			bankContent = new TextField();
	private VBox
			leftBox = new VBox(20),
			rightBox = new VBox(20);
	private HBox 
			stockDetails = new HBox(spacingBoxes), 
			mainButtonBox = new HBox(spacingBoxes), 
			searchFieldBox = new HBox(spacingBoxes), 
			mainBox = new HBox(spacingBoxes);
	public CustomerScreen(ATDProgram controller) {
		this.controller = controller;
		//StockDetails
		stockDetails.getChildren().addAll(
				new VBox(20,
						new HBox(20,nameLabel,		nameContent),
						new HBox(20,addressLabel,	addressContent),
						new HBox(20,postalLabel,	postalContent),
						new HBox(20,placeLabel,		placeContent),
						new HBox(20,dateLabel,		datecontent),
						new HBox(20,emailLabel,		emailContent),
						new HBox(20,phoneLabel,		phoneContent),
						new HBox(20,bankLabel,		bankContent),	
						new HBox(20,blackListLabel, blackListContent),
						new HBox(20,cancelButton,	saveButton)
						));
		stockDetails.setPrefSize(450, 500);
		stockDetails.getStyleClass().add("stockDetails");
		stockDetails.setPadding(new Insets(20));
		setEditable(false);
		//set width for all detail labels and textfields
		for (Node node : ((VBox)stockDetails.getChildren().get(0)).getChildren()) {
			if(((HBox)node).getChildren().get(0) instanceof Label)
				((Label)((HBox)node).getChildren().get(0)).setMinWidth(widthLabels);
			if(((HBox)node).getChildren().get(1) instanceof TextField)
				((TextField)((HBox)node).getChildren().get(1)).setMinWidth(widthLabels*1.5);
		}
		datecontent.setMinWidth(widthLabels*1.5);
		//save/cancel buttons
		cancelButton.setPrefSize(150, 50);
		cancelButton.setOnAction(e -> {
			clearInput();
			setEditable(false);
		});
		saveButton.setPrefSize(150, 50);
		saveButton.setOnAction(e -> {
			save();
		});
		//Listview
		itemList.setPrefSize(450, 500);
		for (Customer customer : controller.getCustomers()) 
			itemList.getItems().add(new ListRegel(customer));
		refreshList();
		itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			select(newValue);
		});
		//SearchField
		searchFieldBox = new HBox(10,searchContent = new TextField("Zoek..."),filterSelector);
		searchContent.setPrefSize(310, 50);
		searchContent.setOnMouseClicked(e -> {
			if (searchContent.getText().equals("Zoek...")) {
				searchContent.clear();
			} else searchContent.selectAll();
		});
		searchContent.textProperty().addListener((observable, oldValue, newValue) -> {
				search(oldValue, newValue);
		});
		//Main Buttons and filter
		mainButtonBox.getChildren().addAll(
				newButton,
				changeButton,
				removeButton
				);
		newButton.setPrefSize(150, 50);
		newButton.setOnAction(e -> {
			clearInput();
			setEditable(true);
			isChanging = false;
		});
		changeButton.setPrefSize(150, 50);
		changeButton.setOnAction(e -> {
			if(checkSelected()){
				setEditable(true);
				isChanging = true;
			}
		});
		removeButton.setPrefSize(150, 50);
		removeButton.setOnAction(e->{
			remove();
		});
		filterSelector.setPrefSize(150, 50);
		filterSelector.getItems().addAll("Filter: Geen", "Filter: Service", "Filter: Onderhoud");
		filterSelector.getSelectionModel().selectFirst();
		filterSelector.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			changeFilter(newValue.intValue());
		});
		//Make & merge left & right
		leftBox.getChildren().addAll (itemList, searchFieldBox);
		rightBox.getChildren().addAll(stockDetails,mainButtonBox);
		mainBox.getChildren().addAll (leftBox,rightBox);
		mainBox.setPadding(new Insets(20));
		this.getChildren().add(mainBox);
	}
	/**
	 * fills the list with items that fit with the given filter
	 * @param newValue selected filter
	 */
	private void changeFilter(int newValue) {
		switch(newValue){
		case 0:{
				itemList.getItems().clear();
				for (Customer customer : controller.getCustomers())
					itemList.getItems().add(new ListRegel(customer));
				break;
			}
		case 1:{
				itemList.getItems().clear();
				for (Customer customer : controller.getRemindList(false))
					itemList.getItems().add(new ListRegel(customer));
				break;
			}
		case 2:{
				itemList.getItems().clear();
				for (Customer customer : controller.getRemindList(true))
					itemList.getItems().add(new ListRegel(customer));
				break;
			}
		}
		if(!searchContent.getText().equals("Zoek..."))search(null, searchContent.getText());	
	}
	/*
	 * removes the selected item
	 */
	private void remove(){
		if(checkSelected()){
			Notification removeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u dit customer wilt verwijderen?", ATDProgram.notificationStyle.CONFIRM);
			removeConfirm.showAndWait();
			if (removeConfirm.getKeuze().equals("confirm")){
				itemList.getItems().remove(selectedCustomer);
				controller.addorRemoveCustomer(selectedCustomer, true);
				Notification removeNotify = new Notification(controller.getStage(), "Het customer is verwijderd.", ATDProgram.notificationStyle.NOTIFY);
				removeNotify.showAndWait();
			}
		}			
	}
	/**
	 * saves the input in either a selected item or a new item
	 */
	private void save(){
		if(checkInput()){
			if(isChanging){
				Notification changeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u deze wijzigingen wilt doorvoeren?",ATDProgram.notificationStyle.CONFIRM);
				changeConfirm.showAndWait();
				switch (changeConfirm.getKeuze()) {
					case "confirm": {
						selectedCustomer.setName(nameContent.getText());
						selectedCustomer.setAddress(addressContent.getText());
						selectedCustomer.setPostal(postalContent.getText());
						selectedCustomer.setPlace(placeContent.getText());
						selectedCustomer.setDateOfBirth(datecontent.getValue());
						selectedCustomer.setEmail(emailContent.getText());
						selectedCustomer.setTel(phoneContent.getText());
						selectedCustomer.setBankAccount(bankContent.getText());
						selectedCustomer.setOnBlackList(blackListContent.isSelected());
						setEditable(false);
						break;
					}
					case "cancel":
						clearInput();
						setEditable(false);
						break;
				}
			}
			else{	
				Notification confirm = new Notification(controller.getStage(),"Deze klant aanmaken?",ATDProgram.notificationStyle.CONFIRM);
				confirm.showAndWait();
				switch (confirm.getKeuze()) {
					case "confirm": {
						 Customer newCustomer = new Customer(
							nameContent.getText(), 
							addressContent.getText(), 
							postalContent.getText(), 
							datecontent.getValue(), 
							placeContent.getText(), 
							emailContent.getText(),
							phoneContent.getText(),
							bankContent.getText(),
							blackListContent.isSelected()
						);		
						 controller.addorRemoveCustomer(newCustomer, false);
						 itemList.getItems().add(new ListRegel(newCustomer));
						 setEditable(false);
						 break;
					}
					case "cancel":	{
						clearInput();
						setEditable(false);
						break;
					}
				}
			}
			refreshList();
		}
		else{
			Notification notFilled = new Notification(controller.getStage(), "Niet alle velden zijn Juist ingevuld",ATDProgram.notificationStyle.NOTIFY);
			notFilled.showAndWait();
		}
	}
	/**
	 * refreshes the list and every item itself
	 */
	private void refreshList(){
		content.clear();
		content.addAll(itemList.getItems());
		itemList.getItems().clear();
		itemList.getItems().addAll(content);
		for (ListRegel listRegel : itemList.getItems())
			listRegel.refresh();
	}
	/**
	 * selects an item
	 * @param selectedValue the item to be selected
	 */
	private void select(ListRegel selectedValue){
		if(selectedValue!= null)selectedCustomer = selectedValue.getCustomer();
		nameContent.setText(selectedCustomer.getName());
		addressContent.setText(selectedCustomer.getAddress());
		postalContent.setText(selectedCustomer.getPostal());
		placeContent.setText(selectedCustomer.getPlace());
		datecontent.setValue(selectedCustomer.getDateOfBirth());
		emailContent.setText(selectedCustomer.getEmail());
		phoneContent.setText(selectedCustomer.getTel());
		bankContent.setText(selectedCustomer.getBankAccount());
		blackListContent.setSelected(selectedCustomer.isOnBlackList());
	}
	/**
	 * disables/enables the left or right side of the stage
	 * @param enable
	 */
	private void setEditable(boolean enable){
		cancelButton.setVisible(enable);
		saveButton.setVisible(enable);
		for (Node node1 : ((VBox)stockDetails.getChildren().get(0)).getChildren())
			if(((HBox)node1).getChildren().get(1) instanceof TextField)((TextField)((HBox)node1).getChildren().get(1)).setDisable(!enable);
		datecontent.setDisable(!enable);
		blackListContent.setDisable(!enable);
		leftBox.setDisable(enable);
	}	
	/**
	 * clears all the inputfields
	 */
	private void clearInput(){
		for (Node node1 : ((VBox)stockDetails.getChildren().get(0)).getChildren())
			if(((HBox)node1).getChildren().get(1) instanceof TextField)((TextField)((HBox)node1).getChildren().get(1)).clear();
		datecontent.setValue(null);
		blackListContent.setSelected(false);
	}
	/**
	 * checks if all the inputfields are filled in
	 * @return false if one of the inputs is null
	 */
	private boolean checkInput(){
		for (Node node1 : ((VBox)stockDetails.getChildren().get(0)).getChildren())
			if(((HBox)node1).getChildren().get(1) instanceof TextField){
				if(((TextField)((HBox)node1).getChildren().get(1)).getText().isEmpty()){
					return false;
				}
			}
		return true;
	}
	/**
	 * checks if an item is selected in the list
	 * @return false if nothing is selected
	 */
	private boolean checkSelected() {
		if(selectedCustomer == null) return false;
		return true;
	}
	/**
	 * searches through all items in the list
	 * @param oldVal the previous content of the searchfield
	 * @param newVal the new content of the searchfield
	 */
	public void search(String oldVal, String newVal) {
		if (oldVal != null && (newVal.length() < oldVal.length())) {
			//actor has deleted a character, so reset the search
			itemList.getItems().clear();
			itemList.getItems().addAll(content);
		}
		itemList.getItems().clear();
		//add an item if any item that exists contains any value that has been searched for
		for (ListRegel entry : content) {		
			if (entry.getCustomer().getName().contains(newVal)
					|| entry.getCustomer().getEmail().contains(newVal)
					|| entry.getCustomer().getName().contains(newVal)) {
				itemList.getItems().add(entry);
			}
		}
	}
	// this class represents every item in the list
	public class ListRegel extends HBox{
		private Customer customer;
		private Label itemPostalLabel = new Label(),itemNameLabel = new Label(),itemEmailLabel = new Label();
		public ListRegel(Customer customer){
			//no filter
			this.customer = customer;
			refresh();
			setSpacing(5);
			getChildren().addAll(
				itemNameLabel,
				new Separator(Orientation.VERTICAL),
				itemPostalLabel,
				new Separator(Orientation.VERTICAL),
				itemEmailLabel);
				for (Node node : getChildren()) 
					if(node instanceof Label)((Label)node).setPrefWidth(100);
		}
		/**
		 * fills in all the labels with the latest values
		 */
		public void refresh(){
			itemNameLabel.setText(customer.getName());
			itemPostalLabel.setText(customer.getPostal());
			itemEmailLabel.setText(customer.getEmail());
		}
		/**
		 * @return the object this item represents
		 */
		public Customer getCustomer(){
			return customer;
		}
	}
}
	
