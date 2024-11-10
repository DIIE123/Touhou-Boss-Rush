package playerbullets;
//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to hold player 3 bomb information
import java.awt.Rectangle;

import enemybullets.Bullet;

public class Player3Bomb extends Bullet {
	private int angle;
	
	// Description: Constructor method
	// Parameters: xPos, yPos
	// Return: ---
	public Player3Bomb(double xPos, double yPos) {
		super("red", 0, xPos, yPos, 2, 180);
		
		// Variables
		angle = 0;
		damage = 200;
		width = 200;
		height = 200;
		hitbox = new Rectangle(200, 200);
		
		// Sprite Overwrite
		originalSprite = bulletSpriteSheet.getSubimage(333, 32, 64, 64);
		sprite = originalSprite;
	}
	
	// Description: Method to override update
	// Parameters: ---
	// Return: ---
	public void update() {
		super.update();
		sprite = rotateSprite(originalSprite, angle += 3);
	}
}
