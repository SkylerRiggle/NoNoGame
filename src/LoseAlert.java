import java.io.FileInputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoseAlert extends Alert {

	private static final String STYLE_SHEET = "style.css";
	
	private static final String lossIcon = "Loss.png";
	private static Image lossImage;
	
	private static final ButtonType REPLAY_BUTTON = new ButtonType("Play Again");
	private static final ButtonType EXIT_BUTTON = new ButtonType("Return");
	
	public LoseAlert(AlertType type, String message, Main main) {
		super(type, message, REPLAY_BUTTON, EXIT_BUTTON);
		
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
			lossImage = new Image(new FileInputStream(Main.getImageLocation() + lossIcon));
			setGraphic(new ImageView(lossImage));
		} catch (Exception e) {}
		
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(Main.getAppIcon());
		stage.getScene().getStylesheets().add(STYLE_SHEET);
	}
}
