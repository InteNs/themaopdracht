package screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.ATDProgram;
import main.Invoice;

import java.time.LocalDate;

public class FinancesScreen extends Screen {
    private final ATDProgram controller;
    private final PieChart chart = new PieChart();
    private final double
            spacingBoxes = 10;
    private final double widthLabels = 120;
    private final Label
            grossProfitLabel = new Label("Omzet: ");
    private final Label taxLabel = new Label("BTW: ");
    private final Label amountLabel = new Label("Aantal facturen: ");
    private final Label grossProfitLabelContent = new Label("-");
    private final Label taxLabelContent = new Label("-");
    private final Label amountLabelContent = new Label("-");
    private final Label fromLabel = new Label("Kies van datum:\t");
    private final Label toLabel = new Label("\t\tKies tot datum:\t");
    private final DatePicker
            fromDate = new DatePicker();
    private final DatePicker toDate = new DatePicker();
    private final VBox
            rightBox = new VBox(20);
    private final HBox
            details = new HBox(spacingBoxes);
    private final HBox mainButtonBox = new HBox(spacingBoxes);
    private final HBox mainBox = new HBox(spacingBoxes);

    public FinancesScreen(ATDProgram controller) {
        super(controller);
        this.controller = controller;
        chart.setTitle("Facturen op totaalprijs");
        //StockDetails

        details.getChildren().addAll(
                new VBox(20,
                        new HBox(20, grossProfitLabel, grossProfitLabelContent),
                        new HBox(20, taxLabel, taxLabelContent),
                        new HBox(20, amountLabel, amountLabelContent)
                ));
        details.setPrefSize(910, 520);
        details.getStyleClass().add("detailsBox");
        details.setPadding(new Insets(20));
        //set width for all detail labels and textfields
        ((VBox) details.getChildren().get(0)).getChildren().stream().filter(node -> ((HBox) node).getChildren().get(0) instanceof Label).forEach(node -> ((Label) ((HBox) node).getChildren().get(0)).setMinWidth(widthLabels));
        //Main Buttons and filter
        mainButtonBox.getChildren().addAll(
                fromLabel,
                fromDate,
                toLabel,
                toDate
        );
        fromDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && oldValue != newValue && toDate.getValue() != null && (toDate.getValue().isAfter(newValue) || toDate.getValue().isEqual(newValue))) {
                getFinanceData(newValue, toDate.getValue());
            }
        });
        toDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && oldValue != newValue && fromDate.getValue() != null && (fromDate.getValue().isBefore(newValue) || fromDate.getValue().isEqual(newValue))) {
                getFinanceData(fromDate.getValue(), newValue);
            }
        });
        fromDate.setValue(LocalDate.now());
        toDate.setValue(LocalDate.now());
        //Make & merge left & right
        details.getChildren().add(chart);
        rightBox.getChildren().addAll(details, mainButtonBox);
        mainBox.getChildren().addAll(rightBox);
        mainBox.setPadding(new Insets(10));
        this.getChildren().add(mainBox);
    }

    private void drawCharts() {
        int onder100 = 0;
        int over100 = 0;
        for (Invoice invoice : controller.getInvoices()) {
            if (invoice.isPayed() && invoice.getTotalPrice() < 100) onder100++;
            else if (invoice.isPayed()) over100++;
        }
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("<100", onder100),
                        new PieChart.Data(">100", over100));
        chart.getData().clear();
        chart.getData().addAll(pieChartData);
    }

    private void getFinanceData(LocalDate from, LocalDate to) {
        double omzet = 0.0;
        double btw = 0.0;
        int aantal = 0;
        for (Invoice invoice : controller.getInvoices()) {
            if (invoice.isPayed() && ATDProgram.periodIsOverlapping(from, to, invoice.getInvoiceDate(), invoice.getInvoiceDate())) {
                aantal++;
                omzet += invoice.getTotalPrice();
            }
            btw = (omzet / 121) * 21;
            grossProfitLabelContent.setText(ATDProgram.convert(omzet));
            taxLabelContent.setText(ATDProgram.convert(btw));
            amountLabelContent.setText(aantal + "");
        }
    }

    @Override
    public void refreshList() {
        drawCharts();
        getFinanceData(fromDate.getValue(), toDate.getValue());

    }
}

	