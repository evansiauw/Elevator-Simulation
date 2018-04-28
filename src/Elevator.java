import java.util.LinkedList;

public class Elevator extends Controller{

	int elevatorNumber;
	int direction = 0; // 1 for Up, 0 for idle, -1 for down
	int currentFloor;
	// int[] buttonsPressed; // 0 if not pressed, 1 if pressed
	LinkedList<Person> ElevatorList;
	
	public Elevator(int elevatorNumber) {
		this.elevatorNumber = elevatorNumber;
		this.direction = 0;
		this.currentFloor = 1;
		ElevatorList = new LinkedList<>();
		// buttonsPressed = new int[10];
	}
	
	public boolean isElevatorEmpty(){
	    return ElevatorList.isEmpty();
	}
	
	public void addPersonToElevator(Person p){
	    ElevatorList.add(p);
	}
	
	public Person removePersonFromElevator(){
	    return ElevatorList.remove();
	}

    public void addPeopleToElevator(LinkedList<Person> upList) {
        ElevatorList.addAll(upList);
    }
    
    public LinkedList<Person> getElevatorList(){
    		return ElevatorList;
    }
    
    public void directionUp() {
    		direction = 1;
    }
    
    public void directionDown() {
    		direction = -1;
    }
    
    public void idling() {
    		direction = 0;
    }
    
    public int getDirection() {
    		return direction;
    }
    
    public int getCurrentFloor() {
    		return currentFloor;
    }
    
    public void  decreaseCurrentFloor() {
    		currentFloor--;
    }
    
    public void increaseCurrentFloor() {
    		currentFloor++;
    }
   
	
}
