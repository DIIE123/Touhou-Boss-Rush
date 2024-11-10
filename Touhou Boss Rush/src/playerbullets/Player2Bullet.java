package playerbullets;
//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to hold player 2 bullet information
import java.awt.Rectangle;

import enemybullets.Bullet;

public class Player2Bullet extends Bullet {
	// Description: Constructor method
	// Parameters: x position, y position, speed
	// Return: ---
	public Player2Bullet(double xPos, double yPos, double speed) {
		super("yellow", 128, xPos, yPos, speed, 180);
		
		// Size and Hitbox
		damage = 2;
		width = 20;
		height = 20;
		hitbox = new Rectangle(10, 10);
	}
}
