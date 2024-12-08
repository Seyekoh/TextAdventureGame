package edu.westga.cs3211.text_adventure_game.tests.hazardmanager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.HazardManager;

/**
 * Tests the constructor of the HazardManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class ConstructorTest {

	/**
	 * Tests the constructor
	 */
	@Test
	public void testConstructorValid() {
		HazardManager manager = new HazardManager();
		
		assertTrue(manager != null);
	}
}
