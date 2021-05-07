
/**
 * The create presenter is responsible for synchronizing graphics and game data while
 * the user is creating a new puzzle.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class CreatePresenter implements Presenter {

	private CreateModel model;
	private CreateView view;
	
	
	/**
	 * This constructor stores the model and view parameters to local variables to be
	 * used later.
	 * 
	 * @param model The create model for the puzzle.
	 * @param view The create view for the puzzle.
	 */
	public CreatePresenter(CreateModel model, CreateView view) {
		this.model = model;
		this.view = view;
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
	 * This method updates the current state of a cell in both the model and view.
	 * 
	 * @param rowIdx The row the cell is in.
	 * @param colIdx The column the cell is in.
	 * @param state The desired new state of the cell.
	 */
	private void updateView(int rowIdx, int colIdx, CellState state) {
		model.setCellState(rowIdx, colIdx, state);
		view.setCellState(rowIdx, colIdx, state);
	}
}
