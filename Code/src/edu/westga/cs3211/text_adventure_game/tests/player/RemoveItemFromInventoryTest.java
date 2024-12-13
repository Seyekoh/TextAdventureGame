package edu.westga.cs3211.text_adventure_game.tests.player;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.Player;

/**
 * Tests the removeItemFromInventory method of the Player class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class RemoveItemFromInventoryTest {
	
	/**
	 * Tests the removeItemFromInventory method
	 */
	@Test
	public void testRemoveItemFromInventory() {
		Player player = new Player();
		player.addItemToInventory(Item.DIARY);
		player.removeItemFromInventory(Item.DIARY);
		
		assertTrue(player.getInventory().isEmpty());
	}
}
