package edu.westga.cs3211.text_adventure_game;

import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3211.text_adventure_game.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.GlobalEnums.HazardType;

/**
 * The world manager
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class WorldManager {
	
	private World world;

	/**
	 * The constructor for the WorldManager
	 */
	public WorldManager() {
		this.world = new World();
		
		this.initializeWorld();
	}

	private void initializeWorld() {
		ArrayList<Location> createdLocations = this.createLocations();
		for (Location location : createdLocations) {
            this.world.addLocation(location);
		}
		
		this.createWorldMap();
		this.populateActionsForLocations();
	}
	
	private ArrayList<Location> createLocations() {
		Location forest = new Location("Forest", "A dark and mysterious forest.", HazardType.NONE, false, new ArrayList<>());
		Location cave = new Location("Cave", "A dark and damp cave.", HazardType.PIT, false, new ArrayList<>());
		Location oldChurch = new Location("Old Church", "An old church.", HazardType.NONE, true, new ArrayList<>());
		
		ArrayList<Location> newLocations = new ArrayList<>();
		
		newLocations.add(this.world.getStartLocation());
		newLocations.add(forest);
		newLocations.add(cave);
		newLocations.add(oldChurch);
		
		return newLocations;
	}
	
	private void createWorldMap() {
		this.world.connectLocations(this.world.getStartLocation(), Direction.NORTH, this.world.getLocationByName("Forest"));
		this.world.connectLocations(this.world.getLocationByName("Forest"), Direction.EAST, this.world.getLocationByName("Cave"));
		this.world.connectLocations(this.world.getLocationByName("Cave"), Direction.NORTH, this.world.getLocationByName("Old Church"));
	}
	
	private void populateActionsForLocations() {
		for (Location location : this.world.getAllLocations().values()) {
			List<Action> actions = new ArrayList<>();
		
			for (Direction direction : location.getConnections().keySet()) {
				String actionDescription = direction.toString();
				actions.add(new Action(GlobalEnums.ActionType.MOVE.toString(), actionDescription, GlobalEnums.ActionType.MOVE));
			}
			
			location.setActions(actions);
		}
	}
	
	/**
	 * Gets the world object
	 * 
	 * @return	the world object
	 */
	public World getWorld() {
		return this.world;
	}

}
