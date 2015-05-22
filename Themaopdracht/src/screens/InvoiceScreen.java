package screens;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.*;
import main.Invoice.InvoiceItem;
import main.Invoice.PayMethod;
import notifications.Notification;

import java.time.LocalDate;
import java.util.ArrayList;

public class InvoiceScreen extends Screen {
    private final ATDProgram controller;
    private final ComboBox<String> filterSelector = new ComboBox<>();
    private final ComboBox<Customer> customerContent = new ComboBox<>();
    private final CheckBox isPayedContent = new CheckBox();
    private final ArrayList<ListItem> content = new ArrayList<>();
    private final ListView<ListItem> itemList = new ListView<>();
    private final ListView<InvoiceItem> contentList = new ListView<>();
    private final DatePicker dateContent = new DatePicker();
    private Invoice selectedobject;
    private ListItem selectedItem;
    private boolean isChanging;
    private static final double
            space_Small = 10,
            space_Big = 20,
            space_4 = 6,
            button_4 = 108,
            label_Normal = 120,
            item_Normal = 98;
    private final Button
            newButton = new Button("Nieuw"),
            payButton = new Button("Betalen"),
            removeButton = new Button("Verwijderen"),
            cancelButton = new Button("Annuleren"),
            changeButton = new Button("Aanpassen"),
            saveButton = new Button("Opslaan"),
            addMaintenanceButton = new Button("+Onderhoud"),
            addRefuelButton = new Button("+Tanksessie"),
            addParkingButton = new Button("+Reservering");
    private final Label
            dateLabel = new Label("Datum: "),
            priceLabel = new Label("Prijs: "),
            isPayedLabel = new Label("Is betaalt: "),
            customerLabel = new Label("Klant: ");
    private final TextField
            priceContent = new TextField();
    private final VBox
            leftBox = new VBox(),
            rightBox = new VBox(space_Big);
    private final HBox
            listLabels = new HBox(5),
            detailsBox = new HBox(space_Small),
            control_MainBox = new HBox(space_4),
            control_SecBox = new HBox(space_4),
            mainBox = new HBox(space_Small);

