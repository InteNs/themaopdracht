package screens;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.ATDProgram;
import main.Invoice;

public class FinancesScreen extends Screen {
	private ATDProgram controller;
	private PieChart chart = new PieChart();
	private double
			spacingBoxes = 10,
			widthLabels = 120;
	private Label 
			grossProfitLabel = new Label("Omzet: "),
			taxLabel = new Label("BTW: "), 
			amountLabel = new Label("Aantal facturen: "),
			grossProfitLabelContent = new Label("-"), 
			taxLabelContent = new Label("-"), 
			amountLabelContent = new Label("-"), 
			fromLabel = new Label("Kies van datum:\t"),
			toLabel = new Label("\t\tKies tot datum:\t");
	private DatePicker 
			fromDate = new DatePicker(),
			toDate = new DatePicker();
	private VBox
			rightBox = new VBox(20);
	private HBox 
			details = new HBox(spacingBoxes), 
			mainButtonBox = new HBox(spacingBoxes), 
			mainBox = new HBox(spacingBoxes);
	public FinancesScreen(ATDProgram controller) {
		super(controller);
		this.controller = controller;
		chart.setTitle("Facturen op totaalprijs");
		//StockDetails
		
		details.getChildren().addAll(
				new VBox(20,
						new HBox(20,grossProfitLabel,	grossProfitLabelContent),
						new HBox(20,taxLabel,			taxLabelContent),
						new HBox(20,amountLabel,		amountLabelContent)
						));
		details.setPrefSize(910, 520);
		details.getStyleClass().add("detailsBox");
		details.setPadding(new Insets(20));
		//set width for all detail labels and textfields
		for (Node node : ((VBox)details.getChildren().get(0)).getChildren()) {
			if(((HBox)node).getChildren().get(0) instanceof Label)
				((Label)((HBox)node).getChildren().get(0)).setMinWidth(widthLabels);
		}
		//Main Buttons and filter
		mainButtonBox.getChildren().addAll(
				fromLabel,
				fromDate,
				toLabel,
				toDate
				);
		fromDate.valueProperty().addListener((observable, oldValue, newValue)->{
			if(newValue != null && oldValue != newValue && toDate.getValue() != null && (toDate.getValue().isAfter(newValue) || toDate.getValue().isEqual(newValue))){
				getFinanceData(newValue, toDate.getValue());
			}
		});
		toDate.valueProperty().addListener((observable, oldValue, newValue)->{
			if(newValue != null && oldValue != newValue && fromDate.getValue() != null && (fromDate.getValue().isBefore(newValue) || fromDate.getValue().isEqual(newValue))){
				getFinanceData(fromDate.getValue(), newValue);
			}
		});
        fromDate.setValue(LocalDate.now());
		toDate.setValue(LocalDate.now());
		//Make & merge left & right
        details.getChildren().add(chart);
		rightBox.getChildren().addAll(details,mainButtonBox);
		mainBox.getChildren().addAll (rightBox);
		mainBox.setPadding(new Insets(10));
		this.getChildren().add(mainBox);
	}
	private void drawCharts(){
		int onder100 = 0;
		int over100  = 0;
		for (Invoice invoice : controller.getInvoices()) {
			if(invoice.isPayed()&&invoice.getTotalPrice()<100)onder100++;
			else if(invoice.isPayed()) over100++;
		}
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("<100", onder100),
                new PieChart.Data(">100", over100));
		chart.getData().clear();
		chart.getData().addAll(pieChartData);
	}
	public void getFinanceData(LocalDate from, LocalDate to) {
		double omzet = 0.0;
		double btw = 0.0;
		int aantal = 0;
		for (Invoice invoice : controller.getInvoices()) {
			if(invoice.isPayed()&&ATDProgram.isOverlapping(from, to, invoice.getInvoiceDate(), invoice.getInvoiceDate())){
						aantal++;
						omzet += invoice.getTotalPrice();
			}
			btw = (omzet/121)*21;
			grossProfitLabelContent.setText(ATDProgram.convert(omzet));
			taxLabelContent.setText(ATDProgram.convert(btw));
			amountLabelContent.setText(aantal+"");
		}
	}
	@Override
	public void refreshList() {
		drawCharts();
		getFinanceData(fromDate.getValue(), toDate.getValue());
		
	}
}

	