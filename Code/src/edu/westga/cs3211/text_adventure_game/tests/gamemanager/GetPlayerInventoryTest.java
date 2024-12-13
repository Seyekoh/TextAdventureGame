package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GameManager;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.Player;

/**
 * Tests the getPlayerInventory method in the GameManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GetPlayerInventoryTest {

	/**
	 * Tests the getPlayerInventory method
	 */
	@Test
	public void testGetPlayerInventory() {
		GameManager testGameManager = new GameManager();
		Player testPlayer = testGameManager.getPlayer();
		testPlayer.addItemToInventory(Item.DRESS);
		
		assertEquals(testGameManager.getPlayerInventory().get(0), Item.DRESS);
	}
}
