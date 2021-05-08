
/**
 * The play presenter is responsible for synchronizing graphics and game data while
 * the user is playing a puzzle.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class PlayPresenter implements Presenter {

	private static PlayModel model;
	private static PlayView view;
	
	
	/**
	 * This method stores the play model and view locally for later use, and synchronizes the
	 * view data to match the model data.
	 * 
	 * @param model The play model containing the game data for the puzzle.
	 * @param view The play view containing the graphical data for the puzzle.
	 */
	public PlayPresenter(PlayModel model, PlayView view) {
		PlayPresenter.model = model;
		PlayPresenter.view = view;
		
		//Initialize each cell to match the model data.
		for (int row = 0; row < PlayPresenter.model.getSize(); row++) {
			for (int col = 0; col < PlayPresenter.model.getSize(); col++) {
				CellState state = PlayPresenter.model.getCellState(row, col);
				updateView(row, col, state);
			}
		}
		
		PlayPresenter.view.register(this);
	}

	/**
	 * This method updates the current state of a cell in both the model and view.
	 * 
	 * @param rowIdx The row the cell is in.
	 * @param colIdx The column the cell is in.
	 * @param state The desired new state of the cell.
	 */
	public static void updateCell(int rowIdx, int colIdx, CellState state) {
		model.setCellState(rowIdx, colIdx, state);
		view.setCellState(rowIdx, colIdx, state);
	}

	/**
	 * The method takes the player's input and the current state of the selected cell
	 * and determines the new state of the cell.
	 * 
	 * @param rowIdx The row the cell is in.
	 * @param colIdx The column the cell is in.
	 * @param isPrimary Whether or not the left mouse button was pressed. If false, assume
	 * the right mouse button was pressed.
	 */
	@Override
	public void cellClicked(int rowIdx, int colIdx, boolean isPrimary) {
		CellState state = model.getCellState(rowIdx, colIdx);
		
		if (isPrimary) {
			//Left mouse button.
			switch (state) {
			case EMPTY:
				updateView(rowIdx, colIdx, CellState.FILLED);
				break;
			case FILLED:
				updateView(rowIdx, colIdx, CellState.EMPTY);
				break;
			case MARKED:
				updateView(rowIdx, colIdx, CellState.FILLED);
				break;
			}
		} else {
			//Right mouse button.
			switch (state) {
			case EMPTY:
				updateView(rowIdx, colIdx, CellState.MARKED);
				break;
			case FILLED:
				updateView(rowIdx, colIdx, CellState.MARKED);
				break;
			case MARKED:
				updateView(rowIdx, colIdx, CellState.EMPTY);
				break;
			}
		}
	}
	
	/**
	 * This method updates both the cell and clue items by examining the new state of the cell
	 * and the puzzles current solved state.
	 * 
	 * @param rowIdx The row the cell is in.
	 * @param colIdx The column the cell is in.
	 * @param state The desired new state of the cell.
	 */
	private static void updateView(int rowIdx, int colIdx, CellState state) {
		boolean isSolved = model.isSolved();
		
		//If the puzzle isn't solved, update the cell.
		if (!isSolved) 
		{
			updateCell(rowIdx, colIdx, state);
			
			//Check if the update has solved the puzzle.
			isSolved = model.isSolved();
			
			//If the puzzle has been solved, remove the marks from the puzzle.
			if (isSolved) {
				for (int row = 0; row < model.getSize(); row++) {
					for (int col = 0; col < model.getSize(); col++) {
						if (model.getCellState(row, col) == CellState.MARKED) {
							updateCell(row, col, CellState.EMPTY);
						}
					}
				}
			}
			
			//Update the clue and puzzle solved states.
			view.setRowClueState(rowIdx, model.isRowSolved(rowIdx));
			view.setColClueState(colIdx, model.isColSolved(colIdx));
			view.setPuzzleState(isSolved);
		}
	}
	
	/**
	 * This method completely resets the puzzle so the player can replay it.
	 */
	public static void reset() {
		for (int row = 0; row < model.getSize(); row++) {
			for (int col = 0; col < model.getSize(); col++) {
				updateCell(row, col, CellState.EMPTY);
			}
		}
		
		for (int row = 0; row < model.getSize(); row++) {
			for (int col = 0; col < model.getSize(); col++) {
				updateView(row, col, CellState.EMPTY);
			}
		}
		
		PlayModel.resetTime();
	}
}
