import java.util.ArrayList;


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
		loadMap(); // load dummy map
	}
	
	public void loadMap() { // dummy
		this.mapWidth = 64;
		this.mapHeight = 48;
		this.walls = new ArrayList<int[]>();
		walls.add( new int[]{0, 0, 64, 0} );
		walls.add( new int[]{64, 0, 64, 48} );
		walls.add( new int[]{64, 48, 0, 48} );
		walls.add( new int[]{0, 48, 0, 0} );
		walls.add( new int[]{14, 24, 50, 24} );
		
		this.checkpoints = new ArrayList<int[]>();
		checkpoints.add( new int[]{0, 24, 14, 24} );
		checkpoints.add( new int[]{32, 0, 32, 24} );
		checkpoints.add( new int[]{50, 24, 64, 24} );
		checkpoints.add( new int[]{32, 24, 32, 48} );
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
	
}
