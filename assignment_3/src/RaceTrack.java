import java.util.*;

public class RaceTrack {

	public static ArrayList<GameObject> gameObjects;
	
	public static void main( String args[] ) {
		gameObjects = new ArrayList<GameObject>();
		
		setupMap( new Map() );
		
		// Game loop
		StdDraw.show(0);
		while (true) {
			update();
			render();
		}
	}
	
	public static void render() {
		for (int i = 0; i < gameObjects.size(); i++ ) {
			gameObjects.get( i ).render();
		}
		StdDraw.show(0);
	}
	
	public static void update() {
		for (int i = 0; i < gameObjects.size(); i++ ) {
			gameObjects.get( i ).update();
		}
	}
	
	public static void setupMap( Map map ) {
		// setup canvas size:
		StdDraw.setCanvasSize( map.getSize()[0], map.getSize()[1] );
		StdDraw.setXscale( 0, map.getSize()[0] );
		StdDraw.setYscale( map.getSize()[1], 0 );
		
		// add walls
		for ( int[] wall : map.getWalls() ) {
			gameObjects.add( new Wall( wall[0], wall[1], wall[2], wall[3] ) );
		}
	}
	
}
