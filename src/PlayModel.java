import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.scene.control.Alert.AlertType;

/**
 * The play model contains all the game data for playing a puzzle.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class PlayModel implements Model {

	private static final int RANDOM_UPPER = 1000;
	private static final double RANDOM_DETERMINENT = 1.25;
	
	private CellState[][] cellStates;
	private int[][] rowClues;
	private int[][] colClues;
	
	private static long startTime;
	
	
	/**
	 * This constructor creates a new random puzzle of small size for the player to
	 * play.
	 */
	public PlayModel() {
		Random random = new Random();
		
		int length = SMALL_SIZE;
		
		initializeArrays(length, length);
		
		CellState[][] solution = createRandomPuzzle(length, length, random);
		decodeSolution(length, length, solution);
		
		resetTime();
	}

	
	/**
	 * This method generates an array to represent the solution to a random puzzle.
	 * 
	 * @param numRows The number of rows in the puzzle.
	 * @param numCols The number of columns in the puzzle.
	 * @param random The random to be used when generating values.
	 * @return A two dimensional array of filled and empty cells.
	 */
	private CellState[][] createRandomPuzzle(int numRows, int numCols, Random random) {
		CellState[][] solution = new CellState[numRows][numCols];
		
		//Generate a random state for each cell.
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
	
	/**
	 * This method initializes the clues for a given solution array.
	 * 
	 * @param numRows The number of rows in the puzzle.
	 * @param numCols The number of columns in the puzzle.
	 * @param solution The solution array to be evaluated.
	 */
	private void decodeSolution(int numRows, int numCols, CellState[][] solution) {
		//Get the row clues
		for (int row = 0; row < numRows; row++) {
			List<Integer> projection = project(solution[row]);
			rowClues[row] = new int[projection.size()];
					
			for (int i = 0; i < rowClues[row].length; i++) {
				rowClues[row][i] = projection.get(i);
			}
		}
		
		//Get the column clues
		for (int col = 0; col < numCols; col++) {
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
	
	/**
	 * This constructor loads a puzzle for the player to play from a given file.
	 * 
	 * @param filename The puzzle file name.
	 */
	public PlayModel(String filename) {
		File puzzleFile = new File(Main.getPuzzleLocation() + filename);
		
		try {
			Scanner scanner = new Scanner(puzzleFile);
			
			//Set the size of the puzzle.
			int length;
			PuzzleSize size = PuzzleSize.valueOf(scanner.nextLine());
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
			
			//Initialize the clue arrays.
			initializeArrays(length, length);
			
			//Get each row clue.
			for (int row = 0; row < length; row++) {
				rowClues[row] = extractClue(scanner.nextLine().trim());
			}
			
			//Get each column clue.
			for (int col = 0; col < length; col++) {
				colClues[col] = extractClue(scanner.nextLine().trim());
			}
			
			resetTime();
			
			scanner.close();
		} catch (FileNotFoundException e) {
			//Display an error if the file cannot be read.
			String message = "ERROR: File cannot be read.";
			ErrorAlert errorAlert = new ErrorAlert(AlertType.ERROR, message);
			errorAlert.show();
		}
	}

	/**
	 * This method initializes the clue arrays to a set size, and the cell state array to a certain size
	 * as well as to empty.
	 * 
	 * @param numRows The number of rows in the puzzle.
	 * @param numCols The number of columns in the puzzle.
	 */
	private void initializeArrays(int numRows, int numCols) {
		cellStates = new CellState[numRows][numCols];
		rowClues = new int[numRows][];
		colClues = new int[numCols][];
		
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				setCellState(row, col, CellState.EMPTY);
			}
		}
	}
	
	/**
	 * This method returns a projection for a given cell state array. A projection is an
	 * array of integer values representing the clusters of filled values. For example,
	 * the array [FILLED, FILLED, EMPTY, FILLED, EMPTY] would project to [2, 1].
	 * 
	 * @param states The states to be projected.
	 * @return The projection of a collection of cell states.
	 */
	public List<Integer> project(CellState[] states) {
		List<Integer> result = new ArrayList<Integer>();
		int sum = 0;
		
		//Count and add the chains of true values.
		for (CellState state : states) {
			if (state == CellState.FILLED) {
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
	 * This method returns the solved state of a row.
	 * 
	 * @param rowIdx The row to be evaluated.
	 * @return The solved state of the row.
	 */
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
	
	/**
	 * This method returns the solved state of a column.
	 * 
	 * @param colIdx The column to be evaluated.
	 * @return The solved state of the column.
	 */
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
	
	/**
	 * This method returns the solved state of the puzzle.
	 * 
	 * @return The solved state of the puzzle.
	 */
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
	
	/**
	 * This method returns the array of row clues.
	 * 
	 * @return The row clues.
	 */
	public int[][] getRowClues() {
		return Arrays.copyOf(rowClues, rowClues.length);
	}
	
	/**
	 * This method returns the array of column clues.
	 * 
	 * @return The column clues.
	 */
	public int[][] getColClues() {
		return Arrays.copyOf(colClues, colClues.length);
	}
	
	/**
	 * This method returns the size of the puzzle, which is equal to both the number
	 * of rows and columns.
	 * 
	 * @return The size of the puzzle.
	 */
	@Override
	public int getSize() {
		return cellStates.length;
	}

	/**
	 * This method returns the current state of a given cell.
	 * 
	 * @param rowIdx The row the cell is in.
	 * @param colIdx The column the cell is in.
	 * @return The current state of the cell.
	 */
	@Override
	public CellState getCellState(int rowIdx, int colIdx) {
		return cellStates[rowIdx][colIdx];
	}

	/**
	 * This method sets the state of a given cell.
	 * 
	 * @param rowIdx The row the cell is in.
	 * @param colIdx The column the cell is in.
	 * @param state The desired new state of the cell.
	 */
	@Override
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		cellStates[rowIdx][colIdx] = state;
	}
	
	/**
	 * This method extracts a clue array from a string.
	 * 
	 * @param line The string containing the clue values.
	 * @return A clue array from a string.
	 */
	private int[] extractClue(String line) {
		if (line == null || line.isEmpty()) {
			return new int[] {0};
		}
		
		//Split the string into pieces.
		String[] nums = line.split(" ");
		int[] result = new int[nums.length];
		
		//Add the integer values in the string to an array.
		for (int i = 0; i < nums.length; i++) {
			result[i] = Integer.parseInt(nums[i]);
		}
		
		return result;
	}
	
	/**
	 * This method resets the start time of the puzzle.
	 */
	public static void resetTime() {
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * This method returns the difference between the start time and the current time.
	 * @return The difference between the start time and the current time.
	 */
	public static long getTotalTime() {
		return System.currentTimeMillis() - startTime;
	}
}
