import java.util.*;

public class RaceTrack {

	public static ArrayList<GameObject> gameObjects;
	private static boolean game_over;	
	
	public static void main( String args[] ) {
		Scanner console = new Scanner( System.in );
		
		while ( true ) {
			gameObjects = new ArrayList<GameObject>();
			game_over = false;
			setupMap( new Map() );
			
			System.out.println( "Game has started!" );
			// Game loop
			StdDraw.show(0);
			while ( !game_over ) {
				update();
				render();
			}
			System.out.println( "Game has ended. Type \"more!\" to play again.\n" +
					"type anything else to be a wuz and quit." );
			if ( !console.nextLine().matches( "more!" ) )
				break;
			
		}
		
		console.close();
	}
	
	public static void render() {
		StdDraw.clear( StdDraw.WHITE );
		for ( GameObject gameObject : gameObjects ) {
			gameObject.render();
		}
		StdDraw.show(0);
	}
	
	public static void update() {
		// key events:
		// only send first key
		if ( StdDraw.hasNextKeyTyped() ) {
			int key = StdDraw.nextKeyTyped();
			for ( GameObject gameObject : gameObjects ) {
				gameObject.keyPressed( key );
			}
		}
		// clear buffer
		while ( StdDraw.hasNextKeyTyped() ) {
			StdDraw.nextKeyTyped();
		}
		
		// position and collision update:
		for ( GameObject gameObject : gameObjects ) {
			gameObject.update();
		}
		
		// collision checking:
		collisionCheck();
	}
	
	public static void setupMap( Map map ) {
		// setup canvas size:
		StdDraw.setCanvasSize( 640, 480 );
		StdDraw.setXscale( 0, map.getSize()[0] );
		StdDraw.setYscale( map.getSize()[1], 0 );
		
		// add walls
		for ( int[] wall : map.getWalls() ) {
			gameObjects.add( new Wall( wall[0], wall[1], wall[2], wall[3] ) );
		}
		
		// add checkpoints
		ArrayList<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
		for ( int[] checkpoint : map.getCheckpoints() ) {
			checkpoints.add( new Checkpoint( checkpoint[0], checkpoint[1], checkpoint[2], checkpoint[3] ) );
			gameObjects.add( checkpoints.get( checkpoints.size() - 1 ) );
		}
		
		gameObjects.add( new Player( map.getStart()[0], map.getStart()[1], checkpoints ) );
		
	}
	
	public static void collisionCheck() {
		for ( int i = 0; i < gameObjects.size(); i++ ) {
			for ( int j = i + 1; j < gameObjects.size(); j++ ) {
				if ( ( gameObjects.get(i).getId() != gameObjects.get(j).getId() ) &&
						gameObjects.get(i).isCollidable() && gameObjects.get(j).isCollidable() &&
						collideObjects( gameObjects.get(i), gameObjects.get(j) ) ) {
					gameObjects.get(i).collided( gameObjects.get(j) );
					gameObjects.get(j).collided( gameObjects.get(i) );
				}
			}
		}
	}
	
	private static boolean lineLineCollision( double x00, double y00, double x01, double y01,
			double x10, double y10, double x11, double y11) {
		
		double nom = -x11 * y01 + x11 * y00 + x10 * y01 - x10 * y00 +
				y11 * x01 - y11 * x00 - y10 * x01 + y10 * x00;
		if ( nom == 0 ) {
			return false;
		} else {
			double t = x11 * y00 + y11 * x10 - x11 * y10 - x10 * y00 - y11 * x00 + y10 * x00;
			double s = x10 * y01 - x10 * y00 + y10 * x00 + y00 * x01 - y10 * x01 - x00 * y01;
			t /= nom;
			s /= nom;
			if ( ( t >= 0 ) && ( t <= 1 ) && ( s >= 0 ) && ( s <= 1 ) )
				return true;
			else
				return false;
		}
	}
	
	private static boolean collideObjects( GameObject obj1, GameObject obj2 ) {
		for ( int[] side1 : obj1.getCollisionBox() ) {
			for ( int[] side2 : obj2.getCollisionBox() ) {
				if ( lineLineCollision( side1[0], side1[1], side1[2], side1[3],
						side2[0], side2[1], side2[2], side2[3] ) )
					return true;
			}
		}
		return false;
	}
	
	public static void onWin( int steps ) {
		System.out.println( "You sir, or ma'am, win \"one million\" internetz!\n" +
				"Well not really; men du klarede det på:\n" +
				steps + " træk.");
		game_over = true;
	}
	public static void onDeath( int steps ) {
		System.out.println( "Derp °_o\n" +
				"Du døde vist\n" +
				"Ej hvor flot du smadrede din \"vektor\" på bare:\n" +
				steps +" træk.");
		game_over = true;
	}

}
