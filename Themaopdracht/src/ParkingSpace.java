
public class ParkingSpace {
	private int parkingId;
	private boolean isAvailable;
	public ParkingSpace(int ID){
		this.parkingId = ID;
	}
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public int getParkingId() {
		return parkingId;
	}
	
}
