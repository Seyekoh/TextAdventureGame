package edu.westga.cs3211.text_adventure_game.tests.location;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.Location;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;

/**
 * Tests the constructor of the Location class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class ConstructorTest {

	/**
	 * Tests the constructor of the Location class for a valid location
	 */
	@Test
	public void testConstructorForValidLocation() {
		Location testLocation = new Location(GlobalEnums.LocationName.ATTIC, "This is a test location", HazardType.NONE, false, new ArrayList<>(), Item.NONE);

	    assertAll(
	        () -> assertEquals(GlobalEnums.LocationName.ATTIC, testLocation.getName()),
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
			new Location(null, "This is a test location", HazardType.NONE, false, new ArrayList<>(), Item.NONE);
		});
		
		assertTrue(exception.getMessage().equalsIgnoreCase("Name cannot be null"));
	}
	
	/**
	 * Tests the constructor of the Location class with a null description
	 */
	@Test
	public void testConstructorWithNullDescription() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Location(GlobalEnums.LocationName.ATTIC, null, HazardType.NONE, false, new ArrayList<>(), Item.NONE);
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Description cannot be null"));
	}
	
	/**
	 * Tests the constructor of the Location class with a blank description
	 */
	@Test
	public void testConstructorWithBlankDescription() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Location(GlobalEnums.LocationName.ATTIC, "", HazardType.NONE, false, new ArrayList<>(), Item.NONE);
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Description cannot be blank"));
	}
	
	/**
	 * Tests the constructor of the Location class with a null hazard type
	 */
	@Test
	public void testConstructorWithNullHazardType() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Location(GlobalEnums.LocationName.ATTIC, "This is a test location", null, false, new ArrayList<>(), Item.NONE);
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Hazard Type cannot be null"));
	}
	
	/**
	 * Tests the constructor of the Location class with a null actions list
	 */
	@Test
	public void testConstructorWithNullActions() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Location(GlobalEnums.LocationName.ATTIC, "This is a test location", HazardType.NONE, false, null, Item.NONE);
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Actions cannot be null"));
	}
	
	/**
	 * Tests the constructor of the Location class with a null item
	 */
	@Test
	public void testConstructorWithNullItem() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Location(GlobalEnums.LocationName.ATTIC, "This is a test location", HazardType.NONE, false,
					new ArrayList<>(), null);
		});
		
		assertTrue(exception.getMessage().equalsIgnoreCase("Item cannot be null"));
	}
}
