package enemybullets;
// Names: Dylan Wang and Simon Gao
// Date: June 14, 2024
// Description: Class to hold general bullet information

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.Arrays;

import basic.BasicObject;

public abstract class Bullet extends BasicObject{
	// Variables
	protected static BufferedImage bulletSpriteSheet;
	
	protected int spriteY;
	protected double damage;
	protected double speed;
	protected double angle;
	protected boolean shouldRemove;
	protected Image originalSprite;
	protected String[] colorArray = 
		{"black", "dark red", "red", "dark purple", "purple", "dark blue", "blue", "dark aqua",
			"aqua", "dark green", "green", "light green", "dark yellow", "yellow", "orange", "white"};
	
	// Description: Constructor methods
	// Parameters: x and y position (center), speed, angle (straight down is 0 degrees, clockwise)
	// Return: ---
	public Bullet(String color, int spriteY, double xPos, double yPos, double speed, double angle) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.angle = angle;
		this.speed = speed;
		
		xVel = speed * Math.cos(Math.toRadians(angle + 90));
		xAcc = 0;
		yVel = speed * Math.sin(Math.toRadians(angle + 90));
		yAcc = 0;
		shouldRemove = true;
		
		// Sprites
		int index = Arrays.asList(colorArray).indexOf(color);
		this.spriteY = spriteY;
		originalSprite = bulletSpriteSheet.getSubimage(10 + index * 16, spriteY, 16, 16);
		sprite = rotateSprite(originalSprite, angle + 180);
	}
	
	// Description: Method to override moveAngle
	// Parameters: speed and angle
	// Return: ---
	public void moveAngle(double speed, double angle) {
		super.moveAngle(speed, angle);
		sprite = rotateSprite(originalSprite, angle + 180);
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
		sprite = rotateSprite(originalSprite, Math.toDegrees(angle) + 90);
		xVel = speed * Math.cos(angle);
		yVel = speed * Math.sin(angle);
	}
	
	// Description: Method to override launch to rotate sprite
	// Parameters: x and y position to launch to, time
	// Return: ---
	public void launch(double xPos, double yPos, double time) {
		super.launch(xPos, yPos, time);
		// Displacement
		double xDist = xPos - this.xPos;
		double yDist = yPos - this.yPos;
		
		// Sprite rotation
		double angle = Math.atan2(yDist, xDist);
		sprite = rotateSprite(originalSprite, Math.toDegrees(angle) + 90);
	}
	
	// Description: Method to rotate sprite
	// Parameters: Image and Angle
	// Return: Rotated Image
	public Image rotateSprite(Image image, double angle) {
		BufferedImage img = (BufferedImage) image;
		double rotationRequired = Math.toRadians (angle);
        double locationX = img.getWidth() / 2.0;
        double locationY = img.getHeight() / 2.0;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);         
        BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType()); 
        op.filter(img, newImage);

        return(newImage);
	}
	
	// Description: Getter methods
	// Parameters: ---
	// Return: damage, shouldRemove, angle, speed
	public double getDamage() {
		return damage;
	}
	
	public boolean getShouldRemove() {
		return shouldRemove;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public double getSpeed() {
		return speed;
	}
		
	// Description: Setter methods
	// Parameters: spriteSheet
	// Return: ---
	public static void setSpriteSheet(BufferedImage spriteSheet) {
		bulletSpriteSheet = spriteSheet;
	}
}
