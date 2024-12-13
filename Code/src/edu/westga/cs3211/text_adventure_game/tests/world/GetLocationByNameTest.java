package edu.westga.cs3211.text_adventure_game.tests.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.Location;
import edu.westga.cs3211.text_adventure_game.model.World;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;

/**
 * Tests the World class's getLocationByName method
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GetLocationByNameTest {
	
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
        this.location1 = new Location(GlobalEnums.LocationName.ENTRANCEHALL, "Entrance hall", HazardType.NONE, false, new ArrayList<>(), Item.NONE);
        this.location2 = new Location(GlobalEnums.LocationName.BALLROOM, "A ballroom", HazardType.DANCINGSHADOWS, false, new ArrayList<>(), Item.NONE);
        this.location3 = new Location(GlobalEnums.LocationName.KITCHEN, "A kitchen", HazardType.NONE, false, new ArrayList<>(), Item.NONE);
        this.location4 = new Location(GlobalEnums.LocationName.EXIT, "The exit", HazardType.NONE, true, new ArrayList<>(), Item.NONE);
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

}
