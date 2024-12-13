package edu.westga.cs3211.text_adventure_game.tests.player;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.Player;

/**
 * Tests the getInventory method of the Player class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GetInventoryTest {
	
	/**
	 * Tests the get inventory method
	 */
	@Test
	public void testGetInventory() {
		Player player = new Player();
		List<Item> inventory = player.getInventory();
		assertTrue(inventory.isEmpty());
	}

}
