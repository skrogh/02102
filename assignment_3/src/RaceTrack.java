import java.util.*;

public class RaceTrack {

	public static ArrayList<GameObject> gameObjects;
	
	public static void main( String args[] ) {
		gameObjects = new ArrayList<GameObject>();
		StdDraw.setCanvasSize( 640, 480 );
		StdDraw.setXscale( 32 / 1.1, 640 - ( 32 / 1.1 ) );
		StdDraw.setYscale( 480 - ( 24 / 1.1 ), 24 / 1.1 );
		StdDraw.line(0, 0, 1, 1);
		StdDraw.show(0);
		Map map = new Map();
		gameObjects.add( map );
		Player player = new Player( 10, 3, 0, 0, 16 );
		gameObjects.add( player );
		render();
	}
	
	public static void render() {
		StdDraw.show( 0 );
		
		for (int i = 0; i < gameObjects.size(); i++ ) {
			gameObjects.get( i ).render();
		}
		
		StdDraw.show( 0 );
	}
}
