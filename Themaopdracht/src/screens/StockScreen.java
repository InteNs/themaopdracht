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
import main.Product;
import main.ProductSupplier;
import notifications.Notification;

public class StockScreen extends HBox {
	private ATDProgram controller;
	private ListRegel selectedProduct;
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
			nameContent = new Label("-"), 
			amount = new Label("Aantal: "), 
			amountContent = new Label("-"),
			minAmount = new Label("Min. aantal: "),
			minAmountContent = new Label("-"),
			price = new Label("Prijs: "), 
			priceContent = new Label("-"),
			buyPrice = new Label("Inkoopprijs: "), 
			buyPriceContent = new Label("-"),
			supplier = new Label("Leverancier: "), 
			supplierContent = new Label("-"), 
			address = new Label("Adres: "), 
			addressContent = new Label("-"), 
			postal = new Label("Postcode: "), 
			postalContent = new Label("-"), 
			place = new Label("Plaats: "), 
			placeContent = new Label("-");
	
	private TextField 
			searchInput = new TextField(), 
			nameInput = new TextField(), 
			amountInput = new TextField(),
			minAmountInput = new TextField(), 
			priceInput = new TextField(), 
			buyPriceInput = new TextField(), 
			supplierInput = new TextField(),
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
		
		supplierSelector.getItems().addAll(controller.getSuppliers());
		supplierSelector.setEditable(true);
		supplierSelector.getSelectionModel().selectedItemProperty().addListener(e->{
			if(supplierSelector.getSelectionModel().getSelectedItem() instanceof ProductSupplier){
				ProductSupplier supplier = supplierSelector.getSelectionModel().getSelectedItem();
				addressInput.setDisable(true);
				addressInput.setText(supplier.getAdress());
				postalInput.setDisable(true);
				postalInput.setText(supplier.getPostal());
				placeInput.setDisable(true);
				placeInput.setText(supplier.getPlace());
			}else{
				addressInput.setDisable(false);
				addressInput.clear();
				postalInput.setDisable(false);
				postalInput.clear();
				placeInput.setDisable(false);
				placeInput.clear();
			}
		});
		//StockDetails
		StockDetails.getChildren().addAll(
				new VBox(20,
						new HBox(20,name,		nameContent,		nameInput),
						new HBox(20,amount,		amountContent,		amountInput),
						new HBox(20,minAmount,	minAmountContent,	minAmountInput),
						new HBox(20,price,		priceContent,		priceInput),
						new HBox(20,buyPrice,	buyPriceContent,	buyPriceInput),
						new HBox(20,supplier,	supplierContent,	supplierSelector),
						new HBox(20,address,	addressContent,		addressInput),
						new HBox(20,postal,		postalContent,		postalInput),
						new HBox(20,place,		placeContent,		placeInput),	
						new HBox(20,cancelButton,saveButton)
						));
		StockDetails.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border: solid;");
		StockDetails.setPrefSize(450, 520-15);
		StockDetails.setPadding(new Insets(20));
		setVisibility(true, false, false);
		for (Node node1 : ((VBox)StockDetails.getChildren().get(0)).getChildren()) {
			if(((HBox)node1).getChildren().size()>2)((Label)((HBox)node1).getChildren().get(0)).setMinWidth(widthLabels);
		}
		
		cancelButton.setPrefSize(150, 50);
		cancelButton.setOnAction(e -> {
				setVisibility(true, false, false);
		});
		
