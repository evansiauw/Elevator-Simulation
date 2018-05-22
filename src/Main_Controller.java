import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

/*
Elevator Simulation

Joshua Goldstein, Iwan Siauw, Orlando Calle

Description:
The five elevators are initially distributed evenly throughout the 10 floors. In other words, elevator 1 will start on the 1st floor, elevator 2 will start on the 3rd floor, elevator 3 will start on the 5th floor, 
elevator 4 will start on the 7th floor, and elevator 5 will start on the 9th floor. 

The simulatingElevator() method will perform the simulation based on the number of people that are defined. This method does the following:
1. Create the person and add that person to the future events list. The future events list is a priority queue where arrival time is the priority.
2. Determine who is going to board the elevator
3. Determine who is going to exit the elevators by comparing if the person’s destination with the elevator’s floor matches.
4. Determine what direction the elevator will go

*/

public class Main_Controller {

    static double time = 0; // in second
    static int personCounter = 0;
    static int meanArrival = 5;
    static int personAt;
    static int floorDestination;
    static int totalWaitTime = 0;
    static int numOfElevator = 5;
    static int numOfFloor = 10;
    static int numOfPerson = 100;
    static double arrivalTimeSum = 0;
    static double averageWaitingTime = 0;
    static Random rand = new Random();
    static Elevator[] elevator = new Elevator[numOfElevator + 1];
    static Floor[] floor = new Floor[numOfFloor + 1];
    static DecimalFormat df = new DecimalFormat("0.##");
    static double sumOfWaitingTimes = 0.0;
    static double sumOfTimeFloorToFloor = 0.0;
    static ArrayList<Double> waitingTimeList = new ArrayList<>(); //keeps a record of all waiting times
    static ArrayList<Double> timeFloorToFloorList = new ArrayList<>(); //keeps a record of all floor to floor times
    static PriorityQueue<Person> futureEventList = new PriorityQueue<>();
    static PriorityQueue<Elevator> closestDistance = new PriorityQueue<>();

    public static void main(String[] args) {

        for(int i=1; i <= numOfElevator; i++) {
            elevator[i] = new Elevator(i,(2*i)-1); }

        for(int i=1; i <= numOfFloor; i++) {
            floor[i]= new Floor(i); }

        // each event of the elevator is simulated based on the number of people defined in the simulation
        while(personCounter < numOfPerson) {
            simulatingElevator();
        }

        printStats();
    }

    /* this method performs the simulation of the elevator */
    public static void simulatingElevator() {

        creatingPerson();
        boarding();
        exit();
        nextMove();
        time +=0.2;

    }

    /*
     * Creates one person object at a time with an arrival time. The person will also be added to the future events list.
     */
    public static void creatingPerson() {

        double custArrival = meanArrival * (- Math.log(1 - rand.nextDouble()));

        do {
            personAt = generateStartingFloor();
            floorDestination = rand.nextInt(10) + 1;
        }while (personAt == floorDestination);

        Person newPerson= new Person(++personCounter,custArrival, personAt, floorDestination);
        futureEventList.add(newPerson);

        System.out.println("*****************************************************************");
        System.out.println("[Time: " + df.format(time) +  "] PersonId: " + newPerson.personNumber + " AtFloor: " + newPerson.personAtFloor
                + " Dest: " + newPerson.floorDestination + " ArrTime: "+ df.format(newPerson.arrivalTime));

    }

    /* 
     * Determines which people in the future events list should board the elevator. This depends on whether there are people in the future events list that have an arrival time <= current time.
     * The future events list is a priority queue where arrival time is the priority.
     */
    public static void boarding(){

        while(!futureEventList.isEmpty() && futureEventList.peek().getArrivalTime() <= time) {

            int index = calculatingDistance();

            if(index == 0){ break; }

            if (elevator[index].getCurrentFloor() == futureEventList.peek().getPersonAtFloor()) {
                System.out.print("[Boarding] Person: " + futureEventList.peek().personNumber);
                futureEventList.peek().setTimePersonGoesInElevator(time);
                sumOfWaitingTimes += futureEventList.peek().getWaitingTime();
                waitingTimeList.add(futureEventList.peek().getWaitingTime());
                elevator[index].addPersonToElevator(futureEventList.poll());
                System.out.println(" to Elevator " + index);
                elevator[index].setElevatorAvailability(false);
                time += .1;
            } else {

                if (elevator[index].getCurrentFloor() < futureEventList.peek().getPersonAtFloor()) {
                    elevator[index].increaseCurrentFloor();
                    elevator[index].setDirection(1);
                } else {
                    elevator[index].decreaseCurrentFloor();
                    elevator[index].setDirection(-1);
                }
                break;
            }
            }
        }


    /* This method performs the calculation as to which elevator should be assigned to the person. The person will be assigned the closest elevator. */
    public static int calculatingDistance(){

        int custFloor = futureEventList.peek().getPersonAtFloor();
        int closestElevator= 0;
        int distance;

        for (int i=1; i<=numOfElevator; i++) {
            if (elevator[i].isElevatorAvailable()) {
                distance = Math.abs(custFloor - elevator[i].getCurrentFloor());
                elevator[i].setElevatorDistance(distance);
                closestDistance.add(elevator[i]);
            }
        }

        if(!closestDistance.isEmpty()) {
            closestElevator = closestDistance.peek().elevatorNumber;
            closestDistance.clear();
            System.out.print("[Request] Person: " + futureEventList.peek().personNumber);
            System.out.println(" ClosestElevator is:" + closestElevator);
        }
        else {
            System.out.println("[Request] Person: " + futureEventList.peek().personNumber + " is Waiting");
        }
        return closestElevator;
    }

