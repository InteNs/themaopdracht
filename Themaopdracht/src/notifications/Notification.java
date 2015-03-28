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
	private enum notificationStyle {CONFIRM, NOTIFY}
	
	public Notification(Stage currentStage, String bericht, notificationStyle stijl){
		super(StageStyle.UTILITY);
		initOwner(currentStage); 
		initModality(Modality.WINDOW_MODAL); 
		this.setResizable(false);
		this.setTitle(bericht);
		melding = new Label(bericht);
		
		
		if(stijl == notificationStyle.CONFIRM){
			notification = new VBox(10,
					new HBox(
							new VBox(10,
									melding,
							new HBox(10, 
									annuleren = new Button("Annuleren"),
									ok = new Button("Ja") 
									))));
			annuleren.setPrefWidth(100);
			annuleren.setOnAction(e -> {
				keuze = "annuleren";
				this.hide();
			});
		}
		if(stijl == notificationStyle.NOTIFY) {
			notification = new VBox(10,
					new HBox(
							new VBox(10,
									melding,
							new HBox(10, 
									ok = new Button("Ja") 
									))));
		}
		ok.setPrefWidth(100);
		ok.setOnAction(e -> {
			keuze = "ja";
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
