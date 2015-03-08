import java.time.LocalDate;
import java.util.HashMap;


public class MaintenanceSession {
	private LocalDate date;
	private int hours;
	private HashMap<Part, Integer> usedParts;
	public MaintenanceSession(LocalDate date, int hours) {
		this.date = date;
		this.hours = hours;
	}
	
}
