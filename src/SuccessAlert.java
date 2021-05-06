import java.io.FileInputStream;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * The success alert displays to the player that an operation has successfully occured.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class SuccessAlert extends Alert {

	private static final String STYLE_SHEET = "style.css";
	
	private static final String successIcon = "Success.png";
	private static Image successImage;
	
	
	/**
	 * This constructor creates the success alert and all of its elements.
	 * 
	 * @param type The type for this alert.
	 * @param message The message that will be displayed by this alert.
	 */
	public SuccessAlert(AlertType type, String message) {
		super(type, message, ButtonType.OK);

		//Set the title and header text.
		setHeaderText(message);
		setTitle(message);
		
		//Get and set the graphic for this alert.
		try {
			successImage = new Image(new FileInputStream(Main.getImageLocation() + successIcon));
			setGraphic(new ImageView(successImage));
		} catch (Exception e) {}
		
		//Set the alert icon and style sheet.
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(Main.getAppIcon());
		stage.getScene().getStylesheets().add(STYLE_SHEET);
	}
}
