package screens;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.ATDProgram;
import main.Invoice;
import main.MaintenanceSession;
import main.Mechanic;
import notifications.GetInfoNotification;
import notifications.Notification;

public class MaintenanceScreen extends HBox {
	private ATDProgram controller;
	private MaintenanceSession selectedMaintenanceSession;
	private double
			spacingBoxes = 10,
			widthLabels = 120;
	private boolean isChanging = false;
	private Button 
			newButton = new Button("Nieuw"), 
			changeButton = new Button("Aanpassen"), 
			removeButton = new Button("Verwijderen"), 
			cancelButton = new Button("Annuleren"),
			newProductButton = new Button("product toevoegen"),
			saveButton = new Button("Opslaan"),
			endSession = new Button("Afronden");
		
	private DatePicker 
			dateInput = new DatePicker();
	private ComboBox<String> 
			filterSelector = new ComboBox<String>();
	private ComboBox<Mechanic>
			mechanicSelector = new ComboBox<Mechanic>();
	private ArrayList<ListItem> content = new ArrayList<ListItem>();

	private Label 
			date = new Label("Geplande datum: "),
			dateContent = new Label("-"), 
			mechanic = new Label("Monteur: "), 
			mechanicContent = new Label("-"),
			usedPartsAmount = new Label("aantal onderdelen: "),
			usedPartsAmountContent = new Label("-");

	private TextField 
			searchInput = new TextField();
			
