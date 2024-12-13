package edu.westga.cs3211.text_adventure_game.viewModel;

import edu.westga.cs3211.text_adventure_game.model.Action;
import edu.westga.cs3211.text_adventure_game.model.GameManager;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.ActionType;
import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The ViewModel for the MainWindow.
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class ViewModel {

	private final GameManager gameManager;
	private Item selectedInventoryItem;

	private StringProperty currentLocationDescription;
	private StringProperty interactionDisplayProperty;
	private StringProperty playerStatus;
	private ListProperty<String> availableActions;
	private ListProperty<Item> inventory;
	private SimpleBooleanProperty isGameOver;

	private String interactionInfo;

	/**
	 * Constructs a new ViewModel object.
	 */
	public ViewModel() {
		this.gameManager = new GameManager();

		this.initializeProperties();
	}

	private void initializeProperties() {
		this.currentLocationDescription = new SimpleStringProperty();
		this.playerStatus = new SimpleStringProperty();
		this.interactionDisplayProperty = new SimpleStringProperty();
		this.availableActions = new SimpleListProperty<>(FXCollections.observableArrayList());
		this.inventory = new SimpleListProperty<>(FXCollections.observableArrayList());
		this.isGameOver = new SimpleBooleanProperty(false);

		this.updateProperties();
	}

	private void updateProperties() {
		this.currentLocationDescription.set(this.gameManager.getCurrentLocationDescription());
		this.interactionDisplayProperty.set(this.gameManager.getInteractionInfo());
		this.playerStatus.set("Health: " + this.gameManager.getPlayer().getHealth());

		this.updateInteractionDisplay(this.gameManager.getInteractionInfo());
		this.updateAvailableActions();
		this.updateInventory();

		this.isGameOver.set(this.gameManager.checkIfGameOver());
	}

	/**
	 * Updates the interaction display
	 * 
	 * @param newInfo the new information to display
	 */
	public void updateInteractionDisplay(String newInfo) {
		if (newInfo == null || newInfo.isBlank() || newInfo.equalsIgnoreCase(this.interactionInfo)) {
			this.clearInteractionDisplay();
		} else {
			this.interactionInfo = newInfo;
			this.interactionDisplayProperty.set(newInfo);
		}
	}

	/**
	 * Clears the interaction display
	 */
	public void clearInteractionDisplay() {
		this.interactionDisplayProperty.set("");
	}

	private void updateAvailableActions() {
		ObservableList<String> actions = FXCollections.observableArrayList();
		this.gameManager.getCurrentLocation().getActions().forEach(action -> actions.add(action.toString()));
		this.availableActions.set(actions);
	}

	private void updateInventory() {
		ObservableList<Item> inventoryItems = FXCollections
				.observableArrayList(this.gameManager.getPlayer().getInventory());
		this.inventory.set(inventoryItems);
	}

	/**
	 * Handles the action button being pressed
	 * 
	 * @param selectedAction the selected action to be handled
	 */
	public void handleActionButtonPressed(String selectedAction) {
		String[] actionDetails = selectedAction.split(": ");
		Action action = null;
		for (Action currentAction : this.gameManager.getAllAvailableActions()) {
			if (currentAction.toString().equalsIgnoreCase(selectedAction)) {
				action = currentAction;
				break;
			}
		}

		if (action == null) {
			System.out.println("Action not found: " + actionDetails[0]);
		} else {
			if (action.getType() == ActionType.TAKE) {
				this.gameManager.performAction(action, Item.valueOf(actionDetails[1]));
			} else if (this.selectedInventoryItem != null) {
				System.out.println("Performing action: " + action + " with item: " + this.selectedInventoryItem);
				this.gameManager.performAction(action, this.selectedInventoryItem);
			} else {
				this.gameManager.performAction(action, Item.NONE);
			}
		}

		this.updateProperties();
	}

	/**
	 * Gets the currentLocationDescriptionProperty
	 * 
	 * @return the currentLocationDescriptionProperty
	 */
	public StringProperty currentLocationDescriptionProperty() {
		return this.currentLocationDescription;
	}

	/**
	 * Gets the interactionDisplayProperty
	 * 
	 * @return the interactionDisplayProperty
	 */
	public StringProperty interactionDisplayProperty() {
		return this.interactionDisplayProperty;
	}

	/**
	 * Gets the playerStatusProperty
	 * 
	 * @return the playerStatusProperty
	 */
	public StringProperty playerStatusProperty() {
		return this.playerStatus;
	}

	/**
	 * Gets the availableActionsProperty
	 * 
	 * @return the availableActionsProperty
	 */
	public ListProperty<String> availableActionsProperty() {
		return this.availableActions;
	}

	/**
	 * Gets the availableActionsDescriptionsProperty
	 * 
	 * @return the availableActionsDescriptionsProperty
	 */
	public ListProperty<String> availableActionsDescriptionsProperty() {
		return this.availableActions;
	}

	/**
	 * Gets the inventoryProperty
	 * 
	 * @return the inventoryProperty
	 */
	public ListProperty<Item> inventoryProperty() {
		return this.inventory;
	}

	/**
	 * Gets the isGameOverProperty
	 * 
	 * @return the isGameOverProperty
	 */
	public BooleanProperty isGameOverProperty() {
		return this.isGameOver;
	}

	/**
	 * Sets the current selected item
	 * 
	 * @param item the selected item
	 */
	public void setItem(Item item) {
		this.selectedInventoryItem = item;
	}
}
