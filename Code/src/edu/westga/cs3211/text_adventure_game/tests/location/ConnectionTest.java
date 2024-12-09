package edu.westga.cs3211.text_adventure_game.tests.location;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Location;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;

/**
 * Tests connections in the Location class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class ConnectionTest {

	/**
	 * Tests the addConnection method for a Location with no connections
	 */
	@Test
	public void testAddConnectionForLocationWithNoConnections() {
		Location testLocation = new Location(GlobalEnums.LocationName.LIBRARY, "This is a test location", HazardType.NONE, false,
				new ArrayList<>(), Item.NONE);
		Location testLocation2 = new Location(GlobalEnums.LocationName.ATTIC, "This is a test location2", HazardType.NONE, false,
				new ArrayList<>(), Item.NONE);
		testLocation.addConnection(Direction.DOWN, testLocation2);

		assertEquals(testLocation2, testLocation.getConnection(Direction.DOWN));
	}
	
	/**
	 * Tests the addConnection method for a Location with an 
	 * existing connection not in same Direction
	 */
	@Test
	public void testAddConnectionForLocationWithExistingConnection() {
		Location testLocation = new Location(GlobalEnums.LocationName.BALLROOM, "This is a test location", HazardType.NONE, false,
				new ArrayList<>(), Item.NONE);
		Location testLocation2 = new Location(GlobalEnums.LocationName.ENTRANCEHALL, "This is a test location2", HazardType.NONE, false,
				new ArrayList<>(), Item.NONE);
		Location testLocation3 = new Location(GlobalEnums.LocationName.KITCHEN, "This is a test location3", HazardType.NONE, false,
				new ArrayList<>(), Item.DIARY);
		testLocation.addConnection(Direction.NORTH, testLocation2);
		testLocation.addConnection(Direction.EAST, testLocation3);

		assertEquals(testLocation3, testLocation.getConnection(Direction.EAST));
	}
	
	/**
	 * Tests the addConnection method for a Location with an existing connection in
	 * the same Direction
	 */
	@Test
	public void testAddConnectionForLocationWithExistingConnectionInSameDirection() {
		Location testLocation = new Location(GlobalEnums.LocationName.BALLROOM, "This is a test location", HazardType.NONE, false,
				new ArrayList<>(), Item.NONE);
		Location testLocation2 = new Location(GlobalEnums.LocationName.ENTRANCEHALL, "This is a test location2", HazardType.NONE, false,
				new ArrayList<>(), Item.MUSICBOX);
		Location testLocation3 = new Location(GlobalEnums.LocationName.KITCHEN, "This is a test location3", HazardType.NONE, false,
				new ArrayList<>(), Item.DRESS);
		testLocation.addConnection(Direction.NORTH, testLocation2);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			testLocation.addConnection(Direction.NORTH, testLocation3);
		});

		assertEquals("Connection already exists in that direction", exception.getMessage());
	}
	
	/**
	 * Tests the getConnections method for a Location with no connections
	 */
	@Test
	public void testGetConnectionsForLocationWithNoConnections() {
		Location testLocation = new Location(GlobalEnums.LocationName.BASEMENT, "This is a test location", HazardType.NONE, false,
				new ArrayList<>(), Item.NONE);

		assertEquals(0, testLocation.getConnections().size());
	}
	
	/**
	 * Tests the getConnections method for a Location with 1 connection
	 */
	@Test
	public void testGetConnectionsForLocationWithOneConnection() {
		Location testLocation = new Location(GlobalEnums.LocationName.BASEMENT, "This is a test location", HazardType.NONE, false,
				new ArrayList<>(), Item.NONE);
		Location testLocation2 = new Location(GlobalEnums.LocationName.KITCHEN, "This is a test location2", HazardType.NONE, false,
				new ArrayList<>(), Item.NONE);
		testLocation.addConnection(Direction.UP, testLocation2);

		assertAll(() -> assertEquals(1, testLocation.getConnections().size()),
				() -> assertEquals(testLocation2, testLocation.getConnection(Direction.UP)),
				() -> assertEquals(testLocation, testLocation2.getConnection(Direction.DOWN)));
	}
	
	/**
	 * Tests the getConnections method for a Location with 4 connections
	 */
	@Test
	public void testGetConnectionsForLocationWithFourConnections() {
		Location testLocation = new Location(GlobalEnums.LocationName.BALLROOM, "This is a test location", HazardType.NONE, false,
				new ArrayList<>(), Item.RING);
		Location testLocation2 = new Location(GlobalEnums.LocationName.ENTRANCEHALL, "This is a test location2", HazardType.NONE, false,
				new ArrayList<>(), Item.DIARY);
		Location testLocation3 = new Location(GlobalEnums.LocationName.KITCHEN, "This is a test location3", HazardType.NONE, false,
				new ArrayList<>(), Item.DRESS);
		Location testLocation4 = new Location(GlobalEnums.LocationName.LIBRARY, "This is a test location4", HazardType.NONE, false,
				new ArrayList<>(), Item.POTION);
		Location testLocation5 = new Location(GlobalEnums.LocationName.EXIT, "This is a test location5", HazardType.NONE, false,
				new ArrayList<>(), Item.MUSICBOX);
		testLocation.addConnection(Direction.NORTH, testLocation2);
		testLocation.addConnection(Direction.EAST, testLocation3);
		testLocation.addConnection(Direction.SOUTH, testLocation4);
		testLocation.addConnection(Direction.WEST, testLocation5);

		assertEquals(4, testLocation.getConnections().size());
	}
}
