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
import notifications.GetInfoNotification;
import notifications.Notification;

public class StockScreen extends HBox {
	private ATDProgram controller;
	private Product selectedProduct;
	private ComboBox<ProductSupplier> supplierContent = new ComboBox<ProductSupplier>();
	private ComboBox<String> filterSelector = new ComboBox<String>();
	private ArrayList<ListRegel> content = new ArrayList<ListRegel>();
	private ListView<ListRegel> itemList = new ListView<ListRegel>();
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
			nameLabel = new Label("Naam: "),
			amountLabel = new Label("Aantal: "), 
			minAmountLabel = new Label("Min. aantal: "),
			priceLabel = new Label("Prijs: "), 
			buyPriceLabel = new Label("Inkoopprijs: "), 
			supplierLabel = new Label("Leverancier: "), 
			addressLabel = new Label("Adres: "), 
			postalLabel = new Label("Postcode: "), 
			placeLabel = new Label("Plaats: ");	
	private TextField 
			searchInput = new TextField(), 
			nameContent = new TextField(), 
			amountContent = new TextField(),
			minAmountContent = new TextField(), 
			priceContent = new TextField(), 
			buyPriceContent = new TextField(), 
			addressContent = new TextField(),
			postalContent = new TextField(),
			placeContent = new TextField();
	private VBox
			leftBox = new VBox(20),
			rightBox = new VBox(20);
	private HBox 
			stockDetails = new HBox(spacingBoxes), 
			mainButtonBox = new HBox(spacingBoxes), 
			searchFieldBox = new HBox(spacingBoxes), 
			mainBox = new HBox(spacingBoxes);
	public StockScreen(ATDProgram controller) {
		this.controller = controller;
		//StockDetails
		stockDetails.getChildren().addAll(
				new VBox(20,
						new HBox(20,nameLabel,		nameContent),
						new HBox(20,amountLabel,	amountContent),
						new HBox(20,minAmountLabel,	minAmountContent),
						new HBox(20,priceLabel,		priceContent),
						new HBox(20,buyPriceLabel,	buyPriceContent),
						new HBox(20,supplierLabel,	supplierContent),
						new HBox(20,addressLabel,	addressContent),
						new HBox(20,postalLabel,	postalContent),
						new HBox(20,placeLabel,		placeContent),	
						new HBox(20,cancelButton,	saveButton)
						));
		stockDetails.setPrefSize(555, 520);
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
		supplierContent.setMinWidth(widthLabels*1.5);
		supplierContent.getItems().addAll(controller.getSuppliers());
		supplierContent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			//fill in supplier details in corresponding textfields
			if(newValue != null){
				ProductSupplier supplier = supplierContent.getSelectionModel().getSelectedItem();
				addressContent.setEditable(false);
				addressContent.setText(supplier.getAdress());
				postalContent.setEditable(false);
				postalContent.setText(supplier.getPostal());
				placeContent.setEditable(false);
				placeContent.setText(supplier.getPlace());
			}
		});
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
		itemList.setPrefSize(450, 520);
		for (Product product : controller.getStock().getAllProducts()) 
			itemList.getItems().add(new ListRegel(product));
		refreshList();
		itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			select(newValue);
		});
		//SearchField
		searchFieldBox = new HBox(10,searchInput = new TextField("Zoek..."),filterSelector);
		searchInput.setPrefSize(310, 50);
		searchInput.setOnMouseClicked(e -> {
			if (searchInput.getText().equals("Zoek...")) {
				searchInput.clear();
			} else searchInput.selectAll();
		});
		searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
				search(oldValue, newValue);
		});
		//Main Buttons and filter
		mainButtonBox.getChildren().addAll(
				newButton,
				changeButton,
				removeButton
				);
		newButton.setPrefSize(180, 50);
		newButton.setOnAction(e -> {
			clearInput();
			setEditable(true);
			isChanging = false;
		});
		changeButton.setPrefSize(180, 50);
		changeButton.setOnAction(e -> {
			if(checkSelected()){
				setEditable(true);
				isChanging = true;
			}
		});
		removeButton.setPrefSize(180, 50);
		removeButton.setOnAction(e->{
			remove();
		});
		filterSelector.setPrefSize(150, 50);
		filterSelector.getItems().addAll("Mode: Voorraad", "Mode: Benzine", "Mode: Onderdelen", "Mode: Bestellijst", "Mode: Opboeken");
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
			for (Product product : controller.getStock().getAllProducts())
				itemList.getItems().add(new ListRegel(product));
			break;
			}
		case 1:{
			itemList.getItems().clear();
			for (Product product : controller.getStock().getAllProducts())
				if(product instanceof Fuel)itemList.getItems().add(new ListRegel(product));
			break;
		}
		case 2:{
			itemList.getItems().clear();
			for (Product product : controller.getStock().getAllProducts())
				if(product instanceof Part)itemList.getItems().add(new ListRegel(product));
			break;
		}
		case 3:{
			itemList.getItems().clear();
			for(Entry<Object, Integer> entry : controller.getStock().getOrderedItems().entrySet()) 
			    itemList.getItems().add(new ListRegel((Product)entry.getKey(), entry.getValue()));
			break;
			}
		case 4:{
			itemList.getItems().clear();
			Button proceed = new Button("Opboeken");
			Button newRule = new Button("regel toevoegen");
			proceed.setOnAction(e-> boekop());
			newRule.setOnAction(e->	itemList.getItems().add(itemList.getItems().size()-1,new ListRegel()));
			itemList.getItems().add(new ListRegel(newRule, proceed));
			break;
			}
		}
		if(!searchInput.getText().equals("Zoek..."))search(null, searchInput.getText());	
	}
	/**
	 * updates stock with every item in the list after removing the last item(buttons)
	 */
	private void boekop() {
		itemList.getItems().remove(itemList.getItems().size()-1);
		for (ListRegel listRegel : itemList.getItems())
				controller.getStock().fill(listRegel.getSelected(), listRegel.getAmount());
		filterSelector.getSelectionModel().selectFirst();
	}
	/*
	 * removes the selected item
	 */
	private void remove(){
		if(checkSelected()){
			Notification removeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u dit product wilt verwijderen?", ATDProgram.notificationStyle.CONFIRM);
			removeConfirm.showAndWait();
			if (removeConfirm.getKeuze().equals("confirm")){
				itemList.getItems().remove(selectedProduct);
				controller.addorRemoveproduct(selectedProduct, true);
				Notification removeNotify = new Notification(controller.getStage(), "Het product is verwijderd.", ATDProgram.notificationStyle.NOTIFY);
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
						selectedProduct.setName(nameContent.getText());
						selectedProduct.setAmount(Integer.parseInt(amountContent.getText()));
						selectedProduct.setMinAmount(Integer.parseInt(minAmountContent.getText()));
						selectedProduct.setSellPrice(Double.parseDouble(priceContent.getText()));
						selectedProduct.setSellPrice(Double.parseDouble(buyPriceContent.getText()));
						selectedProduct.setSupplier(supplierContent.getValue());
						supplierContent.setDisable(true);
						refreshList();
					}
					case "cancel":
						clearInput();
						setEditable(false);
				}
			}
			else{	
				GetInfoNotification getType = new GetInfoNotification(controller, ATDProgram.notificationStyle.TYPE);
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
							 itemList.getItems().add(new ListRegel(newProduct));
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
							 itemList.getItems().add(new ListRegel(newProduct));
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
		if(filterSelector.getSelectionModel().getSelectedIndex() != 3){
			if(selectedValue!=null)	{
				selectedProduct = selectedValue.getProduct();
				nameContent.setText(selectedProduct.getName());
				amountContent.setText(Integer.toString(selectedProduct.getAmount()));
				minAmountContent.setText(Integer.toString(selectedProduct.getMinAmount()));
				priceContent.setText(Double.toString(selectedProduct.getSellPrice()));
				buyPriceContent.setText(Double.toString(selectedProduct.getBuyPrice()));
				supplierContent.setValue(selectedProduct.getSupplier());
			}
		}
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
		supplierContent.setDisable(!enable);
		leftBox.setDisable(enable);
	}	
	/**
	 * clears all the inputfields
	 */
	private void clearInput(){
		for (Node node1 : ((VBox)stockDetails.getChildren().get(0)).getChildren())
			if(((HBox)node1).getChildren().get(1) instanceof TextField)((TextField)((HBox)node1).getChildren().get(1)).clear();
		supplierContent.getSelectionModel().clearSelection();
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
		if(selectedProduct == null) return false;
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
			if (entry.getProduct().getName().contains(newVal)
					|| Double.toString(entry.getProduct().getSellPrice()).contains(newVal)
					|| entry.getProduct().getSupplier().getName().contains(newVal)) {
				itemList.getItems().add(entry);
			}
		}
	}
	// this represents every item in the list, it has different constructor for every filter option
	public class ListRegel extends HBox{
		private Product product;
		private ComboBox<Product> productSelector = new ComboBox<Product>();
		private TextField input = new TextField();
		private Label itemPriceLabel = new Label(),itemNameLabel = new Label(),itemSupplierLabel = new Label();
		public ListRegel(Product product){
			//no filter
			this.product = product;
			refresh();
			setSpacing(5);
			getChildren().addAll(
				itemNameLabel,
				new Separator(Orientation.VERTICAL),
				itemPriceLabel,
				new Separator(Orientation.VERTICAL),
				itemSupplierLabel);
				for (Node node : getChildren()) 
					if(node instanceof Label)((Label)node).setPrefWidth(100);
		}
		public ListRegel(Product product, int amount){
			//bestellijst filter
			this.product = product;
			refresh();
			setSpacing(5);
			getChildren().addAll(
					itemNameLabel,
					new Separator(Orientation.VERTICAL),
					new Label(Integer.toString(amount)),
					new Separator(Orientation.VERTICAL),
					itemSupplierLabel);
			for (Node node : getChildren())
				if(node instanceof Label)((Label)node).setPrefWidth(100);
		}
		public ListRegel(){
			//opboeken filter
			productSelector.getItems().addAll(controller.getProducts());
			getChildren().addAll(productSelector, input);
		}
		public ListRegel(Button b1,Button b2){
			setSpacing(5);
			getChildren().addAll(b1,b2);	
		}
		/**
		 * fills in all the labels with the latest values
		 */
		public void refresh(){
			itemNameLabel.setText(product.getName());
			itemPriceLabel.setText(Double.toString(product.getSellPrice()));
			itemSupplierLabel.setText(product.getSupplier().getName());
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
			return product;
		}
	}
}
	