package edu.westga.cs3211.text_adventure_game.tests.hazarddata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.HazardData;

/**
 * The test class for the HazardData class's toString method
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class ToStringTest {

	/**
	 * Test the toString method of the HazardData class
	 */
	@Test
	public void testToString() {
		HazardData testHazardData = new HazardData(10, "A test hazard");

		assertEquals("10 A test hazard", testHazardData.toString());
	}
}
