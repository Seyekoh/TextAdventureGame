package edu.westga.cs3211.text_adventure_game.tests.locationreader;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.LocationReader;

/**
 * The test class for the LocationReader constructor.
 * 
 * @author Liam Worthington
 * @version Fall 2024
 */
public class ConstructorTest {

	/**
	 * Test the constructor of the LocationReader class with a null path
	 */
	@Test
	public void testConstructorNullPath() {
		assertAll(() -> assertEquals("locationPath cannot be null",
				assertThrows(IllegalArgumentException.class, () -> new LocationReader(null)).getMessage()));
	}

	/**
	 * Test the constructor of the LocationReader class with a blank path
	 */
	@Test
	public void testConstructorBlankDescription() {
		assertAll(() -> assertEquals("locationPath cannot be empty.",
				assertThrows(IllegalArgumentException.class, () -> new LocationReader("")).getMessage()));
	}

}
