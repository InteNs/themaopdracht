package main;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;



public class Invoice {
	private LocalDate invoiceDate;
	private double totalPrice;
	private boolean isPayed;
	private Customer customer;
	private ArrayList<InvoiceItem> items = new ArrayList<InvoiceItem>();
	public enum PayMethod{CASH,PIN,CREDIT};
	public Invoice(){
		invoiceDate = LocalDate.now();
	}
	public double getTotalPrice() {
		return totalPrice;
	}

	public boolean add(InvoiceItem e) {
		totalPrice += e.getTotal();
		return items.add(e);	
	}
	
	public ArrayList<InvoiceItem> getItems() {
		return items;
	}
	public boolean isPayed() {
		return isPayed;
	}
	public void payNow(PayMethod payMethod){
		isPayed = true;
	}
	public void bindToCustomer(Customer customer){
		this.customer = customer;
	}
	public Customer getCustomer(){
		return customer;
	}
	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}
	public String toString(){
		return "invoice";
	}
	public class InvoiceItem extends HBox{
		private ATDProgram controller;
		private int amount;
		private double price, semiTotalPrice;
		private Label description = new Label(),amountL = new Label(),priceL = new Label(),totalPriceL = new Label();;
		public InvoiceItem(String desc, double price, int amount){
			this.price = price;
			this.amount = amount;
			this.semiTotalPrice = amount*price;
			description.setText(desc);
			priceL.setText( controller.nf.format(price));
			amountL.setText( Integer.toString(amount));
			totalPriceL.setText( controller.nf.format(semiTotalPrice));
			setSpacing(5);
			getChildren().addAll(
					amountL,
					new Separator(Orientation.VERTICAL),
					description,
					new Separator(Orientation.VERTICAL),
					priceL,
					new Separator(Orientation.VERTICAL),
					totalPriceL);
			((Label)getChildren().get(0)).setPrefWidth(50);
			((Label)getChildren().get(2)).setPrefWidth(100);
			((Label)getChildren().get(4)).setPrefWidth(100);
			((Label)getChildren().get(6)).setPrefWidth(100);
		}
		public double getTotal(){
			return price * amount;
		}
		public void refresh(){
			totalPriceL.setText(Double.toString(price * amount));
		}
	}
}

