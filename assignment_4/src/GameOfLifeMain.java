

public class GameOfLifeMain {	
	public static void main( String[] args ) {
		boolean mouseLast = false;
		int setState = 0;
		GameOfLife golObj = new GameOfLife();

		StdDraw.setCanvasSize( 512, 512 );
		StdDraw.setXscale( 0, 1 );
		StdDraw.setYscale( 1, 0 );

		switch ( Cutil.promptStringSet( "Type \"load\" to load a file set,\n" +
				"Type \"random\" to initialize a random game of life,\n" +
				"Type \"clean\" to initialize a random game of life:",
				new String[] {"load", "random", "clean"} ) ) {
				case "load":
					if ( !golObj.loadSetup( Cutil.promptString( "Write filename for rules, N for Game of life" ),
							Cutil.promptString( "Write filename for initial state"),
							Cutil.promptString( "Write filename for colorfile, N for random" ), true ) )
						System.out.println( "Error in loading" );
					break;
				case "random":
					try {
						golObj = new GameOfLife( Cutil.promptInt( "width:", Cutil.option.POSITIVE ),
								Cutil.promptInt( "height:", Cutil.option.POSITIVE ), true );
					} catch ( Exception ex ) {
						System.out.println( "Please enter positive values" );
					}
					break;
				case "clean":
					try {
						golObj = new GameOfLife( Cutil.promptInt( "width:", Cutil.option.POSITIVE ),
								Cutil.promptInt( "heigth:", Cutil.option.POSITIVE ), false );
					} catch ( Exception ex ) {
						System.out.println( "Please enter positive values" );
					}
					break;
				case "":
					System.out.println( "Wrong intput" );
					break;

		}
		
		//Stop automatic update
		StdDraw.show( 0 );
		golObj.render( 0, 0, 1, 1 );

		for (;;) {
			// Save, to avoid errors if mouse button changes during stepping
			boolean mousePressed = StdDraw.mousePressed();
			
			// Check if has to step
			if ( StdDraw.hasNextKeyTyped() && ( StdDraw.nextKeyTyped() == ' ' ) )
				golObj.step();
			// Check if mouse pressed, sample state under mouse
			else if ( mousePressed && ( !mouseLast ) )
				setState = golObj.getState( golObj.mouseX( 0, 0, 1, 1 ), golObj.mouseY( 0, 0, 1, 1 ) );
			
			// If mouse held, change state under mouse
			if ( mousePressed )
				golObj.setState( golObj.mouseX( 0, 0, 1, 1 ), golObj.mouseY( 0, 0, 1, 1 ), setState + 1 );
			
			// Clear key buffer
			while ( StdDraw.hasNextKeyTyped() )
				StdDraw.nextKeyTyped();
			
			// Render GOL and mouse
			golObj.render( 0, 0, 1, 1 );
			golObj.renderMouse( 0, 0, 1, 1 );
			StdDraw.show( 0 );

			// Save last mouse state 
			mouseLast = mousePressed;
		}
	}
}
