package notifications;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.*;
import main.Invoice.PayMethod;

public class Notification extends Stage {
    public enum notificationStyle {CONFIRM, NOTIFY, ENDSESSION, PRODUCTS, CUSTOMER, TANK, PARKING, MAINTENANCE, TYPE, PAY, SUPPLIER}

    private final ComboBox<Product> partSelector = new ComboBox<>();
    private final ComboBox<Product> fuelSelector = new ComboBox<>();
    private final ComboBox<Customer> customerSelector = new ComboBox<>();
    private final ComboBox<MaintenanceSession> maintenanceSessionSelector = new ComboBox<>();
    private final ComboBox<Reservation> reservationSelector = new ComboBox<>();
    private final ComboBox<String> typeSelector = new ComboBox<>();
    private final ComboBox<PayMethod> paymentSelector = new ComboBox<>();
    private final ComboBox<ProductSupplier> supplierSelector = new ComboBox<>();

    private final TextField
            input = new TextField();
    private final TextField name = new TextField("Naam");
    private final TextField address = new TextField("Adres");
    private final TextField postal = new TextField("Postcode");
    private final TextField place = new TextField("Plaats");
    private final Button annuleren = new Button("annuleren");
    private final Button ok = new Button("Opslaan");
    private final Label
            melding = new Label();
    private final Label label1 = new Label("Of selecteer leverancier\nom te verwijderen:");
    private String keuze;
    private final VBox notification = new VBox(10, melding);
    private final HBox buttonBox = new HBox(10, annuleren, ok);
    private final notificationStyle stijl;

