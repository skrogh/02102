import java.awt.geom.Line2D;
import java.util.*;

public class RaceTrack {
	public static final int CANVAS_WIDTH = 640, CANVAS_HEIGHT = 480;
	public static boolean debug;
	public static ArrayList<GameObject> gameObjects;
	public static ArrayList<Checkpoint> checkpoints;
	private static boolean game_over;	
	public static Map map;	
	
	public static void main( String args[] ) {
		debug = false;
		Scanner console = new Scanner( System.in );
		
		while ( true ) {
			gameObjects = new ArrayList<GameObject>();
			checkpoints = new ArrayList<Checkpoint>();
			game_over = false;
			map = new Map();
			setupMap( map );
            setupGUI();
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
			if ( key == 'd' )
				debug = !debug;
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
		// reset lists
		gameObjects = new ArrayList<GameObject>();
		
		// setup canvas size:
		StdDraw.setCanvasSize( CANVAS_WIDTH, CANVAS_HEIGHT );
		
		// Scale "camera" to full map:
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
		checkpoints = new ArrayList<Checkpoint>();
		for ( int[] checkpoint : map.getCheckpoints() ) {
			checkpoints.add( new Checkpoint( checkpoint[0], checkpoint[1], checkpoint[2], checkpoint[3] ) );
			gameObjects.add( checkpoints.get( checkpoints.size() - 1 ) );
		}
		
		gameObjects.add( new Player( map.getStart()[0], map.getStart()[1], checkpoints ) );
		
		gameObjects.add( new AgentManager( map.getStart()[0], map.getStart()[1], 20000, checkpoints, gameObjects ) );
		
	}
	
	public static void collisionCheck() {
		for ( int i = 0; i < gameObjects.size(); i++ ) {
			for ( int j = i + 1; j < gameObjects.size(); j++ ) {
				if ( ( gameObjects.get(i).getId() != gameObjects.get(j).getId() ) &&
						( gameObjects.get(i).getState() != GameObject.states.DEAD ) &&
						( gameObjects.get(j).getState() != GameObject.states.DEAD ) &&
						gameObjects.get(i).isCollidable() && gameObjects.get(j).isCollidable() &&
						collideObjects( gameObjects.get(i), gameObjects.get(j) ) ) {
					gameObjects.get(i).collided( gameObjects.get(j) );
					gameObjects.get(j).collided( gameObjects.get(i) );
				}
			}
		}
	}
	
	public static boolean collideObjects( GameObject obj1, GameObject obj2 ) {
		for ( int[] side1 : obj1.getCollisionBox() ) {
			for ( int[] side2 : obj2.getCollisionBox() ) {
				if ( Line2D.linesIntersect( side1[0], side1[1], side1[2], side1[3],
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


    public static void setupGUI() {
        Button testButton = new Button( 25, 25, 4, 4, "hello world!" );
        gameObjects.add( testButton );
    }
}
