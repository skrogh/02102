import java.awt.Color;
import java.io.IOException;




public class GameOfLife {

	protected int edgeState; //0 for dead edges, 1 for living edges, 2 for warp edges
	protected int[][] state;
	protected int[][] rules;
	protected int states;
	protected Color[] colors;

	/**
	 * Generates a game of life with a specific size and the option to be randomly initialized.
	 * Edges warp.
	 * @param xSize of the game
	 * @param ySize of the game
	 * @param random Set to true, for a random initial state
	 */
	GameOfLife( int xSize, int ySize, boolean random ) {
		state = new int[xSize][ySize];
		colors = new Color[0];
		edgeState = -1;

		// Load default rule-set and random colors
		setRules();

		// Initialize random states, if told to:
		if ( random )
			for ( int i = 0; i < state.length; i++ ) {
				for ( int j = 0; j < state[0].length; j++ ) {
					state[i][j] = (int) ( Math.random() * states );
				}
			}
	}

	/**
	 * Creates a game of life from an array of states.
	 * @param initialState
	 */
	GameOfLife( int[][] initialState ) {
		state = new int[1][1];
		setStates( initialState );
		colors = new Color[0];
		edgeState = -1;

		// Load default rule-set and random colors
		setRules();

	}

	/**
	 * Loads a default rule-set, in this case Conway's Game of Life
	 */
	public void setRules() {
		setRules( new int[][] {
				{0,		0, 0, 0, 0, 1, 0},	// 0.  Dead
				{1,		0, 0, 0, 1, 1, 0},	// 1.  living
		} );
	}

	/**
	 * Safe setter for rule-set.
	 * Each state is defines as following:  
	 * state0: { "number of 'lives'", "default low", "goto for 0 neighbors", "goto for 1 neighbors" ... "default high" }
	 * state1: { "number of 'lives'", "default low", "goto for 0 neighbors", "goto for 1 neighbors" ... "default high" }
	 * 
	 * where the "lives" are how many neighbors a cell in this state counts for.
	 * A cell then goes to the state indicated by the following list.
	 * Negative lives are ok.
	 * 
	 * @param rules, sets the rule-set of this object to this.
	 * @return true if the set was successful, false of not. The rule-set is left unchanged then.
	 */
	public boolean setRules( int[][] rules ) {
		// { "number of 'lives'", "default low", "goto for 0 lives", "goto for 1 lives" ... "default high" }

		if ( !validRules( rules ) )
			return false;

		this.rules = rules.clone();
		states = rules.length;

		// Add missing colors at random
		addMissingColors();

		return true;
	}

	/**
	 * Adds random colors to fill up, so that each state defined in rules, has a color
	 */
	private void addMissingColors() {
		if ( colors.length < states ) {
			Color[] newColors = new Color[states];
			for ( int i = 0; i < newColors.length; i++ ) {
				if ( i < colors.length )
					newColors[i] = colors[i];
				else 
					newColors[i] = new Color( (int) ( Math.random() * 256 ),
							(int) ( Math.random() * 256 ), (int) ( Math.random() * 256 ) );
			}
			colors = newColors;
		}
	}

	/**
	 * Calculates the number of neighbors ( times their "life" ) for a given cell
	 * @param x coordinate of cell
	 * @param y coordinate of cell
	 * @return
	 */
	private int neighbors( int x, int y ) {
		int result = 0;
		int width = state.length;
		int height = state[0].length;
		if ( edgeState == -1 ) {
			for ( int i = -1; i <= 1; i++ ) {
				result += rules[state[mod( x + 1, width )][mod( y + i, height )]][0];
				result += rules[state[mod( x - 1, width )][mod( y + i, height )]][0];
				if ( i != 0 )
					result += rules[state[mod( x, width )][mod( y + i, height )]][0];
			}
		} else {
			for ( int i = -1; i <= 1; i++ ) {
				if ( ( x + 1 >= width ) || ( ( y + i ) < 0 ) || ( ( y + i ) >= height ) )
					result += edgeState;
				else
					result += rules[state[( x + 1 )][( y + i )]][0];

				if ( ( x - 1 < 0 ) || ( ( y + i ) < 0 ) || ( ( y + i ) >= height ) )
					result += edgeState;
				else
					result += rules[state[( x - 1 )][( y + i )]][0];

				if ( ( ( y + i ) < 0 ) || ( ( y + i ) >= height ) )
					result += edgeState;
				else if ( i != 0 )
					result += rules[state[( x )][( y + i )]][0];
			}
		}
		return result;
	}

