package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GameManager;

/**
 * Tests the setIsDiaryRead method in the GameManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class SetIsDiaryReadTest {
	
	/**
	 * Tests the setIsDiaryRead method
	 */
	@Test
	public void testSetIsDiaryRead() {
		GameManager gameManager = new GameManager();
		gameManager.setIsDiaryRead(true);
		assertTrue(gameManager.getIsDiaryRead());
	}

}
