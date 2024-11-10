package players;
//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to hold player 2 information

import java.awt.image.BufferedImage;

import playerbullets.*;

public class Player2 extends Player {
	private static BufferedImage marisaSpriteSheet;
	private static BufferedImage portrait;
	private Player2 player;
	
	// Description: Constructor method
	// Parameters: ---
	// Return: ---
	public Player2() {
		// Variables
		super(marisaSpriteSheet);
		sprite = portrait;
		speed = 5;
		isBombing = false;
		bombName = "Master Spark";
		player = this;
	}
	
	// Description: Method to shoot bullets
	// Parameters: ---
	// Return: ---
	public void shoot(boolean focused) {
		// Unfocused: Shoots 4 bullets straight in an arrow pattern
		if (!focused) {
			bulletList.add(new Player2Bullet(xPos - 5, yPos, 10));
			bulletList.add(new Player2Bullet(xPos + 5, yPos, 10));
			bulletList.add(new Player2Bullet(xPos - 20, yPos+10, 10));
			bulletList.add(new Player2Bullet(xPos + 20, yPos+10, 10));
		}
		// Focused: Shoots 4 bullets straight, closer together and overlapping more
		else {
			bulletList.add(new Player2Bullet(xPos - 5, yPos, 10));
			bulletList.add(new Player2Bullet(xPos + 5, yPos, 10));
			bulletList.add(new Player2Bullet(xPos - 5, yPos, 10));
			bulletList.add(new Player2Bullet(xPos + 5, yPos, 10));
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
	
	// Bomb: Shoots a giant laser for 3 seconds
	public class Bomb extends Thread {
		public void run() {
			try {
				isBombing = true;
				invincible = true;
				playSound("player2bomb.wav");
				
				sleep(500);
				Player2Bomb bomb = new Player2Bomb(player);
				bulletList.add(bomb);
				sleep(3000);
				bulletList.remove(bomb);
				
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
		marisaSpriteSheet = image;
	}
	
	public static void setPortrait(BufferedImage image) {
		portrait = image;
	}
}
