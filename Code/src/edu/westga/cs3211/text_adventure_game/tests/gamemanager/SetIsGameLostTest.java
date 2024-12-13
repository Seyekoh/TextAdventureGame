package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GameManager;

/**
 * Tests the setIsGameLost method in the GameManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class SetIsGameLostTest {

	/**
	 * Tests the setIsGameLost method
	 */
	@Test
	public void testSetIsGameLost() {
		GameManager gameManager = new GameManager();
		gameManager.setIsGameLost(true);
		assertTrue(gameManager.checkIfGameOver());
	}
}
