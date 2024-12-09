package edu.westga.cs3211.text_adventure_game.tests.location;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Action;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.ActionType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.Location;

/**
 * Tests the setters of the Location class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class SetterTest {

	/**
	 * Tests the setHazardType method for a Location
	 */
	@Test
	public void testSetHazardType() {
		Location testLocation = new Location(GlobalEnums.LocationName.ATTIC, "This is a test location", HazardType.NONE, false, new ArrayList<>(), Item.NONE);
		testLocation.setHazardType(HazardType.CURSEDSTOVE);
		
		assertTrue(testLocation.getHazardType() == HazardType.CURSEDSTOVE);
	}
	
	/**
	 * Tests the setIsGoal method for a Location
	 */
	@Test
	public void testSetIsGoal() {
		Location testLocation = new Location(GlobalEnums.LocationName.ATTIC, "This is a test location", HazardType.NONE,
				false, new ArrayList<>(), Item.NONE);
		testLocation.setIsGoal(true);

		assertTrue(testLocation.checkIfLocationIsGoal());
	}
	
	/**
	 * Tests the setActions method for a Location
	 */
	@Test
	public void testSetActions() {
		Location testLocation = new Location(GlobalEnums.LocationName.ATTIC, "This is a test location", HazardType.NONE,
				false, new ArrayList<>(), Item.NONE);
		Action action = new Action("Test", "Test Description", ActionType.MOVE);
		List<Action> actions = new ArrayList<>();
		actions.add(action);
		testLocation.setActions(actions);

		assertTrue(testLocation.getActions().contains(action));
	}
}
