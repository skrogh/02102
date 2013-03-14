import java.util.*;


public class GhostPlayer extends Player {
	
	private List<Integer> moves; // stores list of moves
	
	public GhostPlayer( int x, int y, ArrayList<Checkpoint> checkpoints, List<Integer> moves ){
		super( x, y, checkpoints );
		this.moves = moves;
	
	}
	
	public GhostPlayer( int x, int y, ArrayList<Checkpoint> checkpoints){
		super( x, y, checkpoints );
		this.moves = new ArrayList<Integer>(); 
		
		for( long i = 0; i < 200; i++ ) {
			moves.add( (int) ( Math.floor(Math.random()*9) + 1 ) );
		}
		
	}
	
	public void render() {
		if ( state == states.ALIVE ) {
			int[] pointP = path.get(0);
			for ( int[] point : path ) {
				if ( point[2] == 1 )
					StdDraw.setPenColor( StdDraw.RED );
				else
					StdDraw.setPenColor( StdDraw.BOOK_BLUE );
				StdDraw.setPenRadius( 0.0007 );
				StdDraw.line( pointP[0], pointP[1], point[0], point[1] );
				pointP = point;
			}
			StdDraw.setPenColor( StdDraw.GREEN );
			StdDraw.setPenRadius( 0.001 );
			StdDraw.point( xPos, yPos );
		}
	}
	
	public void keyPressed( int key ) {
		if ( ( state == states.ALIVE ) && ( key > 0x30 ) && ( key < 0x3A ) )
			movePlayer( moves.remove( 0 ) );
		else if ( !( ( key > 0x30 ) && ( key < 0x3A ) ) )
			System.out.println( this + " has this left: " + this.checkpoints );
	}
	
	public void onDeath( int steps ) {
		state = states.DEAD;
	}
	public void onWin( int steps ) {
		System.out.println( "Ghost vundet" );
	}
	
}
