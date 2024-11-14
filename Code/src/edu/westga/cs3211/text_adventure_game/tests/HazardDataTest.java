package edu.westga.cs3211.text_adventure_game.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.HazardData;

/**
 * The test class for the HazardData class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class HazardDataTest {
	
	/**
	 * Test the constructor of the HazardData class
	 */
	@Test
	public void testConstructorValid() {
		HazardData testHazardData = new HazardData(10, "A test hazard");

		assertAll(
				() -> assertEquals(10, testHazardData.getDamage()),
				() -> assertEquals("A test hazard", testHazardData.getDescription()));
	}
	
	/**
	 * Test the constructor of the HazardData class with a negative damage
	 */
	@Test
	public void testConstructorNegativeDamage() {
		assertAll(
				() -> assertEquals("Damage cannot be negative",
						assertThrows(IllegalArgumentException.class, 
								() -> new HazardData(-1, "A test hazard")).getMessage()),
				() -> assertEquals("Damage cannot be negative",
						assertThrows(IllegalArgumentException.class, 
								() -> new HazardData(-100, "A test hazard")).getMessage()));
	}
	
	/**
	 * Test the constructor of the HazardData class with a null description
	 */
	@Test
	public void testConstructorNullDescription() {
		assertAll(
				() -> assertEquals("Description cannot be null",
						assertThrows(IllegalArgumentException.class, 
								() -> new HazardData(10, null)).getMessage()),
				() -> assertEquals("Description cannot be null",
						assertThrows(IllegalArgumentException.class, 
								() -> new HazardData(10, null)).getMessage()));
	}
	
	/**
	 * Test the constructor of the HazardData class with a blank description
	 */
	@Test
	public void testConstructorBlankDescription() {
		assertAll(
				() -> assertEquals("Description cannot be blank",
						assertThrows(IllegalArgumentException.class, 
								() -> new HazardData(10, "")).getMessage()),
				() -> assertEquals("Description cannot be blank",
						assertThrows(IllegalArgumentException.class, 
								() -> new HazardData(10, "   ")).getMessage()));
	}
	
	/**
	 * Test the getDamage method of the HazardData class
	 */
	@Test
	public void testGetDamage() {
		HazardData testHazardData = new HazardData(10, "A test hazard");

		assertEquals(10, testHazardData.getDamage());
	}
	
	/**
	 * Test the getDescription method of the HazardData class
	 */
	@Test
	public void testGetDescription() {
		HazardData testHazardData = new HazardData(10, "A test hazard");

		assertEquals("A test hazard", testHazardData.getDescription());
	}
	
	/**
	 * Test the toString method of the HazardData class
	 */
	@Test
	public void testToString() {
		HazardData testHazardData = new HazardData(10, "A test hazard");

		assertEquals("10 A test hazard", testHazardData.toString());
	}
}
