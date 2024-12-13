package edu.westga.cs3211.text_adventure_game.tests.npc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.NPC;

/**
 * Tests the setters of the npc class
 * 
 * @author Liam Worthington
 * @version Fall 2024
 */
public class SetterTest {

	/**
	 * Tests the setDescription method for an NPC
	 */
	@Test
	public void testSetValidDescription() {
		NPC testNPC = new NPC("This is a test NPC");
		testNPC.setDescription("Still a test NPC");

		assertEquals("Still a test NPC", testNPC.getDescription());
	}

	/**
	 * Tests the setDialogue method for an NPC
	 */
	@Test
	public void testSetValidDialogue() {
		NPC testNPC = new NPC("This is a test NPC");
		testNPC.setDialogue("Still a test NPC");

		assertEquals("Still a test NPC", testNPC.getDialogue());
	}

	/**
	 * Tests the setDescription method for an NPC when null parameter
	 */
	@Test
	public void testNullDescription() {
		NPC testNPC = new NPC("This is a test NPC");
	
		assertThrows(IllegalArgumentException.class, () -> {
			testNPC.setDescription(null);
		});
	}

	/**
	 * Tests the setDescription method for an NPC when empty parameter
	 */
	@Test
	public void testEmptyDescription() {
		NPC testNPC = new NPC("This is a test NPC");
		
		assertThrows(IllegalArgumentException.class, () -> {
			testNPC.setDescription("");
		});
	}

	/**
	 * Tests the setDialogue method for an NPC when null parameter
	 */
	@Test
	public void testNullDialogue() {
		NPC testNPC = new NPC("This is a test NPC");
		
		assertThrows(IllegalArgumentException.class, () -> {
			testNPC.setDialogue(null);
		});
	}

	/**
	 * Tests the setDialogue method for an NPC when empty parameter
	 */
	@Test
	public void testEmptyDialogue() {
		NPC testNPC = new NPC("This is a test NPC");
		
		assertThrows(IllegalArgumentException.class, () -> {
			testNPC.setDialogue("");
		});
	}
}
