package edu.westga.cs3211.text_adventure_game.tests.world;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.Location;
import edu.westga.cs3211.text_adventure_game.model.World;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;

/**
 * Tests the World class's opposite direction method
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class OppositeDirectionTest {
	
	private World world;
	private Location location1;
	private Location location2;
	private Location location3;
	private Location location4;
	private Location location5;
	private Location location6;
	
	/**
	 * Sets up the instance variables for each test
	 */
	@BeforeEach
	public void setUp() {
        this.world = new World();
        this.location1 = new Location(GlobalEnums.LocationName.ENTRANCEHALL, "Entrance hall", HazardType.NONE, false, new ArrayList<>(), Item.NONE);
        this.location2 = new Location(GlobalEnums.LocationName.BALLROOM, "A ballroom", HazardType.DANCINGSHADOWS, false, new ArrayList<>(), Item.NONE);
        this.location3 = new Location(GlobalEnums.LocationName.KITCHEN, "A kitchen", HazardType.NONE, false, new ArrayList<>(), Item.NONE);
        this.location4 = new Location(GlobalEnums.LocationName.EXIT, "The exit", HazardType.NONE, true, new ArrayList<>(), Item.NONE);
        this.location5 = new Location(GlobalEnums.LocationName.ATTIC, "The attic", HazardType.NONE, false, new ArrayList<>(), Item.NONE);
        this.location6 = new Location(GlobalEnums.LocationName.BASEMENT, "The cellar", HazardType.NONE, false, new ArrayList<>(), Item.NONE);
	}

	/**
	 * Tests the getOppositeDirection method via the connect locations method
	 */
	@Test
	public void testGetOppositeDirectionValid() {
		this.world.addLocation(this.location1);
		this.world.addLocation(this.location2);
		this.world.addLocation(this.location3);
		this.world.addLocation(this.location4);
		this.world.addLocation(this.location5);
		this.world.addLocation(this.location6);
		
		assertAll(() -> {
			this.world.connectLocations(this.location1, Direction.NORTH, this.location2);
			this.world.connectLocations(this.location2, Direction.EAST, this.location3);
			this.world.connectLocations(this.location3, Direction.SOUTH, this.location4);
			this.world.connectLocations(this.location4, Direction.WEST, this.location1);
			this.world.connectLocations(this.location5, Direction.DOWN, this.location6);
			this.world.connectLocations(this.location3, Direction.UP, this.location6);
		});
	}
}
