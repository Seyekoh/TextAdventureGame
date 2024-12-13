package edu.westga.cs3211.text_adventure_game.tests.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Action;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.ActionType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.LocationName;
import edu.westga.cs3211.text_adventure_game.model.Location;

/**
 * Tests the addAction method in the Location class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class AddActionTest {
	private Location location = new Location(LocationName.BALLROOM, "This is a test location", HazardType.NONE, false, new ArrayList<>(), Item.DIARY);
	
	/**
	 * Tests the addAction method
	 */
	@Test
	public void testAddAction() {
		Action action = new Action("Test Action", "Test Action Description", ActionType.EXAMINE);
		this.location.addAction(action);
		assertEquals(action, this.location.getActions().get(0));
	}
	
	/**
	 * Tests the addAction method with null
	 */
	@Test
	public void testAddActionWithNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			this.location.addAction(null);
		});
	}
}
