import java.util.*;

public class Player extends GameObject {
	
	ArrayList<int[]> path; //x, y, alive/dead
	int xSpeed, ySpeed;
	
	public Player( int x, int y ){
		ID = IDs.PLAYER;
		collidable = true;
		xPos = x;
		yPos = y;
		xSpeed = 0;
		ySpeed = 0;
		path = new ArrayList<int[]>();
		path.add( new int[]{xPos, yPos, 0} );
		path.add( new int[]{xPos, yPos, 0} );
		
		collisionBox = new ArrayList<int[]>();
	}
	
	public void render() {
		int[] pointP = path.get(0);
		for ( int[] point : path ) {
			if ( point[2] == 1 )
				StdDraw.setPenColor( StdDraw.RED );
			else
				StdDraw.setPenColor( StdDraw.BOOK_BLUE );
			StdDraw.setPenRadius( 0.007 );
			StdDraw.line( pointP[0], pointP[1], point[0], point[1] );
			pointP = point;
		}
		StdDraw.setPenColor( StdDraw.GREEN );
		StdDraw.setPenRadius( 0.01 );
		StdDraw.point( xPos, yPos );
	}
	
	public void update() {
		collisionBox = new ArrayList<int[]>();
		collisionBox.add( new int[]{xPos, yPos,
				path.get( path.size() - 2 )[0], path.get( path.size() - 2 )[1]} );
	}
	
	public void movePlayer( int direction ) {
		switch ( direction ) {
		case 1: //DOWN LEFT
			xSpeed--;
			ySpeed++;
			break;
		case 2: //DOWN
			ySpeed++;
			break;
		case 3: //DOWN RIGHT
			xSpeed++;
			ySpeed++;
			break;
		case 4: //LEFT
			xSpeed--;
			break;
		case 5: //NOP
			break;
		case 6: //RIGHT
			xSpeed++;
			break;
		case 7: //UP LEFT
			xSpeed--;
			ySpeed--;
			break;
		case 8: //UP
			ySpeed--;
			break;
		case 9: //UP RIGHT
			xSpeed++;
			ySpeed--;
			break;
		}
		xPos += xSpeed;
		yPos += ySpeed;
		path.add( new int[]{xPos, yPos, 0} );
	}
	
	public void collided( GameObject object ) {
		System.out.println( "DERP DU' DØD!" );
		xSpeed = 0;
		ySpeed = 0;
		xPos = path.get( path.size() - 2 )[0];
		yPos = path.get( path.size() - 2 )[1];
		path.add( new int[]{xPos, yPos, 1} );
		path.add( new int[]{xPos, yPos, 0} );
		
	}
	
	public void keyPressed( int key ) {
		if ( ( key > 0x30 ) && ( key < 0x3A ) )
			movePlayer( key - 0x30 );
	}
	
}
