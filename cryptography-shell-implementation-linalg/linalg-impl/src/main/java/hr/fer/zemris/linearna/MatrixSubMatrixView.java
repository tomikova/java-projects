package hr.fer.zemris.linearna;

/**
 * Class models sub-matrix view on original matrix.
 * @author Tomislav
 *
 */

public class MatrixSubMatrixView extends AbstractMatrix {

	/**
	 * Row indexes of sub matrix.
	 */
	private int[] rowIndexes;
	/**
	 * Column indexes of sub matrix.
	 */
	private int[] colIndexes;
	/**
	 * Original matrix.
	 */
	private IMatrix original; 

	/**
	 * Constructor with three parameters.
	 * @param original Original matrix.
	 * @param rows Row that will be reoved from original matrix.
	 * @param cols Column that will be reoved from original matrix.
	 */
	public MatrixSubMatrixView(IMatrix original, int rows, int cols) {
		if (rows > original.getRowsCount()-1 || cols > original.getColsCount()) {
			throw new IllegalArgumentException();
		}

		this.rowIndexes = new int[original.getRowsCount()-1];
		this.colIndexes = new int[original.getColsCount()-1];

		int numRow = 0;
		for (int i = 0; i < rowIndexes.length; i++) {
			if (i == rows) {
				numRow++;
			}
			this.rowIndexes[i] = numRow;
			numRow++;
		}

		int numCol = 0;
		for (int i = 0; i < colIndexes.length; i++) {
			if (i == cols) {
				numCol++;
			}
			this.colIndexes[i] = numCol;
			numCol++;
		}
		this.original = original;
	}

	/**
	 * Constructor with three parameters.
	 * @param original Original matrix.
	 * @param rowIndexes Row indexes of sub matrix.
	 * @param colIndexes Column indexes of sub matrix.
	 */
	private MatrixSubMatrixView(IMatrix original, int[] rowIndexes, int[] colIndexes) {
		int rows = original.getRowsCount();
		int cols = original.getColsCount();
		if (rowIndexes.length > rows || colIndexes.length > cols) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < rowIndexes.length; i++) {
			if (rowIndexes[i] > rows-1) {
				throw new IllegalArgumentException();
			}
		}
		for (int i = 0; i < colIndexes.length; i++) {
			if (colIndexes[i] > cols-1) {
				throw new IllegalArgumentException();
			}
		}
		this.rowIndexes = rowIndexes;
		this.colIndexes = colIndexes;
		this.original = original;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowsCount() {
		return rowIndexes.length;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColsCount() {
		return colIndexes.length;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(int row, int col) {
		return original.get(rowIndexes[row], colIndexes[col]);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix set(int row, int col, double value) {
		original.set(rowIndexes[row], colIndexes[col], value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix copy() {
		IMatrix copy = this.newInstance(rowIndexes.length, colIndexes.length);
		for (int i = 0; i < rowIndexes.length; i++) {
			for (int j = 0; j < colIndexes.length; j++) {
				copy.set(i, j, this.get(i, j));
			}
		}
		return copy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix newInstance(int rows, int cols) {
		return original.newInstance(rows, cols);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double[][] toArray() {
		double[][] array = new double[rowIndexes.length][colIndexes.length];
		for (int i = 0; i < rowIndexes.length; i++) {
			for (int j = 0; j < colIndexes.length; j++) {
				array[i][j] = this.get(i, j);
			}
		}
		return array;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		if (liveView) {		
			int[] newRowIndexes = new int[rowIndexes.length-1];
			int[] newColIndexes = new int[colIndexes.length-1];

			int rowNum = 0;
			for (int i = 0; i < newRowIndexes.length; i++) {
				if (i == row) {
					rowNum++;
				}
				newRowIndexes[i] = rowIndexes[rowNum];
				rowNum++;
			}	
			int colNum = 0;
			for (int i = 0; i < newColIndexes.length; i++) {
				if (i == col) {
					colNum++;
				}
				newColIndexes[i] = colIndexes[colNum];
				colNum++;
			}	
			return new MatrixSubMatrixView(original, newRowIndexes, newColIndexes);
		}
		else {
			IMatrix newM = this.newInstance(this.getRowsCount()-1, this.getColsCount()-1);
			int rowNum = 0;
			for (int i = 0, rows = this.getRowsCount()-1; i < rows; i++) {
				if (i == row) {
					rowNum++;
				}
				int colNum = 0;
				for (int j = 0, cols = this.getRowsCount()-1; j < cols; j++) {
					if (j == col) {
						colNum++;
					}
					newM.set(i, j, this.get(rowNum, colNum));
					colNum++;
				}
				rowNum++;
			}
			return newM;
		}
	}
}
