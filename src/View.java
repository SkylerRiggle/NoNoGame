

public interface View {
	public void register(Presenter presenter);
	public void setCellState(int rowIdx, int colIdx, CellState state);
}
