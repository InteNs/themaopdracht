package screens;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.ATDProgram;
import main.ParkingSpace;
import main.Reservation;
import notifications.Notification;

import java.time.LocalDate;
import java.util.ArrayList;

public class ParkingScreen extends Screen {
    private final ATDProgram controller;
    private final ComboBox<String> filterSelector = new ComboBox<>();
    private final ComboBox<ParkingSpace> spaceContent = new ComboBox<>();
    private final ArrayList<ListItem> content = new ArrayList<>();
    private final ListView<ListItem> itemList = new ListView<>();
    private final DatePicker dateFromContent = new DatePicker();
    private final DatePicker dateToContent = new DatePicker();
    private Reservation selectedObject;
    private ListItem selectedItem;
    private boolean isChanging;
    private static final double
            space_Small = 10,
            space_Big = 20,
            space_3 = 15,
            button_3 = 140,
            label_Normal = 120,
            item_Normal = 98;
    private final Button
            newButton = new Button("Nieuw"),
            changeButton = new Button("Aanpassen"),
            removeButton = new Button("Verwijderen"),
            cancelButton = new Button("Annuleren"),
            saveButton = new Button("Opslaan");
    private final Label
            dateToLabel = new Label("Datum tot: "),
            dateFromLabel = new Label("Datum van: "),
            spaceLabel = new Label("Parkeerplaats: "),
            numberPlateLabel = new Label("Kenteken: ");
    private final TextField
            searchContent = new TextField("Zoek..."),
            numberPlateContent = new TextField();
    private final VBox
            leftBox = new VBox(),
            rightBox = new VBox(space_Big);
    private final HBox
            listLabels = new HBox(5),
            detailsBox = new HBox(space_Small),
            control_MainBox = new HBox(space_3),
            control_SecBox = new HBox(space_Small),
            mainBox = new HBox(space_Small);

