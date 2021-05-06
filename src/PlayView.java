import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

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
	
	public PlayView(int[][] rowClues, int[][] colClues, Main main) {
		super();
		
		this.main = main;
		
		rowCluesView = new RowCluesView(rowClues, CELL_SIZE, max(rowClues));
		colCluesView = new ColCluesView(colClues, CELL_SIZE, max(colClues));
		cellGrid = new CellGridView(rowClues.length, colClues.length);
		
		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String message = "Are you sure you want to quit?";
				ConfirmAlert confirmAlert = new ConfirmAlert(AlertType.CONFIRMATION, message, new EventHandler<ActionEvent>() {
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
		
		HBox buttonBox = new HBox(quitButton);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));

		setLeft(rowCluesView);
		setTop(colCluesView);
		setCenter(cellGrid);
		setBottom(buttonBox);
		
		setState(false);
	}
	
	@Override
	public void register(Presenter presenter) {
		cellGrid.register(presenter);
	}

	@Override
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		cellGrid.updateCell(rowIdx, colIdx, state);
	}
	
	public void setState(boolean isSolved) {
		ObservableList<String> styleClasses = getStyleClass();
		
		styleClasses.removeAll(MODEL_STYLE, SOLVED_STYLE);
		
		if (isSolved) {
			styleClasses.add(SOLVED_STYLE);
		} else {
			styleClasses.add(MODEL_STYLE);
		}
	}
	
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
	
	private void displayWin() {
		if (isPlaying) {
			WinAlert winAlert = new WinAlert(AlertType.CONFIRMATION, "YOU WIN!!!", main);
			winAlert.show();
			isPlaying = false;
		}
	}
	
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
	
	public static void resetPlay() {
		isPlaying = true;
	}
}
