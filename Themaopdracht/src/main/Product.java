package main;

public abstract class Product {
    String name;
    int amount;
    int minAmount;
    double buyPrice;
    double sellPrice;
    ProductSupplier supplier;

    public Product(String name, int amount, int minAmount, double buyPrice, double sellPrice, ProductSupplier supplier) {
        this.name = name;
        this.amount = amount;
        this.minAmount = minAmount;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.supplier = supplier;
    }

    protected Product() {
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public ProductSupplier getSupplier() {
        return supplier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setSupplier(ProductSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return name;
    }
}
