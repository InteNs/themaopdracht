
public class Factuur {
	private double amount;
	private int daysToPay;
	private boolean isPayed;
	public Factuur(double amount){
		this.amount = amount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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

