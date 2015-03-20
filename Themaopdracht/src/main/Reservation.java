package main;
import java.time.LocalDate;


public class Reservation {
	private LocalDate fromDate, toDate;
	private double totalPrice;
	private ParkingSpace parkingSpace;
	private Customer customer;
	public Reservation(LocalDate fromDate, LocalDate toDate, double totalPrice, ParkingSpace parkingSpace, Customer customer) {
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.totalPrice = totalPrice;
		this.parkingSpace = parkingSpace;
		this.customer = customer;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public ParkingSpace getParkingSpace() {
		return parkingSpace;
	}
	public Customer getCustomer() {
		return customer;
	}
	
}
