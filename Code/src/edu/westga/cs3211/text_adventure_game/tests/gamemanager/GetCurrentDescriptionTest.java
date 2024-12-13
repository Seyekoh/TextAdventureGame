package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GameManager;
import edu.westga.cs3211.text_adventure_game.model.Location;

/**
 * Tests the getCurrentDescription method in the GameManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GetCurrentDescriptionTest {

	/**
	 * Tests the getCurrentDescription method
	 */
	@Test
	public void testGetCurrentDescription() {
		GameManager testGameManager = new GameManager();
		Location testLocation = testGameManager.getCurrentLocation();
		String result = testGameManager.getCurrentLocationDescription();
		assertEquals(testLocation.getDescription(), result);
	}
}
