import java.text.DecimalFormat;
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
	private static DecimalFormat df = new DecimalFormat(".#");

	
	public static void main(String[] args) {
	
		for(int i=1; i <= numOfElevator; i++) {
			elevator[i] = new Elevator(i); }
		
		for(int i=1; i <= numOfFloor; i++) {
			floor[i]= new Floor(i); }
		
		elevator[1].directionUp();
		
		while(time < maxRunningTime) {
			System.out.println("current time is: " + df.format(time) + "  Current Floor: " + elevator[1].getCurrentFloor());
			creatingPerson();
			simulatingElevator();
			System.out.println();
		}	
		System.out.println("number of object person created: " + personCounter);
	}
	
	// creating one person object at a time
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
	
	// consist of 3 method which will be executed in one while loop cycle
	public static void simulatingElevator() {
		
		int currentFloor = elevator[1].getCurrentFloor();

		exit();
		boarding(currentFloor);
		nextMove(currentFloor);	
	}
	
	// if elevator not empty, increase time, remove the person who's destination == elevator's current position
	public static void exit() {
		
		if(!elevator[1].getElevatorList().isEmpty()) {
			time = time + 0.1;
			Iterator<Person> it = elevator[1].getElevatorList().iterator();
			while(it.hasNext()) {
				Person element= it.next();
				if(element.floorDestination == elevator[1].getCurrentFloor()) {
					System.out.println("Person " + element.personNumber + " arrived at destination and exiting");
					it.remove();
					}
			}
			
		}
	  }
	
	// if elevator's direction is up, load people who's going up(if up list, not empty).. or the other way around
	public static void boarding (int currentFloor) {

		if(elevator[1].getDirection() == 1) {
			if(!elevator[1].floor[currentFloor].upList.isEmpty()) {
				elevator[1].addPeopleToElevator(floor[currentFloor].getUpList());
				time = time + 0.1;
				System.out.println("Boarding people who's going 'up' to elevator at floor " + elevator[1].getCurrentFloor());}
			else {
				System.out.println("Nobody is in the upList, No Boarding");
			}
		}
		else {
			if(!elevator[1].floor[currentFloor].downList.isEmpty()) {
				elevator[1].addPeopleToElevator(floor[currentFloor].getDownList()); 
				time = time + 0.1;
				System.out.println("Boarding people who's going 'down' to elevator at floor " + elevator[1].getCurrentFloor());}
			else {
				System.out.println("Nobody is in the downList, No Boarding");
			}
		}
	}
	
	/* THERE MIGHT BE SOME BUGS HERE
	
	 if elevator's direction is up: 
			if elevator empty or the opposite direction's list(down list) is empty too.. idling
			else changing direction.. boarding the current direction's list (down).. move one floor down
	
	 else the other way around... */
	public static void nextMove(int currentFloor) {
	
		if(elevator[1].getDirection() == 1) {
			headingUp();
		}
		else {
			headingDown();
		}
		
		Iterator <Person> iter = elevator[1].getElevatorList().iterator();
		while(iter.hasNext()) {
			Person element = iter.next();
			System.out.println("***person id: " + element.personNumber + "  With destination " + element.floorDestination +"***");	
		}
	}
	
	// if this is the lowest level.. change direction(down to up).. otw..go one floor down
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
	
	// if this is the Top level.. change direction(up to down).. go one floor down
	public static void headingUp() {
		if(elevator[1].getCurrentFloor() == 10) {
			elevator[1].directionDown();		
			System.out.println("this is the 'Top' level, changing direction to 'Down'");
			}
		else {
				elevator[1].increaseCurrentFloor();
				System.out.println("Going one floor 'up', next floor is: " + elevator[1].getCurrentFloor());
				time = time + 0.2;
			}
	}
	
	
	
	
}