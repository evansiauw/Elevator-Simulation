public class Person extends Controller implements Comparable <Person>{

	int floorDestination;
	int personNumber;
	int personAtFloor;
	Double arrivalTime;
	double completedTime;
	int direction; // 1 for up and -1 for down
	
	public Person(int personNumber, double arrivalTime, int personAt, int floorDestination){
		this.personNumber = personNumber;
		this.arrivalTime = arrivalTime;
		this.personAtFloor = personAt;
		this.floorDestination = floorDestination;
	}

	public int getPersonDirection(){

		if (personAtFloor < floorDestination){
			direction = 1;
		} else {
			direction = -1;
		}
		return direction;
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
