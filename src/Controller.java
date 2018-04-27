import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Controller {

	static int maxRunningTime = 60; // a minute
	static double time = 0; // in second
	static int j = 0;
	static int meanArrival = 5;
	static int personAt;
	static int floorDestination;
	static int personCounter = 0;
	static int totalWaitTime = 0;
	static int numOfElevator = 5;
	static int numOfFloor = 10;
	static int numOfPerson = 100;
	static Random rand = new Random();
	static Elevator [] elevator = new Elevator[2];
	static Floor [] floor = new Floor[++numOfFloor];
	static Person [] person  = new Person[++numOfPerson];
	
	public static void main(String[] args) {
	
		for(int i=1; i <= 1; i++) {
			elevator[i] = new Elevator(i); }
		
		for(int i=1; i <= 10; i++) {
			floor[i]= new Floor(i); }
		
		while(time < maxRunningTime) {
			
			creatingPerson();
			simulatingElevator();
			time++;
			
		}	
		System.out.println(personCounter);
	}
	
	public static void creatingPerson() {
		
		
		j++;
		personCounter++;
		double custArrival = meanArrival * (- Math.log(1 - rand.nextDouble()));
		
		do {
		personAt = rand.nextInt(10) + 1;
		floorDestination = rand.nextInt(10) + 1;
		}while (personAt == floorDestination);
		
		person[j] = new Person(j,custArrival, personAt, floorDestination);
		if(person[j].personAtFloor < person[j].floorDestination) {
			floor[personAt].addPersonToUpList(person[j]); }
		else {
			floor[personAt].addPersonToDownList(person[j]);
		}
		
		System.out.println("created a person with id: " + person[j].personNumber + " at floor: " + person[j].personAtFloor
				+ " with destination: " + person[j].floorDestination);
	}
	
	public static void simulatingElevator() {
		
		int currentFloor = elevator[1].getCurrentFloor();
		
		elevator[1].goingUp();
		exit();
		boarding(currentFloor);
		nextMove(currentFloor);
		
	}
	
	public static void exit() {
		
	if(elevator[1].getElevatorList() != null) {
		
		Iterator<Person> it = elevator[1].getElevatorList().iterator();
		while(it.hasNext()) {
			if(it.next().floorDestination == elevator[1].getCurrentFloor()) {
				elevator[1].getElevatorList().remove(it.next());
				
			
			}
		}
	}
	}
	
	public static void boarding (int currentFloor) {
		System.out.println("boarding at"+ currentFloor);

		
		if(elevator[1].getDirection() == 1) {	
			elevator[1].addPeopleToElevator(floor[currentFloor].getUpList()); 
			System.out.println();

		}
			else {
				elevator[1].addPeopleToElevator(floor[currentFloor].getDownList());
			}
	}
	
	public static void nextMove(int currentFloor) {
		System.out.println("moving ");
		
	if(elevator[1].getDirection() == 1) {
		if (elevator[1].getElevatorList() == null && floor[currentFloor].getDownList() == null) {
			elevator[1].idling();
		} else {
			elevator[1].goingDown();
			boarding(currentFloor);
		}
	} else {
		
		if (elevator[1].getElevatorList() == null && floor[currentFloor].getUpList() == null) {
			elevator[1].idling();
		} else {
			elevator[1].goingUp();
			boarding(currentFloor);
		}
	}
		
	}
	
}