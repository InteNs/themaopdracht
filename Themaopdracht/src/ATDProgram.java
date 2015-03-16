
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;


public class ATDProgram extends Application {
	private ArrayList<Customer> customers;
	private ArrayList<Mechanic> mechanics;
	private ArrayList<Reservation> reservations;
	private ArrayList<ParkingSpace> parkingSpaces;
	private Stock stock;
	public enum visit{SERVICE, MAINTENANCE};
	public static void main(String[] args) {launch();}
	@Override
	public void start(Stage arg0) throws Exception {
		customers = new ArrayList<Customer>();
		mechanics = new ArrayList<Mechanic>();
		reservations = new ArrayList<Reservation>();
		parkingSpaces = new ArrayList<ParkingSpace>();	
		stock = new Stock();
		// TODO Auto-generated method stub
	}
	public List<Customer> findCustomers(String input){
		ArrayList<Customer> list = new ArrayList<Customer>();
		for (Customer customer : customers) {
			if(customer.getEmail().equals(input) && !list.contains(customer)) list.add(customer);
			if(customer.getPostal().equals(input) && !list.contains(customer)) list.add(customer);
			if(customer.getSirName().equals(input) && !list.contains(customer)) list.add(customer);
		}
		return list;
	}
	public List<Customer> getRemindList(visit remindoption) {
		ArrayList<Customer> remindables = new ArrayList<Customer>();
		for (Customer customer : customers) {
			switch (remindoption) {
			case SERVICE:
				if (customer.getLastVisit().isBefore(
						LocalDate.now().minusMonths(2))) {
					remindables.add(customer);
				}
				break;
			case MAINTENANCE:
				if (customer.getLastMaintenance().isBefore(
						LocalDate.now().minusMonths(6))) {
					remindables.add(customer);
				}
				break;
			default: return null;
			}

		}
		return remindables;
	}

}

