
import java.util.*;

public class PrimeFactors {

	public static void main(String[] args) {
		//System.out.println(Integer.MAX_VALUE);
		long timestart = System.currentTimeMillis();
		System.out.println( primeFactors( 9223372036854775807L ).toString() );
		System.out.println( System.currentTimeMillis() - timestart );
		//System.out.println( eratosthenesSieve( 15 ).toString() );
	}
	
	
	public static ArrayList<Long> primeFactors( long n ) {
		ArrayList<Long> factorList = new ArrayList<Long>();
		
		for (long i = 2; i < ( (long) Math.sqrt( n ) + 1 ); i++ ) {
			if ( n % i == 0 ) {
				n /= i;
				factorList.add( i );
				i--;
			}
		}
		
		if ( n != 0 )
			factorList.add( n );
		
		return factorList;
	}
	
}
