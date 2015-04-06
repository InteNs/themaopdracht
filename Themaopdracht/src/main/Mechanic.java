package main;

public class Mechanic {
	private String name;
	private double hourlyFee;
	private int workedHours;
	public Mechanic(int employeeId, String name, double hourlyFee) {
		this.hourlyFee = hourlyFee;
		this.name = name;
	}
	public int getWorkedHours() {
		return workedHours;
	}
	public void setWorkedHours(int workedHours) {
		this.workedHours = workedHours;
	}
	public double getHourlyFee(){
		return hourlyFee;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setHourlyFee(double hourlyFee) {
		this.hourlyFee = hourlyFee;
	}
	public String toString(){
		return name + " a " + hourlyFee;
	}
	
}
