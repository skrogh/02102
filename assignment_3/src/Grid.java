import java.util.*;

public class Grid extends GameObject{
	
	
	public Grid( int width, int heigth) {
		xPos = width;
		yPos = heigth;
	}
	
	public void update() {
		
	}
	
	public void render() {
		StdDraw.setPenColor( StdDraw.BOOK_LIGHT_BLUE );
		StdDraw.setPenRadius( 0.0005 );
		for ( int i = 0; i <= xPos; i++ )
				StdDraw.line( i, 0, i, yPos );
		for ( int i = 0; i <= yPos; i++ )
			StdDraw.line( 0, i, xPos, i );
			
	}

	
	
	
}
