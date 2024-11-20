package edu.westga.cs3211.text_adventure_game.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.Action;
import edu.westga.cs3211.text_adventure_game.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.Location;

/**
 * The test class for the Location class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class LocationTest {
	
	/**
	 * Tests the constructor of the Location class for a valid location
	 */
	@Test
	public void testConstructorForValidLocation() {
		Location testLocation = new Location("Test Location", "This is a test location", HazardType.NONE, false, new ArrayList<>());

	    assertAll(
	        () -> assertEquals("Test Location", testLocation.getName()),
	        () -> assertEquals("This is a test location", testLocation.getDescription()),
	        () -> assertEquals(HazardType.NONE, testLocation.getHazardType()),
	        () -> assertEquals(false, testLocation.checkIfLocationIsGoal()),
	        () -> assertEquals(0, testLocation.getActions().size())
	    );
	}
	
	/**
	 * Tests the constructor of the Location class with a null name
	 */
	@Test
	public void testConstructorWithNullName() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Location(null, "This is a test location", HazardType.NONE, false, new ArrayList<>());
		});
		
		assertTrue(exception.getMessage().equalsIgnoreCase("Name cannot be null"));
	}
	
	/**
	 * Tests the constructor of the Location class with a blank name
	 */
	@Test
	public void testConstructorWithBlankName() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Location("", "This is a test location", HazardType.NONE, false, new ArrayList<>());
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Name cannot be blank"));
	}
	
	/**
	 * Tests the getter for the Location name
	 */
	@Test
	public void testGetName() {
		Location testLocation = new Location("Test Location", "This is a test location", HazardType.NONE, false,
				new ArrayList<>());

		assertEquals("Test Location", testLocation.getName());
	}
	
	/**
	 * Tests the constructor of the Location class with a null description
	 */
	@Test
	public void testConstructorWithNullDescription() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Location("Test Location", null, HazardType.NONE, false, new ArrayList<>());
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Description cannot be null"));
	}
	
	/**
	 * Tests the constructor of the Location class with a blank description
	 */
	@Test
	public void testConstructorWithBlankDescription() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Location("Test Location", "", HazardType.NONE, false, new ArrayList<>());
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Description cannot be blank"));
	}
	
	/**
	 * Tests the getter for the Location description
	 */
	@Test
	public void testGetDescription() {
		Location testLocation = new Location("Test Location", "This is a test location", HazardType.NONE, false,
				new ArrayList<>());

		assertEquals("This is a test location", testLocation.getDescription());
	}
	
	/**
	 * Tests the constructor of the Location class with a null hazard type
	 */
	@Test
	public void testConstructorWithNullHazardType() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Location("Test Location", "This is a test location", null, false, new ArrayList<>());
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Hazard Type cannot be null"));
	}
	
	/**
	 * Tests the getter for the Location hazard type
	 */
	@Test
	public void testGetHazardType() {
		Location testLocation = new Location("Test Location", "This is a test location", HazardType.NONE, false,
				new ArrayList<>());

		assertEquals(HazardType.NONE, testLocation.getHazardType());
	}
	
	/**
	 * Tests the constructor of the Location class with a null actions list
	 */
	@Test
	public void testConstructorWithNullActions() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Location("Test Location", "This is a test location", HazardType.NONE, false, null);
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Actions cannot be null"));
	}
	
	/**
	 * Tests the setActions method for a location with no actions
	 */
	@Test
	public void testSetActionsForLocationWithNoActions() {
		Location testLocation = new Location("Test Location", "This is a test location", HazardType.NONE, false,
				new ArrayList<>());
		ArrayList<Action> newActions = new ArrayList<>();
		testLocation.setActions(newActions);
		
		assertEquals(newActions, testLocation.getActions());
	}
	
	/**
	 * Tests the setIsGoal method for a location that is a goal
	 */
	@Test
	public void testSetIsGoalForLocation() {
		Location testLocation = new Location("Test Location", "This is a test location", HazardType.NONE, false,
				new ArrayList<>());
		testLocation.setIsGoal(true);

		assertEquals(true, testLocation.checkIfLocationIsGoal());
	}
	
	/**
	 * Tests the addConnection method for a Location with no connections
	 */
	@Test
	public void testAddConnectionForLocationWithNoConnections() {
		Location testLocation = new Location("Test Location", "This is a test location", HazardType.NONE, false,
				new ArrayList<>());
		Location testLocation2 = new Location("Test Location2", "This is a test location2", HazardType.NONE, false,
				new ArrayList<>());
		testLocation.addConnection(Direction.NORTH, testLocation2);

		assertEquals(testLocation2, testLocation.getConnection(Direction.NORTH));
	}
	
	/**
	 * Tests the addConnection method for a Location with an 
	 * existing connection not in same Direction
	 */
	@Test
	public void testAddConnectionForLocationWithExistingConnection() {
		Location testLocation = new Location("Test Location", "This is a test location", HazardType.NONE, false,
				new ArrayList<>());
		Location testLocation2 = new Location("Test Location2", "This is a test location2", HazardType.NONE, false,
				new ArrayList<>());
		Location testLocation3 = new Location("Test Location3", "This is a test location3", HazardType.NONE, false,
				new ArrayList<>());
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
		Location testLocation = new Location("Test Location", "This is a test location", HazardType.NONE, false,
				new ArrayList<>());
		Location testLocation2 = new Location("Test Location2", "This is a test location2", HazardType.NONE, false,
				new ArrayList<>());
		Location testLocation3 = new Location("Test Location3", "This is a test location3", HazardType.NONE, false,
				new ArrayList<>());
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
		Location testLocation = new Location("Test Location", "This is a test location", HazardType.NONE, false,
				new ArrayList<>());

		assertEquals(0, testLocation.getConnections().size());
	}
	
	/**
	 * Tests the getConnections method for a Location with 1 connection
	 */
	@Test
	public void testGetConnectionsForLocationWithOneConnection() {
		Location testLocation = new Location("Test Location", "This is a test location", HazardType.NONE, false,
				new ArrayList<>());
		Location testLocation2 = new Location("Test Location2", "This is a test location2", HazardType.NONE, false,
				new ArrayList<>());
		testLocation.addConnection(Direction.NORTH, testLocation2);

		assertAll(() -> assertEquals(1, testLocation.getConnections().size()),
				() -> assertEquals(testLocation2, testLocation.getConnection(Direction.NORTH)),
				() -> assertEquals(testLocation, testLocation2.getConnection(Direction.SOUTH)));
	}
	
	/**
	 * Tests the getConnections method for a Location with 4 connections
	 */
	@Test
	public void testGetConnectionsForLocationWithFourConnections() {
		Location testLocation = new Location("Test Location", "This is a test location", HazardType.NONE, false,
				new ArrayList<>());
		Location testLocation2 = new Location("Test Location2", "This is a test location2", HazardType.NONE, false,
				new ArrayList<>());
		Location testLocation3 = new Location("Test Location3", "This is a test location3", HazardType.NONE, false,
				new ArrayList<>());
		Location testLocation4 = new Location("Test Location4", "This is a test location4", HazardType.NONE, false,
				new ArrayList<>());
		Location testLocation5 = new Location("Test Location5", "This is a test location5", HazardType.NONE, false,
				new ArrayList<>());
		testLocation.addConnection(Direction.NORTH, testLocation2);
		testLocation.addConnection(Direction.EAST, testLocation3);
		testLocation.addConnection(Direction.SOUTH, testLocation4);
		testLocation.addConnection(Direction.WEST, testLocation5);

		assertEquals(4, testLocation.getConnections().size());
	}
	
	/**
	 * Tests the toString method for a Location
	 */
	@Test
	public void testToString() {
		Location testLocation = new Location("Test Location", "This is a test location", HazardType.NONE, false,
				new ArrayList<>());

		assertEquals("Test Location: This is a test location", testLocation.toString());
	}
	
}
