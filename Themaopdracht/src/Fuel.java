
public class Fuel extends Product {

	public Fuel(String name, int partId, int amount, int orderAmount,
			double buyPrice, double sellPrice, Supplier supplier) {
		super(name, amount, orderAmount, buyPrice, sellPrice, supplier);
	}
}
