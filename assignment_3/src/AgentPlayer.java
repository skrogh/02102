import java.util.*;

/**
 * An agent player, createt by AgenManager
 * 
 * @author Søren Andersen, s123369
 *
 */
public class AgentPlayer extends Player {

	private ArrayList<Integer> moves; // Stores list of moves
	public ArrayList<Integer> drove; // Stores list of moves
	public ArrayList<Integer> child; // Stores list of moves
	private int goal;
	private int lookAhead;
	public int fertility;
	private double cpDistance;

	/**
	 * Create Agent with a predetermined path
	 * 
	 * @param x Start position
	 * @param y Start position
	 * @param checkpoints List of checkpoints to pass
	 * @param goal Number of checkpoints to pass
	 * @param lookAhead Number of extra checkpoints to go to, but not use the path from
	 * @param moves List of moves
	 */
	public AgentPlayer( int x, int y, ArrayList<Checkpoint> checkpoints, int goal, int lookAhead, ArrayList<Integer> moves ){
		super( x, y, checkpoints );
		this.ID = IDs.AI;
		this.moves = (ArrayList<Integer>) moves.clone();
		this.fertility = Integer.MAX_VALUE;
		this.drove = new ArrayList<Integer>();
		this.child = new ArrayList<Integer>();
		this.goal = this.checkpoints.size() - goal;
		this.lookAhead = lookAhead;
		cpDistance = distanceToCheckpointSq( checkpoints.get( 0 ) );

	}
	
	/**
	 * Create Agent with no predetermined path
	 * 
	 * @param x Start position
	 * @param y Start position
	 * @param checkpoints List of checkpoints to pass
	 * @param goal Number of checkpoints to pass
	 * @param lookAhead Number of extra checkpoints to go to, but not use the path from
	 */
	public AgentPlayer( int x, int y, ArrayList<Checkpoint> checkpoints, int goal, int lookAhead ){
		super( x, y, checkpoints );
		this.ID = IDs.AI;
		this.moves = new ArrayList<Integer>();
		this.fertility = Integer.MAX_VALUE;
		this.drove = new ArrayList<Integer>();
		this.child = new ArrayList<Integer>();
		this.goal = this.checkpoints.size() - goal;
		this.lookAhead = lookAhead;
		cpDistance = distanceToCheckpointSq( checkpoints.get( 0 ) );
	}
	/**
	 * Called from AgentManager.
	 * Only display if global variable debug == true.
	 */
	public void render() {
		if ( state == states.ALIVE ) {
			int[] pointP = path.get(0);
			if ( RaceTrack.debug ) {
				// Draw driven path
				for ( int[] point : path ) {
					if ( point[2] == 1 )
						StdDraw.setPenColor( StdDraw.RED );
					else
						StdDraw.setPenColor( StdDraw.BOOK_RED );
					StdDraw.setPenRadius( 0.0007 );
					StdDraw.line( pointP[0], pointP[1], point[0], point[1] );
					pointP = point;
				}
				StdDraw.setPenColor( StdDraw.BLACK );
				StdDraw.setPenRadius( 0.001 );
				StdDraw.point( xPos, yPos );
			}
		}
	}
	
	/**
	 * Called from AgentManager
	 */
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
		
		// If out of moves, add a random one
		if ( moves.isEmpty() )
			moves.add( (int) ( Math.floor(Math.random()*9) + 1 ) );
		
		// Move next move in line.
		drove.add( moves.remove( 0 ) );
		movePlayer( drove.get( drove.size() - 1) );

		super.update();
	}
	
	/**
	 * Overwrite Player keyPressed, since Agents are not user controlled
	 */
	public void keyPressed( int key ) {
	}

	public void onDeath( int steps ) {
		state = states.DEAD;
	}
	public void onWin( int steps ) {

	}
	
	/**
	 * Called on collision with checkpoint
	 */
	public void onCheckpoint ( int steps ) {
		// Reset previous checkpoint distance
		if ( checkpoints.size() > 0 )
			cpDistance = distanceToCheckpointSq( checkpoints.get( 0 ) );
		else
			cpDistance = 0;
		
		// If crossed the checkpoints "lookAhead" before the goal, set current path as child
		if ( checkpoints.size() == goal + lookAhead ) {
			child = (ArrayList<Integer>) drove.clone();
			// If crossed end line
			if ( goal + lookAhead == 0 ) {
				fertility = steps;
				state = states.DEAD;
			}
		}
		
		// If hit goal checkpoint
		if ( checkpoints.size() <= goal ) {
			fertility = steps;
			state = states.DEAD;
		}
	}

}
