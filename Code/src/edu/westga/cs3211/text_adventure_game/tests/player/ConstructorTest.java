package edu.westga.cs3211.text_adventure_game.tests.player;

import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Player;

/**
 * Tests the Player class's constructor
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class ConstructorTest {
	
	/**
	 * Tests the Player constructor for valid Player creation
	 */
	@Test
	public void testPlayerConstructorValid() {
		Player player = new Player();
		assertFalse(player == null);
	}

}
