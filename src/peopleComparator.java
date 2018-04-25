import java.util.Comparator;

public class peopleComparator implements Comparator<Person>{

    @Override
    public int compare(Person o1, Person o2) {
        if(o1.arrivalTime > o2.arrivalTime){
            return 1;
        } else {
            return -1;
        }
    }

}
