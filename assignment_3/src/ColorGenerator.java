import java.awt.*;
import java.util.*;

public class ColorGenerator {

	public static Color[] generateRandomColorMap( int numColors ) {
		Color[] colors = new Color[ numColors ];
		Hashtable<String, Color> colorTable = new Hashtable<String, Color>( numColors, 1 );
		
		for( int i = 0; i < numColors; i++ ) {
			int red = (int)Math.random() * 255;
			int green = (int)Math.random() * 255;
			int blue = (int)Math.random() * 255;
			
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

}
