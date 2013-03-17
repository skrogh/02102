import java.util.*;

/**
 * Class for managing agents, for creating a computer player.
 * Contaings a list 
 * 
 * @author Søren Andersen, s123369
 *
 */
public class AgentManager extends GameObject {

	private ArrayList<AgentPlayer> agents;
	private ArrayList<Checkpoint> checkpoints;
	private ArrayList<ArrayList<Integer>> moves;
	private ArrayList<ArrayList<Integer>> breed;
	private ArrayList<GameObject> gameObjects;
	private int startX, startY;
	private int checkpointsToPass;
	private int waitNumber, passNumber;
	private double mutateRate;
	private int optimizationRuns;
	private int lookAhead;

	/**
	 * Minimal constructor.
	 * 
	 * @param startX Start position of the player
	 * @param startY Start position of the player
	 * @param agents Number of agents, more will be faster, few will give a better visual image; when in debug mode. a good number is 5000-20000
	 * @param checkpoints List of checkpoints to pass
	 * @param gameObjects Reference to all other objects, used for collision checking
	 */
	public AgentManager( int startX, int startY, int agents, ArrayList<Checkpoint> checkpoints, ArrayList<GameObject> gameObjects ){

		// Setup game object variables:
		super( );
		ID = IDs.AI;
		state = states.ALIVE;
		collidable = false;

		// Setup local variables
		this.agents = new ArrayList<AgentPlayer>();
		moves = new ArrayList<ArrayList<Integer>>();
		breed = new ArrayList<ArrayList<Integer>>();
		breed.add( new ArrayList<Integer>() );
		this.startX = startX;
		this.startY = startY;
		this.checkpoints = checkpoints; // Make reference, not copy, as we will not change this.
		this.gameObjects = gameObjects; // Make reference to game objects
		waitNumber = 50;
		passNumber = 20;
		mutateRate = 0.0005;
		optimizationRuns = 3;
		lookAhead = 0;
		checkpointsToPass = 2 + lookAhead;

		// Create agents:
		for ( int i = 0; i < agents; i++ ) {
			this.agents.add( new AgentPlayer( this.startX, this.startY, this.checkpoints, checkpointsToPass, lookAhead ) );
		}

	}


	/**
	 * Called from "RaceTrack"
	 * Update position of agents, checks if they finished or fails and remove/add new.
	 * This could be done in a while loop in the constructor; but that would freeze the program, leaving the user with little feedback while the process is going on
	 */
	public void update() {
		// Check if time for new generation
		if ( moves.size() >= waitNumber )
			newGeneration();

		// List for storing agents to be replaced 
		ArrayList<AgentPlayer> remove = new ArrayList<AgentPlayer>();

		for ( AgentPlayer agent : agents ) {
			// Update agent
			agent.update();

			// Do collision checking
			for ( GameObject gameObject : gameObjects ) {
				if ( ( gameObject.getState() != GameObject.states.DEAD ) &&
						( agent.getState() != GameObject.states.DEAD ) &&
						gameObject.isCollidable() && agent.isCollidable() &&
						RaceTrack.collideObjects( gameObject, agent ) ) {
					agent.collided( gameObject );
				}
			}
			// Check for death
			if ( agent.state == GameObject.states.DEAD ) {
				// Add to list of agents to be removed, we can't remove agent here, 'cause that would change the size of the list
				remove.add( agent );
			}
		}

		// Remove all dead agents
		for ( AgentPlayer agent : remove ) {
			if ( agent.fertility < Integer.MAX_VALUE ) {
				ArrayList<Integer> move = new ArrayList<Integer>();
				move.add( agent.fertility );
				move.addAll( (ArrayList<Integer>) agent.child.clone() );
				moves.add( move );

				// Print progress
				System.out.println( "Progress: " + moves.size() + " / " + waitNumber + " Runs left: " + ( checkpoints.size() - checkpointsToPass + optimizationRuns ) );
			}
			agents.remove( agent );
			addAgent();
		}

	}

	/**
	 * Add a new agent from a mutated breed
	 */
	private void addAgent() {
		// get a random breed
		ArrayList<Integer> path = (ArrayList<Integer>) breed.get( (int) ( Math.random() * breed.size() ) ).clone();
		// Mutate steps:
		// Alter steps
		for ( int i = 0; i < path.size(); i++ ) {
			if ( Math.random() < mutateRate )
				path.set( i, (int) ( (int) ( Math.floor(Math.random() * 9 ) + 1 ) ) );
		}
		// Remove steps.
		for ( int i = 0; i < Math.random() * 0.1 * mutateRate * path.size(); i++ )
			path.remove( (int) Math.floor(Math.random() * path.size() ) );
		// Add the new agent
		agents.add( new AgentPlayer( this.startX, this.startY, this.checkpoints, checkpointsToPass, lookAhead, path ) );
	}

	/**
	 * Called from "RaceTrack"
	 * Call all agents and tell them to render
	 */
	public void render() {
		for ( AgentPlayer agent : agents ) {
			agent.render();
		}
	}

	/**
	 * Create a new generation
	 * 
	 */

	private void newGeneration() {
		// Sort list of possible paths
		Collections.sort( moves, new ListSorter());

		// Remove fertility variable:
		for ( ArrayList<Integer> move : moves )
			move.remove( 0 );

		// Remove all, but the x best
		while ( moves.size() > passNumber )
			moves.remove( 0 );

		// Create new breed
		breed = (ArrayList<ArrayList<Integer>>) moves.clone();
		moves.clear();

		// Print the new breed, for debugging
		for ( ArrayList<Integer> br : breed )
			System.out.println( br );

		int nAgents = agents.size();
		agents.clear();
		checkpointsToPass++;

		// If we found a way all the way around, optimize it. during optimization, mutations become more common
		if ( checkpointsToPass > checkpoints.size() + lookAhead ) {
			checkpointsToPass = checkpoints.size() + lookAhead;
			optimizationRuns--;
			mutateRate = mutateRate * 0.95 + 1 * 0.05;
			if ( optimizationRuns < 0 ) {
				// Create GhostPlayer and remove this object.
				// Since this is being done in a for-loop, calling all objects,
				// this only runs without errors, because we remove and add the same number of objects,
				// so the list size stays constant
				gameObjects.add( new GhostPlayer( startX, startY, checkpoints, breed.remove( breed.size() - 1 ) ) );
				gameObjects.remove( this );
			}
		}

		// Create new agents:
		for ( int i = 0; i < nAgents; i++ ) {
			addAgent();
		}
	}
}
