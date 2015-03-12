import java.util.HashMap;


public class Order {
	private int orderId;
	private HashMap<Object, Integer> orderList;
	public Order(int orderId, HashMap<Object, Integer> orderList) {
		this.orderId = orderId;
		this.orderList = orderList;
	}
}
