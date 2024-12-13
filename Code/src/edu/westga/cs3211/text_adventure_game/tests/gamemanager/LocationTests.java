package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Action;
import edu.westga.cs3211.text_adventure_game.model.GameManager;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.Location;
import edu.westga.cs3211.text_adventure_game.model.Player;
import edu.westga.cs3211.text_adventure_game.model.World;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.ActionType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;

/**
 * Tests the GameManager for location info
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class LocationTests {
	
	private GameManager gameManager;
	
	/**
	 * Sets up the instance variables for the test class
	 */
	@BeforeEach
	public void setUp() {
		this.gameManager = new GameManager();
	}
	
	/**
	 * Tests the getCurrentLocation method
	 */
	@Test
	public void testGetCurrentLocation() {
		assertEquals(GlobalEnums.LocationName.ENTRANCEHALL, this.gameManager.getCurrentLocation().getName());
	}
	
	/**
	 * Tests the movePlayer method with a valid location and no hazard or goal
	 */
	@Test
	public void testMovePlayerToValidLocation() {
		Location startLocation = this.gameManager.getCurrentLocation();
		Action action = new Action("Move", "NORTH", ActionType.MOVE);
		this.gameManager.performAction(action, Item.NONE);

		assertAll(() -> assertEquals(GlobalEnums.LocationName.ENTRANCEHALL, startLocation.getName()),
				() -> assertNotEquals(this.gameManager.getCurrentLocation(), startLocation));
	}

	/**
	 * Tests the movePlayer method with no Location in the given direction
	 */
	@Test
	public void testMovePlayerToInvalidLocation() {
		Action action = new Action("Move", "South", ActionType.MOVE);
		assertThrows(IllegalArgumentException.class, () -> {
			this.gameManager.performAction(action, Item.NONE);
		});
	}

	/**
	 * Tests the movePlayer method with a location that kills the player
	 */
	@Test
	public void testMovePlayerToLocationThatKillsPlayer() {
		this.gameManager.setCurrentLocation(this.gameManager.getWorld().getLocationByName(GlobalEnums.LocationName.KITCHEN));
		Action action = new Action("MOVE", Direction.DOWN.toString(), ActionType.MOVE);
		this.gameManager.performAction(action, Item.NONE);

		assertAll(() -> assertEquals(GlobalEnums.LocationName.BASEMENT, this.gameManager.getCurrentLocation().getName()),
				() -> assertEquals(0, this.gameManager.getPlayer().getHealth()),
				() -> assertTrue(this.gameManager.checkIfGameOver()),
				() -> assertEquals("You are attacked by a rotting corpse! You have taken 100 damage. You have died in the BASEMENT. Your journey is over and you have lost the game. Better luck next time.",
						this.gameManager.getGameOverMessage()));

	}

	/**
	 * Tests the movePlayer method with a location that is the goal
	 */
	@Test
	public void testMovePlayerToGoalLocation() {
		World world = this.gameManager.getWorld();
		this.gameManager.setCurrentLocation(world.getLocationByName(GlobalEnums.LocationName.ATTIC));
		Action talk = new Action("TALK", "To NPC", ActionType.TALK);
		this.gameManager.performAction(talk, Item.NONE);
		this.gameManager.setCurrentLocation(world.getLocationByName(GlobalEnums.LocationName.ENTRANCEHALL));
		Player player = this.gameManager.getPlayer();
		player.addItemToInventory(Item.MUSICBOX);
		Action give = new Action("GIVE", "MUSICBOX", ActionType.GIVE);
		this.gameManager.performAction(give, Item.MUSICBOX);
		Action moveSouth = new Action(GlobalEnums.ActionType.MOVE.toString(), Direction.SOUTH.toString(), GlobalEnums.ActionType.MOVE);
		this.gameManager.performAction(moveSouth, Item.NONE);

		assertAll(() -> assertEquals(GlobalEnums.LocationName.EXIT, this.gameManager.getCurrentLocation().getName()),
				  () -> assertTrue(this.gameManager.checkIfGameOver()),
				  () -> assertEquals(
						"You have made it to the " + this.gameManager.getCurrentLocation().getName()
								+ " alive. Your journey is complete and you have won the game. Rest well adventurer.",
						this.gameManager.getGameOverMessage()));
	}
	
	/**
	 * Tests the setCurrentLocation method with null location
	 */
	@Test
	public void testSetCurrentLocationNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			this.gameManager.setCurrentLocation(null);
		});
	}
}
