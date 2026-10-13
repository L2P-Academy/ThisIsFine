package model;

// author: Whatchamaycall

public class ItemModel {

	// attributes
	private int positionX, positionY, width, height;
	private String name;
	private boolean collected, visible;

	// constructor
	public ItemModel(int positionX, int positionY, int width, int height, String name, boolean collected,
			boolean visible) {
		super();
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
		this.name = name;
		this.collected = collected;
		this.visible = visible;
	}

	// methods
	public void collectItem() {
		this.collected = true;
		this.visible = false;
	}

	public void showItem() {
		this.visible = true;
	}

	public void hideItem() {
		this.visible = false;
	}

	public boolean isAvailable() {
		return !this.collected;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
