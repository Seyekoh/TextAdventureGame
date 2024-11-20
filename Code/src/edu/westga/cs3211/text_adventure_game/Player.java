package edu.westga.cs3211.text_adventure_game;

/**
 * The player in the game
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class Player {
	private int health;
	
	/**
	 * Creates a new Player object
	 */
	public Player() {
		this.health = 100;
	}
	
	/**
	 * Gets the player's health
	 * 
	 * @return the player's health
	 */
	public int getHealth() {
		return this.health;
	}
	
	/**
	 * Decreases the player's health
	 * 
	 * @param damage the amount of damage to take
	 */
	public void takeDamage(int damage) {
		this.health -= damage;
    }
}
