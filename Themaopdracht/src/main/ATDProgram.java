
package main;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import screens.CustomerScreen;
import screens.InvoiceScreen;
import screens.MaintenanceScreen;
import screens.StockScreen;


public class ATDProgram extends Application {
	public enum notificationStyle {CONFIRM, NOTIFY, ENDSESSION, PRODUCTS, CUSTOMER, TANK, PARKING, MAINTENANCE, TYPE}
	private Stage mainStage;
	private TabPane tabsScreen;
	private Tab customerAdministration;
	private Tab stockAdministration;
	private Tab serviceScreen;
	private Tab invoiceScreen;
	private Scene mainScene;
	private Stock stock = new Stock();
	private ArrayList<MaintenanceSession> maintenanceSessions = new ArrayList<MaintenanceSession>();
	private ArrayList<Invoice> receipts = new ArrayList<Invoice>();
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	private ArrayList<Mechanic> mechanics = new ArrayList<Mechanic>();
	private ArrayList<ProductSupplier> suppliers = new ArrayList<ProductSupplier>();
	private ArrayList<ParkingSpace> parkingSpaces = new ArrayList<ParkingSpace>();
	private ArrayList<RefuelSession> refuelSessions = new ArrayList<RefuelSession>();
	private ArrayList<Reservation> reservations = new ArrayList<Reservation>();
	@Override
	public void start(Stage stage) throws Exception {
		mainStage = stage;
		addContent();
		//create tabs and add content
		tabsScreen = new TabPane();

		customerAdministration = new Tab("Klanten");
		customerAdministration.setClosable(false);
		customerAdministration.setContent(new CustomerScreen(this));
		
		stockAdministration = new Tab("Voorraad");
		stockAdministration.setClosable(false);
		stockAdministration.setContent(new StockScreen(this));
		
		serviceScreen = new Tab("Onderhoud");
		serviceScreen.setClosable(false);
		serviceScreen.setContent(new MaintenanceScreen(this));
		
		invoiceScreen = new Tab("Facturen");
		invoiceScreen.setClosable(false);
		invoiceScreen.setContent(new InvoiceScreen(this));
		tabsScreen.getTabs().addAll(customerAdministration,stockAdministration,serviceScreen,invoiceScreen);

		// Create Mainscreen
		mainScene = new Scene(tabsScreen, 1024, 655);
		stage.setScene(mainScene);
		stage.setTitle("AutoTotaalDienst");
		stage.setResizable(false);
		stage.show();
	}
	
	public ArrayList<Customer> getCustomers() {
		return customers;
	}
	public List<Customer> getRemindList(boolean Maintenace) {
		ArrayList<Customer> remindables = new ArrayList<Customer>();
		for (Customer customer : customers) {
			if(Maintenace){
				if (customer.getLastMaintenance().isBefore(
						LocalDate.now().minusMonths(6))) {
					remindables.add(customer);
				}
			}
			else{
				if (customer.getLastVisit().isBefore(
						LocalDate.now().minusMonths(2))) {
					remindables.add(customer);
				}
			}
		}
		return remindables;
	}
	public void addorRemoveRefuelSessions(RefuelSession refuelSession,boolean remove) {
		if (remove)refuelSessions.remove(refuelSession);
		else refuelSessions.add(refuelSession);
	}
	public ArrayList<Reservation> getReservations() {
		return reservations;
	}
	public void addorRemoveReservations(Reservation reservation, boolean remove) {
		if (remove)reservations.remove(reservation);
		else reservations.add(reservation);
	}
	public void addorRemoveCustomer(Customer customer, boolean remove){
		if(remove)customers.remove(customer);
		else customers.add(customer);
	}
	public ArrayList<Product> getProducts() {
		return stock.getAllProducts();
	}
	public void addorRemoveproduct(Product product, boolean remove){
		if(remove)stock.removeProduct(product);
		else stock.newProduct(product);
	}
	public ArrayList<ProductSupplier> getSuppliers() {
		return suppliers;
	}
	public void addorRemoveSupplier(ProductSupplier supplier, boolean remove){
		if(remove)suppliers.remove(supplier);
		else suppliers.add(supplier);
	}
	public ArrayList<Mechanic> getMechanics() {
		return mechanics;
	}
	public void addorRemoveMechanic(Mechanic mechanic, boolean remove) {
		if (remove)mechanics.remove(mechanic);
		else mechanics.add(mechanic);
	}
	public ArrayList<Invoice> getInvoices() {
		return receipts;
	}
	public void addorRemoveInvoice(Invoice receipt, boolean remove) {
		if(remove)receipts.remove(receipt);
		else receipts.add(receipt);
	}
	public ArrayList<MaintenanceSession> getMaintenanceSessions() {
		return maintenanceSessions;
	}
	public void addorRemoveMaintenanceSessions(MaintenanceSession maintenanceSession, boolean remove) {
		if(remove)maintenanceSessions.remove(maintenanceSession);
		else maintenanceSessions.add(maintenanceSession);
	}
	
