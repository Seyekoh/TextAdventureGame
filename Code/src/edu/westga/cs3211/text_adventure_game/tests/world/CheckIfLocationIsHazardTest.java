package edu.westga.cs3211.text_adventure_game.tests.world;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums;
import edu.westga.cs3211.text_adventure_game.model.Location;
import edu.westga.cs3211.text_adventure_game.model.World;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;

/**
 * Tests the World class's checkIfLocationIsHazard method
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class CheckIfLocationIsHazardTest {
	private World world;
	private Location location1;
	private Location location2;
	private Location location3;
	private Location location4;
	
	/**
	 * Sets up the instance variables for each test
	 */
	@BeforeEach
	public void setUp() {
        this.world = new World();
        this.location1 = new Location(GlobalEnums.LocationName.ENTRANCEHALL, "Entrance hall", HazardType.NONE, false, new ArrayList<>(), Item.NONE);
        this.location2 = new Location(GlobalEnums.LocationName.BALLROOM, "A ballroom", HazardType.DANCINGSHADOWS, false, new ArrayList<>(), Item.NONE);
        this.location3 = new Location(GlobalEnums.LocationName.KITCHEN, "A kitchen", HazardType.NONE, false, new ArrayList<>(), Item.NONE);
        this.location4 = new Location(GlobalEnums.LocationName.EXIT, "The exit", HazardType.NONE, true, new ArrayList<>(), Item.NONE);
	}
	
	/**
	 * Tests the checkIfLocationIsHazard method with a location that is not a hazard
	 */
	@Test
	public void testCheckIfLocationIsHazardNotHazard() {
		this.world.addLocation(this.location1);

		assertFalse(this.world.checkIfLocationIsHazard(this.location1));
	}
	
	/**
	 * Tests the checkIfLocationIsHazard method with a location that is a hazard
	 */
	@Test
	public void testCheckIfLocationIsHazardIsHazard() {
		this.world.addLocation(this.location2);

		assertTrue(this.world.checkIfLocationIsHazard(this.location2));
	}
}
