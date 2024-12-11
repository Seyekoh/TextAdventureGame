package edu.westga.cs3211.text_adventure_game.tests.npc;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
