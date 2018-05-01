import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
	static int numOfPerson = 100;
	static double arrivalTimeSum = 0;
	static double averageWaitingTime = 0;
	static Random rand = new Random();
	static Elevator [] elevator = new Elevator[numOfElevator+1];
	static Floor [] floor = new Floor[numOfFloor+1];
	static Person [] person  = new Person[numOfPerson+1];
	private static DecimalFormat df = new DecimalFormat(".#");
	static ArrayList<Double> waitingTimesForEachPerson = new ArrayList<>();

	public static void main(String[] args) {

        // puts all System.outs into a txt file
        PrintStream out;
        try {
            out = new PrintStream(new FileOutputStream("output.txt"));
            System.setOut(out);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		for(int i=1; i <= numOfElevator; i++) {
			elevator[i] = new Elevator(i); }
		
		for(int i=1; i <= numOfFloor; i++) {
			floor[i]= new Floor(i); }
		
		elevator[1].directionUp();
		
		while(personObjectCounter < numOfPerson) {
			System.out.println("Current time is: " + df.format(time) + "  Current Floor: " + elevator[1].getCurrentFloor() + 
					" Direction: " + elevator[1].printDirectionInWord());
			creatingPerson();
			simulatingElevator();
			System.out.println();
		}	
		System.out.println("Total number of persons created: " + personCounter);
		computeAverageWaitingTime();
	}
	
	private static void computeAverageWaitingTime() {
	    for (Double i : waitingTimesForEachPerson){
            arrivalTimeSum += i;
        }
        averageWaitingTime = arrivalTimeSum / numOfPerson;
        System.out.println("Average Waiting Time: "+ df.format(averageWaitingTime)); 
    }

    // creating one person object at a time
	public static void creatingPerson() {
				
		double custArrival = meanArrival * (- Math.log(1 - rand.nextDouble()));
		
		do {
		personAt = generateStartingFloor(); // new method
		floorDestination = rand.nextInt(10) + 1;
		}while (personAt == floorDestination);
		
		Person newPerson= new Person(personObjectCounter,custArrival, personAt, floorDestination);
		newPerson.arrivalTime = time;
		if(newPerson.personAtFloor < newPerson.floorDestination) {
			floor[personAt].addPersonToUpList(newPerson); }
		else {
			floor[personAt].addPersonToDownList(newPerson); 
		}
		
		System.out.println("Created: PersonId: " + newPerson.personNumber + " AtFloor: " + newPerson.personAtFloor
				+ " Dest: " + newPerson.floorDestination);
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
		int counter=0;
		System.out.print("Exit: ");
		if(!elevator[1].getElevatorList().isEmpty()) {
			Iterator<Person> it = elevator[1].getElevatorList().iterator();
			while(it.hasNext()) {
				Person element= it.next();
				if(element.floorDestination == elevator[1].getCurrentFloor()) {
					System.out.print("Person " + element.personNumber + " ");
			        waitingTimesForEachPerson.add(element.getWaitingTime());
					it.remove();
					counter++;
					}
			}
			if(counter>0) {System.out.println();}
		}
		if(counter==0) { System.out.print("None\n"); }
	  }
	
	// if elevator's direction is up, load people who's going up(if up list, not empty).. or the other way around
	public static void boarding (int currentFloor) {
		int counter=0;
		if(elevator[1].getDirection() == 1) {
			System.out.print("Boarding: ");
			if(!elevator[1].floor[currentFloor].upList.isEmpty()) {
				Iterator<Person> it = elevator[1].floor[currentFloor].upList.iterator();
			while(it.hasNext()) {
				Person element= it.next();
				if(element.arrivalTime <= time) {
					System.out.print("Person " + element.personNumber + " ");
					elevator[1].addPersonToElevator(element);
					it.remove();
					counter++;
					}
			}
			if(counter>0) {System.out.println(); time = time + 0.1;}
			}
			if(counter==0) { System.out.print("None\n"); }
		}
		else {
				System.out.print("Boarding: ");
				if(!elevator[1].floor[currentFloor].upList.isEmpty()) {
					Iterator<Person> it = elevator[1].floor[currentFloor].downList.iterator();
				while(it.hasNext()) {
					Person element= it.next();
					if(element.arrivalTime <= time) {
						System.out.print("Person " + element.personNumber + " ");
						elevator[1].addPersonToElevator(element);
						it.remove();
						counter++;
						}
				}
				if(counter>0) {System.out.println(); time = time + 0.1;}
				}
				if(counter==0) { System.out.print("None\n"); }
			}
		}
	
	 /* if elevator's direction is up: 
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
		
		System.out.println("***List of people in the elevator***");
		Iterator <Person> iter = elevator[1].getElevatorList().iterator();
		while(iter.hasNext()) {
			Person element = iter.next();
			System.out.println("PersonId: " + element.personNumber + "  Dest: " + element.floorDestination +"  ArrivalTime: "+ df.format(element.getArrivalTime()));	
		}
	}
	
	// if this is the lowest level.. change direction(down to up).. otw..go one floor down
	public static void headingDown() {
		if(elevator[1].getCurrentFloor() == 1) {
			elevator[1].directionUp();

			}
		else {
				elevator[1].decreaseCurrentFloor();
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
				time = time + 0.2;
			}
	}
	
	// generate which floor the person is going to start on (1/2 the time, this will be the first floor)
    // the other half of the time will be the other 9 
    private static int generateStartingFloor() {
        int floorNumber = 1;
        Random random = new Random();
        
        // the random number determines whether the person will start at the first floor or the other 9 floors
        // 0 -> first floor, 1 -> floors 2-10
        int randomNumber = random.nextInt(2); 
        if (randomNumber == 0){
            floorNumber = 1;
        }
        else{
            floorNumber = random.nextInt(9) + 2;
        }
        return floorNumber;
    }
	
	
	
	
}