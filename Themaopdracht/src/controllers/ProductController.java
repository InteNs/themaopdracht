package controllers;

import main.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by InteNs on 22.mei.2015.
 */
public class ProductController {
    private final ArrayList<Product> products;
    private final HashMap<Object, Integer> orderMap;
    private final ArrayList<ProductSupplier> suppliers;

    /**
     * create a new controller(use only once)
     */
    public ProductController() {
        products = new ArrayList<>();
        orderMap = new HashMap<>();
        suppliers = new ArrayList<>();
    }

    /**
     * @return an arraylist with all productsuppliers
     */
    public ArrayList<ProductSupplier> getSuppliers() {
        return suppliers;
    }

    /**
     * @return a list with all the products
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * @return a HashMap with all the ordered products
     */
    public HashMap<Object, Integer> getOrderMap() {
        return orderMap;
    }

    /**
     * @return a list with all products that are fuel
     */
    public List getFuel(){
       return products.stream()
               .filter(product -> product instanceof Fuel)
               .collect(Collectors.toList());
   }

    /**
     * @return a list with all products that are parts
     */
    public List getparts(){
        return products.stream()
                .filter(product -> product instanceof Part)
                .collect(Collectors.toList());
    }

    /**
     * search for Product (on name, sellprice amount and suppliername
     * @param oldVal    old value of changelistener
     * @param newVal    new value of changelistener
     * @return the list with results of the search
     */
    public List<Product> searchCustomer(String oldVal, String newVal) {
        ArrayList<Product> results = new ArrayList<>();

        if (oldVal != null && (newVal.length() < oldVal.length())) {
            //actor has deleted a character, so reset the search
            results.clear();
        }
        //add an item if any item that exists contains any value that has been searched for
        products.stream()
                .filter(product ->
                        product.getName().contains(newVal)
                                || Double.toString(product.getSellPrice()).contains(newVal)
                                || Integer.toString(product.getAmount()).contains(newVal)
                                || product.getSupplier().getName().equals(newVal))
                .forEach(results::add);
        return results;
    }

    /**
     * create a new part
     * @param name      partname
     * @param amount    initial amount in stock
     * @param minAmount minimal amount(new ordered when this is passed
     * @param buyPrice  the price the company pays for this product
     * @param sellPrice the price the customers pay
     * @param supplier  the supplier of this product(existing or new)
     */
    public void newPart(String name, int amount, int minAmount, double buyPrice, double sellPrice, ProductSupplier supplier){
        products.add(new Part(name,amount,minAmount,buyPrice,sellPrice,supplier));
    }

    /**
     * create a new Fuel
     * @param name      partname
     * @param amount    initial amount in stock
     * @param minAmount minimal amount(new ordered when this is passed
     * @param buyPrice  the price the company pays for this product
     * @param sellPrice the price the customers pay
     * @param supplier  the supplier of this product(existing or new)
     */
    public void newFuel(String name, int amount, int minAmount, double buyPrice, double sellPrice, ProductSupplier supplier){
        products.add(new Fuel(name,amount,minAmount,buyPrice,sellPrice,supplier));
    }

    /**
     * create a new supplier
     * @param name supplier name
     * @param adress adress
     * @param postal postal code
     * @param place  place
     */
    public void newSupplier(String name, String adress, String postal, String place){
        suppliers.add(new ProductSupplier(name, adress, postal, place));
    }

    /**
     * remove a product
     * @param product the product to be removed
     */
    public void removeProduct(Product product) {
        products.remove(product);
    }

    /**
     * remove a product by name
     * @param name the name of the product to be removed
     */
    public void removeProduct(String name){
        products.stream()
                .filter(product -> product.getName().equals(name))
                .forEach(products::remove);
    }

    /**
     * remove a supplier
     * @param supplier to be removed
     */
    public void removeSupplier(ProductSupplier supplier){
        suppliers.remove(supplier);
    }

    /**
     * add N to the total amount of a product
     * @param product the product
     * @param amount  the amount to be added
     */
    public void addAmount(Product product, int amount) {
        product.setAmount(product.getAmount() + amount);
    }

    /**
     * substract N from the total amount of a product
     * @param product the product
     * @param amount the amount to be substracted
     */
    public void subtractAmount(Product product, int amount) {
        if (product.getAmount() < amount) return;
        product.setAmount(product.getAmount() - amount);
        checkStock();
    }

    /**
     * put a certain amount of products on the ordermap
     * @param product the product to put
     * @param amount  the amount of this product to put
     */
    private void orderProduct(Product product, int amount) {
        orderMap.put(product, amount);
    }

    /**
     * call this method if you want the app to automatically order products
     * wich have an amount under the minimumamount
     */
    private void checkStock() {
        products.stream()
                .filter(product -> product.getAmount() < product.getMinAmount())
                .forEach(product -> this.orderProduct(product, product.getMinAmount() - product.getAmount()));
    }

    /**
     * send the order to the supplier(s)
     */
    @SuppressWarnings("unused")
    public void sendOrder() {
        Order order = new Order(orderMap);
        orderMap.clear();
    }

}
