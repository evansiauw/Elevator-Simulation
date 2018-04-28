import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Controller {

	static int maxRunningTime = 60; // a minute
	static double time = 0; // in second
	static int personObjectCounter = 1;
	static int meanArrival = 5;
	static int personAt;
	static int floorDestination;
	static int personCounter = 0;
	static int totalWaitTime = 0;
	static int numOfElevator = 1;
	static int numOfFloor = 10;
	static int numOfPerson = 1000;
	static Random rand = new Random();
	static Elevator [] elevator = new Elevator[numOfElevator+1];
	static Floor [] floor = new Floor[numOfFloor+1];
	static Person [] person  = new Person[numOfPerson+1];
	
	public static void main(String[] args) {
	
		for(int i=1; i <= numOfElevator; i++) {
			elevator[i] = new Elevator(i); }
		
		for(int i=1; i <= numOfFloor; i++) {
			floor[i]= new Floor(i); }
		
		elevator[1].directionUp();
		
		while(time < maxRunningTime) {
			System.out.println("current time is: " + time);
			creatingPerson();
			simulatingElevator();
			time++;
			System.out.println();
		}	
		System.out.println("number of object person created: " + personCounter);
	}
	
	public static void creatingPerson() {
				
		double custArrival = meanArrival * (- Math.log(1 - rand.nextDouble()));
		
		do {
		personAt = rand.nextInt(10) + 1;
		floorDestination = rand.nextInt(10) + 1;
		}while (personAt == floorDestination);
		
		person[personObjectCounter] = new Person(personObjectCounter,custArrival, personAt, floorDestination);
		if(person[personObjectCounter].personAtFloor < person[personObjectCounter].floorDestination) {
			floor[personAt].addPersonToUpList(person[personObjectCounter]); }
		else {
			floor[personAt].addPersonToDownList(person[personObjectCounter]);
		}
		
		System.out.println("created a person with id: " + person[personObjectCounter].personNumber + " at floor: " + person[personObjectCounter].personAtFloor
				+ " with destination: " + person[personObjectCounter].floorDestination);
		personObjectCounter++;
		personCounter++;

	}
	
	public static void simulatingElevator() {
		
		int currentFloor = elevator[1].getCurrentFloor();

		exit();
		boarding(currentFloor);
		nextMove(currentFloor);	
	}
	
	public static void exit() {
		
		if(elevator[1].getElevatorList() != null) {
			time = time + 0.1;
			Iterator<Person> it = elevator[1].getElevatorList().iterator();
			while(it.hasNext()) {
				if(it.next().floorDestination == elevator[1].getCurrentFloor()) {
					elevator[1].getElevatorList().remove(it.next());	
			}
		}
			System.out.println("People exiting the elevator at floor " + elevator[1].getCurrentFloor());
	  }
	}
	
	public static void boarding (int currentFloor) {

		if(elevator[1].getDirection() == 1) {		
			elevator[1].addPeopleToElevator(floor[currentFloor].getUpList()); 
			System.out.println("Boarding people who's going 'up' to elevator at floor " + currentFloor);

		}
			else {
				elevator[1].addPeopleToElevator(floor[currentFloor].getDownList());
				System.out.println("Boarding people who's going 'down' to elevator at floor " + currentFloor);
			}
		time = time + 0.1;
	}
	
	public static void nextMove(int currentFloor) {
	
	if(elevator[1].getDirection() == 1) {
		if (elevator[1].getElevatorList() == null && floor[currentFloor].getDownList() == null) {
			elevator[1].idling();
			System.out.println("Elevator empty and downlist request is empty... Idling");
		} else {
			elevator[1].directionDown();
			System.out.println("Changing direction from Up to Down");
			boarding(currentFloor);
			headingDown();
		}
	} else {
		
		if (elevator[1].getElevatorList() == null && floor[currentFloor].getUpList() == null) {
			elevator[1].idling();
			System.out.println("Elevator empty and 'uplist' request is empty... Idling");

		} else {
			elevator[1].directionUp();
			System.out.println("Changing direction from Up to Down");
			boarding(currentFloor);
			headingUp();
		}
	}
		
	}
	
	public static void headingDown() {
		if(elevator[1].getCurrentFloor() == 1) {
			elevator[1].directionUp(); 
			System.out.println("this is the 'lowest' level, changing direction to 'Up'");
			}
		else {
				elevator[1].decreaseCurrentFloor();
				System.out.println("Going one floor 'down', next floor is: " + elevator[1].getCurrentFloor());
				time = time + 0.2;
			}
	}
	
	public static void headingUp() {
		if(elevator[1].getCurrentFloor() == 1) {
			elevator[1].directionUp(); 
			System.out.println("this is the 'Top' level, changing direction to 'Down'");
			}
		else {
				elevator[1].increaseCurrentFloor();
				System.out.println("Going one floor 'up', next floor is: " + elevator[1].getCurrentFloor());
				time = time + 0.2;
			}
	}
	
	
	
	
	
}