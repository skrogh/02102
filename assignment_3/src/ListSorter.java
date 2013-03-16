import java.util.ArrayList;
import java.util.Comparator;


// Sorts a list of list according to the first nubler in each list.
public class ListSorter implements Comparator<ArrayList<Integer>> {
	@Override
	public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
		if ( o1.get( 0 ) < o2.get( 0 ) )
			return +1;
		else if ( o1.get( 0 ) > o2.get( 0 ) )
			return -1;
		else
			return 0;
	}  
}