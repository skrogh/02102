import java.awt.Color;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.out;


public class RandomWalk {
	public static final int WIDTH = 512;
	public static final int HEIGHT = 512;
	
	
	public static void main( String[] args ) {
		Scanner console = new Scanner( System.in );
		
		while(true)
		try {
			int[][] walk = generateWalk( 10 );
			drawWalk( min( walk, 1) );
			StdDraw.line(0, 0, 10, 10);
			StdDraw.show();
			if ( console.nextLine().matches( "exit" ) )
				break;
			drawWalk( walk );
			if ( console.nextLine().matches( "exit" ) )
				break;
			drawWalk( min( mapWalk( walk, WIDTH, HEIGHT ), 1 ) );
			if ( console.nextLine().matches( "exit" ) )
				break;
			drawWalk( mapWalk( walk, WIDTH, HEIGHT ) );
			if ( console.nextLine().matches( "exit" ) )
				break;
		}
		catch ( IllegalArgumentException e )  {
			out.println( "ERROR!" );
		}
		
		console.close();
		
		
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
				// Outcommented, cause hella slow!!
				// out.println("Position: (" + xPos + ", " + yPos + ")");
				hits[xPos][yPos]++;
			}
		}
		return hits;
	}
	
	
	public static void drawWalk( int[][] hits) {
		drawWalk( intArrayToDouble( hits ) );
	}
	
	


	public static void drawWalk( double [][] hits) {
		out.println( "size: (" + hits.length + ", " + hits[0].length + ")" );
		StdDraw.setXscale( 0 , hits.length );
	    StdDraw.setYscale( 0, hits[0].length );
	    StdDraw.setPenRadius( 0.01 / hits.length );
	    
	    double maxHits = max( hits );
	    out.println( "Maximum hits: " + maxHits );
	    
	    StdDraw.show(0); // Draw off-screen
	    StdDraw.clear();
	    for ( int i = 0; i < hits.length; i++ ) {
			for ( int j = 0; j < hits[0].length; j++ ) {
				if ( hits[i][j] > 0 ) {
					int value = 255 - (int) ( 255d * ( hits[i][j] / maxHits ) );
					StdDraw.setPenColor( new Color( value, value, value ) );
					StdDraw.filledSquare( i + 0.5, j + 0.5, 0.5);
					StdDraw.square( i + 0.5, j + 0.5, 0.5);
				}
			}
		}
	    StdDraw.show(); //Draw on-screen
		
	}
	
	// only works for downscaling
	public static double[][] mapWalk( int[][] hits, int xScale, int yScale  ) {
		double[][] result = new double[xScale][yScale];
		// Initialize result
		for (int i = 0; i<result.length; i++) {
			for (int j = 0; j<result[0].length; j++) {
				result[i][j] = 0;
			}
		}

		
		
		for ( int i = 0; i<hits.length; i++ ){
			for ( int j = 0; j<hits[0].length; j++ ){
				// Get coordinates in new coordinate system:
				double x = (double) i / hits.length * xScale;
				double y = (double) j / hits[0].length * yScale;
				
				// Get position of all four involved pixels in the new coordinate system 
				int[] pixelX = {
						(int) Math.max( Math.floor( x ), 0 ),
						(int) Math.min( Math.ceil( x ), xScale-1 ),
						(int) Math.min( Math.ceil( x ), xScale-1 ),
						(int) Math.max( Math.floor( x ), 0 )
						};
				int[] pixelY = {
						(int) Math.max( Math.floor( y ), 0 ),
						(int) Math.max( Math.floor( y ), 0 ),
						(int) Math.min( Math.ceil( y ), xScale-1 ),
						(int) Math.min( Math.ceil( y ), xScale-1 )
						};
				
				// Add value to involved pixels, but only to the ones, that are different
				// The first one is always unique
				result[pixelX[0]][pixelY[0]] += antiAlias( x, y, pixelX[0], pixelY[0] ) * hits[i][j];
				
				// If different from the first
				if ( ( pixelX[0] != pixelX[1] ) || ( pixelY[0] != pixelY[1] ) )
				result[pixelX[1]][pixelY[1]] += antiAlias( x, y, pixelX[1], pixelY[1] ) * hits[i][j];
				
				// If different from the first and second
				if ( ( ( pixelX[0] != pixelX[2] ) || ( pixelY[0] != pixelY[2] ) ) &&
						( ( pixelX[1] != pixelX[2] ) || ( pixelY[1] != pixelY[2] ) ) )
				result[pixelX[2]][pixelY[2]] += antiAlias( x, y, pixelX[2], pixelY[2] ) * hits[i][j];
				
				// If different from the first, second and third
				if ( ( ( pixelX[0] != pixelX[3] ) || ( pixelY[0] != pixelY[3] ) ) &&
						( ( pixelX[1] != pixelX[3] ) || ( pixelY[1] != pixelY[3] ) ) &&
						( ( pixelX[2] != pixelX[3] ) || ( pixelY[2] != pixelY[3] ) ) )
				result[pixelX[3]][pixelY[3]] += antiAlias( x, y, pixelX[3], pixelY[3] ) * hits[i][j];
			}	
		}
		
		return result;
	}
	
	public static void drawBlack(  ) {
		StdDraw.setXscale( 0 , WIDTH );
	    StdDraw.setYscale( 0, HEIGHT );
	    StdDraw.show(0); //Draw off-screen
	    for ( int i = 0; i<WIDTH; i++ ) {
	    	for ( int j = 0; j<HEIGHT; j++ ) {
	    		StdDraw.filledSquare( i, j, 0);
	    	}
	    }
	    StdDraw.show(); //Draw on-screen again
	    
	}
	
	
	/*
	 * Utility functions:
	 */
	
	/*
	 * Returns the distance between two points
	 */
	public static double distance( double x1, double y1, double x2, double y2 ) {
		return Math.sqrt( ( x1 - x2 ) * ( x1 - x2 ) + ( y1 - y2 ) * ( y1 - y2 ) );
	}
	
	/*
	 * Returns the anti-alias coeficien
	 */
	public static double antiAlias( double x1, double y1, double x2, double y2 ) {
		// Inaccurate, will have to be changed...
		return Math.max( 1 - distance( x1, y1, x2, y2 ), 0);
	}
	
	/*
	 * Return the maximum value in a 2D array
	 * Takes ints as inputs 
	 */
	public static int max( int[][] in ) {
		int result = 0;
		
		for (int i = 0; i<in.length; i++) {
			for (int j = 0; j<in[0].length; j++) {
				result = Math.max( result, in[i][j] );
			}
		}
		
		return result;
	}
	
	/*
	 * Return the maximum value in a 2D array
	 * Takes floats as inputs 
	 */
	public static double max( double[][] in ) {
		double result = 0;
		
		for (int i = 0; i<in.length; i++) {
			for (int j = 0; j<in[0].length; j++) {
				result = Math.max( result, in[i][j] );
			}
		}
		
		return result;
	}
	
	/*
	 * For each cell in an array
	 * returns the maximum value of those two in a 2D array
	 * Takes floats as inputs 
	 */
	public static double[][] max( double[][] in1, double[][] in2 ) {
		double[][] result = new double[in1.length][in1[0].length];
		
		for (int i = 0; i<in1.length; i++) {
			for (int j = 0; j<in1[0].length; j++) {
				result[i][j] = Math.max( in2[i][j], in1[i][j] );
			}
		}
		return result;
	}
	
	/*
	 * For each cell in an array
	 * returns the maximum value of the cell and a number
	 * as a 2D array
	 * Takes floats as inputs 
	 */
	public static double[][] max( double[][] in1, double in2 ) {
		double[][] result = new double[in1.length][in1[0].length];
		
		for (int i = 0; i<in1.length; i++) {
			for (int j = 0; j<in1[0].length; j++) {
				result[i][j] = Math.max( in2, in1[i][j] );
			}
		}
		return result;
	}
	
	/*
	 * For each cell in an array
	 * returns the maximum value of the cell and a number
	 * as a 2D array
	 * Takes floats as inputs 
	 */
	public static int[][] max( int[][] in1, int in2 ) {
		return DoubleArrayToInt( max( intArrayToDouble( in1 ), (double) in2 ) );
	}
	
	/*
	 * For each cell in an array
	 * returns the maximum value of those two in a 2D array
	 * Takes floats as inputs 
	 */
	public static double[][] min( double[][] in1, double[][] in2 ) {
		double[][] result = new double[in1.length][in1[0].length];
		
		for (int i = 0; i<in1.length; i++) {
			for (int j = 0; j<in1[0].length; j++) {
				result[i][j] = Math.min( in2[i][j], in1[i][j] );
			}
		}
		return result;
	}
	
	/*
	 * For each cell in an array
	 * returns the maximum value of the cell and a number
	 * as a 2D array
	 * Takes floats as inputs 
	 */
	public static double[][] min( double[][] in1, double in2 ) {
		double[][] result = new double[in1.length][in1[0].length];
		
		for (int i = 0; i<in1.length; i++) {
			for (int j = 0; j<in1[0].length; j++) {
				result[i][j] = Math.min( in2, in1[i][j] );
			}
		}
		return result;
	}
	
	/*
	 * For each cell in an array
	 * returns the maximum value of the cell and a number
	 * as a 2D array
	 * Takes floats as inputs 
	 */
	public static int[][] min( int[][] in1, int in2 ) {
		return DoubleArrayToInt( min( intArrayToDouble( in1 ), (double) in2 ) );
	}
	
	/*
	 * Typecast arrays
	 * 
	 */
	private static double[][] intArrayToDouble( int[][] hits ) {
		double[][] hitsD = new double[hits.length][hits[0].length];
		for ( int i = 0; i < hitsD.length; i++ ) {
			for ( int j = 0; j < hitsD[0].length; j++ ) {
				hitsD[i][j] = (double) hits[i][j];
			}
		}
		return hitsD;
	}
	
	private static int[][] DoubleArrayToInt( double[][] hits ) {
		int[][] hitsD = new int[hits.length][hits[0].length];
		for ( int i = 0; i < hitsD.length; i++ ) {
			for ( int j = 0; j < hitsD[0].length; j++ ) {
				hitsD[i][j] = (int) hits[i][j];
			}
		}
		return hitsD;
	}
	
}

//while(true) {	
//	int[][] hits = new int[512][512];
//	StdDraw.clear();
//	
//	for ( int i = 0; i < 512; i++ ){
//		for ( int j = 0; j < 512; j++ ) {
//			
//			
//			if ( ( i == (int) Math.round( StdDraw.mouseX() * (double) 512/WIDTH ) ) &&
//						( j == (int) Math.round( StdDraw.mouseY() * (double) 512/HEIGHT ) )	) 							hits[i][j] = 1;
//			else
//				hits[i][j] = 0;
//		}
//	}
//	drawWalk( mapWalk( hits, WIDTH, HEIGHT ) );
//	try { Thread.sleep( 100 ); }
//    catch (InterruptedException e) { System.out.println("Error sleeping"); }
//}
