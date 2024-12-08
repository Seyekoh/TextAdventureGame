package edu.westga.cs3211.text_adventure_game.tests.world;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.Location;
import edu.westga.cs3211.text_adventure_game.model.World;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.HazardData;

/**
 * Tests the World class's location methods
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class LocationTest {
	
	private World world;
	private Location location1;
	private Location location2;
	private Location location3;
	private Location location4;
	
	/**
	 * Sets up the instance variables for each test
	 */
	@BeforeEach
	public void setUp() {
        this.world = new World();
        this.location1 = new Location(GlobalEnums.LocationName.ENTRANCEHALL, "Entrance hall", HazardType.NONE, false, new ArrayList<>());
        this.location2 = new Location(GlobalEnums.LocationName.BALLROOM, "A ballroom", HazardType.DANCINGSHADOWS, false, new ArrayList<>());
        this.location3 = new Location(GlobalEnums.LocationName.KITCHEN, "A kitchen", HazardType.NONE, false, new ArrayList<>());
        this.location4 = new Location(GlobalEnums.LocationName.EXIT, "The exit", HazardType.NONE, true, new ArrayList<>());
	}
	
	/**
	 * Tests the getStartLocation method
	 */
	@Test
	public void testGetStartLocation() {
		this.world.setStartLocation(this.location1);
		assertEquals(GlobalEnums.LocationName.ENTRANCEHALL, this.world.getStartLocation().getName());
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
	 * Tests the getLocationByName method
	 */
	@Test
	public void testGetLocationByName() {
		this.world.addLocation(this.location1);

		assertEquals(this.location1, this.world.getLocationByName(GlobalEnums.LocationName.ENTRANCEHALL));
	}
	
	/**
	 * Tests the getLocationByName method with a location that does not exist
	 */
	@Test
	public void testGetLocationByNameLocationDoesNotExist() {
		this.world.addLocation(this.location1);

	    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	        this.world.getLocationByName(null);
	    });

	    assertEquals("Location not found: null", exception.getMessage());
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
	 * Tests the checkIfLocationIsHazard method with a location that is a hazard
	 */
	@Test
	public void testCheckIfLocationIsHazardIsHazard() {
		this.world.addLocation(this.location2);

		assertTrue(this.world.checkIfLocationIsHazard(this.location2));
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
	 * Tests the getHazardDataForLocation method
	 */
	@Test
	public void testGetHazardDataForLocation() {
		this.world.addLocation(this.location2);

		assertAll(() -> {
			assertEquals(HazardType.DANCINGSHADOWS, this.location2.getHazardType());
			assertEquals("18 The shadows attack you!", this.world.getHazardDataForLocation(this.location2).toString());
		});
	}
	
	/**
	 * Tests the setHazardTypeForLocation method
	 */
	@Test
	public void testSetHazardTypeForLocation() {
		this.world.addLocation(this.location1);
		HazardData newHazardData = this.world.getHazardDataForLocation(this.location2);

		this.world.setHazardTypeForLocation(this.location1, newHazardData);

		assertEquals(HazardType.DANCINGSHADOWS, this.location1.getHazardType());
	}
	
}
