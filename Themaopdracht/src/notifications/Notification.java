package notifications;

import main.ATDProgram;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Notification extends Stage {
	private Button annuleren, ok;
	private Label melding;
	private String keuze;
	private VBox notification;
	private ATDProgram atdProgram;
	
	public Notification(Stage currentStage, String bericht, ATDProgram.notificationStyle stijl){
		super(StageStyle.UTILITY);
		initOwner(currentStage); 
		initModality(Modality.WINDOW_MODAL); 
		this.setResizable(false);
		melding = new Label(bericht);
		
		
		if(stijl == ATDProgram.notificationStyle.CONFIRM){
			this.setTitle("Bevestigen");
			notification = new VBox(10,
					new HBox(
							new VBox(10,
									melding,
							new HBox(10, 
									annuleren = new Button("Annuleren"),
									ok = new Button("OK") 
									))));
			annuleren.setPrefWidth(100);
			annuleren.setOnAction(e -> {
				keuze = "cancel";
				this.hide();
			});
		}
		if(stijl == ATDProgram.notificationStyle.NOTIFY) {
			this.setTitle("Melding");
			notification = new VBox(10,
					new HBox(
							new VBox(10,
									melding,
							new HBox(10, 
									ok = new Button("OK") 
									))));
		}
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
}
