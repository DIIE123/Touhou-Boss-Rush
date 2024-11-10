package enemybullets;
//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to hold butterfly bullet information

import java.awt.Rectangle;
import java.util.Arrays;

public class ButterflyBullet extends Bullet {
	// Description: Constructor method
	// Parameters: Color, x position, y position, speed, angle
	// Return: ---
	public ButterflyBullet(String color, double xPos, double yPos, double speed, double angle) {
		super(color, 0, xPos, yPos, speed, angle);
		
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
	// Parameters: Color, x position, y position, speed, angle, acceleration
	// Return: ---
	public ButterflyBullet(String color, double xPos, double yPos, double speed, double angle, double acceleration) {
		this(color, xPos, yPos, speed, angle);

		xAcc = acceleration * Math.cos(Math.toRadians(angle + 90));
		yAcc = acceleration * Math.sin(Math.toRadians(angle + 90));
	}
}
