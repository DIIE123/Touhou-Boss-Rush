// Names: Dylan Wang and Simon Gao
// Date: June 14, 2024
// Description: Main method that runs all the boss battles

import java.awt.event.*;
import java.awt.*;

import javax.imageio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.*;

import enemies.*;
import enemybullets.*;
import players.*;
import playerbullets.*;

import java.util.*;
import java.io.*;

public class Main extends JPanel implements KeyListener {
	// General Variables
	int FPS = 60;
	Thread mainThread;
	int screenWidth = 900;
	int screenHeight = 700;
	int score;
	int specialScore;
	int levelOption;
	int playerOption;
	JFrame frame;
	String name;
	
	// Misc
	boolean showHitbox = false;
	boolean debugInvincible = false;
	int invincibleTimer = 0;
	
	// Player Variables
	Player player;
	Player[] playerList = new Player[3];
	int spriteTimer = 0;
	int spriteNum = 8;
	boolean moveUp = false;
	boolean moveDown= false;
	boolean moveLeft = false;
	boolean moveRight = false;
	boolean focused = false;
	boolean shoot = false;
	
	// Enemy Variables
	Enemy enemy;
	Enemy[] enemyList = new Enemy[3];
	ArrayList<Thread> attackThreads;
	Thread attackThread;
	double attackTimer;
	int enemySpriteNum = 0;
	
	// Bullets Variables
	ArrayList<Bullet> enemyBulletList;
	ArrayList<Bullet> playerBulletList;
	
	// Graphics
	Image[] backgrounds = new Image[3];
	Image lifeIcon, bombIcon, logo, sidebar;
	
	float opacity = 0f;
	int opacityDirection = 1;
	int portraitY = 400;
	int textY = 550;
	
	float enemyOpacity = 0f;
	int enemyOpacityDirection = 1;
	int enemyPortraitY = 400;
	int enemyTextY = 550;
	
	// Sounds
	Clip[] music = new Clip[3];
	
	// Description: Constructor method
	// Parameters: playeroption, leveloption, frame, name
	// Return: ---
	public Main(int playerOption, int levelOption, JFrame frame, String name) {
		// Sets up JPanel
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setVisible(true);
		
		// Imports Images
		try {
			// Backgrounds
			backgrounds[0] = ImageIO.read(new File("Images/Touhou Boss 1 Background.png"));
			backgrounds[1] = ImageIO.read(new File("Images/Touhou Boss 2 Background.png"));
			backgrounds[2] = ImageIO.read(new File("Images/Touhou Boss 3 Background.png"));
			
			// Sidebars
			lifeIcon = ImageIO.read(new File("Images/Touhou Life.png"));
			bombIcon = ImageIO.read(new File("Images/Touhou Bomb.png"));
			logo = ImageIO.read(new File("Images/Touhou Sidebar Logo.png"));
			sidebar = ImageIO.read(new File("Images/Touhou Sidebar Background.png"));
			
			// Player Spritesheets
			Player1.setSpriteSheet(ImageIO.read(new File("Images/Touhou Reimu Hakurei.png")));
			Player1.setPortrait(ImageIO.read(new File("Images/Touhou Reimu Portrait.png")));
			Player2.setSpriteSheet(ImageIO.read(new File("Images/Touhou Marisa Kirisame.png")));
			Player2.setPortrait(ImageIO.read(new File("Images/Touhou Marisa Portrait.png")));
			Player3.setSpriteSheet(ImageIO.read(new File("Images/Touhou Sanae Kochiya.png")));
			Player3.setPortrait(ImageIO.read(new File("Images/Touhou Sanae Portrait.png")));
			
			// Enemy Spritesheets
			Boss1.setSpriteSheet(ImageIO.read(new File("Images/Touhou Cirno.png")));
			Boss1.setPortrait(ImageIO.read(new File("Images/Touhou Cirno Portrait.png")));
			Boss2.setSpriteSheet(ImageIO.read(new File("Images/Touhou Sakuya.png")));
			Boss2.setPortrait(ImageIO.read(new File("Images/Touhou Sakuya Portrait.png")));
			Boss3.setSpriteSheet(ImageIO.read(new File("Images/Touhou Yukari.png")));
			Boss3.setPortrait(ImageIO.read(new File("Images/Touhou Yukari Portrait.png")));
			
			// Bullet Spritesheets
			Bullet.setSpriteSheet(ImageIO.read(new File("Images/Touhou Objects and Projectiles.png")));
			Player.setHitboxSprite(ImageIO.read(new File("Images/Touhou Objects and Projectiles.png")).getSubimage(92, 257, 16, 16));
			Player2Bomb.setSprite(ImageIO.read(new File("Images/Touhou Player 2 Bomb.png")));
			
			// Music
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File ("Sounds/boss1theme.wav"));
			music[0] = AudioSystem.getClip();
			music[0].open(sound);
			sound = AudioSystem.getAudioInputStream(new File ("Sounds/boss2theme.wav"));
			music[1] = AudioSystem.getClip();
			music[1].open(sound);
			sound = AudioSystem.getAudioInputStream(new File ("Sounds/boss3theme.wav"));
			music[2] = AudioSystem.getClip();
			music[2].open(sound);
			for (int i = 0; i < music.length; i++) {
				FloatControl volume = (FloatControl) music[i].getControl(FloatControl.Type.MASTER_GAIN);
		        volume.setValue(-5.0f);
			}
			FloatControl volume = (FloatControl) music[2].getControl(FloatControl.Type.MASTER_GAIN);
	        volume.setValue(-15.0f);
		}
		catch (Exception e) {
			System.out.println("File Error");
		}
		
