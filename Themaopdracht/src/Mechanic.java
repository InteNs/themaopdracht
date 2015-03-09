
public class Mechanic {
	private int employeeId;
	private String name;
	private int workedHours;
	public Mechanic(int employeeId, String name) {
		super();
		this.employeeId = employeeId;
		this.name = name;
	}
	public int getWorkedHours() {
		return workedHours;
	}
	public void setWorkedHours(int workedHours) {
		this.workedHours = workedHours;
	}
	
}
