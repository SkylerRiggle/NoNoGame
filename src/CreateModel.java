import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class CreateModel implements Model {
	
	private boolean[][] cellStates;
	
	private PuzzleSize size;
	
	
	/**
	 * 
	 * @param size The desired size of the puzzle.
	 */
	public CreateModel(PuzzleSize size) {
		this.size = size;
		
		int length;
		
		//Get the integer value of the puzzle size.
		switch (size) {
		case SMALL:
			length = SMALL_SIZE;
			break;
		case MEDIUM:
			length = MEDIUM_SIZE;
			break;
		case LARGE:
			length = LARGE_SIZE;
			break;
		default:
			length = SMALL_SIZE;
			break;
		}
		
		cellStates = new boolean[length][length];
	}

	/**
	 * This method returns the current size of the puzzle which is equal to both
	 * the number of columns and rows in the puzzle.
	 * 
	 * @return The size of the puzzle.
	 */
	@Override
	public int getSize() {
		return cellStates.length;
	}
	
	/**
	 * 
	 * @param states
	 * @return
	 */
	private List<Integer> project(boolean[] states) {
		List<Integer> result = new ArrayList<Integer>();
		int sum = 0;
		
		for (boolean state : states) {
			if (state) {
				sum++;
			} else if (sum > 0) {
				result.add(sum);
				sum = 0;
			}
		}
		
		if (result.size() == 0 || sum > 0) {
			result.add(sum);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param rowIdx
	 * @return
	 */
	private List<Integer> projectRow(int rowIdx) {
		boolean[] states = new boolean[getSize()];
		
		for (int i = 0; i < states.length; i++) {
			states[i] = cellStates[rowIdx][i];
		}
		
		return project(states);
	}
	
	/**
	 * 
	 * @param colIdx
	 * @return
	 */
	private List<Integer> projectCol(int colIdx) {
		boolean[] states = new boolean[getSize()];
		
		for (int i = 0; i < states.length; i++) {
			states[i] = cellStates[i][colIdx];
		}
		
		return project(states);
	}

	/**
	 * 
	 * @param rowIdx
	 * @param colIdx
	 * @return
	 */
	@Override
	public CellState getCellState(int rowIdx, int colIdx) {
		boolean state = cellStates[rowIdx][colIdx];
		
		if (state) {
			return CellState.FILLED;
		} else {
			return CellState.EMPTY;
		}
	}

	/**
	 * 
	 * @param rowIdx
	 * @param colIdx
	 * @param state
	 */
	@Override
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		if (state != CellState.FILLED) {
			cellStates[rowIdx][colIdx] = false;
		} else {
			cellStates[rowIdx][colIdx] = true;
		}
	}
	
	/**
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public void save(String filename) throws IOException {
		File saveFile = new File(Main.getPuzzleLocation() + filename + Main.getPuzzleFileType());
		 
		if (!saveFile.exists()) {
			saveFile.createNewFile();
		}
		
		FileWriter fileWriter = new FileWriter(saveFile);

		fileWriter.write(toString());
		
		fileWriter.close();
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		StringJoiner result = new StringJoiner(System.lineSeparator());
		
		int gridSize = getSize();
		
		for (int row = 0; row < gridSize; row++) {
			List<Integer> projection = projectRow(row);
			StringJoiner rowString = new StringJoiner(" ");
			
			for (Integer num : projection) {
				rowString.add(num.toString());
			}
			
			result.add(rowString.toString());
		}
		
		for (int col = 0; col < gridSize; col++) {
			List<Integer> projection = projectCol(col);
			StringJoiner colString = new StringJoiner(" ");

			for (Integer num : projection) {
				colString.add(num.toString());
			}
			
			result.add(colString.toString());
		}

		return size.toString() + '\n' + result.toString();
	}
}
