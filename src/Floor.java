import java.util.LinkedList;

/*
 * Joshua Goldstein, Iwan Siauw, Orlando Calle
 * 
 * This class defines the floor object which has a floor number and a list containing all the passengers on a floor of a building.
 */
public class Floor extends Controller{

	int floorNumber;
	LinkedList <Person> passengerList;
	
	public Floor(int floorNumber) {
		this.floorNumber = floorNumber;
		passengerList = new LinkedList<>();
	}
	
	public void addToPassengerList(Person p){
	    passengerList.add(p);
	}

	public LinkedList<Person> getPassengerList(){ return passengerList; }

}
