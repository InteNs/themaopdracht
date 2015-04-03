package screens;
import java.util.ArrayList;
import java.util.List;
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
	private ProductSupplier productSupplier;
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
			name = new Label("Naam: "),
			amount = new Label("Aantal: "), 
			minAmount = new Label("Min. aantal: "),
			price = new Label("Prijs: "), 
			buyPrice = new Label("Inkoopprijs: "), 
			supplier = new Label("Leverancier: "), 
			address = new Label("Adres: "), 
			postal = new Label("Postcode: "), 
			place = new Label("Plaats: ");	
	private TextField 
			searchInput = new TextField(), 
			nameInput = new TextField(), 
			amountInput = new TextField(),
			minAmountInput = new TextField(), 
			priceInput = new TextField(), 
			buyPriceInput = new TextField(), 
			addressInput = new TextField(),
			postalInput = new TextField(),
			placeInput = new TextField();
	private ComboBox<ProductSupplier> supplierSelector = new ComboBox<ProductSupplier>();
	private ComboBox<String> filterSelector = new ComboBox<String>();
	private ArrayList<ListRegel> content = new ArrayList<ListRegel>();
	private ListView<ListRegel> boxView = new ListView<ListRegel>();
	private VBox
			leftBox = new VBox(20),
			rightBox = new VBox(20);
	private HBox 
			StockDetails = new HBox(spacingBoxes), 
			mainButtonBox = new HBox(spacingBoxes), 
			searchFieldBox = new HBox(spacingBoxes), 
			mainBox = new HBox(spacingBoxes);
	public StockScreen(ATDProgram controller) {
		this.controller = controller;
		//StockDetails
		StockDetails.getChildren().addAll(
				new VBox(20,
						new HBox(20,name,			nameInput),
						new HBox(20,amount,			amountInput),
						new HBox(20,minAmount,		minAmountInput),
						new HBox(20,price,			priceInput),
						new HBox(20,buyPrice,		buyPriceInput),
						new HBox(20,supplier,		supplierSelector),
						new HBox(20,address,		addressInput),
						new HBox(20,postal,			postalInput),
						new HBox(20,place,			placeInput),	
						new HBox(20,cancelButton,	saveButton)
						));
		StockDetails.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border: solid;");
		StockDetails.setPrefSize(450, 520-15);
		StockDetails.setPadding(new Insets(20));
		setEditable(false);
		for (Node node1 : ((VBox)StockDetails.getChildren().get(0)).getChildren()) {
			if(((HBox)node1).getChildren().get(0) instanceof Label)((Label)((HBox)node1).getChildren().get(0)).setMinWidth(widthLabels);
			if(((HBox)node1).getChildren().get(1) instanceof TextField){
				((TextField)((HBox)node1).getChildren().get(1)).setMinWidth(widthLabels*1.5);
			}
		}
		supplierSelector.setStyle("-fx-opacity: 1;");
		supplierSelector.setMinWidth(widthLabels*1.5);
		supplierSelector.getItems().addAll(controller.getSuppliers());
		supplierSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null){
				ProductSupplier supplier = supplierSelector.getSelectionModel().getSelectedItem();
				System.out.println(supplier);
				addressInput.setEditable(false);
				addressInput.setText(supplier.getAdress());
				postalInput.setEditable(false);
				postalInput.setText(supplier.getPostal());
				placeInput.setEditable(false);
				placeInput.setText(supplier.getPlace());
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
		boxView.setPrefSize(450, 520);
		for (Product product : controller.getStock().getAllProducts()) 
			boxView.getItems().add(new ListRegel(product));
		refreshList(null);
		boxView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue!=null)	
				if(newValue instanceof ListRegel) selectedProduct = newValue.getProduct();
				else selectedProduct = oldValue.getProduct();
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
		//Main Button Area
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
			setEditable(true);
			isChanging = true;
		});
		removeButton.setPrefSize(150, 50);
		removeButton.setOnAction(e->{
			remove();
		});
		filterSelector.setPrefSize(150, 50);
		filterSelector.getItems().addAll("Mode: Voorraad", "Mode: Bestellijst", "Mode: Opboeken");
		filterSelector.getSelectionModel().selectFirst();
		filterSelector.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			changeFilter(newValue);
		});
		//Make & merge left & right
		leftBox.getChildren().addAll (boxView, searchFieldBox,mainButtonBox);
		rightBox.getChildren().addAll(StockDetails);
		mainBox.getChildren().addAll (leftBox,rightBox);
		mainBox.setSpacing(20);
		mainBox.setPadding(new Insets(20));
		this.getChildren().add(mainBox);
	}
	private void changeFilter(Number newValue) {
		if(newValue.intValue()==0){
			boxView.getItems().clear();
			for (Product product : controller.getStock().getAllProducts())
				boxView.getItems().add(new ListRegel(product));
		}
		if(newValue.intValue()==1){
			boxView.getItems().clear();
			for(Entry<Object, Integer> entry : controller.getStock().getOrderedItems().entrySet()) 
			    boxView.getItems().add(new ListRegel((Product)entry.getKey(), entry.getValue()));
		}
		if(newValue.intValue()==2){
			boxView.getItems().clear();
			Button proceed = new Button("wijzigingen doorvoeren");
			proceed.setOnAction(e->{
				boekop();
				filterSelector.getSelectionModel().selectFirst();
			});
			Button newRule = new Button("regel toevoegen");
			newRule.setOnAction(e->{
				boxView.getItems().remove(boxView.getItems().size()-1);
				boxView.getItems().add(new ListRegel());
				boxView.getItems().add(boxView.getItems().size(), new ListRegel(newRule, proceed));
			});
			boxView.getItems().add(boxView.getItems().size(), new ListRegel(newRule, proceed));
		}
		if(!searchInput.getText().equals("Zoek..."))search(null, searchInput.getText());
		
	}
	private void boekop() {
		boxView.getItems().remove(boxView.getItems().size()-1);
		for (ListRegel listRegel : boxView.getItems()) {
			if(listRegel.getChildren().size()==2)
				controller.getStock().fill(listRegel.getSelected(), listRegel.getAmount());
		}
		
	}
	private void remove(){
		Notification removeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u dit product wilt verwijderen?", ATDProgram.notificationStyle.CONFIRM);
		removeConfirm.showAndWait();
		if (removeConfirm.getKeuze().equals("confirm")){
		boxView.getItems().remove(selectedProduct);
		controller.addorRemoveproduct(selectedProduct, true);
		Notification removeNotify = new Notification(controller.getStage(), "Het product is verwijderd.", ATDProgram.notificationStyle.NOTIFY);
		removeNotify.showAndWait();}
	}
	private void save(){
		if(checkInput()){
			if(isChanging){
				Notification changeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u deze wijzigingen wilt doorvoeren?",ATDProgram.notificationStyle.CONFIRM);
				changeConfirm.showAndWait();
				switch (changeConfirm.getKeuze()) {
					case "confirm": {
						selectedProduct.setName(nameInput.getText());
						selectedProduct.setAmount(Integer.parseInt(amountInput.getText()));
						selectedProduct.setMinAmount(Integer.parseInt(minAmountInput.getText()));
						selectedProduct.setSellPrice(Double.parseDouble(priceInput.getText()));
						selectedProduct.setSellPrice(Double.parseDouble(buyPriceInput.getText()));
						selectedProduct.setSupplier(supplierSelector.getValue());
						supplierSelector.setDisable(true);
						refreshList(null);
					}
					case "cancel":
						clearInput();
						setEditable(false);
				}
			}
			else{	
				GetInfoNotification getsupplier = new GetInfoNotification(controller.getStage(), "selecteer het type product.",controller, ATDProgram.notificationStyle.TYPE);
				getsupplier.showAndWait();
				switch (getsupplier.getKeuze()) {
					case "confirm": {
						 if(getsupplier.getSelected().equals("Benzine")) {
							 Fuel newProduct = new Fuel(
								nameInput.getText(), 
								Integer.parseInt(amountInput.getText()), 
								Integer.parseInt(minAmountInput.getText()), 
								Double.parseDouble(priceInput.getText()), 
								Double.parseDouble(buyPriceInput.getText()), 
								supplierSelector.getValue());	
							 controller.addorRemoveproduct(newProduct, false);
							 boxView.getItems().add(new ListRegel(newProduct));
						 }
						 if(getsupplier.getSelected().equals("Onderdeel")) {
							 Part newProduct = new Part(
								nameInput.getText(), 
								Integer.parseInt(amountInput.getText()), 
								Integer.parseInt(minAmountInput.getText()), 
								Double.parseDouble(priceInput.getText()), 
								Double.parseDouble(buyPriceInput.getText()), 
								supplierSelector.getValue());	
							 controller.addorRemoveproduct(newProduct, false);
							 boxView.getItems().add(new ListRegel(newProduct));
						 }
						 setEditable(false);
					}
					case "cancel":
						clearInput();
						setEditable(false);
				}
			}
		}
		else{
			Notification notFilled = new Notification(controller.getStage(), "Niet alle velden zijn Juist ingevuld",ATDProgram.notificationStyle.NOTIFY);
			notFilled.showAndWait();
		}
	}
	private void refreshList(ArrayList<ListRegel> newContent){
		if(newContent == null){
			content.clear();
			content.addAll(boxView.getItems());
			boxView.getItems().clear();
			boxView.getItems().addAll(content);
		}
		else{
			content.clear();
			content.addAll(newContent);
			boxView.getItems().clear();
			boxView.getItems().addAll(content);
		}
		for (ListRegel listRegel : boxView.getItems()) {
			listRegel.refresh();
		}
	}
	private void selectedListEntry(){
		if(filterSelector.getSelectionModel().getSelectedIndex() != 3){
			if(selectedProduct.getName()!=null)nameInput.setText(selectedProduct.getName());
			if(Integer.toString(selectedProduct.getAmount())!=null)amountInput.setText(Integer.toString(selectedProduct.getAmount()));
			if(Integer.toString(selectedProduct.getMinAmount())!=null)minAmountInput.setText(Integer.toString(selectedProduct.getMinAmount()));
			if(Double.toString(selectedProduct.getSellPrice())!=null) priceInput.setText(Double.toString(selectedProduct.getSellPrice()));
			if(Double.toString(selectedProduct.getBuyPrice())!=null)buyPriceInput.setText(Double.toString(selectedProduct.getBuyPrice()));
			if(selectedProduct.getSupplier().getName()!=null)supplierSelector.setValue(selectedProduct.getSupplier());
		}
	}
	private void setEditable(boolean enable){
		cancelButton.setVisible(enable);
		saveButton.setVisible(enable);
		for (Node node1 : ((VBox)StockDetails.getChildren().get(0)).getChildren())
			if(((HBox)node1).getChildren().get(1) instanceof TextField)((TextField)((HBox)node1).getChildren().get(1)).setEditable(enable);
		supplierSelector.setDisable(!enable);
	}
	private void clearInput(){
		for (Node node1 : ((VBox)StockDetails.getChildren().get(0)).getChildren())
			if(((HBox)node1).getChildren().get(1) instanceof TextField)((TextField)((HBox)node1).getChildren().get(1)).clear();
		supplierSelector.getSelectionModel().clearSelection();
	}
	private boolean checkInput(){
		for (Node node1 : ((VBox)StockDetails.getChildren().get(0)).getChildren())
			if(((HBox)node1).getChildren().get(1) instanceof TextField){
				if(((TextField)((HBox)node1).getChildren().get(1)).getText().isEmpty()){
					return false;
				}
			}
		return true;
		
	}
	public void search(String oldVal, String newVal) {
		if (oldVal != null && (newVal.length() < oldVal.length())) {
			boxView.getItems().clear();
			boxView.getItems().addAll(content);
		}
		boxView.getItems().clear();
		for (ListRegel entry : content) {		
			if (entry.getProduct().getName().contains(newVal)
					|| Double.toString(entry.getProduct().getSellPrice()).contains(newVal)
					|| entry.getProduct().getSupplier().getName().contains(newVal)) {
				boxView.getItems().add(entry);
			}
		}
	}
	public class ListRegel extends HBox{
		private Product product;
		private ComboBox<Product> productSelector = new ComboBox<Product>();
		private TextField input = new TextField();
		private Label itemPriceLabel = new Label(),itemNameLabel = new Label(),itemSupplierLabel = new Label();
		public ListRegel(Product product){
			//ALLE PRODUCTEN
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
			//BESTELDE PRODUCTEN
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
			//OPBOEKEN
			productSelector.getItems().addAll(controller.getProducts());
			getChildren().addAll(productSelector, input);
		}
		public ListRegel(Button b,Button c){
			setSpacing(5);
			getChildren().addAll(
					new Label("+"),
					b,c);
			
		}
		public void refresh(){
			itemNameLabel.setText(product.getName());
			itemPriceLabel.setText(Double.toString(product.getSellPrice()));
			itemSupplierLabel.setText(product.getSupplier().getName());
		}
		public Product getSelected(){
			return productSelector.getSelectionModel().getSelectedItem();
		}
		public int getAmount(){
			return Integer.parseInt(input.getText());
		}
		public Product getProduct(){
			return product;
		}
	}
}
	
	