import java.awt.Color;
import java.io.IOException;
import java.util.Random;
import static java.lang.System.out;


public class RandomWalk {
	public static void main( String[] args ) {
		try {
			drawWalk( generateWalk( 200 ) );
		}
		catch ( IllegalArgumentException e )  {
			out.println( "ERROR!" );
		}
		
		
	}


	public static int[][] generateWalk( int gridSize ) throws IllegalArgumentException { 
		// Throw exception if grid is nonexistent.
		if ( gridSize < 1) 
			throw new IllegalArgumentException(
					"Error: Gridsize in 'genereateWalk' must be >1");

		int[][] hits = new int[gridSize*2 + 1][gridSize*2 + 1];
		int xPos = gridSize, yPos = gridSize;
		boolean hit = false;
		Random random = new Random();
		

		// Initialize hits
		for (int i = 0; i<hits.length; i++) {
			for (int j = 0; j<hits[0].length; j++) {
				hits[i][j] = 0;
			}
		}

		// Add starting position
		hits[xPos][yPos] += 1;

		// Do the walk! (of shame?)
		while( !hit ) {
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
			if ( ( xPos < 0 ) || ( xPos > gridSize*2 ) ||
					( yPos < 0 ) || ( yPos > gridSize*2 ) )
				hit = true;
			else {
				out.println("Position: (" + xPos + ", " + yPos + ")");
				hits[xPos][yPos]++;
			}
		}
		return hits;
	}
	
	public static void drawWalk( int[][] hits) {
		out.println( "size: (" + hits.length + ", " + hits[0].length + ")" );
		StdDraw.setXscale( 0 , hits.length );
	    StdDraw.setYscale( 0, hits[0].length );
	    StdDraw.setPenRadius( 0.01 / hits.length );
	    
	    int maxHits = max( hits );
	    out.println( "Maximum hits: " + maxHits );
	    
	    for (int i = 0; i<hits.length; i++) {
			for (int j = 0; j<hits[0].length; j++) {
				if ( hits[i][j] > 0 ) {
					int value = 255 - (int) ( 255d * ( hits[i][j] / (double) maxHits ) );
					//StdDraw.setPenColor( new Color( value, value, value ) );
					StdDraw.filledSquare( i + 0.5, j + 0.5, 0.5);
					StdDraw.square( i + 0.5, j + 0.5, 0.5);
				}
			}
		}
		
	}
	
	public static int max( int[][] in ) {
		int result = 0;
		
		for (int i = 0; i<in.length; i++) {
			for (int j = 0; j<in[0].length; j++) {
				result = Math.max( result, in[i][j] );
			}
		}
		
		return result;
	}
	
}
