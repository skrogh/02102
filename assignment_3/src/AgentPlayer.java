import java.util.*;


public class AgentPlayer extends Player {

	private ArrayList<Integer> moves; // stores list of moves
	public ArrayList<Integer> drove; // stores list of moves
	public ArrayList<Integer> child; // stores list of moves
	private int goal;
	public int fertility;
	private double cpDistance;

	public AgentPlayer( int x, int y, ArrayList<Checkpoint> checkpoints, int goal, ArrayList<Integer> moves ){
		super( x, y, checkpoints );
		this.ID = IDs.AI;
		this.moves = (ArrayList<Integer>) moves.clone();
		this.fertility = Integer.MAX_VALUE;
		this.drove = new ArrayList<Integer>();
		this.child = new ArrayList<Integer>();
		this.goal = this.checkpoints.size() - goal;
		cpDistance = distanceToCheckpointSq( checkpoints.get( 0 ) );

	}

	public AgentPlayer( int x, int y, ArrayList<Checkpoint> checkpoints, int goal ){
		super( x, y, checkpoints );
		this.ID = IDs.AI;
		this.moves = new ArrayList<Integer>();
		this.fertility = Integer.MAX_VALUE;
		this.drove = new ArrayList<Integer>();
		this.child = new ArrayList<Integer>();
		this.goal = this.checkpoints.size() - goal;
		cpDistance = distanceToCheckpointSq( checkpoints.get( 0 ) );

	}

	public void render() {
		if ( state == states.ALIVE ) {
			int[] pointP = path.get(0);
			if ( RaceTrack.debug ) {
				for ( int[] point : path ) {
					if ( point[2] == 1 )
						StdDraw.setPenColor( StdDraw.RED );
					else
						StdDraw.setPenColor( StdDraw.BOOK_RED );
					StdDraw.setPenRadius( 0.0007 );
					StdDraw.line( pointP[0], pointP[1], point[0], point[1] );
					pointP = point;
				}
			}
			StdDraw.setPenColor( StdDraw.BLACK );
			StdDraw.setPenRadius( 0.001 );
			StdDraw.point( xPos, yPos );
		}
	}

	public void update()  {
		// Kill if moving the wrong way
		double dis;
		if ( checkpoints.size() > 0 )
			dis = distanceToCheckpointSq( checkpoints.get( 0 ) );
		else
			dis = 0;
		if ( dis > cpDistance )
			state = states.DEAD;
		cpDistance = dis;

		if ( moves.isEmpty() )
			moves.add( (int) ( Math.floor(Math.random()*9) + 1 ) );

		drove.add( moves.remove( 0 ) );
		movePlayer( drove.get( drove.size() - 1) );

		super.update();
	}

	public void keyPressed( int key ) {
	}

	public void onDeath( int steps ) {
		state = states.DEAD;
	}
	public void onWin( int steps ) {

	}
	public void onCheckpoint ( int steps ) {
		if ( checkpoints.size() > 0 )
			cpDistance = distanceToCheckpointSq( checkpoints.get( 0 ) );
		else
			cpDistance = 0;
		
		if ( checkpoints.size() == goal + 1 ) {
			child = (ArrayList<Integer>) drove.clone();
			if ( goal == -1 ) {
				fertility = this.steps;
				state = states.DEAD;
			}
		}
		if ( checkpoints.size() <= goal ) {
			fertility = this.steps;
			state = states.DEAD;
		}
	}

}
