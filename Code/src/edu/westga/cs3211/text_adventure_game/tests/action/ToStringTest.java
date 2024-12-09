package edu.westga.cs3211.text_adventure_game.tests.action;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Action;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.ActionType;

/**
 * Tests the toString method of the Action class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class ToStringTest {
	
	/**
	 * Tests the toString method of the Action class
	 */
	@Test
	public void testToString() {
		Action action = new Action("Test", "Test Description", ActionType.MOVE);
		assertTrue(action.toString().equals("Test: Test Description"));
	}
}
