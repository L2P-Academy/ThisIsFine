package model;

// author: pombyte

public class CameraModel {
	

	// attributes 
   private int maxAngle;
   private int turnSpeed;
   private int maxRange;
   private int detectionPersec;
   private int currentAngle;
   private double posX;
   private double posY;
   
   
   // constructor
   public CameraModel(int maxAngle, int turnSpeed, int maxRange, int detectionPersec, int currentAngle, double posX, double posY) {
       this.maxAngle = maxAngle;
       this.turnSpeed = turnSpeed;
       this.maxRange = maxRange;
       this.detectionPersec = detectionPersec;
       this.currentAngle = currentAngle;
       this.posX = posX;
       this.posY = posY;
   }


   
   // getter & setter
   
   
   public int getMaxAngle() {
	return maxAngle;
   }


   public void setMaxAngle(int maxAngle) {
	this.maxAngle = maxAngle;
   }


   public int getTurnSpeed() {
	return turnSpeed;
   }


   public void setTurnSpeed(int turnSpeed) {
	this.turnSpeed = turnSpeed;
   }


   public int getMaxRange() {
	return maxRange;
   }


   public void setMaxRange(int maxRange) {
	this.maxRange = maxRange;
   }


   public int getDetectionPersec() {
	return detectionPersec;
   }


   public void setDetectionPersec(int detectionPersec) {
	this.detectionPersec = detectionPersec;
   }


   public int getCurrentAngle() {
	return currentAngle;
   }


   public void setCurrentAngle(int currentAngle) {
	this.currentAngle = currentAngle;
   }


   public double getPosX() {
	return posX;
   }


   public void setPosX(double posX) {
	this.posX = posX;
   }


   public double getPosY() {
	return posY;
   }


   public void setPosY(double posY) {
	this.posY = posY;
   }
   
  
   
   

   // methods
    
public boolean isTargetInRange(double targetX, double targetY) {
	double deltaX = targetX - this.posX;
	double deltaY = targetY - this.posY;
	
	double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
	
	return distance <= this.maxRange;
}


public void turnTo(int targetAngle) {
	if (this.currentAngle < targetAngle) {
		this.currentAngle += this.turnSpeed;
		
	if (this.currentAngle > targetAngle) {
		this.currentAngle = targetAngle;
		
		}
	} else if (this.currentAngle > targetAngle) {
		this.currentAngle -= this.turnSpeed;
		if (this.currentAngle < targetAngle) {
			this.currentAngle = targetAngle;
			}
		}
	}
}
