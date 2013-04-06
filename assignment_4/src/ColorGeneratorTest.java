import java.util.*;
import java.awt.*;


public class ColorGeneratorTest {


	public static void main(String[] args) {
		Color[] colors = ColorGenerator.generateRandomColorMap(50);
		for(int i = 0; i < 20; i++ ) {
			System.out.println( colors[ i ].getRed() + " " + colors[ i ].getBlue() + " " + colors[ i ].getGreen() );
		}

	}

}
