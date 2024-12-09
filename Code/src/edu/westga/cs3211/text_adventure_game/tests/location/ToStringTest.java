package edu.westga.cs3211.text_adventure_game.tests.location;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Location;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;

/**
 * Tests the toString method of the Location class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class ToStringTest {

	/**
	 * Tests the toString method for a Location
	 */
	@Test
	public void testToString() {
		Location testLocation = new Location(GlobalEnums.LocationName.ATTIC, "This is a test location", HazardType.NONE, false,
				new ArrayList<>(), Item.NONE);

		assertEquals("ATTIC: This is a test location", testLocation.toString());
	}
}
