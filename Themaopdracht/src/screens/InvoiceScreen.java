package screens;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.ATDProgram;
import main.Customer;
import main.Invoice;
import main.Invoice.InvoiceItem;
import main.MaintenanceSession;
import main.Product;
import notifications.GetInfoNotification;
import notifications.Notification;


public class InvoiceScreen extends HBox {
	private ATDProgram controller;
	private Invoice selectedInvoice;
	private double
			spacingBoxes = 10,
			widthLabels = 120;
	private Button 
			newButton = new Button("Nieuw"), 
			bindButton = new Button("Klant binden"), 
			removeButton = new Button("Verwijderen"), 
			addMaintenance = new Button("+Onderhoud"),
			addRefuel = new Button("+Tanksessie"),
			addParking = new Button("+reservering");
	private ComboBox<String> 
			filterSelector = new ComboBox<String>();
	private ArrayList<ListItem> content = new ArrayList<ListItem>();
	private Label 
			date = new Label("Datum: "),
			dateContent = new Label("-"), 
			price = new Label("Prijs: "), 
			priceContent = new Label("-"),
			isBetaalt = new Label("Is betaalt: "),
			isBetaaltContent = new Label("-"),
			customer = new Label("Klant: "), 
			customerContent = new Label("-");
	private ListView<ListItem> 
			listView = new ListView<ListItem>();
	private ListView<InvoiceItem>
			contentView = new ListView<InvoiceItem>();
	private VBox
			leftBox = new VBox(20),
			rightBox = new VBox(20);
	private HBox 
			detailsBox = new HBox(spacingBoxes), 
			mainButtonBox = new HBox(spacingBoxes), 
			SecButtonBox = new HBox(6), 
			mainBox = new HBox(spacingBoxes);
	public InvoiceScreen(ATDProgram controller) {
		this.controller = controller;
		//CustomerDetails
		detailsBox.getChildren().addAll(
				new VBox(20,
						new HBox(20,date     ,dateContent	  ,price   ,priceContent),
						new HBox(20,isBetaalt,isBetaaltContent,customer,customerContent),
						contentView
						));
		detailsBox.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border: solid;");
		detailsBox.setPrefSize(450, 520-15);
		detailsBox.setPadding(new Insets(20));
//		setVisibility(true, false, false);
		//geef alle labels een bepaalde grootte
		for (Node node1 : ((VBox)detailsBox.getChildren().get(0)).getChildren())
			if(node1 instanceof HBox&&((HBox)node1).getChildren().get(0)instanceof Label){
				((Label)((HBox)node1).getChildren().get(0)).setMinWidth(100);
				((Label)((HBox)node1).getChildren().get(1)).setMinWidth(widthLabels);
				((Label)((HBox)node1).getChildren().get(2)).setMinWidth(100);
				((Label)((HBox)node1).getChildren().get(3)).setMinWidth(widthLabels);
			}
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
		//contentView
		contentView.setPrefSize(410, 370);
		//filter & buttons
		SecButtonBox.getChildren().addAll(
				addMaintenance,
				addRefuel,
				addParking,
				filterSelector);
		//maintenance
		addMaintenance.setPrefSize(112.5, 50);
		addMaintenance.setOnAction(e -> {
			GetInfoNotification addMaintenanceNotification = new GetInfoNotification(controller.getStage(), "selecteer een onderhoudssessie", controller, ATDProgram.notificationStyle.MAINTENANCE);
			addMaintenanceNotification.showAndWait();
			if(addMaintenanceNotification.getKeuze().equals("confirm")){
				MaintenanceSession selection = (MaintenanceSession)addMaintenanceNotification.getSelected();
				selectedInvoice.add(selectedInvoice.new InvoiceItem("Uurloon", selection.getMechanic().getHourlyFee(), selection.getMechanic().getWorkedHours()));
				Iterator<Product> keySetIterator = selection.getUsedParts().keySet().iterator();
				while(keySetIterator.hasNext()){
					Product key = keySetIterator.next();
					double price = key.getSellPrice() * selection.getUsedParts().get(key);
					controller.getStock().useProduct(key, selection.getUsedParts().get(key));
					selectedInvoice.add(selectedInvoice.new InvoiceItem(key.getName(),price,selection.getUsedParts().get(key)));
					refreshList();
				}
			}	
		});
		//fuel
		addRefuel.setPrefSize(112.5, 50);
		addRefuel.setOnAction(e -> {
			//TODO
		});
		addParking.setPrefSize(112.5, 50);
		addParking.setOnAction(e->{
			//TODO
		});
		filterSelector.setPrefSize(112.5, 50);
		filterSelector.getItems().addAll("Filter: Geen", "Filter: Achterstand", "Filter: Huidig anoniem");
		filterSelector.getSelectionModel().selectFirst();
		filterSelector.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			listView.getItems().clear();
			if(newValue.intValue()==0)
				for (Invoice invoice : controller.getInvoices())
					listView.getItems().add(new ListItem(invoice));
			if(newValue.intValue()==1)
				for (Invoice invoice : controller.getInvoices())
					if(!invoice.isPayed() && invoice.getInvoiceDate().isBefore(LocalDate.now().minusMonths(3)))
						listView.getItems().add(new ListItem(invoice));
			if(newValue.intValue()==2)
				for (Invoice invoice : controller.getInvoices())
					if(invoice.getCustomer()==null && !invoice.isPayed())
						listView.getItems().add(new ListItem(invoice));
			refreshList();
//			if (!searchInput.getText().equals("Zoek..."))search(null, searchInput.getText());
		});
		
		
		//main Buttons
		mainButtonBox.getChildren().addAll(
				newButton,
				bindButton,
				removeButton);
		//NewButton
		newButton.setPrefSize(150, 50);
		newButton.setOnAction(e -> {
			Invoice newInvoice = new Invoice();
			controller.addorRemoveInvoice(newInvoice, false);
			listView.getItems().add(new ListItem(newInvoice));
			refreshList();
		});
		//bindButton
		bindButton.setPrefSize(150, 50);
		bindButton.setOnAction(e -> {
			GetInfoNotification getCustomer = new GetInfoNotification(controller.getStage(), "selecteer een klant", controller, ATDProgram.notificationStyle.CUSTOMER);
			getCustomer.showAndWait();
			if(getCustomer.getKeuze().equals("confirm"))
				selectedInvoice.bindToCustomer((Customer)getCustomer.getSelected());
		});
		//RemoveButton
		removeButton.setPrefSize(150, 50);
		removeButton.setOnAction(e->{
			Notification removeConfirm = new Notification(controller.getStage(), "Weet u zeker dat u deze factuur wilt verwijderen?", ATDProgram.notificationStyle.CONFIRM);
			removeConfirm.showAndWait();
			if (removeConfirm.getKeuze() == "confirm"){
			listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
			controller.addorRemoveInvoice(selectedInvoice, true);
			Notification removeNotify = new Notification(controller.getStage(), "factuur is verwijderd.", ATDProgram.notificationStyle.NOTIFY);
			removeNotify.showAndWait();}
			refreshList();
		});
		//Make & merge left & right
		leftBox.getChildren().addAll (listView,SecButtonBox,mainButtonBox);
		rightBox.getChildren().addAll(detailsBox);
		mainBox.getChildren().addAll (leftBox,rightBox);
		mainBox.setSpacing(20);
		mainBox.setPadding(new Insets(20));
		this.getChildren().add(mainBox);
	}
	private void refreshList(){
		content.clear();
		content.addAll(listView.getItems());
		listView.getItems().clear();
		listView.getItems().addAll(content);
		for (ListItem listItem : listView.getItems())
			listItem.refresh();
	}
	private void selectedListEntry(){
		dateContent.setText(selectedInvoice.getInvoiceDate().toString());
		priceContent.setText(Double.toString(selectedInvoice.getTotalPrice()));
		if(selectedInvoice.isPayed())isBetaaltContent.setText("Ja");
		else isBetaaltContent.setText("Nee");
		if(selectedInvoice.getCustomer()!=null)customerContent.setText(selectedInvoice.getCustomer().getName());
		else customerContent.setText("Anoniem");
		contentView.getItems().clear();
		for (InvoiceItem item : selectedInvoice.getItems())
			contentView.getItems().add(item);
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
	