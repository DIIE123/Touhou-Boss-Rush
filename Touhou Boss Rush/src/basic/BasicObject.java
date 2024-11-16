package basic;
// Names: Dylan Wang and Simon Gao
// Date: June 14, 2024
// Description: Class to hold information on moving objects

import java.awt.*;
import java.io.*;

import javax.sound.sampled.*;

public abstract class BasicObject {
	// Instance Variables
	protected double xPos;
	protected double yPos;
	protected double xAcc;
	protected double yAcc;
	protected double xVel;
	protected double yVel;
	protected int width;
	protected int height;
	protected Rectangle hitbox;
	protected Image sprite;
	
	// Description: Method to update position
	// Parameters: ---
	// Return: ---
	public void update() {
		if (xVel <= 0.0001 && xVel >= -0.0001)
			xAcc = 0;
		if (yVel <= 0.0001 && yVel >= -0.0001)
			yAcc = 0;
		
		xPos += xVel;
		yPos += yVel;
		xVel += xAcc;
		yVel += yAcc;
		
		
		hitbox.x = (int) (xPos - hitbox.width/2);
		hitbox.y = (int) (yPos - hitbox.height/2);
	}
	
	// Description: Method to move to a specific point at a set speed
	// Parameters: x and y position to move to, speed
	// Return: ---
	public void movePoint(double xPos, double yPos, double speed) {
		// Displacement
		double xDist = xPos - this.xPos;
		double yDist = yPos - this.yPos;
		
		// Trigonometry
		double angle = Math.atan2(yDist, xDist);
		xVel = speed * Math.cos(angle);
		yVel = speed * Math.sin(angle);
	}
	
	// Description: Method to object at a specific angle
	// Parameters: speed and angle
	// Return: ---
	public void moveAngle(double speed, double angle) {
		xVel = speed * Math.cos(Math.toRadians(angle + 90));
		yVel = speed * Math.sin(Math.toRadians(angle + 90));
	}
	
	// Description: Method to launch enemy to a specific position with deceleration
	// Parameters: x and y coordinates to move to, time it takes to move
	// Return: ---
	public void launch(double xPos, double yPos, double time) {
		// Displacement
		double xDist = xPos - this.xPos;
		double yDist = yPos - this.yPos;
		
		// Kinematics formulas
		xVel = xDist / time * 2 / 60;
		yVel = yDist / time * 2 / 60;
		xAcc = xVel / time * -1 / 60;
		yAcc = yVel / time * -1 / 60;
	}
	
	// Description: Method to play a sound
	// Parameters: File name
	// Return: ---
	public static void playSound(String file)
	{
	    try
	    {
	        Clip clip = AudioSystem.getClip();
	        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("Sounds/" + file));
	        clip.open(inputStream);
	        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	        volume.setValue(-15.0f);
	        clip.addLineListener(new CloseClipWhenDone());
	        clip.start();
	    }
	    catch (Exception e)
	    {
	    	System.out.println("Sound Error");
	    }        
	}
	
	// Description: Class to close a sound once it is done playing
	// Parameters: ---
	// Return: ---
	private static class CloseClipWhenDone implements LineListener
	{
	    public void update(LineEvent event)
	    {
	        if (event.getType().equals(LineEvent.Type.STOP))
	        {
	            Line soundClip = event.getLine();
	            soundClip.close();
	        }
	    }
	}
	
	// Description: Getter methods
	// Parameters: ---
	// Return: xPos, yPos, xAcc, width, height, hitbox, sprite
	public double getXTopLeft() {
		return xPos - width/2;
	}
	
	public double getYTopLeft() {
		return yPos - height/2;
	}
	
	public double getX() {
		return xPos;
	}
	
	public double getXAcc() {
		return xAcc;
	}
	
	public double getY() {
		return yPos;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public Image getImage() {
		return sprite;
	}
}
