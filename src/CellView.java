import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * The cell view holds all of the graphical elements of a cell and checks for input from the player.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class CellView extends StackPane {
	
	private static final String STYLE = "cell-view";
	private static final String EMPTY_CLASS = "cell-view-empty";
	private static final String FILLED_CLASS = "cell-view-filled";
	private static final String MARKED_CLASS = "cell-view-marked";
	
	private static final double X_LENGTH_SCALE = 1.0 / 2.0;
	
	private int rowIdx;
	private int colIdx;
	
	private Rectangle background = new Rectangle();
	private Line leftLeg = new Line();
	private Line rightLeg = new Line();
	
	
	/**
	 * This constructor initializes the look of the cell and stores its index.
	 * 
	 * @param rowIdx The row the cell is in.
	 * @param colIdx The column the cell is in.
	 * @param cellSize The side length of the cell.
	 */
	public CellView(int rowIdx, int colIdx, int cellSize) {
		super();
		
		//Store the cells location for use locally.
		this.rowIdx = rowIdx;
		this.colIdx = colIdx;
		
		//Add the style class.
		getStyleClass().add(STYLE);
		
		//Instantiate the cell look.
		setCellState(CellState.EMPTY);
		setSize(cellSize);
		
		//Add the cells elements to the pane.
		getChildren().addAll(background, leftLeg, rightLeg);
	}
	
	/**
	 * This method sets the size of the cell and the mark for the cell.
	 * 
	 * @param cellSize The side length of a cell.
	 */
	public void setSize(int cellSize) {
		//Cell setup
		background.setWidth(cellSize);
		background.setHeight(cellSize);
		
		//Mark setup
		double legLength = X_LENGTH_SCALE * cellSize;
		double xWidth = legLength / Math.sqrt(2);
		double xHeight = xWidth;
		leftLeg.setStartX(0);
		leftLeg.setStartY(0);
		leftLeg.setEndX(xWidth);
		leftLeg.setEndY(xHeight);
		rightLeg.setStartX(0);
		rightLeg.setStartY(xHeight);
		rightLeg.setEndX(xWidth);
		rightLeg.setEndY(0);
	}
	
	/**
	 * This method changes the look of a cell based on its new state.
	 * 
	 * @param state The new state of the cell.
	 */
	public void setCellState(CellState state) {
		ObservableList<String> styleClasses = getStyleClass();
		
		//Remove the current style class.
		styleClasses.removeAll(EMPTY_CLASS, FILLED_CLASS, MARKED_CLASS);
		
		//Set the desired style class.
		switch (state) {
		case EMPTY:
			styleClasses.add(EMPTY_CLASS);
			break;
		case MARKED:
			styleClasses.add(MARKED_CLASS);
			break;
		case FILLED:
			styleClasses.add(FILLED_CLASS);
			break;
		}
	}
	
	/**
	 * This method checks for player input and passes it to the presenter for synchronization.
	 * 
	 * @param presenter A reference to the presenter that will handle the input data.
	 */
	public void register(Presenter presenter) {
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				MouseButton button = event.getButton();
				if (button == MouseButton.PRIMARY) {
					presenter.cellClicked(rowIdx, colIdx, true);
				} else if (button == MouseButton.SECONDARY) {
					presenter.cellClicked(rowIdx, colIdx, false);
				}
			}
		});
	}
}
