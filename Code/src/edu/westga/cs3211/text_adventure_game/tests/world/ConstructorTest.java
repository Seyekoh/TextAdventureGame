package edu.westga.cs3211.text_adventure_game.tests.world;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.World;

/**
 * Tests the constructor for the World class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class ConstructorTest {
	
	private World world;
	
	/**
	 * Tests the constructor for the World class
	 */
	@Test
	public void testWorldConstructorValid() {
		this.world = new World();
		assertEquals(0, this.world.getAllLocations().size());
    }

}
