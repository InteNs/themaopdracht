import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Stock {
	private ArrayList<Part> partTypes;
	private ArrayList<Fuel> fuelTypes;
	private HashMap<Object, Integer> toOrder;
	public Stock(){
		partTypes = new ArrayList<Part>();
		fuelTypes = new ArrayList<Fuel>();
		toOrder = new HashMap<Object, Integer>(); // maybe Integer, Integer als Object, Integer niet lukt
	}
	public void OrderPart(Part part, int amount){
		 toOrder.put(part, amount);
	}
	public void OrderFuel(int tSIC, int liters){
		for (Fuel fuelType : fuelTypes){
			if(fuelType.gettSIC() == tSIC) toOrder.put(tSIC, liters);
		}
	}
	public void usePart(Part part, int amount){
		part.setAmount(part.getAmount() - amount);
		if(part.getAmount()<1) this.OrderPart(part,part.getOrderAmount());
	}
	public void useFuel(int tSIC, int liters){
		for (Fuel fuelType : fuelTypes){
			if(fuelType.gettSIC() == tSIC){
				fuelType.setLiters(fuelType.getLiters() - liters);
				if(fuelType.getLiters()<1000)this.OrderFuel(tSIC, 5000);
			}
		}
	}
	public List<Part> getAllParts(){
		return partTypes;
	}
	public List<Fuel> getAllFuel(){
		return fuelTypes;
	}
	public HashMap<Object, Integer> getOrderedItems(){
		return toOrder;
	}
	public void sendOrder(int orderId){
		Order order = new Order(orderId, toOrder);
		toOrder.clear();
	}
	public void fill(Object item, int amount){
		if(item instanceof Part){
			((Part) item).setAmount(((Part) item).getAmount()+amount);
		}
		if(item instanceof Fuel){
			((Fuel) item).setLiters(((Fuel) item).getLiters()+amount);
		}
	}
}
