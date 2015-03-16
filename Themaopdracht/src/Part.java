
public class Part {
	private String name;
	private int amount, orderAmount;
	private double buyPrice, sellPrice;
	private Supplier supplier;
	public Part(String name, int partId, int amount, int orderAmount, double buyPrice,double sellPrice, Supplier supplier) {
		this.name = name;
		this.amount = amount;
		this.orderAmount = orderAmount;
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
		this.supplier = supplier;
	}
	public double getSellPrice() {
		return sellPrice;
	}
	public int getOrderAmount(){
		return orderAmount;
	}
	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getName() {
		return name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getBuyPrice() {
		return buyPrice;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	
	
}
