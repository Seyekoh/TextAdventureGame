package edu.westga.cs3211.text_adventure_game.view;

import edu.westga.cs3211.text_adventure_game.viewModel.ViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
	private TextArea availableActionDescriptionsTextArea;
	
	@FXML
	private TextArea playerStatusTextArea;
	
	@FXML
	private ComboBox<String> actionsComboBox;
	
	@FXML
	private Button takeActionButton;
	
	@FXML
	private void onTakeActionButtonClicked() {
        String selectedAction = this.actionsComboBox.getSelectionModel().getSelectedItem();
        this.viewModel.handleActionButtonPressed(selectedAction);
	}
	
	/**
	 * Sets the ViewModel and instantiates the bindings
	 * 
	 * @param viewModel	the ViewModel
	 */
	public void setupView(ViewModel viewModel) {
		this.viewModel = viewModel;
		
		this.bindTextAreaProperties();
		this.bindComboBoxProperties();
		
		this.addChangeListenerToComboBox();
	}
	
	private void bindTextAreaProperties() {
		this.currentLocationDescriptionTextArea.textProperty().bind(this.viewModel.currentLocationDescriptionProperty());
		this.availableActionDescriptionsTextArea.textProperty().bind(this.viewModel.availableActionsDescriptionsProperty());
		this.playerStatusTextArea.textProperty().bind(this.viewModel.playerStatusProperty());
	}
	
	private void bindComboBoxProperties() {
		this.actionsComboBox.itemsProperty().bind(this.viewModel.availableActionsProperty());
	}
	
	private void addChangeListenerToComboBox() {
		javafx.application.Platform.runLater(() -> {
			if (!this.actionsComboBox.getItems().isEmpty()) {
                this.actionsComboBox.getSelectionModel().select(0);
            }
		});
	}

}
