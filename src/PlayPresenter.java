
public class PlayPresenter implements Presenter {

	private static PlayModel model;
	private static PlayView view;
	
	public PlayPresenter(PlayModel model, PlayView view) {
		PlayPresenter.model = model;
		PlayPresenter.view = view;
		
		for (int row = 0; row < PlayPresenter.model.getSize(); row++) {
			for (int col = 0; col < PlayPresenter.model.getSize(); col++) {
				updateView(row, col, CellState.EMPTY);
			}
		}
	}

	public static void updateCell(int rowIdx, int colIdx, CellState state) {
		model.setCellState(rowIdx, colIdx, state);
		view.setCellState(rowIdx, colIdx, state);
	}

	@Override
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
	
	private static void updateView(int rowIdx, int colIdx, CellState state) {
		boolean isSolved = model.isSolved();
		
		if (!isSolved) 
		{
			updateCell(rowIdx, colIdx, state);
			
			isSolved = model.isSolved();
		}
		
		if (isSolved) {
			for (int row = 0; row < model.getSize(); row++) {
				for (int col = 0; col < model.getSize(); col++) {
					if (model.getCellState(row, col) == CellState.MARKED) {
						updateCell(row, col, CellState.EMPTY);
					}
				}
			}
		}
		
		view.setRowClueState(rowIdx, model.isRowSolved(rowIdx));
		view.setColClueState(colIdx, model.isColSolved(colIdx));
		view.setPuzzleState(isSolved);
	}
	
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
