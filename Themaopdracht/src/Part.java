
public class Part {
	private String name;
	private int partId, amount;
	private double buyPrice, sellPrice;
	private Supplier supplier;
	public Part(String name, int partId, int amount, double buyPrice,double sellPrice, Supplier supplier) {
		this.name = name;
		this.partId = partId;
		this.amount = amount;
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
		this.supplier = supplier;
	}
	public double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getName() {
		return name;
	}
	public int getPartId() {
		return partId;
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
