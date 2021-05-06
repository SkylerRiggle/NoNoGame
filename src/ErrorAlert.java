import java.io.FileInputStream;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * The error alert informs the player that an operation has unsuccessfully occurred.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class ErrorAlert extends Alert {

	private static final String STYLE_SHEET = "style.css";
	
	private static final String errorIcon = "Cancel.png";
	private static Image errorImage;
	
	
	/**
	 * This constructor creates the success alert and all of its elements.
	 * 
	 * @param type The type for this alert.
	 * @param message The message that will be displayed by this alert.
	 */
	public ErrorAlert(AlertType type, String message) {
		super(type, message);
		
		//Set the title.
		setTitle(message);
		
		//Get and set the graphic for this alert.
		try {
			errorImage = new Image(new FileInputStream(Main.getImageLocation() + errorIcon));
			setGraphic(new ImageView(errorImage));
		} catch (Exception e) {}
		
		//Set the alert icon and style sheet.
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(Main.getAppIcon());
		stage.getScene().getStylesheets().add(STYLE_SHEET);
	}
}
