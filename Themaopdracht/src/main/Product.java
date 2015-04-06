package main;

public abstract class Product {
	protected ATDProgram controller;
	protected String name;
	protected int amount, minAmount;
	protected double buyPrice, sellPrice;
	protected ProductSupplier supplier;
	public Product(String name, int amount, int minAmount, double buyPrice,double sellPrice, ProductSupplier supplier) {
		this.name = name;
		this.amount = amount;
		this.minAmount = minAmount;
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
		this.supplier = supplier;
	}
	public abstract String getName();
	public abstract void setName(String name);
	public abstract int getAmount();
	public abstract void setAmount(int amount);
	public abstract int getMinAmount();
	public abstract void setMinAmount(int orderAmount);
	public abstract double getBuyPrice();
	public abstract void setBuyPrice(double buyPrice);
	public abstract double getSellPrice();
	public abstract void setSellPrice(double sellPrice);
	public abstract ProductSupplier getSupplier();
	public abstract void setSupplier(ProductSupplier supplier);
	@Override
	public abstract String toString();
	
}
