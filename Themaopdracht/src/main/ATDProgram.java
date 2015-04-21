
package main;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import screens.CustomerScreen;
import screens.FinancesScreen;
import screens.InvoiceScreen;
import screens.MaintenanceScreen;
import screens.ParkingScreen;
import screens.StockScreen;


public class ATDProgram extends Application {
	private Stage mainStage;
	private TabPane tabsScreen;
	private Tab customerAdministration;
	private Tab stockAdministration;
	private Tab serviceScreen;
	private Tab invoiceScreen;
	private Tab financesScreen;
	private Tab parkingScreen;
	private Scene mainScene;
	private Stock stock = new Stock();
	private ArrayList<MaintenanceSession> maintenanceSessions = new ArrayList<MaintenanceSession>();
	private ArrayList<Invoice> receipts = new ArrayList<Invoice>();
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	private ArrayList<Mechanic> mechanics = new ArrayList<Mechanic>();
	private ArrayList<Order> orders = new ArrayList<Order>();
	private ArrayList<ProductSupplier> suppliers = new ArrayList<ProductSupplier>();
	private ArrayList<ParkingSpace> parkingSpaces = new ArrayList<ParkingSpace>();
	private ArrayList<Reservation> reservations = new ArrayList<Reservation>();
	public final static NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("nl", "NL"));
	@Override
	public void start(Stage stage) throws Exception {
		mainStage = stage;
		nf.setCurrency(Currency.getInstance("EUR"));
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
		
		financesScreen = new Tab("Financien");
		financesScreen.setClosable(false);
		financesScreen.setContent(new FinancesScreen(this));
		
		parkingScreen = new Tab("Parkeren");
		parkingScreen.setClosable(false);
		parkingScreen.setContent(new ParkingScreen(this));
		
		tabsScreen.getTabs().addAll(financesScreen,invoiceScreen,customerAdministration,stockAdministration,serviceScreen,parkingScreen);
		tabsScreen.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
			((screens.Screen)newValue.getContent()).refreshList();
		});
		// Create Mainscreen
		mainScene = new Scene(tabsScreen);
		mainScene.getStylesheets().add("application.css");
		stage.setScene(mainScene);
		stage.setTitle("AutoTotaalDienst");
		stage.sizeToScene();
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
	public ArrayList<Order> getOrders() {
		return orders;
	}
	public void addorRemoveOrder(Order order, boolean remove){
		if(remove)orders.remove(order);
		else orders.add(order);
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
	
	public ArrayList<ParkingSpace> getParkingSpaces() {
		return parkingSpaces;
	}

	public void addorRemoveMaintenanceSessions(MaintenanceSession maintenanceSession, boolean remove) {
		if(remove)maintenanceSessions.remove(maintenanceSession);
		else maintenanceSessions.add(maintenanceSession);
	}
	public static String convert(double price){
		return nf.format(price);
	}
	public static boolean isOverlapping(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
		return (start1.isBefore(end2) || start1.isEqual(end2)) && (start2.isBefore(end1) || start2.isEqual(end1));
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
		addorRemoveCustomer(new Customer("Taoufik Abou", "Utrecht",
				"NL35 INGB 0001 1234 56", LocalDate.parse("1997-08-08"), "taoufik.abou@live.nl",
				"3550AA", "0612345678", "Mijnstraat 123", false), false);
		addorRemoveCustomer(new Customer("Oussama Aalili", "Utrecht", "n.v.t.", LocalDate.parse("1995-08-25"),
				"oussama.aalili@live.nl", "3563CR", "0690874563",
				"Drielenbrochdreef 11", false),false);
		addorRemoveCustomer(new Customer("Jantje Bont", "Utrecht",
				"NL35 INGB 0128 4553 66", LocalDate.parse("1980-09-07"), "jantje.bont@live.nl",
				"3535AA", "0656789032", "Walstraat 123", false), false);
		addorRemoveCustomer(new Customer("Kees Jansma", "Dordrecht", "n.v.t.", LocalDate.parse("1970-07-12"),
				"kees.jansma@gmail.com", "3400BB", "0678324211",
				"Kiboltstraat 12", false),false);
		addorRemoveCustomer(new Customer("Jeroen van der Boom", "Vianen",
				"NL35 ABNA 0056 8953 57", LocalDate.parse("1989-08-25"), "jeroen.vanderboom@live.nl",
				"3552AZ", "0636114939", "Jeroenstraat 11", false), false);
		addorRemoveCustomer(new Customer("Frans Bouwer", "Maastricht", "n.v.t.", LocalDate.parse("1975-05-31"),
				"frans.bouwer@gmail.com", "3451AC", "0690324576",
				"Franslaan 89", false),false);
		addorRemoveCustomer(new Customer("Ali Bouali", "Nieuwegein",
				"NL35 ABNA 0067 8953 57", LocalDate.parse("1945-08-25"), "ali.bouali@icloud.com",
				"3560AZ", "0690125467", "Groteneusdreef 10", false), false);
		addorRemoveCustomer(new Customer("Jan Shit", "Amsterdam", "n.v.t.", LocalDate.parse("1965-05-31"),
				"jan.shit@gmail.com", "3745BC", "0301234567",
				"Wallendreef 34", false),false);
		addorRemoveCustomer(new Customer("Arjen Robben", "Groningen",
				"NL35 INGB 0208 5953 55", LocalDate.parse("1978-01-30"), "arjenrobben@icloud.com",
				"2544AZ", "0637124999", "kalekop 11", false), false);
		addorRemoveCustomer(new Customer("Robin van Persie", "Rotterdam", "n.v.t.", LocalDate.parse("1979-05-31"),
				"robin.vanpersie@gmail.com", "3853MC", "0672831215",
				"", false),false);
		addorRemoveCustomer(new Customer("Wesley Sneijder", "Utrecht",
				"NL35 INGB 0031 1134 78", LocalDate.parse("1987-08-08"), "wesley.sneijder@live.nl",
				"3230AA", "0622375668", "Ondiepstraat 11", false), false);
		addorRemoveCustomer(new Customer("Klaas-Jan Huntelaar", "Abcoude", "n.v.t.", LocalDate.parse("1989-08-25"),
				"klaasjan.huntelaar@live.nl", "3134CR", "0620174413",
				"schalkestraat 11", false),false);
		addorRemoveCustomer(new Customer("Memphis Depay", "Leiden",
				"NL35 INGB 0118 4253 56", LocalDate.parse("1990-09-07"), "memphisdepay@live.nl",
				"3333AA", "0645769232", "wcstraat 13", false), false);
		addorRemoveCustomer(new Customer("Daley Blind", "Amsterdam", "n.v.t.", LocalDate.parse("1991-07-12"),
				"daley.blind@gmail.com", "3441BB", "0678624321",
				"Kilostraat 12", false),false);
		addorRemoveCustomer(new Customer("Tim Krul", "Vianen",
				"NL65 ABNA 0156 8653 51", LocalDate.parse("1989-08-25"), "tim.krul@live.nl",
				"3553AZ", "0616113931", "mexicostraat 11", false), false);
		addorRemoveCustomer(new Customer(" Bas Dost", "Den Haag", "n.v.t.", LocalDate.parse("1975-05-31"),
				"Bas.Dost@gmail.com", "3451AC", "0690324576",
				"Englandlaan 89", false),false);
		addorRemoveCustomer(new Customer("Leo Messi", "Nieuwegein",
				"NL23 ABNA 0267 8153 37", LocalDate.parse("1988-08-25"), "leo.messi@icloud.com",
				"3564AZ", "0690421467", "Kleineneusdreef 10", false), false);
		addorRemoveCustomer(new Customer("Andre Hazes", "Amsterdam", "n.v.t.", LocalDate.parse("1957-05-31"),
				"andre.hazes@gmail.com", "3715BC", "0621234167",
				"legendedreef 12", false),false);
		
		
		
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
		addorRemoveMaintenanceSessions(new MaintenanceSession("13-LOL-3",stock, LocalDate.now()), false);
		addorRemoveMaintenanceSessions(new MaintenanceSession("65-RGB-1",stock, LocalDate.now().plusDays(4)), false);
		addorRemoveSupplier(new ProductSupplier("Cheapo BV", "Hoevelaan 2", "7853OQ", "Den Haag"), false);
		addorRemoveSupplier(new ProductSupplier("Banden BV", "Hamburgerstraat 10", "4198KW", "Utrecht"), false);
		addorRemoveSupplier(new ProductSupplier("Peugeot", "Parijslaan", "2353PL", "Amsterdam"), false);
		addorRemoveSupplier(new ProductSupplier("Mercedes", "Berlijnstraat 45", "5462KY", "Rotterdam"), false);
		addorRemoveSupplier(new ProductSupplier("Shell", "Hoevenlaan 99", "1337AF", "Eindhoven"), false);
		addorRemoveproduct(new Part("Uitlaat", 5, 5, 20, 22,suppliers.get(0)), false);
		addorRemoveproduct(new Part("Band klein", 7, 10, 60, 100,suppliers.get(1)), false);
		addorRemoveproduct(new Part("Accu", 80, 85, 180, 220,suppliers.get(2)), false);
		addorRemoveproduct(new Part("Airco", 70, 100, 200, 230,suppliers.get(3)), false);
		addorRemoveproduct(new Part("Band groot", 50, 50, 25, 52,suppliers.get(0)), false);
		addorRemoveproduct(new Part("Velgen klein", 9, 10, 60, 120,suppliers.get(1)), false);
		addorRemoveproduct(new Part("Velgen groot", 20, 27, 20, 22,suppliers.get(2)), false);
		addorRemoveproduct(new Part("Besturingsysteem", 4, 10, 110, 250,suppliers.get(3)), false);
		addorRemoveproduct(new Part("Brandstofsysteem", 30, 50, 60, 80,suppliers.get(0)), false);
		addorRemoveproduct(new Part("Dashboard", 15, 20, 60, 100,suppliers.get(1)), false);
		addorRemoveproduct(new Part("Schakelaar", 20, 20, 20, 35,suppliers.get(2)), false);
		addorRemoveproduct(new Part("Kabels", 78, 80, 6, 12,suppliers.get(3)), false);
		addorRemoveproduct(new Part("Filter", 40, 50, 20, 22,suppliers.get(0)), false);
		addorRemoveproduct(new Part("Bekleding", 26, 30, 40, 100,suppliers.get(1)), false);
		addorRemoveproduct(new Part("Interieur", 50, 50, 250, 320,suppliers.get(2)), false);
		addorRemoveproduct(new Part("Motor", 70, 75, 300, 400,suppliers.get(3)), false);
		addorRemoveproduct(new Part("Ophanging", 10, 15, 75, 88,suppliers.get(0)), false);
		addorRemoveproduct(new Part("Onderstel", 7, 10, 60, 100,suppliers.get(1)), false);
		addorRemoveproduct(new Part("Remmen", 50, 65, 80, 120,suppliers.get(2)), false);
		addorRemoveproduct(new Part("Aandrijving", 20, 25, 60, 100,suppliers.get(3)), false);
		addorRemoveproduct(new Part("Ruiten", 40, 50, 20, 40,suppliers.get(0)), false);
		addorRemoveproduct(new Part("Toebehoren Ruiten", 40, 50, 20, 40,suppliers.get(1)), false);
		addorRemoveproduct(new Part("Spiegels", 25, 30, 20, 32,suppliers.get(2)), false);
		addorRemoveproduct(new Part("Transmissie en toebehoren", 50, 60, 30, 40,suppliers.get(3)), false);
		addorRemoveproduct(new Part("Transmissie", 25, 30, 220, 332,suppliers.get(0)), false);
		addorRemoveproduct(new Part("Lampen", 35, 40, 2, 5,suppliers.get(1)), false);
		addorRemoveproduct(new Part("Koplamp voor", 35, 40, 20, 42,suppliers.get(2)), false);
		addorRemoveproduct(new Part("Koplamp achter", 35, 40, 60, 90,suppliers.get(3)), false);
		addorRemoveproduct(new Part("Autosleutel", 5, 20, 15, 25,suppliers.get(0)), false);
		addorRemoveproduct(new Part("Winterband groot", 20, 10, 60, 100,suppliers.get(1)), false);
		addorRemoveproduct(new Part("Winterband klein", 20, 5, 50, 90,suppliers.get(2)), false);
		addorRemoveproduct(new Part("Sneeuwketting groot", 20, 15, 60, 100,suppliers.get(3)), false);
		addorRemoveproduct(new Part("Sneeuwketting klein", 20, 15, 50, 90,suppliers.get(0)), false);
		addorRemoveproduct(new Part("Schroeven extra groot", 8, 10, 5, 10,suppliers.get(1)), false);
		addorRemoveproduct(new Part("Schroeven groot", 8, 10, 4, 8,suppliers.get(2)), false);
		addorRemoveproduct(new Part("Schroeven middelmaat", 8, 3, 6, 100,suppliers.get(3)), false);
		addorRemoveproduct(new Part("Schroeven klein", 8, 10, 2, 4,suppliers.get(0)), false);
		addorRemoveproduct(new Part("Schroeven extra klein", 8, 10, 1, 2,suppliers.get(1)), false);
		addorRemoveproduct(new Part("Radio duur", 10, 20, 50, 75,suppliers.get(2)), false);
		addorRemoveproduct(new Part("Radio normaal", 7, 10, 30, 55,suppliers.get(3)), false);
		addorRemoveproduct(new Part("Radio goedkoop", 5, 5, 15, 20,suppliers.get(0)), false);
		addorRemoveproduct(new Part("Radio extra goedkoop", 7, 10, 10, 15,suppliers.get(1)), false);
		addorRemoveproduct(new Part("Velgen extra duur", 20, 20, 30, 62,suppliers.get(2)), false);
		addorRemoveproduct(new Part("Besturingsysteem duur", 20, 30, 150, 250,suppliers.get(3)), false);
		addorRemoveproduct(new Part("Brandstofsysteem duur", 20, 40, 200, 350,suppliers.get(0)), false);
		addorRemoveproduct(new Part("Dashboard classic", 7, 10, 60, 100,suppliers.get(1)), false);
		addorRemoveproduct(new Part("doekjes", 5, 5,  1, 3,suppliers.get(2)), false);
		addorRemoveproduct(new Part("ververs olie", 150, 200, 6, 10,suppliers.get(3)), false);
		addorRemoveproduct(new Part("Reiningmiddel", 150, 200, 2, 5,suppliers.get(0)), false);
		addorRemoveproduct(new Part("Schoonmaakmiddel", 200, 300, 3, 6,suppliers.get(1)), false);
		addorRemoveproduct(new Fuel("Diesel", 300, 200, 1, 1.19, suppliers.get(4)), false);
		addorRemoveproduct(new Fuel("Euro95", 275, 150, 1.11, 1.52, suppliers.get(4)), false);
		addorRemoveproduct(new Fuel("Euro98", 271, 250, 1, 1.69, suppliers.get(4)), false);
		addorRemoveproduct(new Fuel("Lpg", 275, 150, 1.11, 1.12, suppliers.get(4)), false);
		addorRemoveproduct(new Fuel("Electriteit", 300, 200, 1, 2.19, suppliers.get(4)), false);
		addorRemoveproduct(new Fuel("Waterstof", 275, 150, 1.11, 8.62, suppliers.get(4)), false);
		addorRemoveReservations(new Reservation(LocalDate.parse("2015-03-29"), LocalDate.parse("2015-04-20"), "13-edm-1", parkingSpaces.get(0)), false);
		addorRemoveReservations(new Reservation(LocalDate.parse("2015-04-01"), LocalDate.parse("2015-04-04"), "69-lmr-7", parkingSpaces.get(1)), false);
		addorRemoveReservations(new Reservation(LocalDate.parse("2015-04-04"), LocalDate.parse("2015-04-09"), "31-dos-3", parkingSpaces.get(2)), false);
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
