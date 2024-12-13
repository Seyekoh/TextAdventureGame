package edu.westga.cs3211.text_adventure_game.tests.player;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.Player;

/**
 * Tests the doesPlayerHaveItem method of the Player class
 * 
 * @author James bridges
 * @version Fall 2024
 */
public class DoesPlayerHaveItemTest {
	
	/**
	 * Tests the doesPlayerHaveItem method
	 */
	@Test
	public void testDoesPlayerHaveItem() {
		Player player = new Player();
		player.addItemToInventory(Item.RING);
		assert (player.doesPlayerHaveItem(Item.RING));
	}

}
