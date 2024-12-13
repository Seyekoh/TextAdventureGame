package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GameManager;

/**
 * The Class SetCurrentLocationDescriptionTest.
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class SetCurrentLocationDescriptionTest {

	/**
	 * Test set current location description.
	 */
	@Test
	public void testSetCurrentLocationDescription() {
		GameManager theGameManager = new GameManager();
		String newDescription = "New Description";
		theGameManager.setCurrentLocationDescription(newDescription);
		assertEquals(newDescription, theGameManager.getCurrentLocationDescription());
	}
	
	/**
	 * Test set current location description null.
	 */
	@Test
	public void testSetCurrentLocationDescriptionNull() {
		GameManager theGameManager = new GameManager();
		String newDescription = null;
		assertThrows(IllegalArgumentException.class, () -> {
			theGameManager.setCurrentLocationDescription(newDescription);
		});
	}
}
