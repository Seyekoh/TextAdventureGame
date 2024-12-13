package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GameManager;

/**
 * Tests the CheckIfPlayerIsAlive method
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class CheckIfPlayerIsAliveTest {
	
	/**
	 * Tests the player is alive
	 */
	@Test
	public void testPlayerIsAliveWhenAlive() {
		GameManager gameManager = new GameManager();
		assertEquals(true, gameManager.checkIfPlayerIsAlive());
	}
	
	/**
	 * Tests the player is dead
	 */
	@Test
	public void testPlayerIsAliveWhenDead() {
		GameManager gameManager = new GameManager();
		gameManager.getPlayer().applyDamage(100);
		assertEquals(false, gameManager.checkIfPlayerIsAlive());
	}

}
