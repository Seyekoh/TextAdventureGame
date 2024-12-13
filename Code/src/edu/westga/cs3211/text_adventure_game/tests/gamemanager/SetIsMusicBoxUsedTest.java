package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GameManager;

/**
 * Tests the setIsMusicBoxUsed method in the GameManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class SetIsMusicBoxUsedTest {
	
	/**
	 * Tests the setIsMusicBoxUsed method
	 */
	@Test
	public void testSetIsMusicBoxUsed() {
		GameManager gameManager = new GameManager();
		gameManager.setIsMusicBoxUsed(true);
		assertTrue(gameManager.getIsMusicBoxUsed());
	}

}
