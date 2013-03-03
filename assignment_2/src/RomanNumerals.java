/*
 * =============================================
 * Carsten Nielsen og Søren Krogh Andersen
 * Converts a number entered by the user to
 * roman numerals.
 * =============================================
 */

import static java.lang.System.out;
import java.util.Scanner;

public class RomanNumerals {
	
	/*
	 * Literals have the following values:
	 *	 I = 1	 		*	V = 5
	 * 	 X = 10	 		*	L = 50
	 *   C = 100 		*   D = 500
	 *   M = 1000	 	*   V̅ = 5000
	 *   X̅ = 10000	 	*   L̅ = 50000
	 *  |I̅| = 100000	*  |V̅| = 500000
	 *  |X̅| = 1000000	*  |L̅| = 5000000
	 *  |C̅| = 10000000	*  |D̅| = 50000000
	 *  |M̅| = 100000000
	 */
	
	// these two constants store the literals in decade 1-9 and 1-8 respectively
	public static final String[] LITERAL_ONES = 
		{ "I", "X", "C", "M", "X\u0305", "|I\u0305|", "|X\u0305|", "|C\u0305|", "|M\u0305|" };
	public static final String[] LITERAL_FIVES = 
		{ "V", "L", "D", "V\u0305", "L\u0305", "|V\u0305|", "|L\u0305|", "|D\u0305|" };

	public static void main( String[] args ) {
		
		Scanner console = new Scanner( System.in ); // open a scanner, scanning the console
		
		boolean exit = false;
		while( !exit ) {
			out.println( "Input any positive integer. Type \"exit\" to end the program" );
	
			String in  = console.nextLine(); // temporary string storing the input
			
			/* check if the entered input is really a number. We could use nextInt and handle exceptions, 
			 * but this way we would not be able to: 1. look for "exit" 2. handle numbers larger than 2^31-1,
			 * of course this is a bit stupid when working with Roman numerals.
			 */
			if ( isPosInt(in) ) {  
				out.println( arabToRoman(in) ); // print the number	
			} else if ( in.matches( "exit" ) ) {
				exit = true; // escape the while-loop and continue.
			} else if ( in.matches( "test" ) ) {
				testDecades();
			} else {
				out.println( "Please enter a positive integer, " +
						"that's a number without a decimal point," +
						"larger than zero" ); // throw error message to console
			}
		}
		
		out.println( "Done!" );
		
		console.close();
	}


	public static String arabToRoman( long arabic ) { // for numbers
		// this functions simply converts the input to a string and calls the functions for strings
		if ( arabic>0 ) // if in the allowed range
			return arabToRoman( String.valueOf( arabic ) ); // call the string Arab to Roman for the long
		else
			return "not in the correct range";
	}
	
	public static String arabToRoman( String arabic ) { // for strings
		String roman = ""; // empty string for Roman number
		for ( int i = 0; i < arabic.length(); i++ ) { // for each digit in the Arabic number convert it to the combination of Roman digits
			int num = arabic.charAt( i ) - 0x30; // get numbers from left; zero is at 0x30, nine is at 0x39
			roman += arabLiteralToRoman( num, arabic.length() - i ); // convert to Roman digits in the correct decade
		}
		return roman;
	}