	private ListView<ListItem> 
			listView = new ListView<ListItem>();
	private VBox
			leftBox = new VBox(20),
			rightBox = new VBox(20);
	private HBox 
			detailsBox = new HBox(spacingBoxes), 
			mainButtonBox = new HBox(spacingBoxes), 
			searchFieldBox = new HBox(spacingBoxes), 
			mainBox = new HBox(spacingBoxes);
	public MaintenanceScreen(ATDProgram controller) {
		this.controller = controller;
		//MaintenanceSessionDetails
		mechanicSelector.getItems().addAll(controller.getMechanics());
		detailsBox.getChildren().addAll(
				new VBox(20,
						new HBox(20,date,			dateContent,		dateInput),
						new HBox(20,mechanic,		mechanicContent,	mechanicSelector),
						new HBox(20,usedPartsAmount,usedPartsAmountContent, new Label()),
						new HBox(20,endSession ,newProductButton),
						new HBox(20,cancelButton,	saveButton)
						));
		detailsBox.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border: solid;");
		detailsBox.setPrefSize(450, 520-15);
		detailsBox.setPadding(new Insets(20));
		setVisibility(true, false, false);
		//geef alle labels een bepaalde grootte
		for (Node node1 : ((VBox)detailsBox.getChildren().get(0)).getChildren()) {
			if(((HBox)node1).getChildren().size()>2)((Label)((HBox)node1).getChildren().get(0)).setMinWidth(widthLabels);
		}
		//cancelbutton
		cancelButton.setPrefSize(150, 50);
		cancelButton.setOnAction(e -> {
			disableMainButtons(false);
				setVisibility(true, false, false);
					((VBox)detailsBox.getChildren().get(0)).getChildren().get(1).setVisible(true);
		});
		//savebutton
		saveButton.setPrefSize(150, 50);
		saveButton.setOnAction(e -> {
			Notification changeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u deze wijzigingen wilt doorvoeren?", ATDProgram.notificationStyle.CONFIRM);
			changeConfirm.showAndWait();
			save();
			Notification changeNotify = new Notification(controller.getStage(), "Wijzigingen zijn doorgevoerd.", ATDProgram.notificationStyle.NOTIFY);
			changeNotify.showAndWait();
			disableMainButtons(false);
		});
		//listview
		listView.setPrefSize(450, 520);
		for (MaintenanceSession customer : controller.getMaintenanceSessions()) {
			listView.getItems().add(new ListItem(customer));
		}
		refreshList();
		listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue!=null)selectedMaintenanceSession = newValue.getMaintenanceSession();
			else selectedMaintenanceSession = oldValue.getMaintenanceSession();
			selectedListEntry();
		});
		//SearchField
		searchFieldBox = new HBox(10,searchInput = new TextField("Zoek..."),filterSelector);
		searchInput.setPrefSize(310, 50);
		searchInput.setDisable(true);
		searchInput.setOnMouseClicked(e -> {
			if (searchInput.getText().equals("Zoek...")) {
				searchInput.clear();
			} else
				searchInput.selectAll();
		});
		searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
				search(oldValue, newValue);
		});
		//filter
		filterSelector.setPrefSize(150, 50);
		filterSelector.getItems().addAll("Filter: Geen", "Filter: Vandaag", "Filter: niet aangewezen");
		filterSelector.getSelectionModel().selectFirst();
		filterSelector.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			listView.getItems().clear();
			if(newValue.intValue()==0){
				for (MaintenanceSession session : controller.getMaintenanceSessions()) {
					listView.getItems().add(new ListItem(session));
				}
			}
			if(newValue.intValue()==1){
				for (MaintenanceSession session : controller.getMaintenanceSessions()) {
					if(session.getPlannedDate().isEqual(LocalDate.now()))
					listView.getItems().add(new ListItem(session));
				}
			}
			if(newValue.intValue()==2){
				for (MaintenanceSession session : controller.getMaintenanceSessions()) {
					if(session.getMechanic()==null)
					listView.getItems().add(new ListItem(session));
				}
			}
			refreshList();
			if (!searchInput.getText().equals("Zoek..."))search(null, searchInput.getText());
		});
		
		
		//main Buttons
		mainButtonBox.getChildren().addAll(
				newButton,
				changeButton,
				removeButton
				);
		//NewButton
		newButton.setPrefSize(150, 50);
		newButton.setOnAction(e -> {
			setVisibility(true, false, false);
			setVisibility(false, true, true);
			isChanging = false;
			if (!isChanging) {
				((VBox)detailsBox.getChildren().get(0)).getChildren().get(1).setVisible(false);
			}
			disableMainButtons(true);
		});
		//addProductButton
		newProductButton.setPrefSize(150, 50);
		newProductButton.setOnAction(e->{
			GetInfoNotification addProductNormal = new GetInfoNotification(controller.getStage(), "Weet u zeker dat u dit onderdeel wilt toevoegen?",controller, ATDProgram.notificationStyle.PRODUCT);
			addProductNormal.showAndWait();
			if(addProductNormal.getKeuze().equals("confirm")){
				selectedMaintenanceSession.usePart(addProductNormal.getSelected());
				System.out.println(addProductNormal.getSelected());
				System.out.println(selectedMaintenanceSession.getUsedParts());
			 	System.out.println(Integer.toString(selectedMaintenanceSession.getTotalParts()));
			 	Notification changeNotify = new Notification(controller .getStage(), "Wijzigingen zijn doorgevoerd.",ATDProgram.notificationStyle.NOTIFY);
			 	changeNotify.showAndWait();
			 	refreshList();
			 	selectedListEntry();
			} 
		});
		//endSession button
				endSession.setPrefSize(150, 50);
				endSession.setOnAction(e -> {
					GetInfoNotification addProductEndSession = new GetInfoNotification(controller.getStage(), "Weet u zeker dat u dit onderdeel wilt toevoegen?",controller, ATDProgram.notificationStyle.ENDSESSION);
					addProductEndSession.showAndWait();
					selectedMaintenanceSession.endSession(addProductEndSession.getHours());
					Notification changeNotify = new Notification(controller .getStage(), "Wijzigingen zijn doorgevoerd.",ATDProgram.notificationStyle.NOTIFY);
				 	changeNotify.showAndWait();
				 	refreshList();
				 	selectedListEntry();
				});
		//ChangeButton
		changeButton.setPrefSize(150, 50);
		changeButton.setOnAction(e -> {
			if(selectedMaintenanceSession!=null){
				disableMainButtons(true);
				isChanging = true;
				if (isChanging) {
					((VBox)detailsBox.getChildren().get(0)).getChildren().get(1).setVisible(true);
				}
				change();
			}else{
				Notification errorNotify = new Notification(controller.getStage(), "Niets geselecteerd!", ATDProgram.notificationStyle.NOTIFY);
				errorNotify.showAndWait();
			}
		});
		//RemoveButton
		removeButton.setPrefSize(150, 50);
		removeButton.setOnAction(e->{
			Notification removeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u deze klant wilt verwijderen?", ATDProgram.notificationStyle.CONFIRM);
			removeConfirm.showAndWait();
			if (removeConfirm.getKeuze() == "ja"){
			listView.getItems().remove(selectedMaintenanceSession);
			controller.addorRemoveMaintenanceSessions(selectedMaintenanceSession, true);
			Notification removeNotify = new Notification(controller.getStage(), "Klant is verwijderd.", ATDProgram.notificationStyle.NOTIFY);
			removeNotify.showAndWait();}
		});
		
		//Make & merge left & right
		leftBox.getChildren().addAll (listView,searchFieldBox,mainButtonBox);
		rightBox.getChildren().addAll(detailsBox);
		mainBox.getChildren().addAll (leftBox,rightBox);
		mainBox.setSpacing(20);
		mainBox.setPadding(new Insets(20));
		this.getChildren().add(mainBox);
	}
	 
	/**
	 * vult alle gegevens in de TextFields om aan te passen
	 */
	private void change(){
		dateInput.setValue(selectedMaintenanceSession.getPlannedDate());
		if(selectedMaintenanceSession.getMechanic()==null)mechanicSelector.getSelectionModel().select(null);
		mechanicSelector.getSelectionModel().select(selectedMaintenanceSession.getMechanic());
		setVisibility(true, true, true);	
		isChanging = true;
	}
	/**
	 * slaat het nieuwe of aangepaste product op
	 */
	private void save(){
		if(isChanging){
			selectedMaintenanceSession.setPlannedDate(dateInput.getValue());
			selectedMaintenanceSession.setMechanic(mechanicSelector.getValue());
			refreshList();
			setVisibility(true, false, false);
			
		}
		else{
			MaintenanceSession newMaintenanceSession = new MaintenanceSession(
					controller.getStock(),
					dateInput.getValue()
					);
			controller.addorRemoveMaintenanceSessions(newMaintenanceSession, false);
			listView.getItems().add(new ListItem(newMaintenanceSession));
			setVisibility(true, false, false);
		}
	}
	private void refreshList(){
		content.clear();
		content.addAll(listView.getItems());
		listView.getItems().clear();
		listView.getItems().addAll(content);
		for (ListItem listItem : listView.getItems()) {
			listItem.refresh();
		}
	}
	private void selectedListEntry(){
		if(selectedMaintenanceSession.getPlannedDate()!=null)
			dateContent.setText(selectedMaintenanceSession.getPlannedDate().toString());
		if(selectedMaintenanceSession.getMechanic()!=null)
			mechanicContent.setText(selectedMaintenanceSession.getMechanic().getName());
		usedPartsAmountContent.setText(Integer.toString(selectedMaintenanceSession.getTotalParts()));
	}
	private void setVisibility(boolean setDetailsVisible, boolean setTextFieldsVisible, boolean setButtonsVisible) {
		cancelButton.setVisible(setButtonsVisible);
		saveButton.setVisible(setButtonsVisible);	
		if (!isChanging) {
			newProductButton.setVisible(!setButtonsVisible);
		}
		else newProductButton.setVisible(true);
		for (Node node1 : ((VBox)detailsBox.getChildren().get(0)).getChildren()) {
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
					if(input instanceof DatePicker){
						((DatePicker)input).setPrefWidth(0);
						((DatePicker)input).setValue(null);
					}
					if(input instanceof ComboBox){
						((ComboBox)input).setPrefWidth(0);
						((ComboBox)input).getSelectionModel().select(null);
					}
					content.setPrefWidth(widthLabels*2);
				}
				else if (!setDetailsVisible) {
					if(input instanceof TextField)	((TextField)input).setPrefWidth(widthLabels*2);
					if(input instanceof DatePicker)	((DatePicker)input).setPrefWidth(widthLabels*2);
					if(input instanceof ComboBox)	((ComboBox)input).setPrefWidth(widthLabels);
					if(input instanceof CheckBox)	((CheckBox)input).setPrefSize(25,25);
					content.setPrefWidth(0);
				}			
				else if (setDetailsVisible || setTextFieldsVisible) {
					if(input instanceof TextField)	((TextField)input).setPrefWidth(widthLabels);
					if(input instanceof DatePicker)	((DatePicker)input).setPrefWidth(widthLabels);
					if(input instanceof ComboBox)	((ComboBox)input).setPrefWidth(widthLabels);
					if(input instanceof CheckBox)	((CheckBox)input).setPrefSize(25,25);
					content.setPrefWidth(widthLabels);
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
		for (ListItem entry : content) {
			if (entry.getMaintenanceSession().getMechanic()!=null) {
			if (entry.getMaintenanceSession().getMechanic().getName().equals(newVal)) {
				listView.getItems().add(entry);
			}
			}
		}
	}
	public class ListItem extends HBox{
		private Label itemDate = new Label(),itemMechanic = new Label(),itemAmount = new Label();
		private MaintenanceSession session;
		public ListItem(MaintenanceSession session){
			this.session = session;
			refresh();
			setSpacing(5);
			getChildren().addAll(
					itemDate,
					new Separator(Orientation.VERTICAL),
					itemMechanic,
					new Separator(Orientation.VERTICAL),
					itemAmount);
			((Label)getChildren().get(0)).setPrefWidth(120);
			((Label)getChildren().get(2)).setPrefWidth(100);
			((Label)getChildren().get(4)).setPrefWidth(200);
				
			
		}
		public void refresh(){
			itemDate.setText(session.getPlannedDate().toString());
			if(session.getMechanic()!=null)itemMechanic.setText(session.getMechanic().getName());
			else itemMechanic.setText("Geen Mechanic");
			itemAmount.setText(Integer.toString(session.getUsedParts().size()));
		}
		
		public MaintenanceSession getMaintenanceSession(){
			return session;
		}
	}
	public void disableMainButtons(boolean disable) {
		newButton.setDisable(disable);
		changeButton.setDisable(disable);
		removeButton.setDisable(disable);
	}
}

	