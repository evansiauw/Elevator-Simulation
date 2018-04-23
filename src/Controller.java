import java.util.Random;

public class Controller {

	public static void main(String[] args) {
		
		int maxRunningTime = 60; // a minute
		double time = 0; // in second
		int j = 1;
		int meanArrival = 5;
		int personAt;
		int destinationFloor;
		
		Elevator [] elevator = new Elevator[3];
		Floor [] floor = new Floor[11];
		Person [] person  = new Person[100];
		Random rand = new Random();
		
		for(int i=1; i<=2; i++) {
			elevator[i] = new Elevator(i);
		}
		
		for(int i=1; i<= 10; i++) {
			floor[i]= new Floor(i);
		}
		
		while(time < maxRunningTime) {
			double custArrival = meanArrival * (- Math.log(1 - rand.nextDouble()));
			
			do {
			personAt = rand.nextInt(11) + 1;
			destinationFloor = rand.nextInt() + 1;
			}while (personAt == destinationFloor);
			
			person[j] = new Person(j,custArrival, personAt, destinationFloor);
			
			
			
			
		}
		
		
		
	}
}
