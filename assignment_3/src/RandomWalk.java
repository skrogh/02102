import java.util.Random;
import java.util.Scanner;


public class RandomWalk {
	public static int canvasSize = 512;
	public static Scanner scanner;
	
	/**
	 * Prompts the user for a canvas size and walk size; alongside with some options,
	 * then draws a random walk (pseudo brownian movement) to the canvas.
	 * Is dependent on the StdDraw library.
	 * 
	 * @param args Not used
	 */
	public static void main( String[] args ) {
		scanner = new Scanner( System.in );
		StdDraw.show(0); // Disable automatic re-draw
		while(true) {
			canvasSize = promptForPosInt( "Type size for canvas: ", 512 );
			StdDraw.setCanvasSize( canvasSize, canvasSize );
			drawWalk( promptForPosInt( "Type size of walk: ", 50 ),
					promptForToken( "Debug mode? \"y\" for yes: ", "y", "Entering debug mode", "Entering regular mode" ) );
			StdDraw.show(0); // Re-draw
			if ( promptForToken( "Type \"exit\" to stop, anything else to draw a new walk: ", "exit", "Quitting", "Draw new walk" ) )
				break;
		}
		
		scanner.close();
		System.out.println( "Program terminated" );
	}

	/**
	 * Draws a random walk with a given size.
	 * In debug mode the position of the particle is printed to the console,
	 * and the window is refreshed for every step.
	 * DON'T DO THIS FOR LONG WALKS!
	 * @param size Size from the center to any edge. ex. 5 will result in a grid of 9x9.
	 * @param debug true for debug-mode.
	 */
	public static void drawWalk( int size, boolean debug ) {
		int xPos = 0;
		int yPos = 0;
		Random random = new Random();
		
		//setup canvas and pen size
		StdDraw.setXscale( -size , size );
		StdDraw.setYscale( -size , size );
		StdDraw.setPenRadius( canvasSize / 1200d / size );

		StdDraw.clear();
		StdDraw.setPenColor( StdDraw.BLACK );
		
		while ( true ) {
			// Move
			switch ( random.nextInt( 4 ) ) {
			case 0:
				xPos++;
				break;
			case 1:
				xPos--;
				break;
			case 2:
				yPos++;
				break;
			case 3:
				yPos--;
				break;
			}
			// Exit loop, if left window
			if ( ( Math.abs(xPos) > size ) || ( Math.abs(yPos) > size ) )
				break;
			
			StdDraw.point( xPos, yPos );
			
			if ( debug ) {
				StdDraw.show( 0 );
				System.out.println( "Position: (" + xPos + ", " + yPos + ")" );
			}
		}

	}
	
	/**
	 * Prompts the user for an integer 
	 * 
	 * @param message Message to show the user
	 * @param def Default value
	 * @return Returns the typed int, if possible; else returns "def"
	 */
	public static int promptForInt( String message, int def ) {
		System.out.print( message );
		try {
			return Integer.parseInt( scanner.nextLine() );
		} catch ( Exception e) {
			System.out.println( "This is not an integer, using default of: " + def );
			return def;
		}
	}
	
	/**
	 * Prompts the user for a positive integer 
	 * 
	 * @param message Message to show the user
	 * @param def Default value
	 * @return Returns the typed int, if possible; else returns "def"
	 */
	public static int promptForPosInt( String message, int def ) {
			int got = promptForInt( message, def );
			if ( got > 0 )
				return got;
			else {
				System.out.println( "This is not a positive integer, using default of: " + def );
				return def;
			}
	}
	
	/**
	 * Prompts the user for a token, If they write the correct one; true is returned
	 * 
	 * @param message Message to display to the user
	 * @param token Token to look for
	 * @param succes Message shown on token match
	 * @param falure Message to show on no match
	 * @return Returns true if match, else returns false
	 */
	public static boolean promptForToken( String message, String token, String succes, String falure ) {
		System.out.print( message );
		try {
			if ( scanner.nextLine().matches( token ) ) {
				System.out.println( succes );
				return true;
			} else {
				System.out.println( falure );
				return false;
			}
		} catch ( Exception e) {
			System.out.println( falure );
			return false;
		}
	}
	
}
