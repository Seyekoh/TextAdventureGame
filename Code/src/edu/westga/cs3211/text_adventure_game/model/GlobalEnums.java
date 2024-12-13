package edu.westga.cs3211.text_adventure_game.model;

/**
 * This class serves as a container for various enums used throughout the
 * program.
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GlobalEnums {

	/**
	 * The type of action that can be performed
	 */
	public enum ActionType {
		MOVE, SEARCH, TAKE, USE, DROP, EXAMINE, TALK, GIVE, NONE
	}

	/**
	 * The direction the player can move
	 */
	public enum Direction {
		NORTH, SOUTH, EAST, WEST, UP, DOWN
	}

	/**
	 * The type of hazard at a location
	 */
	public enum HazardType {
		NONE, GHOSTLYAPPARITION, CURSEDSTOVE, ROTTINGCORPSE, CREEPYDOLL, DANCINGSHADOWS
	}

	/**
	 * The names of the locations in the game
	 */
	public enum LocationName {
		ENTRANCEHALL, LIBRARY, KITCHEN, BALLROOM, BASEMENT, ATTIC, EXIT
	}

	/**
	 * The items available in the game. Each item must have a description
	 */
	public enum Item {
		POTION("A shimmering vial of ruby liquid, promising vitality—or peril—if consumed."),
		RING("A delicate band adorned with a beautiful diamond."),
		DIARY("A tattered leather-bound diary, its pages filled with faded ink."),
		MUSICBOX("A quaint wooden music box with intricate carvings."), 
		DRESS("A radiant garment of deep crimson, its fabric whispering stories of grandeur and sorrow."),
		NONE("No item");

		private final String description;

		/**
		 * Initializes an Item.
		 * @param description the description of the item
		 */
		Item(String description) {
			this.description = description;
		}

		/**
		 * Gets the description of the item.
		 * @return the description of the item.
		 */
		public String getDescription() {
			return this.name() + ": " + this.description;
		}
	}

}
