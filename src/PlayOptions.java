
import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * The play options menu is where the player decides what puzzle they will play, whether it be created or
 * generated, and proceed to do so.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class PlayOptions extends BorderPane {
	
	private static final int SPACING = 10;
	private static final int PADDING = 20;
	
	private static final String PADDING_STYLE = "HBox-filled";
	
	private static Label puzzleListLabel = new Label("Puzzles:");
	private static ComboBox<String> puzzleList = new ComboBox<String>();
	
	private static Button playButton = new Button("Play");
	private static Button randomPuzzleButton = new Button("Play Random Puzzle");
	private static Button backButton = new Button("Back");
	
	
	/**
	 * This constructor creates a new Play-Options menu with all of its elements.
	 * 
	 * @param main A reference to the Main class for this application.
	 */
	public PlayOptions(Main main) {
		super();

		//Get the puzzle files.
		ObservableList<String> puzzleFiles = getPuzzleFiles(Main.getPuzzleLocation());
		puzzleList.setItems(puzzleFiles);
		
		//Set the default value of the list to its first value.
		if (puzzleFiles.size() > 0) {
			puzzleList.setValue(puzzleFiles.get(0));
		}
		
		//The play button loads the selected puzzle into the game.
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				PlayModel playModel = new PlayModel(puzzleList.getValue());
				
				startPlayer(playModel, main);
			}
		});
		
		//The random puzzle buttons starts the game with a randomly generated puzzle.
		randomPuzzleButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				PlayModel playModel = new PlayModel();
				
				startPlayer(playModel, main);
			}
		});
		
		//The back button returns the player to the main menu.
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Main.setScene(new MainMenu(main));
			}
		});
		
		//This VBox contains the puzzle drop-down list and label.
		VBox puzzles = new VBox(puzzleListLabel, puzzleList);
		puzzles.setAlignment(Pos.CENTER);
		puzzles.setSpacing(SPACING);
		puzzles.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
		
		//This VBox contains each button on the options menu.
		VBox buttons = new VBox(playButton, randomPuzzleButton, backButton);
		buttons.setAlignment(Pos.CENTER_RIGHT);
		buttons.setSpacing(SPACING);
		buttons.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
		
		//The region that sets the height of the top bar.
		Region topSpace = new Region();
		topSpace.setMaxHeight(PADDING);
		topSpace.setMinHeight(PADDING);
		topSpace.setPrefHeight(PADDING);
		
		//Top bar for aesthetic purposes.
		HBox topBar = new HBox(topSpace);
		topBar.getStyleClass().add(PADDING_STYLE);
		
		//Add the elements to the options menu.
		setBottom(topBar);
		setLeft(puzzles);
		setCenter(buttons);
	}
	
	/**
	 * This method gathers all of the puzzles contained in a set directory.
	 * 
	 * @param directory The location for the puzzles.
	 * @return A list of all of the puzzle files in a directory.
	 */
	private ObservableList<String> getPuzzleFiles(String directory) {
		ObservableList<String> result = FXCollections.observableArrayList();
		
		File fileLocation = new File(directory);
		String[] allFiles = fileLocation.list();
		
		//Check for which files are puzzles (i.e. they contain the correct suffix.).
		for (String file : allFiles) {
			if (file.contains(Main.getPuzzleFileType())) {
				result.add(file);
			}
		}
		
		return result;
	}

	/**
	 * This method starts and displays the games graphical elements.
	 * 
	 * @param playModel The model containing the game data.
	 * @param main A reference to the Main class of this application.
	 */
	private void startPlayer(PlayModel playModel, Main main) {
		//Create and register the puzzle view.
		PlayView playView = new PlayView(playModel.getRowClues(), playModel.getColClues(), main);
		playView.register(new PlayPresenter(playModel, playView));
		
		Main.setScene(playView);
	}
}
