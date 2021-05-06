
/**
 * This interface is for all view objects that contain graphical user interface elements.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public interface View {
	public void register(Presenter presenter);
	public void setCellState(int rowIdx, int colIdx, CellState state);
}
