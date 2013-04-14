
public class GameOfLifeTest {
	public static void main( String[] foo ) {
		GameOfLife gol = new GameOfLife( 5, 5, true );
		System.out.println( "State:\n" + gol );
		System.out.println( "Neighbors:\n" + gol.printNeighbors() );
		gol.step();
		System.out.println( "State:\n" + gol );

	}
}
