
public class Elevator {

	int elevatorNumber;
	int direction; // -1 for down, 0 for idle, 1 for up
	int currentPosition;
	
	public Elevator(int elevatorNumber) {
		this.elevatorNumber = elevatorNumber;
		direction = 0;
		currentPosition = 0;	
	}
}
