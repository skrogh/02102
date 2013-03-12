import java.util.*;

public class Wall extends GameObject {
	
	protected int xEnd, yEnd;
	
	
	public Wall( int x0, int y0, int x1, int y1 ) {
		xPos = x0;
		yPos = y0;
		xEnd = x1;
		yEnd = y1;
	}
	
	public void render() {
		StdDraw.setPenColor( StdDraw.BLACK );
		StdDraw.setPenRadius( 0.005 );
		StdDraw.line( xPos, yPos, xEnd, yEnd );
	}

}
