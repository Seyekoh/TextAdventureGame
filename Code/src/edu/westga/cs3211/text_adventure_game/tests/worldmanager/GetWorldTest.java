package edu.westga.cs3211.text_adventure_game.tests.worldmanager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.WorldManager;

/**
 * Tests the getWorld method
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GetWorldTest {
	
	/**
	 * Tests the getWorld method
	 */
	@Test
	public void testGetWorld() {
		WorldManager manager = new WorldManager();
		assertNotNull(manager.getWorld());
	}

}
