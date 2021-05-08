import java.io.FileInputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * The lose alert is displayed when the player officially gives up on a puzzle and
 * gives them the options to replay the puzzle or return to the Play-Options menu.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class LoseAlert extends Alert {

	private static final String STYLE_SHEET = "style.css";
	
	private static final String lossIcon = "Loss.png";
	private static Image lossImage;
	
	private static final ButtonType REPLAY_BUTTON = new ButtonType("Play Again");
	private static final ButtonType EXIT_BUTTON = new ButtonType("Return");
	
	
	/**
	 * This constructor creates the lose alert and all of its elements.
	 * 
	 * @param type The type for this alert.
	 * @param message The message that will be displayed by this alert.
	 * @param main A reference to the main class for this application.
	 */
	public LoseAlert(AlertType type, String message, Main main) {
		super(type, message, REPLAY_BUTTON, EXIT_BUTTON);
		
		//Set the title and header text.
		setHeaderText(message);
		setTitle(message);
		
		//The replay button resets the puzzle so that the player can replay the puzzle.
		Button replayButton = (Button) getDialogPane().lookupButton(REPLAY_BUTTON);
		replayButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				PlayView.resetPlay();
				PlayPresenter.reset();
			}
		});
		
		//The exit button returns the player to the Play-Options menu.
		Button exitButton = (Button) getDialogPane().lookupButton(EXIT_BUTTON);
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Main.setScene(new PlayOptions(main));
				PlayView.resetPlay();
			}
		});
		
		//Get and set the graphic for this alert.
		try {
			lossImage = new Image(new FileInputStream(Main.getImageLocation() + lossIcon));
			setGraphic(new ImageView(lossImage));
		} catch (Exception e) {}
		
		//Set the alert icon and style sheet.
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(Main.getAppIcon());
		stage.getScene().getStylesheets().add(STYLE_SHEET);
	}
}
