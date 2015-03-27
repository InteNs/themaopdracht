package screens;
import main.ProductSupplier;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StockScreen extends HBox {
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

	public StockScreen() {
		
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
								new HBox(supplierContent),
								new HBox(
										new HBox(20,
												pickSupplier,
												newSupplierButton
												),
										new HBox(
								supplierTextField))
						),
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
		setVisibility(true, false, false);
		
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
			setVisibility(true, false, false);
				addNewSupplier(false, true);
		});
		
		saveStock.setPrefSize(125, 50);
		saveStock.setOnAction(e -> {
			addNewSupplier(false, false);
			setVisibility(true, false, false);
			
		});
		
		saveSupplier.setPrefSize(125, 50);
		saveSupplier.setOnAction(e -> {
			addNewSupplier(false, false);
			setVisibility(true, false, false);
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
			setVisibility(false, true, true);	
			addNewSupplier(false, false);
			pickSupplier.setPrefWidth(widthLabels+75);
			saveStock.setDisable(false);
		});
		changeStockButton.setPrefSize(150, 50);
		changeStockButton.setOnAction(e -> {
			setVisibility(true, true, true);	
			addNewSupplier(false, false);
			saveStock.setDisable(false);
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
		
		getChildren().add(mainBox);


	}

	public String getString() {
		return b;
	}
	@SuppressWarnings("unchecked")
	private void setVisibility(boolean setDetailsVisible, boolean setTextFieldsVisible, boolean setButtonsVisible) {
		cancelStock.setVisible(setButtonsVisible);
		saveStock.setVisible(setButtonsVisible);
		saveStock.setDisable(false);
		saveSupplier.setVisible(false);
		newSupplierButton.setVisible(false);
		addNewSupplier(false, true);	
		for (Node node1 : ((VBox)stockDetails.getChildren().get(0)).getChildren()) {
			HBox box = (HBox) node1;
			if(box.getChildren().size()>2){
				Node input = box.getChildren().get(2);				//WERKT NIET HELP!!!!!!!!!!
				Label content = ((Label)box.getChildren().get(1)); 	//WERKT NIET HELP!!!!!!!!!!
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
					newSupplierButton.setVisible(true);
				}
			}
		}	
	}
	public void addNewSupplier(boolean add, boolean cancel) {
		nameTextField.setDisable(add);
		amountTextField.setDisable(add);
		minAmountTextField.setDisable(add);
		sellPriceTextField.setDisable(add);
		buyPriceTextField.setDisable(add);
		supplierTextField.setVisible(add);
		
		pickSupplier.setVisible(!cancel);
		newSupplierButton.setVisible(!cancel);
		saveSupplier.setVisible(add);
		saveStock.setDisable(true);
		
		
		
		if (add) {
			pickSupplier.setPrefWidth(0);
			newSupplierButton.setPrefWidth(0);
			supplierTextField.setPrefWidth(widthLabels*2);
		} else {
			if (cancel){
				pickSupplier.setPrefWidth(0);
				newSupplierButton.setPrefWidth(0);
				supplierTextField.setPrefWidth(0);
		} else {
			pickSupplier.setPrefWidth(widthLabels);
			newSupplierButton.setPrefWidth(25);
		}
		}
	}
}
	