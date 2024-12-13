package edu.westga.cs3211.text_adventure_game.tests.location;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Action;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.ActionType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.LocationName;
import edu.westga.cs3211.text_adventure_game.model.Location;

/**
 * Tests the removeTakeActions method in the Location class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class RemoveTakeActionsTest {
	private Location location = new Location(LocationName.BALLROOM, "This is a test location", HazardType.NONE, false, new ArrayList<>(), Item.DIARY);
	
	/**
	 * Tests the removeTakeActions method
	 */
	@Test
	public void testRemoveTakeActions() {
		Action action = new Action("Test Action", "Test Action Description", ActionType.TAKE);
		Action action2 = new Action("Test Action2", "Test Action Description2", ActionType.EXAMINE);
		this.location.addAction(action);
		this.location.addAction(action2);
		this.location.removeTakeActions();
		assertAll(() -> {
			assertEquals(action2, this.location.getActions().get(0));
			assertEquals(1, this.location.getActions().size());
		});
	}
}
