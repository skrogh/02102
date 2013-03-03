/*
 * =============================================
 * Carsten Nielsen og Søren Krogh Andersen
 * Checks whether or not a string entered by
 * the user is a palindrome.
 * =============================================
 */

import static java.lang.System.out;
import java.util.Scanner;

public class Palindrome {
	
	// strings for storing all letters in the alphabet, letters in these strings will be "allowed"
	public static final String ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyzæøå";
	public static final String ALPHABET_UPPER = ALPHABET_LOWER.toUpperCase();
	public static final String ALPHABET = ALPHABET_LOWER + ALPHABET_UPPER;
	
	public static void main( String[] arg ) {
		
		Scanner console = new Scanner( System.in );
		
		boolean exit = false;
		while( !exit ) {
			out.println( "Input a string. Input \"exit\" to end the program." );
	
			String in  = console.nextLine(); // temporary string storing the input
			
			if ( in.matches( "exit" ) )
				exit = true; // escape the while-loop and continue.
			else {
				if ( isPalindrome( in ) )
					out.println( "\"" + in + "\"\nIs a palindrome." );
				else
					out.println( "\"" + in + "\"\nIs not a palindrome!" );
			}
		}
		
		out.println( "Done!" );
		
		console.close();
	}

	/*
	 * ==========================================================================
	 * leaveLiterals:
	 * Trims away all characters from "from" that is not in the string "literals"
	 * ==========================================================================
	 */
	public static String leaveLiterals( String from, String literals ) {
		char[] charArray = from.toCharArray();
		String result = "";
		
		for ( int i = 0; i < charArray.length; i++ ) {
			if ( charInString( charArray[i], literals ) )
				result += charArray[i];
		}
		
		return result;
	}
	
	/*
	 * =================================================================
	 * charInString:
	 *  Returns true if the character "opa1" exists in the string "opa2"
	 * ================================================================
	 */
	public static boolean charInString( char opa1, String opa2 ) {
		return opa2.indexOf( opa1 ) > -1; //indexOf(String string) returns -1 if the sequence is not found.
	}
	
	/*
	 * =====================================================
	 * reverseString:
	 * returns "in" backwards
	 * =====================================================
	 */
	public static String reverseString( String in ) {
		String result = "";
		
		for ( int i = in.length()-1; i >= 0; i-- ) {
			result += in.charAt( i );
		}
		
		return result;
		
	}
	
	/*
	 * ========================================================================================
	 * isPalindrome:
	 * Returns true if the entered string is the same, no matter in which direction you read it
	 * ========================================================================================
	 */
	public static boolean isPalindrome ( String in ) {
		in = leaveLiterals( in.toLowerCase() , ALPHABET_LOWER ); // cast to lowercase and trimm of all non letters.
		return in.matches( reverseString( in ) );
	}
	
	
}
