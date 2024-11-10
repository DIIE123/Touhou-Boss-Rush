package enemies;

import java.awt.image.BufferedImage;
import java.util.*;

import enemybullets.*;

//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to store attacks of boss 3

public class Boss3 extends Enemy {
	private static BufferedImage yukariSpriteSheet;
	private static BufferedImage portrait;
	
	// Description: Constructor method
	// Parameters: ---
	// Return: ---
	public Boss3() {
		lives = 6;
		basicMaxHealth = 800;
		spellMaxHealth = 1200;
		health = basicMaxHealth;
		width = 60;
		height = 90;
		
		// Attacks
		attackTimer = 60;
		attackList.add(new Basic1());
		attackList.add(new Spell1());
		attackList.add(new Basic2());
		attackList.add(new Spell2());
		attackList.add(new Basic3());
		attackList.add(new Spell3());
		
		// Sprites
		sprite = portrait;
		spriteArray[8] = yukariSpriteSheet.getSubimage(11, 5, 71, 94);
		for (int i = 0; i < 5; i++) {
			spriteArray[i] = spriteArray[8];
		}
		for (int i = 5; i < 8; i++) {
			spriteArray[i] = yukariSpriteSheet.getSubimage(11 + 76 * (i-5), 104, 76, 96);
		}
		for (int i = 9; i < 12; i++) {
			spriteArray[i] = yukariSpriteSheet.getSubimage(11 + 76 * (i-9), 200, 76, 96);
		}
	}
	
	// Description: Setter methods
	// Parameters: spriteSheet, portrait
	// Return: ---
	public static void setSpriteSheet(BufferedImage image) {
		yukariSpriteSheet = image;
	}
	
	public static void setPortrait(BufferedImage image) {
		portrait = image;
	}
	
