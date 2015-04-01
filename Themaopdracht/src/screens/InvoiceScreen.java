package screens;
import java.util.ArrayList;

import screens.StockScreen.ListRegel;
import notifications.Notification;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import main.Customer;
import main.Invoice;

public class InvoiceScreen extends HBox {
	private ATDProgram controller;
	private Invoice selectedInvoice;
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
	private ComboBox<String> 
			filterSelector = new ComboBox<String>();
	private ComboBox<Customer>
			customerSelector = new ComboBox<Customer>();
	private ArrayList<ListItem> content = new ArrayList<ListItem>();
	private CheckBox 
			blackListInput = new CheckBox();
	private Label 
			date = new Label("Datum: "),
			dateContent = new Label("-"), 
			price = new Label("Prijs: "), 
			priceContent = new Label("-"),
			isBetaalt = new Label("Is betaalt: "),
			isBetaaltContent = new Label("-"),
			customer = new Label("Klant: "), 
			customerContent = new Label("-");
	private TextField searchInput = new TextField("Zoek...");
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
	public InvoiceScreen(ATDProgram controller) {
		this.controller = controller;
		//CustomerDetails
		customerSelector.getItems().addAll(controller.getCustomers());
		detailsBox.getChildren().addAll(
				new VBox(20,
						new HBox(20,date,		dateContent),
						new HBox(20,price,		priceContent),
						new HBox(20,isBetaalt,	isBetaaltContent),
						new HBox(20,customer,	customerContent,	customerSelector),
						new HBox(20,cancelButton,saveButton)
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
				setVisibility(true, false, false);
		});
		//savebutton
		saveButton.setPrefSize(150, 50);
		saveButton.setOnAction(e -> {
			Notification changeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u deze wijzigingen wilt doorvoeren?", ATDProgram.notificationStyle.CONFIRM);
			changeConfirm.showAndWait();
			save();
			Notification changeNotify = new Notification(controller.getStage(), "Wijzigingen zijn doorgevoerd.", ATDProgram.notificationStyle.NOTIFY);
			changeNotify.showAndWait();
		});
		//listview
		listView.setPrefSize(450, 520);
		for (Invoice invoice : controller.getInvoices()) {
			listView.getItems().add(new ListItem(invoice));
		}
		refreshList();
		listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue!=null)selectedInvoice = newValue.getContent();
			else selectedInvoice = oldValue.getContent();
			selectedListEntry();
		});
		//SearchField
		searchFieldBox = new HBox(10,searchInput,filterSelector);
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
		filterSelector.getItems().addAll("Filter: Geen", "Filter: Niet betaald", "Filter: Anoniem");
		filterSelector.getSelectionModel().selectFirst();
		filterSelector.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			listView.getItems().clear();
			if(newValue.intValue()==0){
				for (Invoice invoice : controller.getInvoices()) {
					listView.getItems().add(new ListItem(invoice));
				}
			}
			if(newValue.intValue()==1){
				for (Invoice invoice : controller.getInvoices()) {
					if(!invoice.isPayed())
						listView.getItems().add(new ListItem(invoice));
				}
			}
			if(newValue.intValue()==2){
				for (Invoice invoice : controller.getInvoices()) {
					if(invoice.getCustomer()==null)
						listView.getItems().add(new ListItem(invoice));
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
		});
		//ChangeButton
		changeButton.setPrefSize(150, 50);
		changeButton.setOnAction(e -> {
			isChanging = true;
			change();
		});
		//RemoveButton
		removeButton.setPrefSize(150, 50);
		removeButton.setOnAction(e->{
			Notification removeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u deze klant wilt verwijderen?", ATDProgram.notificationStyle.CONFIRM);
			removeConfirm.showAndWait();
			if (removeConfirm.getKeuze() == "ja"){
			listView.getItems().remove(selectedInvoice);
			controller.addorRemoveInvoice(selectedInvoice, true);
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
		//dateContent.setText(selectedInvoice.getInvoiceDate().toString());
		//priceContent.setText(Double.toString(selectedInvoice.getTotalPrice()));
		//if(selectedInvoice.isPayed())isBetaaltContent.setText("Ja");
		//else isBetaaltContent.setText("Nee");
		//if(selectedInvoice.getCustomer()!=null)customerContent.setText(selectedInvoice.getCustomer().getName());
		//else customerContent.setText("Anoniem");
		setVisibility(true, true, true);	
		isChanging = true;
	}
	/**
	 * slaat het nieuwe of aangepaste product op
	 */
	private void save(){
//		if(isChanging){
//			selectedInvoice.setName(nameInput.getText());
//			selectedInvoice.setPlace(placeInput.getText());
//			selectedInvoice.setBankAccount(bankInput.getText());
//			selectedInvoice.setDateOfBirth(dateInput.getValue());
//			selectedInvoice.setEmail(emailInput.getText());
//			selectedInvoice.setPostal(postalInput.getText());
//			selectedInvoice.setTel(phoneInput.getText());
//			selectedInvoice.setAdress(addressInput.getText());
//			selectedInvoice.setOnBlackList(blackListInput.isSelected());
//			refreshList();
//			setVisibility(true, false, false);
//			
//		}
		if(!isChanging){
			Invoice newInvoice = new Invoice();
			if(customerSelector.getSelectionModel().getSelectedItem()!=null)
				newInvoice.bindToCustomer(customerSelector.getSelectionModel().getSelectedItem());
			controller.addorRemoveInvoice(newInvoice, false);
			listView.getItems().add(new ListItem(newInvoice));
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
		dateContent.setText(selectedInvoice.getInvoiceDate().toString());
		priceContent.setText(Double.toString(selectedInvoice.getTotalPrice()));
		if(selectedInvoice.isPayed())isBetaaltContent.setText("Ja");
		else isBetaaltContent.setText("Nee");
		if(selectedInvoice.getCustomer()!=null)customerContent.setText(selectedInvoice.getCustomer().getName());
		else customerContent.setText("Anoniem");
		
	}
	private void setVisibility(boolean setDetailsVisible, boolean setTextFieldsVisible, boolean setButtonsVisible) {
		cancelButton.setVisible(setButtonsVisible);
		saveButton.setVisible(setButtonsVisible);	
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
					if(input instanceof CheckBox){
						((CheckBox)input).setPrefSize(0,0);
						((CheckBox)input).setSelected(false);
					}
					content.setPrefWidth(widthLabels*2);
				}
				else if (!setDetailsVisible) {
					if(input instanceof TextField)	((TextField)input).setPrefWidth(widthLabels*2);
					if(input instanceof DatePicker)	((DatePicker)input).setPrefWidth(widthLabels*2);
					if(input instanceof CheckBox)	((CheckBox)input).setPrefSize(25,25);
					content.setPrefWidth(0);
				}			
				else if (setDetailsVisible || setTextFieldsVisible) {
					if(input instanceof TextField)	((TextField)input).setPrefWidth(widthLabels);
					if(input instanceof DatePicker)	((DatePicker)input).setPrefWidth(widthLabels);
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
			try{
				if (entry.getContent().getTotalPrice() == Double.parseDouble(newVal)) 
					listView.getItems().add(entry);
			}catch(NumberFormatException e){}
		}
	}
	public class ListItem extends HBox{
		private Label contentDate = new Label(),contentIsPayed = new Label(),contentPrice = new Label();
		private Invoice invoice;
		public ListItem(Invoice invoice){
			this.invoice = invoice;
			refresh();
			setSpacing(5);
			getChildren().addAll(
					contentDate,
					new Separator(Orientation.VERTICAL),
					contentIsPayed,
					new Separator(Orientation.VERTICAL),
					contentPrice);
			((Label)getChildren().get(0)).setPrefWidth(120);
			((Label)getChildren().get(2)).setPrefWidth(100);
			((Label)getChildren().get(4)).setPrefWidth(200);
				
			
		}
		public void refresh(){
			contentDate.setText(invoice.getInvoiceDate().toString());
			if(invoice.isPayed())contentIsPayed.setText("Betaald");
			else contentIsPayed.setText("Niet betaald");
			contentPrice.setText("$"+invoice.getTotalPrice());
		}
		public Invoice getContent(){
			return invoice;
		}
	}
}

	