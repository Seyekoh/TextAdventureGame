package edu.westga.cs3211.text_adventure_game.tests.player;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Player;

/**
 * Tests the takeDamage method of the Player class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class TakeDamageTest {
	
	/**
	 * Tests the takeDamage method
	 */
	@Test
	public void testTakeDamage() {
		Player player = new Player();
		player.takeDamage(10);
		assertTrue(player.getHealth() == 90);
	}

}
