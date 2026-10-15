package model;

//author: ewald87

public class GameStateModel {
	
	//	attributes
	private int worldSizeX, worldSizeY, playerX, playerY;
	private String playTime;

	//	constructor
	public GameStateModel(int worldSizeX, int worldSizeY, int playerX, int playerY, String playTime) {
		
		super();
		this.worldSizeX = worldSizeX;
		this.worldSizeY = worldSizeY;
		this.playerX = playerX;
		this.playerY = playerY;
		this.playTime = playTime;
	}

	//	getter and setter
	public int getWorldSizeX() {
		return worldSizeX;
	}

	public void setWorldSizeX(int worldSizeX) {
		this.worldSizeX = worldSizeX;
	}

	public int getWorldSizeY() {
		return worldSizeY;
	}

	public void setWorldSizeY(int worldSizeY) {
		this.worldSizeY = worldSizeY;
	}

	public int getPlayerX() {
		return playerX;
	}

	public void setPlayerX(int playerX) {
		this.playerX = playerX;
	}

	public int getPlayerY() {
		return playerY;
	}

	public void setPlayerY(int playerY) {
		this.playerY = playerY;
	}

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}
	
	
	
}
