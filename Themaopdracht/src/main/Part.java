package main;

public class Part extends Product {

    public Part(String name, int amount, int minAmount, double buyPrice,
                double sellPrice, ProductSupplier supplier) {
        super(name, amount, minAmount, buyPrice, sellPrice, supplier);
    }

    @Override
    public String toString() {
        return "[Part]"+super.toString();
    }
}

