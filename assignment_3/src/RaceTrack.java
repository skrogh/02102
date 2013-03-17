import java.awt.geom.Line2D;
import java.util.*;

/**
 * Main Class for playing "Vector Rally"
 * @author Søren Andersen, Carsten Nielsen
 *
 */
public class RaceTrack {
	public static final int CANVAS_WIDTH = 640, CANVAS_HEIGHT = 480;
	public static boolean debug;
	public static ArrayList<GameObject> gameObjects;
	public static ArrayList<Checkpoint> checkpoints;
	private static boolean game_over;	
	public static Map map;	
	public static Scanner console;

	public static void main( String args[] ) {
		debug = false;
		console = new Scanner( System.in );

		gameObjects = new ArrayList<GameObject>();
		checkpoints = new ArrayList<Checkpoint>();
		map = new Map();

		while ( true ) {
			game_over = false;
			setupMap( map );
            // setupGUI();
			System.out.println( "Game has started!" );

			// Game loop
			StdDraw.show(0);
			while ( !game_over ) {
				update();
				render();
			}

			// Game end, ask for rematch
			System.out.println( "Game has ended. Type \"more!\" to play again.\n" +
					"type anything else to be a wuz and quit." );
			if ( !console.nextLine().matches( "more!" ) )
				break;
		}

		console.close();
	}

	/**
	 * Call all GameObjects and tell them to render
	 */
	public static void render() {
		StdDraw.clear( StdDraw.WHITE );
		for ( GameObject gameObject : gameObjects ) {
			gameObject.render();
		}
		StdDraw.show(0);
	}

	/**
	 * Call all GameObjects and tell them to update
	 */
	public static void update() {
		// Key events:
		// Only send first key
		if ( StdDraw.hasNextKeyTyped() ) {
			int key = StdDraw.nextKeyTyped();
			for ( GameObject gameObject : gameObjects ) {
				gameObject.keyPressed( key );
			}
			if ( key == 'd' )
				debug = !debug;
		}
		// Clear buffer
		while ( StdDraw.hasNextKeyTyped() ) {
			StdDraw.nextKeyTyped();
		}

		// Position and collision update:
		for ( GameObject gameObject : gameObjects ) {
			gameObject.update();
		}

		// Collision checking:
		collisionCheck();
	}

	/**
	 * Setup the map from a Map objects
	 * @param map Map object to setup map from
	 */
	public static void setupMap( Map map ) {
		// reset lists
		gameObjects.clear();

		// setup canvas size:
		StdDraw.setCanvasSize( CANVAS_WIDTH, CANVAS_HEIGHT );

		// Scale "camera" to full map: Ungh this is messy, but such is the life of the SdtDraw library
		if (  map.getSize()[0] / (double) CANVAS_WIDTH > map.getSize()[1] / (double) CANVAS_HEIGHT ) {
			StdDraw.setXscale( 0, map.getSize()[0] );
			StdDraw.setYscale( map.getSize()[0] * CANVAS_HEIGHT / (double) CANVAS_WIDTH * 0.5 + map.getSize()[1] * 0.5,
					- map.getSize()[0] * CANVAS_HEIGHT / (double) CANVAS_WIDTH * 0.5 + map.getSize()[1] * 0.5);
		} else {
			StdDraw.setXscale( - map.getSize()[1] * CANVAS_WIDTH / (double) CANVAS_HEIGHT * 0.5 + map.getSize()[0] * 0.5,
					map.getSize()[1] * CANVAS_WIDTH / (double) CANVAS_HEIGHT * 0.5 + map.getSize()[0] * 0.5);
			StdDraw.setYscale( map.getSize()[1], 0 );
		}

		// Add grid
		gameObjects.add( new Grid( map.getSize()[0], map.getSize()[1] ) );

		// Add walls
		for ( int[] wall : map.getWalls() ) {
			gameObjects.add( new Wall( wall[0], wall[1], wall[2], wall[3] ) );
		}

		// Add checkpoints
		checkpoints.clear();
		for ( int[] checkpoint : map.getCheckpoints() ) {
			checkpoints.add( new Checkpoint( checkpoint[0], checkpoint[1], checkpoint[2], checkpoint[3] ) );
			gameObjects.add( checkpoints.get( checkpoints.size() - 1 ) );
		}

		gameObjects.add( new Player( map.getStart()[0], map.getStart()[1], checkpoints ) );
		
		if ( promptForToken( "Run \"AI\", type \"y\" for yes: ", "y", "Running \"AI\"", "Not running \"AI\"" ) )
			gameObjects.add( new AgentManager( map.getStart()[0], map.getStart()[1], 20000, checkpoints, gameObjects ) );

	}


