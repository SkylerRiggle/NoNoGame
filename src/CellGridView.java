import javafx.scene.layout.GridPane;

/**
 * The cell grid view is a GridPane containing each individual cell in the puzzle.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class CellGridView extends GridPane {

	private static final int CELL_SIZE = 30;
	
	CellView[][] cells;
	
	
	/**
	 * This constructor creates and adds each cell to the grid.
	 * 
	 * @param numRows The number of rows in the puzzle.
	 * @param numCols The number of columns in the puzzle.
	 */
	public CellGridView(int numRows, int numCols) {
		super();
		
		//Create an array of type CellView at the appropriate size.
		cells = new CellView[numRows][numCols];
		
		//Create, display, and store each cell.
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				CellView cellView = new CellView(row, col, CELL_SIZE);
				cells[row][col] = cellView;
				add(cellView, col, row);
			}
		}
	}
	
	/**
	 * This method updates the state of a cell.
	 * 
	 * @param rowIdx The row the cell is in.
	 * @param colIdx The column the cell is in.
	 * @param state The desired new state of the cell.
	 */
	public void updateCell(int rowIdx, int colIdx, CellState state) {
		cells[rowIdx][colIdx].setCellState(state);
	}
	
	/**
	 * This method passes the presenter object to each cells register method.
	 * 
	 * @param presenter The presenter responsible for synchronizing the graphics and game data.
	 */
	public void register(Presenter presenter) {
		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells[row].length; col++) {
				cells[row][col].register(presenter);
			}
		}
	}
}
