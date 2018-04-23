public class Controller {

	public static void main(String[] args) {
		
		int maxRunningTime = 1; // in minute
		double time = 0; // in second
		Elevator [] elevator = new Elevator[3];
		Floor [] floor = new Floor[11];
		
		for(int i=1; i<=2; i++) {
			elevator[i] = new Elevator(i);
		}
		
		for(int i=1; i<= 10; i++) {
			floor[i]= new Floor(i);
		}
		
		
		
		
	}
}
