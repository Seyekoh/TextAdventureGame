package edu.westga.cs3211.text_adventure_game.tests.world;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.Location;
import edu.westga.cs3211.text_adventure_game.model.World;

/**
 * Tests the World class's getStartLocation method
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class GetStartLocationTest {
	
	/**
	 * Tests the getStartLocation method
	 */
	@Test
	public void testGetStartLocation() {
		World world = new World();
		Location location1 = new Location(GlobalEnums.LocationName.ENTRANCEHALL, "Entrance hall", HazardType.NONE, false, new ArrayList<>());
		world.setStartLocation(location1);
		
		assertEquals(GlobalEnums.LocationName.ENTRANCEHALL, world.getStartLocation().getName());
	}

}
