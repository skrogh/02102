import static java.lang.System.out;
import java.util.Scanner;

public class Opgave2 {
	
	// strings for storing all letters in the alphabet, letters in these strings will be "allowed"
	public static final String ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyzæøå";
	public static final String ALPHABET_UPPER = ALPHABET_LOWER.toUpperCase();
	public static final String ALPHABET = ALPHABET_LOWER + ALPHABET_UPPER;
	
	public static void main( String[] arg ) {
		out.println( isPalindrome( "Do geese see god?" ) );
	}
	
	public static String leaveLiterals( String from, String literals ) {
		/*
		 * Trims away all characters from "from" that is not in the string "literals"
		 */
		char[] charArray = from.toCharArray();
		String result = "";
		
		for ( int i = 0; i < charArray.length; i++ ) {
			if ( charInString( charArray[i], literals ) )
				result += charArray[i];
		}
		
		return result;
	}
	
	
	public static boolean charInString( char opa1, String opa2) {
		/*
		 *  Returns true if the character "opa1" exists in the string "opa2"
		 */
		return opa2.indexOf( opa1 ) > -1;
	}
	
	public static String reverseString( String in ) {
		/*
		 * returns "in" backwards
		 */
		String result = "";
		
		for ( int i = in.length()-1; i >= 0; i-- ) {
			result += in.charAt( i );
		}
		
		return result;
		
	}
	
	public static boolean isPalindrome ( String in ) {
		/*
		 * Returns true if the entered string is the same, no matter in which direction you read it
		 */
		in = leaveLiterals( in.toLowerCase() , ALPHABET_LOWER ); // cast to lowercase and trimm of all non letters.
		return in.matches( reverseString( in ) );
	}
	
	
}
