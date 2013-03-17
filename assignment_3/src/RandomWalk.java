import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;


public class RandomWalk {
	public static int canvasSize = 512;
	public static Scanner scanner;


	public static void main( String[] args ) {
		scanner = new Scanner( System.in );
		StdDraw.show(0); // Disable automatic update
		while(true)
			try {
				canvasSize = promtForPosInt( "Type size for canvas: " );
				StdDraw.setCanvasSize( canvasSize, canvasSize );
				int walkSize = promtForPosInt( "Type size of walk: " );
				drawWalk( walkSize, false );
				StdDraw.show(0);
				if ( scanner.nextLine().matches( "exit" ) )
					break;
			}
		catch ( IllegalArgumentException e )  {
			System.out.println( "ERROR!" );
		}

		scanner.close();


	}


	public static void drawWalk( int size, boolean debug ) {
		int xPos = 0;
		int yPos = 0;
		Random random = new Random();
		
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
	
	public static int promtForInt( String message ) {
		System.out.print( message );
		try {		
			return scanner.nextInt();
		} catch ( InputMismatchException  e ) {
			return promtForInt( message );
		} catch ( Exception e) {
			return 1;
		}
	}
	
	public static int promtForPosInt( String message ) {
			int got = promtForInt( message );
			if ( got > 0 )
				return got;
			else
				return promtForPosInt( message );
	}
}
