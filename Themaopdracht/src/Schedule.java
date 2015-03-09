import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Schedule {
	private ArrayList<MaintenanceSession> dueTasks;
	private ArrayList<MaintenanceSession> plannedTasks;
	public Schedule(){
		dueTasks = new ArrayList<MaintenanceSession>();
		plannedTasks = new ArrayList<MaintenanceSession>();
	}
	public void newTask(MaintenanceSession task){
		dueTasks.add(task);
	}
	public List<MaintenanceSession> getWeekSchedule(){
		LocalDate date = LocalDate.now();
		TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
		int weekNumber = date.get(woy);
		ArrayList<MaintenanceSession> list = new ArrayList<MaintenanceSession>();
		for (MaintenanceSession task : dueTasks) {
			if(task.getPlannedDate().get(woy)==weekNumber){
				list.add(task);
			}
		}
		return list;
	}
	public List<MaintenanceSession> getPlannedTasks(Mechanic mechanic){
		ArrayList<MaintenanceSession> list = new ArrayList<MaintenanceSession>();
		for (MaintenanceSession task : plannedTasks) {
			if(task.getMechanic() == mechanic)list.add(task);
		}
		return list;
	}
	
	public void planTask(MaintenanceSession task, Mechanic mechanic){
		if(dueTasks.contains(task)){
			task.setMechanic(mechanic);
			plannedTasks.add(task);
			dueTasks.remove(task);
		}
	}
	public void finishTask(MaintenanceSession task, int hours){
		if(plannedTasks.contains(task)){
			task.endSession(hours);
			plannedTasks.remove(task);
		}
		
	}
}
