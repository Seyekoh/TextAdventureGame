package edu.westga.cs3211.text_adventure_game;

/**
 * An action in the game
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class Action {
	private String name;
	private String description;
	private ActionType type;
	
	/**
	 * Creates a new Action object
	 * 
	 * @param name        the action's name
	 * @param description the action's description
	 * @param type        the action's type
	 */
	public Action(String name, String description, ActionType type) {
		this.name = name;
        this.description = description;
        this.type = type;
    }
	
	/**
	 * Gets the action's name
	 */
	public String getName() {
		return this.name;
    }
	
	/**
	 * Gets the action's description
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Gets the action's type
	 */
	public ActionType getType() {
		return this.type;
    }
	
}
