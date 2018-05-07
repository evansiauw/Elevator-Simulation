public class Person extends Controller implements Comparable <Person>{

	int floorDestination;
	int personNumber;
	int personAtFloor;
	Double arrivalTime;
	double completedTime;
	
	public Person(int personNumber, double arrivalTime, int personAt, int floorDestination){
		this.personNumber = personNumber;
		this.arrivalTime = arrivalTime;
		this.personAtFloor = personAt;
		this.floorDestination = floorDestination;
	}

	public int getPersonAtFloor(){ return personAtFloor; }

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
	public int compareTo(Person other) {
		
		return this.arrivalTime.compareTo(other.arrivalTime);

	}
}
