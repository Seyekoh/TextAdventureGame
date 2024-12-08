package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Action;
import edu.westga.cs3211.text_adventure_game.model.GameManager;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.ActionType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;

/**
 * Tests the GameManager for action info
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class ActionTest {
	
	private GameManager gameManager;
	
	/**
	 * Sets up the instance variables for the test class
	 */
	@BeforeEach
	public void setUp() {
		this.gameManager = new GameManager();
	}
	
	/**
	 * Tests the performAction method with the player moving
	 */
	@Test
	public void testPerformActionWithPlayerMoving() {
		Action action = new Action("MOVE", Direction.NORTH.toString(), ActionType.MOVE);
		this.gameManager.performAction(action);

		assertAll(() -> assertEquals(GlobalEnums.LocationName.LIBRARY, this.gameManager.getCurrentLocation().getName()),
				() -> assertEquals(90, this.gameManager.getPlayer().getHealth()));
	}

	/**
	 * Tests the getAllAvailableActions method
	 */
	@Test
	public void testGetAllAvailableActions() {
		assertEquals(1, this.gameManager.getAllAvailableActions().size());
	}
	
	/**
	 * Tests the getInteractionInfo method
	 */
	@Test
	public void testGetInteractionInfo() {
		this.gameManager.movePlayer(Direction.NORTH);
		
		String expected = "You have taken 10 damage due to: A ghastly apparition appears and attacks you!";
	    String actual = this.gameManager.getInteractionInfo().replaceAll("\\s+", " ").trim();
	    assertEquals(expected, actual);
	}
	
}
