package edu.westga.cs3211.text_adventure_game.tests.location;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Action;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.LocationName;
import edu.westga.cs3211.text_adventure_game.model.Location;

/**
 * Tests the setStartingItem method in the Location class
 * 
 * @author James bridges
 * @version Fall 2024
 */
public class SetStartingItemTest {
	
	/**
	 * Tests setting a starting item
	 */
	@Test
	public void testSetStartingItem() {
		Location testLocation = new Location(LocationName.LIBRARY, "A library", HazardType.NONE, false, new ArrayList<Action>(), Item.NONE);
		testLocation.setStartingItem(Item.DIARY);
		assertEquals(Item.DIARY, testLocation.getStartingItem());
	}

}
