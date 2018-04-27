import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Controller2 {
    public static void main(String[] args){
        //int maxRunningTime = 100; // a minute
        double time = 0; // in second
        int numberOfCustomers = 100;
        int customerCounter = 0;
        int meanArrival = 5;
        int personAtFloor;
        int floorDestination;
        Floor[] floor = new Floor[11];
        int totalTime = 0;
        

        Elevator elevator = new Elevator(1); // elevator 1
        
        for(int i=0; i < 10; i++) {
            floor[i]= new Floor(i);
        }

        Person person;
        Random rand = new Random();

        LinkedList<Person> peopleList = new LinkedList<>();
        
        while(customerCounter < numberOfCustomers) {
            double custArrival = meanArrival * (- Math.log(1 - rand.nextDouble()));
            totalTime+=custArrival;
            // generate a new floor the person is currently on and a new destination floor if the previous of the two match
            do {
                personAtFloor = generateStartingFloor();
                floorDestination = rand.nextInt(10) + 1;
            }while (personAtFloor == floorDestination);
//            
            person = new Person(customerCounter,custArrival, personAtFloor, floorDestination);
            peopleList.add(person);
            customerCounter++;
            //time++;
        }
        System.out.println("Before Sorting:");
        printList(peopleList);
        Collections.sort(peopleList,new peopleComparator());
        System.out.println("After Sorting:");
        printList(peopleList);
        //time=0;
        customerCounter=0;
        while(customerCounter< numberOfCustomers && time < totalTime) {  
            time+=peopleList.get(customerCounter).arrivalTime;
            System.out.println("Person "+ peopleList.get(customerCounter).personNumber+ " arrival time: "+peopleList.get(customerCounter).arrivalTime);
            System.out.println("Starting from floor "+peopleList.get(customerCounter).personAtFloor +" and ending at floor "+peopleList.get(customerCounter).floorDestination);
            System.out.println("Time is: "+time);
            
            //put customer on appropriate floor
            Floor custFloor = null;
            if (time >= peopleList.get(customerCounter).arrivalTime) {
                custFloor = new Floor(peopleList.get(customerCounter).personAtFloor);
                //determine whether the customer is going up or down
                if (peopleList.get(customerCounter).personAtFloor < peopleList.get(customerCounter).floorDestination){
                    custFloor.addPersonToUpList(peopleList.get(customerCounter));
                }
                else{
                    custFloor.addPersonToDownList(peopleList.get(customerCounter));
                }
                
                //bring the elevator to the appropriate floor
                if (elevator.isElevatorEmpty()){
                    elevator.currentFloor = peopleList.get(customerCounter).personAtFloor;
                    if (elevator.currentFloor - peopleList.get(customerCounter).floorDestination < 0) elevator.direction = 1; //direction is up
                    else elevator.direction = -1; //direction is down
                }
                //determine how much time it takes to go from floor to floor
                time += (peopleList.get(customerCounter).personAtFloor - elevator.currentFloor)*0.2;
                //add to totalTime calculation 
                totalTime += (peopleList.get(customerCounter).personAtFloor - elevator.currentFloor)*0.2;
                
                if (elevator.direction == 1){
                    elevator.addPeopleToElevator(custFloor.upList);
                    time += custFloor.upList.size() * 0.1;
                }
                else if (elevator.direction == -1){
                    elevator.addPeopleToElevator(custFloor.downList);
                    time += custFloor.downList.size() * 0.1;
                }
                System.out.println("Person "+ peopleList.get(customerCounter).personNumber +" has entered the elevator");
            }

            //whoever is on floor i waiting for an elevator, put them in the elevator
            if (time == peopleList.get(customerCounter).arrivalTime){
                elevator.addPersonToElevator(peopleList.get(customerCounter));
                System.out.println("Person "+ peopleList.get(customerCounter).personNumber +" has entered the elevator");
            }
            
            if (elevator.currentFloor == peopleList.get(customerCounter).floorDestination){
                Person p = elevator.removePersonFromElevator();
                System.out.println("Person "+ p.personNumber +" has left the elevator");
            }
            
            // sort people in the elevator by increasing floor destination order
            
            // do calculations on how much time it will take to get to a floor from curr floor
            // ex: person is on floor 2 and wants to go to 4 - takes 4 seconds
            // instead of having uplist and downlist, determine which elevator the person will take
            // that is, the elevator that can get to the person the fastest
            
            customerCounter++;

        }
    }

    private static void printList(LinkedList<Person> peopleList) {
        for (Person p:peopleList){
            System.out.println("person " + p.personNumber);
            System.out.println("arrival time "+p.arrivalTime);
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
