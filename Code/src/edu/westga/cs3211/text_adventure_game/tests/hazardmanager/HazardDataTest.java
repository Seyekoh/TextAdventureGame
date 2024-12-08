package edu.westga.cs3211.text_adventure_game.tests.hazardmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.HazardManager;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;

/**
 * Tets the HazardData from the HazardManager
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class HazardDataTest {

	/**
	 * Tests the getHazardData method
	 */
	@Test
	public void testGetHazardDataValid() {
		HazardManager manager = new HazardManager();

		assertEquals(manager.getHazardData(HazardType.DANCINGSHADOWS).toString(), "18 The shadows attack you!");
	}
	
	/**
	 * Tests the getHazardTypeFromHazardData method
	 */
	@Test
	public void testGetHazardTypeFromHazardDataValid() {
		HazardManager manager = new HazardManager();

		assertEquals(manager.getHazardTypeFromHazardData(manager.getHazardData(HazardType.DANCINGSHADOWS)), HazardType.DANCINGSHADOWS);
	}
}
