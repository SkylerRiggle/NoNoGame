import java.util.LinkedList;
import java.util.List;

import javafx.scene.layout.HBox;

/**
 * This class extends the HBox class.
 * This class displays the row clues for a row and its solved state.
 * 
 * @author Skyler Riggle
 * @version 1.0
 */
public class RowClueView extends HBox {

	private static final String STYLE_CLASS = "row-clue-view";

	private List<ClueView> clueViews = new LinkedList<>();

	/**
	 * This constructor initializes the row clue with each clue number in an array
	 * at a set clue size and total height.
	 * 
	 * @param rowClue The array of clues for the row.
	 * @param cellLength The side length for each clue.
	 * @param width The width of the row clues.
	 */
	public RowClueView(int[] rowClue, int cellLength, int width, boolean isDark) {
		getStyleClass().add(STYLE_CLASS);
		
		for (int clue : rowClue) {
			clueViews.add(new ClueView(clue, cellLength, isDark));
		}
		while (clueViews.size() < width) {
			clueViews.add(0, new ClueView(cellLength, isDark));
		}
		getChildren().addAll(clueViews);
		setMaxWidth(USE_PREF_SIZE);
		setMaxHeight(USE_PREF_SIZE);
	}

	/**
	 * This method displays the solved state for the row.
	 * 
	 * @param solved The solved state of the row.
	 */
	public void setState(boolean solved) {
		for (ClueView clueView : clueViews) {
			clueView.setState(solved);
		}
	}
}
