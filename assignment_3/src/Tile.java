
public class Tile extends GameObject {
	
	protected int tileSize;
	
	public Tile() {
		this( 0, 0, 16, states.ALIVE );
	}
	
	public Tile( int centerX, int centerY, int tileSize, states state ) {
		this.xPos = centerX;
		this.yPos = centerY;
		this.ID = IDs.TILE;
		this.tileSize = tileSize;
		this.state = state;
	}
	
	public void render() {
		
		if ( state == states.ALIVE ) {
			StdDraw.setPenColor( StdDraw.GRAY );
			StdDraw.filledSquare( xPos, yPos, tileSize / 2 );
			StdDraw.setPenColor( StdDraw.WHITE );
			StdDraw.square( xPos, yPos, tileSize / 2 );
			
		}
	}
	
	public void update()  {
		
	}
}
