package edu.westga.cs3211.text_adventure_game.tests.location;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.Location;

/**
 * Tests the Searched method in the Location class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class SearchedTest {
	private Location testLocation = new Location(GlobalEnums.LocationName.BALLROOM, "This is a test location", HazardType.NONE, false, new ArrayList<>(), Item.NONE);

	/**
	 * Tests the isSearched method for a Location that has not been searched
	 */
	@Test
	public void testIsSearchedWhenNotSearched() {
		assertFalse(this.testLocation.isSearched());
	}
	
	/**
	 * Tests the setSearched method
	 */
	@Test
	public void testSetSearched() {
		this.testLocation.setSearched(true);
		assertTrue(this.testLocation.isSearched());
	}
}
