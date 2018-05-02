public class Person extends Controller implements Comparable <Person>{

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
	
	public double getArrivalTime() {
		return arrivalTime;
	}
	
	/*
	 * Calculation for waiting time
	 */
	public double getWaitingTime(){
	    return completedTime - arrivalTime;
	}

	@Override
	public int compareTo(Person o) {
		
		if (this.arrivalTime < o.arrivalTime) {
			return -1;
		} if (this.arrivalTime == o.arrivalTime) {
			return 0;		
		}else {
			return 1;
		}
		
	
	}
}