	public static String arabLiteralToRoman( int num, int dec ) {
		// returns the Roman literal combination that equals the Arabic in decade dec. if num is not in the range [1-9], "" is returned
		String result = "";
		if ( dec > 8 ) { // if we can no longer write numbers as combinations of literals, switch to base 1 and the correct amount of 100000000
			for ( int i = 0; i<Math.pow( 10, dec-9 ); i++ ) { // create 10^(dec-9)
				result += getRomanLiterals( num, 1, 9 ); // times num;
			}
		} else { // get the combination of Roman literals giving num in decade dec
			switch ( num ) {
			case 0:
				// there are no zeros in Roman
				break;
			case 1:
				result += getRomanLiterals( 1, 1, dec ); // get 1 ones in the correct decade;
				break;
			case 2:
				result += getRomanLiterals( 2, 1, dec ); // get 2 ones in the correct decade;
				break;	
			case 3:
				result += getRomanLiterals( 3, 1, dec ); // get 3 ones in the correct decade;
				break;	
			case 4:
				result += getRomanLiterals( 1, 1, dec ) + getRomanLiterals( 1, 5, dec ); // get one, five in the correct decade;
				break;	
			case 5:
				result += getRomanLiterals( 1, 5, dec ); // get five in the correct decade;
				break;	
			case 6:
				result += getRomanLiterals( 1, 5, dec ) + getRomanLiterals( 1, 1, dec ); // get five, one in the correct decade;
				break;	
			case 7:
				result += getRomanLiterals( 1, 5, dec ) + getRomanLiterals( 2, 1, dec ); // get five, one, one in the correct decade;
				break;
			case 8:
				result += getRomanLiterals( 1, 5, dec ) + getRomanLiterals( 3, 1, dec ); // get five, one, one, one in the correct decade;
				break;	
			case 9:
				result += getRomanLiterals( 1, 1, dec ) + getRomanLiterals( 1, 1, dec + 1 ); // get one, ten in the correct decade;
				break;	
			}
		}
		return result;
	}

	public static String getRomanLiterals( int i, int num, int dec ) {
		// get the Roman literal for num in decade dec, i times
		String literal = "";
		
		if( num == 1 ) {
			// choose 1, 10, 100, 1000 ... etc (1 is first decade, 10 second etc.)
			literal = LITERAL_ONES[dec-1];
		} else {
			// choose 5, 50, 500 ... etc (5 is first decade, 50 second etc.)
			literal = LITERAL_FIVES[dec-1];
		}
		
		// add i of the literals) 
		String result = "";
		for ( int j = 0; j < i; j++ ) {
			result += literal;
		}
		return result;
	}


	// did not get used in the end
	public static boolean isInt( String in ) {
		boolean result = in.length() > 0; // if no length; it's not an int.

		for ( int i = 0; ( i < in.length() ) && result; i++ ) { // break if we hit a non-number 
			char c = in.charAt(i);
			// set to false, if any char is NOT a number; the first char may be a minus
			result &= ( c == '0' ) || ( c == '1' ) ||
					( c == '2' ) || ( c == '3') ||
					( c == '4' ) || ( c == '5') ||
					( c == '6' ) || ( c == '7') ||
					( c == '8' ) || ( c == '9' ) ||
					( ( i == 0 ) && ( c == '-' ) ); 
		}
		return result;
	}
	
	public static boolean isPosInt(String in) {
		boolean result = in.length()>0; // if no length; it's not an int.

		for(int i = 0; (i<in.length())&&(result); i++) { // break if we hit a non-number 
			char c = in.charAt(i);
			// set to false, if any char is NOT a number
			result &= ( ( c == '0' ) && ( i != 0 ) ) || // not zero
					( c == '1' ) ||
					( c == '2' ) || ( c == '3' ) ||
					( c == '4' ) || ( c == '5' ) ||
					( c == '6' ) || ( c == '7' ) ||
					( c == '8' ) || ( c == '9' );
		}
		return result;
	}

	/*
	 * ===============================================================
	 * testDecades:
	 * Runs through the program with the values [1-9]^n for n=0 to n=8
	 * and prints the values to the console
	 * ===============================================================
	 */
	public static void testDecades() {
		System.out.println("STARTING TEST");
		
		for ( int i = 1; i <= 8; i++ ) {
			for ( int j = 1; j <= 9; j++ ) {
				int testNumber = (int) ( j * Math.pow( 10, i ) );
				String romanTestNumeral = arabToRoman( testNumber );
				
				System.out.print( j + "*" + "10^" + i + " = " + romanTestNumeral + "  " );
				
				if ( i == 8 )
					break;
			}
			
			System.out.println();
		}
		
		
	}
}
