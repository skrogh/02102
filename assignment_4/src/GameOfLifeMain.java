import java.io.IOException;
import java.util.Scanner;

public class GameOfLifeMain {	
	public static void main( String[] args ) {
		boolean mouseLast = false;
		int setState = 0;
		GameOfLife golObj = new GameOfLife();

		StdDraw.setCanvasSize( 1024, 512 );
		StdDraw.setXscale( 0, 2 );
		StdDraw.setYscale( 1, 0 );

		//Stop automatic update
		StdDraw.show( 0 );

		switch ( Cutil.promptStringSet( "Type \"load\" to load a file set,\n" +
				"Type \"random\" to initialize a random game of life,\n" +
				"Type \"clean\" to initialize a random game of life:",
				new String[] {"load", "random"} ) ) {
				case "load":
					if ( !golObj.loadSetup( Cutil.promptString( "Write filename for rules" ),
							Cutil.promptString( "Write filename for initial state"),
							Cutil.promptString( "Write filename for colorfile" ), true ) )
						System.out.println( "Error in loading" );
					break;
				case "random":
					try {
						golObj = new GameOfLife( Cutil.promptInt( "width:", Cutil.option.POSITIVE ),
								Cutil.promptInt( "width:", Cutil.option.POSITIVE ), true );
					} catch ( IllegalArgumentException ex ) {
						System.out.println( "Please enter positive values" );
					}
					break;
				case "clean":
					try {
						golObj = new GameOfLife( Cutil.promptInt( "width:", Cutil.option.POSITIVE ),
								Cutil.promptInt( "width:", Cutil.option.POSITIVE ), false );
					} catch ( IllegalArgumentException ex ) {
						System.out.println( "Please enter positive values" );
					}
					break;

		}

		golObj.render( 0, 0, 1, 1 );
		//System.out.println( golObj );
		//System.out.println( golObj.printNeighbors() );
		for (;;) {
			boolean mousePressed = StdDraw.mousePressed();
			//System.out.println( "now: " + mousePressed + " last: " + mouseLast );
			if ( StdDraw.hasNextKeyTyped() && ( StdDraw.nextKeyTyped() == ' ' ) )
				golObj.step();
			else if ( mousePressed && ( !mouseLast ) )
				setState = golObj.getState( golObj.mouseX( 0, 0, 1, 1 ), golObj.mouseY( 0, 0, 1, 1 ) );
			if ( mousePressed )
				golObj.setState( golObj.mouseX( 0, 0, 1, 1 ), golObj.mouseY( 0, 0, 1, 1 ), setState + 1 );
			//Clear key buffer
			while ( StdDraw.hasNextKeyTyped() )
				StdDraw.nextKeyTyped();
			golObj.render( 0, 0, 1, 1 );
			golObj.renderMouse( 0, 0, 1, 1 );
			StdDraw.show( 0 );
			//System.out.println( golObj );
			//System.out.println( golObj.printNeighbors() );
			mouseLast = mousePressed;
		}
	}
}
