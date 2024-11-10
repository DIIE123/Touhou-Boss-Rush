package players;
//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to hold player 1 information

import java.awt.image.BufferedImage;

import playerbullets.*;

public class Player1 extends Player {
	private static BufferedImage reimuSpriteSheet;
	private static BufferedImage portrait;
	
	// Description: Constructor method
	// Parameters: ---
	// Return: ---
	public Player1() {
		// Variables
		super(reimuSpriteSheet);
		sprite = portrait;
		speed = 4;
		isBombing = false;
		bombName = "Fantasy Seal";
	}
	
	// Description: Method to shoot bullets
	// Parameters: ---
	// Return: ---
	public void shoot(boolean focused) {
		// Unfocused: spread of 4 normal bullets and 2 homing bullets
		if (!focused) {
			bulletList.add(new Player1BulletHoming(xPos-20, yPos, 10, 150, 3, enemy));
			bulletList.add(new Player1BulletHoming(xPos+20, yPos, 10, 210, 3, enemy));
			bulletList.add(new Player1Bullet(xPos, yPos, 10, 170));
			bulletList.add(new Player1Bullet(xPos, yPos, 10, 178));
			bulletList.add(new Player1Bullet(xPos, yPos, 10, 182));
			bulletList.add(new Player1Bullet(xPos, yPos, 10, 190));
		}
		
		// Focused: Spread is slightly decreased
		else {
			bulletList.add(new Player1BulletHoming(xPos-20, yPos, 10, 180, 0, enemy));
			bulletList.add(new Player1BulletHoming(xPos+20, yPos, 10, 180, 0, enemy));
			bulletList.add(new Player1Bullet(xPos, yPos, 10, 175));
			bulletList.add(new Player1Bullet(xPos, yPos, 10, 179));
			bulletList.add(new Player1Bullet(xPos, yPos, 10, 181));
			bulletList.add(new Player1Bullet(xPos, yPos, 10, 185));
		}
	}
	
	// Description: Method to bomb
	// Parameters: ---
	// Return: ---
	public void bomb() {
		if (bombs > 0) {
			bombs--;
			Thread bombThread = new Bomb();
			bombThread.start();
		}
	}
	
	// Bomb: Shoots 6 bombs that home on the enemy
	public class Bomb extends Thread {
		public void run() {
			try {
				isBombing = true;
				invincible = true;
				int direction = 1;
				sleep (500);
				for (int i = 0; i < 6; i++) {
					playSound("player1bomb.wav");
					bulletList.add(new Player1Bomb(xPos-20, yPos, 10, 180 + 20 * direction, 10, enemy));
					direction *= -1;
					sleep(500);
				}
				isBombing = false;
				invincible = false;
				
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	// Description: Setter methods
	// Parameters: spriteSheet, portrait
	// Return: ---
	public static void setSpriteSheet(BufferedImage image) {
		reimuSpriteSheet = image;
	}
	
	public static void setPortrait(BufferedImage image) {
		portrait = image;
	}
}
