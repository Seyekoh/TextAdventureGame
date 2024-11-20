package edu.westga.cs3211.text_adventure_game.model;

/**
 * This class serves as a container for various enums used throughout the program.
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GlobalEnums { 
	
	/**
	 * The type of action that can be performed
	 */
	public enum ActionType {
		MOVE
	}
	
	/**
	 * The direction the player can move
	 */
	public enum Direction {
		NORTH,
		SOUTH,
		EAST,
		WEST
	}
	
	/**
	 * The type of hazard at a location
	 */
	public enum HazardType {
		NONE,
		PIT
	}
}
