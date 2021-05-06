import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.VBox;

/**
 * This class extends the VBox class.
 * This class displays all of the row clues in the puzzle.
 * 
 * @author Skyler Riggle
 * @version 1.0
 */
public class RowCluesView extends VBox {
	private List<RowClueView> rowClueViews = new ArrayList<RowClueView>();
	
	/**
	 * This constructor initializes the row clues with a set height and length.
	 * 
	 * @param rowClues The array of row clues.
	 * @param cellLength The side length of the cell.
	 * @param width The width of each row clue.
	 */
	public RowCluesView(int[][] rowClues, int cellLength, int width) {
		super();
		
		for (int i = 0; i < rowClues.length; i++) {
			RowClueView currentClue = new RowClueView(rowClues[i], cellLength, width, isDark(i));
			rowClueViews.add(currentClue);
			super.getChildren().add(currentClue);
		}
	}
	
	/**
	 * This method sets the solved state for a given row.
	 * 
	 * @param rowIdx The row to be displayed.
	 * @param solved The solved state for the row.
	 */
	public void setRowState(int rowIdx, boolean solved) {
		rowClueViews.get(rowIdx).setState(solved);
	}
	
	private boolean isDark(int counter) {
		if (counter % 2 == 0) {
			return true;
		}
		
		return false;
	}
}
