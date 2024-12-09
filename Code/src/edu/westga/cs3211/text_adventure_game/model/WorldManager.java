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
		this.createLocations();
		for (Location location : this.world.getAllLocations().values()) {
            this.world.addLocation(location);
		}
		
		this.createWorldMap();
		this.populateActionsForLocations();
	}
	
	private void createLocations() {
		ArrayList<Location> newLocations = this.locationReader.importLocations();
		for (Location location : newLocations) {
			System.out.println("Adding location: " + location.getName());
			if (location.getName() == GlobalEnums.LocationName.ENTRANCEHALL) {
                this.world.setStartLocation(location);
			}
			this.world.addLocation(location);
		}
		System.out.println("All locations added to the world: " + this.world.getAllLocations().keySet().toString());
	}
	
	private void createWorldMap() {
		this.world.connectLocations(this.world.getStartLocation(), Direction.NORTH, this.world.getLocationByName(GlobalEnums.LocationName.LIBRARY));
		this.world.connectLocations(this.world.getLocationByName(GlobalEnums.LocationName.LIBRARY), Direction.EAST, this.world.getLocationByName(GlobalEnums.LocationName.KITCHEN));
		this.world.connectLocations(this.world.getLocationByName(GlobalEnums.LocationName.LIBRARY), Direction.UP, this.world.getLocationByName(GlobalEnums.LocationName.ATTIC));
		this.world.connectLocations(this.world.getLocationByName(GlobalEnums.LocationName.KITCHEN), Direction.NORTH, this.world.getLocationByName(GlobalEnums.LocationName.BALLROOM));
		this.world.connectLocations(this.world.getLocationByName(GlobalEnums.LocationName.KITCHEN), Direction.DOWN, this.world.getLocationByName(GlobalEnums.LocationName.BASEMENT));
		this.world.connectLocations(this.world.getLocationByName(GlobalEnums.LocationName.BALLROOM), Direction.EAST, this.world.getLocationByName(GlobalEnums.LocationName.EXIT));
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
	
	/**
	 * Gets the hazard description for a location
	 * 
	 * @param location	the location to get the hazard description for
	 * @return			the hazard description for the location
     */
	public String getHazardDescription(Location location) {
		return this.world.getHazardDataForLocation(location).toString();
	}

}
