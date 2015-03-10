
public class Fuel {
	private String type;
	private int liters, tSIC;
	private double buyPrice, sellPrice; //price per liter
	private Supplier supplier;
	public Fuel(String type, int tSIC, double buyPrice, double sellPrice, Supplier supplier) {
		this.supplier = supplier;
		this.type = type;
		this.tSIC = tSIC;
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
	}
	public String getType() {
		return type;
	}
	public int gettSIC() {
		return tSIC;
	}
	public int getLiters() {
		return liters;
	}
	public void setLiters(int liters){
		this.liters = liters;
	}
	public double getBuyPrice() {
		return buyPrice;
	}
	public double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}
	
}