	private void addContent(){
		for (int i = 0; i < 20; i++) {
			parkingSpaces.add(new ParkingSpace(i));
		}
		addorRemoveCustomer(new Customer("Jorrit Meulenbeld", "Utrecht",
				"NL35 INGB 0008 8953 57", LocalDate.parse("1990-08-25"), "jorritmeulenbeld@icloud.com",
				"3552AZ", "0636114939", "Omloop 48", false), false);
		addorRemoveCustomer(new Customer("Mark Havekes", "De Meern", "n.v.t.", LocalDate.parse("1990-05-31"),
				"mark.havekes@gmail.com", "3453MC", "0302801265",
				"De Drecht 32", false),false);
		customers.get(0).setLastMaintenance(LocalDate.now().minusMonths(7));
		customers.get(0).setLastVisit(		LocalDate.now().minusMonths(1));
		customers.get(1).setLastMaintenance(LocalDate.now().minusMonths(4));
		customers.get(1).setLastVisit(		LocalDate.now().minusMonths(3));
		addorRemoveMechanic(new Mechanic(1, "Jaap", 15.0), false);
		addorRemoveMechanic(new Mechanic(2, "Hans", 12.5), false);
		addorRemoveMechanic(new Mechanic(3, "Sjaak", 10.0), false);
		addorRemoveMechanic(new Mechanic(4, "Piet", 15.0), false);
		addorRemoveInvoice(new Invoice(), false);
		addorRemoveInvoice(new Invoice(), false);
		addorRemoveMaintenanceSessions(new MaintenanceSession(stock, LocalDate.now()), false);
		addorRemoveSupplier(new ProductSupplier("Cheapo BV", "Hoevelaan 2", "7853OQ", "Den Haag"), false);
		addorRemoveSupplier(new ProductSupplier("Banden BV", "Hamburgerstraat 10", "4198KW", "Utrecht"), false);
		addorRemoveSupplier(new ProductSupplier("Shell", "Aarde", "1337AF", "De Maan"), false);
		addorRemoveproduct(new Part("Uitlaat", 5, 5, 20, 22,suppliers.get(0)), false);
		addorRemoveproduct(new Part("Band klein", 7, 10, 60, 100,suppliers.get(1)), false);
		addorRemoveproduct(new Fuel("Diesel", 300, 200, 1, 1.19, suppliers.get(2)), false);
		addorRemoveproduct(new Fuel("Euro95", 275, 150, 1.11, 1.52, suppliers.get(2)), false);
		stock.orderProduct(stock.getAllProducts().get(0), 7);
	}

	public static void main(String[] args) {;
		launch();
		}
	public Stock getStock(){
		return stock;
	}
	public Stage getStage(){
		return mainStage;
	}
	
}
