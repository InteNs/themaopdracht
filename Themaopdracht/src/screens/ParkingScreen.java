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
import main.ParkingSpace;
import main.Reservation;
import notifications.Notification;

public class ParkingScreen extends HBox {
	private final ATDProgram controller;
	
	private final ComboBox<String> filterSelector 	= new ComboBox<String>();
	private final ComboBox<ParkingSpace> spaceContent	= new ComboBox<ParkingSpace>();
	private final ArrayList<ListRegel> content 		= new ArrayList<ListRegel>();
	private final ListView<ListRegel> itemList 		= new ListView<ListRegel>();
	private final DatePicker dateFromContent 		= new DatePicker();
	private final DatePicker dateToContent 			= new DatePicker();
	private Reservation selectedObject;
	private ListRegel selectedItem;
	private boolean isChanging;
	private static final double
			space_Small = 10,
			space_Big 	= 20,
			space_3 	= 15,
			button_3 	= 140,
			label_Normal= 120;
	private final Button 
			newButton 			= new Button("Nieuw"), 
			changeButton 		= new Button("Aanpassen"), 
			removeButton 		= new Button("Verwijderen"), 
			cancelButton 		= new Button("Annuleren"),
			saveButton 			= new Button("Opslaan");
	private final Label 
			dateToLabel 		= new Label("Datum van: "),
			dateFromLabel 		= new Label("Datum tot: "), 
			spaceLabel 			= new Label("Parkeerplaats: "),
			numberPlateLabel 	= new Label("Kenteken: ");
	private final TextField 
			searchContent 		= new TextField("Zoek..."),
			numberPlateContent	= new TextField();
	private final VBox
			leftBox 			= new VBox(space_Big),
			rightBox 			= new VBox(space_Big);
	private final HBox 
			detailsBox 			= new HBox(space_Small), 
			control_MainBox 	= new HBox(space_3), 
			control_secBox 		= new HBox(space_3), 
			mainBox 			= new HBox(space_Small);
	public ParkingScreen(ATDProgram controller) {
		this.controller = controller;
		//put everything in the right place
		control_MainBox.getChildren().addAll(newButton		, changeButton	  , removeButton);
		control_secBox.getChildren().addAll (searchContent	, filterSelector);
		leftBox.getChildren().addAll		(itemList		, control_secBox);
		rightBox.getChildren().addAll		(detailsBox		, control_MainBox);
		mainBox.getChildren().addAll 		(leftBox		, rightBox);
		this.getChildren().add				(mainBox);
		detailsBox.getChildren().addAll(
				new VBox(space_Big,
						new HBox(space_Big,dateToLabel,		dateToContent),
						new HBox(space_Big,dateFromLabel,	dateFromContent),
						new HBox(space_Big,numberPlateLabel,numberPlateContent),
						new HBox(space_Big,spaceLabel,	 	spaceContent),
						new HBox(space_Big,cancelButton,	saveButton))
		);
		//set styles and sizes
		////set width for all detail labels and textfields
		for (Node node : ((VBox)detailsBox.getChildren().get(0)).getChildren()) {
			if(((HBox)node).getChildren().get(0) instanceof Label)
				((Label)((HBox)node).getChildren().get(0)).setMinWidth(label_Normal);
		}
		numberPlateContent.setMinWidth(label_Normal*1.5);
		dateToContent.setMinWidth(label_Normal*1.5);
		spaceContent.setMinWidth(label_Normal*1.5);	
		detailsBox.setPadding(new Insets(space_Big));
		mainBox.setPadding(new Insets(space_Big));
		detailsBox.getStyleClass().addAll("removeDisabledEffect","detailsBox");
		leftBox.getStyleClass().add("removeDisabledEffect");
		itemList.getStyleClass().add("removeDisabledEffect");
		detailsBox.setPrefSize(450, 520);
		itemList.setPrefSize(450, 520);
		searchContent.setPrefSize	(290, 50);
		filterSelector.setPrefSize	(button_3, 50);
		cancelButton.setPrefSize	(150	 , 50);
		saveButton.setPrefSize		(150	 , 50);
		newButton.setPrefSize		(button_3, 50);
		changeButton.setPrefSize	(button_3, 50);
		removeButton.setPrefSize	(button_3, 50);
		//StockDetails
		spaceContent.getItems().addAll(controller.getParkingSpaces());
		setEditable(false);
		//Listview
		for (Reservation object : controller.getReservations()) 
			if(object.isActive().equals("active")||object.isActive().equals("before"))itemList.getItems().add(new ListRegel(object));
		refreshList();
		itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			select(newValue);
		});
		//SearchField	
		searchContent.setOnMouseClicked(e -> {
			if (searchContent.getText().equals("Zoek...")) {
				searchContent.clear();
			} else searchContent.selectAll();
		});
		searchContent.textProperty().addListener((observable, oldValue, newValue) -> {
				search(oldValue, newValue);
		});
		//Buttons and filter
		newButton.setOnAction(e -> {
			clearInput();
			setEditable(true);
			isChanging = false;
		});
		changeButton.setOnAction(e -> {
			if(checkSelected()){
				setEditable(true);
				isChanging = true;
			}
		});
		removeButton.setOnAction(e->{
			remove();
		});
		cancelButton.setOnAction(e -> {
			clearInput();
			setEditable(false);
		});
		saveButton.setOnAction(e -> {
			save();
		});

		filterSelector.getItems().addAll("Filter: Geen", "Filter: Actief", "Filter: Overzicht", "Filter: Afgesloten");
		filterSelector.getSelectionModel().selectFirst();
		filterSelector.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			changeFilter(newValue.intValue());
		});
	}
	/**
	 * fills the list with items that fit with the given filter
	 * @param newValue selected filter
	 */
	private void changeFilter(int newValue) {
		switch(newValue){
		case 0:{//Geen
				itemList.getItems().clear();
				for (Reservation object : controller.getReservations())
					itemList.getItems().add(new ListRegel(object));
				break;
			}
		case 1:{//Actief
				itemList.getItems().clear();
				for (Reservation object : controller.getReservations())
					if(object.isActive().equals("active"))itemList.getItems().add(new ListRegel(object));
				break;
			}
		case 2:{//Overzicht
				itemList.getItems().clear();
				//TODO
				break;
			}
		case 3:{//Afgesloten
			itemList.getItems().clear();
			for (Reservation object : controller.getReservations())
				if(object.isActive().equals("done"))itemList.getItems().add(new ListRegel(object));
			break;
		}
		}
		
		if(!searchContent.getText().equals("Zoek..."))search(null, searchContent.getText());	
	}
	/*
	 * removes the selected item
	 */
	private void remove(){
		if(checkSelected()){
			Notification Confirm = new Notification(controller, "Weet u zeker dat u deze reservering wilt verwijderen?", ATDProgram.notificationStyle.CONFIRM);
			Confirm.showAndWait();
			if (Confirm.getKeuze().equals("confirm")){
				itemList.getItems().remove(selectedItem);
				controller.addorRemoveReservations(selectedObject, true);
				Notification Notify = new Notification(controller, "de klus is verwijderd.", ATDProgram.notificationStyle.NOTIFY);
				Notify.showAndWait();
			}
		}			
	}
	/**
	 * saves the input in either a selected item or a new item
	 */
	private void save(){
		if(checkInput()){
			if(isChanging){
				Notification confirm = new Notification(controller, "Weet u zeker dat u deze wijzigingen wilt doorvoeren?",ATDProgram.notificationStyle.CONFIRM);
				confirm.showAndWait();
				switch (confirm.getKeuze()) {
					case "confirm": {
						selectedObject.setToDate(dateToContent.getValue());
						selectedObject.setFromDate(dateFromContent.getValue());
						selectedObject.setNumberPlate(numberPlateContent.getText());
						spaceContent.setDisable(true);
						refreshList();
					}
					case "cancel":
						clearInput();
						setEditable(false);
				}
			}
			else{	
				Notification confirm = new Notification(controller,"Deze klus aanmaken?",ATDProgram.notificationStyle.CONFIRM);
				confirm.showAndWait();
				switch (confirm.getKeuze()) {
					case "confirm": {
						Reservation newReservation = new Reservation(
								dateFromContent.getValue(), 
								dateToContent.getValue(), 
								numberPlateContent.getText(), 
								spaceContent.getValue());
						controller.addorRemoveReservations(newReservation, false);
						itemList.getItems().add(new ListRegel(newReservation));
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
			Notification notFilled = new Notification(controller, "Niet alle velden zijn Juist ingevuld",ATDProgram.notificationStyle.NOTIFY);
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
		select(selectedItem);
	}
	/**
	 * selects an item and fills in the information
	 * @param selectedValue the item to be selected
	 */
	private void select(ListRegel selectedValue){
		if(selectedValue!=null)	{
			selectedItem = selectedValue;
			selectedObject = selectedValue.getReservation();
			dateToContent.setValue(selectedObject.getToDate());
			dateFromContent.setValue(selectedObject.getFromDate());
			spaceContent.setValue(selectedObject.getParkingSpace());
			numberPlateContent.setText(selectedObject.getNumberPlate());
		}
	}
	/**
	 * disables/enables the left or right side of the stage
	 * @param enable
	 */
	private void setEditable(boolean enable){
		cancelButton.setVisible(enable);
		saveButton.setVisible(enable);
		detailsBox.setDisable(!enable);
		control_MainBox.setDisable(enable);
		spaceContent.setDisable(!enable);
		leftBox.setDisable(enable);
	}	
	/**
	 * clears all the inputfields
	 */
	private void clearInput(){
		for (Node node1 : ((VBox)detailsBox.getChildren().get(0)).getChildren())
			if(((HBox)node1).getChildren().get(1) instanceof TextField)((TextField)((HBox)node1).getChildren().get(1)).clear();
		spaceContent.getSelectionModel().clearSelection();
		dateToContent.setValue(null);
		dateFromContent.setValue(null);
	}
	/**
	 * checks if all the inputfields are filled in
	 * @return false if one of the inputs is null
	 */
	private boolean checkInput(){
		boolean result = true;
		if(numberPlateContent.getText().isEmpty())result = false;
		if(dateToContent.getValue().isBefore(LocalDate.now()))result = false;
		if(dateFromContent.getValue().isBefore(LocalDate.now()))result = false;
		if(spaceContent.getSelectionModel().getSelectedItem()!=null) result = false;
		return result;
	}
	/**
	 * checks if an item is selected in the list
	 * @return false if nothing is selected
	 */
	private boolean checkSelected() {
		if(selectedObject == null) return false;
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
			if (entry.getReservation().getNumberPlate().contains(newVal)){
				itemList.getItems().add(entry);
			}
		}
	}
	// this represents every item in the list, it has different constructor for every filter option
	public class ListRegel extends HBox{
		private Reservation object;
		private Label itemPlateLabel = new Label(),itemFromLabel = new Label(),itemToLabel = new Label(),itemSpaceLabel = new Label();
		public ListRegel(Reservation object){
			this.object = object;
			refresh();
			setSpacing(5);
			getChildren().addAll(
				itemFromLabel,
				new Separator(Orientation.VERTICAL),
				itemToLabel,
				new Separator(Orientation.VERTICAL),
				itemPlateLabel,
				new Separator(Orientation.VERTICAL),
				itemSpaceLabel);
				for (Node node : getChildren()) 
					if(node instanceof Label)((Label)node).setPrefWidth(100);
		}
		/**
		 * fills in all the labels with the latest values
		 */
		public void refresh(){
			itemPlateLabel.setText(object.getNumberPlate());
			itemFromLabel.setText(object.getFromDate().toString());
			itemToLabel.setText(object.getToDate().toString());
			itemSpaceLabel.setText(object.getParkingSpace().toString());
		}
		/**
		 * @return the object this item represents
		 */
		public Reservation getReservation(){
			return object;
		}
	}
}

