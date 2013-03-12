import java.util.*;

public class GameObject {
	
	public static enum IDs{ BACKGROUND, MAP, RACE_CAR, AI, PLAYER, TILE, MISC };
	public static enum states{ ALIVE, DEAD, INVISIBLE };
	
	protected int xPos;
	protected int yPos;
	
	protected int xTile;
	protected int yTile;
	
	protected IDs ID;
	protected states state;
	
	protected boolean collidable;
	
	public GameObject() {
		
	}
	
	public void update() {
		
	}
	
	public void render() {
		
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos( int xPos ) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos( int yPos ) {
		this.yPos = yPos;
	}

	public IDs getId() {
		return ID;
	}


	public states getState() {
		return state;
	}

	public void setState( states state ) {
		this.state = state;
	}

	public boolean isCollidable() {
		return collidable;
	}
	
	
}
