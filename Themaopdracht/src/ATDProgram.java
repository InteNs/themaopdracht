
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ATDProgram extends Application {
	private ArrayList<Customer> customers;
	private ArrayList<Mechanic> mechanics;
	private ArrayList<Reservation> reservations;
	private ArrayList<ParkingSpace> parkingSpaces;
	ObservableList<Customer> subentries = FXCollections.observableArrayList();
	private boolean isChanging;
	private ArrayList<Customer> foundCustomers;
	Customer customer;
	private TextField searchField,tf;
	ListView<Customer> customerList;
	private Stock stock;
	private HBox buttonBox,buttonBox1,customerInfo;
	private Stage mainStage;
	private Customer selectedCustomer;
	private Customer customer1,customer2;
	private Button back,cancel,delete,change,newCustomer,voorraadbeheer,klantbeheer,savenewCustomer;
	private VBox customerInput, customerDetails;
	private Scene  lastScene,mainScene, klantScene,newKlantScene, voorraadScene;
	private VBox customerDetailsshort,addCustomerBox,beheerBox,customerDetailsContent;
	private DatePicker dp;
	private TabPane customerTabs;
	private Tab reminders,beheer;


	//1024x768
	public enum visit{SERVICE, MAINTENANCE};
	public static void main(String[] args) {launch();}
	@Override
	public void start(Stage stage) throws Exception {
		this.mainStage = stage;
		back = new Button("Hoofdmenu");
		back.setOnAction(e ->{
			lastScene = stage.getScene();
			stage.setScene(mainScene);
		});
		cancel = new Button("annuleren");
		cancel.setOnAction(e ->{
			stage.setScene(lastScene);
		});
		customers = new ArrayList<Customer>();
		foundCustomers = new ArrayList<Customer>();
		mechanics = new ArrayList<Mechanic>();
		reservations = new ArrayList<Reservation>();
		parkingSpaces = new ArrayList<ParkingSpace>();	
		stock = new Stock();
		customer1 = new Customer("Jorrit Meulenbeld", "Utrecht", "NL35 INGB 0008 8953 57",null, "3552AZ", "3552AZ", "0636114939", "Omloop 48");customers.add(customer1);
		customer2 = new Customer("Mark Havekes", "Utrecht", "NL35 INGB 0008 8953 57", null, "3552AZ", "3552AZ", "0636114939", "Omloop 48");customers.add(customer2);
		//MAINSCENE
		klantbeheer = new Button("Klantenbeheer");
		klantbeheer.setMinSize(200, 200); 
		klantbeheer.setOnAction(e->{
			subentries.addAll(getAllCustomers());
			lastScene = stage.getScene();
			stage.setScene(klantScene);
		});
		voorraadbeheer = new Button("Voorraadbeheer");
		voorraadbeheer.setMinSize(200, 200);
		voorraadbeheer.setOnAction(e->{
			lastScene = stage.getScene();
			stage.setScene(voorraadScene);
		});
		buttonBox = new HBox();
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setSpacing(10);
		buttonBox.getChildren().addAll(klantbeheer, voorraadbeheer);
		mainScene = new Scene(buttonBox,1024,768);
		stage.setScene(mainScene);
		stage.setTitle("AutoTotaalDienst");
		stage.setResizable(false);
		stage.show();
		//KLANTSCENE
		newCustomer = new Button("Nieuwe Klant");
		newCustomer.setMinSize(160, 40);
		newCustomer.setOnAction(e ->{
			lastScene = stage.getScene();
			stage.setScene(newKlantScene);
		});
		change = new Button("Aanpassen");
		change.setOnAction(e->{
			change();
		});
		change.setMinSize(160, 40);
		delete = new Button("Verwijderen");
		delete.setOnAction(e->{
			deleteCustomer();
		});
		
		
		delete.setMinSize(160, 40);
		searchField = new TextField("Zoek...");
		searchField.setMinSize(350, 40);
		searchField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String newValue) {

		    	handleSearchByKey(oldValue,newValue);
		    }
		});
			//customer details
		customerDetails = new VBox(20,new Label("Naam: "),new Label("Adres: "),new Label("Postcode: "),new Label("Plaats: "),new Label("Geboortedatum: "),new Label("Email: "),new Label("Telefoonnummer: "),new Label("Rekeningnummer: "),new Label("Blacklist: "));
		  customerDetailsContent = new VBox(20,new Label("Naam"),new Label("Adres"),new Label("Postcode"),new Label("Plaats"),new Label("geboortedatum"),new Label("Email"),new Label("Telefoonnummer"),new Label("Rekeningnummer"),new Label("Blacklist"));
		  customerInfo = new HBox(10,customerDetails,customerDetailsContent);
		  customerList = new ListView<Customer>();
		  customerList.setOrientation(Orientation.VERTICAL);
		  customerList.setMinSize(492, 300);
		  customerList.setItems(getAllCustomers());
		  customerList.setOnMousePressed(e ->{
			  selectListEntry();
		  });
		  beheerBox = new VBox(20,new HBox(back),new HBox(20,customerList,customerInfo), new HBox(5,searchField), new HBox(6,newCustomer,change,delete));
		  beheerBox.setPadding(new Insets(20));
		
			//tabs
		customerTabs = new TabPane();
		beheer = new Tab("beheer");
		beheer.setClosable(false);
		beheer.setContent(beheerBox);
		reminders = new Tab("herinneringen");
		reminders.setClosable(false);
		customerTabs.getTabs().addAll(beheer,reminders);
		klantScene = new Scene(customerTabs,1024,768);
		//newKlant scene
		savenewCustomer = new Button("Opslaan");
		savenewCustomer.setOnAction(e ->{
			saveCustomer();
		});
		customerInput = new VBox();
		customerInput.setSpacing(9);
		for (int i = 0; i < 8; i++) {
			if(i == 4){
				dp = new DatePicker();
				customerInput.getChildren().add(dp);
			}
			else{
				tf = new TextField();
				customerInput.getChildren().add(tf);
			}
		}
		buttonBox1 = new HBox();
		buttonBox1.getChildren().addAll(cancel,savenewCustomer);
		customerDetailsshort = new VBox(20,new Label("Naam: "),new Label("Adres: "),new Label("Postcode: "),new Label("Plaats: "),new Label("Geboortedatum: "),new Label("Email: "),new Label("Telefoonnummer: "),new Label("Rekeningnummer: "));
		addCustomerBox = new VBox(new HBox(30,customerDetailsshort,customerInput),new HBox(40,cancel,savenewCustomer));
		addCustomerBox.setPadding(new Insets(20));
		addCustomerBox.setSpacing(20);
		newKlantScene = new Scene(addCustomerBox,1024,768);
		
		
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
			isChanging = false;
		}
		customer = new Customer(((TextField)customerInput.getChildren().get(0)).getText(), ((TextField)customerInput.getChildren().get(3)).getText(),((TextField)customerInput.getChildren().get(7)).getText(), ((DatePicker)customerInput.getChildren().get(4)).getValue(), ((TextField)customerInput.getChildren().get(5)).getText(),((TextField)customerInput.getChildren().get(2)).getText(), ((TextField)customerInput.getChildren().get(6)).getText(),((TextField)customerInput.getChildren().get(1)).getText());
		customers.add(customer);
		customerList.setItems(getAllCustomers());
		mainStage.setScene(klantScene);
		
	}
	private void change() {
		if(customerList.getSelectionModel().getSelectedItem() != null){
			selectedCustomer = customerList.getSelectionModel().getSelectedItem();
			((TextField)customerInput.getChildren().get(0)).setText(selectedCustomer.getName());
			((TextField)customerInput.getChildren().get(1)).setText(selectedCustomer.getAdress());
			((TextField)customerInput.getChildren().get(2)).setText(selectedCustomer.getPostal());
			((TextField)customerInput.getChildren().get(3)).setText(selectedCustomer.getPlace());
			((DatePicker)customerInput.getChildren().get(4)).setValue(selectedCustomer.getDateOfBirth());
			((TextField)customerInput.getChildren().get(5)).setText(selectedCustomer.getEmail());
			((TextField)customerInput.getChildren().get(6)).setText(selectedCustomer.getTel());
			((TextField)customerInput.getChildren().get(7)).setText(selectedCustomer.getBankAccount());
			isChanging = true;
			mainStage.setScene(newKlantScene);
		}
		
	}
	public ObservableList<Customer> getAllCustomers(){
		return FXCollections.observableArrayList(customers);
	}
	public ObservableList<Customer> findCustomers(String input){
		foundCustomers.clear();
		for (Customer customer : customers) {
			if(customer.getEmail().equals(input) && !foundCustomers.contains(customer)) foundCustomers.add(customer);
			if(customer.getPostal().equals(input) && !foundCustomers.contains(customer)) foundCustomers.add(customer);
			if(customer.getName().equals(input) && !foundCustomers.contains(customer)) foundCustomers.add(customer);
		}
		return FXCollections.observableArrayList(foundCustomers);
	}
	public void handleSearchByKey(String oldVal, String newVal) {
	    // If the number of characters in the text box is less than last time
	    // it must be because the user pressed delete
	    if ( oldVal != null && (newVal.length() < oldVal.length()) ) {
	        // Restore the lists original set of entries 
	        // and start from the beginning
	        customerList.setItems(getAllCustomers());
	    }
	     
	    // Change to upper case so that case is not an issue
	    //newVal = newVal.toUpperCase();
	    // Filter out the entries that don't contain the entered text
	    for ( Customer entry: getAllCustomers() ) {
	        if ( !entry.getName().contains(newVal) ) {
	        	customerList.getItems().remove(entry);;
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
				}
				break;
			case MAINTENANCE:
				if (customer.getLastMaintenance().isBefore(
						LocalDate.now().minusMonths(6))) {
					remindables.add(customer);
				}
				break;
			default: return null;
			}

		}
		return remindables;
	}

}