	/**
	 * Advance the Game of Life by one generation 
	 */
	public void step() {
		int[][] nextState = new int[state.length][state[0].length];
		for ( int i = 0; i < state.length; i++ ) {
			for ( int j = 0; j < state[0].length; j++ ) {
				int neighbors = neighbors( i, j );
				if ( neighbors >= rules[state[i][j]].length - 2 )
					nextState[i][j] = rules[state[i][j]][rules[state[i][j]].length - 1];
				else if ( neighbors < 0 )
					nextState[i][j] = rules[state[i][j]][1];
				else
					nextState[i][j] = rules[state[i][j]][neighbors + 2];
			}
		}
		state = nextState;
	}

	/**
	 * Render the game of life with it's top corner state[0][0] in (0, 0) SdtDraw coordinates. the size is set by width and height.
	 * Due to funky aliasing artifacts, it is advised that the screen is not cleared under the area being drawn in,
	 * as this gives prettier results.
	 * @param x start
	 * @param y start
	 * @param width self explaining
	 * @param height --||--
	 */
	public void render( double x, double y, double width, double height ) {

		StdDraw.setPenRadius( 2 / 512d );

		for ( int i = 0; i < state.length; i++ ) {
			for ( int j = 0; j < state[0].length; j++ ) {
				StdDraw.setPenColor( colors[state[i][j]] );
				StdDraw.filledRectangle( x + ( i + 0.5d ) * width / state.length , y + ( j + 0.5d ) * height / state[0].length,
						width / state.length * 0.5d, height / state[0].length * 0.5d );
			}
		}	
	}

	/**
	 * Draws a red square at the position of the mouse, quantized to the grid of the cells
	 * @param x corner of the area, the game is drawn in
	 * @param y corner of the area, the game is drawn in
	 * @param width of the area, the game is drawn in
	 * @param height of the area, the game is drawn in
	 */
	public void renderMouse( double x, double y, double width, double height ) {
		StdDraw.setPenRadius( 2 / 512d );
		StdDraw.setPenColor( StdDraw.RED );
		int mX = mouseX( x, y, width, height );
		int mY = mouseY( x, y, width, height );

		if( ( mX >= 0 ) && ( mY >= 0 ) )
			StdDraw.rectangle( x + ( mX + 0.5d ) * width / state.length , y + ( mY + 0.5d ) * height / state[0].length,
					width / state.length * 0.5d, height / state[0].length * 0.5d );

	}

	/**
	 * 
	 * @param x corner of the area, the game is drawn in
	 * @param y corner of the area, the game is drawn in
	 * @param width of the area, the game is drawn in
	 * @param height of the area, the game is drawn in
	 * @return Given the render region returns the x coordinate of the mouse in the Game of Life array.
	 * Returns -1 if the mouse is outside the area
	 */
	public int mouseX( double x, double y, double width, double height ) {
		int mX = (int) Math.floor( ( StdDraw.mouseX() - x ) * state.length / width);
		if ( ( mX < 0 ) || ( mX >= state.length ) )
			return -1;

		return mX;
	}

	/**
	 * 
	 * @param x corner of the area, the game is drawn in
	 * @param y corner of the area, the game is drawn in
	 * @param width of the area, the game is drawn in
	 * @param height of the area, the game is drawn in
	 * @return Given the render region returns the y coordinate of the mouse in the Game of Life array.
	 * Returns -1 if the mouse is outside the area
	 */
	public int mouseY( double x, double y, double width, double height ) {
		int mY = (int) Math.floor( ( StdDraw.mouseY() - y ) * state[0].length / height);
		if ( ( mY < 0 ) || ( mY > state[0].length ) )
			return -1;

		return mY;
	}

	/**
	 * @return the stare array as a String of lines of numbers with state[0][0] in the top left corner
	 * and state[n][m] in  bottom right corner 
	 */
	public String toString() {
		String result = "";
		for ( int j = 0; j < state[0].length; j++ ) {
			for ( int i = 0; i < state.length; i++ ) {
				result += state[i][j] + " ";
			}
			result += "\n";
		}		

		return result;
	}

	/**
	 * @return the neighbors as a String of lines of numbers with neighbors(0, 0) in the top left corner
	 * and neighbors(n, m) in  bottom right corner 
	 */
	public String printNeighbors() {
		String result = "";
		for ( int j = 0; j < state[0].length; j++ ) {
			for ( int i = 0; i < state.length; i++ ) {
				result += neighbors( i, j ) + " ";
			}
			result += "\n";
		}		

		return result;
	}

	/**
	 * Custom "fake" modulus method. In reality this works just like integer overflow/underflow,
	 * except the overflow happens at a specific point.
	 * @param in input to over/underflow
	 * @param mod overflow point, ex: mod( 4, 3 ) = 1. mod( -1, 3 ) = 2. mod( 3, 3 ) = 0
	 * @return
	 */
	private int mod( int in, int mod ) {
		if ( mod == 0 )
			return in;

		if ( mod < 0 )
			return in;

		mod = Math.abs( mod );

		while ( in >= mod )
			in -= mod;

		while ( in < 0 )
			in += mod;

		return in;
	}