    /*
     * Determines whether there is anyone that has reached their destination.
     */
    public static void exit() {

        for (int i = 1; i <= numOfElevator; i++) {
            int counter = 0;
            if (!elevator[i].getElevatorList().isEmpty()) {
                Iterator<Person> it = elevator[i].getElevatorList().iterator();
                while (it.hasNext()) {
                    Person element = it.next();
                    if (element.floorDestination == elevator[i].getCurrentFloor()) {
                        counter++;
                        if(counter == 1){ System.out.print("[Exit] Elevator: " + i); }
                        System.out.print(" Person " + element.personNumber + " ");
                        time+=.1;
                        element.completedTime = time;
                        sumOfTimeFloorToFloor += element.getTimeFloorToFloor();
                        timeFloorToFloorList.add(element.getTimeFloorToFloor());
                        it.remove();
                    }
                }


                if (counter > 0) {
                    System.out.println();
                }
            }
        }
    }

    /* determines the next move of the elevator (UP, DOWN, or IDLE)*/
	public static void nextMove() {

        for(int i=1; i<=numOfElevator; i++) {

            System.out.print(" Elevator " + i + " at floor " + elevator[i].getCurrentFloor());

            if (elevator[i].isElevatorEmpty()){
                elevator[i].setDirection(0);
                elevator[i].setElevatorAvailability(true);
            } else if(elevator[i].getElevatorList().peek().getPersonDirection() == 1){
                elevator[i].setDirection(elevator[i].getElevatorList().peek().getPersonDirection());
                headingUp(i);
            } else {
                elevator[i].setDirection(elevator[i].getElevatorList().peek().getPersonDirection());
                headingDown(i);
            }

            System.out.println( " Direction " + elevator[i].printDirectionInWord());
            Iterator<Person> it = elevator[i].getElevatorList().iterator();
            while (it.hasNext()) {
                Person element = it.next();
                System.out.println("    PersonId: " + element.personNumber + " AtFloor: " + element.personAtFloor + "  Dest: " + element.floorDestination + "  ArrivalTime: " + df.format(element.getArrivalTime()));
            }
        }
        System.out.println();
    }

    /*Determines what the elevator should do if the current direction of the elevator is DOWN.*/
    public static void headingDown(int i) {

    	    // if the 1st floor is reached
            if (elevator[i].getCurrentFloor() == 1) {
            	//if the elevator is empty, set it to the IDLE state
                if(elevator[i].getElevatorList().isEmpty()){
                    elevator[i].setDirection(0);
                } 
                //otherwise, make the elevator go UP
                else {
                    elevator[i].setDirection(1);
                    elevator[i].increaseCurrentFloor();
                }
            }
            // otherwise decrease the current floor (this is what normally happens)
            else {
                elevator[i].decreaseCurrentFloor();
            }
    }

    /*Determines what the elevator should do if the current direction of the elevator is UP.*/
    public static void headingUp(int i) {

    		// if the 10th floor is reached
            if (elevator[i].getCurrentFloor() == 10) {
            	//if the elevator is empty, set it to the IDLE state
                if(elevator[i].getElevatorList().isEmpty()){
                    elevator[i].setDirection(0);
                } 
                //otherwise, make the elevator go DOWN
                else {
                    elevator[i].setDirection(-1);
                    elevator[i].decreaseCurrentFloor();
                }
               // System.out.println("this is the 'Top' level, changing direction to 'Down'");
            } 
            // otherwise increase the current floor (this is what normally happens)
            else {
                elevator[i].increaseCurrentFloor();
            }

    }

    /*generates the floor that the person will start on*/
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

    /*Calculates variance for avg waiting time. This is used to calculate standard deviation which is SQRT(VAR(X))*/
    private static double getVarianceAvgWaitingTime(double mean) {
        double temp = 0;
        for(Double d: waitingTimeList)
            temp += (d-mean)*(d-mean);
        return temp/numOfPerson;
    }
    
    /*Calculates variance for avg amount of time it takes to go from floor to floor. This is used to calculate standard deviation which is SQRT(VAR(X))*/
    private static double getVarianceFloorToFloor(double mean) {
    	double temp = 0;
        for(Double d: timeFloorToFloorList)
            temp += (d-mean)*(d-mean);
        return temp/numOfPerson;
    }

    /*Method for printing all the stats related to the elevator simulation*/
    public static void printStats(){
        System.out.println("Total number of persons created: " + personCounter);
        System.out.print("Average time it takes to go from floor i to j: " + df.format(sumOfTimeFloorToFloor / numOfPerson));
        System.out.println(", Standard deviation: "+ df.format(Math.sqrt(getVarianceFloorToFloor(sumOfTimeFloorToFloor / numOfPerson))));
        System.out.print("Average waiting time from floor i to j: "+ df.format(sumOfWaitingTimes / numOfPerson));
        System.out.println(", Standard deviation: "+df.format(Math.sqrt(getVarianceAvgWaitingTime(sumOfWaitingTimes / numOfPerson))));
    }


}