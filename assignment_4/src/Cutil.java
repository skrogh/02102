//============================================
//Cutil
// Custom utilities for common tasks in 02102
// Carsten Nielsen s123161 & Søren Krogh 
// Andersen s123369
//============================================

import java.io.*;
import java.util.*;

public class Cutil {
	public static Scanner inputScanner = new Scanner( System.in );
    
	public enum option { NEGATIVE, POSITIVE };

	//================================================
	// promptStringSet - returns accepted string
	// Matches console input with acceptor list
	// and returns accepted string if found
	//===============================================
	public static String promptStringSet( String promptString, String[] acceptors ) {
		System.out.println( promptString );
		String acceptedString = "";
		String inputString = inputScanner.next();
		
		for( int i = 0; i < acceptors.length; i++ ) {
			if( inputString.equals( acceptors[ i ] ) ) {
				acceptedString = inputString;
				break;
			}
		}
		
		return acceptedString;
	}
    
    //=================================================
    // promptString - like promptStringSet, but
    // returns any string entered
    // ===============================================
	public static String promptString( String promptString ) {
		System.out.println( promptString ); 
		while ( !inputScanner.hasNext() );
		String inputString = inputScanner.next();
		return inputString;
	}

    //================================================
    // promptInt - returns an int entered in the
    // console in the range min - max
    // overloaded to take positive and negative ints
    // ==============================================
	public static int promptInt( String promptString,  int min, int max ) throws IllegalArgumentException {
		System.out.println( promptString );

		int inputInt = inputScanner.nextInt();

		if( inputInt > min && inputInt < max )
			return inputInt;
		else
			throw new IllegalArgumentException();

	}

	public static int promptInt( String promptString,  option option ) throws IllegalArgumentException {
		try {
			switch( option ) {
			case NEGATIVE:
				return promptInt( promptString,  - Integer.MAX_VALUE, 0 );
			case POSITIVE:
				return promptInt( promptString,  0, Integer.MAX_VALUE );
			default:
				throw new IllegalArgumentException();

			}
		}
		catch( IllegalArgumentException ex ) {
			throw ex;
		} 
	}
    
    //===============================================
    // promptDouble - like promptInt, only for 
    // doubles. Also overloaded to take negative
    // or positives only
    // =============================================
	public static double promptDouble( String promptString,  double min, double max ) throws IllegalArgumentException {
		System.out.println( promptString );

		double inputDouble = inputScanner.nextDouble();

		if( inputDouble > min && inputDouble < max )
			return inputDouble;
		else
			throw new IllegalArgumentException();

	}

	public static double promptDouble( String promptString,  option option ) throws IllegalArgumentException {
		try {
			switch( option ) {
			case NEGATIVE:
				return promptDouble( promptString,  - Double.MAX_VALUE, 0 );
			case POSITIVE:
				return promptDouble( promptString,  0, Double.MAX_VALUE );
			default:
				throw new IllegalArgumentException();

			}
		}
		catch( IllegalArgumentException ex ) {
			throw ex;
		} 
	}

    //======================================================
    // fileToIntArray - reads an int matrix in a file into
    // a 2-dimensional array and returns the array.
    // Overloaded to read jagged arrays
    // ====================================================
	public static int[][] fileToIntArray( String filename, int columns ) throws java.io.IOException {
		try {
			int[][]result = fileToIntArray( filename );
			for ( int i = 0; i < result.length; i++ ) {
				if ( result[i].length != columns )
					throw new InputMismatchException();
			}
			return result;
		}
		catch( IOException ex ) {
			throw ex;
		}
	}
    
	public static int[][] fileToIntArray( String filename ) throws java.io.IOException {
		try {
			//int lines = countFileLines( filename );
			ArrayList<ArrayList<Integer>> datamatrix = new ArrayList<ArrayList<Integer>>();

			Scanner lineReader = new Scanner( new File( filename ) );
			String line = "";
			while( lineReader.hasNextLine() ) {
				datamatrix.add( new ArrayList<Integer>() );
				line = lineReader.nextLine();
				Scanner lineDissector = new Scanner( line );
				while ( lineDissector.hasNext() ) {
					if ( lineDissector.hasNextInt() )
						datamatrix.get( datamatrix.size() - 1 ).add( lineDissector.nextInt() );
					else
						lineDissector.next(); //Ignore all non integers, allows for commenting in files :)
				}
				lineDissector.close();
			}

			lineReader.close();

			int[][] result = new int[datamatrix.size()][0];
			for ( int i = 0; i < datamatrix.size(); i++ ) {
				result[i] = new int[datamatrix.get( i ).size()];
				for ( int j = 0; j < result[i].length; j++ ) {
					result[i][j] = datamatrix.get( i ).get( j );
				}
			}

			return result;
		}
		catch( IOException ex ) {
			throw ex;
		}
	}
    
    //===================================================
    // countFileLines - returns the number of lines in
    // a file
    // =================================================
	public static int countFileLines( String filename ) throws java.io.IOException {

		try {
			Scanner lineCounter = new Scanner( new File( filename ) );
			int lines = 0;
			while ( lineCounter.hasNextLine() ) { 
				lineCounter.nextLine();
				lines++;
			}
			lineCounter.close();

			return lines;
		}
		catch( IOException ex ) {
			throw ex;
		}
	}


}
