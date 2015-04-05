package screens;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.ATDProgram;
import main.MaintenanceSession;
import main.Mechanic;
import main.Product;
import notifications.GetInfoNotification;
import notifications.Notification;

public class MaintenanceScreen extends HBox {
	private ATDProgram controller;
	private MaintenanceSession selectedMaintenanceSession;
	private ComboBox<String> filterSelector = new ComboBox<String>();
	private ComboBox<Mechanic> mechanicContent = new ComboBox<Mechanic>();
	private ArrayList<ListRegel> content = new ArrayList<ListRegel>();
	private ListView<ListRegel> itemList = new ListView<ListRegel>();
	private DatePicker dateContent = new DatePicker();
	private double
			spacingBoxes = 10,
			widthLabels = 120;
	private boolean isChanging = false;
	private Button 
			newButton = new Button("Nieuw"), 
			changeButton = new Button("Aanpassen"), 
			removeButton = new Button("Verwijderen"), 
			cancelButton = new Button("Annuleren"),
			saveButton = new Button("Opslaan"),
			addButton = new Button("+Product"),
			endButton = new Button("Afronden");
	private Label 
			dateLabel = new Label("Datum: "),
			mechanicLabel = new Label("Monteur: "), 
			usedPartsLabel = new Label("Aantal onderdelen: "),
			numberPlateLabel = new Label("Kenteken: ");
	private TextField 
			searchInput = new TextField("Zoek..."), 
			usedPartsContent = new TextField(),
			numberPlateContent = new TextField();
	private VBox
			leftBox = new VBox(20),
			rightBox = new VBox(20);
	private HBox 
			details = new HBox(spacingBoxes), 
			mainButtonBox = new HBox(spacingBoxes), 
			searchFieldBox = new HBox(6), 
			mainBox = new HBox(spacingBoxes);
	public MaintenanceScreen(ATDProgram controller) {
		this.controller = controller;
		//StockDetails
		mechanicContent.getItems().addAll(controller.getMechanics());
		details.getChildren().addAll(
				new VBox(20,
						new HBox(20,dateLabel,		 dateContent),
						new HBox(20,mechanicLabel,	 mechanicContent),
						new HBox(20,usedPartsLabel,	 usedPartsContent),
						new HBox(20,numberPlateLabel,numberPlateContent),
						new HBox(20,cancelButton,	 saveButton)
						));
		details.setPrefSize(450, 500);
		details.getStyleClass().add("stockDetails");
		details.setPadding(new Insets(20));
		setEditable(false);
		//set width for all detail labels and textfields
		for (Node node : ((VBox)details.getChildren().get(0)).getChildren()) {
			if(((HBox)node).getChildren().get(0) instanceof Label)
				((Label)((HBox)node).getChildren().get(0)).setMinWidth(widthLabels);
			if(((HBox)node).getChildren().get(1) instanceof TextField)
				((TextField)((HBox)node).getChildren().get(1)).setMinWidth(widthLabels*1.5);
		}
		dateContent.setMinWidth(widthLabels*1.5);
		mechanicContent.setMinWidth(widthLabels*1.5);
		// save/cancel button
		cancelButton.setPrefSize(150, 50);
		cancelButton.setOnAction(e -> {
			clearInput();
			setEditable(false);
		});
		saveButton.setPrefSize(150, 50);
		saveButton.setOnAction(e -> {
			save();
		});
		//Listview
		itemList.setPrefSize(450, 500);
		for (MaintenanceSession session : controller.getMaintenanceSessions()) 
			if(!session.isFinished())itemList.getItems().add(new ListRegel(session));
		refreshList();
		itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			select(newValue);
		});
		//SearchField
		searchInput.setPrefSize(112.5, 50);
		searchInput.setOnMouseClicked(e -> {
			if (searchInput.getText().equals("Zoek...")) {
				searchInput.clear();
			} else searchInput.selectAll();
		});
		searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
				search(oldValue, newValue);
		});
		//Main Buttons and filter
		mainButtonBox.getChildren().addAll(
				newButton,
				changeButton,
				removeButton
				);
		newButton.setPrefSize(150, 50);
		newButton.setOnAction(e -> {
			clearInput();
			setEditable(true);
			isChanging = false;
		});
		changeButton.setPrefSize(150, 50);
		changeButton.setOnAction(e -> {
			if(checkSelected()){
				setEditable(true);
				isChanging = true;
			}
		});
		removeButton.setPrefSize(150, 50);
		removeButton.setOnAction(e->{
			remove();
		});
		endButton.setPrefSize(112.5, 50);
		endButton.setOnAction(e -> {
			endSession();
		});
		addButton.setPrefSize(112.5, 50);
		addButton.setOnAction(e->{
			addProduct();
		});
		filterSelector.setPrefSize(112.5, 50);
		filterSelector.getItems().addAll("Filter: Geen", "Filter: Vandaag", "Filter: Niet aangewezen", "Filter: Afgesloten");
		filterSelector.getSelectionModel().selectFirst();
		filterSelector.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			changeFilter(newValue.intValue());
		});
		//Make & merge left & right
		searchFieldBox.getChildren().addAll(searchInput,filterSelector,addButton,endButton);
		leftBox.getChildren().addAll (itemList, searchFieldBox);
		rightBox.getChildren().addAll(details,mainButtonBox);
		mainBox.getChildren().addAll (leftBox,rightBox);
		mainBox.setPadding(new Insets(20));
		this.getChildren().add(mainBox);
	}
	/**
	 * fills the list with items that fit with the given filter
	 * @param newValue selected filter
	 */
	private void changeFilter(int newValue) {
		switch(newValue){
		case 0:{
				itemList.getItems().clear();
				for (MaintenanceSession session : controller.getMaintenanceSessions())
					if(!session.isFinished())itemList.getItems().add(new ListRegel(session));
				break;
			}
		case 1:{
				itemList.getItems().clear();
				for (MaintenanceSession session : controller.getMaintenanceSessions())
					if(session.getPlannedDate().isEqual(LocalDate.now()) && !session.isFinished()) itemList.getItems().add(new ListRegel(session));
				break;
			}
		case 2:{
				itemList.getItems().clear();
				for (MaintenanceSession session : controller.getMaintenanceSessions())
					if(session.getMechanic()==null && !session.isFinished()) itemList.getItems().add(new ListRegel(session));
				break;
			}
		case 3:{
			itemList.getItems().clear();
			for (MaintenanceSession session : controller.getMaintenanceSessions())
				if(session.isFinished())itemList.getItems().add(new ListRegel(session));
			break;
		}
		}
		
		if(!searchInput.getText().equals("Zoek..."))search(null, searchInput.getText());	
	}
	/**
	 * adds a product to the session
	 */
	private void addProduct(){
		if(checkSelected() && checkMechanic()){
			GetInfoNotification addProduct = new GetInfoNotification(controller, ATDProgram.notificationStyle.PRODUCTS);
			addProduct.showAndWait();
			if(addProduct.getKeuze().equals("confirm")){
				selectedMaintenanceSession.usePart((Product)addProduct.getSelected());
				refreshList();
			} 
		}
	}
	/**
	 * ends the selected session
	 */
	private void endSession(){
		if(checkSelected() && checkMechanic()){
			GetInfoNotification endSession = new GetInfoNotification(controller, ATDProgram.notificationStyle.ENDSESSION);
			endSession.showAndWait();
			selectedMaintenanceSession.endSession(endSession.getInput());
			Notification changeNotify = new Notification(controller .getStage(), "Klus is afgesloten",ATDProgram.notificationStyle.NOTIFY);
			changeNotify.showAndWait();
			refreshList();
		}
	}
	/*
	 * removes the selected item
	 */
	private void remove(){
		if(checkSelected()){
			Notification removeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u dit session wilt verwijderen?", ATDProgram.notificationStyle.CONFIRM);
			removeConfirm.showAndWait();
			if (removeConfirm.getKeuze().equals("confirm")){
				itemList.getItems().remove(selectedMaintenanceSession);
				controller.addorRemoveMaintenanceSessions(selectedMaintenanceSession, true);
				Notification removeNotify = new Notification(controller.getStage(), "Het session is verwijderd.", ATDProgram.notificationStyle.NOTIFY);
				removeNotify.showAndWait();
			}
		}			
	}
	/**
	 * saves the input in either a selected item or a new item
	 */
	private void save(){
		if(checkInput()){
			if(isChanging){
				Notification confirm = new Notification(controller.getStage(), "Weet u zeker dat u deze wijzigingen wilt doorvoeren?",ATDProgram.notificationStyle.CONFIRM);
				confirm.showAndWait();
				switch (confirm.getKeuze()) {
					case "confirm": {
						selectedMaintenanceSession.setPlannedDate(dateContent.getValue());
						selectedMaintenanceSession.setMechanic(mechanicContent.getValue());
						selectedMaintenanceSession.setNumberPlate(numberPlateContent.getText());
						mechanicContent.setDisable(true);
						refreshList();
					}
					case "cancel":
						clearInput();
						setEditable(false);
				}
			}
			else{	
				Notification confirm = new Notification(controller.getStage(),"Deze klus aanmaken?",ATDProgram.notificationStyle.CONFIRM);
				confirm.showAndWait();
				switch (confirm.getKeuze()) {
					case "confirm": {
						MaintenanceSession newMaintenanceSession = new MaintenanceSession(
								numberPlateContent.getText(), 
								controller.getStock(), 
								dateContent.getValue());
						controller.addorRemoveMaintenanceSessions(newMaintenanceSession, false);
						itemList.getItems().add(new ListRegel(newMaintenanceSession));
						setEditable(false);
					}
					case "cancel":	{
						clearInput();
						setEditable(false);
					}
				}
			}
		}
		else{
			Notification notFilled = new Notification(controller.getStage(), "Niet alle velden zijn Juist ingevuld",ATDProgram.notificationStyle.NOTIFY);
			notFilled.showAndWait();
		}
	}
	/**
	 * refreshes the list and every item itself
	 */
	private void refreshList(){
		content.clear();
		content.addAll(itemList.getItems());
		itemList.getItems().clear();
		itemList.getItems().addAll(content);
		for (ListRegel listRegel : itemList.getItems())
			listRegel.refresh();
		changeFilter(filterSelector.getSelectionModel().getSelectedIndex());
	}
	/**
	 * selects an item and fills in the information
	 * @param selectedValue the item to be selected
	 */
	private void select(ListRegel selectedValue){
		if(filterSelector.getSelectionModel().getSelectedIndex() != 3){
			if(selectedValue!=null)	{
				selectedMaintenanceSession = selectedValue.getMaintenanceSession();
				dateContent.setValue(selectedMaintenanceSession.getPlannedDate());
				mechanicContent.setValue(selectedMaintenanceSession.getMechanic());
				numberPlateContent.setText(selectedMaintenanceSession.getNumberPlate());
				usedPartsContent.setText(Integer.toString(selectedMaintenanceSession.getTotalParts()));
			}
		}
	}
	/**
	 * disables/enables the left or right side of the stage
	 * @param enable
	 */
	private void setEditable(boolean enable){
		cancelButton.setVisible(enable);
		saveButton.setVisible(enable);
		details.setDisable(!enable);
		leftBox.setDisable(enable);
	}	
	/**
	 * clears all the inputfields
	 */
	private void clearInput(){
		for (Node node1 : ((VBox)details.getChildren().get(0)).getChildren())
			if(((HBox)node1).getChildren().get(1) instanceof TextField)((TextField)((HBox)node1).getChildren().get(1)).clear();
		mechanicContent.getSelectionModel().clearSelection();
		dateContent.setValue(null);
	}
	/**
	 * checks if all the inputfields are filled in
	 * @return false if one of the inputs is null
	 */
	private boolean checkInput(){
		boolean result = true;
		for (Node node1 : ((VBox)details.getChildren().get(0)).getChildren())
			if(((HBox)node1).getChildren().get(1) instanceof TextField){
				if(((TextField)((HBox)node1).getChildren().get(1)).getText().isEmpty()){
					result = false;
				}
			}
		if(dateContent.getValue()==null)result = false;
		return result;
	}
	/**
	 * checks if an item is selected in the list
	 * @return false if nothing is selected
	 */
	private boolean checkSelected() {
		if(selectedMaintenanceSession == null) return false;
		return true;
	}
	/**
	 * checks if the selected item has a mechanic bound to it and notifies the actor if it doesn't
	 * @return true if a mechanic is found
	 */
	private boolean checkMechanic(){
		if(selectedMaintenanceSession.getMechanic()==null){
			Notification notification = new Notification(controller.getStage(), "Eerst een montuer toewijzen!", ATDProgram.notificationStyle.NOTIFY);
			notification.showAndWait();
			return false;
		}
		return true;
	}
	/**
	 * searches through all items in the list
	 * @param oldVal the previous content of the searchfield
	 * @param newVal the new content of the searchfield
	 */
	public void search(String oldVal, String newVal) {
		if (oldVal != null && (newVal.length() < oldVal.length())) {
			//actor has deleted a character, so reset the search
			itemList.getItems().clear();
			itemList.getItems().addAll(content);
		}
		itemList.getItems().clear();
		//add an item if any item that exists contains any value that has been searched for
		for (ListRegel entry : content) {		
			if (entry.getMaintenanceSession().getNumberPlate().contains(newVal)){
				itemList.getItems().add(entry);
			}
		}
	}
	// this represents every item in the list, it has different constructor for every filter option
	public class ListRegel extends HBox{
		private MaintenanceSession session;
		private Label itemPlateLabel = new Label(),itemMechLabel = new Label(),itemPartsLabel = new Label();
		public ListRegel(MaintenanceSession session){
			this.session = session;
			refresh();
			setSpacing(5);
			getChildren().addAll(
				itemMechLabel,
				new Separator(Orientation.VERTICAL),
				itemPlateLabel,
				new Separator(Orientation.VERTICAL),
				itemPartsLabel);
				for (Node node : getChildren()) 
					if(node instanceof Label)((Label)node).setPrefWidth(100);
		}
		/**
		 * fills in all the labels with the latest values
		 */
		public void refresh(){
			if(session.getMechanic()==null)itemMechLabel.setText("geen Monteur");
			else itemMechLabel.setText(session.getMechanic().getName());
			itemPlateLabel.setText(session.getNumberPlate());
			itemPartsLabel.setText(Integer.toString(session.getTotalParts()));
		}
		/**
		 * @return the object this item represents
		 */
		public MaintenanceSession getMaintenanceSession(){
			return session;
		}
	}
}
	