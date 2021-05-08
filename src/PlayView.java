import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * The play view displays all of the game-play elements to the player, including a cell grid, the
 * row and column clues, and a give up button.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class PlayView extends BorderPane implements View {

	private static final String MODEL_STYLE = "nonogram-view";
	private static final String SOLVED_STYLE = "nonogram-view-solved";
	
	private static final int CELL_SIZE = 30;
	
	private static final int SPACING = 10;
	
	private RowCluesView rowCluesView;
	private ColCluesView colCluesView;
	private CellGridView cellGrid;
	
	private static boolean isPlaying = true;
	
	private static Button quitButton = new Button("Give Up");
	
	private Main main;
	
	
	/**
	 * This constructor initializes the cell gird, clues, and give up button and displays them.
	 * 
	 * @param rowClues The clues for each row.
	 * @param colClues The clues for each column.
	 * @param main A reference to the Main class for this application.
	 */
	public PlayView(int[][] rowClues, int[][] colClues, Main main) {
		super();
		
		this.main = main;
		
		//Create the grid and clue elements.
		rowCluesView = new RowCluesView(rowClues, CELL_SIZE, max(rowClues));
		colCluesView = new ColCluesView(colClues, CELL_SIZE, max(colClues));
		cellGrid = new CellGridView(rowClues.length, colClues.length);
		
		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			//Confirm the player's action to quit.
			public void handle(ActionEvent arg0) {
				String message = "Are you sure you want to quit?";
				ConfirmAlert confirmAlert = new ConfirmAlert(AlertType.CONFIRMATION, message, new EventHandler<ActionEvent>() {
					//Display the lose alert if the player confirms.
					@Override
					public void handle(ActionEvent arg0) {
						String message = "GAME OVER!";
						LoseAlert loseAlert = new LoseAlert(AlertType.INFORMATION, message, main);
						loseAlert.show();
					}
				});
				confirmAlert.show();
			}
		});
		
		//Add the quit button to an HBox for display.
		HBox buttonBox = new HBox(quitButton);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));

		//Set the location of each element.
		setLeft(rowCluesView);
		setTop(colCluesView);
		setCenter(cellGrid);
		setBottom(buttonBox);
		
		//Set the default style.
		getStyleClass().add(MODEL_STYLE);
	}
	
	/**
	 * This method passes the presenter to the cell grid.
	 * 
	 * @param presenter The presenter used in synchronization.
	 */
	@Override
	public void register(Presenter presenter) {
		cellGrid.register(presenter);
	}

	/**
	 * This method updates the cell state of a cell in the grid.
	 * 
	 * @param rowIdx The row the cell is in.
	 * @param colIdx The column the cell is in.
	 * @param state The desired new state of the cell.
	 */ 
	@Override
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		cellGrid.updateCell(rowIdx, colIdx, state);
	}
	
	/**
	 * This method updates a given rows solved state.
	 * 
	 * @param rowIdx The row to be updated.
	 * @param solved The new solved state of the row.
	 */
	public void setRowClueState(int rowIdx, boolean solved) {
		rowCluesView.setRowState(rowIdx, solved);
	}

	/**
	 * This method updates a given columns solved state.
	 * 
	 * @param colIdx The column to be updated.
	 * @param solved The new solved state of the column.
	 */
	public void setColClueState(int colIdx, boolean solved) {
		colCluesView.setColState(colIdx, solved);
	}

	/**
	 * This method updates the puzzle's solved state, and if solved, displays
	 * the win alert to the player.
	 * 
	 * @param solved The solved state of the puzzle.
	 */
	public void setPuzzleState(boolean solved) {
		ObservableList<String> styleClasses = getStyleClass();

		if (solved) {
			styleClasses.add(SOLVED_STYLE);
			displayWin();
		} else if (styleClasses.contains(SOLVED_STYLE)) {
			styleClasses.remove(SOLVED_STYLE);
		}
	}
	
	/**
	 * This method displays the win alert to the player.
	 */
	private void displayWin() {
		if (isPlaying) {
			WinAlert winAlert = new WinAlert(AlertType.CONFIRMATION, "YOU WIN!!!", main);
			winAlert.show();
			isPlaying = false;
		}
	}
	
	/**
	 * This method finds the maximum length of an array contained within a two
	 * dimensional array.
	 * 
	 * @param array The two dimensional array being evaluated.
	 * @return The maximum length of an array contained within a two dimensional array.
	 */
	private int max(int[][] array) {
		int result = 1;
		
		for (int i = 0; i < array.length; i++) {
			int length = array[i].length;
			
			if (result < length) {
				result = length;
			}
		}
		
		return result;
	}
	
	/**
	 * This method resets the isPlaying boolean so that the win alert will be displayed on the next win.
	 */
	public static void resetPlay() {
		isPlaying = true;
	}
}
