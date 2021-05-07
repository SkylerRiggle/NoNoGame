import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * The create model stores the game data for creating new puzzles.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class CreateModel implements Model {
	
	private boolean[][] cellStates;
	
	private PuzzleSize size;
	
	
	/**
	 * This constructor creates a new array of boolean values that represent
	 * the puzzle cell gird.
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
	 * This method returns a projection for a given boolean array. A projection is an
	 * array of integer values representing the clusters of true values. For example,
	 * the array [TRUE, TRUE, FALSE, TRUE, FALSE] would project to [2, 1].
	 * 
	 * @param states The boolean values to be evaluated.
	 * @return The projection of the given array.
	 */
	private List<Integer> project(boolean[] states) {
		List<Integer> result = new ArrayList<Integer>();
		int sum = 0;
		
		//Count and add the chains of true values.
		for (boolean state : states) {
			if (state) {
				sum++;
			} else if (sum > 0) {
				result.add(sum);
				sum = 0;
			}
		}
		
		//Handle the case where a cluster has yet to be added or
		//no clusters were found.
		if (result.size() == 0 || sum > 0) {
			result.add(sum);
		}
		
		return result;
	}
	
	/**
	 * This method returns the projection for a given row.
	 * 
	 * @param rowIdx The row to be projected.
	 * @return The projection of the row.
	 */
	private List<Integer> projectRow(int rowIdx) {
		boolean[] states = new boolean[getSize()];
		
		for (int i = 0; i < states.length; i++) {
			states[i] = cellStates[rowIdx][i];
		}
		
		return project(states);
	}
	
	/**
	 * This method returns the projection for a given column.
	 * 
	 * @param colIdx The column to be projected.
	 * @return The projection of the column.
	 */
	private List<Integer> projectCol(int colIdx) {
		boolean[] states = new boolean[getSize()];
		
		for (int i = 0; i < states.length; i++) {
			states[i] = cellStates[i][colIdx];
		}
		
		return project(states);
	}

	/**
	 * This method returns the current state of a given cell.
	 * 
	 * @param rowIdx The row the cell is in.
	 * @param colIdx The column the cell is in.
	 * @return The state of the cell.
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
	 * This method changes the state of a given cell.
	 * 
	 * @param rowIdx The row the cell is in.
	 * @param colIdx The column the cell is in.
	 * @param state The desired new state of the cell.
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
	 * This method writes the result of the toString method to a desired file.
	 * 
	 * @param filename The desired filename for the created puzzle.
	 * @throws IOException Thrown in case of error while file writing.
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
	 * This method returns a string representation of the puzzle to inclue its size and clues.
	 * 
	 * @return A string representation of the created puzzle.
	 */
	@Override
	public String toString() {
		StringJoiner result = new StringJoiner(System.lineSeparator());
		
		//Get the size of the grid for looping.
		int gridSize = getSize();
		
		//Add the row clues to the string.
		for (int row = 0; row < gridSize; row++) {
			List<Integer> projection = projectRow(row);
			StringJoiner rowString = new StringJoiner(" ");
			
			for (Integer num : projection) {
				rowString.add(num.toString());
			}
			
			result.add(rowString.toString());
		}
		
		//Add the column clues to the string.
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
