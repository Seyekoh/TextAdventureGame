package edu.westga.cs3211.text_adventure_game.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.WorldManager;

/**
 * Tests the WorldManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class WorldManagerTest {
	
	/**
	 * Tests the constructor
	 */
	@Test
	public void testConstructor() {
		WorldManager manager = new WorldManager();
		assertNotNull(manager);
	}
	
	/**
	 * Tests the getWorld method
	 */
	@Test
	public void testGetWorld() {
		WorldManager manager = new WorldManager();
		assertNotNull(manager.getWorld());
	}
}
