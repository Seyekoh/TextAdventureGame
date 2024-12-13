package edu.westga.cs3211.text_adventure_game.model;

import java.util.HashMap;
import java.util.Map;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.LocationName;

/**
 * The world in the game
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class World {
	private HazardManager hazardManager;
	private Map<LocationName, Location> locations;
	private Location startLocation;
	private Location goalLocation;
	private NPC npc;
	
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
	 * Gets the goal location
	 * 
	 * @return the goal location
	 */
	public Location getGoalLocation() {
		return this.goalLocation;
	}
	
	/**
	 * Gets the npc
	 * 
	 * @return the npc
	 */
	public NPC getNPC() {
		return this.npc;
	}
	
	/**
	 * Sets the start location of the world
	 * @param startLocation the start location
	 */
	public void setStartLocation(Location startLocation) {
		this.startLocation = startLocation;
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
	 * Sets the hazard type for a location
	 * 
	 * @param location the location to set the hazard type
	 * @param hazardData the hazard data to set
	 */
	public void setHazardTypeForLocation(Location location, HazardData hazardData) {
		location.setHazardType(this.hazardManager.getHazardTypeFromHazardData(hazardData));
	}
	
	/**
	 * Sets the goal location in the world. 
	 * 
	 * @param newGoalLocation the new goal location
	 */
	public void setGoalLocation(Location newGoalLocation) {
		this.goalLocation = newGoalLocation;
	}
	
	/**
	 * Gets the goal location by it's name
	 * 
	 * @param name the name of the location
	 * @return the goal location
	 */
	public Location getLocationByName(LocationName name) {
		Location location = this.locations.get(name);
	    if (location == null) {
	        throw new IllegalArgumentException("Location not found: " + name);
	    }
	    return location;
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
	public Map<LocationName, Location> getAllLocations() {
		return this.locations;
    }
	
	/**
	 * Adds the npc to the world.
	 * 
	 * @param npc the npc to add
	 */
	public void addNPC(NPC npc) {
		this.npc = npc;
	}
	
	/**
	 * Moves the npc to the destination
	 * 
	 * @param npc the npc to move
	 * @param destination the location that should contain the npc
	 */
	public void moveNPCToLocation(NPC npc, Location destination) {
		destination.setNPC(npc);
	}
}