    public ParkingScreen(ATDProgram controller) {
        super(controller);
        this.controller = controller;
        //put everything in the right place
        control_MainBox.getChildren().addAll(newButton, changeButton, removeButton);
        control_SecBox.getChildren().addAll(searchContent, filterSelector);
        leftBox.getChildren().addAll(listLabels, itemList, control_SecBox);
        rightBox.getChildren().addAll(detailsBox, control_MainBox);
        mainBox.getChildren().addAll(leftBox, rightBox);
        this.getChildren().add(mainBox);
        detailsBox.getChildren().addAll(
                new VBox(space_Big,
                        new HBox(space_Big, dateFromLabel, dateFromContent),
                        new HBox(space_Big, dateToLabel, dateToContent),
                        new HBox(space_Big, numberPlateLabel, numberPlateContent),
                        new HBox(space_Big, spaceLabel, spaceContent),
                        new HBox(space_Big, cancelButton, saveButton))
        );
        //set styles and sizes
        ////set width for all detail labels and textfields
        ((VBox) detailsBox.getChildren().get(0)).getChildren().stream().filter(node -> ((HBox) node).getChildren().get(0) instanceof Label).forEach(node -> ((Label) ((HBox) node).getChildren().get(0)).setMinWidth(label_Normal));
        numberPlateContent.setMinWidth(label_Normal * 1.5);
        dateToContent.setMinWidth(label_Normal * 1.5);
        dateFromContent.setMinWidth(label_Normal * 1.5);
        spaceContent.setMinWidth(label_Normal * 1.5);
        detailsBox.setPadding(new Insets(space_Big));
        control_SecBox.setPadding(new Insets(space_Big, 0, space_Small, 0));
        listLabels.setPadding(new Insets(0, 0, 0, 7));
        mainBox.setPadding(new Insets(space_Small));
        detailsBox.getStyleClass().addAll("removeDisabledEffect", "detailsBox");
        leftBox.getStyleClass().add("removeDisabledEffect");
        itemList.getStyleClass().add("removeDisabledEffect");
        listLabels.getStyleClass().add("detailsBox");
        detailsBox.setPrefSize(450, 520);
        itemList.setPrefSize(450, 501);
        searchContent.setPrefSize(290, 50);
        filterSelector.setPrefSize(150, 50);
        cancelButton.setPrefSize(150, 50);
        saveButton.setPrefSize(150, 50);
        newButton.setPrefSize(button_3, 50);
        changeButton.setPrefSize(button_3, 50);
        removeButton.setPrefSize(button_3, 50);
        //StockDetails
        dateFromContent.valueProperty().addListener(e -> {
            if (dateFromContent.getValue() != null && dateToContent.getValue() != null) checkDates();
        });
        dateToContent.valueProperty().addListener(e -> {
            if (dateFromContent.getValue() != null && dateToContent.getValue() != null) checkDates();
        });
        //spaceContent.getItems().addAll(controller.getParkingSpaces());
        setEditable(false);
        //Listview
        controller.getReservations().stream().filter(object -> object.isActive().equals("active") || object.isActive().equals("before")).forEach(object -> itemList.getItems().add(new ListItem(object)));
        refreshList();
        itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            select(newValue);
        });
        listLabels.getChildren().addAll(
                new Label("Datum van"),
                new Separator(Orientation.VERTICAL),
                new Label("Datum tot"),
                new Separator(Orientation.VERTICAL),
                new Label("Kenteken"),
                new Separator(Orientation.VERTICAL),
                new Label("Parkeerplaats"));
        listLabels.getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setPrefWidth(item_Normal));
        //SearchField
        searchContent.setOnMouseClicked(e -> {
            if (searchContent.getText().equals("Zoek...")) {
                searchContent.clear();
            } else searchContent.selectAll();
        });
        searchContent.textProperty().addListener((observable, oldValue, newValue) -> {
            search(oldValue, newValue);
        });
        //Buttons and filter
        newButton.setOnAction(e -> {
            clearInput();
            setEditable(true);
            isChanging = false;
        });
        changeButton.setOnAction(e -> {
            if (checkSelected()) {
                setEditable(true);
                isChanging = true;
            }
        });
        removeButton.setOnAction(e -> remove());
        cancelButton.setOnAction(e -> {
            clearInput();
            setEditable(false);
        });
        saveButton.setOnAction(e -> save());
        filterSelector.getItems().addAll("Filter: Geen", "Filter: Actief", "Filter: Overzicht", "Filter: Afgesloten");
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
            case 0: {
                itemList.getItems().clear();
                for (Reservation object : controller.getReservations())
                    itemList.getItems().add(new ListItem(object));
                break;
            }
            case 1: {
                itemList.getItems().clear();
                controller.getReservations().stream().filter(object -> object.isActive().equals("active")).forEach(object -> itemList.getItems().add(new ListItem(object)));
                break;
            }
            case 2: {
                itemList.getItems().clear();
                controller.getReservations().stream().filter(object -> object.getFromDate().getMonth() == (LocalDate.now().getMonth().minus(1)) || object.getToDate().getMonth() == (LocalDate.now().getMonth().minus(1))).forEach(object -> itemList.getItems().add(new ListItem(object)));
                break;
            }
            case 3: {
                itemList.getItems().clear();
                controller.getReservations().stream().filter(object -> object.isActive().equals("done")).forEach(object -> itemList.getItems().add(new ListItem(object)));
                break;
            }
        }

        if (!searchContent.getText().equals("Zoek...")) search(null, searchContent.getText());
    }

    /*
     * removes the selected item
     */
    private void remove() {
        if (checkSelected()) {
            Notification Confirm = new Notification(null, controller, "Weet u zeker dat u deze reservering wilt verwijderen?", Notification.notificationStyle.CONFIRM);
            Confirm.showAndWait();
            if (Confirm.getKeuze().equals("confirm")) {
                itemList.getItems().remove(selectedItem);
                controller.addorRemoveReservations(selectedObject, true);
                Notification Notify = new Notification(null, controller, "de reservering is verwijderd.", Notification.notificationStyle.NOTIFY);
                Notify.showAndWait();
            }
        }
    }

    /**
     * saves the input in either a selected item or a new item
     */
    private void save() {
        if (checkInput()) {
            if (isChanging) {
                Notification confirm = new Notification(null, controller, "Weet u zeker dat u deze wijzigingen wilt doorvoeren?", Notification.notificationStyle.CONFIRM);
                confirm.showAndWait();
                switch (confirm.getKeuze()) {
                    case "confirm": {
                        selectedObject.setToDate(dateToContent.getValue());
                        selectedObject.setFromDate(dateFromContent.getValue());
                        selectedObject.setNumberPlate(numberPlateContent.getText());
                        spaceContent.setDisable(true);
                        refreshList();
                    }
                    case "cancel":
                        clearInput();
                        setEditable(false);
                }
            } else {
                Notification confirm = new Notification(null, controller, "Deze reservering aanmaken?", Notification.notificationStyle.CONFIRM);
                confirm.showAndWait();
                switch (confirm.getKeuze()) {
                    case "confirm": {
                        Reservation newReservation = new Reservation(
                                dateFromContent.getValue(),
                                dateToContent.getValue(),
                                numberPlateContent.getText(),
                                spaceContent.getValue());
                        controller.addorRemoveReservations(newReservation, false);
                        itemList.getItems().add(new ListItem(newReservation));
                        setEditable(false);
                    }
                    case "cancel": {
                        clearInput();
                        setEditable(false);
                    }
                }
            }
        } else {
            Notification notFilled = new Notification(null, controller, "Niet alle velden zijn juist ingevuld", Notification.notificationStyle.NOTIFY);
            notFilled.showAndWait();
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
        itemList.getItems().forEach(ParkingScreen.ListItem::refresh);
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
            selectedObject = selectedValue.getReservation();
            dateToContent.setValue(selectedObject.getToDate());
            dateFromContent.setValue(selectedObject.getFromDate());
            spaceContent.setValue(selectedObject.getParkingSpace());
            numberPlateContent.setText(selectedObject.getNumberPlate());
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
        detailsBox.setDisable(!enable);
        control_MainBox.setDisable(enable);
        spaceContent.setDisable(!enable);
        leftBox.setDisable(enable);
    }

    /**
     * clears all the inputfields
     */
    private void clearInput() {
        ((VBox) detailsBox.getChildren().get(0)).getChildren().stream().filter(node1 -> ((HBox) node1).getChildren().get(1) instanceof TextField).forEach(node1 -> ((TextField) ((HBox) node1).getChildren().get(1)).clear());
        spaceContent.getSelectionModel().clearSelection();
        dateToContent.setValue(null);
        dateFromContent.setValue(null);
    }

    private void checkDates() {
        spaceContent.getItems().clear();
        for (ParkingSpace space : controller.getParkingSpaces())
            spaceContent.getItems().add(space);
        controller.getReservations().stream().filter(reservation -> reservation != selectedObject && ATDProgram.periodIsOverlapping(reservation.getFromDate(), reservation.getToDate(), dateFromContent.getValue(), dateToContent.getValue())).forEach(reservation -> spaceContent.getItems().remove(reservation.getParkingSpace()));
    }

    /**
     * checks if all the inputfields are filled in
     *
     * @return false if one of the inputs is null
     */
    private boolean checkInput() {
        boolean result = true;
        if (numberPlateContent.getText().isEmpty()) result = false;
        if (dateToContent.getValue() == null || dateToContent.getValue().isBefore(LocalDate.now())) result = false;
        if (isChanging && dateFromContent.getValue() == null) result = false;

        if (dateFromContent.getValue() == null || dateFromContent.getValue().isAfter(dateToContent.getValue()))
            result = false;
        if (!isChanging && (dateFromContent.getValue() == null || dateFromContent.getValue().isBefore(LocalDate.now())))
            result = false;
        if (spaceContent.getSelectionModel().getSelectedItem() == null) result = false;
        return result;
    }

    /**
     * checks if an item is selected in the list
     *
     * @return false if nothing is selected
     */
    private boolean checkSelected() {
        return !(selectedObject == null || selectedObject.getToDate().isBefore(LocalDate.now()));
    }

    /**
     * searches through all items in the list
     *
     * @param oldVal the previous content of the searchfield
     * @param newVal the new content of the searchfield
     */
    private void search(String oldVal, String newVal) {
        if (oldVal != null && (newVal.length() < oldVal.length())) {
            //actor has deleted a character, so reset the search
            itemList.getItems().clear();
            itemList.getItems().addAll(content);
        }

        itemList.getItems().clear();
        //add an item if any item that exists contains any value that has been searched for
        content.stream().filter(entry -> entry.getReservation().getNumberPlate().contains(newVal)
                || Integer.toString(entry.getReservation().getParkingSpace().getID()).contains(newVal)).forEach(entry -> itemList.getItems().add(entry));
    }

    // this represents every item in the list, it has different constructor for every filter option
    public class ListItem extends HBox {
        private final Reservation object;
        private final Label itemPlateLabel = new Label();
        private final Label itemFromLabel = new Label();
        private final Label itemToLabel = new Label();
        private final Label itemSpaceLabel = new Label();

        public ListItem(Reservation object) {
            this.object = object;
            refresh();
            setSpacing(5);
            getChildren().addAll(
                    itemFromLabel,
                    new Separator(Orientation.VERTICAL),
                    itemToLabel,
                    new Separator(Orientation.VERTICAL),
                    itemPlateLabel,
                    new Separator(Orientation.VERTICAL),
                    itemSpaceLabel);
            getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setPrefWidth(item_Normal));
        }

        /**
         * fills in all the labels with the latest values
         */
        public void refresh() {
            itemPlateLabel.setText(object.getNumberPlate());
            itemFromLabel.setText(object.getFromDate().toString());
            itemToLabel.setText(object.getToDate().toString());
            itemSpaceLabel.setText(object.getParkingSpace().toString());
        }

        /**
         * @return the object this item represents
         */
        public Reservation getReservation() {
            return object;
        }
    }
}

