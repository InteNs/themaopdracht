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
	public void OrderPart(int partId, int amount){
		for (Part partType : partTypes) {
			if(partType.getPartId() == partId) toOrder.put(partId, amount);
		}
	}
	public void OrderFuel(int tSIC, int liters){
		for (Fuel fuelType : fuelTypes){
			if(fuelType.gettSIC() == tSIC) toOrder.put(tSIC, liters);
		}
	}
	public void usePart(int partId, int amount){
		for (Part partType : partTypes) {
			if(partType.getPartId()==partId){
				partType.setAmount(partType.getAmount() - amount);
			}
		}
	}
	public void useFuel(int tSIC, int liters){
		for (Fuel fuelType : fuelTypes){
			if(fuelType.gettSIC() == tSIC){
				fuelType.setLiters(fuelType.getLiters() - liters);
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
}
