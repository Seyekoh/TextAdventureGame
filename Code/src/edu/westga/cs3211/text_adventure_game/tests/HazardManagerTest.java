package edu.westga.cs3211.text_adventure_game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.HazardManager;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;

/**
 * Tests the HazardManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class HazardManagerTest {
	
	/**
	 * Tests the constructor
	 */
	@Test
	public void testConstructorValid() {
		HazardManager manager = new HazardManager();
		
		assertTrue(manager != null);
	}
	
	/**
	 * Tests the getHazardData method
	 */
	@Test
	public void testGetHazardDataValid() {
		HazardManager manager = new HazardManager();

		assertEquals(manager.getHazardData(HazardType.PIT).toString(), "80 A deep, dark pit with no visible bottom.");
	}
}
