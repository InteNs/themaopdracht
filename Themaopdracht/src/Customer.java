import java.time.LocalDate;


public class Customer {
	private String name, sirName, place, email, adress, postal, tel;
	private LocalDate dateOfBirth,lastVisit, lastMaintenance;
	private boolean isOnBlackList;
	public Customer(String name,String sirName, String place, LocalDate bornDate, String email, String postal, String tel, String adress) {
		this.name = name;
		this.sirName = sirName;
		this.place = place;
		this.email = email;
		this.adress = adress;
		this.postal = postal;
		this.tel = tel;
		this.dateOfBirth = bornDate;
	}
	public String getName() {
		return name;
	}
	public String getSirName() {
		return sirName;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public String getPlace(){
		return place;
	}
	public String getAdress() {
		return adress;
	}
	public String getEmail() {
		return email;
	}
	public String getPostal() {
		return postal;
	}
	public String getTel() {
		return tel;
	}
	public LocalDate getLastVisit() {
		return lastVisit;
	}
	
	public boolean isOnBlackList() {
		return isOnBlackList;
	}
	public void setOnBlackList(boolean isOnBlackList) {
		this.isOnBlackList = isOnBlackList;
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
