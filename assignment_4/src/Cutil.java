//============================================
//Cutil
// Custom utilities for common tasks in 02102
// Carsten Nielsen s123161 & Søren Krogh 
// Andersen s123369
//============================================

import java.io.*;
import java.util.*;

public class Cutil {
    
    public enum option { NEGATIVE, POSITIVE };

    //================================================
    // Cutil - returns accepted string
    // Matches console input with acceptor list
    // and returns accepted string if found
    //===============================================
    public static String promptString( String promptString, String[] acceptors ) {
        Scanner inputScanner = new Scanner( System.in );
        String acceptedString = "";
        String inputString = inputScanner.next();

        for( int i = 0; i < acceptors.length; i++ ) {
            if( inputString.equals( acceptors[ i ] ) ) {
                acceptedString = inputString;
                break;
            }
        }
        inputScanner.close();
        return acceptedString;
    }

    public static int promptInt( String promptString,  int min, int max ) throws IllegalArgumentException {
        Scanner inputScanner = new Scanner( System.in );
        System.out.println( promptString );

        int inputInt = inputScanner.nextInt();
        inputScanner.close();

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
    
    public static double promptDouble( String promptString,  double min, double max ) throws IllegalArgumentException {
        Scanner inputScanner = new Scanner( System.in );
        System.out.println( promptString );

        double inputDouble = inputScanner.nextDouble();
        inputScanner.close();

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
    
    public static int[][] fileToIntArray( String filename, int columns ) throws IOException {
    	try {
    		int lines = countFileLines( filename );
    		int[][] datamatrix = new int[ lines ][ columns ];
    		
    		Scanner lineReader = new Scanner( new File( filename ) );
    		String line = "";
    		Scanner lineDissector = new Scanner( line );
    		for( int i = 0; i < lines; i++ ) {
    			line = lineReader.nextLine();
    			for( int j = 0; j < columns; j++ ) {
    				datamatrix[ i ][ j ] = lineDissector.nextInt();
    			}
    		}
    		
    		lineReader.close();
    		lineDissector.close();
    		
    		return datamatrix.clone();
    	}
    	catch( IOException ex ) {
    		throw ex;
    	}
    	
    }
    
    public static int countFileLines( String filename ) throws IOException {
    	
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