		saveButton.setPrefSize(150, 50);
		saveButton.setOnAction(e -> {
			Notification changeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u deze wijzigingen wilt doorvoeren?", ATDProgram.notificationStyle.CONFIRM);
			changeConfirm.showAndWait();
			save();
			Notification changeNotify = new Notification(controller.getStage(), "Wijzigingen zijn doorgevoerd.", ATDProgram.notificationStyle.NOTIFY);
			changeNotify.showAndWait();
			if(isChanging){
				setVisibility(true, false, false);
			}
			else{
				
				setVisibility(true, false, false);
			}
		});
		//Listview
		boxView.setPrefSize(450, 520);
		for (Product product : controller.getStock().getAllProducts()) 
			boxView.getItems().add(new ListRegel(product));
		refreshList(null);
		boxView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue!=null){
				if(newValue instanceof ListRegel) selectedProduct = newValue;
			}
			else{
				if(newValue instanceof ListRegel) selectedProduct = oldValue;
			}
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
		//Buttons Add, Change & Remove
		mainButtonBox.getChildren().addAll(
				newButton,
				changeButton,
				removeButton
				);
		newButton.setPrefSize(150, 50);
		newButton.setOnAction(e -> {
			setVisibility(false, true, true);
			isChanging = false;
		});
		changeButton.setPrefSize(150, 50);
		changeButton.setOnAction(e -> {
			setVisibility(true, true, true);	
			isChanging = true;
			change();
		});
		removeButton.setPrefSize(150, 50);
		removeButton.setOnAction(e->{
			Notification removeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u dit product wilt verwijderen?", ATDProgram.notificationStyle.CONFIRM);
			removeConfirm.showAndWait();
			if (removeConfirm.getKeuze() == "ja"){
			boxView.getItems().remove(selectedProduct);
			controller.addorRemoveproduct(selectedProduct.getProduct(), true);
			Notification removeNotify = new Notification(controller.getStage(), "Het product is verwijderd.", ATDProgram.notificationStyle.NOTIFY);
			removeNotify.showAndWait();}
			
		});
		filterSelector.setPrefSize(150, 50);
		filterSelector.getItems().addAll("Mode: Voorraad", "Mode: Bestellijst", "Mode: Opboeken");
		filterSelector.getSelectionModel().selectFirst();
		filterSelector.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
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

				
			}



			//if (!searchInput.getText().equals("Zoek..."))search(null, searchInput.getText());
		});
		//Make & merge left & right
		leftBox.getChildren().addAll (boxView, searchFieldBox,mainButtonBox);
		rightBox.getChildren().addAll(StockDetails);
		mainBox.getChildren().addAll (leftBox,rightBox);
		mainBox.setSpacing(20);
		mainBox.setPadding(new Insets(20));
		this.getChildren().add(mainBox);
	}
	public class ListRegel extends HBox{
		private Product product;
		private ComboBox<Product> productSelector = new ComboBox<Product>();
		private TextField amount = new TextField();
		private Label itemPriceLabel = new Label(),itemNameLabel = new Label(),itemSupplierLabel = new Label();
		public ListRegel(Product product){
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
			productSelector.getItems().addAll(controller.getProducts());
			getChildren().addAll(productSelector, amount);
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
			return Integer.parseInt(amount.getText());
		}
		public Product getProduct(){
			return product;
		}
	}

	private void change(){
		nameInput.setText(selectedProduct.getProduct().getName());
		amountInput.setText(Integer.toString(selectedProduct.getProduct().getAmount()));
		minAmountInput.setText(Integer.toString(selectedProduct.getProduct().getMinAmount()));
		priceInput.setText(Double.toString(selectedProduct.getProduct().getSellPrice()));
		buyPriceInput.setText(Double.toString(selectedProduct.getProduct().getBuyPrice()));
		supplierSelector.setValue(selectedProduct.getProduct().getSupplier());
		addressInput.setText(selectedProduct.getProduct().getSupplier().getAdress());
		postalInput.setText(selectedProduct.getProduct().getSupplier().getPostal());
		placeInput.setText(selectedProduct.getProduct().getSupplier().getPlace());
		setVisibility(true, true, true);
		isChanging = true;
	}
	
	private void save(){
		if(isChanging){
			selectedProduct.getProduct().setName(nameInput.getText());
			selectedProduct.getProduct().setAmount(Integer.parseInt(amountInput.getText()));
			selectedProduct.getProduct().setMinAmount(Integer.parseInt(minAmountInput.getText()));
			selectedProduct.getProduct().setSellPrice(Double.parseDouble(priceInput.getText()));
			selectedProduct.getProduct().setSellPrice(Double.parseDouble(buyPriceInput.getText()));
			selectedProduct.getProduct().setSupplier(readSupplier());
			
			refreshList(null);
			setVisibility(true, false, false);
		}
		else {
			Product newProduct = new Product(
					nameInput.getText(), 
					Integer.parseInt(amountInput.getText()), 
					Integer.parseInt(minAmountInput.getText()), 
					Double.parseDouble(priceInput.getText()), 
					Double.parseDouble(buyPriceInput.getText()), 
					readSupplier());	
			controller.addorRemoveproduct(newProduct, false);
			boxView.getItems().add(new ListRegel(newProduct));
			setVisibility(true, false, false);
		}
	}
	private ProductSupplier readSupplier(){
		if(supplierSelector.getSelectionModel().getSelectedItem()instanceof ProductSupplier) 
			return supplierSelector.getSelectionModel().getSelectedItem();
		else {
			ProductSupplier newProductSupplier = new ProductSupplier(
					supplierSelector.getEditor().getText(), 
					addressInput.getText(), 
					postalInput.getText(), 
					placeInput.getText());
			controller.addorRemoveSupplier(newProductSupplier, false);
			supplierSelector.getItems().add(newProductSupplier);
			selectedProduct.getProduct().setSupplier(newProductSupplier);
			return newProductSupplier;
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
			if(selectedProduct.getProduct().getName()!=null)nameContent.setText(selectedProduct.getProduct().getName());
			if(Integer.toString(selectedProduct.getProduct().getAmount())!=null)amountContent.setText(Integer.toString(selectedProduct.getProduct().getAmount()));
			if(Integer.toString(selectedProduct.getProduct().getMinAmount())!=null)minAmountContent.setText(Integer.toString(selectedProduct.getProduct().getMinAmount()));
			if(Double.toString(selectedProduct.getProduct().getSellPrice())!=null) priceContent.setText("€ " + Double.toString(selectedProduct.getProduct().getSellPrice()));
			if(Double.toString(selectedProduct.getProduct().getBuyPrice())!=null)buyPriceContent.setText("€ " + Double.toString(selectedProduct.getProduct().getBuyPrice()));
			if(selectedProduct.getProduct().getSupplier().getName()!=null)supplierContent.setText(selectedProduct.getProduct().getSupplier().getName());
			if(selectedProduct.getProduct().getSupplier().getAdress()!=null)addressContent.setText(selectedProduct.getProduct().getSupplier().getAdress());
			if(selectedProduct.getProduct().getSupplier().getPostal()!=null)postalContent.setText(selectedProduct.getProduct().getSupplier().getPostal());
			if(selectedProduct.getProduct().getSupplier().getPlace()!=null)placeContent.setText(selectedProduct.getProduct().getSupplier().getPlace());
		}
	}
	@SuppressWarnings("unchecked")
	private void setVisibility(boolean setDetailsVisible, boolean setTextFieldsVisible, boolean setButtonsVisible) {
		cancelButton.setVisible(setButtonsVisible);
		saveButton.setVisible(setButtonsVisible);	
		for (Node node1 : ((VBox)StockDetails.getChildren().get(0)).getChildren()) {
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
					if(input instanceof ComboBox){
						((ComboBox<ProductSupplier>)input).setPrefWidth(0);
						((ComboBox<ProductSupplier>)input).setValue(null);
					}

					content.setPrefWidth(widthLabels*2);
					supplier.setMinWidth(widthLabels);
				}
				else if (!setDetailsVisible) {
					if(input instanceof TextField)	((TextField)input).setPrefWidth(widthLabels*2);
					if(input instanceof ComboBox)	((ComboBox<ProductSupplier>)input).setPrefWidth(widthLabels*2);
					content.setPrefWidth(0);
					supplier.setMinWidth(widthLabels-5);
				}			
				else if (setDetailsVisible || setTextFieldsVisible) {
					if(input instanceof TextField)	((TextField)input).setPrefWidth(widthLabels);
					if(input instanceof ComboBox)	((ComboBox<ProductSupplier>)input).setPrefWidth(widthLabels);
					content.setPrefWidth(widthLabels);
					supplier.setMinWidth(widthLabels);
				}
			}
		}
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
}
	