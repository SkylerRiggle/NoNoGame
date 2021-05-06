import java.util.LinkedList;
import java.util.List;

import javafx.scene.layout.VBox;

/**
 * This class extends the VBox class.
 * This class displays the column clues for a column and its solved state.
 * 
 * @author Skyler Riggle
 * @version 1.0
 */
public class ColClueView extends VBox {

	private static final String STYLE_CLASS = "col-clue-view";

	private List<ClueView> clueViews = new LinkedList<>();

	/**
	 * This constructor initializes the column clue with each clue number in an array
	 * at a set clue size and total height.
	 * 
	 * @param colClue The array of clues for the column.
	 * @param cellLength The side length for each clue.
	 * @param height The height of the column clues.
	 */
	public ColClueView(int[] colClue, int cellLength, int height, boolean isDark) {
		getStyleClass().add(STYLE_CLASS);
		
		for (int clue : colClue) {
			clueViews.add(new ClueView(clue, cellLength, isDark));
		}
		while (clueViews.size() < height) {
			clueViews.add(0, new ClueView(cellLength, isDark));
		}
		getChildren().addAll(clueViews);
		setMaxWidth(USE_PREF_SIZE);
		setMaxHeight(USE_PREF_SIZE);
	}

	/**
	 * This method displays the solved state for the column.
	 * 
	 * @param solved The solved state of the column.
	 */
	public void setState(boolean solved) {
		for (ClueView clueView : clueViews) {
			clueView.setState(solved);
		}
	}
}
