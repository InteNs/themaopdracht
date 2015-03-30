package notifications;

import java.util.ArrayList;

import main.ATDProgram;
import main.Product;
import main.ProductSupplier;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddProductNotification extends Stage {
	private ComboBox<Product> productSelector = new ComboBox<Product>();
	private Button annuleren, ok;
	private Label melding;
	private String keuze;
	private VBox notification;
	private ATDProgram controller;
	
	public AddProductNotification(Stage currentStage, String bericht, ATDProgram controller){
		super(StageStyle.UTILITY);
		initOwner(currentStage); 
		initModality(Modality.WINDOW_MODAL); 
		this.controller = controller;
		this.setResizable(false);
		melding = new Label(bericht);
		
		productSelector.getItems().addAll(controller.getProducts());
		
		this.setTitle("Product toevoegen aan klus.");
		notification = 	new VBox(10,
						new HBox(
							new VBox(10,
									melding, productSelector,
							new HBox(10, 
									annuleren = new Button("Annuleren"),
									ok = new Button("Toevoegen") 
									))));
			annuleren.setPrefWidth(100);
			annuleren.setOnAction(e -> {
				keuze = "cancel";
				this.hide();
		});
			ok.setPrefWidth(100);
			   ok.setOnAction(e -> {
			    keuze = "confirm";
			    this.hide();
			   });
		notification.setAlignment(Pos.CENTER);
		notification.setPadding(new Insets(20));
		
		Scene scene = new Scene(notification);
		this.setScene(scene);
		
		}	
	
	public String getKeuze(){
		return keuze;
	}
	public Product getSelected(){
		return productSelector.getSelectionModel().getSelectedItem();
	}
}
