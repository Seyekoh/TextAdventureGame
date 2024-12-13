package edu.westga.cs3211.text_adventure_game.tests.world;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.NPC;
import edu.westga.cs3211.text_adventure_game.model.World;

/**
 * Tests the World class's getNPC method
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GetNPCTest {
	
	/**
	 * Tests the getNPC method
	 */
	@Test
	public void testGetNPC() {
		World world = new World();
		NPC npc = new NPC("Test");
		world.addNPC(npc);
		
		assertEquals(npc, world.getNPC());		
	}

}
