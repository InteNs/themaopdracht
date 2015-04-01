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
	public void setPayed(boolean isPayed) {
		this.isPayed = isPayed;
	}
	public String toString(){
		return "";
	}
	public class InvoiceItem extends HBox{
		private int amount;
		private double price, totalPrice;
		private Label description = new Label(),amountL = new Label(),priceL = new Label(),totalPriceL = new Label();;
		public InvoiceItem(String desc, double price, int amount){
			description.setText(desc);
			this.price = price;
			priceL.setText( Double.toString(price));
			this.amount = amount;
			amountL.setText( Integer.toString(amount));
			
			setSpacing(5);
			getChildren().addAll(
					amountL,
					new Separator(Orientation.VERTICAL),
					description,
					new Separator(Orientation.VERTICAL),
					priceL,
					new Separator(Orientation.VERTICAL),
					totalPriceL);
			((Label)getChildren().get(0)).setPrefWidth(80);
			((Label)getChildren().get(2)).setPrefWidth(150);
			((Label)getChildren().get(4)).setPrefWidth(80);
		}
		public double getTotal(){
			return price * amount;
		}
	}
}

