import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;

/**
 * The save box appears at the bottom of the create view and contains the buttons
 * for saving the puzzle and returning to the Create-Options menu.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class SaveBox extends HBox {
	
	private static final int SPACING = 10;
	
	private static Button saveButton = new Button("Save Puzzle");
	private static Button backButton = new Button("Back");
	
	private String filename;
	private CreateModel model;
	private Main main;
	
	
	/**
	 * This method constructs the save box with all of its elements.
	 * 
	 * @param filename The desired filename for the puzzle.
	 * @param model The model containing the puzzle data.
	 * @param main A reference to the Main class for this application.
	 */
	public SaveBox(String filename, CreateModel model, Main main) {
		super(saveButton, backButton);
		
		//Save the filename, model, and main values to be used locally.
		this.filename = filename;
		this.model = model;
		this.main = main;
		
		//Set the spacing and alignment for this HBox.
		setSpacing(SPACING);
		setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
		setAlignment(Pos.CENTER);
		
		//The save button saves the puzzle the player has created.
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				save();
			}
		});
		
		//The back button returns the user to the Create-Options menu.
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				back();
			}
		});
	}
	
	/**
	 * This method saves the data contained in the model to a filename of the player's choice.
	 */
	private void save() {
		try {
			//Save the file data in the model.
			model.save(filename);
			
			//Display a success alert to the player.
			String message = "Your puzzle has been saved.";
			SuccessAlert successAlert = new SuccessAlert(AlertType.CONFIRMATION, message);
			successAlert.show();
			
			//Return to the Create-Options menu.
			Main.setScene(new CreateOptions(main));
		} catch (IOException e) {
			//If an error occurs, display an error alert to the player.
			String message = "Could not save puzzle.";
			ErrorAlert errorAlert = new ErrorAlert(AlertType.ERROR, message);
			errorAlert.show();
		}
	}
	
	/**
	 * This method first gets confirmation from the player, then returns the player to
	 * the Create-Options menu.
	 */
	private void back() {
		//First get confirmation that the player wants to leave, 
		//then return to the Create-Options menu.
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
