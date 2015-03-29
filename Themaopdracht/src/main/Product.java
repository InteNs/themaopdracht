package main;

public class Product {
	private String name;
	private int amount, minAmount;
	private double buyPrice, sellPrice;
	private ProductSupplier supplier;
	public Product(String name, int amount, int minAmount, double buyPrice,double sellPrice, ProductSupplier supplier) {
		this.name = name;
		this.amount = amount;
		this.minAmount = minAmount;
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
	public int getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(int orderAmount) {
		this.minAmount = orderAmount;
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
	public ProductSupplier getSupplier() {
		return supplier;
	}
	public void setSupplier(ProductSupplier supplier) {
		this.supplier = supplier;
	}
	@Override
	public String toString() {
		return name + " - €" + sellPrice + " - " + supplier.getName();
	}
	
}
