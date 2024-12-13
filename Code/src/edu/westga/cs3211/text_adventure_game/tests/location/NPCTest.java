package edu.westga.cs3211.text_adventure_game.tests.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.Location;
import edu.westga.cs3211.text_adventure_game.model.NPC;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;

/**
 * Tests the Location class while it contains an NPC.
 * 
 * @author Liam Worthington
 * @version Fall 2024
 */
public class NPCTest {
	
	/**
	 * Tests the get description method when an npc is present.
	 */
	@Test
	public void testDescriptionWhenNpcPresent() {
		Location testLocation = new Location(GlobalEnums.LocationName.ATTIC, "This is a test location", HazardType.NONE, false, new ArrayList<>(), Item.NONE);
		testLocation.setNPC(new NPC("This is a test NPC."));
		
		assertEquals("This is a test location This is a test NPC.", testLocation.getDescription());
	}
	
	/**
	 * Tests the isNPCPresent method when an npc has not been introduced to location.
	 */
	@Test
	public void testIsNPCPresentWhenNeverSet() {
		Location testLocation = new Location(GlobalEnums.LocationName.ATTIC, "This is a test location", HazardType.NONE, false, new ArrayList<>(), Item.NONE);
		assertFalse(testLocation.isNPCPresent());
	}
	
	/**
	 * Tests the removeNPC method.
	 */
	@Test
	public void testRemoveNPC() {
		Location testLocation = new Location(GlobalEnums.LocationName.ATTIC, "This is a test location", HazardType.NONE, false, new ArrayList<>(), Item.NONE);
		testLocation.setNPC(new NPC("This is a test NPC."));
		assertTrue(testLocation.isNPCPresent());
		
		testLocation.removeNPC();
		assertFalse(testLocation.isNPCPresent());
	}
	
	/**
	 * Tests the removeNPC method when the NPC has a talk action.
	 */
	@Test
	public void testRemoveNPCWithNoTalkAction() {
		Location testLocation = new Location(GlobalEnums.LocationName.ATTIC, "This is a test location", HazardType.NONE, false, new ArrayList<>(), Item.NONE);
		
		testLocation.removeNPC();
		assertFalse(testLocation.isNPCPresent());
		assertTrue(testLocation.getActions().isEmpty());
	}

}
