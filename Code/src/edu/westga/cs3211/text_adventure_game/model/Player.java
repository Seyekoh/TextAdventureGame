package edu.westga.cs3211.text_adventure_game.model;

import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;

/**
 * The player in the game
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class Player {
	private int health;
	private List<Item> inventory;
	
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
	public void applyDamage(int damage) {
		this.health -= damage;
		if (this.health < 0) {
			this.health = 0;
		}
    }
	
	/**
	 * Checks whether a player has a specific item
	 * 
	 * @param item the item to check for
	 * @return true if the player has the item, false otherwise
	 */
	public boolean doesPlayerHaveItem(Item item) {
		return this.inventory.contains(item);
	}
	
	/**
	 * Gets the player's inventory
	 * @return inventory the player's inventory
	 */
	public List<Item> getInventory() {
		ArrayList<Item> inventory = new ArrayList<Item>();
		inventory.addAll(this.inventory);
		return inventory;
	}
	
	/**
	 * Adds an item to the player's inventory
	 * @param item the item to add
	 */
	public void addItemToInventory(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null or blank");
		}
		this.inventory.add(item);
	}
	
	/**
	 * Removes an item from the player's inventory
	 * @param item the item to remove
	 * @return true if the item was removed, false otherwise
	 */
	public boolean removeItemFromInventory(Item item) {
		return this.inventory.remove(item);
	}
	
}
