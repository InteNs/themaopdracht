import java.time.LocalDate;


public class Customer {
	private String name, sirName, place, email, adress, postal,bankAccount, tel;
	private LocalDate dateOfBirth,lastVisit, lastMaintenance;
	private boolean isOnBlackList;
	public Customer(String name,String sirName, String place,String bankAccount, LocalDate bornDate, String email, String postal, String tel, String adress) {
		this.name = name;
		this.sirName = sirName;
		this.place = place;
		this.bankAccount = bankAccount;
		this.email = email;
		this.adress = adress;
		this.postal = postal;
		this.tel = tel;
		this.dateOfBirth = bornDate;
	}
	public String getName() {
		return name;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSirName(String sirName) {
		this.sirName = sirName;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public void setPostal(String postal) {
		this.postal = postal;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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
