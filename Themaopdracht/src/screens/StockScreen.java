package screens;
import java.util.ArrayList;
import java.util.Map.Entry;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.ATDProgram;
import main.Fuel;
import main.Part;
import main.Product;
import main.ProductSupplier;
import notifications.Notification;

public class StockScreen extends HBox {
	private final ATDProgram controller;
	private final ComboBox<ProductSupplier> supplierContent = new ComboBox<ProductSupplier>();
	private final ComboBox<String> filterSelector 			= new ComboBox<String>();
	private final ArrayList<ListItem> content 				= new ArrayList<ListItem>();
	private final ListView<ListItem> itemList 				= new ListView<ListItem>();
	private Product selectedObject;
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
	newSupButton	= new Button("Nieuwe supplier"),
	changeButton 	= new Button("Aanpassen"), 
	removeButton 	= new Button("Verwijderen"), 
	cancelButton 	= new Button("Annuleren"),
	saveButton 		= new Button("Opslaan"),
	proceedButton 	= new Button("Opboeken"),
	newRuleButton 	= new Button("Regel toevoegen");
	private final Label 
	nameLabel 		= new Label("Naam: "),
	amountLabel 	= new Label("Aantal: "), 
	minAmountLabel 	= new Label("Min. aantal: "),
	priceLabel 		= new Label("Prijs: "), 
	buyPriceLabel 	= new Label("Inkoopprijs: "), 
	supplierLabel	= new Label("Leverancier: "), 
	addressLabel 	= new Label("Adres: "), 
	postalLabel 	= new Label("Postcode: "), 
	placeLabel 		= new Label("Plaats: ");	
	private final TextField 
	searchContent 	= new TextField("Zoek..."), 
	nameContent 	= new TextField(), 
	amountContent 	= new TextField(),
	minAmountContent= new TextField(), 
	priceContent 	= new TextField(), 
	buyPriceContent = new TextField(), 
	addressContent 	= new TextField(),
	postalContent 	= new TextField(),
	placeContent 	= new TextField();
	private final VBox
	leftBox 		= new VBox(space_Big),
	rightBox 		= new VBox(space_Big);
	private final HBox 
	detailsBox 		= new HBox(space_Small), 
	control_MainBox = new HBox(space_3), 
	control_secBox 	= new HBox(space_Small), 
	mainBox 		= new HBox(space_Small);
	public StockScreen(ATDProgram controller) {
		this.controller = controller;
		//put everything in the right places
		control_MainBox.getChildren().addAll(newButton		, changeButton		,removeButton);
		control_secBox.getChildren().addAll	(searchContent	, filterSelector	,newSupButton);
		leftBox.getChildren().addAll 		(itemList		, control_secBox);
		rightBox.getChildren().addAll		(detailsBox		, control_MainBox);
		mainBox.getChildren().addAll 		(leftBox		, rightBox);
		this.getChildren().add				(mainBox);
		detailsBox.getChildren().addAll(
				new VBox(space_Big,
						new HBox(space_Big,nameLabel	 , nameContent),
						new HBox(space_Big,amountLabel	 , amountContent),
						new HBox(space_Big,minAmountLabel, minAmountContent),
						new HBox(space_Big,priceLabel	 , priceContent),
						new HBox(space_Big,buyPriceLabel , buyPriceContent),
						new HBox(space_Big,supplierLabel , supplierContent),
						new HBox(space_Big,addressLabel	 , addressContent),
						new HBox(space_Big,postalLabel	 , postalContent),
						new HBox(space_Big,placeLabel	 , placeContent),	
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
		supplierContent.setMinWidth(label_Normal*1.5);		
		detailsBox.setPadding(new Insets(space_Big));
		mainBox.setPadding(new Insets(space_Big));
		detailsBox.getStyleClass().addAll("removeDisabledEffect","detailsBox");
		leftBox.getStyleClass().add("removeDisabledEffect");
		itemList.getStyleClass().add("removeDisabledEffect");
		detailsBox.setPrefSize		(450	 , 520);
		itemList.setPrefSize		(450	 , 520);
		searchContent.setPrefSize	(button_3, 50);
		filterSelector.setPrefSize	(button_3, 50);
		newSupButton.setPrefSize	(button_3, 50);
		cancelButton.setPrefSize	(180	 , 50);
		saveButton.setPrefSize		(180	 , 50);
		newButton.setPrefSize		(button_3, 50);
		changeButton.setPrefSize	(button_3, 50);
		removeButton.setPrefSize	(button_3, 50);		
		//details
		supplierContent.getItems().addAll(controller.getSuppliers());
		supplierContent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selectSupplier(newValue);			
		});
		setEditable(false);
		//Listview
		for (Product object : controller.getStock().getAllProducts()) 
			itemList.getItems().add(new ListItem(object));
		refreshList();
		itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			select(newValue);
		});
		//SearchField
		searchContent.setOnMouseClicked(e -> {
			if (searchContent.getText().equals("Zoek...")) searchContent.clear();
			else searchContent.selectAll();
		});
		searchContent.textProperty().addListener((observable, oldValue, newValue) -> {
			search(oldValue, newValue);
		});
		//Buttons and filter		
		cancelButton.setOnAction(e -> {
			clearInput();
			setEditable(false);
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
		newSupButton.setOnAction(e->{
			newSupplier();
		});
		proceedButton.setOnAction(e-> boekop());
		newRuleButton.setOnAction(e->	itemList.getItems().add(itemList.getItems().size()-1,new ListItem()));	
		filterSelector.getItems().addAll("Mode: Voorraad", "Mode: Benzine", "Mode: Onderdelen", "Mode: Bestellijst", "Mode: Opboeken");
		filterSelector.getSelectionModel().selectFirst();
		filterSelector.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			changeFilter(newValue.intValue());
		});
	}
	/**
	 * fills the list with items that fit with the given filter or mode
	 * @param newValue selected filter
	 */
	private void changeFilter(int newValue) {
		switch(newValue){
		case 0: default:{//voorraad
			itemList.getItems().clear();
			for (Product object : controller.getStock().getAllProducts())
				itemList.getItems().add(new ListItem(object));
			break;
		}
		case 1:{//Benzine
			itemList.getItems().clear();
			for (Product object : controller.getStock().getAllProducts())
				if(object instanceof Fuel)itemList.getItems().add(new ListItem(object));
			break;
		}
		case 2:{//Onderdelen
			itemList.getItems().clear();
			for (Product object : controller.getStock().getAllProducts())
				if(object instanceof Part)itemList.getItems().add(new ListItem(object));
			break;
		}
		case 3:{//Bestellijst
			itemList.getItems().clear();
			for(Entry<Object, Integer> entry : controller.getStock().getOrderedItems().entrySet()) 
				itemList.getItems().add(new ListItem((Product)entry.getKey(), entry.getValue()));
			break;
		}
		case 4:{//Opboeken
			itemList.getItems().clear();
			itemList.getItems().add(new ListItem(newRuleButton, proceedButton));
			break;
		}
		}
		if(!searchContent.getText().equals("Zoek..."))search(null, searchContent.getText());	
	}
	/**
	 * updates stock with every item in the list after removing the last item(buttons)
	 */
	private void boekop() {
		itemList.getItems().remove(itemList.getItems().size()-1);
		for (ListItem listItem : itemList.getItems())
			controller.getStock().fill(listItem.getSelected(), listItem.getAmount());
		filterSelector.getSelectionModel().selectFirst();
	}
	/*
	 * removes the selected item
	 */
	private void remove(){
		if(checkSelected()){
			Notification confirm = new Notification(controller, "Weet u zeker dat u dit product wilt verwijderen?", ATDProgram.notificationStyle.CONFIRM);
			confirm.showAndWait();
			if (confirm.getKeuze().equals("confirm")){
				itemList.getItems().remove(selectedItem);
				controller.addorRemoveproduct(selectedObject, true);
				Notification notify = new Notification(controller, "Het product is verwijderd.", ATDProgram.notificationStyle.NOTIFY);
				notify.showAndWait();
				refreshList();
			}
		}			
	}
	private void newSupplier(){
		Notification newSupplier = new Notification(controller, "", ATDProgram.notificationStyle.SUPPLIER);
		newSupplier.showAndWait();
		if(newSupplier.getKeuze().equals("confirm")){
			controller.addorRemoveSupplier((ProductSupplier)newSupplier.getSelected(), false);
			supplierContent.getItems().add((ProductSupplier)newSupplier.getSelected());
		}
	}
	/**
	 * saves the input in either a selected item or a new item
	 */
	private void save(){
		if(checkInput()){
			if(isChanging){
				Notification changeConfirm = new Notification(controller, "Weet u zeker dat u deze wijzigingen wilt doorvoeren?",ATDProgram.notificationStyle.CONFIRM);
				changeConfirm.showAndWait();
				switch (changeConfirm.getKeuze()) {
				case "confirm": {
					selectedObject.setName(nameContent.getText());
					selectedObject.setAmount(Integer.parseInt(amountContent.getText()));
					selectedObject.setMinAmount(Integer.parseInt(minAmountContent.getText()));
					selectedObject.setSellPrice(Double.parseDouble(priceContent.getText()));
					selectedObject.setSellPrice(Double.parseDouble(buyPriceContent.getText()));
					selectedObject.setSupplier(supplierContent.getValue());
					supplierContent.setDisable(true);
					refreshList();
				}
				case "cancel":
					clearInput();
					setEditable(false);
				}
			}
			else{	
				Notification getType = new Notification(controller,"", ATDProgram.notificationStyle.TYPE);
				getType.showAndWait();
				switch (getType.getKeuze()) {
				case "confirm": {
					if(getType.getSelected().equals("Benzine")) {
						Fuel newProduct = new Fuel(
								nameContent.getText(), 
								Integer.parseInt(amountContent.getText()), 
								Integer.parseInt(minAmountContent.getText()), 
								Double.parseDouble(priceContent.getText()), 
								Double.parseDouble(buyPriceContent.getText()), 
								supplierContent.getValue());	
						controller.addorRemoveproduct(newProduct, false);
						itemList.getItems().add(new ListItem(newProduct));
					}
					if(getType.getSelected().equals("Onderdeel")) {
						Part newProduct = new Part(
								nameContent.getText(), 
								Integer.parseInt(amountContent.getText()), 
								Integer.parseInt(minAmountContent.getText()), 
								Double.parseDouble(priceContent.getText()), 
								Double.parseDouble(buyPriceContent.getText()), 
								supplierContent.getValue());	
						controller.addorRemoveproduct(newProduct, false);
						itemList.getItems().add(new ListItem(newProduct));
					}
					setEditable(false);
				}
				case "cancel":	{
					clearInput();
					setEditable(false);
				}
				}
			}
		}
		else{
			Notification notFilled = new Notification(controller, "Niet alle velden zijn juist ingevuld!",ATDProgram.notificationStyle.NOTIFY);
			notFilled.showAndWait();
		}
	}
	/**
	 * refreshes the list and every item 
	 */
	private void refreshList(){
		content.clear();
		content.addAll(itemList.getItems());
		itemList.getItems().clear();
		itemList.getItems().addAll(content);
		for (ListItem listItem : itemList.getItems())
			listItem.refresh();
		select(selectedItem);
		controller.getStock().checkStock();
	}
	/**
	 * 	fill in supplier details in corresponding textfields
	 * @param newValue the selected supplier
	 */
	private void selectSupplier(ProductSupplier newValue){
		if(newValue != null){
			ProductSupplier supplier = supplierContent.getSelectionModel().getSelectedItem();
			addressContent.setDisable(true);
			addressContent.setText(supplier.getAdress());
			postalContent.setDisable(true);
			postalContent.setText(supplier.getPostal());
			placeContent.setDisable(true);
			placeContent.setText(supplier.getPlace());
		}
	}
	/**
	 * selects an item
	 * @param selectedValue the item to be selected
	 */
	private void select(ListItem selectedValue){
		//		if(filterSelector.getSelectionModel().getSelectedIndex() != 3){
		if(selectedValue!=null)	{
			selectedItem = selectedValue;
			selectedObject = selectedValue.getProduct();
			nameContent.setText(selectedObject.getName());
			amountContent.setText(Integer.toString(selectedObject.getAmount()));
			minAmountContent.setText(Integer.toString(selectedObject.getMinAmount()));
			priceContent.setText(Double.toString(selectedObject.getSellPrice()));
			buyPriceContent.setText(Double.toString(selectedObject.getBuyPrice()));
			supplierContent.setValue(selectedObject.getSupplier());
		}
		//		}
	}
	/**
	 * disables/enables the left or right side of the stage
	 * @param enable
	 */
	private void setEditable(boolean enable){
		cancelButton.setVisible(enable);
		saveButton.setVisible(enable);
		detailsBox.setDisable(!enable);
		amountContent.setDisable(true);
		control_MainBox.setDisable(enable);
		leftBox.setDisable(enable);
	}	
	/**
	 * clears all the inputfields
	 */
	private void clearInput(){
		for (Node node1 : ((VBox)detailsBox.getChildren().get(0)).getChildren())
			if(((HBox)node1).getChildren().get(1) instanceof TextField)((TextField)((HBox)node1).getChildren().get(1)).clear();
		supplierContent.getSelectionModel().clearSelection();
	}
	/**
	 * checks if all the inputfields are filled in
	 * @return false if one of the inputs is null
	 */
	private boolean checkInput(){
		for (Node node1 : ((VBox)detailsBox.getChildren().get(0)).getChildren())
			if(((HBox)node1).getChildren().get(1) instanceof TextField)
				if(((TextField)((HBox)node1).getChildren().get(1)).getText().isEmpty())
					return false;
		try {
			Integer.parseInt(amountContent.getText());
			Integer.parseInt(minAmountContent.getText());
			Double.parseDouble(priceContent.getText());
			Double.parseDouble(buyPriceContent.getText());
		} catch (NumberFormatException e) {
			return false;
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
			if (entry.getProduct().getName().contains(newVal)
					|| Double.toString(entry.getProduct().getSellPrice()).contains(newVal)
					|| entry.getProduct().getSupplier().getName().contains(newVal)) {
				itemList.getItems().add(entry);
			}
		}
	}
	// this represents every item in the list, it has different constructor for every filter option
	public class ListItem extends HBox{
		private Product object;
		private ComboBox<Product> productSelector = new ComboBox<Product>();
		private TextField input = new TextField();
		private Label 
				itemPriceLabel	 = new Label(),
				itemNameLabel	 = new Label(),
				itemSupplierLabel= new Label(),
				itemAmountLabel  = new Label(),
				itemOrderedLabel = new Label();
		public ListItem(Product object){
			//no filter
			this.object = object;
			refresh();
			setSpacing(5);
			getChildren().addAll(
					itemNameLabel,
					new Separator(Orientation.VERTICAL),
					itemPriceLabel,
					new Separator(Orientation.VERTICAL),
					itemSupplierLabel,
					new Separator(Orientation.VERTICAL),
					itemAmountLabel);
			for (Node node : getChildren()) 
				if(node instanceof Label)((Label)node).setPrefWidth(100);
		}
		public ListItem(Product object, int amount){
			//bestellijst filter
			this.object = object;
			itemOrderedLabel.setText(Integer.toString(amount));
			refresh();
			setSpacing(5);
			getChildren().addAll(
					itemNameLabel,
					new Separator(Orientation.VERTICAL),
					itemOrderedLabel,
					new Separator(Orientation.VERTICAL),
					itemSupplierLabel);
			for (Node node : getChildren())
				if(node instanceof Label)((Label)node).setPrefWidth(100);
		}
		public ListItem(){
			//opboeken filter
			productSelector.getItems().addAll(controller.getProducts());
			getChildren().addAll(productSelector, input);
		}
		public ListItem(Button b1,Button b2){
			setSpacing(5);
			getChildren().addAll(b1,b2);	
		}
		/**
		 * fills in all the labels with the latest values
		 */
		public void refresh(){
			itemNameLabel.setText(object.getName());
			itemPriceLabel.setText(Double.toString(object.getSellPrice()));
			itemSupplierLabel.setText(object.getSupplier().getName());
			itemAmountLabel.setText(Integer.toString(object.getAmount()));
		}
		/**
		 * @return the selected item	(opboeken)
		 */
		public Product getSelected(){
			return productSelector.getSelectionModel().getSelectedItem();
		}
		/**
		 * @return the filled in amount (opboeken)
		 */
		public int getAmount(){
			return Integer.parseInt(input.getText());
		}
		/**
		 * @return the object this item represents
		 */
		public Product getProduct(){
			return object;
		}
	}
}
