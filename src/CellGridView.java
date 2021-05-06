import javafx.scene.layout.GridPane;

public class CellGridView extends GridPane {

	private static final int CELL_SIZE = 30;
	
	CellView[][] cells;
	
	public CellGridView(int numRows, int numCols) {
		super();
		
		cells = new CellView[numRows][numCols];
		
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				CellView cellView = new CellView(row, col, CELL_SIZE);
				cells[row][col] = cellView;
				add(cellView, col, row);
			}
		}
	}
	
	public void updateCell(int rowIdx, int colIdx, CellState state) {
		cells[rowIdx][colIdx].setCellState(state);
	}
	
	public void register(Presenter presenter) {
		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells[row].length; col++) {
				cells[row][col].register(presenter);
			}
		}
	}
}
