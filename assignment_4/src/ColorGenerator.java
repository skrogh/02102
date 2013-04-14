//============================================
//ColorGenerator
// Generates arrays of colors randomly or
// from a file using RGB values
// Carsten Nielsen s123161 & Søren Krogh 
// Andersen s123369
//============================================

import java.awt.*;
import java.io.*;
import java.util.*;



public class ColorGenerator {
    
    //========================================
    // generateRandomColorMap
    // Generates a number of unique random
    // colors. Very slow for a very large 
    // number of colors.
    // Returns colors in an array
    // ======================================
    public static Color[] generateRandomColorMap( int numColors ) throws IllegalArgumentException {
        //throw exception if number of colors is too small
        //or so large that runtime will be infinite
        if ( numColors <= 0  || numColors >= 16581375 - 1)
            throw new IllegalArgumentException();

        Color[] colors = new Color[ numColors ];
        Hashtable<String, Color> colorTable = new Hashtable<String, Color>( numColors, 1 );
        //very large runtimes for very large numColors
        //but duplicate colors are avoided
        for( int i = 0; i < numColors; i++ ) {
            int red = (int)(Math.random() * 255);
            int green = (int)(Math.random() * 255);
            int blue = (int)(Math.random() * 255);
            String keyString = red + "" + green + "" + blue;
            if ( colorTable.containsKey( keyString ) ) {
                //color already generated, repeat step
                i--;
            }
            else {
                colorTable.put( keyString, new Color( red, green, blue ) );	
            }
        }

        Enumeration keys = colorTable.keys();
        int i = 0;
        while( keys.hasMoreElements() ) {
            if ( i < numColors ) {
                colors[ i ] = colorTable.get( keys.nextElement() );
                i++;
            }
            else 
                break;
        }
        return colors.clone();
    }
    
    //============================================
    // generateRandomColor - generates a single
    // random color and returns it
    // ==========================================
    public static Color generateRandomColor() {
        return new Color( (int)Math.random() * 255, (int)Math.random() * 255,
                (int)Math.random() * 255 ) ;
    }

    //============================================
    // generateColorMapFromFile - generates a
    // color map corresponding to RGB values in
    // a text file. Returns an array with the
    // colors.
    // ==========================================
    public static Color[] generateColorMapFromFile( String filename ) throws java.io.IOException {
        try {
            int[][] datamatrix = Cutil.fileToIntArray( filename, 3 );
            Color[] colors = new Color[ datamatrix.length ];
            for( int i = 0; i < datamatrix.length; i++ ) {
                colors[ i ] = new Color( datamatrix[ i ][ 0 ], datamatrix[ i ][ 1 ], datamatrix[ i ][ 2 ] );
            }

            return colors.clone();
        }
        catch( IOException ex ) {
            throw ex;
        }
    }
}
