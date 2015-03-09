

public class Invoice {
	private double totalPrice;
	private boolean isPayed;
	private Customer customer;
	public enum PayMethod{CASH,PIN,CREDIT};
	public Invoice(double amount){
		this.totalPrice = amount;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double price) {
		this.totalPrice = price;
	}
	public boolean isPayed() {
		return isPayed;
	}
	public void payNow(){
		
	}
	public void bindToCustomer(Customer customer){
		this.customer = customer;
	}
}