		this.playerOption = playerOption;
		this.levelOption = levelOption;
		this.frame = frame;
		this.name = name;
		
		// Starting the thread
		mainThread = new mainThread();
		mainThread.start();
	}

	// Main Thread
	class mainThread extends Thread {
		public void run() {
			initialize();
			while(!Thread.currentThread().isInterrupted()) {
				// Main game loop
				playerUpdate();
				enemyUpdate();
				enemyBulletUpdate();
				playerBulletUpdate();
				repaint();
				
				// Game Over
				if (player.getLives() < 0 || enemy.getLives() <= 0) {
					attackThread.interrupt();
					for (int i = 0; i < music.length; i++) {
						music[i].close();
					}
					
					endMenu.main(null);
					if (player.getLives() < 0) {
						new endMenu(1, score, player.getLives(), player.getBombs(), name, playerOption, levelOption);
					}
					else {
						new endMenu(0, score, player.getLives(), player.getBombs(), name, playerOption, levelOption);
					}
					frame.dispose();
					this.interrupt();
				}
				try {
					sleep(1000/FPS);
				}catch(Exception e) {
					Thread.currentThread().interrupt();
				}
			}
		}
		
	}

	// Description: Method to initialize variables in new game
	// Parameters: ---
	// Return: ---
	public void initialize() {
		playerList[0] = new Player1();
		playerList[1] = new Player2();
		playerList[2] = new Player3();
		enemyList[0] = new Boss1();
		enemyList[1] = new Boss2();
		enemyList[2] = new Boss3();
		
		enemy = enemyList[levelOption];
		player = playerList[playerOption];
		enemyBulletList = enemy.getBulletList();
		playerBulletList = player.getBulletList();
		player.setEnemy(enemy);
		enemy.setPlayer(player);
		
		attackTimer = enemy.getAttackTimer() * 60;
		
		attackThreads = enemy.getAttackList();
		attackThread = attackThreads.get(enemy.getAttackNum());
		attackThread.start();
		
		music[levelOption].start();
		music[levelOption].loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	// Description: Method to update player variables
	// Parameters: ---
	// Return: ---
	public void playerUpdate() {
		// Update player
		player.update();
		
		// Player Movement
		double speed = player.getSpeed();
		if (focused) {
			speed -= 2;
		}
		if (moveUp && moveRight && !moveDown && !moveLeft) player.moveAngle(speed, -135);
		else if (moveUp && moveLeft && !moveDown && !moveRight) player.moveAngle(speed, 135);
		else if (moveDown && moveRight && !moveUp && !moveLeft) player.moveAngle(speed, -45);
		else if (moveDown && moveLeft && !moveUp && !moveRight) player.moveAngle(speed, 45);
		else if (moveUp && !moveDown) player.moveAngle(speed, 180);
		else if (moveDown && !moveUp) player.moveAngle(speed, 0);
		else if (moveLeft && !moveRight) player.moveAngle(speed, 90);
		else if (moveRight && !moveLeft) player.moveAngle(speed, -90);
		else player.moveAngle(0, 0);
		
		// Player Sprites
		spriteTimer++;
		if (moveLeft && !moveRight) {
			if (spriteTimer % 8 == 0) spriteNum--;
			if (spriteNum >= 8 && spriteNum <= 16) spriteNum = 7;
			if (spriteNum < 0) spriteNum = 3;
		}
		else if (moveRight && !moveLeft) {
			if (spriteTimer % 8 == 0) spriteNum++;
			if (spriteNum >= 7 && spriteNum <= 15) spriteNum = 16;
			if (spriteNum > 23) spriteNum = 20;
		}
		else {
			if (spriteTimer % 8 == 0) {
				if (spriteNum > 15) spriteNum--;
				if (spriteNum <= 15) spriteNum++;
			}
			if (spriteNum == 16) spriteNum = 8;
		}
		if (spriteTimer % 8 == 0) spriteTimer = 0;
		
		// Player Bullets
		if (shoot && spriteTimer % 4 == 0) {
			playSound("shoot.wav");
			player.shoot(focused);
		}
		
		// Player Invincibility
		if (player.getInvincible() && !player.getIsBombing()) {
			invincibleTimer--;
			if (invincibleTimer <= 0) {
				player.setInvincible(false);
			}
		}
	}
	
	// Description: Method to update enemy variables
	// Parameters: ---
	// Return: ---
	public void enemyUpdate() {
		enemy.update();
		attackTimer--;
		specialScore -= (2000000/(enemy.getAttackTimer() + 10))/60;
		
		// Collision
		if (enemy.getHitbox().intersects(player.getHitbox())) {
			death();
		}
		
		// Switch Attack
		if (enemy.getHealth() <= 0 || attackTimer <= 0) {
			if (enemy.getAttackNum() % 2 == 1) {
				score += specialScore;
			}
			specialScore = 2000000;
			
			resetEnemyPortrait();
			attackThread.interrupt();
			enemy.switchAttack();
			attackTimer = enemy.getAttackTimer() * 60;
			
			if (enemy.getLives() > 0) {
				attackThread = attackThreads.get(enemy.getAttackNum());
				attackThread.start();
			}
		}
		
		// Enemy Sprites
		if (spriteTimer % 8 == 0) {
			enemySpriteNum++;
			if (enemy.getXAcc() < 0) {
				if(enemySpriteNum < 4) enemySpriteNum = 4;
				if (enemySpriteNum > 7) enemySpriteNum = 7;
			}
			else if (enemy.getXAcc() > 0) {
				if(enemySpriteNum < 8) enemySpriteNum = 8;
				if (enemySpriteNum > 11) enemySpriteNum = 11;
			}
			else if (enemySpriteNum > 3) enemySpriteNum = 0;
		}
	}
	
	// Description: Method to update enemy bullet variables
	// Parameters: ---
	// Return: ---
	public void enemyBulletUpdate() {
		for (int i = 0; i < enemyBulletList.size(); i++) {
			Bullet bullet = enemyBulletList.get(i);
			
			// Weird Bug where Bullet is null
			if (bullet == null) {
				continue;
			}
			
			bullet.update();
			
			// Out of Bounds
			if (bullet.getX() < -50 || bullet.getX() > 650 || bullet.getY() < -50 || bullet.getY() > screenHeight + 50) {
				enemyBulletList.remove(i);
				i--;
			}
			
			// Collision
			if (bullet.getHitbox().intersects(player.getHitbox())) {
				death();
			}
			
			// Near Miss
			if (Math.sqrt(Math.pow(bullet.getX() - player.getX(),2) + Math.pow(bullet.getY() - player.getY(), 2)) < 18 && !player.getInvincible() && !debugInvincible) {
				score += 2000;
				
				if (attackTimer % 2 == 0) playSound("graze.wav");
			}
		}
	}
	
	// Description: Method to update bullet variables
	// Parameters: ---
	// Return: ---
	public void playerBulletUpdate() {
		for (int i = 0; i < playerBulletList.size(); i++) {
			Bullet bullet = playerBulletList.get(i);
			bullet.update();
			
			// Out of Bounds
			if ((bullet.getX() < -50 || bullet.getX() > 650 || bullet.getY() < -50 || bullet.getY() > screenHeight + 50) && bullet.getShouldRemove()) {
				playerBulletList.remove(i);
				i--;
			}
			
			// Collision
			if (bullet.getHitbox().intersects(enemy.getHitbox()) && i >= 0) {
				if (attackTimer / 60 + 1 < enemy.getAttackTimer()) { // Enemy is invincible for 1 second
					if (playerOption == 0) score += 12;
					else if(playerOption == 1) score += 15;
					else score += 10;
					enemy.decreaseHealth(bullet.getDamage());
				}
				if (bullet.getShouldRemove()) {
					playerBulletList.remove(i);
					i--;
				}
			}
		}
	}

	// Description: Method for player death
	// Parameters: ---
	// Return: ---
	public void death() {
		if (!player.getInvincible() && !debugInvincible) {
			playSound("death.wav");
			
			enemyBulletList.clear();
			invincibleTimer = 3 * 60;
			moveRight = false;
			moveLeft = false;
			moveUp = false;
			moveDown = false;
			shoot = false;
			player.death();
		}
	}
	
	// Description: Method to reset player portrait variables
	// Parameters: ---
	// Return: ---
	public void resetPlayerPortrait() {
		portraitY = 400;
		opacity = 0f;
		opacityDirection = 1;
		textY = 550;
	}
	
	// Description: Method to reset enemy portrait variables
	// Parameters: ---
	// Return: ---
	public void resetEnemyPortrait() {
		enemyPortraitY = 400;
		enemyOpacity = 0f;
		enemyOpacityDirection = 1;
		enemyTextY = 550;
	}
	
	// Description: Method to play a sound
	// Parameters: File name
	// Return: ---
	public static void playSound(String file)
	{
	    try
	    {
	        Clip clip = AudioSystem.getClip();
	        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("Sounds/" + file));
	        clip.open(inputStream);
	        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	        volume.setValue(-15.0f);
	        clip.addLineListener(new CloseClipWhenDone());
	        clip.start();
	    }
	    catch (Exception e)
	    {
	    	System.out.println("Sound Error");
	    }        
	}
	
	// Description: Class to close a sound once it is done playing
	// Parameters: ---
	// Return: ---
	private static class CloseClipWhenDone implements LineListener
	{
	    public void update(LineEvent event)
	    {
	        if (event.getType().equals(LineEvent.Type.STOP))
	        {
	            Line soundClip = event.getLine();
	            soundClip.close();
	        }
	    }
	}
	
	// Description: Paintcomponent method
	// Parameters: Graphics g
	// Return: ---
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Background
		g.drawImage(backgrounds[levelOption], 0, 0, 600, 700, this);
			
		// Enemy
		g.drawImage(enemy.getSpriteArray()[enemySpriteNum], (int) enemy.getXTopLeft(), (int) enemy.getYTopLeft(), enemy.getWidth(), enemy.getHeight(), this);
		
		// Player Bullets
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
		for (int i = 0; i < playerBulletList.size(); i++) {
			Bullet bullet = playerBulletList.get(i);

			// Drawing Bullet
			g.drawImage(bullet.getImage(), (int) bullet.getXTopLeft(), (int) bullet.getYTopLeft(), bullet.getWidth(), bullet.getHeight(), this);
		    
			// Bullet Hitbox
			if (showHitbox) {
				g.setColor(Color.red);
				Rectangle bulletHitbox = bullet.getHitbox();
				g.fillRect((int) bulletHitbox.getX(), (int) bulletHitbox.getY(), (int) bulletHitbox.getWidth(), (int) bulletHitbox.getHeight());
			}
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		
		// Player
		if(invincibleTimer <= 2 * 60 && (invincibleTimer % 6) != 1) {
			g.setColor(Color.BLUE);
			g.drawImage(player.getSpriteArray()[spriteNum], (int) player.getXTopLeft(), (int) player.getYTopLeft(), (int) player.getWidth(), (int) player.getHeight(), this);
			if (focused) {
				Image hitboxSprite = Player.getHitboxSprite();
				g.drawImage(hitboxSprite, (int) player.getX() - hitboxSprite.getWidth(this) / 2, (int) player.getY() - hitboxSprite.getHeight(this) / 2, 20, 20, this);
				if (showHitbox) {
					Rectangle playerHitbox = player.getHitbox();
					g.setColor(Color.RED);
					g.fillRect((int) playerHitbox.getX(), (int) playerHitbox.getY(), (int) playerHitbox.getWidth(), (int) playerHitbox.getHeight());
				}
			}
		}
		
		// Enemy Bullets
		if (player.getIsBombing()) {
			enemyBulletList.clear();
		}
		for (int i = 0; i < enemyBulletList.size(); i++) {
			Bullet bullet = enemyBulletList.get(i);
			if (bullet != null) {
				// Drawing Bullet
				g.drawImage(bullet.getImage(), (int) bullet.getXTopLeft(), (int) bullet.getYTopLeft(), bullet.getWidth(), bullet.getHeight(), this);
			    
				// Bullet Hitbox
				if (showHitbox) {
					g.setColor(Color.red);
					Rectangle bulletHitbox = bullet.getHitbox();
					g.fillRect((int) bulletHitbox.getX(), (int) bulletHitbox.getY(), (int) bulletHitbox.getWidth(), (int) bulletHitbox.getHeight());
				}
			}
		}
		
		// Enemy Health Bar
		if (enemy.getAttackNum() % 2 == 0) {
			g.setColor(Color.RED);
			g.fillRect(10, 10, 100, 10);
			g.setColor(Color.GREEN);
			g.fillRect(110, 10, (int) (enemy.getHealth()/enemy.getBasicMaxHealth() * 420), 10);
		}
		else {
			g.setColor(Color.RED);
			g.fillRect(10, 10, (int) (enemy.getHealth()/enemy.getSpellMaxHealth() * 100), 10);
		}
		
		// Enemy Lives
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier", Font.BOLD, 20));
		g.drawString("Enemy " + Math.round(enemy.getLives()/2), 10, 40);
			
		// Attack Timer
		g.setFont(new Font("Courier", Font.BOLD, 30));
		g.drawString(String.format("%.0f", attackTimer / 60), 550, 30);
		
		// Player Bomb
		if (player.getIsBombing()) {
			// Portrait
			if (opacity >= 0.9f) {
				opacityDirection *= -1;
			}
			if (opacity > 0) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
				g2.drawImage(player.getImage(), 10, portraitY, this);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			}
			else if (textY < 680) textY += 5;
			// Text
			g.setFont(new Font("Courier", Font.BOLD, 20));
			FontMetrics fontMetrics = g.getFontMetrics();
			g.setColor(Color.RED);
			g.fillRect(20, textY - 25, fontMetrics.stringWidth("Spell Sign: " + player.getBombName()) + 20, 35);
			g.setColor(Color.WHITE);
			g.drawString("Spell Sign: " + player.getBombName(), 30, textY);
			
			// Variables
			portraitY--;
			opacity += 0.02 * opacityDirection;
		}
		
		// Enemy Spell
		if (enemy.getAttackNum() % 2 == 1) {
			// Portrait
			if (enemyOpacity >= 0.9f) {
				enemyOpacityDirection *= -1;
			}
			if (enemyOpacity > 0) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, enemyOpacity));
				g2.drawImage(enemy.getImage(), 320, enemyPortraitY, this);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			}
			else if (enemyTextY > 60) enemyTextY -= 10;
			// Text
			g.setFont(new Font("Courier", Font.BOLD, 20));
			FontMetrics fontMetrics = g.getFontMetrics();
			g.setColor(Color.RED);
			g.fillRect(570 - fontMetrics.stringWidth(enemy.getAttackName()), enemyTextY - 25, fontMetrics.stringWidth(enemy.getAttackName()) + 20, 35);
			g.setColor(Color.WHITE);
			g.drawString(enemy.getAttackName(), 580 - fontMetrics.stringWidth(enemy.getAttackName()), enemyTextY);
			
			// Special Score
			g.setFont(new Font("Courier", Font.BOLD, 15));
			g.setColor(Color.RED);
			g.fillRect(440, enemyTextY + 10, 150, 30);
			g.setColor(Color.WHITE);
			g.drawString("Score: " + specialScore, 455, enemyTextY + 30);
			
			// Variables
			enemyPortraitY--;
			enemyOpacity += 0.02 * enemyOpacityDirection;
		}
		
		// Scoreboard
		g.setFont(new Font("Courier", Font.BOLD, 30));
		g.drawImage(sidebar, 600, 0, 300, 700, this);
		
		// Score
		g.setColor(Color.WHITE);
		g.drawString("Score", 640, 50);
		g.drawString(String.format("%010d", score), 640, 90);
		
		// Lives
		g.setColor(Color.RED);
		g.drawString("Lives", 640, 175);
		for (int i = 0; i < player.getLives(); i++) {
			g.drawImage(lifeIcon, 640 + 30 * i, 195, 25, 25, this);
		}
		
		// Bombs
		g.setColor(Color.GREEN);
		g.drawString("Bombs", 640, 300);
		for (int i = 0; i < player.getBombs(); i++) {
			g.drawImage(bombIcon, 640 + 30 * i, 320, 25, 25, this);
		}
		
		// Logo
		g.drawImage(logo, 650, 500, 200, 96, this);
		
		// Debug
		if (showHitbox) {
			g.setFont(new Font("Courier", Font.BOLD, 20));
			g.setColor(Color.WHITE);
			g.drawString("Hitboxes: ON", 640, 400);
		}
		if (debugInvincible) {
			g.setFont(new Font("Courier", Font.BOLD, 20));
			g.setColor(Color.WHITE);
			g.drawString("Invincibility: ON", 640, 450);
		}
	}
	
	public void keyPressed(KeyEvent e) {
		// If not dead
		if(invincibleTimer <= 2 * 60) {
			// Shooting
			if (e.getKeyCode() == KeyEvent.VK_Z) shoot = true;
			if (e.getKeyCode() == KeyEvent.VK_X && !player.getIsBombing() && player.getBombs() > 0) {
				player.bomb();
				invincibleTimer = 0;
				resetPlayerPortrait();
				
				playSound("bomb.wav");
			}
			
			// Player Movement
			if (e.getKeyCode() == KeyEvent.VK_LEFT) moveLeft = true;
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) moveRight = true;
			if (e.getKeyCode() == KeyEvent.VK_UP) moveUp = true;
			if (e.getKeyCode() == KeyEvent.VK_DOWN) moveDown = true;
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) focused = true;
		}
		
		// Debug
		if (e.getKeyCode() == KeyEvent.VK_H) showHitbox = !showHitbox;
		if (e.getKeyCode() == KeyEvent.VK_I) {
			invincibleTimer = 0;
			debugInvincible = !debugInvincible;
		}
	}

	public void keyReleased(KeyEvent e) {
		// Shooting
		if (e.getKeyCode() == KeyEvent.VK_Z) shoot = false;
		
		// Player Movement
		if (e.getKeyCode() == KeyEvent.VK_LEFT) moveLeft = false;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) moveRight = false;
		if (e.getKeyCode() == KeyEvent.VK_UP) moveUp = false;
		if (e.getKeyCode() == KeyEvent.VK_DOWN) moveDown = false;
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) focused = false;
	}
	
	public void keyTyped(KeyEvent e) {}
}
