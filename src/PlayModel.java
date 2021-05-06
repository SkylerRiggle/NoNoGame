import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.scene.control.Alert.AlertType;

public class PlayModel implements Model {

	private static final int RANDOM_UPPER = 1000;
	private static final double RANDOM_DETERMINENT = 1.25;
	
	private CellState[][] cellStates;
	private int[][] rowClues;
	private int[][] colClues;
	
	private static long startTime;
	
	public PlayModel() {
		Random random = new Random();
		
		int numRows = SMALL_SIZE;
		int numCols = SMALL_SIZE;
		
		initializeArrays(numRows, numCols);
		
		CellState[][] solution = createRandomPuzzle(numRows, numCols, random);
		decodeSolution(numRows, numCols, solution);
		
		startTime = System.currentTimeMillis();
	}

	private CellState[][] createRandomPuzzle(int numRows, int numCols, Random random) {
		CellState[][] solution = new CellState[numRows][numCols];
		
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				int randomUpper = random.nextInt(RANDOM_UPPER) + 1;
				int randomValue = random.nextInt(randomUpper);
				
				if (randomValue >= randomUpper / RANDOM_DETERMINENT) {
					solution[row][col] = CellState.FILLED;
				} else {
					solution[row][col] = CellState.EMPTY;
				}
			}
		}
		
		return solution;
	}
	
	private void decodeSolution(int numRows, int numCols, CellState[][] solution) {
		for (int row = 0; row < numRows; row++) {
			List<Integer> projection = project(solution[row]);
			rowClues[row] = new int[projection.size()];
					
			for (int i = 0; i < rowClues[row].length; i++) {
				rowClues[row][i] = projection.get(i);
			}
		}
		
		for (int col = 0; col < numRows; col++) {
			CellState[] clues = new CellState[numRows];
			for (int row = 0; row < numRows; row++) {
				clues[row] = solution[row][col];
			}
			
			List<Integer> projection = project(clues);
			colClues[col] = new int[projection.size()];
					
			for (int i = 0; i < colClues[col].length; i++) {
				colClues[col][i] = projection.get(i);
			}
		}
	}
	
	public PlayModel(String filename) {
		File puzzleFile = new File(Main.getPuzzleLocation() + filename);
		
		try {
			Scanner scanner = new Scanner(puzzleFile);
			
			int numRows;
			int numCols;
			PuzzleSize size = PuzzleSize.valueOf(scanner.nextLine());
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
			
			initializeArrays(numRows, numCols);
			
			rowClues = new int[numRows][];
			
			for (int row = 0; row < numRows; row++) {
				rowClues[row] = extractClue(scanner.nextLine().trim());
			}
			
			colClues = new int[numCols][];
			
			for (int col = 0; col < numRows; col++) {
				colClues[col] = extractClue(scanner.nextLine().trim());
			}
			
			startTime = System.currentTimeMillis();
			
			scanner.close();
		} catch (FileNotFoundException e) {
			String message = "ERROR: File cannot be read.";
			ErrorAlert errorAlert = new ErrorAlert(AlertType.ERROR, message);
			errorAlert.show();
		}
	}

	private void initializeArrays(int numRows, int numCols) {
		cellStates = new CellState[numRows][numCols];
		rowClues = new int[numRows][];
		colClues = new int[numRows][];
		
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				setCellState(row, col, CellState.EMPTY);
			}
		}
	}
	
	public List<Integer> project(CellState[] states) {
		List<Integer> result = new ArrayList<Integer>();
		int sum = 0;
		
		for (CellState state : states) {
			if (state == CellState.FILLED) {
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
	
	public boolean isRowSolved(int rowIdx) {
		int i;
		int size = getSize();
		CellState[] states = new CellState[size];
		
		for (i = 0; i < size; i++) {
			states[i] = cellStates[rowIdx][i];
		}
		
		List<Integer> projection = project(states);
		
		if (projection.size() == rowClues[rowIdx].length) {
			for (i = 0; i < rowClues[rowIdx].length; i++) {
				if (projection.get(i) != rowClues[rowIdx][i]) {
					return false;
				}
			}
		} else {
			return false;
		}
		
		return true;
	}
	
	public boolean isColSolved(int colIdx) {
		int i;
		int size = getSize();
		CellState[] states = new CellState[size];
		
		for (i = 0; i < size; i++) {
			states[i] = cellStates[i][colIdx];
		}
		
		List<Integer> projection = project(states);
		
		if (projection.size() == colClues[colIdx].length) {
			for (i = 0; i < colClues[colIdx].length; i++) {
				if (projection.get(i) != colClues[colIdx][i]) {
					return false;
				}
			}
		} else {
			return false;
		}
		
		return true;
	}
	
	public boolean isSolved() {
		int size = getSize();
		
		for (int row = 0; row < size; row++) {
			if (!isRowSolved(row)) {
				return false;
			}
		}
		
		for (int col = 0; col < size; col++) {
			if (!isColSolved(col)) {
				return false;
			}
		}
		
		return true;
	}
	
	public int[][] getRowClues() {
		return Arrays.copyOf(rowClues, rowClues.length);
	}
	
	public int[][] getColClues() {
		return Arrays.copyOf(colClues, colClues.length);
	}
	
	@Override
	public int getSize() {
		return cellStates.length;
	}

	@Override
	public CellState getCellState(int rowIdx, int colIdx) {
		return cellStates[rowIdx][colIdx];
	}

	@Override
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		cellStates[rowIdx][colIdx] = state;
	}
	
	private int[] extractClue(String line) {
		if (line == null || line.isEmpty()) {
			return new int[] {0};
		}
		
		String[] nums = line.split(" ");
		int[] result = new int[nums.length];
		
		for (int i = 0; i < nums.length; i++) {
			result[i] = Integer.parseInt(nums[i]);
		}
		
		return result;
	}
	
	public static void resetTime() {
		startTime = System.currentTimeMillis();
	}
	
	public static long getTotalTime() {
		return System.currentTimeMillis() - startTime;
	}
}
