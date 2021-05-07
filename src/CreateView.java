import javafx.scene.layout.BorderPane;

/**
 * The create view displays all of the components for creating a puzzle, including a grid, save button, and
 * back button.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class CreateView extends BorderPane implements View {
	
	private CellGridView cellGrid;

	
	/**
	 * This constructor initializes the creation view with a grid of cells and
	 * a box holding a save and back button.
	 * 
	 * @param numRows The number of rows in the grid.
	 * @param numCols The number of columns in the grid.
	 * @param filename The desired filename for the new puzzle.
	 * @param model The create model containing the game data.
	 * @param main A reference to the Main class for this application.
	 */
	public CreateView(int numRows, int numCols, String filename, CreateModel model, Main main) {
		super();
		
		cellGrid = new CellGridView(numRows, numCols);
		setCenter(cellGrid);
		setBottom(new SaveBox(filename, model, main));
	}

	/**
	 * This method passes the presenter to the cell grid's register method.
	 * 
	 * @param presenter The presenter used for synchronization.
	 */
	@Override
	public void register(Presenter presenter) {
		cellGrid.register(presenter);
	}

	/**
	 * This method sets the new state of the cell. This method provides the filter for creation on
	 * how to handle marked values.
	 * 
	 * @param rowIdx The row the cell is in.
	 * @param colIdx The column the cell is in.
	 * @param state The desired new state of the cell.
	 */
	@Override
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		if (state != CellState.FILLED) {
			cellGrid.updateCell(rowIdx, colIdx, CellState.EMPTY);
		} else {
			cellGrid.updateCell(rowIdx, colIdx, state);
		}
	}
}