	/**
	 * Do collision checking for all GameObjects.
	 */
	public static void collisionCheck() {
		for ( int i = 0; i < gameObjects.size(); i++ ) {
			for ( int j = i + 1; j < gameObjects.size(); j++ ) { // made s that for ex. 2 objects a, b only ab collision is checked and not ba, aa or bb
				if ( ( gameObjects.get(i).getId() != gameObjects.get(j).getId() ) && // If they are of same type (ex. two walls), no collision
						( gameObjects.get(i).getState() != GameObject.states.DEAD ) && // If either is dead, no collision
						( gameObjects.get(j).getState() != GameObject.states.DEAD ) && // ----||----
						gameObjects.get(i).isCollidable() && gameObjects.get(j).isCollidable() && // If either is uncollidable, no collision
						collideObjects( gameObjects.get(i), gameObjects.get(j) ) ) { // If the two collide
					gameObjects.get(i).collided( gameObjects.get(j) ); // Call the first, and tell it, it collided with the second
					gameObjects.get(j).collided( gameObjects.get(i) ); // And vice versa
				}
			}
		}
	}

	/**
	 * Check for collision between two objects
	 * @param obj1 Object #1
	 * @param obj2 Object #2
	 * @return Returns true if they collide
	 */
	public static boolean collideObjects( GameObject obj1, GameObject obj2 ) {
		// Check if any line in the colliosionBox from one object intersects with any other line in the colliosionBox from the other object
		for ( int[] side1 : obj1.getCollisionBox() ) {
			for ( int[] side2 : obj2.getCollisionBox() ) {
				if ( Line2D.linesIntersect( side1[0], side1[1], side1[2], side1[3],
						side2[0], side2[1], side2[2], side2[3] ) )
					return true;
			}
		}
		return false;
	}

	/**
	 * Called from Player, when "he" wins
	 * @param steps Steps it took to win
	 */
	public static void onWin( int steps ) {
		System.out.println( "You sir, or ma'am, win \"one million\" internetz!\n" +
				"Well not really; men du klarede det på:\n" +
				steps + " træk.");
		game_over = true;		
	}

	/**
	 * Called from Player, when "he" looses
	 * @param steps Steps it took to loose
	 */
	public static void onDeath( int steps ) {
		System.out.println( "Derp °_o\n" +
				"Du døde vist\n" +
				"Ej hvor flot du smadrede din \"vektor\" på bare:\n" +
				steps +" træk.");
		game_over = true;
	}


    public static void setupGUI() {
        Button testButton = new Button( 25, 25, 4, 4, "hello world!" );
        gameObjects.add( testButton );
    }
    
    /**
	 * Prompts the user for a token, If they write the correct one; true is returned
	 * 
	 * @param message Message to display to the user
	 * @param token Token to look for
	 * @param succes Message shown on token match
	 * @param falure Message to show on no match
	 * @return Returns true if match, else returns false
	 */
	public static boolean promptForToken( String message, String token, String succes, String falure ) {
		System.out.print( message );
		try {
			if ( console.nextLine().matches( token ) ) {
				System.out.println( succes );
				return true;
			} else {
				System.out.println( falure );
				return false;
			}
		} catch ( Exception e) {
			System.out.println( falure );
			return false;
		}
	}
	
}
