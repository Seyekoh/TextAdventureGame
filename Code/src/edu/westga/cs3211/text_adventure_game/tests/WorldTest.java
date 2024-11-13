package edu.westga.cs3211.text_adventure_game.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.Location;
import edu.westga.cs3211.text_adventure_game.World;

/**
 * Tests the World class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class WorldTest {
	
	private World world;
	private Location location1;
	private Location location2;
	private Location location3;
	private Location location4;
	
	/**
	 * Tests the constructor for the World class
	 */
	@Test
	public void testWorldConstructorValid() {
		this.world = new World();
		assertEquals(0, this.world.getAllLocations().size());
    }
	
	/**
	 * Sets up the instance variables for each test
	 */
	@BeforeEach
	public void setUp() {
        this.world = new World();
        this.location1 = new Location("Cave", "A dark cave", HazardType.NONE, false, new ArrayList<>());
        this.location2 = new Location("Forest", "A dense forest", HazardType.NONE, false, new ArrayList<>());
        this.location3 = new Location("Mountain", "A tall mountain", HazardType.NONE, false, new ArrayList<>());
        this.location4 = new Location("Church", "A small church", HazardType.NONE, true, new ArrayList<>());
	}
	
	/**
	 * Tests the addLocation method with a Location that is not a goal
	 */
	@Test
	public void testAddLocationNotGoal() {
		this.world.addLocation(this.location1);
		assertFalse(this.world.checkIfLocationIsGoal(this.location1));
	}
	
	/**
	 * Tests the addLocation method with a Location that is a goal	
	 */
	@Test
	public void testAddLocationIsGoal() {
		this.world.addLocation(this.location4);
		assertTrue(this.world.checkIfLocationIsGoal(this.location4));
	}
	
	/**
	 * Tests the setGoalLocation method with a goal location already set
	 */
	@Test
	public void testSetGoalLocationGoalAlreadySet() {
		this.world.addLocation(this.location4);
		this.world.addLocation(this.location1);
		this.world.addLocation(this.location2);
		this.world.addLocation(this.location3);
		
		Location newLocation = new Location("Castle", "A large castle", HazardType.NONE, true, new ArrayList<>());
		
		assertThrows(IllegalArgumentException.class, () -> {
			this.world.setGoalLocation(newLocation);
		});
	}
	
	/**
	 * Tests the getLocationByName method
	 */
	@Test
	public void testGetLocationByName() {
		this.world.addLocation(this.location1);

		assertEquals(this.location1, this.world.getLocationByName("Cave"));
	}
	
	/**
	 * Tests the getLocationByName method with a location that does not exist
	 */
	@Test
	public void testGetLocationByNameLocationDoesNotExist() {
		this.world.addLocation(this.location1);

		assertEquals(null, this.world.getLocationByName("Forest"));
	}
	
	/**
	 * Tests the checkIfLocationIsHazard method with a location that is not a hazard
	 */
	@Test
	public void testCheckIfLocationIsHazardNotHazard() {
		this.world.addLocation(this.location1);

		assertFalse(this.world.checkIfLocationIsHazard(this.location1));
	}
	
	/**
	 * Tests the connectLocations method
	 */
	@Test
	public void testConnectLocationsValid() {
		this.world.addLocation(this.location1);
		this.world.addLocation(this.location2);

		this.world.connectLocations(this.location1, Direction.NORTH, this.location2);

		assertAll(() -> {
			assertEquals(this.location2, this.location1.getConnection(Direction.NORTH));
			assertEquals(this.location1, this.location2.getConnection(Direction.SOUTH));
		});
	}
	
	/**
	 * Tests the connectLocations method with a location already connected
	 * to fromLocation in the same direction
	 */
	@Test
	public void testConnectLocationsAlreadyConnectedFromLocation() {
		this.world.addLocation(this.location1);
		this.world.addLocation(this.location2);

		this.world.connectLocations(this.location1, Direction.NORTH, this.location2);

		assertThrows(IllegalArgumentException.class, () -> {
			this.world.connectLocations(this.location1, Direction.NORTH, this.location2);
		});
	}
	
	/**
	 * Tests the connectLocations method with a location already connected to
	 * toLocation in the opposite direction
	 */
	@Test
	public void testConnectLocationsAlreadyConnectedToLocation() {
		this.world.addLocation(this.location1);
		this.world.addLocation(this.location2);

		this.world.connectLocations(this.location1, Direction.NORTH, this.location2);
		
		this.world.addLocation(this.location3);

		assertThrows(IllegalArgumentException.class, () -> {
			this.world.connectLocations(this.location3, Direction.NORTH, this.location2);
		});
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
		
		assertAll(() -> {
			this.world.connectLocations(this.location1, Direction.NORTH, this.location2);
			this.world.connectLocations(this.location2, Direction.EAST, this.location3);
			this.world.connectLocations(this.location3, Direction.SOUTH, this.location4);
			this.world.connectLocations(this.location4, Direction.WEST, this.location1);
		});
	}

}
