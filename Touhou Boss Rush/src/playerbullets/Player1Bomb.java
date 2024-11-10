package playerbullets;
//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to hold player 1 bomb information
import java.awt.Rectangle;

import enemies.*;
import enemybullets.Bullet;

public class Player1Bomb extends Bullet {
	private double speed;
	private int timer;
	private int timerMax;
	private Enemy enemy;
	
	// Description: Constructor method
	// Parameters: x position, y position, speed, angle, time before bullet homes, enemy
	// Return: ---
	public Player1Bomb(double xPos, double yPos, double speed, double angle, int timerMax, Enemy enemy) {
		super("blue", 0, xPos, yPos, speed, angle);
		timer = 0;
		damage = 30;
		this.speed = speed;
		this.timerMax = timerMax;
		this.enemy = enemy;
		
		// Size and Hitbox
		width = 80;
		height = 80;
		hitbox = new Rectangle(50, 50);
		
		// Sprite Rewrite
		int randomNum = (int) (Math.random() * 3.99);
		int x;
		int y;
		if (randomNum == 0) {
			x = 397;
			y = 32;
		}
		else if (randomNum == 1) {
			x = 461;
			y = 32;
		}
		else if (randomNum == 2) {
			x = 270;
			y = 96;
		}
		else  {
			x = 334;
			y = 96;
		}
		originalSprite = bulletSpriteSheet.getSubimage(x, y, 64, 64);
		sprite = rotateSprite(originalSprite, angle + 180);
	}
	
	// Description: Method to override update
	// Parameters: ---
	// Return: ---
	public void update() {
		super.update();
		if (timer <= timerMax) {
			timer++;
		}
		else {
			movePoint(enemy.getX(), enemy.getY(), speed);
		}
	}
}
