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
	 
	/**
	 * load a dummy map
	 */
	public void loadMap() {
		this.mapWidth = 64;
		this.mapHeight = 48;
		this.walls = new ArrayList<int[]>();
		walls.add( new int[]{0, 0, 64, 0} );
		walls.add( new int[]{64, 0, 64, 48} );
		walls.add( new int[]{64, 48, 0, 48} );
		walls.add( new int[]{0, 48, 0, 0} );
		walls.add( new int[]{14, 24, 50, 24} );
		walls.add( new int[]{32, 10, 32, 38} );


		
		this.checkpoints = new ArrayList<int[]>();
		checkpoints.add( new int[]{0, 24, 14, 24} );
		checkpoints.add( new int[]{14, 0, 14, 24} );
		checkpoints.add( new int[]{50, 0, 50, 24} );
		checkpoints.add( new int[]{50, 24, 64, 24} );
		checkpoints.add( new int[]{32, 38, 32, 48} );
		checkpoints.add( new int[]{0, 24, 14, 24} );
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
            ex.printStackTrace();
        }
    }
    
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
