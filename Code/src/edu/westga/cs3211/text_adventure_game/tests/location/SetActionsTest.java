package edu.westga.cs3211.text_adventure_game.tests.location;

import static org.junit.jupiter.api.Assertions.assertAll;
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
 * Tests the setActions method in the Location class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class SetActionsTest {
	private Location location = new Location(LocationName.BALLROOM, "This is a test location", HazardType.NONE, false, new ArrayList<>(), Item.DIARY);
	private Action action1 = new Action("Test Action 1", "Test Action 1 Description", ActionType.EXAMINE);
	private Action action2 = new Action("Test Action 2", "Test Action 2 Description", ActionType.MOVE);
	
	/**
	 * Tests the setActions method
	 */
	@Test
	public void testSetActions() {
		ArrayList<Action> actions = new ArrayList<>();
		actions.add(this.action1);
		actions.add(this.action2);
		this.location.setActions(actions);
		
		assertAll(() -> {
            assertEquals(this.action1, this.location.getActions().get(0));
            assertEquals(this.action2, this.location.getActions().get(1));
		});
	}
	
	/**
	 * Tests the setActions method with null
	 */
	@Test
	public void testSetActionsWithNull() {
		assertThrows(IllegalArgumentException.class, () -> {
            this.location.setActions(null);
        });
	}
}
