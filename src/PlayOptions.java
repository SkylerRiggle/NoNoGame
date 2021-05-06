
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

public class PlayOptions extends BorderPane {
	
	private static final int SPACING = 10;
	private static final int PADDING = 20;
	
	private static final String PADDING_STYLE = "HBox-filled";
	
	private static Label puzzleListLabel = new Label("Puzzles:");
	private static ComboBox<String> puzzleList = new ComboBox<String>();
	
	private static Button playButton = new Button("Play");
	private static Button randomPuzzleButton = new Button("Play Random Puzzle");
	private static Button backButton = new Button("Back");
	
	public PlayOptions(Main main) {
		super();

		ObservableList<String> puzzleFiles = getPuzzleFiles(Main.getPuzzleLocation());
		puzzleList.setItems(puzzleFiles);
		
		if (puzzleFiles.size() > 0) {
			puzzleList.setValue(puzzleFiles.get(0));
		}
		
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				PlayModel playModel = new PlayModel(puzzleList.getValue());
				
				startPlayer(playModel, main);
			}
		});
		
		randomPuzzleButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				PlayModel playModel = new PlayModel();
				
				startPlayer(playModel, main);
			}
		});
		
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Main.setScene(new MainMenu(main));
			}
		});
		
		VBox puzzles = new VBox(puzzleListLabel, puzzleList);
		puzzles.setAlignment(Pos.CENTER);
		puzzles.setSpacing(SPACING);
		puzzles.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
		
		VBox buttons = new VBox(playButton, randomPuzzleButton, backButton);
		buttons.setAlignment(Pos.CENTER_RIGHT);
		buttons.setSpacing(SPACING);
		buttons.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
		
		Region topSpace = new Region();
		topSpace.setMaxHeight(PADDING);
		topSpace.setMinHeight(PADDING);
		topSpace.setPrefHeight(PADDING);
		
		HBox topBar = new HBox(topSpace);
		topBar.getStyleClass().add(PADDING_STYLE);
		
		setBottom(topBar);
		setLeft(puzzles);
		setCenter(buttons);
	}
	
	private ObservableList<String> getPuzzleFiles(String directory) {
		ObservableList<String> result = FXCollections.observableArrayList();
		
		File fileLocation = new File(directory);
		String[] allFiles = fileLocation.list();
		
		for (String file : allFiles) {
			if (file.contains(Main.getPuzzleFileType())) {
				result.add(file);
			}
		}
		
		return result;
	}

	private void startPlayer(PlayModel playModel, Main main) {
		PlayView playView = new PlayView(playModel.getRowClues(), playModel.getColClues(), main);
		playView.register(new PlayPresenter(playModel, playView));
		
		Main.setScene(playView);
	}
}
