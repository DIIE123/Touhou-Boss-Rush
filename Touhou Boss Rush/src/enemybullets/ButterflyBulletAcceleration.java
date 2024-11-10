package enemybullets;
//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to hold butterfly bullet (with circular acceleration) information
//Used for last spell attack of the hard boss

import java.awt.Rectangle;
import java.util.Arrays;

public class ButterflyBulletAcceleration extends Bullet {
	private double acceleration;
	private double angleIncrease;
	
	// Description: Constructor method
	// Parameters: Color, x position, y position, speed, angle
	// Return: ---
	public ButterflyBulletAcceleration(String color, double xPos, double yPos, double speed, double angle) {
		super(color, 0, xPos, yPos, speed, angle);
		acceleration = 0;
		angleIncrease = 0;
		
		// Size and Hitbox
		width = 40;
		height = 40;
		hitbox = new Rectangle(10, 10);
		
		// Sprite Overwrite
		int index = Arrays.asList(colorArray).indexOf(color);
		originalSprite = bulletSpriteSheet.getSubimage(9 + 32 * index/2, 338, 32, 32);
		sprite = rotateSprite(originalSprite, angle + 180);
	}

	// Description: Overridden constructor method
	// Parameters: Color, x position, y position, speed, angle, acceleration, circular angle
	// Return: ---
	public ButterflyBulletAcceleration(String color, double xPos, double yPos, double speed, double angle, double acceleration, double angleIncrease) {
		this(color, xPos, yPos, speed, angle);

		this.acceleration = acceleration;
		this.angleIncrease = angleIncrease;
		xAcc = acceleration * Math.cos(Math.toRadians(angle + 90));
		yAcc = acceleration * Math.sin(Math.toRadians(angle + 90));
	}
	
	// Description: Method to override update
	// Parameters: ---
	// Return: ---
	public void update() {
		xPos += xVel;
		yPos += yVel;
		xVel += xAcc;
		yVel += yAcc;
		
		hitbox.x = (int) (xPos - hitbox.width/2);
		hitbox.y = (int) (yPos - hitbox.height/2);
		
		angle += angleIncrease;
		xAcc = acceleration * Math.cos(Math.toRadians(angle + 90));
		yAcc = acceleration * Math.sin(Math.toRadians(angle + 90));
	}
}