package main;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;


public class MaintenanceSession {
	private LocalDate plannedDate;
	private boolean isFinished;
	private Stock stock;
	private Mechanic mechanic;
	private int totalParts = 0;;
	
	private HashMap<Product, Integer> usedParts;
	public MaintenanceSession(Stock stock,LocalDate plannedDate) {

		this.plannedDate = plannedDate;
		this.stock = stock;
		usedParts = new HashMap<Product, Integer>();
	}
	public void usePart(Product product){
		if(usedParts.containsKey(product)){
			usedParts.put(product, usedParts.get(product)+1);
			totalParts++;
		}
		else {
			usedParts.put(product, 1);
			totalParts++;
		}
	}
	public LocalDate getPlannedDate(){
		return plannedDate;
	}
	public boolean isFinished(){
		return isFinished;
	}
	public void setPlannedDate(LocalDate date){
		plannedDate = date;
	}
	public void setMechanic(Mechanic mechanic){
		this.mechanic = mechanic;
	}
	public Mechanic getMechanic(){
		return this.mechanic;
	}
	public void endSession(int hours){	
		mechanic.setWorkedHours(mechanic.getWorkedHours()+hours);
//		Iterator<Product> keySetIterator = usedParts.keySet().iterator();
//		while(keySetIterator.hasNext()){
//			Product key = keySetIterator.next();
//			double price = key.getSellPrice() * usedParts.get(key);
//			stock.useProduct(key, usedParts.get(key));
//			receipt.addItem(price, key.getName(),""+usedParts.get(key));
//		}
	}
	public HashMap<Product, Integer> getUsedParts(){
		return usedParts;
		
	}
	public int getTotalParts() {
		return totalParts;
	}
}
