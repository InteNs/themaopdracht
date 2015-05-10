package screens;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.*;
import notifications.Notification;

import java.util.ArrayList;
import java.util.Map.Entry;

public class StockScreen extends Screen {
    private final ATDProgram controller;
    private final ComboBox<ProductSupplier> supplierContent = new ComboBox<>();
    private final ComboBox<String> filterSelector = new ComboBox<>();
    private final ArrayList<ListItem> content = new ArrayList<>();
    private final ListView<ListItem> itemList = new ListView<>();
    private Product selectedObject;
    private ListItem selectedItem;
    private boolean isChanging;
    private static final double
            space_Small = 10,
            space_Big = 20,
            space_3 = 15,
            button_3 = 140,
            label_Normal = 120,
            item_Normal = 89;
    private final Button
            newButton = new Button("Nieuw"),
            newSupButton = new Button("Nieuwe supplier"),
            changeButton = new Button("Aanpassen"),
            removeButton = new Button("Verwijderen"),
            cancelButton = new Button("Annuleren"),
            saveButton = new Button("Opslaan"),
            proceedButton = new Button("Opboeken"),
            newRuleButton = new Button("Regel toevoegen");
    private final Label
            nameLabel = new Label("Naam: "),
            amountLabel = new Label("Aantal: "),
            minAmountLabel = new Label("Min. aantal: "),
            priceLabel = new Label("Prijs: "),
            buyPriceLabel = new Label("Inkoopprijs: "),
            supplierLabel = new Label("Leverancier: "),
            addressLabel = new Label("Adres: "),
            postalLabel = new Label("Postcode: "),
            placeLabel = new Label("Plaats: ");
    private final TextField
            searchContent = new TextField("Zoek..."),
            nameContent = new TextField(),
            amountContent = new TextField(),
            minAmountContent = new TextField(),
            priceContent = new TextField(),
            buyPriceContent = new TextField(),
            addressContent = new TextField(),
            postalContent = new TextField(),
            placeContent = new TextField();
    private final VBox
            leftBox = new VBox(),
            rightBox = new VBox(space_Big);
    private final HBox
            listLabels = new HBox(5),
            detailsBox = new HBox(space_Small),
            control_MainBox = new HBox(space_3),
            control_SecBox = new HBox(space_3),
            mainBox = new HBox(space_Small);

