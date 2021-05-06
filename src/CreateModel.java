import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class CreateModel implements Model {
	
	private boolean[][] cellStates;
	
	private PuzzleSize size;
	
	public CreateModel(PuzzleSize size) {
		this.size = size;
		
		int numRows;
		int numCols;
		
		switch (size) {
		case SMALL:
			numRows = SMALL_SIZE;
			numCols = SMALL_SIZE;
			break;
		case MEDIUM:
			numRows = MEDIUM_SIZE;
			numCols = MEDIUM_SIZE;
			break;
		case LARGE:
			numRows = LARGE_SIZE;
			numCols = LARGE_SIZE;
			break;
		default:
			numRows = SMALL_SIZE;
			numCols = SMALL_SIZE;
			break;
		}
		
		cellStates = new boolean[numRows][numCols];
	}

	@Override
	public int getSize() {
		return cellStates.length;
	}
	
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
	
	private List<Integer> projectRow(int rowIdx) {
		boolean[] states = new boolean[getSize()];
		
		for (int i = 0; i < states.length; i++) {
			states[i] = cellStates[rowIdx][i];
		}
		
		return project(states);
	}
	
	private List<Integer> projectCol(int colIdx) {
		boolean[] states = new boolean[getSize()];
		
		for (int i = 0; i < states.length; i++) {
			states[i] = cellStates[i][colIdx];
		}
		
		return project(states);
	}

	@Override
	public CellState getCellState(int rowIdx, int colIdx) {
		boolean state = cellStates[rowIdx][colIdx];
		
		if (state) {
			return CellState.FILLED;
		} else {
			return CellState.EMPTY;
		}
	}

	@Override
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		if (state != CellState.FILLED) {
			cellStates[rowIdx][colIdx] = false;
		} else {
			cellStates[rowIdx][colIdx] = true;
		}
	}
	
	public void save(String filename) throws IOException {
		File saveFile = new File(Main.getPuzzleLocation() + filename + Main.getPuzzleFileType());
		 
		if (!saveFile.exists()) {
			saveFile.createNewFile();
		}
		
		FileWriter fileWriter = new FileWriter(saveFile);

		fileWriter.write(toString());
		
		fileWriter.close();
	}
	
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
