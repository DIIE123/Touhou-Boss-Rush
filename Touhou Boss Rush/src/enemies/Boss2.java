package enemies;

import java.awt.image.BufferedImage;

import enemybullets.*;

//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to store attacks of boss 2

public class Boss2 extends Enemy {
	private static BufferedImage sakuyaSpriteSheet;
	private static BufferedImage portrait;
	
	// Description: Constructor method
	// Parameters: ---
	// Return: ---
	public Boss2() {
		// Health
		lives = 6;
		basicMaxHealth = 700;
		spellMaxHealth = 1100;
		health = basicMaxHealth;
		
		// Attacks
		attackTimer = 40;
		attackList.add(new Basic1());
		attackList.add(new Spell1());
		attackList.add(new Basic2());
		attackList.add(new Spell2());
		attackList.add(new Basic3());
		attackList.add(new Spell3());
		
		// Sprites
		sprite = portrait;
		for (int i = 0; i < 4; i++) {
			spriteArray[i] = sakuyaSpriteSheet.getSubimage(64 * i, 0, 60, 76);
		}
		for (int i = 4; i < 8; i++) {
			spriteArray[i] = sakuyaSpriteSheet.getSubimage(64 * (i-4), 82, 60, 76);
		}
		for (int i = 11; i >= 8; i--) {
			spriteArray[i] = sakuyaSpriteSheet.getSubimage(64 * (i*-1+11), 164, 60, 76);
		}
	}
	
	// Description: Setter methods
	// Parameters: spriteSheet, portrait
	// Return: ---
	public static void setSpriteSheet(BufferedImage image) {
		sakuyaSpriteSheet = image;
	}
	
	public static void setPortrait(BufferedImage image) {
		portrait = image;
	}
	
