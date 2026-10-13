package model;

//author: Whatchamaycall

public class PlayerModel {

	// attributes
	private int positionX, positionY, speed;
	private String name;

	// constructor
	public PlayerModel(int positionX, int positionY, int speed, String name) {
		super();
		this.positionX = positionX;
		this.positionY = positionY;
		this.speed = speed;
		this.name = name;
	}

	// methods
	public int[] getPosition() {
		return new int[] { positionX, positionY };
	}

	public void setPosition(int x, int y) {
		this.positionX = x;
		this.positionY = y;
	}

	// getter & setter
	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
