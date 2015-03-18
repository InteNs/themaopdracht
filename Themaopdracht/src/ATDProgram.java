import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class ATDProgram extends Application {
	// Arraylists van klassen
	private ArrayList<Customer> customers;
	private ArrayList<Mechanic> mechanics;
	private ArrayList<Reservation> reservations;
	private ArrayList<ParkingSpace> parkingSpaces;
	// Bouwen Scherm
	// Stage en Scenes
	private Stage mainStage;
	private Scene klantScene;

	// Objecten op de Scenes
	private TabPane tabsScreen;
	private Tab customerRelations, customerAdministration, stockAdministration;
	// klantensysteem
	private HBox createNewCustomerButtonBox, customerNotificationLabelBox,
			customerNotificationButtonBox;
	private VBox customerDetails, customerDetailsShort, customerDetailsContent,
			addCustomerBox, customerInput, leftCustomerAdministrationBox,
			rightCustomerAdministrationBox, customerInfo, customerNotifications;
	private TextField searchFieldCustomer, textField, name, address, postal, email,
			place, phone, bank;
	private DatePicker datePicker;
	private CheckBox checkBox;
	private Button cancelCustomer, deleteCustomer, changeCustomer,
			createNewCustomer, saveNewCustomer,
			cancelNotification = new Button(),
			agreeNotification = new Button();
	// voorraad systeem
	private HBox createNewStockButtonBox, stockNotificationLabelBox,
			stockNotificationButtonBox;
	private VBox stockDetails, stockDetailsContent,
			addStockBox, stockInput, leftStockBox,
			rightStockBox, stockInfo, stockNotifications;
	private TextField searchFieldStock, nameStock, amountStock, minAmountStock,
			priceStock, buyPriceStock, nameSupplierStock, addressSupplierStock,
			postalSupplierStock, placeSupplierStock, phoneSupplierStock;
	private Button cancelStock, deleteStock, changeStock, createNewStock,
			saveNewStock;
	// onderhoud
	// parkeergelegenheid

	// Klassen
	// klantensysteem
	private Customer customer;
	private Customer selectedCustomer;
	private Customer customer1, customer2;
	ListView<Customer> customerList;
	// voorraad systeem
	private Stock stock;
	private Stock selectedStock;
	private Stock stock1, stock2;
	ListView<Product> stockList;
	// onderhoud
	// parkeergelegenheid

	// Hulpmiddelen methoden
	// klantensysteem
	private Label notificationLabel = new Label();
	private boolean isChanging;
	private boolean verifyFieldsGo = true;
	private String verifyFields = "";

	public enum visit {
		SERVICE, MAINTENANCE
	};

	public static final Pattern EMAILPATTERN = Pattern.compile(
			"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern NAMEPATTERN = Pattern.compile(
			"^([ \\u00c0-\\u01ffa-zA-Z'\\-])+$", Pattern.CASE_INSENSITIVE);
	public static final Pattern PHONEPATTERN = Pattern.compile(
			"^[0-9+\\(\\)#\\.\\s\\/ext-]+$", Pattern.CASE_INSENSITIVE);

	// voorraad systeem
	// onderhoud
	// parkeergelegenheid

	// Start Methode
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		// om scenes te kunnen wisselen e.d.
		this.mainStage = stage;

		// creeren testobjecten
		// klantensysteem
		customers = new ArrayList<Customer>();
		customer1 = new Customer("Jorrit Meulenbeld", "Utrecht",
				"NL35 INGB 0008 8953 57", null, "jorritmeulenbeld@icloud.com",
				"3552AZ", "0636114939", "Omloop 48", false);
		customers.add(customer1);
		customer2 = new Customer("Mark Havekes", "De Meern", "n.v.t.", null,
				"mark.havekes@gmail.com", "3453MC", "0302801265",
				"De Drecht 32", false);
		customers.add(customer2);

		// voorraad systeem
		stock = new Stock();

		// onderhoud
		mechanics = new ArrayList<Mechanic>();

		// parkeergelegenheid
		reservations = new ArrayList<Reservation>();
		parkingSpaces = new ArrayList<ParkingSpace>();

		// creeren objecten voor op de stages

		// Buttons
		// klantensysteem
		createNewCustomer = new Button("Nieuwe Klant");
		createNewCustomer.setMinSize(160, 40);
		changeCustomer = new Button("Aanpassen");
		changeCustomer.setMinSize(160, 40);
		deleteCustomer = new Button("Verwijderen");
		deleteCustomer.setMinSize(160, 40);

		cancelCustomer = new Button("Annuleren");
		cancelCustomer.setMinSize(160, 40);
		saveNewCustomer = new Button("Opslaan");
		saveNewCustomer.setMinSize(160, 40);

		// voorraad systeem
		createNewStock = new Button("Nieuw Artikel");
		createNewStock.setMinSize(160, 40);
		changeStock = new Button("Aanpassen");
		changeStock.setMinSize(160, 40);
		deleteStock = new Button("Verwijderen");
		deleteStock.setMinSize(160, 40);

		cancelStock = new Button("Annuleren");
		cancelStock.setMinSize(160, 40);
		saveNewStock = new Button("Opslaan");
		saveNewStock.setMinSize(160, 40);
		// onderhoud
		// parkeergelegenheid

		// Lijsten
		// klantensysteem
		customerList = new ListView<Customer>();
		customerList.setOrientation(Orientation.VERTICAL);
		customerList.setMinSize(492, 300);
		customerList.setItems(getAllCustomers());
		// voorraad systeem
		stockList = new ListView<Product>();
		stockList.setOrientation(Orientation.VERTICAL);
		stockList.setMinSize(492, 300);
		stockList.setItems(getAllProducts());
		// onderhoud
		// parkeergelegenheid

		// textfields
		// klantensysteem
		searchFieldCustomer = new TextField("Zoek...");
		searchFieldCustomer.setMinSize(492, 40);
		// voorraad systeem
		searchFieldStock = new TextField("Zoek...");
		searchFieldStock.setMinSize(492, 40);
		// onderhoud
		// parkeergelegenheid

		// Boxes
		// klantensysteem
		// rechter scherm info
		// default
		customerDetails = new VBox(20, new Label("Naam: "),
				new Label("Adres: "), new Label("Postcode: "), new Label(
						"Plaats: "), new Label("Geboortedatum: "), new Label(
						"Email: "), new Label("Telefoonnummer: "), new Label(
						"Rekeningnummer: "), new Label("Blacklist: "));
		customerDetails.setPadding(new Insets(5, 20, 0, 0));
		// waarden selected
		customerDetailsContent = new VBox(20, new Label("Naam"), new Label(
				"Adres"), new Label("Postcode"), new Label("Plaats"),
				new Label("geboortedatum"), new Label("Email"), new Label(
						"Telefoonnummer"), new Label("Rekeningnummer"),
				new Label("Blacklist"));
		customerDetailsContent.setPadding(new Insets(5, 20, 0, 0));
		// samenvoegen
		customerInfo = new VBox(10, new HBox(10, customerDetails,
				customerDetailsContent));
		customerInfo.setPadding(new Insets(20));
		customerInfo
				.setStyle("-fx-background-color: white; -fx-border: solid; -fx-border-color: lightgray;");
		customerInfo.setMinSize(472, 400);

		// rechter scherm aanpassen/aanmaken
		// default
		customerDetailsShort = new VBox(20, new Label("Naam: "), new Label(
				"Adres: "), new Label("Postcode: "), new Label("Plaats: "),
				new Label("Geboortedatum: "), new Label("Email: "), new Label(
						"Telefoonnummer: "), new Label("Rekeningnummer: "));
		customerDetailsShort.setPadding(new Insets(20));
		// Textfields, Datepicker en checkbox maken
		customerInput = new VBox();
		customerInput.setSpacing(10);
		for (int i = 0; i < 8; i++) {
			if (i == 4) {
				datePicker = new DatePicker();
				customerInput.getChildren().add(datePicker);
			}
			if (i == 7) {
				checkBox = new CheckBox();
				checkBox.setMinSize(25, 25);
				customerInput.getChildren().add(checkBox);
			} else {
				textField = new TextField();
				customerInput.getChildren().add(textField);
			}
		}
		name = ((TextField) customerInput.getChildren().get(0));
		place = ((TextField) customerInput.getChildren().get(3));
		bank = ((TextField) customerInput.getChildren().get(7));
		email = ((TextField) customerInput.getChildren().get(5));
		postal = ((TextField) customerInput.getChildren().get(2));
		phone = ((TextField) customerInput.getChildren().get(6));
		address = ((TextField) customerInput.getChildren().get(1));
		// knoppen
		createNewCustomerButtonBox = new HBox();
		createNewCustomerButtonBox.getChildren().addAll(cancelCustomer,
				saveNewCustomer);
		// samenvoegen
		addCustomerBox = new VBox(new HBox(30, customerDetailsShort,
				customerInput), new HBox(40, cancelCustomer, saveNewCustomer));
		addCustomerBox.setPadding(new Insets(20));
		addCustomerBox.setSpacing(20);

		// rechter scherm meldingen
		customerNotifications = new VBox(10, customerNotificationLabelBox = new HBox(
				notificationLabel), customerNotificationButtonBox = new HBox(20));
		customerNotificationLabelBox.setAlignment(Pos.CENTER);
		customerNotificationButtonBox.setAlignment(Pos.CENTER);
		customerNotifications
				.setStyle("-fx-background-color: white; -fx-border: solid; -fx-border-color: lightgray;");
		customerNotifications.setAlignment(Pos.CENTER);
		customerNotifications.setMinSize(472, 100);

		// rechter en linker scherm plaatsen
		leftCustomerAdministrationBox = new VBox(20, customerList, searchFieldCustomer,
				new HBox(6, createNewCustomer, changeCustomer, deleteCustomer));
		rightCustomerAdministrationBox = new VBox(20, customerInfo,
				customerNotifications);
		leftCustomerAdministrationBox.setPadding(new Insets(20, 0, 20, 20));
		rightCustomerAdministrationBox.setPadding(new Insets(20, 20, 20, 0));

		// voorraad systeem
		// rechter scherm info
		// default
		stockDetails = new VBox(20, new Label("Naam: "),
				new Label("Adres: "), new Label("Postcode: "), new Label(
						"Plaats: "), new Label("Geboortedatum: "), new Label(
						"Email: "), new Label("Telefoonnummer: "), new Label(
						"Rekeningnummer: "), new Label("Blacklist: "));
		stockDetails.setPadding(new Insets(5, 20, 0, 0));
		// waarden selected
		stockDetailsContent = new VBox(20, new Label("Naam"), new Label(
				"Adres"), new Label("Postcode"), new Label("Plaats"),
				new Label("geboortedatum"), new Label("Email"), new Label(
						"Telefoonnummer"), new Label("Rekeningnummer"),
				new Label("Blacklist"));
		stockDetailsContent.setPadding(new Insets(5, 20, 0, 0));
		// samenvoegen
		stockInfo = new VBox(10, new HBox(10, stockDetails,
				stockDetailsContent));
		stockInfo.setPadding(new Insets(20));
		stockInfo
				.setStyle("-fx-background-color: white; -fx-border: solid; -fx-border-color: lightgray;");
		stockInfo.setMinSize(472, 400);

		// rechter scherm aanpassen/aanmaken
		// default
		stockDetails = new VBox(20, new Label("Naam: "), new Label(
				"Adres: "), new Label("Postcode: "), new Label("Plaats: "),
				new Label("Geboortedatum: "), new Label("Email: "), new Label(
						"Telefoonnummer: "), new Label("Rekeningnummer: "));
		stockDetails.setPadding(new Insets(20));
		// Textfields, Datepicker en checkbox maken
		stockInput = new VBox();
		stockInput.setSpacing(10);
		for (int i = 0; i < 8; i++) {
			if (i == 4) {
				datePicker = new DatePicker();
				stockInput.getChildren().add(datePicker);
			}
			if (i == 7) {
				checkBox = new CheckBox();
				checkBox.setMinSize(25, 25);
				stockInput.getChildren().add(checkBox);
			} else {
				textField = new TextField();
				stockInput.getChildren().add(textField);
			}
		}
		name = ((TextField) stockInput.getChildren().get(0));
		place = ((TextField) stockInput.getChildren().get(3));
		bank = ((TextField) stockInput.getChildren().get(7));
		email = ((TextField) stockInput.getChildren().get(5));
		postal = ((TextField) stockInput.getChildren().get(2));
		phone = ((TextField) stockInput.getChildren().get(6));
		address = ((TextField) stockInput.getChildren().get(1));
		// knoppen
		createNewStockButtonBox = new HBox();
		createNewStockButtonBox.getChildren().addAll(cancelStock,
				saveNewStock);
		// samenvoegen
		addStockBox = new VBox(new HBox(30, stockDetails,
				stockInput), new HBox(40, cancelStock, saveNewStock));
		addStockBox.setPadding(new Insets(20));
		addStockBox.setSpacing(20);

		// rechter scherm meldingen
		stockNotifications = new VBox(10,
				stockNotificationLabelBox = new HBox(/*notificationLabel*/),
				stockNotificationButtonBox = new HBox(20));
		stockNotificationLabelBox.setAlignment(Pos.CENTER);
		stockNotificationButtonBox.setAlignment(Pos.CENTER);
		stockNotifications
				.setStyle("-fx-background-color: white; -fx-border: solid; -fx-border-color: lightgray;");
		stockNotifications.setAlignment(Pos.CENTER);
		stockNotifications.setMinSize(472, 100);

		// rechter en linker scherm plaatsen
		leftStockBox = new VBox(20, stockList,
				searchFieldStock, new HBox(6, createNewStock,
						changeStock, deleteStock));
		rightStockBox = new VBox(20, stockInfo,
				stockNotifications);
		leftStockBox.setPadding(new Insets(20, 0, 20, 20));
		rightStockBox.setPadding(new Insets(20, 20, 20, 0));
		// onderhoud
		// parkeergelegenheid

		// actions met inner class
		// klantensysteem
		searchFieldCustomer.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				handleSearchByKey(oldValue, newValue);
			}
		});

		// knoppen toevoegen, aanpassen en verwijderen.
		createNewCustomer.setOnAction(e -> {
			isChanging = false;
			changeCustomer();
		});
		changeCustomer.setOnAction(e -> {
			isChanging = true;
			changeCustomer();
		});
		deleteCustomer
				.setOnAction(e -> {
					notificationConfirmAction("Weet u zeker dat u deze klant wilt verwijderen?");
					agreeNotification.setOnAction(event -> {
						deleteCustomer();
						notificationConfirmed("Klant is verwijderd.");
					});
				});

		// opslaan of terug bij toevoegen of aanpassen klant
		saveNewCustomer
				.setOnAction(e -> {
					validateAllFields();
					if (verifyFieldsGo) {
						saveCustomer();
						selectListEntry();
						notificationConfirmed("Klant is opgeslagen.");
						resetTextFieldStyle();
					} else {
						notificationConfirmed("Niet alle velden zijn correct ingevuld.\nVul alle gemarkeerde velden in.");
					}

				});
		cancelCustomer.setOnAction(e -> {
			customerInfo.getChildren().clear();
			customerInfo.getChildren().addAll(
					new HBox(20, customerDetails, customerDetailsContent));
			selectListEntry();
			isChanging = false;
			resetTextFieldStyle();
		});
		// searchfield leegmaken of alle tekst selecteren
		searchFieldCustomer.setOnMouseClicked(e -> {
			if (searchFieldCustomer.getText().equals("Zoek...")) {
				searchFieldCustomer.clear();
			} else
				searchFieldCustomer.selectAll();
		});

		customerList.setOnMousePressed(e -> {
			customerInfo.getChildren().clear();
			customerInfo.getChildren().addAll(
					new HBox(20, customerDetails, customerDetailsContent));
			selectListEntry();
			isChanging = false;
		});
		// voorraad systeem
		searchFieldCustomer.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				handleSearchByKey(oldValue, newValue);
			}
		});

		// knoppen toevoegen, aanpassen en verwijderen.
		createNewStock.setOnAction(e -> {
			isChanging = false;
			changeStock();
		});
		changeStock.setOnAction(e -> {
			isChanging = true;
			changeStock();
		});
		deleteStock
				.setOnAction(e -> {
					notificationConfirmAction("Weet u zeker dat u deze klant wilt verwijderen?");
					agreeNotification.setOnAction(event -> {
						deleteStock();
						notificationConfirmed("Klant is verwijderd.");
					});
				});

		// opslaan of terug bij toevoegen of aanpassen klant
		saveNewStock
				.setOnAction(e -> {
					validateAllFields();
					if (verifyFieldsGo) {
						saveStock();
						selectListEntry();
						notificationConfirmed("Klant is opgeslagen.");
						resetTextFieldStyle();
					} else {
						notificationConfirmed("Niet alle velden zijn correct ingevuld.\nVul alle gemarkeerde velden in.");
					}

				});
		cancelStock.setOnAction(e -> {
			stockInfo.getChildren().clear();
			stockInfo.getChildren().addAll(
					new HBox(20, stockDetails, stockDetailsContent));
			selectListEntry();
			isChanging = false;
			resetTextFieldStyle();
		});
		// searchfield leegmaken of alle tekst selecteren
		searchFieldStock.setOnMouseClicked(e -> {
			if (searchFieldStock.getText().equals("Zoek...")) {
				searchFieldStock.clear();
			} else
				searchFieldStock.selectAll();
		});

		stockList.setOnMousePressed(e -> {
			stockInfo.getChildren().clear();
			stockInfo.getChildren().addAll(
					new HBox(20, stockDetails, stockDetailsContent));
			selectListEntry();
			isChanging = false;
		});
		// onderhoud
		// parkeergelegenheid

		// tabs
		tabsScreen = new TabPane();

		customerAdministration = new Tab("Klantenbestand");
		customerAdministration.setClosable(false);
		customerAdministration.setContent(new HBox(20,
				leftCustomerAdministrationBox, rightCustomerAdministrationBox));

		customerRelations = new Tab("Herinneringen");
		customerRelations.setClosable(false);

		stockAdministration = new Tab("Voorraadbeheer");
		stockAdministration.setClosable(false);
		stockAdministration.setContent(new HBox(20,
				leftStockBox, rightStockBox));
		
		

		tabsScreen.getTabs().addAll(customerAdministration, customerRelations,
				stockAdministration);

		// Create Mainscreen
		klantScene = new Scene(tabsScreen, 1024, 600);
		stage.setScene(klantScene);
		stage.setTitle("AutoTotaalDienst");
		stage.setResizable(false);
		stage.show();
	}

	private void saveStock() {
		// TODO Auto-generated method stub
		
	}

	private void deleteStock() {
		// TODO Auto-generated method stub
		
	}

	private void changeStock() {
		// TODO Auto-generated method stub
		
	}

	// methoden
	// klantensysteem
	public ObservableList<Customer> getAllCustomers() {
		return FXCollections.observableArrayList(customers);
	}

	private void selectListEntry() {
		if (customerList.getSelectionModel().getSelectedItem() != null) {
			customer = customerList.getSelectionModel().selectedItemProperty()
					.get();
			((Label) customerDetailsContent.getChildren().get(0))
					.setText(customer.getName());
			((Label) customerDetailsContent.getChildren().get(1))
					.setText(customer.getAdress());
			((Label) customerDetailsContent.getChildren().get(2))
					.setText(customer.getPostal());
			((Label) customerDetailsContent.getChildren().get(3))
					.setText(customer.getPlace());
			if (customer.getDateOfBirth() != null)
				((Label) customerDetailsContent.getChildren().get(4))
						.setText(customer.getDateOfBirth().toString());
			else
				((Label) customerDetailsContent.getChildren().get(4))
						.setText(" ");
			((Label) customerDetailsContent.getChildren().get(5))
					.setText(customer.getEmail());
			((Label) customerDetailsContent.getChildren().get(6))
					.setText(customer.getTel());
			((Label) customerDetailsContent.getChildren().get(7))
					.setText(customer.getBankAccount());
			if (customer.isOnBlackList())
				((Label) customerDetailsContent.getChildren().get(8))
						.setText("Ja");
			else
				((Label) customerDetailsContent.getChildren().get(8))
						.setText("Nee");
		}
	}

	private void saveCustomer() {
		if (isChanging == true) {
			customers.remove(selectedCustomer);
			customerList.getItems().remove(selectedCustomer);
			isChanging = false;
		}
		customer = new Customer(
				((TextField) customerInput.getChildren().get(0)).getText(),
				((TextField) customerInput.getChildren().get(3)).getText(),
				((TextField) customerInput.getChildren().get(7)).getText(),
				((DatePicker) customerInput.getChildren().get(4)).getValue(),
				((TextField) customerInput.getChildren().get(5)).getText(),
				((TextField) customerInput.getChildren().get(2)).getText(),
				((TextField) customerInput.getChildren().get(6)).getText(),
				((TextField) customerInput.getChildren().get(1)).getText(),
				((CheckBox) customerInput.getChildren().get(8)).isSelected());
		customers.add(customer);
		customerList.getItems().add(customer);
		customerInfo.getChildren().clear();
		customerInfo.getChildren().addAll(
				new HBox(20, customerDetails, customerDetailsContent));
	}

	private void changeCustomer() {
		customerInfo.getChildren().clear();
		customerInfo.getChildren().addAll(
				new HBox(customerDetails, customerInput),
				new HBox(20, cancelCustomer, saveNewCustomer));
		if (!isChanging) {
			for (int i = 0; i < 8; i++) {
				if (customerInput.getChildren().get(i) instanceof TextField) {
					((TextField) customerInput.getChildren().get(i))
							.setText("");
				}
				if (customerInput.getChildren().get(i) instanceof CheckBox) {
					((CheckBox) customerInput.getChildren().get(i))
							.setSelected(false);
					;
				}
				if (customerInput.getChildren().get(i) instanceof DatePicker) {
					((DatePicker) customerInput.getChildren().get(i))
							.setValue(null);
				}
			}
		} else if (customerList.getSelectionModel().getSelectedItem() != null) {
			selectedCustomer = customerList.getSelectionModel()
					.getSelectedItem();
			((TextField) customerInput.getChildren().get(0))
					.setText(selectedCustomer.getName());
			((TextField) customerInput.getChildren().get(1))
					.setText(selectedCustomer.getAdress());
			((TextField) customerInput.getChildren().get(2))
					.setText(selectedCustomer.getPostal());
			((TextField) customerInput.getChildren().get(3))
					.setText(selectedCustomer.getPlace());
			((DatePicker) customerInput.getChildren().get(4))
					.setValue(selectedCustomer.getDateOfBirth());
			((TextField) customerInput.getChildren().get(5))
					.setText(selectedCustomer.getEmail());
			((TextField) customerInput.getChildren().get(6))
					.setText(selectedCustomer.getTel());
			((TextField) customerInput.getChildren().get(7))
					.setText(selectedCustomer.getBankAccount());
			((CheckBox) customerInput.getChildren().get(8))
					.setSelected(selectedCustomer.isOnBlackList());
		}
	}

	private void deleteCustomer() {
		if (customerList.getSelectionModel().getSelectedItem() != null) {
			customers
					.remove(customerList.getSelectionModel().getSelectedItem());
			customerList.getItems().remove(
					customerList.getSelectionModel().getSelectedItem());
		}
	}

	public void handleSearchByKey(String oldVal, String newVal) {
		if (oldVal != null && (newVal.length() < oldVal.length())) {
			customerList.setItems(getAllCustomers());
		}
		customerList.getItems().clear();
		for (Customer entry : getAllCustomers()) {
			if (entry.getName().contains(newVal)
					|| entry.getPostal().contains(newVal)
					|| entry.getEmail().contains(newVal)) {
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
				}
				break;
			case MAINTENANCE:
				if (customer.getLastMaintenance().isBefore(
						LocalDate.now().minusMonths(6))) {
					remindables.add(customer);
				}
				break;
			default:
				return null;
			}
		}
		return remindables;
	}

	public void notificationConfirmed(String s) {
		notificationLabel.setText(s);
		customerNotificationButtonBox.getChildren().clear();
		customerNotificationButtonBox.getChildren().add(agreeNotification);
		agreeNotification.setText("OK");
		agreeNotification.setOnAction(e -> {
			notificationLabel.setText("");
			customerNotificationButtonBox.getChildren().clear();
		});
	}

	public void notificationConfirmAction(String s) {
		notificationLabel.setText(s);
		cancelNotification.setText("Annuleren");
		agreeNotification.setText("OK");
		customerNotificationButtonBox.getChildren().clear();
		customerNotificationButtonBox.getChildren().addAll(cancelNotification,
				agreeNotification);
		cancelNotification.setOnAction(e -> {
			notificationLabel.setText("");
			customerNotificationButtonBox.getChildren().clear();
		});
		agreeNotification.setOnAction(e -> {
			notificationLabel.setText("");
			customerNotificationButtonBox.getChildren().clear();
		});
	}

	public static boolean validateEmail(String emailStr) {
		Matcher matcher = EMAILPATTERN.matcher(emailStr);
		return matcher.find();
	}

	public static boolean validateName(String nameStr) {
		Matcher matcher = NAMEPATTERN.matcher(nameStr);
		return matcher.find();
	}

	public static boolean validatePhone(String nameStr) {
		Matcher matcher = NAMEPATTERN.matcher(nameStr);
		return matcher.find();
	}

	public void validateAllFields() {
		verifyFieldsGo = true;
		if (name.getText().isEmpty()) {
			verifyFieldsGo = false;
			name.setStyle("-fx-border-color: red;");
		}
		if (!validateEmail(email.getText())) {
			verifyFieldsGo = false;
			email.setStyle("-fx-border-color: red;");
		}
		if (!validatePhone(phone.getText())) {
			verifyFieldsGo = false;
			phone.setStyle("-fx-border-color: red;");
		}
		if (place.getText().isEmpty()) {
			verifyFieldsGo = false;
			place.setStyle("-fx-border-color: red;");
		}
		if (address.getText().isEmpty()) {
			verifyFieldsGo = false;
			address.setStyle("-fx-border-color: red;");
		}
		if (bank.getText().isEmpty()) {
			verifyFieldsGo = false;
			bank.setStyle("-fx-border-color: red;");
		}
		if (postal.getText().isEmpty()) {
			verifyFieldsGo = false;
			postal.setStyle("-fx-border-color: red;");
		}

	}

	public void resetTextFieldStyle() {
		name.setStyle("");
		address.setStyle("");
		postal.setStyle("");
		place.setStyle("");
		email.setStyle("");
		phone.setStyle("");
		bank.setStyle("");
	}
	// voorraad systeem
	public ObservableList<Product> getAllProducts() {
		return FXCollections.observableArrayList(stock.getAllProducts());
	}
	// onderhoud
	// parkeergelegenheid
}
