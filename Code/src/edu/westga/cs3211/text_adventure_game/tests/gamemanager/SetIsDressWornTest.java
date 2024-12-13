package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GameManager;

/**
 * Tests the setIsDressWorn method in the GameManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class SetIsDressWornTest {
	
	/**
	 * Tests the setIsDressWorn method
	 */
	@Test
	public void testSetIsDressWorn() {
		GameManager gameManager = new GameManager();
		gameManager.setIsDressWorn(true);
		assertTrue(gameManager.getIsDressWorn());
	}

}
