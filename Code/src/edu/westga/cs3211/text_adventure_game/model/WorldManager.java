package edu.westga.cs3211.text_adventure_game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.LocationName;

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
		this.placeNPCAtStartingLocation();
	}

	private void createLocations() {
		ArrayList<Location> newLocations = this.locationReader.importLocations();
		for (Location location : newLocations) {

			if (location.getName() == GlobalEnums.LocationName.ENTRANCEHALL) {
				this.world.setStartLocation(location);
			}
			
			if (location.getName() == GlobalEnums.LocationName.EXIT) {
                this.world.setGoalLocation(location);
			}
			
			this.world.addLocation(location);
		}
	}

	private void createWorldMap() {
		ArrayList<Location> remainingLocations = new ArrayList<>(this.world.getAllLocations().values());
		ArrayList<Location> fixedLocations = new ArrayList<>(this.connectFixedLocations());

		this.removeStartLocationFromRemainingLocations(remainingLocations);
		this.removeExitLocationFromRemainingLocations(remainingLocations);
		this.keepMainFloorLocationsInRemainingLocations(remainingLocations);

		this.connectRandomLocations(remainingLocations, fixedLocations);
	}

	private ArrayList<Location> connectFixedLocations() {
		ArrayList<Location> fixedLocations = new ArrayList<>();

		this.world.connectLocations(this.world.getLocationByName(GlobalEnums.LocationName.LIBRARY), Direction.UP,
				this.world.getLocationByName(GlobalEnums.LocationName.ATTIC));
		fixedLocations.add(this.world.getLocationByName(GlobalEnums.LocationName.LIBRARY));
		fixedLocations.add(this.world.getLocationByName(GlobalEnums.LocationName.ATTIC));

		this.world.connectLocations(this.world.getLocationByName(GlobalEnums.LocationName.KITCHEN), Direction.DOWN,
				this.world.getLocationByName(GlobalEnums.LocationName.BASEMENT));
		fixedLocations.add(this.world.getLocationByName(GlobalEnums.LocationName.KITCHEN));
		fixedLocations.add(this.world.getLocationByName(GlobalEnums.LocationName.BASEMENT));

		return fixedLocations;
	}

	private void removeStartLocationFromRemainingLocations(ArrayList<Location> remainingLocations) {
		remainingLocations.remove(this.world.getStartLocation());
	}

	private void removeExitLocationFromRemainingLocations(ArrayList<Location> remainingLocations) {
		remainingLocations.remove(this.world.getLocationByName(GlobalEnums.LocationName.EXIT));
	}

	private void keepMainFloorLocationsInRemainingLocations(ArrayList<Location> remainingLocations) {
		remainingLocations.remove(this.world.getLocationByName(GlobalEnums.LocationName.ATTIC));
		remainingLocations.remove(this.world.getLocationByName(GlobalEnums.LocationName.BASEMENT));
	}

	private void connectRandomLocations(ArrayList<Location> remainingLocations, ArrayList<Location> fixedLocations) {
		Collections.shuffle(remainingLocations);

		Location randomLocation1 = remainingLocations.get(0);
		Location randomLocation2 = remainingLocations.get(1);
		Location randomLocation3 = remainingLocations.get(2);

		this.world.connectLocations(this.world.getStartLocation(), Direction.NORTH, randomLocation2);
		this.world.connectLocations(randomLocation2, Direction.NORTH, randomLocation1);
		this.world.connectLocations(randomLocation2, Direction.WEST, randomLocation3);
	}

	private void populateActionsForLocations() {
		for (Location location : this.world.getAllLocations().values()) {
			List<Action> actions = new ArrayList<>();

			for (Direction direction : location.getConnections().keySet()) {
				String actionDescription = direction.toString();
				actions.add(new Action(GlobalEnums.ActionType.MOVE.toString(), actionDescription,
						GlobalEnums.ActionType.MOVE));
			}

			actions.add(new Action(GlobalEnums.ActionType.SEARCH.toString(), "Search the location",
					GlobalEnums.ActionType.SEARCH));

			actions.add(new Action(GlobalEnums.ActionType.USE.toString(), "Use an item", GlobalEnums.ActionType.USE));
			actions.add(
					new Action(GlobalEnums.ActionType.DROP.toString(), "Drop an item", GlobalEnums.ActionType.DROP));
			actions.add(new Action(GlobalEnums.ActionType.EXAMINE.toString(), "Examine an item",
					GlobalEnums.ActionType.EXAMINE));

			location.setActions(actions);
		}
	}

	private void placeNPCAtStartingLocation() {
		NPC npc = new NPC("A ghostly figure is sitting wistfully by the window.");
		this.world.addNPC(npc);
		this.world.moveNPCToLocation(npc, this.world.getLocationByName(LocationName.ATTIC));
	}

	/**
	 * Gets the world object
	 * 
	 * @return the world object
	 */
	public World getWorld() {
		return this.world;
	}

	/**
	 * Gets the hazard description for a location
	 * 
	 * @param location the location to get the hazard description for
	 * @return the hazard description for the location
	 */
	public String getHazardDescription(Location location) {
		return this.world.getHazardDataForLocation(location).toString();
	}

}
