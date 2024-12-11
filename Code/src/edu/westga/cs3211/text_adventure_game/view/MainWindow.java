package edu.westga.cs3211.text_adventure_game.view;

import edu.westga.cs3211.text_adventure_game.model.GlobalEnums.Item;
import edu.westga.cs3211.text_adventure_game.viewModel.ViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/** Codebehind for the Main Window of the application.
 * 
 * @author James Bridges
 * @version Fall 2024
 */
public class MainWindow {
	private ViewModel viewModel;
	
	@FXML
	private TextArea currentLocationDescriptionTextArea;
	
	@FXML
	private TextArea interactionDisplayTextArea;
	
	@FXML
	private ListView<String> availableActionDescriptionsListView;
	
	@FXML
	private TextArea playerStatusTextArea;
	
	@FXML
	private ListView<Item> inventoryListView;
	
	@FXML
	private ComboBox<String> actionsComboBox;
	
	@FXML
	private Button takeActionButton;
	
	/**
	 * Sets the ViewModel and instantiates the bindings
	 * 
	 * @param viewModel	the ViewModel
	 */
	public void setupView(ViewModel viewModel) {
		this.viewModel = viewModel;
		
		this.bindTextAreaProperties();
		this.bindListViewProperties();
		this.bindComboBoxProperties();		
		this.addChangeListeners();
		this.bindButtonDisableProperty();
	}
	
	@FXML
	private void onTakeActionButtonClicked() {
        String selectedAction = this.actionsComboBox.getSelectionModel().getSelectedItem();
        if (selectedAction != null) {
            this.viewModel.handleActionButtonPressed(selectedAction);
            this.inventoryListView.getSelectionModel().clearSelection();
        }
	}
	
	private void bindTextAreaProperties() {
		this.currentLocationDescriptionTextArea.textProperty().bind(this.viewModel.currentLocationDescriptionProperty());
		this.interactionDisplayTextArea.textProperty().bind(this.viewModel.interactionDisplayProperty());
		this.playerStatusTextArea.textProperty().bind(this.viewModel.playerStatusProperty());
	}
	
	private void bindListViewProperties() {
		this.availableActionDescriptionsListView.itemsProperty().bind(this.viewModel.availableActionsDescriptionsProperty());
		this.inventoryListView.itemsProperty().bind(this.viewModel.inventoryProperty());
	}
	
	private void bindComboBoxProperties() {
		this.actionsComboBox.itemsProperty().bind(this.viewModel.availableActionsProperty());
	}
	
	private void addChangeListeners() {
		javafx.application.Platform.runLater(() -> {
			if (!this.actionsComboBox.getItems().isEmpty()) {
                this.actionsComboBox.getSelectionModel().select(0);
            }
		});
		
		this.viewModel.availableActionsProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && !newValue.isEmpty()) {
				this.actionsComboBox.getSelectionModel().select(0);
			}
		});
		
		this.inventoryListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
            	this.viewModel.setItem(newValue);
            }
		});
	}
	
	private void bindButtonDisableProperty() {
		this.takeActionButton.disableProperty().bind(this.viewModel.isGameOverProperty());
	}

}
