import java.util.HashMap;
import java.util.Iterator;


public class MaintenanceSession {
	private double hourlyFee;
	private int hours;
	private Invoice receipt;
	private Stock stock;
	private Mechanic mechanic;
	private HashMap<Part, Integer> usedParts;
	public MaintenanceSession(double hourlyFee, Invoice receipt, Stock stock, Mechanic mechanic) {
		this.mechanic = mechanic;
		this.stock = stock;
		this.hourlyFee = hourlyFee;
		this.receipt = receipt;
		usedParts = new HashMap<Part, Integer>();
	}
	public void usePart(Part part){
		if(usedParts.containsKey(part)){
			usedParts.put(part, usedParts.get(part)+1);
		}
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public void endSession(){
		receipt.setTotalPrice(receipt.getTotalPrice() + hours * hourlyFee);
		mechanic.setWorkedHours(mechanic.getWorkedHours()+hours);
		Iterator<Part> keySetIterator = usedParts.keySet().iterator();
		while(keySetIterator.hasNext()){
			Part key = keySetIterator.next();
			stock.usePart(key.getPartId(), usedParts.get(key), receipt);
		}
	}
}
