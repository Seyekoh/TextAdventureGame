package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GameManager;

/**
 * Tests the getIsMusicBoxUsed method in the GameManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GetIsMusicBoxUsedTest {

	/**
	 * Tests the getIsMusicBoxUsed method
	 */
	@Test
	public void testGetIsMusicBoxUsed() {
		GameManager testGameManager = new GameManager();
		boolean result = testGameManager.getIsMusicBoxUsed();
		assertFalse(result);
	}
}
