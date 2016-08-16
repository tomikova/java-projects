package hr.fer.zemris.java.gui.layouts;

/**
 * Class models position constraint for calculator components.
 * @author Tomislav
 *
 */

public class RCPosition {

	/**
	 * Component row.
	 */
	private final int row;
	/**
	 * Component column.
	 */
	private final int column;
	
	/**
	 * Constructor with two parameters.
	 * @param row Component row.
	 * @param column Component column.
	 */
	public RCPosition(int row, int column) {
		checkInput(row, column);
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Constructor with one parameter.
	 * @param input Row/column position constraint in string format.
	 */
	public RCPosition(String input) {
		String[] nums = input.split(",");
		if (nums.length > 2) {
			throw new IllegalArgumentException("Invalid format.");
		}
		int row = Integer.parseInt(nums[0]);
		int column = Integer.parseInt(nums[1]);
		checkInput(row, column);
		this.row = row;
		this.column = column;
		
	}
	
	/**
	 * Method checks if row and column are inside allowed dimensions.
	 * @param row Component row.
	 * @param column Component column.
	 */
	private void checkInput(int row, int column) {
		if (row < 1 || row > 5 || column < 1 || column > 7) {
			throw new IllegalArgumentException("Illegal column or row index.");
		}
		
		if (row == 1 && column > 1 && column < 6 ) {
			throw new IllegalArgumentException("Illegal column index.");
		}
	}

	/**
	 * Method returns component row.
	 * @return Copmonent row.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Method returns component column.
	 * @return Component column.
	 */
	public int getColumn() {
		return column;
	}
	
}
