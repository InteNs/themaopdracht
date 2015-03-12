
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;


public class ATDProgram extends Application {
	private ArrayList<Customer> customers;
	private ArrayList<Mechanic> mechanics;
	private ArrayList<Customer> blackList;
	private ArrayList<Reservation> reservations;
	private ArrayList<ParkingSpace> parkingSpaces;
	private Stock stock;
	public static void main(String[] args) {launch();}
	
	private void addContent(){
		for (int i = 0; i < 10; i++) {
			parkingSpaces.add(new ParkingSpace(i));
		}
		mechanics.add(new Mechanic(0, "harry jekkers", 15.0));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		customers.add(new Customer("Paula Koe", "Breda",LocalDate.parse("1959-sep-24",formatter), customers.size()+1));
	}
	public Customer findCustomer(String name){
		for (Customer customer : customers) {
			if(customer.getName() == name) return customer;
		}
		return null;
	}
	
	public boolean isInBlackList(Customer customer){
		if(blackList.contains(customer)) return true;
		else return false;
	}
	@Override
	public void start(Stage arg0) throws Exception {
		customers = new ArrayList<Customer>();
		mechanics = new ArrayList<Mechanic>();
		blackList = new ArrayList<Customer>();
		reservations = new ArrayList<Reservation>();
		parkingSpaces = new ArrayList<ParkingSpace>();	
		stock = new Stock();
		addContent();
		// TODO Auto-generated method stub
		
	}
	public List<Customer> getRemindList(){
		ArrayList<Customer> remindables = new ArrayList<Customer>();
		for (Customer customer : customers) {
			if(customer.getLastVisit().isBefore(LocalDate.now().minusMonths(2))){
				remindables.add(customer);
			}
		}
		return remindables;
	}
	public List<Customer> getRemindMaintenanceList(){
		ArrayList<Customer> remindables = new ArrayList<Customer>();
		for (Customer customer : customers) {
			if(customer.getLastMaintenance().isBefore(LocalDate.now().minusMonths(6))){
				remindables.add(customer);
			}
		}
		return remindables;	
	}

}
