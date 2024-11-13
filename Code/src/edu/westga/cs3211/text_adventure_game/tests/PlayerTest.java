package edu.westga.cs3211.text_adventure_game.tests;

import edu.westga.cs3211.text_adventure_game.Player;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Player class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class PlayerTest {
	private Player player;
		
	/**
	 * Tests the Player constructor for valid Player creation
	 */
	@Test
	public void testPlayerConstructorValid() {
		Player player = new Player();
		assertFalse(player == null);
	}
	
	/**
	 * Sets up the Player object for testing
	 */
	@BeforeEach
	public void setUp() {
		this.player = new Player();
	}	
	
	/**
	 * Tests the initial health of the player
	 */
	@Test
	public void testInitialHealth() {
		assertTrue(this.player.getHealth() == 100);
	}
	
	/**
	 * Tests the takeDamage method
	 */
	@Test
	public void testTakeDamage() {
		this.player.takeDamage(10);
		assertTrue(this.player.getHealth() == 90);
	}
	
}
