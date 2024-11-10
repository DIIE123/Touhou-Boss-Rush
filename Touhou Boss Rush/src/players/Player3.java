package players;
//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to hold player 3 information

import java.awt.image.BufferedImage;

import playerbullets.*;

public class Player3 extends Player {
	private static BufferedImage sanaeSpriteSheet;
	private static BufferedImage portrait;
	
	// Description: Constructor method
	// Parameters: ---
	// Return: ---
	public Player3() {
		// Variables
		super(sanaeSpriteSheet);
		sprite = portrait;
		speed = 4.5;
		isBombing = false;
		bombName = "Stardust Reverie";
	}
	
	// Description: Method to shoot bullets
	// Parameters: ---
	// Return: ---
	public void shoot(boolean focused) {
		// Unfocused: Shoots 8 bullets in a wide spread pattern
		if (!focused) {
			for (int i = 0; i < 8; i++) {
				bulletList.add(new Player3Bullet(xPos, yPos, 10, 166 + 4 * i));
			}
		}
		// Focused: Shoots 6 bullets all layered on top of each other straight up
		else {
			for (int i = 0; i < 6; i++) {
				bulletList.add(new Player3Bullet(xPos, yPos, 10, 180));
			}
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
	
	// Bomb: Shoots a slow moving giant bullet upwards
	public class Bomb extends Thread {
		public void run() {
			try {
				isBombing = true;
				invincible = true;
				sleep (500);
				
				Player3Bomb bomb = new Player3Bomb(xPos, yPos);
				bulletList.add(bomb);
				
				for (int i = 0; i < 6; i++) {
					playSound("player3bomb.wav");
					sleep(500);
				}
				if (bulletList.contains(bomb)) bulletList.remove(bomb);
				
				isBombing = false;
				invincible = false;
				this.interrupt();
				
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	// Description: Setter methods
	// Parameters: spriteSheet, portrait
	// Return: ---
	public static void setSpriteSheet(BufferedImage image) {
		sanaeSpriteSheet = image;
	}
	
	public static void setPortrait(BufferedImage image) {
		portrait = image;
	}
}
