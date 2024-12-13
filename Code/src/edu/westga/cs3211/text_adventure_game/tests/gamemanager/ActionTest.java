package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Action;
import edu.westga.cs3211.text_adventure_game.model.GameManager;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.ActionType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.World;

/**
 * Tests the GameManager for action info
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class ActionTest {

	private GameManager gameManager;

	/**
	 * Sets up the instance variables for the test class
	 */
	@BeforeEach
	public void setUp() {
		this.gameManager = new GameManager();
	}

	/**
	 * Tests the performAction method
	 */
	@Test
	public void testPerformActionWithPlayerMoving() {
		Action action = new Action(ActionType.MOVE.toString(), Direction.UP.toString(), ActionType.MOVE);
		World world = this.gameManager.getWorld();
		this.gameManager.setCurrentLocation(world.getLocationByName(GlobalEnums.LocationName.LIBRARY));
		this.gameManager.performAction(action, Item.NONE);


		assertEquals(GlobalEnums.LocationName.ATTIC, this.gameManager.getCurrentLocation().getName());
	}

	/**
	 * Tests the getAllAvailableActions method
	 */
	@Test
	public void testGetAllAvailableActions() {
		assertEquals(5, this.gameManager.getAllAvailableActions().size());
	}

	/**
	 * Tests the getInteractionInfo method
	 */
	@Test
	public void testGetInteractionInfo() {
		World world = this.gameManager.getWorld();
		this.gameManager.setCurrentLocation(world.getLocationByName(GlobalEnums.LocationName.LIBRARY));
		Action action = new Action("MOVE", Direction.UP.toString(), ActionType.MOVE);
		this.gameManager.performAction(action, Item.NONE);

		String expected = "You have taken 3 damage due to: A creepy doll attacks you!";
		String actual = this.gameManager.getInteractionInfo();
		assertEquals(expected, actual);
	}

	/**
	 * Tests the performAction method when action is talk.
	 */
	@Test
	public void testTalkAction() {
		Action action = new Action("TALK", "Test talking", ActionType.TALK);
		
		this.gameManager.performAction(action, Item.NONE);
		assertEquals("The ghost gives you a cold stare, seemingly looking through you. "
				+ "As you're about to step away, the ghost speaks to you." + System.lineSeparator() + System.lineSeparator()
				+ "Ghost: I take it you wish to escape, yes? There is something I am missing, something I am looking for. Find it, and I shall set you free. I will be waiting for you by the entrance."
				+ System.lineSeparator() + System.lineSeparator()
				+ "Before you can say anything, the ghost fades away, leaving you alone.", this.gameManager.getInteractionInfo());
	}
	
}
