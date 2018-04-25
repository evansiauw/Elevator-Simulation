import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Controller2 {
    public static void main(String[] args){
        int maxRunningTime = 60; // a minute
        double time = 0; // in second
        int j = 0;
        int meanArrival = 5;
        int personAtFloor;
        int floorDestination;
        
        LinkedList<Person> upList = new LinkedList<>();
        LinkedList<Person> downList = new LinkedList<>();
        
        
        LinkedList<Integer> buttonsPressed = new LinkedList<>();
        Elevator elevator = new Elevator(1); // elevator 1

        Person person;
        Random rand = new Random();

        LinkedList<Person> peopleList = new LinkedList<>();
        
        while(time < maxRunningTime) {
            double custArrival = meanArrival * (- Math.log(1 - rand.nextDouble()));
            
            // generate a new floor the person is currently on and a new destination floor if the previous of the two match
            do {
                personAtFloor = generateStartingFloor();
                floorDestination = rand.nextInt(10) + 1;
            }while (personAtFloor == floorDestination);
//            
            person = new Person(j,custArrival, personAtFloor, floorDestination);
            peopleList.add(person);
            j++;
            time++;
        }
        System.out.println("Before Sorting:");
        printList(peopleList);
        Collections.sort(peopleList,new peopleComparator());
        System.out.println("After Sorting:");
        printList(peopleList);
        time=0;
        j=0;
        while(time < maxRunningTime) {  
            System.out.println(peopleList.get(j).arrivalTime);
            System.out.println(time);
            if (time == peopleList.get(j).arrivalTime){
                elevator.addPersonToElevator(peopleList.get(j));
                System.out.println("Person "+ peopleList.get(j).personNumber +" has entered the elevator");
            }
            
            if (elevator.currentPosition == peopleList.get(j).floorDestination){
                Person p = elevator.removePersonFromElevator();
                System.out.println("Person "+ p.personNumber +" has left the elevator");
            }
            
            // sort people in the elevator by increasing floor destination order
            
            // do calculations on how much time it will take to get to a floor from curr floor
            // ex: person is on floor 2 and wants to go to 4 - takes 4 seconds
            // instead of having uplist and downlist, determine which elevator the person will take
            // that is, the elevator that can get to the person the fastest
            
            j++;
            time++;
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
