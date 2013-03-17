import java.util.Random;
import java.util.Scanner;


public class RandomWalk {
	public static final int WIDTH = 512*9;
	public static final int HEIGHT = 512*9;


	public static void main( String[] args ) {
		Scanner console = new Scanner( System.in );
		StdDraw.setCanvasSize( WIDTH, HEIGHT );
		StdDraw.show(0); // Disable automatic update
		while(true)
			try {
				drawWalk( 500 );
				StdDraw.show(0);
				if ( console.nextLine().matches( "exit" ) )
					break;
			}
		catch ( IllegalArgumentException e )  {
			System.out.println( "ERROR!" );
		}

		console.close();


	}


	public static void drawWalk( int size ) {
		int xPos = 0;
		int yPos = 0;
		Random random = new Random();
		
		StdDraw.setXscale( -size , size );
		StdDraw.setYscale( -size , size );
		StdDraw.setPenRadius( 0.4 / size );

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
		}

	}
}
