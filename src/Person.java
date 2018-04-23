
public class Person extends Controller{

	int floorDestination;
	int personNumber;
	int personAt;
	double arrivalTime;
	
	public Person(int personNumber, double arrivalTime, int personAt, int floorDestination){
		this.personNumber = personNumber;
		this.arrivalTime = arrivalTime;
		this.personAt = personAt;
		this.floorDestination = floorDestination;
	}
}
