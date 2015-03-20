package main;
import java.util.ArrayList;



public class Invoice {
	private double totalPrice;
	private boolean isPayed;
	private Customer customer;
	private ArrayList<String> InvoiceItems;
	public enum PayMethod{CASH,PIN,CREDIT};
	public Invoice(double amount){
		this.totalPrice = amount;
		InvoiceItems = new ArrayList<String>();
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	/**
	 * @param price
	 * @param description "name - amount - totalPrice"
	 */
	public void addItem(double price, String description, String amount) {
		this.totalPrice += price;
		InvoiceItems.add(description+"\tX"+amount+"\t$"+price+"\n");
	}
	public boolean isPayed() {
		return isPayed;
	}
	public void payNow(PayMethod payMethod){
	}
	public void bindToCustomer(Customer customer){
		this.customer = customer;
	}
	public Customer getCustomer(){
		return customer;
	}
	public String toString(){
		String info = "", line = "", items = "";
		if(customer !=null){
			info = "customer: "+customer+"\n";
		}
		line = "-------------------------------\n";
		for (String string : InvoiceItems) {
			items = items + string;
		}
		return info + line + items;
	}
}

