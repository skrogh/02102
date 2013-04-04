public class GameOfLifeMain {
	public static boolean mouseLast = false;
	public static int setState;
	
	public static void main( String[] args ) {
		StdDraw.setCanvasSize( 1024, 512 );
		StdDraw.setXscale( 0, 2 );
		StdDraw.setYscale( 1, 0 );

		// StdDraw.setPenRadius( 100 / 512d );

		StdDraw.show( 0 );
		GameOfLife golObj = new GameOfLife( 512, 512, true );
		golObj.render( 1, 0, 1, 1 );
		//System.out.println( golObj );
		//System.out.println( golObj.printNeighbors() );

		for (;;) {
			boolean mousePressed = StdDraw.mousePressed();
			//System.out.println( "now: " + mousePressed + " last: " + mouseLast );
			if ( StdDraw.hasNextKeyTyped() && ( StdDraw.nextKeyTyped() == ' ' ) )
				golObj.step();
			else if ( mousePressed && ( !mouseLast ) )
				setState = golObj.getState( golObj.mouseX( 1, 0, 1, 1 ), golObj.mouseY( 1, 0, 1, 1 ) );
			if ( mousePressed )
				golObj.setCell( golObj.mouseX( 1, 0, 1, 1 ), golObj.mouseY( 1, 0, 1, 1 ), setState + 1 );
			
			golObj.render( 1, 0, 1, 1 );
			golObj.renderMouse( 1, 0, 1, 1 );
			StdDraw.show( 0 );
			//System.out.println( golObj );
			//System.out.println( golObj.printNeighbors() );
			mouseLast = mousePressed;
		}

	}
}
