package edu.westga.cs3211.text_adventure_game.tests.player;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.Player;

/**
 * Tests the addItemToInventory method of the Player class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class AddItemToInventoryTest {

	/**
	 * Tests the addItemToInventory method
	 */
	@Test
	public void testAddItemToInventory() {
		Player player = new Player();
		player.addItemToInventory(Item.RING);
		
		assertTrue(player.getInventory().contains(Item.RING));
	}
	
	/**
	 * Tests the addItemToInventory method with a null item
	 */
	@Test
	public void testAddItemToInventoryNull() {
		Player player = new Player();
		try {
			player.addItemToInventory(null);
		} catch (IllegalArgumentException iae) {
			assertTrue(true);
		}
	}
}
