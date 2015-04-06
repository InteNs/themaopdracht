package main;
import java.time.LocalDate;


public class Reservation {
	private LocalDate fromDate, toDate;
	private double totalPrice;
	private ParkingSpace parkingSpace;
	private boolean isActive;
	private String numberPlate;
	public Reservation(LocalDate fromDate, LocalDate toDate,String numberPlate, ParkingSpace parkingSpace) {
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.parkingSpace = parkingSpace;
		parkingSpace.setAvailable(false);
		this.numberPlate = numberPlate;
		checkDate();
	}
	
	public String getNumberPlate() {
		return numberPlate;
	}

	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}

	public String isActive() {
		if(LocalDate.now().isAfter(toDate)){
			isActive = false;
			parkingSpace.setAvailable(!isActive);
			return "done";
		}
		if(LocalDate.now().isBefore(fromDate)){
			isActive = false;
			parkingSpace.setAvailable(!isActive);
			return "before";
		}
		isActive = true;
		parkingSpace.setAvailable(!isActive);
		return "active";
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public void checkDate(){
		isActive = true;
		if(LocalDate.now().isAfter(toDate))isActive = false;
		if(LocalDate.now().isBefore(fromDate))isActive = false;
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
}
