package enemies;
// Names: Dylan Wang and Simon Gao
// Date: June 14, 2024
// Description: Abstract class to hold general enemy information

import java.awt.*;
import java.util.*;

import basic.BasicObject;
import enemybullets.*;
import players.*;

public abstract class Enemy extends BasicObject {
	// Variables
	protected double basicMaxHealth;
	protected double spellMaxHealth;
	protected double health;
	protected int lives;
	protected int attackNum;
	protected double attackTimer;
	protected String attackName;
	
	protected static Player player;
	protected Image[] spriteArray;
	
	// Collections
	protected ArrayList<Thread> attackList;
	protected ArrayList<Bullet> bulletList;
	
	// Description: Constructor method
	// Parameters: ---
	// Return: ---
	public Enemy() {
		// Variables
		attackNum = 0;
		xPos = 300;
		yPos = 150;
		xVel = 0;
		yVel = 0;
		width = 48;
		height = 72;
		hitbox = new Rectangle((int) xPos, (int) yPos, width, 20);
		bulletList = new ArrayList<>();
		attackList = new ArrayList<>();
		spriteArray = new Image[12];
	}
	
	// Description: Method to override update to stop going out of bounds
	// Parameters: ---
	// Return: ---
	public void update() {
		super.update();
		
		if (xPos + width/2 > 500) xPos = 500 - width/2;
		if (xPos - width/2 < 100 ) xPos = 100 + width/2;
		if (yPos + height/2 > 250) yPos = 250 - height/2;
		if (yPos - height/2 < 50) yPos = 50 + height/2;
	}
		
	// Description: Method to launch to random position (x:100-500, y: 100-250)
	// Parameters: Time
	// Return: ---
	public void launchRandom(double time) {
		launch ((Math.random()*(500-100+1)) + 100, (Math.random()*(250-100+1)) + 100, time);
	}
	
	// Description: Method to launch to random position within a set distance
	// Parameters: Distance, time
	// Return: ---
	public void launchRandomClose(double distance, double time) {
		launch(xPos + Math.random()*(2 * distance+1) - distance, yPos + Math.random()*(2 * distance +1) - distance, time);
	}
	
	// Description: Method to switch to next attack
	// Parameters: ---
	// Return: ---
	public void switchAttack() {
		bulletList.clear();
		lives--;
		attackNum++;
		
		if (attackNum % 2 == 0) {
			health = basicMaxHealth;
			playSound("enemydeath.wav");
		}
		else {
			health = spellMaxHealth;
			playSound("bomb.wav");
		}
	}
	
	// Description: Getter methods
	// Parameters: ---
	// Return: bulletList, attackList, health, max health, lives, attack num, attack timer, attack name, spriteArray
	public ArrayList<Bullet> getBulletList() {
		return bulletList;
	}
	
	public ArrayList<Thread> getAttackList() {
		return attackList;
	}
	
	public int getAttackNum() {
		return attackNum;
	}
	
	public double getAttackTimer() {
		return attackTimer;
	}
	
	public String getAttackName() {
		return attackName;
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getBasicMaxHealth() {
		return basicMaxHealth;
	}
	
	public double getSpellMaxHealth() {
		return spellMaxHealth;
	}
	
	public double getLives() {
		return lives;
	}
	
	public Image[] getSpriteArray() {
		return spriteArray;
	}
	
	// Description: Setter/Incrementer methods
	// Parameters: Player, health, attack timer
	// Return: ---
	public void setPlayer(Player p) {
		player = p;
	}
	
	public void decreaseHealth(double damage) {
		health -= damage;
	}
	
	public void increaseTimer() {
		attackTimer++;
	}
}
