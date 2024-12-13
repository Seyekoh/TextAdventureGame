package edu.westga.cs3211.text_adventure_game.tests.viewmodel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.viewModel.ViewModel;

/**
 * Tests the constructor of the ViewModel class
 * 
 * @author Liam Worthington
 * @version Fall 2024
 */
public class ConstructorTest {

	/**
	 * Tests the ViewModel constructor for valid ViewModel construction.
	 */
	@Test
	public void testConstructorValid() {
		ViewModel viewModel = new ViewModel();
		
		assertAll(() -> {
			assertEquals("A grand hall with dusty chandeliers and an eerie silence.", viewModel.currentLocationDescriptionProperty().get());
			assertEquals("", viewModel.interactionDisplayProperty().get());
			assertEquals("Health: 100", viewModel.playerStatusProperty().get());
			assertEquals(0, viewModel.inventoryProperty().size());
			assertEquals(5, viewModel.availableActionsDescriptionsProperty().size());
			assertEquals(5, viewModel.availableActionsProperty().size());
			assertFalse(viewModel.isGameOverProperty().get());
		});
	}
}
