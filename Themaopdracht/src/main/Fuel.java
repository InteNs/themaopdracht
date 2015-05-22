package main;

public class Fuel extends Product {

    public Fuel(String name, int amount, int minAmount, double buyPrice,
                double sellPrice, ProductSupplier supplier) {
        super(name, amount, minAmount, buyPrice, sellPrice, supplier);
    }

    @Override
    public String toString() {
        return "[Fuel]"+super.toString();
    }
}
