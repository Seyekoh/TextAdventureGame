package edu.westga.cs3211.text_adventure_game.tests.worldmanager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.WorldManager;

/**
 * Tests the constructor of the WorldManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class ConstructorTest {
	
	/**
	 * Tests the constructor
	 */
	@Test
	public void testConstructor() {
		WorldManager manager = new WorldManager();
		assertNotNull(manager);
	}

}
