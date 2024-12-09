package edu.westga.cs3211.text_adventure_game.tests.npc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.NPC;

/**
 * Tests the constructor of the NPC class
 * 
 * @author Liam Worthington
 * @version Fall 2024
 */
public class ConstructorTest {

	/**
	 * Tests the constructor of the NPC class for a valid NPC
	 */
	@Test
	public void testConstructorForValidNPC() {
		NPC testNPC = new NPC("This is a test NPC");

	    assertAll(
	        () -> assertEquals("This is a test NPC", testNPC.getDescription())
	    );
	}
	
	/**
	 * Tests the constructor of the NPC class with a null description
	 */
	@Test
	public void testConstructorWithNullDescription() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new NPC(null);
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Description cannot be null"));
	}
	
	/**
	 * Tests the constructor of the NPC class with a blank description
	 */
	@Test
	public void testConstructorWithBlankDescription() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new NPC("");
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Description cannot be blank"));
	}
}