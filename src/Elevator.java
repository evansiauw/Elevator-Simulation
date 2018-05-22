import java.util.LinkedList;
import java.util.PriorityQueue;

/*
 * Joshua Goldstein, Iwan Siauw, Orlando Calle
 * 
 * This class defines the elevator object with elevator number, direction, current floor, distance (used to compare distances between elevators),
 * availability, and a list of people in the elevator.
 */
public class Elevator extends Controller implements Comparable<Elevator> {

	int elevatorNumber;
	int direction = 0; // 1 for Up, 0 for idle, -1 for down
	int currentFloor;
	Integer distance;
	boolean isAvailable = true;
	public LinkedList<Person> ElevatorList;
	
	public Elevator(int elevatorNumber, int currentFloor) {
		this.elevatorNumber = elevatorNumber;
		this.currentFloor = currentFloor;
		ElevatorList = new LinkedList<>();
		distance = 0;
	}
	
	/*
	 * checks if the elevator is empty
	 */
	public boolean isElevatorAvailable(){ return isAvailable; }

	public void setElevatorDistance(int distance) { this.distance = distance; }

	public boolean isElevatorEmpty(){
		return ElevatorList.isEmpty();
	}

	public void setElevatorAvailability(boolean bool){
		isAvailable = bool;
	}
	/*
	 * adds a person to the elevator
	 */
	public void addPersonToElevator(Person p){
	    ElevatorList.add(p);
	}
	
	/*
	 * removes a person from the elevator
	 */
	public Person removePersonFromElevator(){

	    return ElevatorList.remove();

	}

    public LinkedList<Person> getElevatorList(){
    	return ElevatorList;
    }

    /*
     * sets direction of the elevator to up
     */
    public void directionUp() {
    		direction = 1;
    }
    
    /*
     * sets direction of the elevator to down
     */
    public void directionDown() {
    		direction = -1;
    }
    
    /*
     * sets direction of the elevator to idle
     */
    public void idling() {
    		direction = 0;
    }
    
    public int getDirection() {
    		return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }


    public int getCurrentFloor() {
    		return currentFloor;
    }
    
    /*
     * simulates elevator moving down
     */
    public void  decreaseCurrentFloor() {
    		currentFloor--;
    }
    
    /*
     * simulates elevator moving up
     */
    public void increaseCurrentFloor() {
    		currentFloor++;
    }
    
    /*
     * prints the current direction in words for output
     */
    public String printDirectionInWord() {
    		if(getDirection()==1) {
    			return "UP";
    		}else if (getDirection()==-1) {
    			return "DOWN";
    		} else {
    			return "IDLE";
    		}
    }

    @Override
    public int compareTo(Elevator other) {

        return this.distance.compareTo(other.distance);

    }
}
