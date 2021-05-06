
/**
 * This interface is for all Presenter objects that link between graphical data and game data.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public interface Presenter {
	public void cellClicked(int rowIdx, int colIdx, boolean isPrimary);
}
