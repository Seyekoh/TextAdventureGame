package edu.westga.cs3211.text_adventure_game.tests.location;

import static org.junit.jupiter.api.Assertions.assertAll;
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
 * Tests the addItem method in the Location class
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class AddItemTest {
	
	private Location location = new Location(LocationName.ENTRANCEHALL, "A dark room", HazardType.NONE, false, new ArrayList<Action>(), Item.NONE);

	/**
	 * Tests the addItem method
	 */
	@Test
	public void testAddItem() {
		this.location.addItem(Item.DIARY);
		assertAll(() -> assertEquals(1, this.location.getItems().size()),
				() -> assertEquals(Item.DIARY, this.location.getItems().get(0)));
	}
	
	/**
	 * Tests the addItem method with a null item
	 */
	@Test
	public void testAddItemWithNullItem() {
		assertThrows(IllegalArgumentException.class, () -> {
			this.location.addItem(null);
		});
	}
}
