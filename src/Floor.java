import java.util.LinkedList;

public class Floor extends Controller{
	
	int floorNumber;
	LinkedList<Person> upList;
    LinkedList<Person> downList;
	
	public Floor(int floorNumber) {
		this.floorNumber = floorNumber;
		upList = new LinkedList<>();
	    downList = new LinkedList<>();
	}
	
	public void addPersonToUpList(Person p){
	    upList.add(p);
	}
	
	public void addPersonToDownList(Person p){
        downList.add(p);
    }
	
	public LinkedList<Person> getUpList(){
		return upList;
	}
	
	public LinkedList<Person> getDownList(){
		return downList;
	}
	

	
}
