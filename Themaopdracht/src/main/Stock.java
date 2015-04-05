package main;
import java.util.ArrayList;
import java.util.HashMap;


public class Stock {
	private ArrayList<Product> products;
	private HashMap<Object, Integer> toOrder;
	public Stock(){
		products = new ArrayList<Product>();
		toOrder = new HashMap<Object, Integer>(); // maybe Integer, Integer als Object, Integer niet lukt
	}
	public void orderProduct(Product product, int amount){
		 toOrder.put(product, amount);
	}
	public boolean useProduct(Product product, int amount){
		if(product.getAmount()<amount) return false;
		product.setAmount(product.getAmount() - amount);
		checkStock();
		return true;
	}
	public void newProduct(Product product){
		products.add(product);
		checkStock();
	}
	public void removeProduct(Product product){
		products.remove(product);
	}
	private void checkStock(){
		for (Product product : products) {
			if(product.getAmount()<product.getMinAmount()) this.orderProduct(product,product.getMinAmount()-product.getAmount());
		}
	}
	public ArrayList<Product> getAllProducts(){
		return products;
	}
	public HashMap<Object, Integer> getOrderedItems(){
		return toOrder;
	}
	@SuppressWarnings("unused")
	public void sendOrder(int orderId){
		Order order = new Order(toOrder);
		toOrder.clear();
	}
	public void fill(Product product, int amount){
		product.setAmount(product.getAmount()+amount);
	}
}
