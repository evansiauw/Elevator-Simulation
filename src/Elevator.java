import java.util.LinkedList;

public class Elevator extends Controller{

	int elevatorNumber;
	int direction; // -1 for down, 0 for idle, 1 for up
	int currentFloor;
	int[] buttonsPressed; // 0 if not pressed, 1 if pressed
	LinkedList<Person> peopleInElevator;
	
	public Elevator(int elevatorNumber) {
		this.elevatorNumber = elevatorNumber;
		this.direction = 0;
		this.currentFloor = 0;
		peopleInElevator = new LinkedList<>();
		buttonsPressed = new int[10];
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

    public void addPeopleToElevator(LinkedList<Person> upList) {
        peopleInElevator.addAll(upList);
    }
	

	
}
