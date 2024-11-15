package edu.westga.cs3211.text_adventure_game.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.Action;
import edu.westga.cs3211.text_adventure_game.GameManager;
import edu.westga.cs3211.text_adventure_game.GlobalEnums.ActionType;
import edu.westga.cs3211.text_adventure_game.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.Location;
import edu.westga.cs3211.text_adventure_game.Player;

/**
 * The test class for the GameManager class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GameManagerTest {

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
		assertEquals("Start", this.gameManager.getCurrentLocation().getName());
	}

	/**
	 * Tests the getCurrentLocation method
	 */
	@Test
	public void testGetCurrentLocation() {
		assertEquals("Start", this.gameManager.getCurrentLocation().getName());
	}

	/**
	 * Tests the getPlayer method
	 */
	@Test
	public void testGetPlayer() {
		Player player = this.gameManager.getPlayer();

		assertEquals(player, this.gameManager.getPlayer());
	}

	/**
	 * Tests the movePlayer method with a valid location and no hazard or goal
	 */
	@Test
	public void testMovePlayerToValidLocation() {
		Location startLocation = this.gameManager.getCurrentLocation();
		this.gameManager.movePlayer(Direction.NORTH);
		Location currentLocation = this.gameManager.getCurrentLocation();

		assertAll(() -> assertEquals("Start", startLocation.getName()),
				() -> assertEquals("Forest", currentLocation.getName()),
				() -> assertEquals(100, this.gameManager.getPlayer().getHealth()),
				() -> assertEquals(HazardType.NONE, currentLocation.getHazardType()),
				() -> assertFalse(currentLocation.checkIfLocationIsGoal()),
				() -> assertEquals(100, this.gameManager.getPlayer().getHealth()),
				() -> assertEquals(HazardType.NONE, this.gameManager.getCurrentLocation().getHazardType()),
				() -> assertFalse(this.gameManager.getCurrentLocation().checkIfLocationIsGoal()));
	}

	/**
	 * Tests the movePlayer method with no Location in the given direction
	 */
	@Test
	public void testMovePlayerToInvalidLocation() {
		Location startLocation = this.gameManager.getCurrentLocation();

		assertThrows(IllegalArgumentException.class, () -> {
			this.gameManager.movePlayer(Direction.SOUTH);
		});
	}

	/**
	 * Tests the movePlayer method with a location that has a hazard
	 */
	@Test
	public void testMovePlayerToLocationWithHazard() {
		this.gameManager.movePlayer(Direction.NORTH);
		this.gameManager.movePlayer(Direction.EAST);

		assertAll(() -> assertEquals(HazardType.PIT, this.gameManager.getCurrentLocation().getHazardType()),
				() -> assertEquals(20, this.gameManager.getPlayer().getHealth()));
	}

	/**
	 * Tests the movePlayer method with a location that kills the player
	 */
	@Test
	public void testMovePlayerToLocationThatKillsPlayer() {
		this.gameManager.getPlayer().takeDamage(80);

		this.gameManager.movePlayer(Direction.NORTH);
		this.gameManager.movePlayer(Direction.EAST);

		assertAll(() -> assertEquals(-60, this.gameManager.getPlayer().getHealth()),
				() -> assertTrue(this.gameManager.checkIfGameOver()),
				() -> assertEquals("You have died in the " + this.gameManager.getCurrentLocation().getName()
						+ " due to falling into a pit. Your journey is over and you have lost the game. Better luck next time.",
						this.gameManager.getGameOverMessage()));

	}

	/**
	 * Tests the movePlayer method with a location that is the goal
	 */
	@Test
	public void testMovePlayerToGoalLocation() {
		this.gameManager.movePlayer(Direction.NORTH);
		this.gameManager.movePlayer(Direction.EAST);
		this.gameManager.movePlayer(Direction.NORTH);

		assertAll(() -> assertEquals("Old Church", this.gameManager.getCurrentLocation().getName()),
				() -> assertTrue(this.gameManager.checkIfGameOver()),
				() -> assertEquals(
						"You have made it to the " + this.gameManager.getCurrentLocation().getName()
								+ " alive. Your journey is complete and you have won the game. Rest well adventurer.",
						this.gameManager.getGameOverMessage()));
	}

	/**
	 * Tests the checkIfGameOver method with neither flag set
	 */
	@Test
	public void testCheckIfGameOverWithNeitherFlagSet() {
		assertFalse(this.gameManager.checkIfGameOver());
	}

	/**
	 * Tests the performAction method with the player moving
	 */
	@Test
	public void testPerformActionWithPlayerMoving() {
		Action action = new Action("MOVE", Direction.NORTH.toString(), ActionType.MOVE);
		this.gameManager.performAction(action);

		assertAll(() -> assertEquals("Forest", this.gameManager.getCurrentLocation().getName()),
				() -> assertEquals(100, this.gameManager.getPlayer().getHealth()));
	}

	/**
	 * Tests the getAllAvailableActions method
	 */
	@Test
	public void testGetAllAvailableActions() {
		assertEquals(1, this.gameManager.getAllAvailableActions().size());
	}
}
