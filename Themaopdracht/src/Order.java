import java.util.HashMap;


public class Order {
	private HashMap<Object, Integer> orderList;
	public Order(int orderId, HashMap<Object, Integer> orderList) {
		this.orderList = orderList;
	}
}
