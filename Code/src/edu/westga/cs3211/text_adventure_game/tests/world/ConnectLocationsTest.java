package edu.westga.cs3211.text_adventure_game.tests.world;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
 *  Tests the World class's connectLocations method
 *  
 *  @author James Bridges
 *  @version Fall 2024
 */
public class ConnectLocationsTest {
	
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

}
