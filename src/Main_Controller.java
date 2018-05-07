import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

public class Main_Controller {

    static double time = 0; // in second
    static int personCounter = 0;
    static int meanArrival = 5;
    static int personAt;
    static int floorDestination;
    static int totalWaitTime = 0;
    static int numOfElevator = 5;
    static int numOfFloor = 10;
    static int numOfPerson = 50;
    static double arrivalTimeSum = 0;
    static double averageWaitingTime = 0;
    static Random rand = new Random();
    static Elevator[] elevator = new Elevator[numOfElevator + 1];
    static Floor[] floor = new Floor[numOfFloor + 1];
    static DecimalFormat df = new DecimalFormat(".#");
    static ArrayList<Double> waitingTimesForEachPerson = new ArrayList<>();
    static PriorityQueue<Person> futureEventList = new PriorityQueue<>();

    public static void main(String[] args) {

        for(int i=1; i <= numOfElevator; i++) {
            elevator[i] = new Elevator(i,(2*i)-1); }

        for(int i=1; i <= numOfFloor; i++) {
            floor[i]= new Floor(i); }

        while(personCounter < numOfPerson) {
            simulatingElevator();
        }
        System.out.println("Total number of persons created: " + personCounter);
    }

    public static void simulatingElevator() {


        creatingPerson();
        boarding();
        exit();
        nextMove();
        System.out.println();
        time +=0.2;
        for(int i=1; i<=5; i++){
            System.out.println("Elevator " + i + " at floor " + elevator[i].getCurrentFloor()
                    + " Direction " + elevator[i].printDirectionInWord());
        }

        System.out.println("******************************************************************");

    }

    public static void creatingPerson() {

        double custArrival = meanArrival * (- Math.log(1 - rand.nextDouble()));

        do {
            personAt = generateStartingFloor();
            floorDestination = rand.nextInt(10) + 1;
        }while (personAt == floorDestination);

        Person newPerson= new Person(++personCounter,custArrival, personAt, floorDestination);
        futureEventList.add(newPerson);

        System.out.println("*****************************************************************");
        System.out.println("Time: " + df.format(time) +  " PersonId: " + newPerson.personNumber + " AtFloor: " + newPerson.personAtFloor
                + " Dest: " + newPerson.floorDestination + " ArrTime: "+ df.format(newPerson.arrivalTime));

    	/*System.out.println("Future events list: ");
        for(Person p: futureEventList) {
        	System.out.println("Person "+ p.personNumber + " arrival time: "+df.format(p.getArrivalTime()));
        }*/
    }

    public static void boarding(){

        while(!futureEventList.isEmpty() && futureEventList.peek().getArrivalTime() <= time){
            int index = calculatingDistance();
            System.out.print("*Boarding person " + futureEventList.peek().personNumber);
            elevator[index].addPersonToElevator(futureEventList.poll());
            System.out.println(" to Elevator " + index);
        }
    }

    public static int calculatingDistance(){

        int custFloor = futureEventList.peek().getPersonAtFloor();
        int closestElevator = 1;
        int distance;
        int currDistance = 0;

        for (int i=1; i<=5; i++){

                distance = Math.abs(custFloor - elevator[i].getCurrentFloor());
                if (i == 1) {
                    currDistance = distance;
                } else if (distance <= currDistance
                           && (futureEventList.peek().getPersonDirection() == elevator[i].direction
                               || elevator[i].direction == 0)
                          ){
                    currDistance = distance;
                    closestElevator = i;
                }
        }
        return closestElevator;
    }


    public static void exit() {

        for (int i = 1; i <= 5; i++) {
            int counter = 0;
            if (!elevator[i].getElevatorList().isEmpty()) {
                Iterator<Person> it = elevator[i].getElevatorList().iterator();
                while (it.hasNext()) {
                    Person element = it.next();
                    if (element.floorDestination == elevator[i].getCurrentFloor()) {
                        counter++;
                        if(counter == 1){ System.out.print("Elevator " + i + " Exit: "); }
                        System.out.print("Person " + element.personNumber + " ");
                        element.completedTime = time;
                        waitingTimesForEachPerson.add(element.getWaitingTime());
                        it.remove();
                    }
                }
                if (counter > 0) {
                    System.out.println();
                }
            }
        }
    }

    public static void nextMove() {

        for(int i=1; i<=5; i++) {

            if (elevator[i].isElevatorEmpty()){
                elevator[i].setDirection(0);
            } else if(elevator[i].getElevatorList().peek().getPersonDirection() == 1){
                elevator[i].setDirection(elevator[i].getElevatorList().peek().getPersonDirection());
                headingUp(i);
            } else {
                elevator[i].setDirection(elevator[i].getElevatorList().peek().getPersonDirection());
                headingDown(i);
            }

            System.out.println("*List of people in the elevator " + i);
            Iterator<Person> it = elevator[i].getElevatorList().iterator();
            while (it.hasNext()) {
                Person element = it.next();
                System.out.println("PersonId: " + element.personNumber + "  Dest: " + element.floorDestination + "  ArrivalTime: " + df.format(element.getArrivalTime()));
            }
        }

    }

    public static void headingDown(int i) {

            if (elevator[i].getCurrentFloor() == 1) {
               // System.out.println("this is the 'Lowest' level, changing direction to 'Up'");
                elevator[i].directionUp();
            } else {
                elevator[i].decreaseCurrentFloor();
            }

    }

    public static void headingUp(int i) {

            if (elevator[i].getCurrentFloor() == 10) {
                elevator[i].directionDown();
               // System.out.println("this is the 'Top' level, changing direction to 'Down'");
            } else {
                elevator[i].increaseCurrentFloor();
            }

    }

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