import java.util.LinkedList;
import java.util.Random;

public class Controller {

	public static void main(String[] args) {
		
		int maxRunningTime = 60; // a minute
		double time = 0; // in second
		int j = 0;
		int meanArrival = 5;
		int personAt;
		int floorDestination;
		
		LinkedList<Person> upList = new LinkedList<>();
		LinkedList<Person> downList = new LinkedList<>();
		LinkedList<Integer> buttonsPressed = new LinkedList<>();
		Elevator [] elevator = new Elevator[3];
		Floor [] floor = new Floor[11];
		Person [] person  = new Person[100];
		Random rand = new Random();
		
		for(int i=0; i < 2; i++) {
			elevator[i] = new Elevator(i);
		}
		
		for(int i=0; i < 10; i++) {
			floor[i]= new Floor(i);
		}
		
		while(time < maxRunningTime) {
			double custArrival = meanArrival * (- Math.log(1 - rand.nextDouble()));
			
			do {
			personAt = rand.nextInt(11) + 1;
			floorDestination = rand.nextInt() + 1;
			}while (personAt == floorDestination);
			
			person[j] = new Person(j,custArrival, personAt, floorDestination);
			if(person[j].personAtFloor < person[j].floorDestination) {
				upList.add(person[j]); }
			else {
				downList.add(person[j]);
			}
			
			// do calculations on how much time it will take to get to a floor from curr floor
			// ex: person is on floor 2 and wants to go to 4 - takes 4 seconds
			// instead of having uplist and downlist, determine which elevator the person will take
			// that is, the elevator that can get to the person the fastest
			
			j++;
			time++;
		}
		
	}
		
	
	
}

