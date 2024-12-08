package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GameManager;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.Player;

/**
 * The test class for the GameManager constructor
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class ConstructorTest {
	
	private GameManager gameManager;

	/**
	 * Sets up the instance variable for the test class
	 */
	@BeforeEach
	public void setUp() {
		this.gameManager = new GameManager();
	}

	/**
	 * Tests if the player always starts at the default location
	 */
	@Test
	public void testPlayerStartsAtDefaultLocation() {
		assertEquals(GlobalEnums.LocationName.ENTRANCEHALL, this.gameManager.getCurrentLocation().getName());
	}
	
	/**
	 * Tests the getPlayer method
	 */
	@Test
	public void testGetPlayer() {
		Player player = this.gameManager.getPlayer();

		assertEquals(player, this.gameManager.getPlayer());
	}
	
}
