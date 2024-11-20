package edu.westga.cs3211.text_adventure_game.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Action;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.ActionType;

/**
 * The test class for the Action class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class ActionTest {
	
	/**
	 * Tests the constructor of the Action class for valid input
	 */
	@Test
	public void testActionConstructorValid() {
		Action action = new Action("Test", "Test Description", ActionType.MOVE);
		assertAll(
				() -> assertTrue(action.getName().equals("Test")),
                () -> assertTrue(action.getDescription().equals("Test Description")),
                () -> assertTrue(action.getType().equals(ActionType.MOVE)));
	}
	
	/**
	 * Tests the constructor of the Action class for null name
	 */
	@Test
	public void testActionConstructorNullName() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Action(null, "Test Description", ActionType.MOVE);
        });

        assertTrue(exception.getMessage().equals("Name cannot be null"));
    }
	
	/**
	 * Tests the constructor of the Action class for empty name
	 */
	@Test
	public void testActionConstructorEmptyName() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Action("", "Test Description", ActionType.MOVE);
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Name cannot be blank or empty"));
	}
	
	/**
	 * Tests the constructor of the Action class for blank name
	 */
	@Test
	public void testActionConstructorBlankName() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Action(" ", "Test Description", ActionType.MOVE);
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Name cannot be blank or empty"));
	}
	
	/**
	 * Tests the getName method of the Action class
	 */
	@Test
	public void testGetName() {
		Action action = new Action("Test", "Test Description", ActionType.MOVE);
		assertTrue(action.getName().equals("Test"));
	}	
	
	/**
	 * Tests the constructor of the Action class for null description
	 */
	@Test
	public void testActionConstructorNullDescription() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Action("Test", null, ActionType.MOVE);
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Description cannot be null"));
	}
	
	/**
	 * Tests the constructor of the Action class for empty description
	 */
	@Test
	public void testActionConstructorEmptyDescription() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Action("Test", "", ActionType.MOVE);
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Description cannot be blank or empty"));
	}
	
	/**
	 * Tests the getDescription method of the Action class
	 */
	@Test
	public void testGetDescription() {
		Action action = new Action("Test", "Test Description", ActionType.MOVE);
		assertTrue(action.getDescription().equalsIgnoreCase("Test Description"));
	}
	
	/**
	 * Tests the constructor of the Action class for null type
	 */
	@Test
	public void testActionConstructorNullType() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Action("Test", "Test Description", null);
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Type cannot be null"));
	}
	
	/**
	 * Tests the getType method of the Action class
	 */
	@Test
	public void testGetType() {
		Action action = new Action("Test", "Test Description", ActionType.MOVE);
		assertTrue(action.getType().equals(ActionType.MOVE));
	}
	
	/**
	 * Tests the toString method of the Action class
	 */
	@Test
	public void testToString() {
		Action action = new Action("Test", "Test Description", ActionType.MOVE);
		assertTrue(action.toString().equalsIgnoreCase("Test: Test Description"));
	}

}
