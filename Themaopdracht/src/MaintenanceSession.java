import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;


public class MaintenanceSession {
	private LocalDate completionDate;
	private double hourlyFee;
	private int hours;
	private Invoice receipt;
	private Stock stock;
	private HashMap<Part, Integer> usedParts;
	public MaintenanceSession(double hourlyFee, Invoice receipt, Stock stock) {
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
	public void setCompletionDate(LocalDate date){
		this.completionDate = date;
	}
	public void endSession(){
		receipt.setTotalPrice(receipt.getTotalPrice() + hours * hourlyFee);
		Iterator<Part> keySetIterator = usedParts.keySet().iterator();
		while(keySetIterator.hasNext()){
			Part key = keySetIterator.next();
			stock.usePart(key.getPartId(), usedParts.get(key), receipt);
		}
	}
}