    public Notification(Stage stage, ATDProgram controller, String message, notificationStyle nwstijl) {
        super(StageStyle.UNDECORATED);
        if (stage == null) initOwner(controller.getStage());
        else initOwner(stage);
        initModality(Modality.WINDOW_MODAL);
        this.stijl = nwstijl;
        this.setResizable(false);
        label1.setTextAlignment(TextAlignment.CENTER);
        melding.setTextAlignment(TextAlignment.CENTER);
        input.setPrefWidth(210);
        ok.setPrefWidth(100);
        ok.setOnAction(e -> {
            switch (stijl) {
                case CONFIRM:
                case NOTIFY: {
                    keuze = "confirm";
                    this.hide();
                    break;
                }
                case TANK:
                    if (getSelected() != null && getInput() != 0) {
                        keuze = "confirm";
                        this.hide();
                        break;
                    }
                case ENDSESSION:
                    if (getInput() != 0) {
                        keuze = "confirm";
                        this.hide();
                        break;
                    }
                default:
                    if (getSelected() != null) {
                        keuze = "confirm";
                        this.hide();
                        break;
                    }
            }
        });
        annuleren.setPrefWidth(100);
        annuleren.setOnAction(e -> {
            keuze = "cancel";
            this.hide();
        });
        switch (stijl) {
            case PRODUCTS: {
                setTitle("Product toevoegen");
                melding.setText("Selecteer een product:");
                ok.setText("Toevoegen");
                partSelector.setPrefWidth(210);
                controller.getProducts().stream().filter(product -> product instanceof Part).forEach(product -> partSelector.getItems().addAll(product));
                notification.getChildren().addAll(partSelector, buttonBox);
                break;
            }
            case TYPE: {
                setTitle("ProductType opslaan");
                melding.setText("Selecteer het type product:");
                typeSelector.setPrefWidth(210);
                typeSelector.getItems().addAll("Benzine", "Onderdeel");
                notification.getChildren().addAll(typeSelector, buttonBox);
                break;
            }
            case CUSTOMER: {
                setTitle("Klant toevoegen");
                melding.setText("Selecteer een klant:");
                ok.setText("Toevoegen");
                customerSelector.setPrefWidth(210);
                customerSelector.getItems().addAll(controller.getCustomers());
                notification.getChildren().addAll(customerSelector, buttonBox);
                break;
            }
            case TANK: {
                setTitle("Tanksessie aanmaken");
                melding.setText("Vul de gegevens in:");
                ok.setText("Toevoegen");
                fuelSelector.setPrefWidth(210);
                controller.getProducts().stream().filter(product -> product instanceof Fuel).forEach(product -> fuelSelector.getItems().addAll(product));
                notification.getChildren().addAll(input, fuelSelector, buttonBox);
                break;
            }

            case PARKING: {
                setTitle("Reservering toevoegen");
                melding.setText("Selecteer een reservering:");
                ok.setText("Toevoegen");
                reservationSelector.setPrefWidth(210);
                reservationSelector.getItems().addAll(controller.getReservations());
                notification.getChildren().addAll(reservationSelector, buttonBox);
                break;
            }

            case MAINTENANCE: {
                setTitle("Klus toevoegen");
                melding.setText("Selecteer een klus:");
                ok.setText("Toevoegen");
                maintenanceSessionSelector.setPrefWidth(210);
                controller.getMaintenanceSessions().stream().filter(session -> session.isFinished()).forEach(session -> maintenanceSessionSelector.getItems().add(session));
                notification.getChildren().addAll(maintenanceSessionSelector, buttonBox);
                break;
            }
            case ENDSESSION: {
                setTitle("Klus afsluiten");
                melding.setText("Vul aantal gewerkte uren in:");
                ok.setText("Afsluiten");
                notification.getChildren().addAll(input, buttonBox);
                break;
            }
            case PAY: {
                setTitle("Factuur afhandelen");
                melding.setText("Kies uw betaalmethode:");
                ok.setText("Betalen");
                paymentSelector.setPrefWidth(210);
                paymentSelector.getItems().addAll(Invoice.PayMethod.values());
                notification.getChildren().addAll(paymentSelector, buttonBox);
                break;
            }
            case CONFIRM: {
                this.setTitle("Bevestigen");
                melding.setText(message);
                notification.getChildren().addAll(buttonBox);
                ok.setText("Ok");
                break;
            }
            case NOTIFY: {
                this.setTitle("Melding");
                melding.setText(message);
                ok.setText("ok");
                buttonBox.getChildren().remove(annuleren);
                notification.getChildren().addAll(buttonBox);
                break;
            }
            case SUPPLIER: {
                this.setTitle("Leverancier aanmaken");
                melding.setText("Vul de gegevens in");
                supplierSelector.setPrefWidth(210);
                supplierSelector.getItems().addAll(controller.getSuppliers());
                supplierSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null && newValue != oldValue) {
                        Notification confirmDelete = new Notification(this, controller, "Weet u zeker dat u deze Leverancier\nwilt verwijderen?", notificationStyle.CONFIRM);
                        confirmDelete.showAndWait();
                        if (confirmDelete.getKeuze().equals("confirm")) {
                            //noinspection LoopStatementThatDoesntLoop
                            for (Product product : controller.getProducts()) {
                                if (product.getSupplier() == newValue) {
                                    Notification notify = new Notification(this, controller, "Deze leverancier bevat nog producten!", notificationStyle.NOTIFY);
                                    notify.showAndWait();
                                    break;
                                } else {
                                    controller.addorRemoveSupplier(newValue, true);
                                    supplierSelector.getItems().remove(newValue);
                                    break;
                                }
                            }
                        }
                        //THIS LINE GIVES A WEIRD EXCEPTION WHEN YOU CANCEL THE DELETION
                        //supplierSelector.getSelectionModel().clearSelection();
                    }
                });
                notification.getChildren().addAll(name, address, postal, place, buttonBox, label1, supplierSelector);
                notification.getChildren().stream().filter(node -> node instanceof TextField).forEach(node -> node.setOnMouseClicked(e -> ((TextField) node).clear()));
                break;
            }
            default:
        }
        notification.setAlignment(Pos.CENTER);
        buttonBox.setAlignment(Pos.CENTER);
        notification.setStyle("-fx-background-color: #e6e6e6; -fx-border-color: darkgray;-fx-border: solid;");
        notification.setPadding(new Insets(20));
        Scene scene = new Scene(notification);
        this.setScene(scene);
    }

    public String getKeuze() {
        return keuze;
    }

    public Object getSelected() {
        switch (stijl) {
            case MAINTENANCE:
                return maintenanceSessionSelector.getSelectionModel().getSelectedItem();
            case TANK:
                return fuelSelector.getSelectionModel().getSelectedItem();
            case PARKING:
                return reservationSelector.getSelectionModel().getSelectedItem();
            case TYPE:
                return typeSelector.getSelectionModel().getSelectedItem();
            case CUSTOMER:
                return customerSelector.getSelectionModel().getSelectedItem();
            case PRODUCTS:
                return partSelector.getSelectionModel().getSelectedItem();
            case PAY:
                return paymentSelector.getSelectionModel().getSelectedItem();
            case SUPPLIER:
                return new ProductSupplier(name.getText(), address.getText(), postal.getText(), place.getText());
            default:
                return null;
        }
    }

    public int getInput() {
        if (!input.getText().isEmpty())
            return Integer.parseInt(input.getText());
        else return 0;
    }
}
