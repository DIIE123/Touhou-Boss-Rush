package playerbullets;
//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to hold player 1 bullet information
import java.awt.Rectangle;

import enemybullets.Bullet;

public class Player1Bullet extends Bullet {
	// Description: Constructor method
	// Parameters: x position, y position, speed, angle
	// Return: ---
	public Player1Bullet(double xPos, double yPos, double speed, double angle) {
		super("red", 128, xPos, yPos, speed, angle);
		
		// Size and Hitbox
		damage = 1;
		width = 20;
		height = 20;
		hitbox = new Rectangle(10, 10);
	}
}
