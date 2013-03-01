import java.util.Scanner;



/***************************************************************
 * Progamming assignment 2, problem 1
 * Carsten Lau Nielsen and Søren Krog Andersen
 *
 */


public class Opgave1 {
	
	private static boolean getNumbers=true;
	
	public static void main(String[] args) {
		int numberToConvert;
		while(getNumbers) {
			System.out.println("Enter a positive integer followed by ENTER for conversion to roman numerals");
			numberToConvert = getNumber();
			System.out.println(convertToRoman(numberToConvert));
			
		}
	}
	
	public static int getNumber() {
		int recievedNumber;
		Scanner inputScanner = new Scanner(System.in);
		
		recievedNumber = inputScanner.nextInt();
		
		return recievedNumber;
	}
	
	public static String convertToRoman(int number) {
		String convertedNumber = "";
		convertedNumber+=(convertThousands(number));
		convertedNumber+=(convertHundreds(number));
		convertedNumber+=(convertTens(number));
		convertedNumber+=(convertOnes(number));
		
		return convertedNumber;
	}
	
	public static String convertThousands(int number) {
		int thousands = number/1000;
		String convertedThousands = "";
		
		for(int i=1; i<=thousands; i++)
			convertedThousands+="M";
		
		return convertedThousands;
	}
	
	public static String convertHundreds(int number) {
		int hundreds = number%1000;
		hundreds = hundreds/100;
		
		String convertedHundreds = "";
		if(hundreds==9) 
			convertedHundreds = "CM";
		else if(hundreds>=5) {
			convertedHundreds = "D";
			for(int i = 1; i<=hundreds-5; i++) 
				convertedHundreds+="C";
		}
		else if(hundreds==4)
			return convertedHundreds = "CL";
		else if(hundreds>=0) {
			for(int i = 1; i<=hundreds; i++) 
				convertedHundreds+="C";
		}
		
		return convertedHundreds;
	}
	
	public static String convertTens(int number) {
		int tens = number%100;
		tens = tens/10;
		
		String convertedTens = "";
		if(tens==9) 
			convertedTens = "XC";
		else if(tens>=5) {
			convertedTens = "L";
			for(int i = 1; i<=tens-5; i++) 
				convertedTens+="X";
		}
		else if(tens==4)
			return convertedTens = "XL";
		else if(tens>=0) {
			for(int i = 1; i<=tens; i++) 
				convertedTens+="X";
		}
		
		return convertedTens;
	}
	
	public static String convertOnes(int number) {
		int ones = number%10;
		String convertedOnes = "";
		if(ones==9) 
			convertedOnes = "IX";
		else if(ones>=5) {
			convertedOnes = "V";
			for(int i = 1; i<=ones-5; i++) 
				convertedOnes+="I";
		}
		else if(ones==4)
			return convertedOnes = "IV";
		else {
			for(int i = 1; i<=ones; i++) 
				convertedOnes+="I";
		}
		
		
		return convertedOnes;
	}
}
