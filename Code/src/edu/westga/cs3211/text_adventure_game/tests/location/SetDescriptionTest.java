package edu.westga.cs3211.text_adventure_game.tests.location;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.LocationName;
import edu.westga.cs3211.text_adventure_game.model.Location;

/**
 * Tests the setDescription method in the Location class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class SetDescriptionTest {
	private Location location = new Location(LocationName.BALLROOM, "This is a test location", HazardType.NONE, false, new ArrayList<>(), Item.NONE);

	/**
	 * Tests the setDescription method
	 */
	@Test
	public void testSetDescription() {
		this.location.setDescription("This is a new description");
		assertEquals("This is a new description", this.location.getDescription());
	}
}
