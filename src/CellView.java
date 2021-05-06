import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

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
	
	public CellView(int rowIdx, int colIdx, int cellSize) {
		this.rowIdx = rowIdx;
		this.colIdx = colIdx;
		
		getStyleClass().add(STYLE);
		
		setCellState(CellState.EMPTY);
		setSize(cellSize);
		
		getChildren().addAll(background, leftLeg, rightLeg);
	}
	
	public void setSize(int cellSize) {
		background.setWidth(cellSize);
		background.setHeight(cellSize);
		
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
	
	public void setCellState(CellState state) {
		ObservableList<String> styleClasses = getStyleClass();
		
		styleClasses.removeAll(EMPTY_CLASS, FILLED_CLASS, MARKED_CLASS);
		
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
