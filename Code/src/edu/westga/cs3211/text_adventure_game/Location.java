package edu.westga.cs3211.text_adventure_game;

import java.util.ArrayList;

/**
 * The location in the game
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class Location {
	private String description;
	private int hazardDamage;
	private Boolean isGoal;
	private ArrayList<Action> actions;
	
	/**
	 * Creates a new Location object
	 * 
	 * @param description  	the location's description
	 * @param hazardDamage 	the damage the location causes
	 * 		        		0 if no hazard present
	 * @param isGoal 		if the location is the goal
	 * @param actions 		the actions available at the location
	 */
	public Location(String description, int hazardDamage, Boolean isGoal, ArrayList<Action> actions) {
		this.description = description;
		this.hazardDamage = hazardDamage;
		this.isGoal = isGoal;
		this.actions = actions;
	}
}
