package screens;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Product;
import main.ProductSupplier;

public class StockScreen extends HBox {
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
	private ListView<Product> 
			listView = new ListView<Product>();
	private VBox
			leftBox = new VBox(20),
			rightBox = new VBox(20);
	private HBox 
			StockDetails = new HBox(spacingBoxes), 
			mainButtonBox = new HBox(spacingBoxes), 
			searchFieldBox = new HBox(spacingBoxes), 
			mainBox = new HBox(spacingBoxes);
	public StockScreen() {
		
		supplierSelector.getItems().addAll(new ProductSupplier(null, null, null, null, null), new ProductSupplier("rest", "54854", "DIEEW", "3435gf", "94545"));
		supplierSelector.getSelectionModel().selectedItemProperty().addListener(e->{
			
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
			if(isChanging){
				setVisibility(true, false, false);
			}
			else{
				
				setVisibility(true, false, false);
			}
		});
		
		listView.setPrefSize(450, 520);
		listView.getSelectionModel().selectedItemProperty().addListener(e->{
			
		});
		//SearchField
		searchFieldBox = new HBox(searchInput = new TextField("Zoek..."));
		searchInput.setPrefSize(470, 50);
		
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
		});
		removeButton.setPrefSize(150, 50);
		removeButton.setOnAction(e->{
			listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
		});
		//Make & merge left & right
		leftBox.getChildren().addAll (listView,searchFieldBox,mainButtonBox);
		rightBox.getChildren().addAll(StockDetails);
		mainBox.getChildren().addAll (leftBox,rightBox);
		mainBox.setSpacing(20);
		mainBox.setPadding(new Insets(20));
		this.getChildren().add(mainBox);
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
}
	