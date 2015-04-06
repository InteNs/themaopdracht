package screens;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

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
import main.Invoice;
import main.Invoice.InvoiceItem;
import main.Invoice.PayMethod;
import main.MaintenanceSession;
import main.Product;
import main.Reservation;
import notifications.Notification;

public class InvoiceScreen extends Screen {
	private ATDProgram controller;
	private final ComboBox<String> filterSelector 	= new ComboBox<String>();
	private final ComboBox<Customer> customerContent= new ComboBox<Customer>();
	private final CheckBox isPayedContent 			= new CheckBox();
	private final ArrayList<ListItem> content 		= new ArrayList<ListItem>();
	private final ListView<ListItem> itemList 		= new ListView<ListItem>();
	private final ListView<InvoiceItem> contentList = new ListView<InvoiceItem>();
	private final DatePicker dateContent 			= new DatePicker();
	private Invoice selectedobject;
	private ListItem selectedItem;
	private boolean isChanging;
	private static final double
			space_Medium = 10,
			space_big = 20,
			space_4 = 6,
			button_4 = 108,
			label_Normal = 120;
	private final Button 
			newButton 	 		= new Button("Nieuw"),  
			payButton 	 		= new Button("Betalen"),
			removeButton 		= new Button("Verwijderen"), 
			cancelButton 		= new Button("Annuleren"),
			changeButton 		= new Button("Aanpassen"),
			saveButton 	 		= new Button("Opslaan"),
			addMaintenanceButton= new Button("+Onderhoud"),
			addRefuelButton 	= new Button("+Tanksessie"),
			addParkingButton 	= new Button("+Reservering");
	private final Label 
			dateLabel 	  		= new Label("Datum: "),
			priceLabel	  		= new Label("Prijs: "), 
			isPayedLabel  		= new Label("Is betaalt: "),
			customerLabel 		= new Label("Klant: ");
	private final TextField 
			priceContent  		= new TextField();
	private final VBox
			leftBox 			= new VBox(space_big),
			rightBox 			= new VBox(space_big);
	private final HBox 	
			detailsBox 			= new HBox(space_Medium), 
			control_MainBox 	= new HBox(space_4), 
			control_secBox 		= new HBox(space_4), 
			mainBox 			= new HBox(space_Medium);
	public InvoiceScreen(ATDProgram controller) {
		super(controller);
		this.controller = controller;
		//put everything in the right place
		control_MainBox.getChildren().addAll(newButton,changeButton	,payButton		,removeButton);
		control_secBox.getChildren().addAll	(addMaintenanceButton	,addRefuelButton,addParkingButton,filterSelector);
		leftBox.getChildren().addAll 		(itemList				, control_secBox);
		rightBox.getChildren().addAll		(detailsBox				,control_MainBox);
		mainBox.getChildren().addAll 		(leftBox				,rightBox);
		this.getChildren().add				(mainBox);
		detailsBox.getChildren().addAll(
				new VBox(space_big,
						new HBox(space_big,dateLabel,		dateContent),
						new HBox(space_big,priceLabel,	 	priceContent),
						new HBox(space_big,customerLabel,	customerContent),
						new HBox(space_big,isPayedLabel,	isPayedContent),
						new HBox(space_big,cancelButton,	saveButton),
						new HBox(contentList))
		);
		//set styles and sizes
		////set width for all detail labels and textfields
		for (Node node : ((VBox)detailsBox.getChildren().get(0)).getChildren()) {
			if(((HBox)node).getChildren().get(0) instanceof Label)
				((Label)((HBox)node).getChildren().get(0)).setMinWidth(label_Normal);
		}
		priceContent.setMinWidth(label_Normal*1.5);
		dateContent.setMinWidth(label_Normal*1.5);
		customerContent.setMinWidth(label_Normal*1.5);
		detailsBox.setPadding(new Insets(space_big));
		mainBox.setPadding(new Insets(space_big));
		detailsBox.getStyleClass().addAll("removeDisabledEffect","detailsBox");
		detailsBox.setPrefSize			(450	 , 520);
		itemList.setPrefSize			(450	 , 520);
		contentList.setPrefSize			(410	 , 300);
		filterSelector.setPrefSize		(button_4, 50);
		cancelButton.setPrefSize		(button_4, 50);
		saveButton.setPrefSize			(button_4, 50);
		newButton.setPrefSize			(button_4, 50);
		changeButton.setPrefSize		(button_4, 50);
		removeButton.setPrefSize		(button_4, 50);
		payButton.setPrefSize			(button_4, 50);
		addParkingButton.setPrefSize	(button_4, 50);
		addMaintenanceButton.setPrefSize(button_4, 50);
		addRefuelButton.setPrefSize		(button_4, 50);
		//details		
		customerContent.getItems().addAll(controller.getCustomers());
		setEditable(false);
		//Listview
		for (Invoice object : controller.getInvoices()) 
			if(!object.isPayed())itemList.getItems().add(new ListItem(object));
		refreshList();
		itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			select(newValue);
		});
		//Buttons and filter
		addRefuelButton.setOnAction(e -> {
			addFuel();
		});
		addMaintenanceButton.setOnAction(e -> {
			addMaintenance();
		});
		addParkingButton.setOnAction(e->{
			addParking();
		});
		newButton.setOnAction(e -> {
			isChanging = false;
			save();
		});
		payButton.setOnAction(e->{
			pay();
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
		cancelButton.setOnAction(e -> {
			setEditable(false);
		});
		saveButton.setOnAction(e -> {
			save();
		});
		filterSelector.getItems().addAll("Filter: Geen", "Filter: Achterstand", "Filter: Anoniem", "Filter: Betaalt");
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
		case 0:{//geen
				itemList.getItems().clear();
				for (Invoice object : controller.getInvoices())
					if(!object.isPayed())itemList.getItems().add(new ListItem(object));
				break;
			}
		case 1:{//achterstand
				itemList.getItems().clear();
				for (Invoice object : controller.getInvoices())
					if(!object.isPayed() && object.getInvoiceDate().isBefore(LocalDate.now().minusMonths(3)))
						itemList.getItems().add(new ListItem(object));
				break;
			}
		case 2:{//anoniem
				itemList.getItems().clear();
				for (Invoice object : controller.getInvoices())
					if(!object.isPayed()&& object.getCustomer()==null)itemList.getItems().add(new ListItem(object));
				break;
			}
		case 3:{//betaalt
			itemList.getItems().clear();
			for (Invoice object : controller.getInvoices())
				if(object.isPayed())itemList.getItems().add(new ListItem(object));
			break;
		}
		}
		
//		if(!searchInput.getText().equals("Zoek..."))search(null, searchInput.getText());	
	}
	/*
	 * removes the selected item
	 */
	private void remove(){
		if(checkSelected()){
			Notification Confirm = new Notification(controller, "Weet u zeker dat u deze rekening wilt verwijderen?", ATDProgram.notificationStyle.CONFIRM);
			Confirm.showAndWait();
			if (Confirm.getKeuze().equals("confirm")){
				itemList.getItems().remove(selectedItem);
				controller.addorRemoveInvoice(selectedobject, true);
				Notification Notify = new Notification(controller, "De rekening is verwijderd.", ATDProgram.notificationStyle.NOTIFY);
				Notify.showAndWait();
			}
		}			
	}
	/**
	 * adds a fuelsession to the invoice
	 */
	private void addFuel(){
		Notification addFuelNotification = new Notification(controller,"", ATDProgram.notificationStyle.TANK);
		addFuelNotification.showAndWait();
		if(addFuelNotification.getKeuze().equals("confirm")){
			int tanked = addFuelNotification.getInput();
			Fuel type = (Fuel)addFuelNotification.getSelected();
			if(tanked > type.getAmount())tanked = type.getAmount();
			controller.getStock().useProduct(type,tanked);
			selectedobject.add(selectedobject.new InvoiceItem(type.getName(), type.getSellPrice(), tanked));
			refreshList();

		}
	}
	/**
	 * adds a maintenance session to the invoice
	 */
	private void addMaintenance(){
		Notification addMaintenanceNotification = new Notification(controller,"", ATDProgram.notificationStyle.MAINTENANCE);
		addMaintenanceNotification.showAndWait();
		if(addMaintenanceNotification.getKeuze().equals("confirm")){
			MaintenanceSession selection = (MaintenanceSession)addMaintenanceNotification.getSelected();
			selectedobject.add(selectedobject.new InvoiceItem("Uurloon", selection.getMechanic().getHourlyFee(), selection.getMechanic().getWorkedHours()));
			Iterator<Product> keySetIterator = selection.getUsedParts().keySet().iterator();
			while(keySetIterator.hasNext()){
				Product key = keySetIterator.next();
				double price = key.getSellPrice() * selection.getUsedParts().get(key);
				controller.getStock().useProduct(key, selection.getUsedParts().get(key));
				selectedobject.add(selectedobject.new InvoiceItem(key.getName(),price,selection.getUsedParts().get(key)));
				refreshList();
			}
		}	
	}
	/** 
	 * adds a reservation to the invoice
	 */
	private void addParking(){
		Notification addParking = new Notification(controller,"", ATDProgram.notificationStyle.PARKING);
		addParking.showAndWait();
		if(addParking.getKeuze().equals("confirm")) {
			selectedobject.add(selectedobject.new InvoiceItem("Parkeersessie",((Reservation)addParking.getSelected()).getTotalPrice(),1));
			Notification notify = new Notification(controller, "reservering toegevoegd.", ATDProgram.notificationStyle.NOTIFY);
			notify.showAndWait();
		}
		refreshList();
	}
	/**
	 * pays the selected invoice
	 */
	private void pay(){
		Notification confirm = new Notification(controller,"", ATDProgram.notificationStyle.PAY);
		confirm.showAndWait();
		if(confirm.getKeuze().equals("confirm")) {
			selectedobject.payNow((PayMethod) confirm.getSelected());
			Notification notify = new Notification(controller, "factuur is betaald.", ATDProgram.notificationStyle.NOTIFY);
			notify.showAndWait();
		}
		refreshList();
	}
	/**
	 * saves the input in either a selected item or a new item
	 */
	private void save(){
		if(isChanging){
			Notification confirm = new Notification(controller, "Weet u zeker dat u deze wijzigingen wilt doorvoeren?",ATDProgram.notificationStyle.CONFIRM);
			confirm.showAndWait();
			switch (confirm.getKeuze()) {
			case "confirm": {	
				if(customerContent.getValue()!=null)selectedobject.bindToCustomer(customerContent.getValue());
				customerContent.setDisable(true);
				refreshList();
			}
			case "cancel":
				setEditable(false);
			}
		}
		else{	
			Invoice newInvoice = new Invoice(controller);
			controller.addorRemoveInvoice(newInvoice, false);
			itemList.getItems().add(new ListItem(newInvoice));
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
	 * selects an item and fills in the information
	 * @param selectedValue the item to be selected
	 */
	private void select(ListItem selectedValue){
		if(selectedValue!=null)	{
			selectedItem = selectedValue;
			selectedobject = selectedValue.getInvoice();
			dateContent.setValue(selectedobject.getInvoiceDate());
			customerContent.setValue(selectedobject.getCustomer());
			priceContent.setText(controller.convert(selectedobject.getTotalPrice()));
			isPayedContent.setSelected(selectedobject.isPayed());
			contentList.getItems().clear();
			for (InvoiceItem item : selectedobject.getItems())
				contentList.getItems().add(item);
		}
	}
	/**
	 * disables/enables the left or right side of the stage
	 * @param enable
	 */
	private void setEditable(boolean enable){
		cancelButton.setVisible(enable);
		saveButton.setVisible(enable);
		customerContent.setDisable(!enable);
		priceContent.setDisable(true);
		dateContent.setDisable(true);
		isPayedContent.setDisable(true);
		control_MainBox.setDisable(enable);
		leftBox.setDisable(enable);
	}		
	/**
	 * checks if an item is selected in the list
	 * @return false if nothing is selected
	 */
	private boolean checkSelected() {
		if(selectedobject == null) return false;
		return true;
	}
	// this represents every item in the list, it has different constructor for every filter option
	public class ListItem extends HBox{
		private Invoice object;
		private Label itemDateLabel = new Label(),itemPriceLabel = new Label(),itemCustomerLabel = new Label(),itemIsPayedLabel = new Label();
		public ListItem(Invoice object){
			this.object = object;
			refresh();
			setSpacing(5);
			getChildren().addAll(
				itemDateLabel,
				new Separator(Orientation.VERTICAL),
				itemCustomerLabel,
				new Separator(Orientation.VERTICAL),
				itemPriceLabel,
				new Separator(Orientation.VERTICAL),
				itemIsPayedLabel);
				for (Node node : getChildren()) 
					if(node instanceof Label)((Label)node).setPrefWidth(100);
		}
		/**
		 * fills in all the labels with the latest values
		 */
		public void refresh(){
			itemDateLabel.setText(object.getInvoiceDate().toString());
			if(object.getCustomer()==null)itemCustomerLabel.setText("Anoniem");
			else itemCustomerLabel.setText(object.getCustomer().getName());
			itemPriceLabel.setText(controller.convert(object.getTotalPrice()));
			if(object.isPayed())itemIsPayedLabel.setText("Betaalt");
			else itemIsPayedLabel.setText("Openstaand");
		}
		/**
		 * @return the object this item represents
		 */
		public Invoice getInvoice(){
			return object;
		}
	}
}
	