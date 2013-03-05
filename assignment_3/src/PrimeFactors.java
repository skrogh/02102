
import java.util.*;

public class PrimeFactors {

	public static void main(String[] args) {
		
		System.out.println( primeFactors( 24 ).toString() );
		System.out.println( eratosthenesSieve( 20 ).toString() );
	}
	
	
	public static ArrayList<Integer> primeFactors( long n ) {
		ArrayList<Integer> primeList = eratosthenesSieve( n );
		ArrayList<Integer> factorList = new ArrayList<Integer>();
		//Find prime factors and add to list of primes
		for ( int i = 0; i < primeList.size() - 1; i++ ) {
			if ( primeList.get( i ) > (int) Math.sqrt( n ) )
				break;
			if ( n % primeList.get( i ) == 0 ) {
				n /= i; 
				factorList.add( primeList.get( i ) );	
			}
				
		}
		
		return factorList;
	}
	
	
	public static ArrayList<Integer> eratosthenesSieve( long n ) {
		int arraySize = (int) Math.sqrt( n );
		//boolean[0] = 2, initialized to false by default
		boolean[] numbersMarked = new boolean[ arraySize ];
		
		ArrayList<Integer> primeList = new ArrayList<Integer>();
		
		for ( int i = 0; i < arraySize; i++ ) {
			//find first marked number
			int firstMarked = 0;
			for ( int j = 0; j < arraySize; j++) {
				if ( numbersMarked[ j ] == false ) {
					firstMarked = j + 2;
					numbersMarked[ j ] = true;
					primeList.add( firstMarked ); 
					break;
				}
			}
			//check for algo completion
			if ( firstMarked == 0 )
				break;
			
			//mark all multiples
			for ( int j = firstMarked - 2; j < arraySize; j+=firstMarked ) {
				numbersMarked[ j ] = true;
			}
		}
		
		return primeList;
		
	}

}
