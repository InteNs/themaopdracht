import java.time.LocalDate;


public class Klant {
	private String name, place;
	private LocalDate bornDate;
	private int customerId;
	public Klant(String name, String place, LocalDate bornDate, int customerId) {
		this.name = name;
		this.place = place;
		this.bornDate = bornDate;
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public LocalDate getBornDate() {
		return bornDate;
	}
	public void setBornDate(LocalDate bornDate) {
		this.bornDate = bornDate;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	
}
