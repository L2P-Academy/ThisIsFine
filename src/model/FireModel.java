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
    
    //Getter Setter
    public int getIntensity() {
        return intensity;
    }
    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }
    
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    //method
    public String method(String type) {
		return type;
    }
}