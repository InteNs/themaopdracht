
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
	public double getHourlyFee(){
		return hourlyFee;
	}
	public void setWorkedHours(int workedHours) {
		this.workedHours = workedHours;
	}
	
}
