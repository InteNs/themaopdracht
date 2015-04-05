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
	private Button annuleren = new Button("Annuleren"), ok = new Button("OK");
	private Label melding;
	private String keuze;
	private VBox notification = new VBox(10);
	
	public Notification(Stage currentStage, String bericht, ATDProgram.notificationStyle stijl){
		super(StageStyle.UTILITY);
		initOwner(currentStage); 
		initModality(Modality.WINDOW_MODAL); 
		initStyle(StageStyle.UNDECORATED);
		this.setResizable(false);
		melding = new Label(bericht);
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
		switch(stijl){
			case CONFIRM: {
			this.setTitle("Bevestigen");
			notification.getChildren().addAll(melding,new HBox(10,annuleren,ok));
			((HBox)notification.getChildren().get(1)).setAlignment(Pos.CENTER);
			break;
			}
			case NOTIFY: {
			this.setTitle("Melding");
			notification.getChildren().addAll(melding,ok);
			break;
			}
		default:
			break;
		}
		notification.setAlignment(Pos.CENTER);
		notification.setStyle("-fx-background-color: #e6e6e6; -fx-border-color: lightgray;-fx-border: solid;");
		notification.setPadding(new Insets(20));
		Scene scene = new Scene(notification);
		this.setScene(scene);
	}	
	/**
	 * returns the option the actor has chosen
	 * @return "confirm" or "cancel"
	 */
	public String getKeuze(){
		return keuze;
	}
}
