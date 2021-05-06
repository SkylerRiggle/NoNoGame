import java.io.FileInputStream;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SuccessAlert extends Alert {

	private static final String STYLE_SHEET = "style.css";
	
	private static final String successIcon = "Success.png";
	private static Image successImage;
	
	public SuccessAlert(AlertType type, String message) {
		super(type, message, ButtonType.OK);

		setHeaderText(message);
		setTitle(message);
		
		try {
			successImage = new Image(new FileInputStream(Main.getImageLocation() + successIcon));
			setGraphic(new ImageView(successImage));
		} catch (Exception e) {}
		
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(Main.getAppIcon());
		stage.getScene().getStylesheets().add(STYLE_SHEET);
	}
}
