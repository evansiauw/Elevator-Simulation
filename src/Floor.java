import java.util.LinkedList;

public class Floor extends Controller{

	LinkedList<Person> UpList = new LinkedList<>();
	LinkedList<Person> downList = new LinkedList<>();
	int floorNumber;
	
	public Floor(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	
	
}
