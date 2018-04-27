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
	static Elevator [] elevator = new Elevator[++numOfElevator];
	static Floor [] floor = new Floor[++numOfFloor];
	static Person [] person  = new Person[++numOfPerson];
	
	public static void main(String[] args) {
	
		for(int i=1; i <= 1; i++) {
			elevator[i] = new Elevator(i); }
		
		for(int i=1; i <= 10; i++) {
			floor[i]= new Floor(i); }
		
		while(time < maxRunningTime) {
			
			creatingPerson();
			boardingElevator();
			
		}
		
	}
	
	public static void creatingPerson() {
		
		j++;
		personCounter++;
		double custArrival = meanArrival * (- Math.log(1 - rand.nextDouble()));
		
		do {
		personAt = rand.nextInt(11) + 1;
		floorDestination = rand.nextInt() + 1;
		}while (personAt == floorDestination);
		
		person[j] = new Person(j,custArrival, personAt, floorDestination);
		if(person[j].personAtFloor < person[j].floorDestination) {
			floor[personAt].addPersonToUpList(person[j]); }
		else {
			floor[personAt].addPersonToDownList(person[j]);
		}
	}
	
	public static void boardingElevator() {
		
		int currentFloor = elevator[1].getCurrentFloor();
		
		if(elevator[1].getDirection() == 1) {	
		elevator[1].addPeopleToElevator(floor[currentFloor].getUpList()); }
		else {
			elevator[1].addPeopleToElevator(floor[currentFloor].getDownList());
		}
		
	}
		
	
	
}

//do calculations on how much time it will take to get to a floor from curr floor
			// ex: person is on floor 2 and wants to go to 4 - takes 4 seconds
			// instead of having uplist and downlist, determine which elevator the person will take
			// that is, the elevator that can get to the person the fastest

