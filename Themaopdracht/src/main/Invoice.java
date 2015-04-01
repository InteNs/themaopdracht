package main;
import java.time.LocalDate;
import java.util.ArrayList;



public class Invoice {
	private LocalDate invoiceDate;
	private double totalPrice;
	private boolean isPayed;
	private Customer customer;
	private ArrayList<String> InvoiceItems;
	public enum PayMethod{CASH,PIN,CREDIT};
	public Invoice(){
		InvoiceItems = new ArrayList<String>();
		invoiceDate = LocalDate.now();
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
	public ArrayList<String> getInvoiceItems() {
		return InvoiceItems;
	}
	public void setPayed(boolean isPayed) {
		this.isPayed = isPayed;
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

