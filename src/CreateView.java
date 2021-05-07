import javafx.scene.layout.BorderPane;

/**
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class CreateView extends BorderPane implements View {
	
	private CellGridView cellGrid;

	
	/**
	 * 
	 * @param numRows
	 * @param numCols
	 * @param filename
	 * @param model
	 * @param main
	 */
	public CreateView(int numRows, int numCols, String filename, CreateModel model, Main main) {
		super();
		
		cellGrid = new CellGridView(numRows, numCols);
		setCenter(cellGrid);
		setBottom(new SaveBox(filename, model, main));
	}

	/**
	 * 
	 * @param presenter
	 */
	@Override
	public void register(Presenter presenter) {
		cellGrid.register(presenter);
	}

	/**
	 * 
	 * @param rowIdx
	 * @param colIdx
	 * @param state
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
