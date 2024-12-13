package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GameManager;
import edu.westga.cs3211.text_adventure_game.model.HazardData;

/**
 * Tests the setInteractionInfo method in the GameManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class SetInteractionInfoTest {
	
	/**
	 * Test set interaction info.
	 */
	@Test
	public void testSetInteractionInfoWithHazardData() {
		GameManager theGameManager = new GameManager();
        HazardData theHazardData = new HazardData(10, "Test Hazard");
        int damage = theHazardData.getDamage();
        theGameManager.setInteractionInfo(theHazardData, damage);
        assertEquals(theGameManager.getInteractionInfo(), "You have taken " + damage + " damage due to: " + System.lineSeparator() + "\t"
				+ theHazardData.getDescription());
    }

}
