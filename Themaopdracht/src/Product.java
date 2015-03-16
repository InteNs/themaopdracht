
public class Product {
	private String name;
	private int amount, orderAmount;
	private double buyPrice, sellPrice;
	private Supplier supplier;
	public Product(String name, int amount, int orderAmount, double buyPrice,double sellPrice, Supplier supplier) {
		this.name = name;
		this.amount = amount;
		this.orderAmount = orderAmount;
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
		this.supplier = supplier;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}
	public double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}
	public double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
}
