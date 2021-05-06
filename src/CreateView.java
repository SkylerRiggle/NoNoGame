import javafx.scene.layout.BorderPane;

public class CreateView extends BorderPane implements View {
	
	private CellGridView cellGrid;

	public CreateView(int numRows, int numCols, String filename, CreateModel model, Main main) {
		super();
		
		cellGrid = new CellGridView(numRows, numCols);
		setCenter(cellGrid);
		setBottom(new SaveBox(filename, model, main));
	}

	@Override
	public void register(Presenter presenter) {
		cellGrid.register(presenter);
	}

	@Override
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		if (state != CellState.FILLED) {
			cellGrid.updateCell(rowIdx, colIdx, CellState.EMPTY);
		} else {
			cellGrid.updateCell(rowIdx, colIdx, state);
		}
	}
}
