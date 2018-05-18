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

        while(personCounter < numOfPerson) {
            simulatingElevator();
        }
        System.out.println("Total number of persons created: " + personCounter);
        System.out.print("Average time it takes to go from floor i to j: " + df.format(sumOfTimeFloorToFloor / numOfPerson));
        System.out.println(", Standard deviation: "+ df.format(Math.sqrt(getVarianceFloorToFloor(sumOfTimeFloorToFloor / numOfPerson))));
        System.out.print("Average waiting time from floor i to j: "+ df.format(sumOfWaitingTimes / numOfPerson));
        System.out.println(", Standard deviation: "+df.format(Math.sqrt(getVarianceAvgWaitingTime(sumOfWaitingTimes / numOfPerson))));
    }

    public static void simulatingElevator() {

        creatingPerson();
        boarding();
        exit();
        nextMove();
        System.out.println();
        time +=2;

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
        System.out.println("[" + df.format(time) +  "] PersonId: " + newPerson.personNumber + " AtFloor: " + newPerson.personAtFloor
                + " Dest: " + newPerson.floorDestination + " ArrTime: "+ df.format(newPerson.arrivalTime));

    }

    public static void boarding(){


        while(!futureEventList.isEmpty() && futureEventList.peek().getArrivalTime() <= time) {

            int index = calculatingDistance() + 1;

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


    public static int calculatingDistance(){

        int custFloor = futureEventList.peek().getPersonAtFloor();
        int closestElevator;
        int distance;

        for (int i=1; i<=numOfElevator; i++) {

            if (elevator[i].isElevatorAvailable()) {

                distance = Math.abs(custFloor - elevator[i].getCurrentFloor());
                elevator[i].setElevatorDistance(distance);
                closestDistance.add(elevator[i]);
            }

        }
            closestElevator = closestDistance.peek().elevatorNumber;

        System.out.print("[Request] Person: " + futureEventList.peek().personNumber);
        System.out.println(" ClosestElevator is:" + closestElevator);
        return closestElevator;
    }


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


	public static void nextMove() {

        for(int i=1; i<=numOfElevator; i++) {

            if (elevator[i].isElevatorEmpty()){
                elevator[i].setDirection(0);
            } else if(elevator[i].getElevatorList().peek().getPersonDirection() == 1){
                elevator[i].setDirection(elevator[i].getElevatorList().peek().getPersonDirection());
                headingUp(i);
            } else {
                elevator[i].setDirection(elevator[i].getElevatorList().peek().getPersonDirection());
                headingDown(i);
            }

                System.out.println(" Elevator " + i + " at floor " + elevator[i].getCurrentFloor()
                        + " Direction " + elevator[i].printDirectionInWord());
                Iterator<Person> it = elevator[i].getElevatorList().iterator();
                while (it.hasNext()) {
                    Person element = it.next();
                    System.out.println("    PersonId: " + element.personNumber + " AtFloor: " + element.personAtFloor + "  Dest: " + element.floorDestination + "  ArrivalTime: " + df.format(element.getArrivalTime()));
                }
        }

    }

    public static void headingDown(int i) {

            if (elevator[i].getCurrentFloor() == 1) {
                if(elevator[i].getElevatorList().isEmpty()){
                    elevator[i].setDirection(0);
                } else {
                    elevator[i].setDirection(1);
                    elevator[i].increaseCurrentFloor();
                }
            } else {
                elevator[i].decreaseCurrentFloor();
            }

    }

    public static void headingUp(int i) {

            if (elevator[i].getCurrentFloor() == 10) {
                if(elevator[i].getElevatorList().isEmpty()){
                    elevator[i].setDirection(0);
                } else {
                    elevator[i].setDirection(-1);
                    elevator[i].decreaseCurrentFloor();
                }
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

    private static double getVarianceAvgWaitingTime(double mean) {
        double temp = 0;
        for(Double d: waitingTimeList)
            temp += (d-mean)*(d-mean);
        return temp/numOfPerson;
    }
    
    private static double getVarianceFloorToFloor(double mean) {
    	double temp = 0;
        for(Double d: timeFloorToFloorList)
            temp += (d-mean)*(d-mean);
        return temp/numOfPerson;
    }


}