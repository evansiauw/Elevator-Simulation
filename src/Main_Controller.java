import java.text.DecimalFormat;
import java.util.ArrayList;
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
    static int numOfPerson = 20;
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
            time += 0.4;
        }
        System.out.println("Total number of persons created: " + personCounter);
    }

    public static void simulatingElevator() {

        creatingPerson();
        boarding();
        exit();
        System.out.println();
        nextMove();
    }

    public static void creatingPerson() {

        double custArrival = meanArrival * (- Math.log(1 - rand.nextDouble()));

        do {
            personAt = generateStartingFloor();
            floorDestination = rand.nextInt(10) + 1;
        }while (personAt == floorDestination);

        Person newPerson= new Person(++personCounter,custArrival, personAt, floorDestination);
        futureEventList.add(newPerson);

        System.out.println("***Time: " + df.format(time) +  " PersonId: " + newPerson.personNumber + " AtFloor: " + newPerson.personAtFloor
                + " Dest: " + newPerson.floorDestination + " ArrTime: "+ df.format(newPerson.arrivalTime));

    }

    public static void boarding(){

        while(!futureEventList.isEmpty() && futureEventList.peek().getArrivalTime() <= time){
            int index = calculatingDistance();
            System.out.print("Boarding person " + futureEventList.peek().personNumber);
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

            /*System.out.println("***List of people in the elevator " + i);
            Iterator<Person> it = elevator[i].getElevatorList().iterator();
            while (it.hasNext()) {
                Person element = it.next();
                System.out.println("PersonId: " + element.personNumber + "  Dest: " + element.floorDestination + "  ArrivalTime: " + df.format(element.getArrivalTime()));
            }*/
        }
    }

    public static void headingDown() {
        if(elevator[1].getCurrentFloor() == 1) {
            elevator[1].directionUp();
        }
        else {
            elevator[1].decreaseCurrentFloor();
            time = time + 0.2;
        }
    }

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