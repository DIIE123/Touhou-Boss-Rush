package playerbullets;
import java.awt.Image;
//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to hold player 2 bomb information
import java.awt.Rectangle;

import enemybullets.Bullet;
import players.*;

public class Player2Bomb extends Bullet {
	private static Image bombSprite;
	private Player player;
	
	// Description: Constructor method
	// Parameters: player
	// Return: ---
	public Player2Bomb(Player player) {
		super("red", 0, 0, 0, 0, 180);
		
		// Variables
		this.player = player;
		damage = 1;
		shouldRemove = false;
		width = 800;
		height = 700;
		hitbox = new Rectangle(800, 700);
		
		// Sprite Overwrite
		sprite = bombSprite;
	}
	
	// Description: Method to override update
	// Parameters: ---
	// Return: ---
	public void update() {
		xPos = player.getX();
		yPos = player.getY() - height/2;
		
		hitbox.x = (int) (xPos - hitbox.width/2);
		hitbox.y = (int) (yPos - hitbox.height/2);
	}
	
	// Description: Setter method
	// Parameters: sprite
	// Return: ---
	public static void setSprite(Image sprite) {
		bombSprite = sprite;
	}
}