	/**
	 * Changes the state of a cell to the next one. If the cell does not exist, nothing changes
	 * @param x coordinate for cell
	 * @param y coordinate for cell
	 */
	public void alterState( int x, int y ) {
		if ( ( x >= 0 ) && ( y >= 0 ) && ( x < state.length ) && ( y < state[0].length ) )
			state[x][y] = mod( state[x][y] + 1, states );
	}

	/**
	 * Set the state of a specific cell. If the state does not exist, it loops around.
	 * @param x coordinate for cell
	 * @param y coordinate for cell
	 * @param state to set the cell to
	 */
	public void setState( int x, int y, int state ) {
		if ( ( x >= 0 ) && ( y >= 0 ) && ( x < this.state.length ) && ( y < this.state[0].length ) )
			this.state[x][y] = mod( state, states );
	}

	/**
	 * Safe setter for the whole state array
	 * @param state
	 * @return true, if the state was set, else false
	 */
	public boolean setStates( int[][] state ) {
		if ( state.length < 1 )
			return false;
		if ( state[0].length < 1 )
			return false;

		for ( int i = 0; i < state.length; i++ ) {
			if ( i > 0 )
				if ( state[i].length != state[i - 1].length ) 
					return false;
			for ( int j = 0; j < state[i].length; j++ )
				if ( ( state[i][j] < 0) || ( state[i][j] > states - 1 ) )
					return false;
		}
		
		this.state = state.clone();
		
		return true;
	}

	/**
	 * 
	 * @return A copy of the rule-set array
	 */
	public int[][] getRules() {
		return rules.clone();
	}

	/**
	 * 
	 * @return A copy of the color array
	 */
	public Color[] getColors() {
		return colors.clone();
	}

	/**
	 * 
	 * @return The number of states in the current rule-set
	 */
	public int getStates() {
		return states;
	}

	/**
	 * 
	 * @return A copy of the array storing the state of each cell
	 */
	public int[][] getState() {
		return state.clone();
	}

	/**
	 * Returns the state of the given cell
	 * @param x coordinate for cell
	 * @param y coordinate for cell
	 * @return The state of the given cell. Returns -1 if the specified cell does not exist.
	 */
	public int getState( int x, int y ) {
		if ( ( x >= 0 ) && ( y >= 0 ) && ( x < state.length ) && ( y < state[0].length ) )
			return state[x][y];
		return -1;
	}

	/**
	 * Checks if a rule-set is valid
	 * @param rules to check
	 * @return true if valid, else false
	 */
	public boolean validRules( int[][] rules ) {
		// Check if valid rule-set: (does not check for over-specified rules, and unused states)
		if ( rules.length < 1 )
			return false;

		for ( int i = 0; i < rules.length; i++ ) {
			if ( rules[i].length < 2 ) {
				i = Integer.MAX_VALUE;
				return false;
			}
			for ( int j = 1; j < rules[i].length; j++ ) {
				if ( ( rules[i][j] < 0 ) || ( rules[i][j] > rules.length - 1 ) ) {
					return false;
				}
			}
		}

		// Check if any current state extends exceeds the new number of states:
		for ( int[] i : state )
			for ( int j : i )
				if ( j > rules.length - 1 )
					return false;

		return true;
	}
	
	/**
	 * Loads the specified set of colors,
	 * If not enough colors are loaded, random ones are generated
	 * @param colors
	 * @return
	 */
	
	public boolean setColors( Color[] colors ) {
		this.colors = colors.clone();
		
		addMissingColors();
		
		return true;
	}
	
	public boolean loadSetup( String ruleFile, String stateFile, String colorFile, boolean print ) {
		int[][] ruleFileLoad;
		int[][] stateFileLoad;
		Color[] colorFileLoad;
		try {
			ruleFileLoad = Cutil.fileToIntArray( ruleFile );
		} catch (IOException e) {
			if ( print )
				System.out.println( "Error loading file: " + ruleFile );
			return false;
		}
		try {
			stateFileLoad = Cutil.fileToIntArray( stateFile );
		} catch (IOException e) {
			if ( print )
				System.out.println( "Error loading file: " + stateFile );
			return false;
		}
		try {
			colorFileLoad = ColorGenerator.generateColorMapFromFile( colorFile );
		} catch (IOException e) {
			if ( print )
				System.out.println( "Error loading file: " + colorFile );
			return false;
		}
		int[][] currentState = state;
		int[][] currentRules = rules;
		Color[] currentColors = colors;
		
		
		
		return true;
	}

}