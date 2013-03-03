/*
 * =============================================
 * Carsten Nielsen og Søren Krogh Andersen
 * Approximates pi by "throwing Buffon's Needle"
 * a number of times as specified by a user.
 * =============================================
 */

import java.util.*;

public class BuffonsNeedle {
	
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
		long iterations = 1;
		long numberOfHits = 0;
		inputScanner = new Scanner(System.in);
		System.out.println( "Type 'exit' to exit" );
		
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
			System.out.println( "Result: \n \titerations = " + iterations + "\n\tHits = " + numberOfHits
					+ "\n\tRatio = " + piApprox + "\n\tDistance to Math.PI: " + absDeviation );
			
		}
		
		System.out.println("Done!");
		
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
	public static long throwNeedle( long iterations ) {
		double needleLength = 1.0d;
		double lineSpacing = 2.0d;
		
		double needleAngle = 0.0d;
		double needleCenter = 0.0d;
		
		long numberOfHits = 0;

		for ( long i=0; i<iterations; i++ ) {
			needleCenter = Math.random() * lineSpacing;
			needleAngle = Math.random() * Math.PI/2.0f;
			
			if( lineSpacing - needleCenter <= Math.cos( needleAngle ) * needleLength/2 ||
					needleCenter <= Math.cos( needleAngle ) * needleLength/2 )
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
			if ( inputScanner.next().equals("exit") )
				stopProgram = true;
			else
				iterations = getPositiveInt();
		}
		
		if ( iterations <=0 )
			iterations = getPositiveInt();
		
		return iterations;
	}
	

}
