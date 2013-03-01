import static java.lang.System.out;
import java.util.Scanner;


public class Opgave1 {

	public static void main(String[] args) {
		Scanner console = new Scanner(System.in); // open a scanner, scanning the console

		out.println("Input any positive integer."); // print a message

		String in; // temporary string storing the next input
		in = console.nextLine(); // get the input
		out.println();
		if(isPosInt(in)) { // check if the entered input is really a number
			String roman = arabToRoman(in);
			out.println(roman); // print the number
			
					
		} else {
			out.println("Please enter an integer, " +
					"that's a number without a decimal point," +
					"larger than zero"); // throw error message to console
		}

		out.println("Done!");
		
		console.close(); // close the console-scanner
	}


	public static String arabToRoman(long arabic) { // for ints
		/*
		 *	I = 1
		 *	V = 5
		 *	X = 10
		 *	L = 50
		 *  C = 100
		 *  D = 500
		 *  M = 1000
		 *  From left to right:
		 *  smaller in front of larger are subtracted from that;
		 *  smaller (this also be a larger, that is subtracted from) after larger is added.
		 *  only literals smaller than 1/5 of the next one can be in front of it.
		 *  for now nothing greater than 3999 can be an input
		 */
		if(arabic>0){ // if in the allowed range
			String str = String.valueOf(arabic); // break into string, to get length and individual literals
			String roman = ""; // empty string for Roman number
			for(int i = 0; i<str.length(); i++) {
				int num = str.charAt(i) - 0x30; // get numbers from left; zero is at 0x30, nine is at 39
				roman += arabLiteralToRoman(num, str.length()-i);
			}
			return roman;
		} else {
			return "not in the correct range";
		}
	}
	
	public static String arabToRoman(String arabic) { // for strings
		/*
		 *	I = 1
		 *	V = 5
		 *	X = 10
		 *	L = 50
		 *  C = 100
		 *  D = 500
		 *  M = 1000
		 *  From left to right:
		 *  smaller in front of larger are subtracted from that;
		 *  smaller (this also be a larger, that is subtracted from) after larger is added.
		 *  only literals smaller than 1/5 of the next one can be in front of it.
		 *  for now nothing greater than 3999 can be an input
		 */
		
		String str = arabic; // break into string, to get length and individual literals
		String roman = ""; // empty string for Roman number
		for(int i = 0; i<str.length(); i++) {
			int num = str.charAt(i) - 0x30; // get numbers from left; zero is at 0x30, nine is at 39
			roman += arabLiteralToRoman(num, str.length()-i);
		}
		return roman;
	}


	public static String arabLiteralToRoman(int num, int dec) {
		// returns the Roman literal combination that equals the Arabic in decade dec
		String result = "";
		switch (num) { // perhaps this can be optimized? but for what reason, this is fast in execution and writing
		case 0:
			// there are no zeros in Roman
			break;
		case 1:
			result += getRomanLiterals(1, 1, dec); // get 1 ones in the correct decade;
			break;
		case 2:
			result += getRomanLiterals(2, 1, dec); // get 2 ones in the correct decade;
			break;	
		case 3:
			result += getRomanLiterals(3, 1, dec); // get 3 ones in the correct decade;
			break;	
		case 4:
			result += getRomanLiterals(1, 1, dec) + getRomanLiterals(1, 5, dec); // get one, five in the correct decade;
			break;	
		case 5:
			result += getRomanLiterals(1, 5, dec); // get five in the correct decade;
			break;	
		case 6:
			result += getRomanLiterals(1, 5, dec) + getRomanLiterals(1, 1, dec); // get five, one in the correct decade;
			break;	
		case 7:
			result += getRomanLiterals(1, 5, dec) + getRomanLiterals(2, 1, dec); // get five, one, one in the correct decade;
			break;
		case 8:
			result += getRomanLiterals(1, 5, dec) + getRomanLiterals(3, 1, dec); // get five, one, one, one in the correct decade;
			break;	
		case 9:
			result += getRomanLiterals(1, 1, dec) + getRomanLiterals(1, 1, dec+1); // get one, ten in the correct decade;
			break;	
		}
		return result;
	}


	public static String getRomanLiterals(int i, int num, int dec) {
		// get the Roman literal for num in decade dec, i times
		String literal = "";
		if(num==1) {
			// choose 1, 10, 100, 1000 (1 is first decade)
			switch(dec) {
			case 1:
				literal = "I";
				break;
			case 2:
				literal = "X";
				break;
			case 3:
				literal = "C";
				break;
			case 4:
				literal = "M";
				break;
			default:
				if(dec>4)
					literal = generateRomanOne(dec); // generate with |))
			}
		} else {
			// choose 5, 50, 500 (5 is first decade)
			switch(dec) {
			case 1:
				literal = "V";
				break;
			case 2:
				literal = "L";
				break;
			case 3:
				literal = "D";
				break;
			default:
				if(dec>3)
					literal = generateRomnfive(dec); // generate with ((|))
			}
		}
		// add i of the literals) 
		String result = "";
		for(int j = 0; j<i; j++) {
			result += literal;
		}
		return result;
	}


	public static String generateRomnfive(int dec) {
		// generate fives |) = 50, |)) = 500 etc.
		String result = "|";
		for(int i=0; i<dec-2; i++) 
			result += "Ɔ";
		
		return result;
	}


	public static String generateRomanOne(int dec) {
		// generate ones (|) = 1000, ((|)) = 10000 etc.
		String result = "";
		for(int i=0; i<dec-3; i++) 
			result += "C";
		result += "|";
		for(int i=0; i<dec-3; i++) 
			result += "Ɔ";
		return result;
	}


	public static boolean isInt(String in) {
		boolean result = in.length()>0; // if no length; it's not an int.

		for(int i = 0; (i<in.length())&&(result); i++) { // break if we hit a non-number 
			char c = in.charAt(i);
			// set to false, if any char is NOT an int; the first char may be a minus
			result &= (c=='0')||(c=='1')
					||(c=='2')||(c=='3')
					||(c=='4')||(c=='5')
					||(c=='6')||(c=='7')
					||(c=='8')||(c=='9')
					||((i==0)&&(c=='-')); 
		}
		return result;
	}
	
	public static boolean isPosInt(String in) {
		boolean result = in.length()>0; // if no length; it's not an int.

		for(int i = 0; (i<in.length())&&(result); i++) { // break if we hit a non-number 
			char c = in.charAt(i);
			// set to false, if any char is NOT an int
			result &= ((c=='0')&&(i!=0)) // not zero
					||(c=='1')
					||(c=='2')||(c=='3')
					||(c=='4')||(c=='5')
					||(c=='6')||(c=='7')
					||(c=='8')||(c=='9');
		}
		return result;
	}


}
