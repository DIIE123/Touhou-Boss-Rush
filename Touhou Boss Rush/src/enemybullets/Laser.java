package enemybullets;
//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to hold laser information

import java.awt.Rectangle;

public class Laser extends Bullet {
	// Description: Constructor method
	// Parameters: Color, x position, y position, speed, angle
	// Return: ---
	public Laser(String color, double xPos, double yPos, double speed, double angle) {
		super(color, 16, xPos, yPos, speed, angle);
		
		// Size and Hitbox
		width = 20;
		height = 20;
		hitbox = new Rectangle(12, 12);
	}
	
	// Description: Overridden constructor method
	// Parameters: Color, x position, y position, speed, angle, acceleration
	// Return: ---
	public Laser(String color, double xPos, double yPos, double speed, double angle, double acceleration) {
		this(color, xPos, yPos, speed, angle);

		xAcc = acceleration * Math.cos(Math.toRadians(angle + 90));
		yAcc = acceleration * Math.sin(Math.toRadians(angle + 90));
	}
}