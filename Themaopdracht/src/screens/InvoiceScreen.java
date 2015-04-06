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
import notifications.GetInfoNotification;
import notifications.Notification;

public class InvoiceScreen extends HBox {
	private ATDProgram controller;
	private Invoice selectedInvoice;
	private ListRegel selectedItem;
	private final ComboBox<String> filterSelector = new ComboBox<String>();
	private final ComboBox<Customer> customerContent = new ComboBox<Customer>();
	private final CheckBox isPayedContent = new CheckBox();
	private final ArrayList<ListRegel> content = new ArrayList<ListRegel>();
	private final ListView<ListRegel> itemList = new ListView<ListRegel>();
	private final ListView<InvoiceItem> contentList = new ListView<InvoiceItem>();
	private final DatePicker dateContent = new DatePicker();
	private static final double
			space_small = 10,
			space_tiny = 6,
			space_big = 20,
			button_small = 108,
			widthLabels = 120;
	private boolean isChanging = false;
	private Button 
			newButton = new Button("Nieuw"),  
			payButton = new Button("Betalen"),
			removeButton = new Button("Verwijderen"), 
			cancelButton = new Button("Annuleren"),
			changeButton = new Button("Aanpassen"),
			saveButton = new Button("Opslaan"),
			addMaintenanceButton = new Button("+Onderhoud"),
			addRefuelButton = new Button("+Tanksessie"),
			addParkingButton = new Button("+reservering");
	private Label 
			dateLabel = new Label("Datum: "),
			priceLabel = new Label("Prijs: "), 
			isPayedLabel = new Label("Is betaalt: "),
			customerLabel = new Label("Klant: ");
	private TextField 
//			searchInput = new TextField("Zoek..."), 
			priceContent = new TextField();
	private VBox
			leftBox = new VBox(space_big),
			rightBox = new VBox(space_big);
	private HBox 
			details = new HBox(space_small), 
			mainButtonBox = new HBox(space_tiny), 
			searchFieldBox = new HBox(space_tiny), 
			mainBox = new HBox(space_small);
	public InvoiceScreen(ATDProgram controller) {
		this.controller = controller;
		//StockDetails
		customerContent.getItems().addAll(controller.getCustomers());
		details.getChildren().addAll(
				new VBox(space_big,
						new HBox(space_big,dateLabel,		dateContent),
						new HBox(space_big,priceLabel,	 	priceContent),
						new HBox(space_big,customerLabel,	customerContent),
						new HBox(space_big,isPayedLabel,	isPayedContent),
						new HBox(space_big,cancelButton,	saveButton),
						new HBox(contentList)
						));
		details.setPrefSize(450, 520);
		details.getStyleClass().add("stockDetails");
		details.setPadding(new Insets(space_big));
		setEditable(false);
		//set width for all detail labels and textfields
		for (Node node : ((VBox)details.getChildren().get(0)).getChildren()) {
			if(((HBox)node).getChildren().get(0) instanceof Label)
				((Label)((HBox)node).getChildren().get(0)).setMinWidth(widthLabels);
		}
		priceContent.setMinWidth(widthLabels*1.5);
		dateContent.setMinWidth(widthLabels*1.5);
		customerContent.setMinWidth(widthLabels*1.5);
		//Listview
		contentList.setPrefSize(410, 300);
		itemList.setPrefSize(450, 520);
		for (Invoice invoice : controller.getInvoices()) 
			if(!invoice.isPayed())itemList.getItems().add(new ListRegel(invoice));
		refreshList();
		itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			select(newValue);
		});
		//SearchField
