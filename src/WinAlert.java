import java.io.FileInputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class WinAlert extends Alert {

	private static final String STYLE_SHEET = "style.css";
	
	private static final int MILI_TO_SECONDS = 1000;
	private static final int SECONDS_TO_MINUTES = 60;
	
	private static final String winIcon = "Win.png";
	private static Image winImage;
	
	private static final ButtonType REPLAY_BUTTON = new ButtonType("Play Again");
	private static final ButtonType EXIT_BUTTON = new ButtonType("Return");
	
	public WinAlert(AlertType type, String message, Main main) {
		super(type, "Time to complete: " + getDisplayTime(), REPLAY_BUTTON, EXIT_BUTTON);

		setHeaderText(message);
		setTitle(message);
		
		Button replayButton = (Button) getDialogPane().lookupButton(REPLAY_BUTTON);
		replayButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				PlayView.resetPlay();
				PlayPresenter.reset();
			}
		});
		
		Button exitButton = (Button) getDialogPane().lookupButton(EXIT_BUTTON);
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Main.setScene(new PlayOptions(main));
			}
		});
		
		try {
			winImage = new Image(new FileInputStream(Main.getImageLocation() + winIcon));
			setGraphic(new ImageView(winImage));
		} catch (Exception e) {}
		
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(Main.getAppIcon());
		stage.getScene().getStylesheets().add(STYLE_SHEET);
	}
	
	private static String getDisplayTime() {
		int rawSeconds = (int) (PlayModel.getTotalTime() / MILI_TO_SECONDS);
		int minutes = Math.floorDiv(rawSeconds, SECONDS_TO_MINUTES);
		int seconds = rawSeconds % SECONDS_TO_MINUTES;
		
		return String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
	}
}
