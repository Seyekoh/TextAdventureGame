package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GameManager;

/**
 * Tests the setIsGameWon method in the GameManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class SetIsGameWonTest {
	
	/**
	 * Test set is game won.
	 */
	@Test
	public void testSetIsGameWon() {
		GameManager theGameManager = new GameManager();
		theGameManager.setIsGameWon(true);
		assertTrue(theGameManager.checkIfGameOver());
	}

	/**
	 * Test set is game won false.
	 */
	@Test
	public void testSetIsGameWonFalse() {
		GameManager theGameManager = new GameManager();
		theGameManager.setIsGameWon(false);
		assertFalse(theGameManager.checkIfGameOver());
	}

}
