import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This class extends the StackPane class.
 * This class displays the an individual clue for the puzzle.
 * 
 * @author Skyler Riggle
 * @version 1.0
 */
public class ClueView extends StackPane {

	private static final String STYLE_CLASS = "clue-view";
	private static final String DARK_STYLE = "-dark";

	private String UNSOLVED_STYLE_CLASS = "clue-view-unsolved";
	private String SOLVED_STYLE_CLASS = "clue-view-solved";
	
	private static final double FONT_SIZE_SCALE = 1.0 / 2.0;

	private Rectangle background = new Rectangle();
	private Text text = new Text();

	/**
	 * This constructor calls the constructor with the parameters
	 * (String, int). with the side length passed and an empty string.
	 * 
	 * @param sideLength The side length of the view.
	 */
	public ClueView(int sideLength, boolean isDark) {
		this("", sideLength, isDark);
	}

	/**
	 * This constructor calls the constructor with the parameters
	 * (String, int). with the side length passed and an a string 
	 * representation of the integer clue.
	 * 
	 * @param clue The clue integer value.
	 * @param sideLength The side length of the view.
	 */
	public ClueView(int clue, int sideLength, boolean isDark) {
		this(Integer.toString(clue), sideLength, isDark);
	}

	/**
	 * This method initializes the clue with a set side length and clue number.
	 * 
	 * @param clue The string representation of the clue number.
	 * @param sideLength The side length of the view.
	 */
	private ClueView(String clue, int sideLength, boolean isDark) {
		getStyleClass().add(STYLE_CLASS);
		text.setText(clue);
		
		if (isDark) {
			UNSOLVED_STYLE_CLASS += DARK_STYLE;
			SOLVED_STYLE_CLASS += DARK_STYLE;
		}
		
		setState(false);
		setSize(sideLength);
		getChildren().addAll(background, text);
	}

	/**
	 * This method displays the solved state for the clue.
	 * 
	 * @param solved The solved state of the clue.
	 */
	public void setState(boolean solved) {
		ObservableList<String> styleClasses = getStyleClass();
		styleClasses.removeAll(SOLVED_STYLE_CLASS, UNSOLVED_STYLE_CLASS);
		if (solved) {
			styleClasses.add(SOLVED_STYLE_CLASS);
		} else {
			styleClasses.add(UNSOLVED_STYLE_CLASS);
		}
	}

	/**
	 * This method sets the size of the clue.
	 * 
	 * @param sideLength The side length of the clue.
	 */
	public void setSize(int sideLength) {
		background.setWidth(sideLength);
		background.setHeight(sideLength);
		text.setFont(new Font(FONT_SIZE_SCALE * sideLength));
	}
}
