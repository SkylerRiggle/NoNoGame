
public interface Model {
	static final int SMALL_SIZE = 10;
	static final int MEDIUM_SIZE = 20;
	static final int LARGE_SIZE = 30;
	
	public int getSize();
	public CellState getCellState(int rowIdx, int colIdx);
	public void setCellState(int rowIdx, int colIdx, CellState state);
}
