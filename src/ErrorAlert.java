import java.io.FileInputStream;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ErrorAlert extends Alert {

	private static final String STYLE_SHEET = "style.css";
	
	private static final String errorIcon = "Cancel.png";
	private static Image errorImage;
	
	public ErrorAlert(AlertType type, String message) {
		super(type, message);
		
		setTitle(message);
		
		try {
			errorImage = new Image(new FileInputStream(Main.getImageLocation() + errorIcon));
			setGraphic(new ImageView(errorImage));
		} catch (Exception e) {}
		
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(Main.getAppIcon());
		stage.getScene().getStylesheets().add(STYLE_SHEET);
	}
}
