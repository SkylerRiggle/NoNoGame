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
 * This Alert shows the player that they have solved the puzzle and how it took to do so.
 * This Alert also presents the player with the options to either play the puzzle again, or
 * return to the Play-Options menu.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class WinAlert extends Alert {

	private static final String STYLE_SHEET = "style.css";
	
	private static final int MILI_TO_SECONDS = 1000;
	private static final int SECONDS_TO_MINUTES = 60;
	
	private static final String winIcon = "Win.png";
	private static Image winImage;
	
	private static final ButtonType REPLAY_BUTTON = new ButtonType("Play Again");
	private static final ButtonType EXIT_BUTTON = new ButtonType("Return");
	
	
	/**
	 * This constructor creates the win alert and all of its elements.
	 * 
	 * @param type The type for this alert.
	 * @param message The message that will be displayed by this alert.
	 * @param main A reference to the main class for this application.
	 */
	public WinAlert(AlertType type, String message, Main main) {
		super(type, "Time to complete: " + getDisplayTime(), REPLAY_BUTTON, EXIT_BUTTON);

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
			}
		});
		
		//Get and set the graphic for this alert.
		try {
			winImage = new Image(new FileInputStream(Main.getImageLocation() + winIcon));
			setGraphic(new ImageView(winImage));
		} catch (Exception e) {}
		
		//Set the alert icon and style sheet.
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(Main.getAppIcon());
		stage.getScene().getStylesheets().add(STYLE_SHEET);
	}
	
	/**
	 * This method returns the time it took to solve the puzzle in minutes and seconds.
	 * 
	 * @return The time in minutes and seconds that it took to solve the puzzle.
	 */
	private static String getDisplayTime() {
		//Get the time in purely seconds.
		int rawSeconds = (int) (PlayModel.getTotalTime() / MILI_TO_SECONDS);
		
		//Convert the raw amount of seconds into minutes and seconds.
		int minutes = Math.floorDiv(rawSeconds, SECONDS_TO_MINUTES);
		int seconds = rawSeconds % SECONDS_TO_MINUTES;
		
		//Return the minute and second values with two decimal places.
		return String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
	}
}
