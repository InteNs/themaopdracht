package main;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import screens.CustomerScreen;
import screens.StockScreen;


public class ATDProgram extends Application {

	private TabPane tabsScreen;
	private Tab customerAdministration;
	private Tab customerRelations;
	private Tab stockAdministration;
	private Scene mainScene;
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	private ArrayList<Product> products = new ArrayList<Product>();
	private ArrayList<ProductSupplier> suppliers = new ArrayList<ProductSupplier>();
	@Override
	public void start(Stage stage) throws Exception {
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
	public void addorRemoveCustomer(Customer customer, boolean remove){
		if(remove)customers.remove(customer);
		else customers.add(customer);
	}
	public ArrayList<Product> getProducts() {
		return products;
	}
	public void addorRemoveproduct(Product product, boolean remove){
		if(remove)products.remove(product);
		else products.add(product);
	}
	public ArrayList<ProductSupplier> getSuppliers() {
		return suppliers;
	}
	public void addorRemoveSupplier(ProductSupplier supplier, boolean remove){
		if(remove)suppliers.remove(supplier);
		else suppliers.add(supplier);
	}

	public static void main(String[] args) {
		launch();
		}
	
}
