package edu.westga.cs3211.text_adventure_game.viewModel;

import edu.westga.cs3211.text_adventure_game.model.GameManager;
import javafx.beans.property.ListProperty;
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
	
	private StringProperty currentLocationDescription;
	private StringProperty availableActionsDescriptions;
	private StringProperty playerStatus;
	private ListProperty<String> availableActions;
	
	/**
	 * Constructs a new ViewModel object.
	 */
	public ViewModel() {
		this.gameManager = new GameManager();
		
		this.initializeProperties();
	}
	
	private void initializeProperties() {
		this.currentLocationDescription = new SimpleStringProperty();
		this.availableActionsDescriptions = new SimpleStringProperty();
		this.playerStatus = new SimpleStringProperty();
		this.availableActions = new SimpleListProperty<>(FXCollections.observableArrayList());
		
		this.updateProperties();
	}
	
	private void updateProperties() {
		this.currentLocationDescription.set(this.gameManager.getCurrentLocation().getDescription());
		this.playerStatus.set("Health: " + this.gameManager.getPlayer().getHealth());
		
		this.updateAvailableActions();
	}
	
	private void updateAvailableActions() {
		ObservableList<String> actions = FXCollections.observableArrayList();
		this.gameManager.getCurrentLocation().getActions().forEach(action -> actions.add(action.toString()));
		this.availableActions.set(actions);
		
		StringBuilder formattedActions = new StringBuilder();
		for (String action : actions) {
			formattedActions.append(action).append("\n");
		}
		this.availableActionsDescriptions.set(formattedActions.toString().trim());
	}
	
	/**
	 * Handles the action button being pressed
	 * 
	 * @param selectedAction	the selected action to be handled
	 */
	public void handleActionButtonPressed(String selectedAction) {
		this.gameManager.getAllAvailableActions().stream()
				.filter(action -> action.toString().equals(selectedAction))
				.findFirst()
				.ifPresent(action -> {
					this.gameManager.performAction(action);
				});
		
		this.updateProperties();
	}
	
	/**
	 * Gets the currentLocationDescriptionProperty
	 * 
	 * @return	the currentLocationDescriptionProperty
	 */
	public StringProperty currentLocationDescriptionProperty() {
		return this.currentLocationDescription;
	}
	
	/**
	 * Gets the availableActionsDescriptionsProperty
	 * 
	 * @return the availableActionsDescriptionsProperty
	 */
	public StringProperty availableActionsDescriptionsProperty() {
		return this.availableActionsDescriptions;
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
}