    public InvoiceScreen(ATDProgram controller) {
        super(controller);
        this.controller = controller;
        //put everything in the right place
        control_MainBox.getChildren().addAll(newButton, changeButton, payButton, removeButton);
        control_SecBox.getChildren().addAll(addMaintenanceButton, addRefuelButton, addParkingButton, filterSelector);
        leftBox.getChildren().addAll(listLabels, itemList, control_SecBox);
        rightBox.getChildren().addAll(detailsBox, control_MainBox);
        mainBox.getChildren().addAll(leftBox, rightBox);
        this.getChildren().add(mainBox);
        detailsBox.getChildren().addAll(
                new VBox(space_Big,
                        new HBox(space_Big, dateLabel, dateContent),
                        new HBox(space_Big, priceLabel, priceContent),
                        new HBox(space_Big, customerLabel, customerContent),
                        new HBox(space_Big, isPayedLabel, isPayedContent),
                        new HBox(space_Big, cancelButton, saveButton),
                        new HBox(contentList))
        );
        //set styles and sizes
        ////set width for all detail labels and textfields
        ((VBox) detailsBox.getChildren().get(0)).getChildren().stream().filter(node -> ((HBox) node).getChildren().get(0) instanceof Label).forEach(node -> ((Label) ((HBox) node).getChildren().get(0)).setMinWidth(label_Normal));
        priceContent.setMinWidth(label_Normal * 1.5);
        dateContent.setMinWidth(label_Normal * 1.5);
        customerContent.setMinWidth(label_Normal * 1.5);
        detailsBox.setPadding(new Insets(space_Big));
        control_SecBox.setPadding(new Insets(space_Big, 0, space_Small, 0));
        listLabels.setPadding(new Insets(0, 0, 0, 7));
        mainBox.setPadding(new Insets(space_Small));
        listLabels.getStyleClass().add("detailsBox");
        detailsBox.getStyleClass().addAll("removeDisabledEffect", "detailsBox");
        detailsBox.setPrefSize(450, 520);
        itemList.setPrefSize(450, 501);
        contentList.setPrefSize(410, 300);
        filterSelector.setPrefSize(button_4, 50);
        cancelButton.setPrefSize(button_4, 50);
        saveButton.setPrefSize(button_4, 50);
        newButton.setPrefSize(button_4, 50);
        changeButton.setPrefSize(button_4, 50);
        removeButton.setPrefSize(button_4, 50);
        payButton.setPrefSize(button_4, 50);
        addParkingButton.setPrefSize(button_4, 50);
        addMaintenanceButton.setPrefSize(button_4, 50);
        addRefuelButton.setPrefSize(button_4, 50);
        //details
        customerContent.getItems().addAll(controller.getCustomers());
        setEditable(false);
        //Listview
        controller.getInvoices().stream().filter(object -> !object.isPayed()).forEach(object -> itemList.getItems().add(new ListItem(object)));
        refreshList();
        itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            select(newValue);
        });
        listLabels.getChildren().addAll(
                new Label("Datum"),
                new Separator(Orientation.VERTICAL),
                new Label("Klant"),
                new Separator(Orientation.VERTICAL),
                new Label("Bedrag"),
                new Separator(Orientation.VERTICAL),
                new Label("Betaald"));
        listLabels.getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setPrefWidth(item_Normal));
        //Buttons and filter
        addRefuelButton.setOnAction(e -> addFuel());
        addMaintenanceButton.setOnAction(e -> addMaintenance());
        addParkingButton.setOnAction(e -> addParking());
        newButton.setOnAction(e -> {
            isChanging = false;
            save();
        });
        payButton.setOnAction(e -> pay());
        changeButton.setOnAction(e -> {
            if (checkSelected()) {
                setEditable(true);
                isChanging = true;
            }
        });
        removeButton.setOnAction(e -> remove());
        cancelButton.setOnAction(e -> setEditable(false));
        saveButton.setOnAction(e -> save());
        filterSelector.getItems().addAll("Filter: Geen", "Filter: Achterstand", "Filter: Anoniem", "Filter: Betaalt");
        filterSelector.getSelectionModel().selectFirst();
        filterSelector.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            changeFilter(newValue.intValue());
        });
    }

    /**
     * fills the list with items that fit with the given filter
     *
     * @param newValue selected filter
     */
    private void changeFilter(int newValue) {
        switch (newValue) {
            case 0: {//geen
                itemList.getItems().clear();
                controller.getInvoices().stream().filter(object -> !object.isPayed()).forEach(object -> itemList.getItems().add(new ListItem(object)));
                break;
            }
            case 1: {//achterstand
                itemList.getItems().clear();
                for (Invoice object : controller.getInvoices())
                    if (!object.isPayed() && object.getInvoiceDate().isBefore(LocalDate.now().minusMonths(3))) {
                        itemList.getItems().add(new ListItem(object));
                        if (object.getCustomer() != null)  object.getCustomer().setIsOnBlackList(true);
                    } else if (object.getCustomer() != null) object.getCustomer().setIsOnBlackList(false);

                break;
            }
            case 2: {//anoniem
                itemList.getItems().clear();
                controller.getInvoices().stream().filter(object -> !object.isPayed() && object.getCustomer() == null).forEach(object -> itemList.getItems().add(new ListItem(object)));
                break;
            }
            case 3: {//betaalt
                itemList.getItems().clear();
                controller.getInvoices().stream().filter(Invoice::isPayed).forEach(object -> itemList.getItems().add(new ListItem(object)));
                break;
            }
        }

//		if(!searchInput.getText().equals("Zoek..."))search(null, searchInput.getText());	
    }

    /*
     * removes the selected item
     */
    private void remove() {
        if (!checkSelected()) return;
        Notification Confirm = new Notification(null, controller, "Weet u zeker dat u deze rekening\nwilt verwijderen?", Notification.notificationStyle.CONFIRM);
        Confirm.showAndWait();
        if (Confirm.getKeuze().equals("confirm")) {
            itemList.getItems().remove(selectedItem);
            controller.addorRemoveInvoice(selectedobject, true);
            Notification Notify = new Notification(null, controller, "De rekening is verwijderd.", Notification.notificationStyle.NOTIFY);
            Notify.showAndWait();
        }
    }

    /**
     * adds a fuelsession to the invoice
     */
    private void addFuel() {
        if (!checkSelected()) return;
        Notification addFuelNotification = new Notification(null, controller, "", Notification.notificationStyle.TANK);
        addFuelNotification.showAndWait();
        if (addFuelNotification.getKeuze().equals("confirm")) {
            int tanked = addFuelNotification.getInput();
            Fuel type = (Fuel) addFuelNotification.getSelected();
            if (tanked > type.getAmount()) tanked = type.getAmount();
            controller.getStock().useProduct(type, tanked);
            selectedobject.add(selectedobject.new InvoiceItem(type.getName(), type.getSellPrice(), tanked));
            refreshList();

        }
    }

    /**
     * adds a maintenance session to the invoice
     */
    private void addMaintenance() {
        if (!checkSelected()) return;
        Notification addMaintenanceNotification = new Notification(null, controller, "", Notification.notificationStyle.MAINTENANCE);
        addMaintenanceNotification.showAndWait();
        if (addMaintenanceNotification.getKeuze().equals("confirm")) {
            MaintenanceSession selection = (MaintenanceSession) addMaintenanceNotification.getSelected();
            selectedobject.add(selectedobject.new InvoiceItem("Uurloon", selection.getMechanic().getHourlyFee(), selection.getMechanic().getWorkedHours()));
            for (Product key : selection.getUsedParts().keySet()) {
                double price = key.getSellPrice() * selection.getUsedParts().get(key);
                controller.getStock().useProduct(key, selection.getUsedParts().get(key));
                selectedobject.add(selectedobject.new InvoiceItem(key.getName(), price, selection.getUsedParts().get(key)));
                refreshList();
            }
        }
    }

    /**
     * adds a reservation to the invoice
     */
    private void addParking() {
        if (!checkSelected()) return;
        Notification addParking = new Notification(null, controller, "", Notification.notificationStyle.PARKING);
        addParking.showAndWait();
        if (addParking.getKeuze().equals("confirm")) {
            selectedobject.add(selectedobject.new InvoiceItem("Parkeersessie", ((Reservation) addParking.getSelected()).getTotalPrice(), 1));
            Notification notify = new Notification(null, controller, "reservering toegevoegd.", Notification.notificationStyle.NOTIFY);
            notify.showAndWait();
        }
        refreshList();
    }

    /**
     * pays the selected invoice
     */
    private void pay() {
        if (!checkSelected()) return;
        Notification confirm = new Notification(null, controller, "", Notification.notificationStyle.PAY);
        confirm.showAndWait();
        if (confirm.getKeuze().equals("confirm")) {
            selectedobject.payNow((PayMethod) confirm.getSelected());
            Notification notify = new Notification(null, controller, "factuur is betaald.", Notification.notificationStyle.NOTIFY);
            notify.showAndWait();
        }
        refreshList();
    }

    /**
     * saves the input in either a selected item or a new item
     */
    private void save() {
        if (isChanging) {
            Notification confirm = new Notification(null, controller, "Weet u zeker dat u deze\nwijzigingen wilt doorvoeren?", Notification.notificationStyle.CONFIRM);
            confirm.showAndWait();
            switch (confirm.getKeuze()) {
                case "confirm": {
                    if (customerContent.getValue() != null) selectedobject.bindToCustomer(customerContent.getValue());
                    customerContent.setDisable(true);
                    refreshList();
                }
                case "cancel":
                    setEditable(false);
            }
        } else {
            Invoice newInvoice = new Invoice();
            controller.addorRemoveInvoice(newInvoice, false);
            itemList.getItems().add(new ListItem(newInvoice));
        }
    }

    /**
     * refreshes the list and every item itself
     */
    public void refreshList() {
        content.clear();
        content.addAll(itemList.getItems());
        itemList.getItems().clear();
        itemList.getItems().addAll(content);
        itemList.getItems().forEach(InvoiceScreen.ListItem::refresh);
        changeFilter(filterSelector.getSelectionModel().getSelectedIndex());
        select(selectedItem);
    }

    /**
     * selects an item and fills in the information
     *
     * @param selectedValue the item to be selected
     */
    private void select(ListItem selectedValue) {
        if (selectedValue != null) {
            selectedItem = selectedValue;
            selectedobject = selectedValue.getInvoice();
            dateContent.setValue(selectedobject.getInvoiceDate());
            customerContent.setValue(selectedobject.getCustomer());
            priceContent.setText(ATDProgram.convert(selectedobject.getTotalPrice()));
            isPayedContent.setSelected(selectedobject.isPayed());
            contentList.getItems().clear();
            for (InvoiceItem item : selectedobject.getItems())
                contentList.getItems().add(item);
        }
    }

    /**
     * disables/enables the left or right side of the stage
     *
     * @param enable
     */
    private void setEditable(boolean enable) {
        cancelButton.setVisible(enable);
        saveButton.setVisible(enable);
        customerContent.setDisable(!enable);
        priceContent.setDisable(true);
        dateContent.setDisable(true);
        isPayedContent.setDisable(true);
        control_MainBox.setDisable(enable);
        leftBox.setDisable(enable);
    }

    /**
     * checks if an item is selected in the list
     *
     * @return false if nothing is selected
     */
    private boolean checkSelected() {
        return selectedobject != null;
    }

    // this represents every item in the list, it has different constructor for every filter option
    public class ListItem extends HBox {
        private final Invoice object;
        private final Label itemDateLabel = new Label();
        private final Label itemPriceLabel = new Label();
        private final Label itemCustomerLabel = new Label();
        private final Label itemIsPayedLabel = new Label();

        public ListItem(Invoice object) {
            this.object = object;
            refresh();
            setSpacing(5);
            getChildren().addAll(
                    itemDateLabel,
                    new Separator(Orientation.VERTICAL),
                    itemCustomerLabel,
                    new Separator(Orientation.VERTICAL),
                    itemPriceLabel,
                    new Separator(Orientation.VERTICAL),
                    itemIsPayedLabel);
            getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setPrefWidth(item_Normal));
        }

        /**
         * fills in all the labels with the latest values
         */
        public void refresh() {
            itemDateLabel.setText(object.getInvoiceDate().toString());
            if (object.getCustomer() == null) itemCustomerLabel.setText("Anoniem");
            //else itemCustomerLabel.setText(object.getCustomer().getName());
            itemPriceLabel.setText(ATDProgram.convert(object.getTotalPrice()));
            if (object.isPayed()) itemIsPayedLabel.setText("Betaalt");
            else itemIsPayedLabel.setText("Openstaand");
        }

        /**
         * @return the object this item represents
         */
        public Invoice getInvoice() {
            return object;
        }
    }
}
