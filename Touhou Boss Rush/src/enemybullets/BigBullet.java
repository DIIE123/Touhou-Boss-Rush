package enemybullets;
//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to hold big bullet information

import java.awt.Rectangle;
import java.util.Arrays;

public class BigBullet extends Bullet {
	
	// Description: Constructor method
	// Parameters: Color, x position, y position, speed, angle
	// Return: ---
	public BigBullet(String color, double xPos, double yPos, double speed, double angle) {
		super(color, 16, xPos, yPos, speed, angle);
		
		// Size and Hitbox
		width = 80;
		height = 80;
		hitbox = new Rectangle(40, 40);
		
		// Sprite Overwrite
		colorArray = new String[4];
		colorArray[0] = "red";
		colorArray[1] = "blue";
		colorArray[2] = "green";
		colorArray[3] = "yellow";
		
		int index = Arrays.asList(colorArray).indexOf(color);
		originalSprite = bulletSpriteSheet.getSubimage(9 + 64 * index, 466, 64, 64);
		sprite = rotateSprite(originalSprite, Math.toDegrees(angle) + 180);
	}
	
	// Description: Overridden constructor method
	// Parameters: Color, x position, y position, speed, angle, acceleration
	// Return: ---
	public BigBullet(String color, double xPos, double yPos, double speed, double angle, double acceleration) {
		this(color, xPos, yPos, speed, angle);

		xAcc = acceleration * Math.cos(Math.toRadians(angle + 90));
		yAcc = acceleration * Math.sin(Math.toRadians(angle + 90));
	}
}
