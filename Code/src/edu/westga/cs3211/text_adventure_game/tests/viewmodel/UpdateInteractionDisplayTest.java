package edu.westga.cs3211.text_adventure_game.tests.viewmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.text_adventure_game.viewModel.ViewModel;

/**
 * Tests the updateInteractionDisplay method of the ViewModel class
 * 
 * @author Liam Worthington
 * @version Fall 2024
 */
public class UpdateInteractionDisplayTest {

	/**
	 * Tests the updateInteractionDisplay method with new information.
	 */
	@Test
	public void testWhenNewInteraction() {
		ViewModel viewModel = new ViewModel();
		
		viewModel.updateInteractionDisplay("This is new information");
		assertEquals("This is new information", viewModel.interactionDisplayProperty().get());
	}
	
	/**
	 * Tests the updateInteractionDisplay method with blank string.
	 */
	@Test
	public void testWhenBlank() {
		ViewModel viewModel = new ViewModel();
		
		viewModel.updateInteractionDisplay("");
		assertEquals("", viewModel.interactionDisplayProperty().get());
	}
	
	/**
	 * Tests the updateInteractionDisplay method with null.
	 */
	@Test
	public void testWhenNull() {
		ViewModel viewModel = new ViewModel();
		
		viewModel.updateInteractionDisplay(null);
		assertEquals("", viewModel.interactionDisplayProperty().get());
	}
	
	/**
	 * Tests the updateInteractionDisplay method with same interaction already displayed.
	 */
	@Test
	public void testWhenSame() {
		ViewModel viewModel = new ViewModel();
		viewModel.updateInteractionDisplay("This is new information");
		viewModel.updateInteractionDisplay("This is new information");
		assertEquals("", viewModel.interactionDisplayProperty().get());
	}
}
