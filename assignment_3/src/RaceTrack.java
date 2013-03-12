import java.util.*;

public class RaceTrack {

	public static ArrayList<GameObject> gameObjects;
	
	public static void main( String args[] ) {
		gameObjects = new ArrayList<GameObject>();
		
		Map map = new Map();
		gameObjects.add( map );
		Player player = new Player( 10, 3, 0, 0, 16 );
		gameObjects.add( player );
		render();
	}
	
	public static void render() {
		for (int i = 0; i < gameObjects.size(); i++ ) {
			gameObjects.get( i ).render();
		}
		
	}
}
