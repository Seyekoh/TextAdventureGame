
package edu.westga.cs3211.text_adventure_game.model;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.ActionType;

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
		if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
		}
		if (name.isBlank()) {
			throw new IllegalArgumentException("Name cannot be blank or empty");
		}
		if (description == null) {
			throw new IllegalArgumentException("Description cannot be null");
		}
		if (description.isBlank()) {
			throw new IllegalArgumentException("Description cannot be blank or empty");
		}
		if (type == null) {
			throw new IllegalArgumentException("Type cannot be null");
		}
		
		this.name = name;
        this.description = description;
        this.type = type;
    }
	
	/**
	 * Gets the action's name
	 * 
	 * @return the name of the action
	 */
	public String getName() {
		return this.name;
    }
	
	/**
	 * Gets the action's description
	 * 
	 * @return the description of the action
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Gets the action's type
	 * 
	 * @return the type of the action
	 */
	public ActionType getType() {
		return this.type;
    }
	
	@Override
	public String toString() {
		return this.name + ": " + this.description;
	}
	
}
