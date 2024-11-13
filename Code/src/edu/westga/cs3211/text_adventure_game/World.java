package edu.westga.cs3211.text_adventure_game;

import java.util.HashMap;
import java.util.Map;

import edu.westga.cs3211.text_adventure_game.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.GlobalEnums.HazardType;

/**
 * The world in the game
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class World {
	private Map<String, Location> locations;
	private Location goalLocation;
	
	/**
	 * Creates a new World object
	 */
	public World() {
		this.locations = new HashMap<>();
	}
	
	/**
	 * Adds a location to the world
	 * 
	 * @param location the location to add
	 */
	public void addLocation(Location location) {
		this.locations.put(location.getName(), location);
		if (location.checkIfLocationIsGoal()) {
			this.setGoalLocation(location);
		}
	}
	
	/**
	 * Sets the goal location in the world. 
	 * 
	 * @param newGoalLocation the new goal location
	 */
	public void setGoalLocation(Location newGoalLocation) {
		if (this.goalLocation == null) {
			this.goalLocation = newGoalLocation;
			newGoalLocation.setIsGoal(true);
		} else {
			throw new IllegalArgumentException("Goal location already set");
		}
	}
	
	/**
	 * Gets the goal location by it's name
	 * 
	 * @param name the name of the location
	 * @return the goal location
	 */
	public Location getLocationByName(String name) {
		return this.locations.get(name);
    }
	
	/**
	 * Checks if the location is a hazard.
	 * 
	 * @param location the location to check
	 * @return true if the location is a hazard
	 * 		   false otherwise
	 */
	public boolean checkIfLocationIsHazard(Location location) {
		return location.getHazardType() != HazardType.NONE;
    }
	
	/**
	 * Checks if a location is the goal
	 * 
	 * @param location the location to check
	 * @return true if the location is the goal
	 *         false otherwise
	 */
	public boolean checkIfLocationIsGoal(Location location) {
		return location.checkIfLocationIsGoal();
    }
	
	/**
	 * Connects two locations
	 * 
	 * @param fromLocation	the location to connect from
	 * @param direction		the direction to connect
	 * @param toLocation    the location to connect to
	 */
	public void connectLocations(Location fromLocation, Direction direction, Location toLocation) {
		if (fromLocation.getConnection(direction) != null) {
			throw new IllegalArgumentException(fromLocation.getName() + " already has a connection to the " + direction);
		}
		if (toLocation.getConnection(this.getOppositeDirection(direction)) != null) {
			throw new IllegalArgumentException(toLocation.getName() + " already has a connection to the " + direction);
		}
		
		fromLocation.addConnection(direction, toLocation);
		toLocation.addConnection(this.getOppositeDirection(direction), fromLocation);
	}
	
	private Direction getOppositeDirection(Direction direction) {
		switch (direction) {
		case NORTH:
			return Direction.SOUTH;
		case SOUTH:
			return Direction.NORTH;
		case EAST:
			return Direction.WEST;
		case WEST:
			return Direction.EAST;
		default:
			throw new IllegalArgumentException("Invalid direction");
		}
	}
	
	/**
	 * Gets all locations in the world
	 * 
	 * @return a map of the locations in the world
	 */
	public Map<String, Location> getAllLocations() {
		return this.locations;
    }
}
