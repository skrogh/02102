/*===================================================
 *PrimeFactors
 * Computes the prime factors of a number inputted
 * by a user and outputs them as a list.
 *===================================================
 */
import java.util.*;

public class PrimeFactors {

	private static final Scanner inputScanner = new Scanner( System.in );
	private static boolean end_program;
	
	public static void main(String[] args) {
		
		while ( !end_program ) {
			
			long factorNum = getInput();
			if ( end_program )
				break;
			System.out.println( primeFactors( factorNum ).toString() );

			
		}
	}
	
    /*================================================================
     * primeFactors()
     * Attemps to find the prime factors of a number by iterative
     * division
     * ==============================================================
     */
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
	
    /*===============================================================
     * getInput()
     *  prompts the user for a positive integer larger than 1
     *  for factorization. Ends the program if the input is "exit"
     *  ============================================================
     */
	public static long getInput() {
		System.out.println( "Enter a positive integer greater than 1 to factorize" +
            ", exit to terminate: " );
		long factorNumber = 2;
		try {
			factorNumber = inputScanner.nextLong();
		}
		catch ( InputMismatchException e ) {
			if ( inputScanner.next().equals( "exit" ) ) {
				end_program = true;
			}
			else {
				factorNumber = getInput();
			}
				
		}
		
		if ( factorNumber <= 1 )
			return getInput();
		return factorNumber;
	}
	
}
