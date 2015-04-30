package main;
import java.time.LocalDate;
import java.util.ArrayList;




//moet bij klant
	//public void addorRemoveVehicle (Vehicle vehicles, boolean remove){
	//if (remove)vehicle.remove(vehicle);
	//else vehicle.add(vehicle);
	//}

//klant
		//addorRemoveVehicle(new Vehicle("volkswagen", "13-LOL-3", Customer.get(1)), false);

public class Customer {
	private String name, place, email, address, postal,bankAccount, tel;
	private LocalDate dateOfBirth,lastVisit, lastMaintenance;
	private boolean isOnBlackList, notified;
	private ArrayList<Car> cars = new ArrayList<Car>();
	
	public Customer(String name, String place,String bankAccount, LocalDate bornDate, String email, String postal, String tel, String adress, boolean isOnBlackList) {
		this.name = name;
		this.isOnBlackList = isOnBlackList;
		this.place = place;
		this.bankAccount = bankAccount;
		this.email = email;
		this.address = adress;
		this.postal = postal;
		this.tel = tel;
		this.dateOfBirth = bornDate;
	}
	
	public boolean isNotified() {
		return notified;
	}

	public void setNotified(boolean notified) {
		this.notified = notified;
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
	public void setPlace(String place) {
		this.place = place;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAddress(String adress) {
		this.address = adress;
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
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public String getPlace(){
		return place;
	}
	public String getAddress() {
		return address;
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
	public ArrayList<Car> getCars() {
		return cars;
	}
	public void addorRemoveCar (Car cars, boolean remove){
		if (remove)Car.remove(cars);
		else Car.add(cars);
		}
	{
	addorRemoveCar(new Car("volkswagen", "13-LOL-3", Customer.get(1)), false);
	addorRemoveCar(new Car("porsche", "14-LAL-5", Customer.get(2)), false);
	addorRemoveCar(new Car("ferrari", "15-LIL-6", Customer.get(3)), false);
	addorRemoveCar(new Car("mercedes", "16-LEL-9", Customer.get(4)), false);
	addorRemoveCar(new Car("bmw", "17-LQL-1", Customer.get(5)), false);
	addorRemoveCar(new Car("nissan", "18-POL-2", Customer.get(6)), false);
	addorRemoveCar(new Car("opel", "18-LVL-8", Customer.get(7)), false);
	addorRemoveCar(new Car("renault", "15-WOL-1", Customer.get(8)), false);
	addorRemoveCar(new Car("audi", "24-QOL-9", Customer.get(9)), false);
	addorRemoveCar(new Car("lamborgini", "53-TOL-3", Customer.get(10)), false);
	
	}
	@Override
	public String toString() {
		return name;
	}

	private static Customer get(int i) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
