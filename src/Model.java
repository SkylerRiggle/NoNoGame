
/**
 * This interface is for all model objects that contain game data.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public interface Model {
	//Puzzle sizes
	static final int SMALL_SIZE = 10;
	static final int MEDIUM_SIZE = 20;
	static final int LARGE_SIZE = 24;
	
	public int getSize();
	public CellState getCellState(int rowIdx, int colIdx);
	public void setCellState(int rowIdx, int colIdx, CellState state);
}
