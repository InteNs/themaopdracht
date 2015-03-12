import java.time.LocalDate;


public class Customer {
	private String name, place;
	private LocalDate dateOfBirth,lastVisit, lastMaintenance;
	private int customerId;
	public Customer(String name, String place, LocalDate bornDate, int customerId) {
		this.name = name;
		this.place = place;
		this.dateOfBirth = bornDate;
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public String getPlace(){
		return place;
	}
	public int getCustomerId() {
		return customerId;
	}
	public LocalDate getLastVisit() {
		return lastVisit;
	}
	public LocalDate getLastMaintenance(){
		return lastMaintenance;
	}
	public void setLastVisit(LocalDate lastVisit) {
		this.lastVisit = lastVisit;
	}
	public void setLastMaintenance(LocalDate lastMaintenance){
		this.lastMaintenance = lastMaintenance;
		this.lastVisit = lastMaintenance;
		
	}
	
}
