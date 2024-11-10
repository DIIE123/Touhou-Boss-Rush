package enemies;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemybullets.*;

// Names: Dylan Wang and Simon Gao
// Date: June 14, 2024
// Description: Class to store attacks of boss 1

public class Boss1 extends Enemy {
	private static BufferedImage cirnoSpriteSheet;
	private static BufferedImage portrait;
	
	// Description: Constructor method
	// Parameters: ---
	// Return: ---
	public Boss1() {
		// Health
		lives = 4;
		basicMaxHealth = 800;
		spellMaxHealth = 1200;
		health = basicMaxHealth;
		
		// Attacks
		attackTimer = 40;
		attackList.add(new Basic1());
		attackList.add(new Spell1());
		attackList.add(new Basic2());
		attackList.add(new Spell2());
		
		// Sprites
		sprite = portrait;
		int spriteY = -64;
		int index = 0;
		for (int i = 0; i < 12; i++) {
			if (i % 4 == 0) {
				spriteY += 64;
				index = 0;
			}
			spriteArray[i] = cirnoSpriteSheet.getSubimage(8 + 64 * index, spriteY, 48, 64);
			index++;
		}
	}
	
	// Description: Setter methods
	// Parameters: spriteSheet, portrait
	// Return: ---
	public static void setSpriteSheet(BufferedImage image) {
		cirnoSpriteSheet = image;
	}
	
	public static void setPortrait(BufferedImage image) {
		portrait = image;
	}
	
	// Basic Attack 1: Shoots 3 circular bullet patterns, then a stream of 5 bullets towards the player
	// Inspiration: Touhou 6 stage 1 1st basic
	public class Basic1 extends Thread {
		public void run() {
			try {
				// Wait 1 second
				sleep(1000);
				while (!Thread.currentThread().isInterrupted()) {
					// Spawn 3 rings
					int angle = 0;
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 20; j++) {
							bulletList.add(new CrystalBullet("blue", xPos, yPos, 3, 360/20 * j + angle));
						}
						angle += 10;
						
						playSound("bulletloud.wav");
						sleep(500);
					}
					// Wait 1 second, move, wait 200 ms
					sleep(1000);
					launchRandom(2);
					sleep(200);
					
					// Spawn bullets
					for (int i = 0; i < 5; i++) {
						double addVelocity = 0;
						for (int j = 0; j < 5; j++) {
							Bullet bullet = new BasicBullet("aqua", xPos, yPos, 0, 0); 
							bulletList.add(bullet);
							bullet.movePoint(player.getX(), player.getY(), 5 + addVelocity);
							addVelocity += 0.5;
						}
						playSound("bulletsoft.wav");
						sleep(200);
					}
					// Wait 2 seconds, move to center
					sleep(2000);
					launch(300, 150, 2);
				}
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	// Spell Attack 1: Shoots 8 different sprays of bullets in a semicircle pattern, then moves to random position
	// Inspiration: Touhou 6 stage 1 1st spell
	public class Spell1 extends Thread {
		public void run() {
			// Set attack name
			attackName = "Snowball Flurry";
			
			// Move back to center
			launch(300, 150, 0.9);
			try {
				while (!Thread.currentThread().isInterrupted()) {
					// Wait 1 second
					sleep(1000);
					
					// Spawn bullets
					for (int i = 0; i < 8; i++) {
						double addVelocity = 0;
						int angle = 47;
						int direction = -1;
						String color = "dark aqua";
						if (i % 2 == 1) {
							angle *= -1;
							direction *= -1;
							color = "dark blue";
						}
						
						for (int j = 0; j < 16; j++) {
							bulletList.add(new BasicBullet(color, xPos, yPos, 3 + addVelocity, angle + j * 11 * direction));
							addVelocity += 0.5;
						}
						
						playSound("bulletloud.wav");
						sleep(500);
					}
					
					// Wait 3 seconds, move
					sleep(3000);
					launchRandom(0.9);
				}
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	// Basic Attack 2: Shoots 3 rings of bullets that shoot towards the player
	// Inspiration: Touhou 6 stage 2 1st basic
	public class Basic2 extends Thread {
		public void run() {
			// Move back to center
			launch(300, 150, 0.9);
						
			try {
				sleep(1000);
				while (!Thread.currentThread().isInterrupted()) {
					// Launch in a circle
					for (int i = 0; i < 3; i++) {
						// Spawn Circle
						for (int j = 0; j < 20; j++) {
							bulletList.add(new CrystalBullet("blue", xPos, yPos, 3, 360/20 * j));
						}
						playSound("bulletloud.wav");
						sleep (700);
						
						ArrayList<Bullet> tempBulletList = new ArrayList<>();
						for (int j = 0; j < 30; j++) {
							Bullet bullet = new DiamondBullet("yellow", xPos, yPos, 0, 0);
							tempBulletList.add(bullet);
							bulletList.add(bullet);
							bullet.launch(xPos + Math.cos(Math.toRadians(360/30*j)) * 125, yPos + Math.sin(Math.toRadians(360/30*j)) * 125, 0.4);
						}
						playSound("bulletsoft.wav");
						sleep (700);
						
						// Move towards player
						for (int j = 0; j < tempBulletList.size(); j++) {
							tempBulletList.get(j).movePoint(player.getX(), player.getY(), 5);
						}
					}
					
					sleep(2000);
					launchRandom(3);
				}
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	// Spell Attack 2: Shoots a flurry of icicles outwards that move towards the bottom of the screen
	// Inspiration: Touhou 6 stage 2 1st spell
	public class Spell2 extends Thread {
		public void run() {
			// Set attack name
			attackName = "Icicle Rain";
			
			// Move back to center
			launch(300, 150, 0.9);
			try {
				// Wait 1 second
				sleep(1000);
				
				while (!Thread.currentThread().isInterrupted()) {
					ArrayList<Bullet> tempBulletList = new ArrayList<>();
					int angle = 0;
					int yAdjust = -30;
					for (int i = 0; i <= 10; i++) {
						// Icicles Launching
						if (i < 8) {
							for (int j = 0; j < 3; j++) {
								Bullet bullet = new CrystalBullet("blue", xPos, yPos, 0, 0);
								tempBulletList.add(bullet);
								bulletList.add(bullet);
								bullet.launch(xPos - 150 - 80 * j, 80 + 20 * i + yAdjust * j, 0.8);
							}
							for (int j = 0; j < 3; j++) {
								Bullet bullet = new CrystalBullet("blue", xPos, yPos, 0, 0);
								tempBulletList.add(bullet);
								bulletList.add(bullet);
								bullet.launch(xPos + 150 + 80 * j, 80 + 20 * i + yAdjust * j, 0.8);
							}
							yAdjust += 8;
							playSound("crystal.wav");
						}
						
						// Icicles moving
						if (i > 2) {
							for (int j = 0; j < 3; j++) {
								tempBulletList.get(0).moveAngle(2, 20 - angle);
								tempBulletList.remove(0);
							}
							for (int j = 0; j < 3; j++) {
								tempBulletList.get(0).moveAngle(2, -20 + angle);
								tempBulletList.remove(0);
							}
							angle += 10;
						}
						
						// Anti afk bullets
						if (i % 5 == 0 && i != 10) {
							double angle2 = Math.toDegrees(Math.atan2(player.getY() - yPos, player.getX() - xPos)) - 90;
							for (int j = 0; j < 5; j++) {
								bulletList.add(new BasicBullet("dark aqua", xPos, yPos, 3, angle2 - 20 + 10 * j));
							}
							playSound("bulletloud.wav");
						}
						sleep(500);
					}
				}
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}
