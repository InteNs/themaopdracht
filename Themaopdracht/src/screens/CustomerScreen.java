
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

public class CustomerScreen extends Screen {
	private ATDProgram controller;

	private final ComboBox<String> filterSelector 	= new ComboBox<String>();
	private final ArrayList<ListItem> content 		= new ArrayList<ListItem>();
	private final ListView<ListItem> itemList 		= new ListView<ListItem>();
	private final CheckBox blackListContent 		= new CheckBox();
	private final DatePicker datecontent 			= new DatePicker();
	private Customer selectedObject;
	private ListItem selectedItem;
	private boolean isChanging;
	private static final double
	space_Small = 10,
	space_Big 	= 20,
	space_3 	= 15,
	button_3 	= 140,
	label_Normal= 120;
	private final Button 
	newButton 		= new Button("Nieuw"), 
	changeButton 	= new Button("Aanpassen"), 
	removeButton 	= new Button("Verwijderen"), 
	cancelButton 	= new Button("Annuleren"),
	saveButton 		= new Button("Opslaan");
	private final Label 
	nameLabel 		= new Label("Naam:"),
	addressLabel 	= new Label("Adres:"),
	postalLabel 	= new Label("Postcode:"),
	placeLabel 		= new Label("Plaats:"),
	dateLabel 		= new Label("Geboortedatum:"),
	emailLabel 		= new Label("Email:"),
	phoneLabel 		= new Label("Telefoonnummer:"),
	bankLabel 		= new Label("Banknummer:"),
	blackListLabel  = new Label("Blacklist:");
	private final TextField 
	searchContent 	= new TextField("Zoek..."),
	nameContent 	= new TextField(),
	addressContent  = new TextField(),
	postalContent 	= new TextField(),
	placeContent 	= new TextField(),
	emailContent 	= new TextField(),
	phoneContent 	= new TextField(),
	bankContent 	= new TextField();
	private  final VBox
	leftBox 		= new VBox(space_Big),
	rightBox 		= new VBox(space_Big);
	private  final HBox 
	detailsBox 		= new HBox(space_Small), 
	control_MainBox	= new HBox(space_3), 
	control_secBox 	= new HBox(space_Small), 
	mainBox 		= new HBox(space_Small);
	public CustomerScreen(ATDProgram controller) {
		super(controller);
		this.controller = controller;
		//put everything in the right places
		control_MainBox.getChildren().addAll(newButton		, changeButton		,removeButton);
		control_secBox.getChildren().addAll (searchContent	, filterSelector);
		leftBox.getChildren().addAll 		(itemList		, control_secBox);
		rightBox.getChildren().addAll		(detailsBox		, control_MainBox);
		mainBox.getChildren().addAll 		(leftBox		, rightBox);
		this.getChildren().add				(mainBox);
		detailsBox.getChildren().addAll(
				new VBox(space_Big,
						new HBox(space_Big,nameLabel	 , nameContent),
						new HBox(space_Big,addressLabel	 , addressContent),
						new HBox(space_Big,postalLabel	 , postalContent),
						new HBox(space_Big,placeLabel	 , placeContent),
						new HBox(space_Big,dateLabel	 , datecontent),
						new HBox(space_Big,emailLabel	 , emailContent),
						new HBox(space_Big,phoneLabel	 , phoneContent),
						new HBox(space_Big,bankLabel	 , bankContent),	
						new HBox(space_Big,blackListLabel, blackListContent),
						new HBox(space_Big,cancelButton	 , saveButton))
		);
		//set styles and sizes
		////set width for all detail labels and textfields
		for (Node node : ((VBox)detailsBox.getChildren().get(0)).getChildren()) {
			if(((HBox)node).getChildren().get(0) instanceof Label)
				((Label)((HBox)node).getChildren().get(0)).setMinWidth(label_Normal);
			if(((HBox)node).getChildren().get(1) instanceof TextField)
				((TextField)((HBox)node).getChildren().get(1)).setMinWidth(label_Normal*1.5);
		}
		datecontent.setMinWidth(label_Normal*1.5);
		detailsBox.setPadding(new Insets(space_Big));
		mainBox.setPadding(new Insets(space_Big));
		detailsBox.getStyleClass().addAll("removeDisabledEffect","detailsBox");
		leftBox.getStyleClass().add("removeDisabledEffect");
		detailsBox.setPrefSize		(450	 , 520);
		itemList.setPrefSize		(450	 , 520);
		searchContent.setPrefSize	(290	 , 50);
		filterSelector.setPrefSize	(150	 , 50);
		cancelButton.setPrefSize	(150	 , 50);
		saveButton.setPrefSize		(150	 , 50);
		newButton.setPrefSize		(button_3, 50);
		changeButton.setPrefSize	(button_3, 50);
		removeButton.setPrefSize	(button_3, 50);
		//details	
		setEditable(false);
		//Listview
		for (Customer object : controller.getCustomers()) 
			itemList.getItems().add(new ListItem(object));
		refreshList();
		itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			select(newValue);
		});
		//SearchField
		searchContent.setOnMouseClicked(e -> {
			if (searchContent.getText().equals("Zoek...")) {
				searchContent.clear();
			} else searchContent.selectAll();
		});
		searchContent.textProperty().addListener((observable, oldValue, newValue) -> {
			search(oldValue, newValue);
		});
		//buttons and filter
		cancelButton.setOnAction(e -> {
			clearInput();
			setEditable(false);
			refreshList();
		});
		saveButton.setOnAction(e -> {
			save();
		});
		newButton.setOnAction(e -> {
			clearInput();
			setEditable(true);
			isChanging = false;
		});
		changeButton.setOnAction(e -> {
			if(checkSelected()){
				setEditable(true);
				isChanging = true;
			}
		});
		removeButton.setOnAction(e->{
			remove();
		});
		filterSelector.getItems().addAll("Filter: Geen", "Filter: Service", "Filter: Onderhoud");
		filterSelector.getSelectionModel().selectFirst();
		filterSelector.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			changeFilter(newValue.intValue());
		});
	}
	/**
	 * fills the list with items that fit with the given filter
	 * @param newValue selected filter
	 */
	private void changeFilter(int newValue) {
		switch(newValue){
		case 0:{
			itemList.getItems().clear();
			for (Customer object : controller.getCustomers())
				itemList.getItems().add(new ListItem(object));
			break;
		}
		case 1:{
			itemList.getItems().clear();
			for (Customer object : controller.getRemindList(false))
				itemList.getItems().add(new ListItem(object));
			break;
		}
		case 2:{
			itemList.getItems().clear();
			for (Customer object : controller.getRemindList(true))
				itemList.getItems().add(new ListItem(object));
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
			Notification confirm = new Notification(controller, "Weet u zeker dat u deze klant wilt verwijderen?", Notification.notificationStyle.CONFIRM);
			confirm.showAndWait();
			if (confirm.getKeuze().equals("confirm")){
				itemList.getItems().remove(selectedItem);
				controller.addorRemoveCustomer(selectedObject, true);
				Notification notify = new Notification(controller, "De klant is verwijderd.", Notification.notificationStyle.NOTIFY);
				notify.showAndWait();
			}
		}			
	}
	/**
	 * saves the input in either a selected item or a new item
	 */
	private void save(){
		if(checkInput()){
			if(isChanging){
				Notification confirm = new Notification(controller, "Weet u zeker dat u deze wijzigingen wilt doorvoeren?",Notification.notificationStyle.CONFIRM);
				confirm.showAndWait();
				switch (confirm.getKeuze()) {
				case "confirm": {
					selectedObject.setName(nameContent.getText());
					selectedObject.setAddress(addressContent.getText());
					selectedObject.setPostal(postalContent.getText());
					selectedObject.setPlace(placeContent.getText());
					selectedObject.setDateOfBirth(datecontent.getValue());
					selectedObject.setEmail(emailContent.getText());
					selectedObject.setTel(phoneContent.getText());
					selectedObject.setBankAccount(bankContent.getText());
					selectedObject.setOnBlackList(blackListContent.isSelected());
					setEditable(false);
					break;
				}
				case "cancel":
					clearInput();
					setEditable(false);
					refreshList();
					break;
				}
			}
			else{	
				Notification confirm = new Notification(controller,"Deze klant aanmaken?",Notification.notificationStyle.CONFIRM);
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
					itemList.getItems().add(new ListItem(newCustomer));
					setEditable(false);
					break;
				}
				case "cancel":	{
					clearInput();
					setEditable(false);
					refreshList();
					break;
				}
				}
			}
			refreshList();
		}
		else{
			Notification notFilled = new Notification(controller, "Niet alle velden zijn juist ingevuld",Notification.notificationStyle.NOTIFY);
			notFilled.showAndWait();
		}
	}
	/**
	 * refreshes the list and every item itself
	 */
	public void refreshList(){
		content.clear();
		content.addAll(itemList.getItems());
		itemList.getItems().clear();
		itemList.getItems().addAll(content);
		for (ListItem listItem : itemList.getItems())
			listItem.refresh();
		changeFilter(filterSelector.getSelectionModel().getSelectedIndex());
		select(selectedItem);
	}
	/**
	 * selects an item
	 * @param selectedValue the item to be selected
	 */
	private void select(ListItem selectedValue){
		if(selectedValue!= null){
			selectedItem = selectedValue;
			selectedObject = selectedValue.getCustomer();
			nameContent.setText(selectedObject.getName());
			addressContent.setText(selectedObject.getAddress());
			postalContent.setText(selectedObject.getPostal());
			placeContent.setText(selectedObject.getPlace());
			datecontent.setValue(selectedObject.getDateOfBirth());
			emailContent.setText(selectedObject.getEmail());
			phoneContent.setText(selectedObject.getTel());
			bankContent.setText(selectedObject.getBankAccount());
			blackListContent.setSelected(selectedObject.isOnBlackList());
		}
	}
	/**
	 * disables/enables the left or right side of the stage
	 * @param enable
	 */
	private void setEditable(boolean enable){
		cancelButton.setVisible(enable);
		saveButton.setVisible(enable);
		detailsBox.setDisable(!enable);
		control_MainBox.setDisable(enable);
		leftBox.setDisable(enable);
	}	
	/**
	 * clears all the inputfields
	 */
	private void clearInput(){
		for (Node node1 : ((VBox)detailsBox.getChildren().get(0)).getChildren())
			if(((HBox)node1).getChildren().get(1) instanceof TextField)((TextField)((HBox)node1).getChildren().get(1)).clear();
		datecontent.setValue(null);
		blackListContent.setSelected(false);
	}
	/**
	 * checks if all the inputfields are filled in
	 * @return false if one of the inputs is null
	 */
	private boolean checkInput(){
		for (Node node1 : ((VBox)detailsBox.getChildren().get(0)).getChildren())
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
		if(selectedObject == null) return false;
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
		for (ListItem entry : content) {		
			if (entry.getCustomer().getName().contains(newVal)
					|| entry.getCustomer().getEmail().contains(newVal)
					|| entry.getCustomer().getName().contains(newVal)) {
				itemList.getItems().add(entry);
			}
		}
	}
	// this class represents every item in the list
	public class ListItem extends HBox{
		private Customer object;
		private Label itemPostalLabel = new Label(),itemNameLabel = new Label(),itemEmailLabel = new Label();
		public ListItem(Customer object){
			//no filter
			this.object = object;
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
			itemNameLabel.setText(object.getName());
			itemPostalLabel.setText(object.getPostal());
			itemEmailLabel.setText(object.getEmail());
		}
		/**
		 * @return the object this item represents
		 */
		public Customer getCustomer(){
			return object;
		}
	}
}
