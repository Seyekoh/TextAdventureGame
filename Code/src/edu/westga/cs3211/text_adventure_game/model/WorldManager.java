package edu.westga.cs3211.text_adventure_game.model;

import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;

/**
 * The world manager
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class WorldManager {
	private static final String LOCATION_PATH = "src/locations.csv";
	
	private World world;
	private LocationReader locationReader;

	/**
	 * The constructor for the WorldManager
	 */
	public WorldManager() {
		this.world = new World();
		this.locationReader = new LocationReader(LOCATION_PATH);
		
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
		ArrayList<Location> newLocations = this.locationReader.importLocations();
		newLocations.add(this.world.getStartLocation());
		
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
