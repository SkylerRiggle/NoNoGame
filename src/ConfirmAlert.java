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
 * The confirm alert is displayed to get confirmation from the player that they
 * want to continue with an action.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class ConfirmAlert extends Alert {

	private static final String STYLE_SHEET = "style.css";
	
	private static final String confirmIcon = "Question.png";
	private static Image confirmImage;
	
	private static final ButtonType CONFIRM_BUTTON = new ButtonType("Confirm");
	
	
	/**
	 * This constructor creates the confirm alert and all of its elements.
	 * 
	 * @param type The type for this alert.
	 * @param message The message that will be displayed by this alert.
	 * @param event The event to be executed if the player confirms the action.
	 */
	public ConfirmAlert(AlertType type, String message, EventHandler<ActionEvent> event) {
		super(type, message, CONFIRM_BUTTON, ButtonType.CANCEL);
		
		//Set the title text.
		setTitle(message);
		
		//The confirm button executes the action passed to the constructor
		Button confirmButton = (Button) getDialogPane().lookupButton(CONFIRM_BUTTON);
		confirmButton.setOnAction(event);
		
		//Get and set the graphic for this alert.
		try {
			confirmImage = new Image(new FileInputStream(Main.getImageLocation() + confirmIcon));
			setGraphic(new ImageView(confirmImage));
		} catch (Exception e) {}
		
		//Set the alert icon and style sheet.
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(Main.getAppIcon());
		stage.getScene().getStylesheets().add(STYLE_SHEET);
	}
}
