package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Action;
import edu.westga.cs3211.text_adventure_game.model.GameManager;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.ActionType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.World;

/**
 * Tests the game over functionality in game manager
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GameOverTest {
	
	private GameManager gameManager;
	
	/**
	 * Sets up the instance variables for each test
	 */
	@BeforeEach
	public void setUp() {
		this.gameManager = new GameManager();
	}
	
	/**
	 * Tests the checkIfGameOver method with neither flag set
	 */
	@Test
	public void testCheckIfGameOverWithNeitherFlagSet() {
		assertFalse(this.gameManager.checkIfGameOver());
	}
	
	/**
	 * Tests the checkIfGameOver method with the lose flag set
	 */
	@Test
	public void testCheckIfGameOverGameLose() {
		this.gameManager.getPlayer().applyDamage(99);
		Action action = new Action("MOVE", "NORTH", ActionType.MOVE);
		this.gameManager.performAction(action, Item.NONE);
		
		assertTrue(this.gameManager.checkIfGameOver());
	}
	
	/**
	 * Tests the checkIfGameOver method with the win flag set
	 */
	@Test
	public void testCheckIfGameOverGameWin() {
		World world = this.gameManager.getWorld();
		world.connectLocations(this.gameManager.getCurrentLocation(), Direction.SOUTH, world.getLocationByName(GlobalEnums.LocationName.EXIT));
        Action action = new Action("MOVE", "SOUTH", ActionType.MOVE);
        this.gameManager.performAction(action, Item.NONE);
		
        assertTrue(this.gameManager.checkIfGameOver());
	}
	
	/**
	 * Tests the onGameOverLose(String) method
	 */
	@Test
	public void testOnGameOverLose() {
		String interactionInfo = "Test";
		this.gameManager.setInteractionInfo(interactionInfo);
		this.gameManager.onGameOverLose("Test");
		
		assertEquals("Test" + System.lineSeparator() + "Test", this.gameManager.getInteractionInfo());
	}
}

