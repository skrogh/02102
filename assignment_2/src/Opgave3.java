/*
 * =============================================
 * Carsten Nielsen og Søren Krogh Andersen
 * Approximates pi by "throwing Buffon's Needle"
 * a number of times as specified by a user.
 * =============================================
 */

import java.util.*;

public class Opgave3 {
	
	public static boolean stopProgram;
	public static Scanner inputScanner;
	
	/*
	 * ================================================
	 * Main program loop, continuously approximates PI
	 * by throwing Buffon's a number of times entered by
	 * the user.
	 * 
	 * ==================================================
	 */
	
	public static void main( String[] args ) {
		stopProgram = false;
		int iterations = 1;
		int numberOfHits = 0;
		inputScanner = new Scanner(System.in);
		System.out.println("Type 'quit' to quit");
		while ( !stopProgram )  {
			iterations = getPositiveInt();
			if ( stopProgram ) 
				break;
			
			numberOfHits = throwNeedle( iterations );
			
			if ( numberOfHits == 0 ) {
				System.out.println( "No hits, try a larger number" );
				continue;
			}
			
			double piApprox = (double)iterations / (double)numberOfHits;
			double absDeviation = Math.abs( piApprox - Math.PI );
			System.out.println( "Result: iterations = " + iterations + "\n Hits = " + numberOfHits
					+ "\n Ratio = " + piApprox + "\n Distance to Math.PI: " + absDeviation );
			
		}
		
	}
	
	/*
	 * =========================================
	 * throwNeedle:
	 * Takes number of iterations as input.
	 * Returns number of line crossings.
	 * Simulates a needle throw by calculating
	 * a center between two lines and determining
	 * if the needle crosses the closest line
	 * =========================================
	 */
	public static int throwNeedle( int iterations ) {
		double needleLength = 1.0d;
		double lineSpacing = 2.0d;
		
		double needleAngle = 0.0d;
		double needleCenter = 0.0d;
		double needleEndPoint = 0.0d;
		
		int numberOfHits = 0;

		for ( int i=0; i<iterations; i++ ) {
			needleCenter = Math.random() * lineSpacing;
			needleAngle = Math.random() * Math.PI/2.0f;
			
			if( lineSpacing - needleCenter < Math.cos( needleAngle ) * needleLength/2 ||
					needleCenter - Math.cos( needleAngle ) * needleLength/2 < 0 )
				numberOfHits++;
		}
		
		return numberOfHits;
	}
	
	/*
	 * ============================
	 * getPositiveInt:
	 * Returns a positive integer 
	 * entered by a user. Also 
	 * performs input validation.
	 * ============================
	 */
	public static int getPositiveInt() {
		
		System.out.print( "Enter a positive number of iterations: " );
		int iterations = 1;
		inputScanner = new Scanner(System.in);
		
		try {
			iterations = inputScanner.nextInt();
		} catch( Exception InputMismatchException ) {
			if ( inputScanner.next().equals("quit") )
				stopProgram = true;
			else
				iterations = getPositiveInt();
		}
		
		if ( iterations <=0 )
			iterations = getPositiveInt();
		
		return iterations;
	}
	

}
