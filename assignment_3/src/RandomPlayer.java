import java.util.ArrayList;
import java.util.Random;



public class RandomPlayer extends Player {
	Random random;
	public RandomPlayer( int x, int y ){
		super( x, y );
		random = new Random();
	}
	
	public void update() {
		super.movePlayer( random.nextInt( 8 ) + 1 );
		super.update();
	}
}
