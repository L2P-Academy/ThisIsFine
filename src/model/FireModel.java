package model;

//author: DschungelTaube

public class FireModel {

    //attributes
    public int intensity;
    public int duration;
    public int size;
    public String type;

    //constructor
    public FireModel(int intensity, int duration, int size, String type) {
        this.intensity = intensity;
        this.duration = duration;
        this.size = size;
        this.type = type;
    }

    //method
    public String method(String type) {
		return type;
    }
}