	// Basic Attack 1: Shoots a constant barrage of 3 bullet rings
	// Inspiration: Touhou 7 phantasm stage 2nd basic
	public class Basic1 extends Thread {
		int acc;
		public void run() {
			try {
				// Wait 1 second
				sleep(1000);
				while (!Thread.currentThread().isInterrupted()) {
					// Spawn Bullets
					for (int j = 0; j < 32; j++) {
						bulletList.add(new Dagger("yellow", xPos, yPos, 3, 7.5 + 360/24 * j));
						bulletList.add(new Dagger("green", xPos, yPos, 3.5, 9.5 + 360/24 * j));
						bulletList.add(new Dagger("green", xPos, yPos, 5, 7.5 + 360/24 * j));
					}
					acc++;
					sleep(200);
					playSound("bulletsmall.wav");
					
					// Launch to random close position
					if (acc >= 6) {
						launchRandomClose(60, 1);
						acc = 0;
					}
				}
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	// Spell Attack 1: Shoots 16 lines of diamond bullets in a circle, then 16 lines of medium bullets the other way
	// Inspiration: Touhou 7 stage 6 first basic
	public class Spell1 extends Thread {
		public void run() {
			try {
				// Attack name
				attackName = "Cluster Butterfly";
				
				// Move to center
				launch(300, 500, 0.9);
				
				// Wait 1 second
				sleep(1000);
				while (!Thread.currentThread().isInterrupted()) {
					int timeSlept = 700;
					for (int acc = 0; acc < 3; acc++) {
						// Diamond Bullets
						for (int i = 0; i < 8; i++) {
							for (int j = 0; j < 16; j++) {
								bulletList.add(new DiamondBullet("aqua", xPos, yPos, 3, 180 - 240/16*j - i * 1));
							}
							playSound("bullettick.wav");
							sleep(50);
						}
						
						// Medium Bullets
						for (int i = 0; i < 3; i++) {
							for (int j = 0; j < 16; j++) {
								bulletList.add(new MediumBullet("dark yellow", xPos, yPos, 3.5, -180 + 240/16*j + i * 5));
							}
							playSound("bulletsmall.wav");
							sleep(250);
						}
						sleep(timeSlept);
						
						// Other Way
						for (int i = 0; i < 8; i++) {
							for (int j = 0; j < 16; j++) {
								bulletList.add(new DiamondBullet("yellow", xPos, yPos, 3, -180 + 240/16*j + i * 1.5));
							}
							playSound("bullettick.wav");
							sleep(50);
						}
						for (int i = 0; i < 5; i++) {
							for (int j = 0; j < 16; j++) {
								bulletList.add(new MediumBullet("aqua", xPos, yPos, 3.5, 180 - 240/16*j - i * 5));
							}
							playSound("bulletsmall.wav");
							sleep(250);
						}
						sleep(timeSlept);
						timeSlept = 0;
					}
					sleep(2000);
				}
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	// Basic Attack 2: Shoots a rotating ring of 8 bullets with 3 different speeds
	// Inspiration: Touhou 7 phantasm stage 6th basic
	public class Basic2 extends Thread {
		int angle = 0;
		public void run() {
			try {
				// Wait 1 second
				sleep(1000);
				while (!Thread.currentThread().isInterrupted()) {
					// Spawn Bullets
					for (int i = 0; i < 8; i++) {
						bulletList.add(new Dagger("aqua", xPos, yPos, 2, angle + 360/8 * i));
						bulletList.add(new Dagger("aqua", xPos, yPos, 3, 1 + angle + 360/8 * i));
						bulletList.add(new Dagger("aqua", xPos, yPos, 4, 2 + angle + 360/8 * i));
					}
					// Increase angle and launch
					angle += 5;
					if (angle >= 90) {
						angle = 0;
						launchRandomClose(60, 1);
					}
					playSound("bulletsmall.wav");
					sleep(150);
				}
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	// Spell Attack 2: Spawns a chaser bullet which launches towards the player and then explodes into a ring of bullets
	// Inspiration: Touhou 7 stage 6 3rd spell
	public class Spell2 extends Thread {
		Stack<Bullet> tempBulletList = new Stack<>();
		Bullet chaserBullet = new ButterflyBullet("red", xPos, yPos, 0, 0);
		Thread chaserThread;
		int angle = 0;
		public void run() {
			try {
				// Attack name
				attackName = "Chaser Butterfly";
				
				// Move back to center
				launch(300, 150, 0.9);
				
				// Wait 1 second
				sleep(1000);
				
				// Start chaser thread
				chaserThread = new ChaserThread();
				chaserThread.start();
				
				// Launch other bullets
				while (!Thread.currentThread().isInterrupted()) {
					for (int acc = 0; acc < 3; acc++) {
						sleep(700);
						playSound("bulletsmall.wav");
						for (int i = 0; i < 20; i++) {
							bulletList.add(new MediumBullet("blue", xPos, yPos, 4, angle + 9 + 360/20 * i));
						}
						for (int i = 0; i < 20; i++) {
							bulletList.add(new MediumBullet("blue", xPos, yPos, 3, angle + 360/20 * i));
						}
					}
					angle -= 3;
					if (angle <= -180) angle = 0;
					sleep(1000);
				}
			}catch(InterruptedException e) {
				chaserThread.interrupt();
				Thread.currentThread().interrupt();
			}
		}
		
		// Thread to spawn chaser
		public class ChaserThread extends Thread {
			public void run() {
				try {
					while (!Thread.currentThread().isInterrupted()) {
						double playerX = player.getX();
						double playerY = player.getY();
						
						// Launch chaser bullet at player
						chaserBullet.launch(playerX, playerY, 1.9);
						playSound("chaser.wav");
						for (int i = 0; i < 40; i++) {
							if (!bulletList.contains(chaserBullet)) bulletList.add(chaserBullet);
							sleep(50);
						}
						
						// Explode into ring
						playSound("bulletloud.wav");
						for (int i = 0; i < 8; i++) {
							Bullet bullet = new ButterflyBullet("blue", playerX, playerY, 0, 0);
							bullet.launch(playerX + Math.cos(Math.toRadians(360/8 * i)) * 100, playerY + Math.sin(Math.toRadians(360/8 * i)) * 100, 0.5);
							bulletList.add(bullet);
							tempBulletList.add(bullet);
						}
						for (int i = 0; i < 8; i++) {
							Bullet bullet = new ButterflyBullet("green", playerX, playerY, 0, 0);
							bullet.launch(playerX + Math.cos(Math.toRadians(15 + 360/8 * i)) * 60, playerY + Math.sin(Math.toRadians(15 + 360/8 * i)) * 60, 0.5);
							bulletList.add(bullet);
							tempBulletList.add(bullet);
						}
						for (int i = 0; i < 8; i++) {
							Bullet bullet = new ButterflyBullet("dark yellow", playerX, playerY, 0, 0);
							bullet.launch(playerX + Math.cos(Math.toRadians(30 + 360/8 * i)) * 20, playerY + Math.sin(Math.toRadians(30 + 360/8 * i)) * 20, 0.5);
							bulletList.add(bullet);
							tempBulletList.add(bullet);
						}
						sleep(510);
						for (int i = 0; i < tempBulletList.size(); i++) {
							tempBulletList.get(i).launch(playerX, playerY, 0.5);
						}
						sleep(510);
						while(!tempBulletList.isEmpty()) {
							bulletList.remove(tempBulletList.pop());
						}
 					}
				}catch(InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
		
	// Basic Attack 3: Shoots a circle of fast bullets and a circle of slow bullets the other way
	// Inspiration: Touhou 7 phantasm stage 7th basic
	public class Basic3 extends Thread {
		int angle = 0;
		public void run() {
			try {
				// Wait 1 second
				sleep(1000);
				while (!Thread.currentThread().isInterrupted()) {
					// Spawn Bullets
					for (int i = 0; i < 8; i++) {
						bulletList.add(new Dagger("dark purple", xPos, yPos, 4, angle * -1 + 360/8 * i));
						bulletList.add(new Dagger("purple", xPos, yPos, 2, angle + 360/8 * i));
					}
					
					// Increase angle and launch
					angle += 7;
					if (angle >= 180) {
						angle = 0;
						launchRandomClose(50, 1);
					}
					if (angle % 14 == 0 ) playSound("bulletsmall.wav");
					sleep(100);
				}
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	// Spell Attack 3: Shoots lasers and 6 rings of accelerating bullets, then 4 rings of curving bullets
	// Inspiration: Touhou 7 stage 6 final spell
	public class Spell3 extends Thread {
		Thread laserThread;
		public void run() {
			try {
				// Attack name
				attackName = "Resurrection Butterfly";
				
				// Wait 1 second
				sleep(1000);
				while (!Thread.currentThread().isInterrupted()) {
					// Start laser thread
					laserThread = new LaserThread();
					laserThread.start();
					playSound("laser.wav");
					sleep(500);
					playSound("bulletloud.wav");
					// First Bullet Pattern
					for (int i = 0; i < 6; i++) {
						for (int j = 0; j < 48; j++) {
							if (i % 2 == 0) {
								for (int k = 0; k < 2; k++) {
									bulletList.add(new BasicBullet("blue", xPos, yPos, 3, 360/48.0 * j, 0.005 + 0.03 * k));
								}
							}
							else {
								for (int k = 0; k < 2; k++) {
									bulletList.add(new BasicBullet("purple", xPos, yPos, 3, 3.75 + 360/48.0 * j, 0.005 + 0.03 * k));
								}
							}
						}
						sleep(100);
					}
					sleep(2000);
					
					// Second Bullet Pattern
					int angle = 0;
					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 32; j++) {
							for (int k = 1; k < 4; k++) {
								if (i % 2 == 0) {
									bulletList.add(new ButterflyBulletAcceleration("red", xPos, yPos, 2, angle + 360/32.0 * j, 0.001 + 0.02 * k, 1));
								}
								else {
									bulletList.add(new ButterflyBulletAcceleration("red", xPos, yPos, 2, angle + 360/32.0 * j, 0.001 + 0.02 * k, -1));
								}
							}
						}
						angle += 2.5; // Maybe change to 3 if too easy (but 3 makes it super hard)
						playSound("bulletloud.wav");
						sleep(500);
					}
					
					// Stop Laser Thread
					laserThread.interrupt();
					
					// Wait and launch
					sleep(2000);
					launchRandom(1);
					sleep(1000);
				}
			}catch(InterruptedException e) {
				laserThread.interrupt();
				Thread.currentThread().interrupt();
			}
		}
		
		// Thread to spawn lasers
		public class LaserThread extends Thread {
			public void run() {
				try {
					while (!Thread.currentThread().isInterrupted()) {
						for (int i = 0; i < 5; i++) {
							bulletList.add(new Laser("blue", xPos + Math.cos(Math.toRadians(72 + 360/5*i)) * 50, yPos + Math.sin(Math.toRadians(72 + 360/5*i)) * 50, 5, -18 + 360/5 * i));
						}
						for (int i = 0; i < 5; i++) {
							bulletList.add(new Laser("purple", xPos + Math.cos(Math.toRadians(108 + 360/5*i)) * 50, yPos + Math.sin(Math.toRadians(108 + 360/5*i)) * 50, 5, 18 + 360/5 * i));
						}
						sleep(40);
					}
				}catch(InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}
