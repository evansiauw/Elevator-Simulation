import java.util.Vector;

public class Controller {

	public static void main(String[] args) {
		
		double time = 0;
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
