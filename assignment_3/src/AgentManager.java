import java.util.*;


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
		checkpointsToPass = 3;
		waitNumber = 200;
		passNumber = 20;
		mutateRate = 0.005;
		optimizationRuns = 5;
		
		// Create agents:
		for ( int i = 0; i < agents; i++ ) {
			this.agents.add( new AgentPlayer( this.startX, this.startY, this.checkpoints, checkpointsToPass ) );
		}
		
	}
	
	public void update() {
		// Check if time for new generation
		if ( moves.size() > waitNumber )
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
				System.out.println( "Progress: " + moves.size() + " / " + waitNumber );
			}
			agents.remove( agent );
			addAgent();
		}
		
	}
	
	private void addAgent() {
		ArrayList<Integer> path = (ArrayList<Integer>) breed.get( (int) ( Math.random() * breed.size() ) ).clone();
		// Alter steps
		for ( int i = 0; i < path.size(); i++ ) {
			if ( Math.random() < mutateRate )
				path.set( i, (int) ( (int) ( Math.floor(Math.random() * 9 ) + 1 ) ) );
		}
		// Remove steps
		for ( int i = 0; i < Math.random() * 0.1 * mutateRate * path.size(); i++ )
			path.remove( (int) Math.floor(Math.random() * path.size() ) );
		
		agents.add( new AgentPlayer( this.startX, this.startY, this.checkpoints, checkpointsToPass, path ) );
	}

	public void render() {
		for ( AgentPlayer agent : agents ) {
			agent.render();
		}
	}
	
	public void keyPressed() {
		System.out.println( "Moves:" + moves );
	}
	
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
		
		for ( ArrayList<Integer> br : breed )
			System.out.println( br );
		
		int nAgents = agents.size();
		agents.clear();
		checkpointsToPass++;
		if ( checkpointsToPass > checkpoints.size() + 1 ) {
			checkpointsToPass = checkpoints.size() + 1;
			optimizationRuns--;
			mutateRate = mutateRate * 0.95 + 1 * 0.05;
			if ( optimizationRuns < 0 ) {
				gameObjects.add( new GhostPlayer( startX, startY, checkpoints, breed.get( 0 ) ) );
				gameObjects.remove( this );
			}
		}
			
		
		// Create new agents:
		for ( int i = 0; i < nAgents; i++ ) {
			addAgent();
		}
	}
	
}
