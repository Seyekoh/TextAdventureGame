package edu.westga.cs3211.text_adventure_game.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The player in the game
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class Player {
	private int health;
	private List<String> inventory;
	
	/**
	 * Creates a new Player object
	 */
	public Player() {
		this.health = 100;
		this.inventory = new ArrayList<>();
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
	
	/**
	 * Gets the player's inventory
	 * @return inventory the player's inventory
	 */
	public List<String> getInventory() {
		return this.inventory;
	}
	
	/**
	 * Adds an item to the player's inventory
	 * @param item the item to add
	 */
	public void addItemToInventory(String item) {
		if (item == null || item.isBlank()) {
			throw new IllegalArgumentException("Item cannot be null or blank");
		}
		this.inventory.add(item);
	}
	
	/**
	 * Removes an item from the player's inventory
	 * @param item the item to remove
	 * @return true if the item was removed, false otherwise
	 */
	public boolean removeItemFromInventory(String item) {
		return this.inventory.remove(item);
	}
	
}
