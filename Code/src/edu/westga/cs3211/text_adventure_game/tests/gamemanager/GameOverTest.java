package edu.westga.cs3211.text_adventure_game.tests.gamemanager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GameManager;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Direction;

/**
 * Tests the game over functionality in game manager
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GameOverTest {
	
	private GameManager gameManager;
	
	/**
	 * Sets up the instance variables for each test
	 */
	@BeforeEach
	public void setUp() {
		this.gameManager = new GameManager();
	}
	
	/**
	 * Tests the checkIfGameOver method with neither flag set
	 */
	@Test
	public void testCheckIfGameOverWithNeitherFlagSet() {
		assertFalse(this.gameManager.checkIfGameOver());
	}
	
	/**
	 * Tests the checkIfGameOver method with the lose flag set
	 */
	@Test
	public void testCheckIfGameOverGameLose() {
		this.gameManager.movePlayer(Direction.NORTH);
		this.gameManager.movePlayer(Direction.EAST);
		this.gameManager.movePlayer(Direction.DOWN);
		
		assertTrue(this.gameManager.checkIfGameOver());
	}
	
	/**
	 * Tests the checkIfGameOver method with the win flag set
	 */
	@Test
	public void testCheckIfGameOverGameWin() {
        this.gameManager.movePlayer(Direction.NORTH);
        this.gameManager.movePlayer(Direction.EAST);
        this.gameManager.movePlayer(Direction.NORTH);
        this.gameManager.movePlayer(Direction.EAST);
        
        assertTrue(this.gameManager.checkIfGameOver());
	}
}

