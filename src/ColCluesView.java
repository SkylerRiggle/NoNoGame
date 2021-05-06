import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 * This class extends the HBox class.
 * This class displays all of the column clues in the puzzle.
 * 
 * @author Skyler Riggle
 * @version 1.0
 */
public class ColCluesView extends HBox {
	private List<ColClueView> colClueViews = new ArrayList<ColClueView>();
	
	/**
	 * This constructor initializes the column clues with a set height and length.
	 * 
	 * @param colClues The array of column clues.
	 * @param cellLength The side length of the cell.
	 * @param height The height of each column clue.
	 * @param width The width used for padding the upper-left corner of the display.
	 */
	public ColCluesView(int[][] colClues, int cellLength, int height) {
		super();
		
		setAlignment(Pos.TOP_RIGHT);
		
		for (int i = 0; i < colClues.length; i++) {
			ColClueView currentClue = new ColClueView(colClues[i], cellLength, height, isDark(i));
			colClueViews.add(currentClue);
			getChildren().add(currentClue);
		}
	}
	
	/**
	 * This method sets the solved state for a given column.
	 * 
	 * @param colIdx The column to be displayed.
	 * @param solved The solved state for the column.
	 */
	public void setColState(int colIdx, boolean solved) {
		colClueViews.get(colIdx).setState(solved);
	}
	
	/**
	 * This method checks if the column clue at a certain index is at an even index, and
	 * if so, returns that it should be dark.
	 * 
	 * @param counter The index value of the column clue.
	 * @return The dark state for the column clue.
	 */
	private boolean isDark(int counter) {
		if (counter % 2 == 0) {
			return true;
		}
		
		return false;
	}
}
