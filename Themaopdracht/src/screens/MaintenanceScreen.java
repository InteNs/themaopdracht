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
import notifications.Notification;

public class MaintenanceScreen extends HBox {
	private final ATDProgram controller;
	
	private final ComboBox<String> filterSelector 	= new ComboBox<String>();
	private final ComboBox<Mechanic> mechanicContent= new ComboBox<Mechanic>();
	private final ArrayList<ListRegel> content 		= new ArrayList<ListRegel>();
	private final ListView<ListRegel> itemList 		= new ListView<ListRegel>();
	private final DatePicker dateContent 			= new DatePicker();
	private MaintenanceSession selectedObject;
	private ListRegel selectedItem;
	private boolean isChanging;
	private static final double
			space_Small = 10,
			space_Big 	= 20,
			space_3 	= 15,
			button_3 	= 140,
			space_4 	= 6,
			button_4 	= 108,
			label_Normal= 120;
	private final Button 
			newButton 			= new Button("Nieuw"), 
			changeButton 		= new Button("Aanpassen"), 
			removeButton 		= new Button("Verwijderen"), 
			cancelButton 		= new Button("Annuleren"),
			saveButton 			= new Button("Opslaan"),
			addButton 			= new Button("+Product"),
			endButton 			= new Button("Afronden");
	private final Label 
			dateLabel 			= new Label("Datum: "),
			mechanicLabel 		= new Label("Monteur: "), 
			usedPartsLabel 		= new Label("Aantal onderdelen: "),
			numberPlateLabel	= new Label("Kenteken: ");
	private final TextField 
			searchContent 		= new TextField("Zoek..."), 
			usedPartsContent 	= new TextField(),
			numberPlateContent  = new TextField();
	private final VBox
			leftBox 			= new VBox(space_Big),
			rightBox 			= new VBox(space_Big);
	private final HBox 
			detailsBox 			= new HBox(space_Small), 
			control_MainBox 	= new HBox(space_3), 
			control_secBox 		= new HBox(space_4), 
			mainBox 			= new HBox(space_Small);
	public MaintenanceScreen(ATDProgram controller) {
		this.controller = controller;
		//put everything in the right place
		control_MainBox.getChildren().addAll(newButton		, changeButton	  , removeButton);
		control_secBox.getChildren().addAll (searchContent	, filterSelector  , addButton	  , endButton);
		leftBox.getChildren().addAll		(itemList		, control_secBox);
		rightBox.getChildren().addAll		(detailsBox		, control_MainBox);
		mainBox.getChildren().addAll 		(leftBox		, rightBox);
		this.getChildren().add				(mainBox);
		detailsBox.getChildren().addAll(
				new VBox(space_Big,
						new HBox(space_Big,dateLabel,		 dateContent),
						new HBox(space_Big,mechanicLabel,	 mechanicContent),
						new HBox(space_Big,usedPartsLabel,	 usedPartsContent),
						new HBox(space_Big,numberPlateLabel,numberPlateContent),
						new HBox(space_Big,cancelButton,	 saveButton))
		);
		//set styles and sizes
		////set width for all detail labels and textfields
		for (Node node : ((VBox)detailsBox.getChildren().get(0)).getChildren()) {
			if(((HBox)node).getChildren().get(0) instanceof Label)
				((Label)((HBox)node).getChildren().get(0)).setMinWidth(label_Normal);
			if(((HBox)node).getChildren().get(1) instanceof TextField)
				((TextField)((HBox)node).getChildren().get(1)).setMinWidth(label_Normal*1.5);
		}
		dateContent.setMinWidth(label_Normal*1.5);
		mechanicContent.setMinWidth(label_Normal*1.5);	
		detailsBox.setPadding(new Insets(space_Big));
		mainBox.setPadding(new Insets(space_Big));
		detailsBox.getStyleClass().addAll("removeDisabledEffect","detailsBox");
		leftBox.getStyleClass().add("removeDisabledEffect");
		itemList.getStyleClass().add("removeDisabledEffect");
		detailsBox.setPrefSize(450, 520);
		itemList.setPrefSize(450, 520);
		searchContent.setPrefSize	(button_4, 50);
		filterSelector.setPrefSize	(button_4, 50);
		cancelButton.setPrefSize	(150	 , 50);
		saveButton.setPrefSize		(150	 , 50);
		newButton.setPrefSize		(button_3, 50);
		changeButton.setPrefSize	(button_3, 50);
		removeButton.setPrefSize	(button_3, 50);
		endButton.setPrefSize		(button_4, 50);
		addButton.setPrefSize		(button_4, 50);
		//StockDetails
		mechanicContent.getItems().addAll(controller.getMechanics());
		setEditable(false);
		//Listview
		for (MaintenanceSession object : controller.getMaintenanceSessions()) 
			if(!object.isFinished())itemList.getItems().add(new ListRegel(object));
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
		endButton.setOnAction(e -> {
			endSession();
		});
		addButton.setOnAction(e->{
			addProduct();
		});
		cancelButton.setOnAction(e -> {
			clearInput();
			setEditable(false);
		});
		saveButton.setOnAction(e -> {
			save();
		});

		filterSelector.getItems().addAll("Filter: Geen", "Filter: Vandaag", "Filter: Niet aangewezen", "Filter: Afgesloten");
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
		case 0:{
				itemList.getItems().clear();
				for (MaintenanceSession object : controller.getMaintenanceSessions())
					if(!object.isFinished())itemList.getItems().add(new ListRegel(object));
				break;
			}
		case 1:{
				itemList.getItems().clear();
				for (MaintenanceSession object : controller.getMaintenanceSessions())
					if(object.getPlannedDate().isEqual(LocalDate.now()) && !object.isFinished()) itemList.getItems().add(new ListRegel(object));
				break;
			}
		case 2:{
				itemList.getItems().clear();
				for (MaintenanceSession object : controller.getMaintenanceSessions())
					if(object.getMechanic()==null && !object.isFinished()) itemList.getItems().add(new ListRegel(object));
				break;
			}
		case 3:{
			itemList.getItems().clear();
			for (MaintenanceSession object : controller.getMaintenanceSessions())
				if(object.isFinished())itemList.getItems().add(new ListRegel(object));
			break;
		}
		}
		
