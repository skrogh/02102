import java.util.ArrayList;
import java.util.Comparator;


/**
 * Utility class for sorting ArrayList of ArrayList<Integer>s
 * The ArrayList<Integer> with the smallest number in the first position are sorted into the last position.
 * 
 * @author Søren Andersen, s123369
 *
 */
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