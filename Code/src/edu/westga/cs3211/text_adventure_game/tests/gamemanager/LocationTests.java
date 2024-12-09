package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GameManager;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.Location;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;

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
		this.gameManager.movePlayer(Direction.NORTH);
		Location currentLocation = this.gameManager.getCurrentLocation();

		assertAll(() -> assertEquals(GlobalEnums.LocationName.ENTRANCEHALL, startLocation.getName()),
				() -> assertEquals(GlobalEnums.LocationName.LIBRARY, currentLocation.getName()),
				() -> assertEquals(90, this.gameManager.getPlayer().getHealth()),
				() -> assertEquals(HazardType.GHOSTLYAPPARITION, currentLocation.getHazardType()),
				() -> assertFalse(currentLocation.checkIfLocationIsGoal()));
	}

	/**
	 * Tests the movePlayer method with no Location in the given direction
	 */
	@Test
	public void testMovePlayerToInvalidLocation() {
		assertThrows(IllegalArgumentException.class, () -> {
			this.gameManager.movePlayer(Direction.SOUTH);
		});
	}

	/**
	 * Tests the movePlayer method with a location that kills the player
	 */
	@Test
	public void testMovePlayerToLocationThatKillsPlayer() {
		this.gameManager.movePlayer(Direction.NORTH);
		this.gameManager.movePlayer(Direction.EAST);
		this.gameManager.movePlayer(Direction.DOWN);

		assertAll(() -> assertEquals(-25, this.gameManager.getPlayer().getHealth()),
				() -> assertTrue(this.gameManager.checkIfGameOver()),
				() -> assertEquals("You are attacked by a rotting corpse! You have taken 100 damage. You have died in the BASEMENT. Your journey is over and you have lost the game. Better luck next time.",
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
		this.gameManager.movePlayer(Direction.EAST);

		assertAll(() -> assertEquals(GlobalEnums.LocationName.EXIT, this.gameManager.getCurrentLocation().getName()),
				() -> assertTrue(this.gameManager.checkIfGameOver()),
				() -> assertEquals(
						"You have made it to the " + this.gameManager.getCurrentLocation().getName()
								+ " alive. Your journey is complete and you have won the game. Rest well adventurer.",
						this.gameManager.getGameOverMessage()));
	}
}
