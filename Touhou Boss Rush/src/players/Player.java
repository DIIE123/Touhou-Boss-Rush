package players;
// Names: Dylan Wang and Simon Gao
// Date: June 14, 2024
// Description: Class to hold player information

import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;

import basic.BasicObject;
import enemies.*;
import enemybullets.*;

public abstract class Player extends BasicObject {
	// Player Variables
	protected double speed;
	protected int lives;
	protected int bombs;
	protected boolean isBombing;
	protected boolean invincible;
	protected String bombName;
	protected Image[] spriteArray;
	protected Enemy enemy;
	
	protected static Image hitboxSprite;
	
	// Bullet Variables
	ArrayList<Bullet> bulletList;
	
	// Description: Constructor method
	// Parameters: spriteSheet
	// Return: ---
	public Player(BufferedImage spriteSheet) {
		// Variables
		xPos = 300;
		yPos = 500;
		xVel = 0;
		yVel = 0;
		width = 48;
		height = 72;
		hitbox = new Rectangle(10, 10);
		bulletList = new ArrayList<>();
		
		lives = 3;
		bombs = 3;
		
		// Sprites
		int index = 0;
		spriteArray = new Image[24];
		for (int i = 7; i >= 0; i--) {
			spriteArray[i] = spriteSheet.getSubimage(index * 32 + 1, 48, 32, 48);
			index++;
		}
		for (int i = 8; i <= 15; i++) {
			spriteArray[i] = spriteSheet.getSubimage((index-8) * 32 + 1, 0, 32, 48);
			index++;
		}
		for (int i = 16; i <= 23; i++) {
			spriteArray[i] = spriteSheet.getSubimage((index-16) * 32 + 1, 96, 32, 48);
			index++;
		}
	}
	
	// Description: Method to override update to stop going out of bounds
	// Parameters: ---
	// Return: ---
	public void update() {
		super.update();
		
		if (xPos + width/2 > 600) xPos = 600 - width/2;
		if (xPos - width/2 < 0 ) xPos = 0 + width/2;
		if (yPos + height/2 > 700) yPos = 700 - height/2;
		if (yPos - height/2 < 0) yPos = 0 + height/2;
	}
	
	// Description: Method for player death
	// Parameters: ---
	// Return: ---
	public void death() {
		invincible = true;
		lives--;
		xPos = 300;
		yPos = 500;
	}
	
	// Description: Abstract methods to shoot a bullet and to bomb
	// Parameters: ---
	// Return: ---
	public abstract void shoot(boolean focused);
	public abstract void bomb();
	
	
	// Description: Getter methods
	// Parameters: ---
	// Return: speed, lives, bombs, spritearray, hitbox sprite, bullet list, isBombing, invincible, bomb name
	public double getSpeed() {
		return speed;
	}
	
	public int getLives() {
		return lives;
	}
	
	public int getBombs() {
		return bombs;
	}
	
	public Image[] getSpriteArray() {
		return spriteArray;
	}
	
	public static Image getHitboxSprite() {
		return hitboxSprite;
	}
	
	public ArrayList<Bullet> getBulletList() {
		return bulletList;
	}
	
	public boolean getIsBombing() {
		return isBombing;
	}
	
	public boolean getInvincible() {
		return invincible;
	}
	
	public String getBombName() {
		return bombName;
	}
	
	// Description: Setter methods
	// Parameters: speed, enemy, invincible, hitbox sprite
	// Return: ---
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}
	
	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}
	
	public static void setHitboxSprite(Image image) {
		hitboxSprite = image;
	}
}
