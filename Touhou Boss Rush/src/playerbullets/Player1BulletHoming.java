package playerbullets;
//Names: Dylan Wang and Simon Gao
//Date: June 14, 2024
//Description: Class to hold player 1 homing bullet information
import java.awt.Rectangle;

import enemies.*;
import enemybullets.Bullet;

public class Player1BulletHoming extends Bullet {
	private double speed;
	private int timer;
	private int timerMax;
	private Enemy enemy;
	
	// Description: Constructor method
	// Parameters: x position, y position, speed, angle, time before bullet homes, enemy
	// Return: ---
	public Player1BulletHoming(double xPos, double yPos, double speed, double angle, int timerMax, Enemy enemy) {
		super("blue", 128, xPos, yPos, speed, angle);
		timer = 0;
		damage = 1;
		this.speed = speed;
		this.timerMax = timerMax;
		this.enemy = enemy;
		
		// Size and Hitbox
		width = 20;
		height = 20;
		hitbox = new Rectangle(10, 10);
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
