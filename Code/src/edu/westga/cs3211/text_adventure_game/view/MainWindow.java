package edu.westga.cs3211.text_adventure_game.view;

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
	
	@FXML
	private TextArea currentLocationDescriptionTextArea;
	
	@FXML
	private TextArea availableActionDescriptionTextArea;
	
	@FXML
	private ComboBox<String> actionsComboBox;
	
	@FXML
	private Button takeActionButton;
	
	/**
	 * Initializes the MainWindow.
	 * 
	 */
	@FXML
	public void initialize() {
   // TODO document why this method is empty
 }
	
	@FXML
	private void onTakeActionButtonClicked() {
   // TODO document why this method is empty
 }

}