	// Basic Attack 1: Shoots 3 triangle bullet patterns at player, then 5 circular bullet patterns
	// Inspiration: Touhou 7 stage 2 midboss
	public class Basic1 extends Thread {
		public void run() {
			try {
				// Wait 1 second
				sleep(1000);
				while (!Thread.currentThread().isInterrupted()) {
					// Triangle bullets
					for (int i = 0; i < 3; i++) {
						double angle = Math.toDegrees(Math.atan2(player.getY() - yPos, player.getX() - xPos)) - 90;
						bulletList.add(new BasicBullet("green", xPos, yPos, 4, angle));
						
						for (int j = 0; j < 2; j++) {
							bulletList.add(new BasicBullet("green", xPos, yPos, 3.5, -7.5 + angle + j * 15));
						}
						for (int j = 0; j < 3; j++) {
							bulletList.add(new BasicBullet("green", xPos, yPos, 3, -15 + angle + j * 15));					
						}
						for (int j = 0; j < 4; j++) {
							bulletList.add(new BasicBullet("green", xPos, yPos, 2.5, -22.5 + angle + j * 15));
						}
						launchRandom(0.5);
						playSound("bulletloud.wav");
						sleep(600);
					}
					sleep(1000);
					
					// Circular bullets
					for (int i = 0; i < 5; i++) {
						for (int j = 0; j < 20; j++) {
							for (int k = 0; k < 3; k++) {
								bulletList.add(new CrystalBullet("blue", xPos, yPos, 3 + k * 0.2, 360/20*j));
							}
						}
						launchRandom(0.3);
						playSound("crystal.wav");
						sleep(310);
					}
					launch(300, 150, 0.3);
					sleep(3000);
				}
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	// Spell Attack 1: Shoots a ring of basic/medium bullets, then another ring of basic bullets
	// Inspiration: Touhou 6 stage 4 last spell (marisa b)
	public class Spell1 extends Thread {
		public void run() {
			try {
				// Attack Name
				attackName = "Waterfowl Dance";
				
				// Move back to center
				launch(300, 150, 0.9);
				
				// Wait 1 second
				sleep(1000);
				while (!Thread.currentThread().isInterrupted()) {
					playSound("bulletloud.wav");
					
					// First Pattern
					for (int i = 0; i < 10; i++) {
						bulletList.add(new MediumBullet("purple", xPos, yPos, 3, -90 + 180/10*i));
					}
					sleep(100);
					for (int i = 0; i < 10; i++) {
						bulletList.add(new MediumBullet("purple", xPos, yPos, 3, -90 + 180/10*i));
					}
					for (int i = 0; i < 24; i++) {
						bulletList.add(new BasicBullet("aqua", xPos, yPos, 4, 360/24*i));
						bulletList.add(new BasicBullet("aqua", xPos, yPos, 3, 7.5 + 360/24*i));
					}
					
					// Second Pattern
					launchRandomClose(80, 1);
					sleep(500);
					for (int i = 0; i < 20; i++) {
						bulletList.add(new BasicBullet("green", xPos, yPos, 4, 360/20*i));
						bulletList.add(new BasicBullet("green", xPos, yPos, 3, 360/20*i));
					}
					sleep(300);
					for (int i = 0; i < 20; i++) {
						bulletList.add(new BasicBullet("green", xPos, yPos, 4, 360/20*i));
						bulletList.add(new BasicBullet("green", xPos, yPos, 3, 360/20*i));
					}
				}
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
		
	// Basic Attack 2: Shoots a spinning ring of bullets, then 3 faster circles and moves to random position
	// Inspiration: Touhou 6 stage 3 first basic
	public class Basic2 extends Thread {
		public void run() {
			try {
				int acc = 0;
				int direction = 1;
				
				// Wait 1 second
				sleep(1000);
				while (!Thread.currentThread().isInterrupted()) {
					// Spawn spinning ring
					for (int i = 0; i < 6; i++) {
						bulletList.add(new CrystalBullet("red", xPos, yPos, 3, -30 + acc * 6 * direction + 360/6*i));
					}
					acc++;
					if (acc % 2 == 0) playSound("bulletsmall.wav");
					sleep(50);
					
					
					// SPawn 3 faster bullet rings and move to random position
					if (acc >= 36) {
						launchRandom(1);
						
						for (int i = 0; i < 3; i++) {
							for (int j = 0; j < 20; j++) {
								bulletList.add(new CrystalBullet("red", xPos, yPos, 5, i * 15 + 360/20*j));
							}
							playSound("crystal.wav");
							sleep(100);
						}
						acc = 0;
						direction *= -1;
						sleep(1500);
					}
				}
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	// Spell Attack 2: Shoots rainbow bullets that move in a circular pattern, then reverses direction
	// Inspiration: Touhou 6 stage 3 1st spell
	public class Spell2 extends Thread {
		public void run() {
			try {
				int angle = -30;
				int acc = 0;
				int direction = 1;
				
				// Set attack name
				attackName = "Rainbow Chime";
				
				// Move back to center
				launch(300, 150, 0.9);
				
				// Wait 1 second
				sleep(1000);
				while (!Thread.currentThread().isInterrupted()) {
					// Red Bullets
					for (int i = 0; i < 2; i++) {
						bulletList.add(new CrystalBullet("red", xPos, yPos, 3.5, angle + 180 * i));
					}
					
					// Orange Bullets
					for (int i = 0; i < 3; i++) {
						bulletList.add(new CrystalBullet("orange", xPos, yPos, 3.5, 5 + angle + 120 * i));
					}
					
					// Yellow Bullets
					for (int i = 0; i < 4; i++) {
						bulletList.add(new CrystalBullet("yellow", xPos, yPos, 3.5, 10 + angle + 90 * i));
					}
					
					// Green Bullets
					for (int i = 0; i < 2; i++) {
						bulletList.add(new CrystalBullet("green", xPos, yPos, 3.5, 15 + angle + 180 * i));
					}
					
					// Aqua Bullets
					for (int i = 0; i < 4; i++) {
						bulletList.add(new CrystalBullet("aqua", xPos, yPos, 3.5, 20 + angle + 90 * i));
					}
					
					// Blue Bullets
					for (int i = 0; i < 3; i++) {
						bulletList.add(new CrystalBullet("aqua", xPos, yPos, 3.5, 25 + angle + 120 * i));
					}
					
					// Purple Bullets
					for (int i = 0; i < 2; i++) {
						bulletList.add(new CrystalBullet("purple", xPos, yPos, 3.5, 30 + angle + 180 * i));
					}
					
					
					// Switching direction and angle
					acc++;
					playSound("bulletsmall.wav");
					if (acc == 60) {
						acc = 0;
						direction *= -1;
					}
					angle += 6 * direction;
					sleep(120);
				}
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
		
	// Basic Attack 3: Shoots knives one way then daggers the other way 
	// Inspiration: Touhou 6 stage 5 midboss
	public class Basic3 extends Thread {
		public void run() {
			try {
				// Move back to center
				launch(300, 150, 0.9);
				
				// Wait 1 second
				sleep(1000);
				while (!Thread.currentThread().isInterrupted()) {
					// Knives
					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 8; j++) {
							bulletList.add(new Knife("blue", xPos, yPos, 4, -145 + 15 * j + 16 * i));
						}
						playSound("bulletsmall.wav");
						sleep(150);
					}
					launchRandomClose(100, 1);
					
					// Daggers
					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 4; j++) {
							bulletList.add(new Dagger("red", xPos, yPos, 2.5, 90 - 2 * j - 15 * i));
							bulletList.add(new Dagger("red", xPos, yPos, 3, 90 - 2 * j - 15 * i));
						}
						playSound("bullettick.wav");
						sleep(50);
					}
					sleep(1000);
					
					// Other Way
					// Knives
					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 8; j++) {
							bulletList.add(new Knife("blue", xPos, yPos, 4, 145 - 15 * j - 16 * i));
						}
						playSound("bulletsmall.wav");
						sleep(150);
					}
					// Daggers
					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 4; j++) {
							bulletList.add(new Dagger("red", xPos, yPos, 2.5, -90 + 2 * j + 15 * i));
							bulletList.add(new Dagger("red", xPos, yPos, 3, -90 + 2 * j + 15 * i));
						}
						playSound("bullettick.wav");
						sleep(50);
					}
				}
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
		
	// Spell Attack 3: Shoots 4 "spawner" bullets that constantly shoot 5 bullets in a circular direction, then a line of purple knives
	// Inspiration: Touhou 6 stage 5 1st basic
	public class Spell3 extends Thread {
		public void run() {
			try {
				// Attack Name
				attackName = "Lunar Clock";
				
				// Move back to center
				launch(300, 150, 0.9);
				
				// Wait 1 second
				sleep(1000);
				while (!Thread.currentThread().isInterrupted()) {
					// Spawners
					Bullet[] launchArray = new Bullet[4];
					launchArray[0] = new BigBullet("red", xPos, yPos, 0, 0);
					launchArray[1] = new BigBullet("red", xPos, yPos, 0, 0);
					launchArray[2] = new BigBullet("red", xPos, yPos, 0, 0);
					launchArray[3] = new BigBullet("red", xPos, yPos, 0, 0);
					for (int i = 0; i < 4; i++) {
						bulletList.add(launchArray[i]);
					}
					launchArray[0].launch(xPos-200, yPos, 3);
					launchArray[1].launch(xPos-100, yPos+150, 3);
					launchArray[2].launch(xPos+100, yPos+150, 3);
					launchArray[3].launch(xPos+200, yPos, 3);
					
					playSound("bulletloud.wav");
					sleep(500);
					
					// Bullets
					int angle = 190;
					for (int i = 0; i < 36; i++) {
						for (int j = 0; j < 4; j++) {
							Bullet launcher = launchArray[j];
							if (!bulletList.contains(launcher)) bulletList.add(launcher);
							for (int k = 0; k < 5; k++) {
								if (j % 2 == 0)
									bulletList.add(new DiamondBullet("green", launcher.getX(), launcher.getY(), 3, angle - k * 2));
								else
									bulletList.add(new DiamondBullet("blue", launcher.getX(), launcher.getY(), 3,  angle * -1 - k * 2));
							}
						}
						
						if (i % 9 == 1 && i > 8) {
							for (int j = 0; j < 16; j++) {
								bulletList.add(new Knife("blue", xPos, yPos, 4, 360/16*j));
							}
							playSound("bulletloud.wav");
							launchRandomClose(100, 6);
						}
						
						angle += 20;
						playSound("bulletsmall.wav");
						sleep(150);
					}
					for (int i = 0; i < 4; i++) {
						bulletList.remove(launchArray[i]);
					}
					sleep(1000);
					
					// Purple Bullets
					for (int i = 0; i < 12; i++) {
						for (int j = 0; j < 8; j++) {
							bulletList.add(new Knife("purple", xPos, yPos, 3 + 0.3 * j, 1 + 10 * i));
							bulletList.add(new Knife("purple", xPos, yPos, 3 + 0.3 * j, -1 - 10 * i));
						}
						playSound("bulletsmall.wav");
						sleep(100);
					}
					sleep(2000);
				}
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

}
