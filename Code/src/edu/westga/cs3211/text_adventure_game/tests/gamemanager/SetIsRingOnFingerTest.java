package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GameManager;

/**
 * Tests the setIsRingOnFinger method in the GameManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class SetIsRingOnFingerTest {
	
	/**
	 * Tests the setIsRingOnFinger method
	 */
	@Test
	public void testSetIsRingOnFinger() {
		GameManager gameManager = new GameManager();
		gameManager.setIsRingOnFinger(true);
		assertTrue(gameManager.getIsRingOnFinger());
	}

}
