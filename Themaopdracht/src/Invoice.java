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
	 * 
	 * @param price
	 * @param description "name - amount - totalPrice"
	 */
	public void addItem(double price, String description) {
		this.totalPrice += price;
		InvoiceItems.add(description);
	}
	public boolean isPayed() {
		return isPayed;
	}
	public void payNow(PayMethod payMethod){
		
	}
	public void bindToCustomer(Customer customer){
		this.customer = customer;
	}
}

