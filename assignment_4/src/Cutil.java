//============================================
//Cutil
// Custom utilities for common tasks in 02102
// Carsten Nielsen s123161 & Søren Krogh 
// Andersen s123369
//============================================

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
}
