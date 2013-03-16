import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;

/*=========================================================
 * MapCreator
 * 	Opens a map creation window. Lines that represent walls 
 * 	can be drawn and saved to a file
 * ========================================================
 */

public class MapCreator {
	
	private static FileWriter fstream;
	private static BufferedWriter out;
	
	private static ArrayList<Wall> addedWalls;
	private static ArrayList<Checkpoint> addedCheckpoints;
	
	public static void main( String args[] ) {
		
		
		addedWalls = new ArrayList<Wall>();
		addedCheckpoints = new ArrayList<Checkpoint>();
		
		
		boolean exit_and_save = false;
		
		StdDraw.setCanvasSize( 640, 480 );
		StdDraw.setXscale( 0, 640 );
		StdDraw.setYscale( 480, 0 );
        StdDraw.show();		
		
		while ( !exit_and_save ) {
			if( StdDraw.mousePressed() ) {
				System.out.println(StdDraw.mouseX() + " , " + StdDraw.mouseY() );
				try {
					Thread.currentThread().sleep(100);
				} catch (InterruptedException e) {
            		Thread.currentThread().interrupt();
				}
			}
			
			if( StdDraw.hasNextKeyTyped() ) {
				switch ( StdDraw.nextKeyTyped() ) {
				case KeyEvent.VK_ESCAPE:
					saveToFile();
					exit_and_save = true;
					break;
				case KeyEvent.VK_W:
					recordWall();
					break;
				case KeyEvent.VK_C:
					recordCheckpoint();
					break;
				}
			}
            //render();
		}
		
		System.out.println("durr");
	}
	
	
	/*=====================================================
	 * getFileName()
	 * 	Attempts to retrieve a filename for a mapfile
	 * 	in the order map0.map->mapN.map
	 * ====================================================
	 */
	public static String getFileName() {
		String fileName = "maps" + File.separator + "map";
		
		boolean valid_name_found = false;
		int mapCount = 0;
		
		while ( !valid_name_found ) {
			String attemptFileName = fileName + Integer.toString( mapCount ) + ".map";
			File attemptFile = new File( attemptFileName );
			
			if ( !attemptFile.exists( ) ) {
				fileName = attemptFileName;
				valid_name_found = true;
				try {
					attemptFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			mapCount++;
		}
		
		return fileName;
	}
	
	public static void recordWall() {
		int xStart = (int) StdDraw.mouseX();
		int yStart = (int) StdDraw.mouseY();
		
		int xEnd = xStart;
		int yEnd = yStart;
		
		Wall wall = new Wall( xStart, yStart, xEnd, yEnd );
		addedWalls.add( wall );

		while( !StdDraw.hasNextKeyTyped() ) {
			wall.xEnd = (int) StdDraw.mouseX();
			wall.yEnd = (int) StdDraw.mouseY();
            render();
			if (StdDraw.mousePressed() ) {
				break;
			}
		}
		
	}
	
	public static void recordCheckpoint() {
		int xStart = (int) StdDraw.mouseX();
		int yStart = (int) StdDraw.mouseY();
		
		int xEnd = xStart;
		int yEnd = yStart;
		
		Checkpoint checkpoint = new Checkpoint( xStart, yStart, xEnd, yEnd );
		addedCheckpoints.add( checkpoint );

		while( !StdDraw.hasNextKeyTyped() ) {

    		checkpoint.xEnd = (int) StdDraw.mouseX();
			checkpoint.yEnd = (int) StdDraw.mouseY();
            
		    render();
			if (StdDraw.mousePressed() ) {
				break;
			}
		}
		
	}
	
	public static void saveToFile() {
        if ( addedWalls.size() == 0 || addedCheckpoints.size() == 0 ) {
            System.out.println( "No map saved, you cannot save a map without walls or checkpoints" );
            return;
        }
		String fileName = getFileName();
		try {
			fstream = new FileWriter( fileName );
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		out = new BufferedWriter( fstream );
        String saveString = "";
		//save first checkpoint for starting line
		int xStart, yStart, xEnd, yEnd;
		xStart = addedCheckpoints.get( 0 ).xPos;
		yStart = addedCheckpoints.get( 0 ).yPos;
		xEnd = addedCheckpoints.get( 0 ).xEnd;
		yEnd = addedCheckpoints.get( 0 ).yEnd;
		
		saveString = Integer.toString( xStart ) + " " + Integer.toString( yStart ) + " " + Integer.toString( xEnd )
				+ " " + Integer.toString( yEnd ) + " " + "checkpoint" + "\n";
		try {
			out.write( saveString );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//save all walls and checkpoints (except first checkpoint)
		for (int i = 0; i < addedWalls.size(); i++ ) {
			xStart = addedWalls.get( i ).xPos;
			yStart = addedWalls.get( i ).yPos;
			xEnd = addedWalls.get( i ).xEnd;
			yEnd = addedWalls.get( i ).yEnd;
			
			saveString = Integer.toString( xStart ) + " " + Integer.toString( yStart ) + " " + Integer.toString( xEnd )
					+ " " + Integer.toString( yEnd ) + " " + "wall" + "\n";
			try {
				out.write( saveString );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for ( int i = 0; i < addedCheckpoints.size(); i++ ) {
			xStart = addedCheckpoints.get( i ).xPos;
			yStart = addedCheckpoints.get( i ).yPos;
			xEnd = addedCheckpoints.get( i ).xEnd;
			yEnd = addedCheckpoints.get( i ).yEnd;
			
			saveString = Integer.toString( xStart ) + " " + Integer.toString( yStart ) + " " + Integer.toString( xEnd )
					+ " " + Integer.toString( yEnd ) + " " + "checkpoint" + "\n";
			try {
				out.write( saveString );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//save first checkpoint as finish line
		xStart = addedCheckpoints.get( 0 ).xPos;
		yStart = addedCheckpoints.get( 0 ).yPos;
		xEnd = addedCheckpoints.get( 0 ).xEnd;
		yEnd = addedCheckpoints.get( 0 ).yEnd;
		
		saveString = Integer.toString( xStart ) + " " + Integer.toString( yStart ) + " " + Integer.toString( xEnd )
				+ " " + Integer.toString( yEnd ) + " " + "checkpoint" + "\n";
		try {
			out.write( saveString );
            System.out.println( " Map saved" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void render() {
        StdDraw.clear( StdDraw.WHITE );
		for ( int i = 0; i < addedWalls.size(); i++ ) 
			addedWalls.get( i ).render();
		
		for ( int i = 0; i < addedCheckpoints.size(); i++ ) {
			addedCheckpoints.get( i ).render();
		}

        StdDraw.show(0);
	}
}
