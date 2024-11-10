package enemybullets;
//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to hold dagger information

import java.awt.Rectangle;

public class Dagger extends Bullet {
	// Description: Constructor method
	// Parameters: Color, x position, y position, speed, angle
	// Return: ---
	public Dagger(String color, double xPos, double yPos, double speed, double angle) {
		super(color, 96, xPos, yPos, speed, angle);
		
		// Size and Hitbox
		width = 20;
		height = 20;
		hitbox = new Rectangle(5, 5);
	}
	
	// Description: Overridden constructor method
	// Parameters: Color, x position, y position, speed, angle, acceleration
	// Return: ---
	public Dagger(String color, double xPos, double yPos, double speed, double angle, double acceleration) {
		this(color, xPos, yPos, speed, angle);

		xAcc = acceleration * Math.cos(Math.toRadians(angle + 90));
		yAcc = acceleration * Math.sin(Math.toRadians(angle + 90));
	}
}
