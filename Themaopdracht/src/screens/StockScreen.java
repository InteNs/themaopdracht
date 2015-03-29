package screens;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

import sun.security.jca.GetInstance.Instance;
import notifications.Notification;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.ATDProgram;
import main.Customer;
import main.Product;
import main.ProductSupplier;
import main.Stock;

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
	private ArrayList<Product> content = new ArrayList<Product>();
	private ListView<Product> 
			listView = new ListView<Product>();
	private ListView<HBox>
			boxView = new ListView<HBox>();
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
		listView.setPrefSize(450, 520);
		listView.getItems().addAll(controller.getProducts());
		refreshList(listView, null);
		listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue!=null)selectedProduct = newValue;
			else selectedProduct = oldValue;
			selectedListEntry();
		});
		//orderView
		boxView.setPrefSize(450, 0);
		boxView.setVisible(false);
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
			Product p = listView.getSelectionModel().getSelectedItem();
			listView.getItems().remove(p);
			controller.addorRemoveproduct(p, true);
			Notification removeNotify = new Notification(controller.getStage(), "Het product is verwijderd.", ATDProgram.notificationStyle.NOTIFY);
			removeNotify.showAndWait();}
			
		});
		filterSelector.setPrefSize(150, 50);
		filterSelector.getItems().addAll("Mode: Voorraad", "Mode: Bestellijst", "Mode: Opboeken");
		filterSelector.getSelectionModel().selectFirst();
		filterSelector.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			if(newValue.intValue()==0){
				listView.setVisible(true);
				listView.setPrefSize(450, 520);
				boxView.setVisible(false);
				boxView.setPrefSize(450, 0);
			}
			if(newValue.intValue()==1){
				listView.setVisible(false);
				listView.setPrefSize(450, 0);
				boxView.setVisible(true);
				boxView.setPrefSize(450, 520);
				//TODO maak orderregel aan voor iedere key in controller.getstock.getordereditems 
			
				
			}
			if(newValue.intValue()==2){
				listView.setVisible(false);
				listView.setPrefSize(450, 0);
				boxView.setVisible(true);
				boxView.setPrefSize(450, 520);
				
			}
			System.out.println(content);
//			refreshList(listView,content);
//			refreshList(orderView,content);
			System.out.println(listView.getItems());
			//if (!searchInput.getText().equals("Zoek..."))search(null, searchInput.getText());
		});
		//Make & merge left & right
		leftBox.getChildren().addAll (listView, boxView, searchFieldBox,mainButtonBox);
		rightBox.getChildren().addAll(StockDetails);
		mainBox.getChildren().addAll (leftBox,rightBox);
		mainBox.setSpacing(20);
		mainBox.setPadding(new Insets(20));
		this.getChildren().add(mainBox);
	}
	public class OrderRegel extends HBox{
		private Label nameL, amountL, suppliernameL;
		public OrderRegel(Product product, int amount){
			nameL.setText(product.getName());
			amountL.setText(""+amount);
			suppliernameL.setText(product.getSupplier().getName());
			
			getChildren().addAll(nameL,amountL,suppliernameL);
		}
	}
	public class OpboekRegel extends HBox{
		private ComboBox<Product> productSelector = new ComboBox<Product>();
		private TextField amount = new TextField();
		public OpboekRegel(){
			productSelector.getItems().addAll(controller.getProducts());
			getChildren().addAll(productSelector, amount);
		}
		public Product getSelected(){
			return productSelector.getSelectionModel().getSelectedItem();
		}
		public int getAmount(){
			return Integer.parseInt(amount.getText());
		}
	}
	private void change(){
		nameInput.setText(selectedProduct.getName());
		amountInput.setText(Integer.toString(selectedProduct.getAmount()));
		minAmountInput.setText(Integer.toString(selectedProduct.getMinAmount()));
		priceInput.setText(Double.toString(selectedProduct.getSellPrice()));
		buyPriceInput.setText(Double.toString(selectedProduct.getBuyPrice()));
		supplierInput.setText(selectedProduct.getSupplier().getName());
		addressInput.setText(selectedProduct.getSupplier().getAdress());
		postalInput.setText(selectedProduct.getSupplier().getPostal());
		placeInput.setText(selectedProduct.getSupplier().getPlace());
		setVisibility(true, true, true);
		isChanging = true;
	}
	
	private void save(){
		if(isChanging){
			selectedProduct.setName(nameInput.getText());
			selectedProduct.setAmount(Integer.parseInt(amountInput.getText()));
			selectedProduct.setMinAmount(Integer.parseInt(minAmountInput.getText()));
			selectedProduct.setSellPrice(Double.parseDouble(priceInput.getText()));
			selectedProduct.setSellPrice(Double.parseDouble(buyPriceInput.getText()));
			selectedProduct.setSupplier(readSupplier());
			
			refreshList(listView, null);
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
			listView.getItems().add(newProduct);
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
			selectedProduct.setSupplier(newProductSupplier);
			return newProductSupplier;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void refreshList(ListView list,ArrayList<Product> newContent){
		if(newContent == null){
			content.clear();
			content.addAll(list.getItems());
			list.getItems().clear();
			list.getItems().addAll(content);
		}
		else{
			content.clear();
			content.addAll(newContent);
			list.getItems().clear();
			list.getItems().addAll(content);
		}
	}
	
	private void selectedListEntry(){
		if(selectedProduct.getName()!=null)nameContent.setText(selectedProduct.getName());
		if(Integer.toString(selectedProduct.getAmount())!=null)amountContent.setText(Integer.toString(selectedProduct.getAmount()));
		if(Integer.toString(selectedProduct.getMinAmount())!=null)minAmountContent.setText(Integer.toString(selectedProduct.getMinAmount()));
		if(Double.toString(selectedProduct.getSellPrice())!=null) priceContent.setText("€ " + Double.toString(selectedProduct.getSellPrice()));
		if(Double.toString(selectedProduct.getBuyPrice())!=null)buyPriceContent.setText("€ " + Double.toString(selectedProduct.getBuyPrice()));
		if(selectedProduct.getSupplier().getName()!=null)supplierContent.setText(selectedProduct.getSupplier().getName());
		if(selectedProduct.getSupplier().getAdress()!=null)addressContent.setText(selectedProduct.getSupplier().getAdress());
		if(selectedProduct.getSupplier().getPostal()!=null)postalContent.setText(selectedProduct.getSupplier().getPostal());
		if(selectedProduct.getSupplier().getPlace()!=null)placeContent.setText(selectedProduct.getSupplier().getPlace());
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
			listView.getItems().clear();
			listView.getItems().addAll(content);
		}
		listView.getItems().clear();
		for (Product entry : content) {
			if (entry.getName().contains(newVal)
					|| Double.toString(entry.getSellPrice()).contains(newVal)
					|| entry.getSupplier().getName().contains(newVal)) {
				listView.getItems().add(entry);
			}
		}
	}
}
	