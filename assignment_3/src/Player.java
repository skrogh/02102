import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.*;


public class Player extends GameObject {
	
	private double cpDistance;

	ArrayList<int[]> path; // x, y, alive/dead
	ArrayList<Checkpoint> checkpoints; // stores all checkpoints. They are removed when passed. When empty, you win!

	int xSpeed, ySpeed, steps;
	
	public Player( int x, int y, ArrayList<Checkpoint> checkpoints ){
		ID = IDs.PLAYER;
		state = states.ALIVE;
		collidable = true;
		xPos = x;
		yPos = y;
		xSpeed = 0;
		ySpeed = 0;
		steps = 0;
		path = new ArrayList<int[]>();
		path.add( new int[]{xPos, yPos, 0} );
		path.add( new int[]{xPos, yPos, 0} );
		this.checkpoints = (ArrayList<Checkpoint>) checkpoints.clone();
		collisionBox = new ArrayList<int[]>();
		cpDistance = distanceToCheckpointSq( checkpoints.get( 0 ) );

	}
	
	public void render() {
		int[] pointP = path.get(0);
		for ( int[] point : path ) {
			if ( point[2] == 1 )
				StdDraw.setPenColor( StdDraw.RED );
			else
				StdDraw.setPenColor( StdDraw.BOOK_BLUE );
			StdDraw.setPenRadius( 0.007 );
			StdDraw.line( pointP[0], pointP[1], point[0], point[1] );
			pointP = point;
		}
		StdDraw.setPenColor( StdDraw.GREEN );
		StdDraw.setPenRadius( 0.01 );
		StdDraw.point( xPos, yPos );
	}
	
	public void update() {
		collisionBox = new ArrayList<int[]>();
		collisionBox.add( new int[]{xPos, yPos,
				path.get( path.size() - 2 )[0], path.get( path.size() - 2 )[1]} );
	}
	
	public void movePlayer( int direction ) {
		switch ( direction ) {
		case 1: //DOWN LEFT
			xSpeed--;
			ySpeed++;
			break;
		case 2: //DOWN
			ySpeed++;
			break;
		case 3: //DOWN RIGHT
			xSpeed++;
			ySpeed++;
			break;
		case 4: //LEFT
			xSpeed--;
			break;
		case 5: //NOP
			break;
		case 6: //RIGHT
			xSpeed++;
			break;
		case 7: //UP LEFT
			xSpeed--;
			ySpeed--;
			break;
		case 8: //UP
			ySpeed--;
			break;
		case 9: //UP RIGHT
			xSpeed++;
			ySpeed--;
			break;
		}
		steps++;
		xPos += xSpeed;
		yPos += ySpeed;
		path.add( new int[]{xPos, yPos, 0} );
	}
	
	public void collided( GameObject object ) {
		if ( object.getId() == IDs.WALL ) {
			onDeath( steps );
			xSpeed = 0;
			ySpeed = 0;
			xPos = path.get( path.size() - 2 )[0];
			yPos = path.get( path.size() - 2 )[1];
			path.add( new int[]{xPos, yPos, 1} );
			path.add( new int[]{xPos, yPos, 0} );
		} else if ( object.getId() == IDs.CHECKPOINT ) {
			// If more checkpoints and the hit is next in line, remove it from checkpoints, that has to be hit.
			if ( !checkpoints.isEmpty() && ( object == checkpoints.get( 0 ) ) ) {
				checkpoints.remove( 0 );
				onCheckpoint( steps );
			}
			// Check if we hit the last checkpoint
			if ( checkpoints.isEmpty() )
				onWin( steps );		
		}
	}
	
	public void keyPressed( int key ) {
		// Warn if moving the wrong way
		double dis;
		if ( checkpoints.size() > 0 )
			dis = distanceToCheckpointSq( checkpoints.get( 0 ) );
		else
			dis = 0;
		if ( dis > cpDistance )
			System.out.println("WRONG WAY!");
		cpDistance = dis;
		
		if ( ( key > 0x30 ) && ( key < 0x3A ) )
			movePlayer( key - 0x30 );	
	}
	
	public void onWin( int steps ) {
		RaceTrack.onWin( steps );
	}
	
	public void onDeath( int steps ) {
		RaceTrack.onDeath( steps );
	}
	public void onCheckpoint( int steps ) {
		if ( checkpoints.size() > 0 )
			cpDistance = distanceToCheckpointSq( checkpoints.get( 0 ) );
		else
			cpDistance = 0;
	}
	
	public double distanceToCheckpointSq( Checkpoint checkpoint ) {
		return Line2D.ptSegDistSq( checkpoint.getPosition()[0], checkpoint.getPosition()[1],
				checkpoint.getPosition()[2], checkpoint.getPosition()[3],
				xPos, yPos );
	}
	
}
