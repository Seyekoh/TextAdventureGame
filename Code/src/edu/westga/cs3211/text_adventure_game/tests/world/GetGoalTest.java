package edu.westga.cs3211.text_adventure_game.tests.world;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.Location;
import edu.westga.cs3211.text_adventure_game.model.World;

/**
 * Tests the World class's getGoal method
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GetGoalTest {
	
	/**
	 * Tests the getGoal method
	 */
	@Test
	public void testGetGoal() {
		World world = new World();
		Location location = new Location(GlobalEnums.LocationName.EXIT, "The exit", HazardType.NONE, true, new ArrayList<>(), Item.NONE);
		world.addLocation(location);
		world.setGoalLocation(location);
		assertEquals(location, world.getGoalLocation());
	}

}
