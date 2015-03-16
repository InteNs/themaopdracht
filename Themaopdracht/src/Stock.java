import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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
	public void useProduct(Product product, int amount){
		product.setAmount(product.getAmount() - amount);
		if(product.getAmount()<1) this.orderProduct(product,product.getOrderAmount());
	}
	public void newProduct(Product product){
		products.add(product);
	}
	public List<Product> getAllProducts(){
		return products;
	}
	public HashMap<Object, Integer> getOrderedItems(){
		return toOrder;
	}
	public void sendOrder(int orderId){
		Order order = new Order(toOrder);
		toOrder.clear();
	}
	public void fill(Product product, int amount){
		product.setAmount(product.getAmount()+amount);
	}
}
