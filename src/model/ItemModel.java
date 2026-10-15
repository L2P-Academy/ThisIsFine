package model;

// author: Whatchamaycall

public class ItemModel {

	// attributes
	private int id, positionX, positionY;
	private String name;
	private boolean collected, visible;

	// constructor
	public ItemModel(int positionX, int positionY, int id, String name, boolean collected, boolean visible) {
		super();
		this.positionX = positionX;
		this.positionY = positionY;
		this.id = id;
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
		return this.collected;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
