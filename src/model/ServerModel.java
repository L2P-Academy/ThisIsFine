package model;

// author: ReallyNotMyName

public class ServerModel {
	
	// --- Attribute ---
	private String name;
	private int heatLevel;
	
	// --- Konstruktor ---
	public ServerModel(String name, int heatLevel) {
		this.name = name;
		this.heatLevel = heatLevel;
	}
	
	// --- Getter ---
	public String getName () {
		return name;
	}
	
	public int getHeatLevel () {
		return heatLevel;
	}
	
	// --- Setter ---
	public void setName (String name) {
		this.name = name;
	}
	
	public void setHeatLevel (int heatLevel) {
		this.heatLevel = heatLevel;
	}
	
	
	
}
