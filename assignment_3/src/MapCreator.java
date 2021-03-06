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
	
    private static int pStartX;
    private static int pStartY;
    private static int gridSize;
	private static FileWriter fstream;
	private static BufferedWriter out;
	
	private static ArrayList<Wall> addedWalls;
	private static ArrayList<Checkpoint> addedCheckpoints;
	
    /*==============================================================
     * main()
     * When program is running: 
     *      SHIFT+S to save startpoint
     *      SHIFT+C to save checkpoint
     *      SHIFT+W to save wall
     *      ESCAPE to save map and quit
     * No map will be saved unless there is at least a checkpoint and
     * a wall.
     * =============================================================
     */
	public static void main( String args[] ) {
		addedWalls = new ArrayList<Wall>();
		addedCheckpoints = new ArrayList<Checkpoint>();
		
        gridSize = getGridSize();
		
		boolean exit_and_save = false;
		
		StdDraw.setCanvasSize( 640, 480 );
		StdDraw.setXscale( 0, 640 / gridSize );
		StdDraw.setYscale( 480 / gridSize, 0 );
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
                case KeyEvent.VK_S:
                    recordStartPoint();
                    break;
				}
			}
            render();
		}
		
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
	
    /*=================================================================
     * recordWall()
     * Instantiates a wall object and lets a user modify it's endpoints
     * until the mouse is pressed.
     * ================================================================
     */
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
	
    /*==============================================================
     * recordCheckpoint()
     * same as recordWall(), just for checkpoints.
     * =============================================================
     */
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
	
    /*===============================================================
     * saveToFIle()
     * Saves the current map objects to a file in the format
     * "int, int, int, int, string"
     * ==============================================================
     */
	public static void saveToFile() {
        if ( addedWalls.size() == 0 || addedCheckpoints.size() == 0 ) {
            System.out.println( "No map saved, you cannot save a map without walls or checkpoints" );
            return;
        }
		String fileName = getFileName();
		try {
			fstream = new FileWriter( fileName );
		} catch (IOException e1) {
            return;
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
            return;
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
                return;
			}
		}
		
		for ( int i = 1; i < addedCheckpoints.size(); i++ ) {
			xStart = addedCheckpoints.get( i ).xPos;
			yStart = addedCheckpoints.get( i ).yPos;
			xEnd = addedCheckpoints.get( i ).xEnd;
			yEnd = addedCheckpoints.get( i ).yEnd;
			
			saveString = Integer.toString( xStart ) + " " + Integer.toString( yStart ) + " " + Integer.toString( xEnd )
					+ " " + Integer.toString( yEnd ) + " " + "checkpoint" + "\n";
			try {
				out.write( saveString );
			} catch (IOException e) {
                return;
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
		} catch (IOException e) {
            return;
		}
        
		xStart = gridSize;
		yStart = gridSize;
		xEnd = gridSize; 
		yEnd = gridSize;
		
		saveString = Integer.toString( xStart ) + " " + Integer.toString( yStart ) + " " + Integer.toString( xEnd )
				+ " " + Integer.toString( yEnd ) + " " + "gridsize" + "\n";
		try {
			out.write( saveString );
		} catch (IOException e) {
            return;
		}
        
        xStart = pStartX;
        yStart = pStartY;
        xEnd = pStartX;
        yEnd = pStartY;

		saveString = Integer.toString( xStart ) + " " + Integer.toString( yStart ) + " " + Integer.toString( xEnd )
				+ " " + Integer.toString( yEnd ) + " " + "startpoint" + "\n";
        
		try {
			out.write( saveString );
            System.out.println( " Map saved" );
		} catch (IOException e) {
            return;
		}

		try {
			out.close();
		} catch (IOException e) {
            return;
		}
	}
    

    /*=================================================================
     * render()
     * Render the object to be displayed in the map creator window
     * ===============================================================
     */
	public static void render() {
        StdDraw.clear( StdDraw.WHITE );
		for ( int i = 0; i < addedWalls.size(); i++ ) 
			addedWalls.get( i ).render();
		
		for ( int i = 0; i < addedCheckpoints.size(); i++ ) {
			addedCheckpoints.get( i ).render();
		}
        StdDraw.setPenColor( StdDraw.GREEN );
        StdDraw.point(pStartX, pStartY);

        //draw grid
        StdDraw.setPenRadius( 0.001 );
        for( int i = 0; i <= 640 / gridSize; i++ ) {
            StdDraw.setPenColor( StdDraw.LIGHT_GRAY );
            StdDraw.line( i, 0, i, 480 / gridSize );
        }

        for( int i = 0; i <= 480 / gridSize; i++ ) {
            StdDraw.setPenColor( StdDraw.LIGHT_GRAY );
            StdDraw.line( 0, i, 640 / gridSize, i );
        }

        StdDraw.show(0);
	}
    

    /*================================================================
     * recordStartPoint()
     * Records the point at which the player will start when the
     * map is loaded.
     * ===============================================================
     */
    public static void recordStartPoint() {
        pStartX = (int) StdDraw.mouseX();
        pStartY = (int) StdDraw.mouseY();
        render();
    }
    
    /*================================================================
     * getGridSize()
     * Returns a gridsize entered by the player
     * ==============================================================
     */
    public static int getGridSize() {
        System.out.println( "Please enter a gridsize >=1" );
        int gridSize = 0;
        try {
            Scanner inputScanner = new Scanner( System.in );
            gridSize = inputScanner.nextInt();
            if ( gridSize <= 1 ) 
                return getGridSize();
            else
                return gridSize;
        }
        catch(Exception ex) {
            return getGridSize();
        }
    }
}
