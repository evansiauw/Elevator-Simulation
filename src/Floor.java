import java.util.LinkedList;

public class Floor extends Controller{

	int floorNumber;
	LinkedList <Person> passengerList;
	
	public Floor(int floorNumber) {
		this.floorNumber = floorNumber;
		passengerList = new LinkedList<>();
	}
	
	public void setPassengerList(Person p){
	    passengerList.add(p);
	}

	public LinkedList<Person> getPassengerList(){ return passengerList; }
	

}
