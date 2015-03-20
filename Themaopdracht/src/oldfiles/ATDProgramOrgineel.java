package oldfiles;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.Customer;
import main.Mechanic;
import main.ParkingSpace;
import main.Reservation;
import main.Stock;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class ATDProgramOrgineel extends Application {
	private ArrayList<Customer> customers;
	private ArrayList<Mechanic> mechanics;
	private ArrayList<Reservation> reservations;
	private ArrayList<ParkingSpace> parkingSpaces;
	private boolean isChanging;
	Customer customer;
	private TextField searchField,tf;
	ListView<Customer> customerList;
	private Popup popup;
	private Stock stock;
	private HBox buttonBox;
	private Stage mainStage;
	private Customer selectedCustomer;
	private Customer customer1,customer2;
	private Button back,cancel,delete,change,newCustomer,savenewCustomer;
	private VBox customerInput, customerDetails,customerInfo, notifications;
	private Scene  lastScene, klantScene,newKlantScene;
	private VBox customerDetailsshort,addCustomerBox,leftBeheerBox,rightBeheerBox,customerDetailsContent;
	private DatePicker dp;
	private TabPane customerTabs;
	private Tab reminders,beheer;
	private CheckBox cb;
	//1024x600
	public enum visit{SERVICE, MAINTENANCE};
	public static void main(String[] args) {launch();}
	@Override
	public void start(Stage stage) throws Exception {
		this.mainStage = stage;
		cancel = new Button("Annuleren");
		cancel.setOnAction(e ->{
			customerInfo.getChildren().clear();
			customerInfo.getChildren().addAll(new HBox(20,customerDetails, customerDetailsContent));
			selectListEntry();
			isChanging = false;
		});
		customers = new ArrayList<Customer>();
		mechanics = new ArrayList<Mechanic>();
		reservations = new ArrayList<Reservation>();
		parkingSpaces = new ArrayList<ParkingSpace>();	
		stock = new Stock();
		customer1 = new Customer("Jorrit Meulenbeld", "Utrecht", "NL35 INGB 0008 8953 57",null,  "jorritmeulenbeld@icloud.com", "3552AZ","0636114939", "Omloop 48", false);customers.add(customer1);
		customer2 = new Customer("Mark Havekes", "De Meern", "n.v.t.", null, "mark.havekes@gmail.com","3453MC",  "0302801265", "De Drecht 32", false);customers.add(customer2);
		//KLANTSCENE
		newCustomer = new Button("Nieuwe Klant");
		newCustomer.setMinSize(160, 40);
		newCustomer.setOnAction(e ->{
			isChanging = false;
			change();
		});
		change = new Button("Aanpassen");
		change.setOnAction(e->{
			isChanging = true;
			change();
		});
		change.setMinSize(160, 40);
		delete = new Button("Verwijderen");
		delete.setOnAction(e->{
			deleteCustomer();
		});
		delete.setMinSize(160, 40);
		searchField = new TextField("Zoek...");
		searchField.setMinSize(492, 40);
		searchField.setOnMouseClicked(e->{
			if(searchField.getText().equals("Zoek...")){
				searchField.clear();
			}
			else searchField.selectAll();
		});
		searchField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String newValue) {
		    	handleSearchByKey(oldValue,newValue);
		    }
		});
		//customer details
		customerDetails = new VBox(20,new Label("Naam: "),new Label("Adres: "),new Label("Postcode: "),new Label("Plaats: "),new Label("Geboortedatum: "),new Label("Email: "),new Label("Telefoonnummer: "),new Label("Rekeningnummer: "),new Label("Blacklist: "));
		customerDetails.setPadding(new Insets(5,20,0,0));
		
		customerDetailsContent = new VBox(20,new Label("Naam"),new Label("Adres"),new Label("Postcode"),new Label("Plaats"),new Label("geboortedatum"),new Label("Email"),new Label("Telefoonnummer"),new Label("Rekeningnummer"),new Label("Blacklist"));
		customerDetailsContent.setPadding(new Insets(5,20,0,0));
		  customerInfo = new VBox(10,new HBox(10,customerDetails,customerDetailsContent));
		  customerInfo.setPadding(new Insets(20));
		  customerInfo.setStyle("-fx-background-color: white; -fx-border: solid; -fx-border-color: lightgray;");
		  customerInfo.setMinSize(472, 400);
		  notifications = new VBox(10, new HBox());
		  notifications.setStyle("-fx-background-color: white; -fx-border: solid; -fx-border-color: lightgray;");
		  notifications.setMinSize(472, 100);
		  customerList = new ListView<Customer>();
		  customerList.setOrientation(Orientation.VERTICAL); 
		  customerList.setMinSize(492, 300);
		  customerList.setItems(getAllCustomers());
		  customerList.setOnMousePressed(e ->{
			  customerInfo.getChildren().clear();
				customerInfo.getChildren().addAll(new HBox(20,customerDetails, customerDetailsContent));
				selectListEntry();
				isChanging = false;
		  });
		  leftBeheerBox = new VBox(20,customerList,searchField, new HBox(6,newCustomer,change,delete));
		  rightBeheerBox = new VBox(20,customerInfo, notifications);
		  leftBeheerBox.setPadding(new Insets(20, 0 , 20, 20));
		  rightBeheerBox.setPadding(new Insets(20, 20, 20, 0));
		//tabs
		customerTabs = new TabPane();
		beheer = new Tab("beheer");
		beheer.setClosable(false);
		beheer.setContent(new HBox(20, leftBeheerBox,rightBeheerBox));
		reminders = new Tab("herinneringen");
		reminders.setClosable(false);
		customerTabs.getTabs().addAll(beheer,reminders);
		klantScene = new Scene(customerTabs,1024,600);
		//newKlant scene
		savenewCustomer = new Button("Opslaan");
		savenewCustomer.setOnAction(e ->{
			saveCustomer();
			selectListEntry();
			//createPopup(new VBox(new Label("de klant is succesvol aangemaakt"), new HBox(new Button("OK"))));
			//popup.show(popup);
		});
		customerInput = new VBox();
		customerInput.setSpacing(10);
		for (int i = 0; i < 8; i++) {
			if(i == 4){
				dp = new DatePicker();
				customerInput.getChildren().add(dp);
			}
			if(i == 7){
				cb = new CheckBox();
				cb.setMinSize(25, 25);
				customerInput.getChildren().add(cb);
			}
			else{
				tf = new TextField();
				customerInput.getChildren().add(tf);
			}
		}
		buttonBox = new HBox();
		buttonBox.getChildren().addAll(cancel,savenewCustomer);
		customerDetailsshort = new VBox(20,new Label("Naam: "),new Label("Adres: "),new Label("Postcode: "),new Label("Plaats: "),new Label("Geboortedatum: "),new Label("Email: "),new Label("Telefoonnummer: "),new Label("Rekeningnummer: "));
		customerDetailsshort.setPadding(new Insets(20));
		addCustomerBox = new VBox(new HBox(30,customerDetailsshort,customerInput),new HBox(40,cancel,savenewCustomer));
		addCustomerBox.setPadding(new Insets(20));
		addCustomerBox.setSpacing(20);
		newKlantScene = new Scene(addCustomerBox,1024,600);	
		
		//Create Mainscreen
		stage.setScene(klantScene);
		stage.setTitle("AutoTotaalDienst");
		stage.setResizable(false);
		stage.show();
	}
	private void deleteCustomer() {
		if(customerList.getSelectionModel().getSelectedItem() != null){
			customers.remove(customerList.getSelectionModel().getSelectedItem());
			customerList.getItems().remove(customerList.getSelectionModel().getSelectedItem());
		}
	}
	private void selectListEntry() {
		if(customerList.getSelectionModel().getSelectedItem() != null){
			customer = customerList.getSelectionModel().selectedItemProperty().get();
			((Label) customerDetailsContent.getChildren().get(0)).setText(customer.getName());
			((Label) customerDetailsContent.getChildren().get(1)).setText(customer.getAdress());
			((Label) customerDetailsContent.getChildren().get(2)).setText(customer.getPostal());
			((Label) customerDetailsContent.getChildren().get(3)).setText(customer.getPlace());
			if (customer.getDateOfBirth() != null)((Label) customerDetailsContent.getChildren().get(4)).setText(customer.getDateOfBirth().toString());
			else((Label) customerDetailsContent.getChildren().get(4)).setText(" ");
			((Label) customerDetailsContent.getChildren().get(5)).setText(customer.getEmail());
			((Label) customerDetailsContent.getChildren().get(6)).setText(customer.getTel());
			((Label) customerDetailsContent.getChildren().get(7)).setText(customer.getBankAccount());
			if (customer.isOnBlackList())((Label) customerDetailsContent.getChildren().get(8)).setText("Ja");
			else((Label) customerDetailsContent.getChildren().get(8)).setText("Nee");
		}
	}
	private void saveCustomer() {
		if(isChanging == true){
			customers.remove(selectedCustomer);
			customerList.getItems().remove(selectedCustomer);
			isChanging = false;
		}
		customer = new Customer(((TextField)customerInput.getChildren().get(0)).getText(), 
				((TextField)customerInput.getChildren().get(3)).getText(),
				((TextField)customerInput.getChildren().get(7)).getText(), 
				((DatePicker)customerInput.getChildren().get(4)).getValue(), 
				((TextField)customerInput.getChildren().get(5)).getText(),
				((TextField)customerInput.getChildren().get(2)).getText(), 
				((TextField)customerInput.getChildren().get(6)).getText(),
				((TextField)customerInput.getChildren().get(1)).getText(), 
				((CheckBox)customerInput.getChildren().get(8)).isSelected());
		customers.add(customer);
		customerList.getItems().add(customer);
		customerInfo.getChildren().clear();
		customerInfo.getChildren().addAll(new HBox(20,customerDetails, customerDetailsContent));
	}
	private void change() {
		customerInfo.getChildren().clear();
		customerInfo.getChildren().addAll(new HBox(customerDetails, customerInput), new HBox(20,cancel, savenewCustomer));
		if (!isChanging) {
			for (int i = 0; i < 8; i++) {
				if(customerInput.getChildren().get(i) instanceof TextField){
					((TextField)customerInput.getChildren().get(i)).setText("");
				}
				if(customerInput.getChildren().get(i) instanceof CheckBox){
					((CheckBox)customerInput.getChildren().get(i)).setSelected(false);;
				}
				if(customerInput.getChildren().get(i) instanceof DatePicker){
					((DatePicker)customerInput.getChildren().get(i)).setValue(null);
				}	
			}
		} else if (customerList.getSelectionModel().getSelectedItem() != null){
			selectedCustomer = customerList.getSelectionModel().getSelectedItem();
			((TextField)customerInput.getChildren().get(0)).setText(selectedCustomer.getName());
			((TextField)customerInput.getChildren().get(1)).setText(selectedCustomer.getAdress());
			((TextField)customerInput.getChildren().get(2)).setText(selectedCustomer.getPostal());
			((TextField)customerInput.getChildren().get(3)).setText(selectedCustomer.getPlace());
			((DatePicker)customerInput.getChildren().get(4)).setValue(selectedCustomer.getDateOfBirth());
			((TextField)customerInput.getChildren().get(5)).setText(selectedCustomer.getEmail());
			((TextField)customerInput.getChildren().get(6)).setText(selectedCustomer.getTel());
			((TextField)customerInput.getChildren().get(7)).setText(selectedCustomer.getBankAccount());
			((CheckBox)customerInput.getChildren().get(8)).setSelected(selectedCustomer.isOnBlackList());
		}	
	}
	public ObservableList<Customer> getAllCustomers(){
		return FXCollections.observableArrayList(customers);
	}
	public void handleSearchByKey(String oldVal, String newVal) {
	    if ( oldVal != null && (newVal.length() < oldVal.length()) ) {
	        customerList.setItems(getAllCustomers());
	    }
	    customerList.getItems().clear();
	    for ( Customer entry: getAllCustomers() ) {
	        if ( entry.getName().contains(newVal) || entry.getPostal().contains(newVal) || entry.getEmail().contains(newVal)) {
	        	customerList.getItems().add(entry);
	        } else {
	        	customerList.getItems().remove(entry);
	        }
	    }
	}
	public List<Customer> getRemindList(visit remindoption) {
		ArrayList<Customer> remindables = new ArrayList<Customer>();
		for (Customer customer : customers) {
			switch (remindoption) {
			case SERVICE:
				if (customer.getLastVisit().isBefore(
						LocalDate.now().minusMonths(2))) {
					remindables.add(customer);
				}break;
			case MAINTENANCE:
				if (customer.getLastMaintenance().isBefore(
						LocalDate.now().minusMonths(6))) {
					remindables.add(customer);
				}break;
			default: return null;
			}
		}
		return remindables;
	}
	public void createPopup(Node children){
		  popup = new Popup();
	        popup.setX(300);
	        popup.setY(200);
	        popup.getContent().addAll(children);
	}
}

