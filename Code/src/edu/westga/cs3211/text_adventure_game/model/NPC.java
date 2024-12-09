package edu.westga.cs3211.text_adventure_game.model;

/**
 * An NPC in the game
 * 
 * @author Liam Worthington
 * @version Fall 2024
 */
public class NPC {
	private String description;

	/**
	 * Creates a new NPC object
	 * 
	 * @param description  	the NPC description
	 */
	public NPC(String description) {

		if (description == null) {
			throw new IllegalArgumentException("Description cannot be null");
		}
		if (description.isBlank()) {
			throw new IllegalArgumentException("Description cannot be blank");
		}
		
		this.description = description;
	}
	
	/**
	 * Gets the location's description
	 * 
	 * @return the location's description
	 */
	public String getDescription() {
		return this.description;
    }
}
