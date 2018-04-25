import java.util.LinkedList;

public class Elevator extends Controller{

	int elevatorNumber;
	int direction; // -1 for down, 0 for idle, 1 for up
	int currentPosition;
	LinkedList<Person> peopleInElevator = new LinkedList<>();
	
	public Elevator(int elevatorNumber) {
		this.elevatorNumber = elevatorNumber;
		direction = 0;
		currentPosition = 0;	
	}
	
	public boolean isElevatorEmpty(){
	    return peopleInElevator.isEmpty();
	}
	
	public void addPersonToElevator(Person p){
	    peopleInElevator.add(p);
	}
	
	public Person removePersonFromElevator(){
	    return peopleInElevator.remove();
	}
	

	
}
