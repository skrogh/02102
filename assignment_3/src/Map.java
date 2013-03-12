import java.util.ArrayList;


public class Map {
	
	private int mapHeight;
	private int mapWidth;
	
	private int xStart;
	private int yStart;
	
	private ArrayList<int[]> walls;
	private ArrayList<int[]> Checkpoints; // first is startline, last is Finishline.
	
	public Map() {
		this.mapHeight = 0;
		this.mapWidth = 0;
		this.walls = new ArrayList<int[]>();
		loadMap(); // load dummy map
	}
	
	public void loadMap() { // dummy
		this.mapWidth = 640;
		this.mapHeight = 480;
		this.walls = new ArrayList<int[]>();
		walls.add( new int[]{0, 0, 640, 0} );
		walls.add( new int[]{640, 0, 640, 480} );
		walls.add( new int[]{640, 480, 0, 480} );
		walls.add( new int[]{0, 480, 0, 0} );
	}
	
	public int[] getSize() {
		return new int[] {mapWidth, mapHeight};
	}
	
	public ArrayList<int[]> getWalls() {
		return walls;
	}
	
}
