package main;

public class Car {

	private String type;
	private String kenteken;
	private Customer customer;

	public Car(String type, String kenteken, Customer customer) {
		this.type = type;
		this.kenteken = kenteken;
		this.customer = customer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKenteken() {
		return kenteken;
	}

	public void setKenteken(String kenteken) {
		this.kenteken = kenteken;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

	@Override
	public String toString() {
		return "";
	}

	public static void remove(Car cars) {
		// TODO Auto-generated method stub
		
	}

	public static void add(Car cars) {
		// TODO Auto-generated method stub
		
	}

	
}
