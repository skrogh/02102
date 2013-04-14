import java.awt.*;
import java.io.*;
import java.util.*;



public class ColorGenerator {

    public static Color[] generateRandomColorMap( int numColors ) throws IllegalArgumentException {
        if ( numColors <= 0 )
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

    public static Color generateRandomColor() {
        return new Color( (int)Math.random() * 255, (int)Math.random() * 255,
                (int)Math.random() * 255 ) ;
    }

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
