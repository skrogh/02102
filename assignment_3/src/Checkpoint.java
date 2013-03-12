import java.util.*;

public class Checkpoint extends GameObject {
	
	protected int xEnd, yEnd;
	
	
	public Checkpoint( int x0, int y0, int x1, int y1 ) {
		ID = IDs.CHECKPOINT;
		collidable = true;
		xPos = x0;
		yPos = y0;
		xEnd = x1;
		yEnd = y1;
		collisionBox = new ArrayList<int[]>();
		collisionBox.add( new int[]{xPos, yPos, xEnd, yEnd} );
	}
	
	public void render() {
		StdDraw.setPenColor( StdDraw.BLUE );
		StdDraw.setPenRadius( 0.003 );
		StdDraw.line( xPos, yPos, xEnd, yEnd );
	}

}
