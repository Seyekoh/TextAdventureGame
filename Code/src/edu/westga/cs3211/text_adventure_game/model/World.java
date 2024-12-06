package edu.westga.cs3211.text_adventure_game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;

/**
 * The world in the game
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class World {
	private HazardManager hazardManager;
	private Map<String, Location> locations;
	private Location startLocation = new Location("Start", "The start of your adventure.", HazardType.NONE, false, new ArrayList<>());
	private Location goalLocation;
	
	/**
	 * Creates a new World object
	 */
	public World() {
		this.hazardManager = new HazardManager();
		this.locations = new HashMap<>();
	}
	
	/**
	 * Gets the start location
	 * 
	 * @return the start location
	 */
	public Location getStartLocation() {
		return this.startLocation;
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
	 * Gets the hazard data for a location
	 * 
	 * @param location	the location to get the hazard data for
	 * 
	 * @return	    the hazard data for the location
	 */
	public HazardData getHazardDataForLocation(Location location) {
		return this.hazardManager.getHazardData(location.getHazardType());
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
	 * 
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
		if (toLocation.getConnection(toLocation.getOppositeDirection(direction)) != null) {
			throw new IllegalArgumentException(toLocation.getName() + " already has a connection to the " + direction);
		}
		
		fromLocation.addConnection(direction, toLocation);
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
