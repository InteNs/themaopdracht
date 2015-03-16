
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
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
	Customer customer;
	private TextField searchField;
	ListView<Customer> customerList;
	private Stock stock;
	private Button back, cancel;
	private VBox customerInput, customerDetails;
	private Scene  lastScene,mainScene, klantScene,newKlantScene, voorraadScene;	
	//1024x768
	public enum visit{SERVICE, MAINTENANCE};
	public static void main(String[] args) {launch();}
	@Override
	public void start(Stage stage) throws Exception {
		back = new Button("Hoofdmenu");
		back.setOnAction(e ->{
			lastScene = stage.getScene();
			stage.setScene(mainScene);
		});
		cancel = new Button("annuleren");
		cancel.setOnAction(e ->{
			stage.setScene(lastScene);
		});
		customer = new Customer("Jorrit Meulenbeld", "Utrecht", "NL35 INGB 0008 8953 57", null, "3552AZ", "3552AZ", "0636114939", "Omloop 48");
		customer = new Customer("Mark Havekes", "Utrecht", "NL35 INGB 0008 8953 57", null, "3552AZ", "3552AZ", "0636114939", "Omloop 48");
		customers = new ArrayList<Customer>();
		mechanics = new ArrayList<Mechanic>();
		reservations = new ArrayList<Reservation>();
		parkingSpaces = new ArrayList<ParkingSpace>();	
		stock = new Stock();
		//MAINSCENE
		Button klantbeheer = new Button("Klantenbeheer");
		klantbeheer.setMinSize(200, 200); 
		klantbeheer.setOnAction(e->{
			lastScene = stage.getScene();
			stage.setScene(klantScene);
		});
		Button voorraadbeheer = new Button("Voorraadbeheer");
		voorraadbeheer.setMinSize(200, 200);
		voorraadbeheer.setOnAction(e->{
			lastScene = stage.getScene();
			stage.setScene(voorraadScene);
		});
		HBox buttonBox = new HBox();
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setSpacing(10);
		buttonBox.getChildren().addAll(klantbeheer, voorraadbeheer);
		mainScene = new Scene(buttonBox,1024,768);
		stage.setScene(mainScene);
		stage.setTitle("AutoTotaalDienst");
		stage.setResizable(false);
		stage.show();
		//KLANTSCENE
		Button newCustomer = new Button("Nieuwe Klant");
		newCustomer.setMinSize(160, 40);
		newCustomer.setOnAction(e ->{
			lastScene = stage.getScene();
			stage.setScene(newKlantScene);
		});
		Button change = new Button("Aanpassen");
		change.setMinSize(160, 40);
		Button delete = new Button("Verwijderen");
		delete.setMinSize(160, 40);
		Button search = new Button("Zoeken");
		search.setMinSize(137, 40);
		search.setOnAction(e->{
			if (findCustomers(searchField.getText()) != null) {
				customerList.setItems(findCustomers(searchField.getText()));
			}
			else {
				customerList.setItems(getAllCustomers());
			}
		});
		searchField = new TextField("Zoek...");
		searchField.setMinSize(350, 40);
			//customer details
		VBox customerDetails = new VBox(20,new Label("Naam: "),new Label("Adres: "),new Label("Postcode: "),new Label("Plaats: "),new Label("Geboortedatum: "),new Label("Email: "),new Label("Telefoonnummer: "),new Label("Rekeningnummer: "),new Label("Blacklist: "));
		VBox customerDetailsContent = new VBox(20,new Label("Naam"),new Label("Adres"),new Label("Postcode"),new Label("Plaats"),new Label("geboortedatum"),new Label("Email"),new Label("Telefoonnummer"),new Label("Rekeningnummer"),new Label("Blacklist"));
		HBox customerInfo = new HBox(10,customerDetails,customerDetailsContent);
		customerList = new ListView<Customer>();
		customerList.setOrientation(Orientation.VERTICAL);
		customerList.setMinSize(492, 300);
		customerList.setItems(getAllCustomers());
		customerList.setOnMousePressed(e ->{
			customer = customerList.getSelectionModel().getSelectedItem();
			System.out.println(customerList.getSelectionModel().getSelectedItem());
			((Label)customerDetailsContent.getChildren().get(0)).setText(customer.getName());
			
			((Label)customerDetailsContent.getChildren().get(1)).setText(customer.getAdress());
			((Label)customerDetailsContent.getChildren().get(2)).setText(customer.getPostal());
			((Label)customerDetailsContent.getChildren().get(3)).setText(customer.getPlace());
			if (customer.getDateOfBirth() != null) ((Label)customerDetailsContent.getChildren().get(4)).setText(customer.getDateOfBirth().toString());
			else ((Label)customerDetailsContent.getChildren().get(4)).setText(" ");
			((Label)customerDetailsContent.getChildren().get(5)).setText(customer.getEmail());
			((Label)customerDetailsContent.getChildren().get(6)).setText(customer.getTel());
			((Label)customerDetailsContent.getChildren().get(7)).setText(customer.getBankAccount());
			if(customer.isOnBlackList()) ((Label)customerDetailsContent.getChildren().get(8)).setText("Ja");
			else ((Label)customerDetailsContent.getChildren().get(8)).setText("Nee");
		});
		VBox beheerBox = new VBox(20,new HBox(back),new HBox(20,customerList,customerInfo), new HBox(5,searchField,search), new HBox(6,newCustomer,change,delete));
		beheerBox.setPadding(new Insets(20));
		
			//tabs
		TabPane customerTabs = new TabPane();
		Tab beheer = new Tab("beheer");
		beheer.setClosable(false);
		beheer.setContent(beheerBox);
		Tab reminders = new Tab("herinneringen");
		reminders.setClosable(false);
		customerTabs.getTabs().addAll(beheer,reminders);
		klantScene = new Scene(customerTabs,1024,768);
		System.out.println(((Label)customerDetails.getChildren().get(0)).getText());
		//newKlant scene
		Button savenewCustomer = new Button("Oplsaan");
		savenewCustomer.setOnAction(e ->{
			Customer customer = new Customer(((TextField)customerInput.getChildren().get(0)).getText(), ((TextField)customerInput.getChildren().get(3)).getText(),((TextField)customerInput.getChildren().get(7)).getText(), ((DatePicker)customerInput.getChildren().get(4)).getValue(), ((TextField)customerInput.getChildren().get(5)).getText(),((TextField)customerInput.getChildren().get(2)).getText(), ((TextField)customerInput.getChildren().get(6)).getText(),((TextField)customerInput.getChildren().get(1)).getText());
			customers.add(customer);
			customerList.setItems(getAllCustomers());
			stage.setScene(klantScene);
		});
		customerInput = new VBox();
		customerInput.setSpacing(9);
		for (int i = 0; i < 8; i++) {
			if(i == 4){
				DatePicker dp = new DatePicker();
				customerInput.getChildren().add(dp);
			}
			else{
				TextField tf = new TextField();
				customerInput.getChildren().add(tf);
			}
		}
		HBox buttonBox1 = new HBox();
		buttonBox1.getChildren().addAll(cancel,savenewCustomer);
		VBox customerDetailsshort = new VBox(20,new Label("Naam: "),new Label("Adres: "),new Label("Postcode: "),new Label("Plaats: "),new Label("Geboortedatum: "),new Label("Email: "),new Label("Telefoonnummer: "),new Label("Rekeningnummer: "));
		VBox addCustomerBox = new VBox(new HBox(30,customerDetailsshort,customerInput),new HBox(40,cancel,savenewCustomer));
		addCustomerBox.setPadding(new Insets(20));
		addCustomerBox.setSpacing(20);
		newKlantScene = new Scene(addCustomerBox,1024,768);
		
		
	}
	public ObservableList<Customer> getAllCustomers(){
		return FXCollections.observableArrayList(customers);
	}
	public ObservableList<Customer> findCustomers(String input){
		ArrayList<Customer> list = new ArrayList<Customer>();
		for (Customer customer : customers) {
			if(customer.getEmail().equals(input) && !list.contains(customer)) list.add(customer);
			if(customer.getPostal().equals(input) && !list.contains(customer)) list.add(customer);
			if(customer.getName().equals(input) && !list.contains(customer)) list.add(customer);
		}
		return FXCollections.observableArrayList(list);
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

