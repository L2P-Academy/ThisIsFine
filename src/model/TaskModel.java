package model;

// merhartmike-hue TaskModel

public class TaskModel {

	// Attribute

	private int id;
	private String title;
	private String description;
	private boolean isActive;
	private boolean isCompleted;

	// Constructor

	public TaskModel(String title, String description, int id, boolean isActive, boolean isCompleted) {

		this.title = title;
		this.description = description;
		this.id = id;
		this.isActive = isActive;
		this.isCompleted = isCompleted;
	}

	// Getter - Setter

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
