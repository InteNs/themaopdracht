import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;


public class MaintenanceSession {
	private LocalDate plannedDate;
	private Invoice receipt;
	private Stock stock;
	private Mechanic mechanic;
	private HashMap<Part, Integer> usedParts;
	public MaintenanceSession(Invoice receipt, Stock stock,LocalDate plannedDate) {

		this.plannedDate = plannedDate;
		this.stock = stock;
		this.receipt = receipt;
		usedParts = new HashMap<Part, Integer>();
	}
	public void usePart(Part part){
		if(usedParts.containsKey(part)){
			usedParts.put(part, usedParts.get(part)+1);
		}
	}
	public LocalDate getPlannedDate(){
		return plannedDate;
	}
	public void setMechanic(Mechanic mechanic){
		this.mechanic = mechanic;
	}
	public Mechanic getMechanic(){
		return this.mechanic;
	}
	public void endSession(int hours){
		if(receipt.getCustomer() != null)receipt.getCustomer().setLastMaintenance(LocalDate.now());
		receipt.addItem(hours * mechanic.getHourlyFee(),"hourlyfee", ""+hours);	
		mechanic.setWorkedHours(mechanic.getWorkedHours()+hours);
		Iterator<Part> keySetIterator = usedParts.keySet().iterator();
		while(keySetIterator.hasNext()){
			Part key = keySetIterator.next();
			double price = key.getSellPrice() * usedParts.get(key);
			stock.usePart(key, usedParts.get(key));
			receipt.addItem(price, key.getName(),""+usedParts.get(key));
		}
	}
}
