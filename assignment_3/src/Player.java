
public class Player extends GameObject {
	
	protected int velX;
	protected int velY;
	
	protected int tileSize;
	
	public Player() {
		this( 0, 0, 0, 0, 16 );
	}
	
	public Player( int xTile, int yTile, int velX, int velY, int tileSize ) {
		this.velX = velX;
		this.velY = velY;
		this.xTile = xTile;
		this.yTile = yTile;
		this.tileSize = tileSize;
		this.xPos = xTile * tileSize + tileSize / 2;
		this.yPos = yTile * tileSize + tileSize / 2;
	}
	
	public void update() {
		
	}
	
	public void render() {
		StdDraw.setPenColor( StdDraw.BLACK );
		StdDraw.filledCircle( xPos, yPos, 4 );
	}
	
	
}
