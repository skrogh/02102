import java.util.*;
import java.io.*;

public class Map {
	
	private int mapHeight;
	private int mapWidth;
	
	private int xStart;
	private int yStart;
	
	private ArrayList<int[]> walls;
	private ArrayList<int[]> checkpoints; // first is startline, last is Finishline.
	
	public Map() {
		this.xStart = 7;
		this.yStart = 24;
		this.mapHeight = 0;
		this.mapWidth = 0;
		this.walls = new ArrayList<int[]>();
		this.checkpoints = new ArrayList<int[]>();
        loadMapFromFile();
	}
	
	
	public int[] getSize() {
		return new int[] {mapWidth, mapHeight};
	}
	
	public int[] getStart() {
		return new int[] {xStart, yStart};
	}
	
	public ArrayList<int[]> getWalls() {
		return walls;
	}
	
	public ArrayList<int[]> getCheckpoints() {
		return checkpoints;
	}
	
    /*================================================================
     * loadMapFromFile()
     *  Steps through the mapfile and instantiates the objects
     *  described
     *================================================================
     */
    public void loadMapFromFile() {
        String filename = getFileName();
        try {
            Scanner lineScanner = new Scanner( new FileReader( filename ) );
        
            while ( lineScanner.hasNextLine() ) {
                int xStart, yStart, xEnd, yEnd;
                Scanner elementScanner = new Scanner( lineScanner.nextLine() );
                xStart = elementScanner.nextInt();
                yStart = elementScanner.nextInt();
                xEnd = elementScanner.nextInt();
                yEnd = elementScanner.nextInt();
                int[] posArray = new int[]{ xStart, yStart, xEnd, yEnd };
                String element = elementScanner.next();
                if ( element.equals( "wall" ) )
                    walls.add( posArray );
                else if ( element.equals( "checkpoint" ) )
                    checkpoints.add( posArray );
                else if ( element.equals( "startpoint" ) ) {
                    this.xStart = xStart;
                    this.yStart = yStart;
                } else if ( element.equals( "gridsize" ) ) {
                    this.mapWidth = 640 / xStart; //xStart is gridSize
                    this.mapHeight = 480 / xStart;
                }
            }
        }
        catch(FileNotFoundException ex) {
            System.out.println( "ERROR LOADING MAP PLEASE TRY AGAIN " );
            walls.clear();
            checkpoints.clear();
            loadMapFromFile();
        }
    }
    
    /*==================================================================
     * getFileName()
     *  Prompts the user for a filename and appends it to the
     *  mapfile path.
     *==================================================================
     */
    public String getFileName() {
        System.out.println( "Enter the name of the map file to be loaded eg. \"map1\" " );
        Scanner inputScanner = new Scanner( System.in );
        String mapName = inputScanner.next();
            
		String filename = "maps" + File.separator + mapName + ".map";
        File attemptFile = new File( filename );
        if ( attemptFile.exists() ) {
            return filename;
        }
        else {
            System.out.println( "The file was not found, try again" );
            return getFileName();
        }

    }
}
