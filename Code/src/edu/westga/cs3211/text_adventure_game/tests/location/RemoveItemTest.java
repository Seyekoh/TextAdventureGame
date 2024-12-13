package edu.westga.cs3211.text_adventure_game.tests.location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.model.Action;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.HazardType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.LocationName;
import edu.westga.cs3211.text_adventure_game.model.Location;

/**
 * Tests the removeItem method in the Location class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class RemoveItemTest {
	
	private Location location = new Location(LocationName.ENTRANCEHALL, "A dark room", HazardType.NONE, false, new ArrayList<Action>(), Item.NONE);

	
	/**
	 * Tests the removeItem method
	 */
	@Test
	public void testRemoveItem() {
		this.location.addItem(Item.DIARY);
		this.location.removeItem(Item.DIARY);
		assertEquals(0, this.location.getItems().size());
	}
	
	/**
	 * Tests the removeItem method with a null item
	 */
	@Test
	public void testRemoveItemWithNullItem() {
		assertThrows(IllegalArgumentException.class, () -> {
			this.location.removeItem(null);
		});
	}
}