//		searchInput.setPrefSize(112.5, 50);
//		searchInput.setOnMouseClicked(e -> {
//			if (searchInput.getText().equals("Zoek...")) {
//				searchInput.clear();
//			} else searchInput.selectAll();
//		});
//		searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
//				search(oldValue, newValue);
//		});
		//Buttons and filter
		addRefuelButton.setPrefSize(button_small, 50);
		addRefuelButton.setOnAction(e -> {
			addFuel();
		});
		addMaintenanceButton.setPrefSize(button_small, 50);
		addMaintenanceButton.setOnAction(e -> {
			addMaintenance();
		});
		addParkingButton.setPrefSize(button_small, 50);
		addParkingButton.setOnAction(e->{
			addParking();
		});
		newButton.setPrefSize(button_small, 50);
		newButton.setOnAction(e -> {
			isChanging = false;
			save();
		});
		payButton.setPrefSize(button_small, 50);
		payButton.setOnAction(e->{
			pay();
		});
		changeButton.setPrefSize(button_small, 50);
		changeButton.setOnAction(e -> {
			if(checkSelected()){
				setEditable(true);
				isChanging = true;
			}
		});
		removeButton.setPrefSize(button_small, 50);
		removeButton.setOnAction(e->{
			remove();
		});
		cancelButton.setPrefSize(button_small, 50);
		cancelButton.setOnAction(e -> {
			setEditable(false);
		});
		saveButton.setPrefSize(button_small, 50);
		saveButton.setOnAction(e -> {
			save();
		});
		filterSelector.setPrefSize(button_small, 50);
		filterSelector.getItems().addAll("Filter: Geen", "Filter: Achterstand", "Filter: Anoniem", "Filter: Betaalt");
		filterSelector.getSelectionModel().selectFirst();
		filterSelector.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			changeFilter(newValue.intValue());
		});
		//Make & merge left & right
		mainButtonBox.getChildren().addAll(newButton,changeButton,payButton,removeButton);
		searchFieldBox.getChildren().addAll(addMaintenanceButton,addRefuelButton,addParkingButton,filterSelector);
		leftBox.getChildren().addAll (itemList, searchFieldBox);
		rightBox.getChildren().addAll(details,mainButtonBox);
		mainBox.getChildren().addAll (leftBox,rightBox);
		mainBox.setPadding(new Insets(space_big));
		this.getChildren().add(mainBox);
		System.out.println(details.getWidth());
	}
	/**
	 * fills the list with items that fit with the given filter
	 * @param newValue selected filter
	 */
	private void changeFilter(int newValue) {
		switch(newValue){
		case 0:{//geen
				itemList.getItems().clear();
				for (Invoice invoice : controller.getInvoices())
					if(!invoice.isPayed())itemList.getItems().add(new ListRegel(invoice));
				break;
			}
		case 1:{//achterstand
				itemList.getItems().clear();
				for (Invoice invoice : controller.getInvoices())
					if(!invoice.isPayed() && invoice.getInvoiceDate().isBefore(LocalDate.now().minusMonths(3)))
						itemList.getItems().add(new ListRegel(invoice));
				break;
			}
		case 2:{//anoniem
				itemList.getItems().clear();
				for (Invoice invoice : controller.getInvoices())
					if(!invoice.isPayed()&& invoice.getCustomer()==null)itemList.getItems().add(new ListRegel(invoice));
				break;
			}
		case 3:{//betaalt
			itemList.getItems().clear();
			for (Invoice invoice : controller.getInvoices())
				if(invoice.isPayed())itemList.getItems().add(new ListRegel(invoice));
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
			Notification removeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u dit invoice wilt verwijderen?", ATDProgram.notificationStyle.CONFIRM);
			removeConfirm.showAndWait();
			if (removeConfirm.getKeuze().equals("confirm")){
				itemList.getItems().remove(selectedItem);
				controller.addorRemoveInvoice(selectedInvoice, true);
				Notification removeNotify = new Notification(controller.getStage(), "Het invoice is verwijderd.", ATDProgram.notificationStyle.NOTIFY);
				removeNotify.showAndWait();
			}
		}			
	}
	/**
	 * adds a fuelsession to the invoice
	 */
	private void addFuel(){
		GetInfoNotification addFuelNotification = new GetInfoNotification(controller, ATDProgram.notificationStyle.TANK);
		addFuelNotification.showAndWait();
		if(addFuelNotification.getKeuze().equals("confirm")){
			controller.getStock().useProduct((Fuel)addFuelNotification.getSelected(), addFuelNotification.getInput());
			//VERGEET NIET TE CHECKEN OF ER GENOEG BENZINE IS
			selectedInvoice.add(selectedInvoice.new InvoiceItem(((Fuel)addFuelNotification.getSelected()).getName(), ((Fuel)addFuelNotification.getSelected()).getSellPrice(), addFuelNotification.getInput()));
			refreshList();

		}
	}
	/**
	 * adds a maintenance session to the invoice
	 */
	private void addMaintenance(){
		GetInfoNotification addMaintenanceNotification = new GetInfoNotification(controller, ATDProgram.notificationStyle.MAINTENANCE);
		addMaintenanceNotification.showAndWait();
		if(addMaintenanceNotification.getKeuze().equals("confirm")){
			MaintenanceSession selection = (MaintenanceSession)addMaintenanceNotification.getSelected();
			selectedInvoice.add(selectedInvoice.new InvoiceItem("Uurloon", selection.getMechanic().getHourlyFee(), selection.getMechanic().getWorkedHours()));
			Iterator<Product> keySetIterator = selection.getUsedParts().keySet().iterator();
			while(keySetIterator.hasNext()){
				Product key = keySetIterator.next();
				double price = key.getSellPrice() * selection.getUsedParts().get(key);
				controller.getStock().useProduct(key, selection.getUsedParts().get(key));
				selectedInvoice.add(selectedInvoice.new InvoiceItem(key.getName(),price,selection.getUsedParts().get(key)));
				refreshList();
			}
		}	
	}
	/** 
	 * adds a reservation to the invoice
	 */
	private void addParking(){
		GetInfoNotification addMaintenanceNotification = new GetInfoNotification(controller, ATDProgram.notificationStyle.PARKING);
		addMaintenanceNotification.showAndWait();
		//TODO
	}
	/**
	 * pays the selected invoice
	 */
	private void pay(){
		GetInfoNotification payConfirm = new GetInfoNotification(controller, ATDProgram.notificationStyle.PAY);
		payConfirm.showAndWait();
		if(payConfirm.getKeuze().equals("confirm")) {
			selectedInvoice.payNow((PayMethod) payConfirm.getSelected());
			Notification payNotify = new Notification(controller.getStage(), "factuur is betaald.", ATDProgram.notificationStyle.NOTIFY);
			payNotify.showAndWait();}
		refreshList();
	}
	/**
	 * saves the input in either a selected item or a new item
	 */
	private void save(){
		if(isChanging){
			Notification confirm = new Notification(controller.getStage(), "Weet u zeker dat u deze wijzigingen wilt doorvoeren?",ATDProgram.notificationStyle.CONFIRM);
			confirm.showAndWait();
			switch (confirm.getKeuze()) {
			case "confirm": {	
				if(customerContent.getValue()!=null)selectedInvoice.bindToCustomer(customerContent.getValue());
				customerContent.setDisable(true);
				refreshList();
			}
			case "cancel":
				setEditable(false);
			}
		}
		else{	
			Invoice newInvoice = new Invoice();
			controller.addorRemoveInvoice(newInvoice, false);
			itemList.getItems().add(new ListRegel(newInvoice));
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
		changeFilter(filterSelector.getSelectionModel().getSelectedIndex());
		select(selectedItem);
	}
	/**
	 * selects an item and fills in the information
	 * @param selectedValue the item to be selected
	 */
	private void select(ListRegel selectedValue){
		if(selectedValue!=null)	{
			selectedItem = selectedValue;
			selectedInvoice = selectedValue.getInvoice();
			dateContent.setValue(selectedInvoice.getInvoiceDate());
			customerContent.setValue(selectedInvoice.getCustomer());
			priceContent.setText(Double.toString(selectedInvoice.getTotalPrice()));
			isPayedContent.setSelected(selectedInvoice.isPayed());
			contentList.getItems().clear();
			for (InvoiceItem item : selectedInvoice.getItems())
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
		leftBox.setDisable(enable);
	}	
	
	/**
	 * checks if an item is selected in the list
	 * @return false if nothing is selected
	 */
	private boolean checkSelected() {
		if(selectedInvoice == null) return false;
		return true;
	}

	/**
	 * searches through all items in the list
	 * @param oldVal the previous content of the searchfield
	 * @param newVal the new content of the searchfield
	 */
//	public void search(String oldVal, String newVal) {
//		if (oldVal != null && (newVal.length() < oldVal.length())) {
//			//actor has deleted a character, so reset the search
//			itemList.getItems().clear();
//			itemList.getItems().addAll(content);
//		}
//		itemList.getItems().clear();
//		//add an item if any item that exists contains any value that has been searched for
//		for (ListRegel entry : content) {		
//			if (entry.getInvoice().getNumberPlate().contains(newVal)){
//				itemList.getItems().add(entry);
//			}
//		}
//	}
	// this represents every item in the list, it has different constructor for every filter option
	public class ListRegel extends HBox{
		private Invoice invoice;
		private Label itemDateLabel = new Label(),itemPriceLabel = new Label(),itemCustomerLabel = new Label(),itemIsPayedLabel = new Label();
		public ListRegel(Invoice invoice){
			this.invoice = invoice;
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
			itemDateLabel.setText(invoice.getInvoiceDate().toString());
			if(invoice.getCustomer()==null)itemCustomerLabel.setText("Anoniem");
			else itemCustomerLabel.setText(invoice.getCustomer().getName());
			itemPriceLabel.setText(Double.toString(invoice.getTotalPrice()));
			if(invoice.isPayed())itemIsPayedLabel.setText("Betaalt");
			else itemIsPayedLabel.setText("Openstaand");
		}
		/**
		 * @return the object this item represents
		 */
		public Invoice getInvoice(){
			return invoice;
		}
	}
}
	