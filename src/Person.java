
public class Person extends Controller{

	int floorDestination;
	int personNumber;
	int personAtFloor;
	double arrivalTime;
	double completedTime;
	
	public Person(int personNumber, double arrivalTime, int personAt, int floorDestination){
		this.personNumber = personNumber;
		this.arrivalTime = arrivalTime;
		this.personAtFloor = personAt;
		this.floorDestination = floorDestination;
	}
	
}
