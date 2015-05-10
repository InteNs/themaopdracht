package main;

import java.util.ArrayList;
import java.util.HashMap;


public class Stock {
    private final ArrayList<Product> products;
    private final HashMap<Object, Integer> toOrder;

    public Stock() {
        products = new ArrayList<>();
        toOrder = new HashMap<>(); // maybe Integer, Integer als Object, Integer niet lukt
    }

    private void orderProduct(Product product, int amount) {
        toOrder.put(product, amount);
    }

    public void useProduct(Product product, int amount) {
        if (product.getAmount() < amount) return;
        product.setAmount(product.getAmount() - amount);
        checkStock();
    }

    public void newProduct(Product product) {
        products.add(product);
        checkStock();
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    private void checkStock() {
        products.stream().filter(product -> product.getAmount() < product.getMinAmount()).forEach(product -> this.orderProduct(product, product.getMinAmount() - product.getAmount()));
    }

    public ArrayList<Product> getAllProducts() {
        return products;
    }

    public HashMap<Object, Integer> getOrderedItems() {
        return toOrder;
    }

    @SuppressWarnings("unused")
    public void sendOrder(int orderId) {
        Order order = new Order(toOrder);
        toOrder.clear();
    }

    public void fill(Product product, int amount) {
        product.setAmount(product.getAmount() + amount);
    }
}
