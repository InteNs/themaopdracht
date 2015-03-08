
public class Invoice {
	private double totalPrice;
	private int daysToPay;
	private boolean isPayed;
	public Invoice(double amount, int daysToPay){
		this.totalPrice = amount;
		this.daysToPay = daysToPay;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double price) {
		this.totalPrice = price;
	}
	public int getDaysToPay() {
		return daysToPay;
	}
	public void setDaysToPay(int daysToPay) {
		this.daysToPay = daysToPay;
	}
	public boolean isPayed() {
		return isPayed;
	}
	public void setPayed(boolean isPayed) {
		this.isPayed = isPayed;
	}
	
}

