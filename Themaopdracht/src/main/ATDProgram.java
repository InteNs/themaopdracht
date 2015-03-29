
package main;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import notifications.Notification;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import screens.CustomerScreen;
import screens.StockScreen;


public class ATDProgram extends Application {
	public enum notificationStyle {CONFIRM, NOTIFY}
	private Stage mainStage;
	private TabPane tabsScreen;
	private Tab customerAdministration;
	private Tab customerRelations;
	private Tab stockAdministration;
	private Scene mainScene;
	private Stock stock = new Stock();
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	private ArrayList<ProductSupplier> suppliers = new ArrayList<ProductSupplier>();
	@Override
	public void start(Stage stage) throws Exception {
		mainStage = stage;
		addContent();
		//create tabs and add content
		tabsScreen = new TabPane();

		customerAdministration = new Tab("Klantenbestand");
		customerAdministration.setClosable(false);
		customerAdministration.setContent(new CustomerScreen(this));
		

		customerRelations = new Tab("Herinneringen");
		customerRelations.setClosable(false);
		//TODO:customerRelations.setContent(arg0);

		stockAdministration = new Tab("Voorraadbeheer");
		stockAdministration.setClosable(false);
		stockAdministration.setContent(new StockScreen());
		
		tabsScreen.getTabs().addAll(customerAdministration, customerRelations,
				stockAdministration);

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
	private void addContent(){
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
		addorRemoveSupplier(new ProductSupplier("Cheapo BV", "1938572819", "Hoevelaan 2", "7853OQ", "Den Haag"), false);
		addorRemoveSupplier(new ProductSupplier("Banden BV", "8456297518", "Hamburgerstraat 10", "4198KW", "Utrecht"), false);
		addorRemoveproduct(new Product("Uitlaat", 5, 5, 20, 22,suppliers.get(0)), false);
		addorRemoveproduct(new Product("Band klein", 7, 10, 60, 100,suppliers.get(1)), false);
	}

	public static void main(String[] args) {;
		launch();
		}
	
	public Stage getStage(){
		return mainStage;
	}
	
}
