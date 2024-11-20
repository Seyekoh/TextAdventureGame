package edu.westga.cs3211.text_adventure_game;

import java.io.IOException;

import edu.westga.cs3211.text_adventure_game.view.MainWindow;
import edu.westga.cs3211.text_adventure_game.viewModel.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Entry point for the program​
 *​
 * @author James Bridges​
 * @version Fall 2024​
 */
public class Main extends Application {

	private static final String WINDOW_TITLE = "Text Adventure Game";
	private static final String GUI_RESOURCE = "view/MainWindow.fxml";
	  
	/** JavaFX entry point.​
	  *​
	  * @precondition none​
	  * @postcondition none​
	  *​
	  * @throws IOException unable to load fxml for MainWindow​
	  */
	@Override
	public void start(Stage primaryStage) throws IOException {
		ViewModel viewModel = new ViewModel();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Main.GUI_RESOURCE));
        Parent parent = loader.load();
        MainWindow mainWindow = loader.getController();
        mainWindow.setupView(viewModel);

        Scene scene = new Scene(parent);
        primaryStage.setTitle(Main.WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	/** Primary Java entry point.​
	  *​
	  * @precondition none​
	  * @postcondition none​
	  *​
	  * @param args command line arguments​
	  */
	public static void main(String[] args) {
		Main.launch(args);
	}

}
