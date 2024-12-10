package edu.westga.cs3211.text_adventure_game.model;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.LocationName;

/**
 * The location in the game
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class Location {
	private LocationName name;
	private String description;

	private HazardType hazardType;

	private boolean isGoal;

	private List<Action> actions;

	private EnumMap<Direction, Location> connections;

	private NPC npc;

	/**
	 * Creates a new Location object
	 * 
	 * @param name        the location's name
	 * @param description the location's description
	 * @param hazardType  the type of hazard
	 * @param isGoal      if the location is the goal
	 * @param actions     the actions available at the location
	 */
	public Location(LocationName name, String description, HazardType hazardType, boolean isGoal,
			List<Action> actions) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		if (description == null) {
			throw new IllegalArgumentException("Description cannot be null");
		}
		if (description.isBlank()) {
			throw new IllegalArgumentException("Description cannot be blank");
		}
		if (hazardType == null) {
			throw new IllegalArgumentException("Hazard Type cannot be null");
		}
		if (actions == null) {
			throw new IllegalArgumentException("Actions cannot be null");
		}

		this.name = name;
		this.description = description;
		this.hazardType = hazardType;
		this.isGoal = isGoal;
		this.actions = actions;

		this.connections = new EnumMap<>(Direction.class);
	}

	/**
	 * Gets the location's name
	 * 
	 * @return the location's name
	 */
	public LocationName getName() {
		return this.name;
	}

	/**
	 * Gets the location's description
	 * 
	 * @return the location's description
	 */
	public String getDescription() {
		if (!this.isNPCPresent()) {
			return this.description;
		} else {
			return this.description.concat(" " + this.npc.getDescription());
		}

	}

	/**
	 * Gets the location's hazard type
	 * 
	 * @return the location's hazard type
	 */
	public HazardType getHazardType() {
		return this.hazardType;
	}

	/**
	 * Sets the location's hazard type
	 * 
	 * @param hazardType the hazard type to set
	 */
	public void setHazardType(HazardType hazardType) {
		this.hazardType = hazardType;
	}

	/**
	 * Gets if the location is the goal
	 * 
	 * @return if the location is the goal
	 */
	public boolean checkIfLocationIsGoal() {
		return this.isGoal;
	}

	/**
	 * Sets the location is the goal
	 * 
	 * @param isGoal if the location is the goal
	 */
	public void setIsGoal(boolean isGoal) {
		this.isGoal = isGoal;
	}

	/**
	 * Gets the actions available at the location
	 * 
	 * @return the actions available at the location
	 */
	public List<Action> getActions() {
		return this.actions;
	}

	/**
	 * Sets the actions available at the location
	 * 
	 * @param actions the actions available at the location
	 */
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	/**
	 * Sets the connections from the location
	 * 
	 * @param direction     the direction of the connection
	 * @param otherLocation the location to connect to
	 */
	public void addConnection(Direction direction, Location otherLocation) {
		if (this.connections.containsKey(direction)) {
			throw new IllegalArgumentException("Connection already exists in that direction");
		}
		this.connections.put(direction, otherLocation);
		otherLocation.connections.put(this.getOppositeDirection(direction), this);
	}

	/**
	 * Gets the opposite direction of the given direction
	 * 
	 * @param direction the direction to get the opposite direction
	 * 
	 * @return the opposite direction
	 */
	public Direction getOppositeDirection(Direction direction) {
		switch (direction) {
		case NORTH:
			return Direction.SOUTH;
		case SOUTH:
			return Direction.NORTH;
		case EAST:
			return Direction.WEST;
		case WEST:
			return Direction.EAST;
		case UP:
			return Direction.DOWN;
		case DOWN:
			return Direction.UP;
		default:
			throw new IllegalArgumentException("Invalid direction");
		}
	}

	/**
	 * Gets the location connected in the given direction
	 * 
	 * @param direction the direction to get the connected location
	 * @return the connected location
	 */
	public Location getConnection(Direction direction) {
		return this.connections.get(direction);
	}

	/**
	 * Gets all connections from the location
	 * 
	 * @return the connections from the location
	 */
	public Map<Direction, Location> getConnections() {
		return this.connections;
	}

	/**
	 * Places an npc to the location.
	 * 
	 * @param npc the npc to set.
	 */
	public void setNPC(NPC npc) {
		this.npc = npc;
	}

	/**
	 * Removes the npc from the location.
	 * 
	 */
	public void removeNPC() {
		this.npc = null;
	}

	/**
	 * Checks if an npc is present in the location
	 * 
	 * @return true if present.
	 */
	public boolean isNPCPresent() {
		return this.npc != null;
	}

	@Override
	public String toString() {
		return this.name.toString() + ": " + this.description;
	}
}
