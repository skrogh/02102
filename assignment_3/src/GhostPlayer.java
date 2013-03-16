import java.util.*;


public class GhostPlayer extends Player {
	
	private ArrayList<Integer> moves; // stores list of moves
	
	public GhostPlayer( int x, int y, ArrayList<Checkpoint> checkpoints, ArrayList<Integer> moves ){
		super( x, y, checkpoints );
		ID = IDs.AI;
		this.moves = (ArrayList<Integer>) moves.clone();

	}
	
	public GhostPlayer( int x, int y, ArrayList<Checkpoint> checkpoints ){
		super( x, y, checkpoints );
		ID = IDs.AI;
		moves = new ArrayList<Integer>();
	}
	
	public void render() {
		if ( state == states.ALIVE ) {
			int[] pointP = path.get(0);
			for ( int[] point : path ) {
				if ( point[2] == 1 )
					StdDraw.setPenColor( StdDraw.RED );
				else
					StdDraw.setPenColor( StdDraw.BOOK_RED );
				StdDraw.setPenRadius( 0.007 );
				StdDraw.line( pointP[0], pointP[1], point[0], point[1] );
				pointP = point;
			}
			StdDraw.setPenColor( StdDraw.BLACK );
			StdDraw.setPenRadius( 0.01 );
			StdDraw.point( xPos, yPos );
		}
	}
	
	
	public void keyPressed( int key ) {
		if ( ( key > 0x30 ) && ( key < 0x3A ) ) {
			if ( moves.isEmpty() )
				moves.add( (int) ( Math.floor(Math.random()*9) + 1 ) );
			movePlayer( moves.remove( 0 ) );
		}
	}
	
	public void onDeath( int steps ) {
		state = states.DEAD;
	}
	public void onWin( int steps ) {

	}
	public void onCheckpoint ( int steps ) {

	}
	
}
