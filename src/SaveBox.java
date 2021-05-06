import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;

public class SaveBox extends HBox {
	
	private static final int SPACING = 10;
	
	private static Button saveButton = new Button("Save Puzzle");
	private static Button backButton = new Button("Back");
	
	private String filename;
	private CreateModel model;
	private Main main;
	
	public SaveBox(String filename, CreateModel model, Main main) {
		super(saveButton, backButton);
		
		this.filename = filename;
		this.model = model;
		this.main = main;
		
		setSpacing(SPACING);
		setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
		setAlignment(Pos.CENTER);
		
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				save();
			}
		});
		
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				back();
			}
		});
	}
	
	private void save() {
		try {
			model.save(filename);
			
			String message = "Your puzzle has been saved.";
			SuccessAlert successAlert = new SuccessAlert(AlertType.CONFIRMATION, message);
			successAlert.show();
			
			Main.setScene(new CreateOptions(main));
		} catch (IOException e) {
			String message = "Could not save puzzle.";
			ErrorAlert errorAlert = new ErrorAlert(AlertType.ERROR, message);
			errorAlert.show();
		}
	}
	
	private void back() {
		String message = "Are you sure you want to exit?" + System.lineSeparator() + "Your progress will not be saved.";
		ConfirmAlert confirmAlert = new ConfirmAlert(AlertType.CONFIRMATION, message, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Main.setScene(new CreateOptions(main));
			}
		});
		confirmAlert.show();
	}
}
