package Screens.copy;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StockScreen extends Application {
	private String 
			b;
	private double
			spacingBoxes = 10,
			widthLabels = 120;
	private Button 
			newSupplierButton = new Button("+"),
			newStockButton = new Button("Nieuw"), 
			changeStockButton = new Button("Aanpassen"), 
			removeStockButton = new Button("Verwijderen"), 
			cancelStock = new Button("Annuleren"),
			saveStock = new Button("  Artikel\nOpslaan"),
			saveSupplier = new Button("Leverancier\n   Opslaan");
	private Label 
			name = new Label("Naam: "),
			nameContent = new Label("-"), 
			amount = new Label("Voorraad: "), 
			amountContent = new Label("-"),
			minAmount = new Label("Min. voorraad: "),
			minAmountContent = new Label("-"),
			sellPrice = new Label("Prijs: "), 
			sellPriceContent = new Label("-"),
			buyPrice = new Label("Inkoop prijs: "), 
			buyPriceContent = new Label("-"),
			supplier = new Label("Leverancier: "), 
			supplierContent = new Label("jorritmeulenbeld@icloud.com"), 
			address = new Label("Adres: "), 
			addressContent = new Label("-"), 
			postal = new Label("Postcode: "), 
			postalContent = new Label("-"), 
			place = new Label("Plaats: "), 
			placeContent = new Label("-");
	private TextField 
			searchTextField = new TextField(), 
			nameTextField = new TextField(), 
			amountTextField = new TextField(),
			minAmountTextField = new TextField(), 
			sellPriceTextField = new TextField(),
			buyPriceTextField = new TextField(),
			supplierTextField = new TextField(), 
			addressTextField = new TextField(),
			postalTextField = new TextField(),
			placeTextField = new TextField();
	private ListView 
			stocks = new ListView<>();
	private ComboBox
			pickSupplier = new ComboBox<>();
	private VBox
			leftBox = new VBox(20),
			rightBox = new VBox(20);
	private HBox 
			stockDetails = new HBox(spacingBoxes), 
			mainButtonBox = new HBox(spacingBoxes), 
			searchFieldBox = new HBox(spacingBoxes), 
			mainBox = new HBox(spacingBoxes);

	@Override
	public void start(Stage stage) throws Exception {
		
		//StockDetails
		stockDetails.getChildren().addAll(
				new VBox(20,
						new HBox(20,
								name,
								nameContent,
								nameTextField),
						new HBox(20,
								amount,
								amountContent,
								amountTextField),
						new HBox(20,
								minAmount,
								minAmountContent,
								minAmountTextField),
						new HBox(20,
								sellPrice,
								sellPriceContent,
								sellPriceTextField),
						new HBox(20,
								buyPrice,
								buyPriceContent,
								buyPriceTextField),
						new HBox(20,
								supplier,
								supplierContent,
								pickSupplier,
								newSupplierButton),
						new HBox(20,
								address,
								addressContent,
								addressTextField),
						new HBox(20,
								postal,
								postalContent,
								postalTextField),
						new HBox(20,
								place,
								placeContent,
								placeTextField),	
						new HBox(20,
								cancelStock,
								saveStock,
								saveSupplier)
						)
				);
		stockDetails.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border: solid;");
		stockDetails.setPrefSize(450, 520-15);
		stockDetails.setPadding(new Insets(20));
		visability(true, false, false);
		
		name.setMinWidth(widthLabels);
		amount.setMinWidth(widthLabels);
		minAmount.setMinWidth(widthLabels);
		sellPrice.setMinWidth(widthLabels);
		buyPrice.setMinWidth(widthLabels);
		supplier.setMinWidth(widthLabels);
		address.setMinWidth(widthLabels);
		postal.setMinWidth(widthLabels);
		place.setMinWidth(widthLabels);
		
		cancelStock.setPrefSize(125, 50);
		cancelStock.setOnAction(e -> {
				visability(true, false, false);
				addNewSupplier(false, true);
		});
		
		saveStock.setPrefSize(125, 50);
		saveStock.setOnAction(e -> {
				addNewSupplier(false, true);
			
		});
		
		saveSupplier.setPrefSize(125, 50);
		saveSupplier.setOnAction(e -> {
			addNewSupplier(false, false);
			visability(true, false, false);
		});
		
		newSupplierButton.setOnAction(e -> {
			addNewSupplier(true, true);
		});
		
		stocks.setPrefSize(450, 520);
		
		//SearchField
		searchFieldBox = new HBox(searchTextField = new TextField("Zoek..."));
		searchTextField.setPrefSize(470, 50);
		
		//Buttons Add, Change & Remove
		mainButtonBox.getChildren().addAll(
				newStockButton,
				changeStockButton,
				removeStockButton
				);
		newStockButton.setPrefSize(150, 50);
		newStockButton.setOnAction(e -> {
			visability(false, true, true);	
			addNewSupplier(false, false);
			pickSupplier.setPrefWidth(widthLabels+75);
		});
		changeStockButton.setPrefSize(150, 50);
		changeStockButton.setOnAction(e -> {
			visability(true, true, true);	
			addNewSupplier(false, false);
		});
		removeStockButton.setPrefSize(150, 50);
		
		//Make & merge left & right
		leftBox.getChildren().addAll(
				stocks,
				searchFieldBox,
				mainButtonBox);
		rightBox.getChildren().add(
				stockDetails);
		mainBox.getChildren().addAll(
				leftBox,
				rightBox);
		mainBox.setSpacing(20);
		mainBox.setPadding(new Insets(20));
		
		Scene mainScene = new Scene(mainBox, 1024, 655);
		stage.setScene(mainScene);
		stage.setTitle("Stock");
		stage.setResizable(false);
		stage.show();

	}

	public String getString() {
		return b;
	}
	
	public void visability(boolean setDetailsVisible, boolean setTextFieldsVisible, boolean setButtonsVisible) {
		nameTextField.setVisible(setTextFieldsVisible);
		amountTextField.setVisible(setTextFieldsVisible);
		minAmountTextField.setVisible(setTextFieldsVisible);
		sellPriceTextField.setVisible(setTextFieldsVisible);
		buyPriceTextField.setVisible(setTextFieldsVisible);
		pickSupplier.setVisible(setTextFieldsVisible);
		addressTextField.setVisible(setTextFieldsVisible);
		postalTextField.setVisible(setTextFieldsVisible);
		placeTextField.setVisible(setTextFieldsVisible);
		
		nameContent.setVisible(setDetailsVisible);
		amountContent.setVisible(setDetailsVisible);
		minAmountContent.setVisible(setDetailsVisible);
		sellPriceContent.setVisible(setDetailsVisible);
		buyPriceContent.setVisible(setDetailsVisible);
		supplierContent.setVisible(setDetailsVisible);
		addressContent.setVisible(setDetailsVisible);
		postalContent.setVisible(setDetailsVisible);
		placeContent.setVisible(setDetailsVisible);
		
		cancelStock.setVisible(setButtonsVisible);
		saveStock.setVisible(setButtonsVisible);
		saveSupplier.setVisible(false);
		newSupplierButton.setVisible(false);

		addNewSupplier(false, true);
		if (!setTextFieldsVisible) {
			nameTextField.setPrefWidth(0);
			amountTextField.setPrefWidth(0);
			minAmountTextField.setPrefWidth(0);
			sellPriceTextField.setPrefWidth(0);
			placeTextField.setPrefWidth(0);
			pickSupplier.setPrefWidth(0);
			addressTextField.setPrefWidth(0);
			postalTextField.setPrefWidth(0);
			placeTextField.setPrefWidth(0);
			
			supplier.setMinWidth(widthLabels);
			
			nameContent.setPrefWidth(widthLabels*2);
			amountContent.setPrefWidth(widthLabels*2);
			minAmountContent.setPrefWidth(widthLabels*2);
			sellPriceContent.setPrefWidth(widthLabels*2);
			buyPriceContent.setPrefWidth(widthLabels*2);
			supplierContent.setPrefWidth(widthLabels*2);
			addressContent.setPrefWidth(widthLabels*2);
			postalContent.setPrefWidth(widthLabels*2);
			placeContent.setPrefWidth(widthLabels*2);
		} else if (!setDetailsVisible) {
			nameTextField.setPrefWidth(widthLabels*2);
			amountTextField.setPrefWidth(widthLabels*2);
			minAmountTextField.setPrefWidth(widthLabels*2);
			sellPriceTextField.setPrefWidth(widthLabels*2);
			buyPriceTextField.setPrefWidth(widthLabels*2);
			pickSupplier.setPrefWidth(widthLabels*2);
			addressTextField.setPrefWidth(widthLabels*2);
			postalTextField.setPrefWidth(widthLabels*2);
			placeTextField.setPrefWidth(widthLabels*2);
			
			supplier.setMinWidth(widthLabels-5);
			
			nameContent.setPrefWidth(0);
			amountContent.setPrefWidth(0);
			minAmountContent.setPrefWidth(0);
			sellPriceContent.setPrefWidth(0);
			buyPriceContent.setPrefWidth(0);
			supplierContent.setPrefWidth(0);
			addressContent.setPrefWidth(0);
			postalContent.setPrefWidth(0);
			placeContent.setPrefWidth(0);
			
			newSupplierButton.setVisible(true);
		} else if (setDetailsVisible || setTextFieldsVisible) {
			nameTextField.setPrefWidth(widthLabels);
			amountTextField.setPrefWidth(widthLabels);
			minAmountTextField.setPrefWidth(widthLabels);
			sellPriceTextField.setPrefWidth(widthLabels);
			buyPriceTextField.setPrefWidth(widthLabels);
			pickSupplier.setPrefWidth(widthLabels-35);
			addressTextField.setPrefWidth(widthLabels);
			postalTextField.setPrefWidth(widthLabels);
			placeTextField.setPrefWidth(widthLabels);
			
			supplier.setMinWidth(widthLabels);
			
			nameContent.setPrefWidth(widthLabels);
			amountContent.setPrefWidth(widthLabels);
			minAmountContent.setPrefWidth(widthLabels);
			sellPriceContent.setPrefWidth(widthLabels);
			buyPriceContent.setPrefWidth(widthLabels);
			supplierContent.setPrefWidth(widthLabels);
			addressContent.setPrefWidth(widthLabels);
			postalContent.setPrefWidth(widthLabels);
			placeContent.setPrefWidth(widthLabels);
			
			newSupplierButton.setVisible(true);
		}
	}

	public void addNewSupplier(boolean add, boolean cancel) {
		nameTextField.setDisable(add);
		amountTextField.setDisable(add);
		minAmountTextField.setDisable(add);
		sellPriceTextField.setDisable(add);
		buyPriceTextField.setDisable(add);
		
		pickSupplier.setVisible(!cancel);
		newSupplierButton.setVisible(!cancel);
		saveSupplier.setVisible(add);
		
		
		
		if (add) {
			pickSupplier.setPrefWidth(0);
			newSupplierButton.setPrefWidth(0);
		} else {
			if (cancel){
				pickSupplier.setPrefWidth(0);
				newSupplierButton.setPrefWidth(0);
				//supplierTextField.setPrefWidth(0);
		} else {
			pickSupplier.setPrefWidth(widthLabels-45);
			newSupplierButton.setPrefWidth(25);
		}
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
	