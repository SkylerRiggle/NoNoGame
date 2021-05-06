import java.io.FileInputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ConfirmAlert extends Alert {

	private static final String STYLE_SHEET = "style.css";
	
	private static final String confirmIcon = "Question.png";
	private static Image confirmImage;
	
	private static final ButtonType CONFIRM_BUTTON = new ButtonType("Confirm");
	
	public ConfirmAlert(AlertType type, String message, EventHandler<ActionEvent> event) {
		super(type, message, CONFIRM_BUTTON, ButtonType.CANCEL);
		
		setTitle(message);
		
		Button confirmButton = (Button) getDialogPane().lookupButton(CONFIRM_BUTTON);
		confirmButton.setOnAction(event);
		
		try {
			confirmImage = new Image(new FileInputStream(Main.getImageLocation() + confirmIcon));
			setGraphic(new ImageView(confirmImage));
		} catch (Exception e) {}
		
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(Main.getAppIcon());
		stage.getScene().getStylesheets().add(STYLE_SHEET);
	}
}
