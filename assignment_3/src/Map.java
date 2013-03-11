import java.util.ArrayList;


public class Map extends GameObject {
	
	private int mapHeight;
	private int mapWidth;
	
	ArrayList<GameObject> tileList;
	
	protected int tileSize;
	
	public Map() {
		this( 640, 480 ); 
	}
	
	public Map( int mapWidth, int mapHeight ) {
		
		this.ID = IDs.MAP;
		tileSize = 16;
		
		this.mapHeight = mapHeight;
		this.mapWidth = mapWidth;
		
		tileList = new ArrayList<GameObject>();
		createMap();
		StdDraw.setCanvasSize( mapWidth, mapHeight );
	}
	
	public void update() {
		
	}
	
	public void render() {
		drawMap();
		
	}
	
	public void drawMap() {
		StdDraw.setPenColor( StdDraw.GRAY );
		
		for ( int i = 0; i < tileList.size(); i++ ) {
			tileList.get( i ).render();
		}
		
		StdDraw.setPenColor( StdDraw.GREEN );
		StdDraw.filledRectangle( tileSize * 10, tileSize * 2.5, 2, tileSize * 2.5 );
	}
	
	public void createMap() {
		int initialX = tileSize / 2;
		int initialY = tileSize / 2;
		
		for ( int i = 0; i < 20; i++ ) {
			int tileCenterY = initialY + i * tileSize;
			for ( int j = 0; j < 20; j++ ) {
				int tileCenterX = initialX + j * tileSize;
				if( i >= 5 && i <= 14 && j <= 14 && j >= 5 )
					continue;
					
				Tile nextTile = new Tile(tileCenterX, tileCenterY, tileSize, states.ALIVE );
				tileList.add( nextTile );
			}
		}
	}
}
