package edu.westga.cs3211.text_adventure_game.tests.worldmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Action;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.Location;
import edu.westga.cs3211.text_adventure_game.model.WorldManager;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;

/**
 * Tests the getHazardDescription method of the WorldManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GetHazardDescriptionTest {

	/**
	 * Tests the getHazardDescription method
	 */
	@Test
	public void testGetHazardDescription() {
		WorldManager manager = new WorldManager();
		Location location = new Location(GlobalEnums.LocationName.BASEMENT, "A dark and damp basement", GlobalEnums.HazardType.CREEPYDOLL, false, new ArrayList<Action>(), Item.NONE);
		
		assertEquals(manager.getHazardDescription(location), "3 A creepy doll attacks you!");
	}
}
