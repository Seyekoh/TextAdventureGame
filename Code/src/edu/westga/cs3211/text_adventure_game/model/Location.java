package edu.westga.cs3211.text_adventure_game.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.LocationName;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;

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

	private boolean isSearched;

	private List<Action> actions;

	private List<Item> items;

	private EnumMap<Direction, Location> connections;

	private Item startingItem;

	/**
	 * Creates a new Location object
	 * 
	 * @param name        the location's name
	 * @param description the location's description
	 * @param hazardType  the type of hazard
	 * @param isGoal      if the location is the goal
	 * @param actions     the actions available at the location
	 * @param item        the item at the location
	 */
	public Location(LocationName name, String description, HazardType hazardType, boolean isGoal, List<Action> actions,
			Item item) {
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

		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}

		this.name = name;
		this.description = description;
		this.hazardType = hazardType;
		this.isGoal = isGoal;
		this.actions = actions;
		this.startingItem = item;

		this.items = new ArrayList<>();
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
	 * Gets if the location has been searched.
	 * 
	 * @return true if the location has been searched, false otherwise.
	 */
	public boolean isSearched() {
		return this.isSearched;
	}

	/**
	 * Sets the location as searched.
	 */
	public void setSearched() {
		if (!this.isSearched || this.startingItem != Item.NONE) {
			this.items.add(this.startingItem);
		}
		this.isSearched = true;
	}
	
	/**
	 * Gets the starting item. 
	 * 
	 * @return the starting item
	 */
	public Item getStartingItem() {
        return this.startingItem;
	}

	/**
	 * Gets if the starting item has been found
	 * 
	 * @return true if the starting item has been found, false otherwise
	 */
	public boolean hasStartingItemBeenFound() {
		return this.items.contains(this.startingItem);
	}

	/**
	 * Gets the location's description
	 * 
	 * @return the location's description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the location's description
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
		if (actions == null) {
			throw new IllegalArgumentException("Actions cannot be null");
		}

		this.actions = actions;
	}

	/**
	 * Adds an action to the location
	 * 
	 * @param action the action to add
	 */
	public void addAction(Action action) {
		if (action == null) {
			throw new IllegalArgumentException("Action cannot be null");
		}
		this.actions.add(action);
	}

	/**
	 * Gets the items at the location
	 * 
	 * @return the items at the location
	 */
	public List<Item> getItems() {
		List<Item> items = new ArrayList<>();
		items.addAll(this.items);
		return items;
	}

	/**
	 * Adds an item to the location
	 * 
	 * @param item the item to add
	 */
	public void addItem(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}
		this.items.add(item);
	}

	/**
	 * Removes an item from the location
	 * 
	 * @param item the item to remove
	 */
	public void removeItem(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}
		
		this.items.remove(item);
	}

	/**
	 * Adds a connection to the location
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

	@Override
	public String toString() {
		return this.name.toString() + ": " + this.description;
	}
}