		if(!searchContent.getText().equals("Zoek..."))search(null, searchContent.getText());	
	}
	/**
	 * adds a product to the session
	 */
	private void addProduct(){
		if(checkSelected() && checkMechanic()){
			Notification addProduct = new Notification(controller,"", ATDProgram.notificationStyle.PRODUCTS);
			addProduct.showAndWait();
			if(addProduct.getKeuze().equals("confirm")){
				if(selectedObject.usePart((Product)addProduct.getSelected())){
					refreshList();
				}
				else {
					Notification noPart  = new Notification(controller, "Dat product is op!",ATDProgram.notificationStyle.NOTIFY);
					noPart.showAndWait();
				}
			} 
		}
	}
	/**
	 * ends the selected session
	 */
	private void endSession(){
		if(checkSelected() && checkMechanic()){
			Notification endSession = new Notification(controller,"", ATDProgram.notificationStyle.ENDSESSION);
			endSession.showAndWait();
			selectedObject.endSession(endSession.getInput());
			Notification Notify = new Notification(controller, "Klus is afgesloten",ATDProgram.notificationStyle.NOTIFY);
			Notify.showAndWait();
			refreshList();
		}
	}
	/*
	 * removes the selected item
	 */
	private void remove(){
		if(checkSelected()){
			Notification Confirm = new Notification(controller, "Weet u zeker dat u deze klus wilt verwijderen?", ATDProgram.notificationStyle.CONFIRM);
			Confirm.showAndWait();
			if (Confirm.getKeuze().equals("confirm")){
				itemList.getItems().remove(selectedItem);
				controller.addorRemoveMaintenanceSessions(selectedObject, true);
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
						selectedObject.setPlannedDate(dateContent.getValue());
						selectedObject.setMechanic(mechanicContent.getValue());
						selectedObject.setNumberPlate(numberPlateContent.getText());
						mechanicContent.setDisable(true);
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
		if(filterSelector.getSelectionModel().getSelectedIndex() != 3){
			if(selectedValue!=null)	{
				selectedItem = selectedValue;
				selectedObject = selectedValue.getMaintenanceSession();
				dateContent.setValue(selectedObject.getPlannedDate());
				mechanicContent.setValue(selectedObject.getMechanic());
				numberPlateContent.setText(selectedObject.getNumberPlate());
				usedPartsContent.setText(Integer.toString(selectedObject.getTotalParts()));
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
		detailsBox.setDisable(!enable);
		control_MainBox.setDisable(enable);
		usedPartsContent.setDisable(true);
		leftBox.setDisable(enable);
	}	
	/**
	 * clears all the inputfields
	 */
	private void clearInput(){
		for (Node node1 : ((VBox)detailsBox.getChildren().get(0)).getChildren())
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
		for (Node node1 : ((VBox)detailsBox.getChildren().get(0)).getChildren())
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
		if(selectedObject == null) return false;
		return true;
	}
	/**
	 * checks if the selected item has a mechanic bound to it and notifies the actor if it doesn't
	 * @return true if a mechanic is found
	 */
	private boolean checkMechanic(){
		if(selectedObject.getMechanic()==null){
			Notification notify = new Notification(controller, "Eerst een montuer toewijzen!", ATDProgram.notificationStyle.NOTIFY);
			notify.showAndWait();
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
		private MaintenanceSession object;
		private Label itemPlateLabel = new Label(),itemMechLabel = new Label(),itemDateLabel = new Label(),itemPartsLabel = new Label();
		public ListRegel(MaintenanceSession object){
			this.object = object;
			refresh();
			setSpacing(5);
			getChildren().addAll(
				itemMechLabel,
				new Separator(Orientation.VERTICAL),
				itemPlateLabel,
				new Separator(Orientation.VERTICAL),
				itemDateLabel,
				new Separator(Orientation.VERTICAL),
				itemPartsLabel);
				for (Node node : getChildren()) 
					if(node instanceof Label)((Label)node).setPrefWidth(100);
		}
		/**
		 * fills in all the labels with the latest values
		 */
		public void refresh(){
			if(object.getMechanic()==null)itemMechLabel.setText("geen Monteur");
			else itemMechLabel.setText(object.getMechanic().getName());
			itemPlateLabel.setText(object.getNumberPlate());
			itemDateLabel.setText(object.getPlannedDate().toString());
			itemPartsLabel.setText(Integer.toString(object.getTotalParts()));
		}
		/**
		 * @return the object this item represents
		 */
		public MaintenanceSession getMaintenanceSession(){
			return object;
		}
	}
}
	