    public StockScreen(ATDProgram controller) {
        super(controller);
        this.controller = controller;
        //put everything in the right places
        control_MainBox.getChildren().addAll(newButton, changeButton, removeButton);
        control_SecBox.getChildren().addAll(searchContent, filterSelector, newSupButton);
        leftBox.getChildren().addAll(listLabels, itemList, control_SecBox);
        rightBox.getChildren().addAll(detailsBox, control_MainBox);
        mainBox.getChildren().addAll(leftBox, rightBox);
        this.getChildren().add(mainBox);
        detailsBox.getChildren().addAll(
                new VBox(space_Big,
                        new HBox(space_Big, nameLabel, nameContent),
                        new HBox(space_Big, amountLabel, amountContent),
                        new HBox(space_Big, minAmountLabel, minAmountContent),
                        new HBox(space_Big, priceLabel, priceContent),
                        new HBox(space_Big, buyPriceLabel, buyPriceContent),
                        new HBox(space_Big, supplierLabel, supplierContent),
                        new HBox(space_Big, addressLabel, addressContent),
                        new HBox(space_Big, postalLabel, postalContent),
                        new HBox(space_Big, placeLabel, placeContent),
                        new HBox(space_Big, cancelButton, saveButton))
        );
        //set styles and sizes
        ////set width for all detail labels and textfields
        for (Node node : ((VBox) detailsBox.getChildren().get(0)).getChildren()) {
            if (((HBox) node).getChildren().get(0) instanceof Label)
                ((Label) ((HBox) node).getChildren().get(0)).setMinWidth(label_Normal);
            if (((HBox) node).getChildren().get(1) instanceof TextField)
                ((TextField) ((HBox) node).getChildren().get(1)).setMinWidth(label_Normal * 1.5);
        }
        supplierContent.setMinWidth(label_Normal * 1.5);
        detailsBox.setPadding(new Insets(space_Big));
        mainBox.setPadding(new Insets(space_Small));
        control_SecBox.setPadding(new Insets(space_Big, 0, space_Small, 0));
        listLabels.setPadding(new Insets(0, 0, 0, 7));
        detailsBox.getStyleClass().addAll("removeDisabledEffect", "detailsBox");
        leftBox.getStyleClass().add("removeDisabledEffect");
        itemList.getStyleClass().add("removeDisabledEffect");
        listLabels.getStyleClass().add("detailsBox");
        detailsBox.setPrefSize(450, 520);
        itemList.setPrefSize(450, 501);
        searchContent.setPrefSize(button_3, 50);
        filterSelector.setPrefSize(button_3, 50);
        newSupButton.setPrefSize(button_3, 50);
        cancelButton.setPrefSize(180, 50);
        saveButton.setPrefSize(180, 50);
        newButton.setPrefSize(button_3, 50);
        changeButton.setPrefSize(button_3, 50);
        removeButton.setPrefSize(button_3, 50);
        //details
        supplierContent.getItems().addAll(controller.getSuppliers());
        supplierContent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectSupplier(newValue);
        });
        setEditable(false);
        //Listview
        for (Product object : controller.getStock().getAllProducts())
            itemList.getItems().add(new ListItem(object));
        refreshList();
        itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            select(newValue);
        });
        //SearchField
        searchContent.setOnMouseClicked(e -> {
            if (searchContent.getText().equals("Zoek...")) searchContent.clear();
            else searchContent.selectAll();
        });
        searchContent.textProperty().addListener((observable, oldValue, newValue) -> {
            search(oldValue, newValue);
        });
        //Buttons and filter
        cancelButton.setOnAction(e -> {
            clearInput();
            setEditable(false);
        });
        saveButton.setOnAction(e -> save());
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
        newSupButton.setOnAction(e -> newSupplier());
        proceedButton.setOnAction(e -> boekop());
        newRuleButton.setOnAction(e -> addRule());
        filterSelector.getItems().addAll("Mode: Voorraad", "Mode: Benzine", "Mode: Onderdelen", "Mode: Bestellijst", "Mode: Opboeken");
        filterSelector.getSelectionModel().selectFirst();
        filterSelector.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            changeFilter(newValue.intValue());
        });
    }

    /**
     * voegt opboekregel toe
     */
    private void addRule() {
        itemList.getItems().add(itemList.getItems().size() - 1, new ListItem());
    }

    /**
     * fills the list with items that fit with the given filter or mode
     *
     * @param newValue selected filter
     */
    private void changeFilter(int newValue) {
        switch (newValue) {
            case 0:
            default: {//voorraad
                itemList.getItems().clear();
                for (Product object : controller.getStock().getAllProducts())
                    itemList.getItems().add(new ListItem(object));
                break;
            }
            case 1: {//Benzine
                itemList.getItems().clear();
                controller.getStock().getAllProducts().stream().filter(object -> object instanceof Fuel).forEach(object -> itemList.getItems().add(new ListItem(object)));
                break;
            }
            case 2: {//Onderdelen
                itemList.getItems().clear();
                controller.getStock().getAllProducts().stream().filter(object -> object instanceof Part).forEach(object -> itemList.getItems().add(new ListItem(object)));
                break;
            }
            case 3: {//Bestellijst
                itemList.getItems().clear();
                for (Entry<Object, Integer> entry : controller.getStock().getOrderedItems().entrySet())
                    itemList.getItems().add(new ListItem((Product) entry.getKey(), entry.getValue()));
                break;
            }
            case 4: {//Opboeken
                itemList.getItems().clear();
                itemList.getItems().add(new ListItem(newRuleButton, proceedButton));
                break;
            }
        }
        if (!searchContent.getText().equals("Zoek...")) search(null, searchContent.getText());
    }

    /**
     * updates stock with every item in the list after removing the last item(buttons)
     */
    private void boekop() {
        itemList.getItems().remove(itemList.getItems().size() - 1);
        for (ListItem listItem : itemList.getItems()) {
            controller.getStock().fill(listItem.getSelected(), listItem.getAmount());
        }
        filterSelector.getSelectionModel().selectFirst();
    }

    /*
     * removes the selected item
     */
    private void remove() {
        if (checkSelected()) {
            Notification confirm = new Notification(null, controller, "Weet u zeker dat u dit product wilt verwijderen?", Notification.notificationStyle.CONFIRM);
            confirm.showAndWait();
            if (confirm.getKeuze().equals("confirm")) {
                itemList.getItems().remove(selectedItem);
                controller.addorRemoveproduct(selectedObject, true);
                Notification notify = new Notification(null, controller, "Het product is verwijderd.", Notification.notificationStyle.NOTIFY);
                notify.showAndWait();
                refreshList();
            }
        }
    }

    private void newSupplier() {
        Notification newSupplier = new Notification(null, controller, "", Notification.notificationStyle.SUPPLIER);
        newSupplier.showAndWait();
        if (newSupplier.getKeuze().equals("confirm")) {
            controller.addorRemoveSupplier((ProductSupplier) newSupplier.getSelected(), false);
            supplierContent.getItems().add((ProductSupplier) newSupplier.getSelected());
        }
    }

    /**
     * saves the input in either a selected item or a new item
     */
    private void save() {
        if (checkInput()) {
            if (isChanging) {
                Notification changeConfirm = new Notification(null, controller, "Weet u zeker dat u deze wijzigingen wilt doorvoeren?", Notification.notificationStyle.CONFIRM);
                changeConfirm.showAndWait();
                switch (changeConfirm.getKeuze()) {
                    case "confirm": {
                        selectedObject.setName(nameContent.getText());
                        selectedObject.setAmount(0);
                        selectedObject.setMinAmount(Integer.parseInt(minAmountContent.getText()));
                        selectedObject.setSellPrice(Double.parseDouble(priceContent.getText()));
                        selectedObject.setSellPrice(Double.parseDouble(buyPriceContent.getText()));
                        selectedObject.setSupplier(supplierContent.getValue());
                        supplierContent.setDisable(true);
                        refreshList();
                    }
                    case "cancel":
                        clearInput();
                        setEditable(false);
                }
            } else {
                Notification getType = new Notification(null, controller, "", Notification.notificationStyle.TYPE);
                getType.showAndWait();
                switch (getType.getKeuze()) {
                    case "confirm": {
                        if (getType.getSelected().equals("Benzine")) {
                            Fuel newProduct = new Fuel(
                                    nameContent.getText(),
                                    0,
                                    Integer.parseInt(minAmountContent.getText()),
                                    Double.parseDouble(priceContent.getText()),
                                    Double.parseDouble(buyPriceContent.getText()),
                                    supplierContent.getValue());
                            controller.addorRemoveproduct(newProduct, false);
                            itemList.getItems().add(new ListItem(newProduct));
                        }
                        if (getType.getSelected().equals("Onderdeel")) {
                            Part newProduct = new Part(
                                    nameContent.getText(),
                                    0,
                                    Integer.parseInt(minAmountContent.getText()),
                                    Double.parseDouble(priceContent.getText()),
                                    Double.parseDouble(buyPriceContent.getText()),
                                    supplierContent.getValue());
                            controller.addorRemoveproduct(newProduct, false);
                            itemList.getItems().add(new ListItem(newProduct));
                        }
                        setEditable(false);
                    }
                    case "cancel": {
                        clearInput();
                        setEditable(false);
                    }
                }
            }
        } else {
            Notification notFilled = new Notification(null, controller, "Niet alle velden zijn juist ingevuld!", Notification.notificationStyle.NOTIFY);
            notFilled.showAndWait();
        }
    }

    /**
     * refreshes the list and every item
     */
    public void refreshList() {
        content.clear();
        content.addAll(itemList.getItems());
        itemList.getItems().clear();
        itemList.getItems().addAll(content);
        itemList.getItems().forEach(StockScreen.ListItem::refresh);
        changeFilter(filterSelector.getSelectionModel().getSelectedIndex());
        select(selectedItem);
    }

    /**
     * fill in supplier details in corresponding textfields
     *
     * @param newValue the selected supplier
     */
    private void selectSupplier(ProductSupplier newValue) {
        if (newValue != null) {
            ProductSupplier supplier = supplierContent.getSelectionModel().getSelectedItem();
            addressContent.setDisable(true);
            addressContent.setText(supplier.getAdress());
            postalContent.setDisable(true);
            postalContent.setText(supplier.getPostal());
            placeContent.setDisable(true);
            placeContent.setText(supplier.getPlace());
        }
    }

    /**
     * selects an item
     *
     * @param selectedValue the item to be selected
     */
    private void select(ListItem selectedValue) {
        //		if(filterSelector.getSelectionModel().getSelectedIndex() != 3){
        if (selectedValue != null) {
            selectedItem = selectedValue;
            selectedObject = selectedValue.getProduct();
            nameContent.setText(selectedObject.getName());
            amountContent.setText(Integer.toString(selectedObject.getAmount()));
            minAmountContent.setText(Integer.toString(selectedObject.getMinAmount()));
            priceContent.setText(ATDProgram.convert(selectedObject.getSellPrice()));
            buyPriceContent.setText(ATDProgram.convert(selectedObject.getBuyPrice()));
            supplierContent.setValue(selectedObject.getSupplier());
        }
        //		}
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
        amountContent.setDisable(true);
        if (checkSelected()) {
            buyPriceContent.setText(Double.toString(selectedObject.getBuyPrice()));
            priceContent.setText(Double.toString(selectedObject.getSellPrice()));
        }
        control_MainBox.setDisable(enable);
        leftBox.setDisable(enable);
    }

    /**
     * clears all the inputfields
     */
    private void clearInput() {
        ((VBox) detailsBox.getChildren().get(0)).getChildren().stream().filter(node1 -> ((HBox) node1).getChildren().get(1) instanceof TextField).forEach(node1 -> ((TextField) ((HBox) node1).getChildren().get(1)).clear());
        supplierContent.getSelectionModel().clearSelection();
    }

    /**
     * checks if all the inputfields are filled in
     *
     * @return false if one of the inputs is null
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private boolean checkInput() {
        for (Node node1 : ((VBox) detailsBox.getChildren().get(0)).getChildren())
            if (((HBox) node1).getChildren().get(1) instanceof TextField && ((HBox) node1).getChildren().get(1) != amountContent)
                if (((TextField) ((HBox) node1).getChildren().get(1)).getText().isEmpty())
                    return false;
        try {
            Integer.parseInt(minAmountContent.getText());
            Double.parseDouble(priceContent.getText());
            Double.parseDouble(buyPriceContent.getText());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * checks if an item is selected in the list
     *
     * @return false if nothing is selected
     */
    private boolean checkSelected() {
        return selectedObject != null;
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
        content.stream().filter(entry -> entry.getProduct().getName().contains(newVal)
                || Double.toString(entry.getProduct().getSellPrice()).contains(newVal)
                || Integer.toString(entry.getProduct().getAmount()).contains(newVal)
                || entry.getProduct().getSupplier().getName().contains(newVal)).forEach(entry -> itemList.getItems().add(entry));
    }

    // this represents every item in the list, it has different constructor for every filter option
    public class ListItem extends HBox {
        private Product object;
        private final ComboBox<Product> productSelector = new ComboBox<>();
        private final TextField input = new TextField();
        private final Label
                itemPriceLabel = new Label();
        private final Label itemNameLabel = new Label();
        private final Label itemSupplierLabel = new Label();
        private final Label itemAmountLabel = new Label();
        private final Label itemBuyPriceLabel = new Label();
        private final Label itemOrderedLabel = new Label();
        private final Label opboekSupplierLabel = new Label();
        private final Label opboekOrderedLabel = new Label();

        public ListItem(Product object) {
            //no filter
            this.object = object;
            refresh();
            setSpacing(5);
            getChildren().addAll(
                    itemNameLabel,
                    new Separator(Orientation.VERTICAL),
                    itemPriceLabel,
                    new Separator(Orientation.VERTICAL),
                    itemSupplierLabel,
                    new Separator(Orientation.VERTICAL),
                    itemAmountLabel);
            getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setPrefWidth(item_Normal));
            listLabels.getChildren().clear();
            listLabels.getChildren().addAll(
                    new Label("Naam"),
                    new Separator(Orientation.VERTICAL),
                    new Label("Prijs"),
                    new Separator(Orientation.VERTICAL),
                    new Label("Leverancier"),
                    new Separator(Orientation.VERTICAL),
                    new Label("Aantal"));
            listLabels.getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setPrefWidth(item_Normal));
        }

        public ListItem(Product object, int amount) {
            //bestellijst filter
            this.object = object;
            itemOrderedLabel.setText(Integer.toString(amount));
            refresh();
            setSpacing(5);
            getChildren().addAll(
                    itemNameLabel,
                    new Separator(Orientation.VERTICAL),
                    itemBuyPriceLabel,
                    new Separator(Orientation.VERTICAL),
                    itemSupplierLabel,
                    new Separator(Orientation.VERTICAL),
                    itemOrderedLabel);
            getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setPrefWidth(item_Normal));
            listLabels.getChildren().clear();
            listLabels.getChildren().addAll(
                    new Label("Naam"),
                    new Separator(Orientation.VERTICAL),
                    new Label("InkoopPrijs"),
                    new Separator(Orientation.VERTICAL),
                    new Label("Leverancier"),
                    new Separator(Orientation.VERTICAL),
                    new Label("Aantal besteld"));
            listLabels.getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setPrefWidth(item_Normal));
        }

        public ListItem() {
            //opboeken filter
            productSelector.getItems().addAll(controller.getProducts());
            productSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    opboekSupplierLabel.setText(newValue.getSupplier().getName());
                    try {
                        opboekOrderedLabel.setText(Integer.toString(controller.getStock().getOrderedItems().get(newValue)));
                    } catch (Exception e) {
                        opboekOrderedLabel.setText("0");
                    }
                }
            });
            getChildren().addAll(
                    productSelector,
                    new Separator(Orientation.VERTICAL),
                    input,
                    new Separator(Orientation.VERTICAL),
                    opboekOrderedLabel,
                    new Separator(Orientation.VERTICAL),
                    opboekSupplierLabel);
            getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setPrefWidth(item_Normal));
            productSelector.setPrefWidth(item_Normal);
            input.setPrefWidth(item_Normal);
            setSpacing(5);
        }

        public ListItem(Button b1, Button b2) {
            listLabels.getChildren().clear();
            listLabels.getChildren().addAll(
                    new Label("Product"),
                    new Separator(Orientation.VERTICAL),
                    new Label("Op te boeken"),
                    new Separator(Orientation.VERTICAL),
                    new Label("Aantal besteld"),
                    new Separator(Orientation.VERTICAL),
                    new Label("Leverancier"));
            listLabels.getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setPrefWidth(item_Normal));

            setSpacing(5);
            getChildren().addAll(b1, b2);
        }

        /**
         * fills in all the labels with the latest values
         */
        public void refresh() {
            itemNameLabel.setText(object.getName());
            itemPriceLabel.setText((ATDProgram.convert(object.getSellPrice())));
            itemSupplierLabel.setText(object.getSupplier().getName());
            itemAmountLabel.setText(Integer.toString(object.getAmount()));
            itemBuyPriceLabel.setText(ATDProgram.convert(object.getBuyPrice()));
        }

        /**
         * @return the selected item	(opboeken)
         */
        public Product getSelected() {
            return productSelector.getSelectionModel().getSelectedItem();
        }

        /**
         * @return the filled in amount (opboeken)
         */
        public int getAmount() {
            try {
                return Integer.parseInt(input.getText());
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        /**
         * @return the object this item represents
         */
        public Product getProduct() {
            return object;
        }
    }
}
