
public class CreatePresenter implements Presenter {

	private CreateModel model;
	private CreateView view;
	
	public CreatePresenter(CreateModel model, CreateView view) {
		this.model = model;
		this.view = view;
	}

	public void updateCell(int rowIdx, int colIdx, CellState state) {
		model.setCellState(rowIdx, colIdx, state);
		view.setCellState(rowIdx, colIdx, state);
	}

	public void cellClicked(int rowIdx, int colIdx, boolean isPrimary) {
		CellState state = model.getCellState(rowIdx, colIdx);
		
		if (isPrimary) {
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
	
	private void updateView(int rowIdx, int colIdx, CellState state) {
		updateCell(rowIdx, colIdx, state);
	}
}
