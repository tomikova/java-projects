package hr.fer.zemris.linearna;

/**
 * Class models matrix view on given vector.
 * @author Tomislav
 *
 */

public class MatrixVectorView extends AbstractMatrix {
	
	/**
	 * Indicates if matrix should be looked as row or column matrix.
	 */
	private boolean asRowMatrix;
	/**
	 * Original vector.
	 */
	private IVector original;
	
	/**
	 * Constructor with two parameters.
	 * @param vector Original vector.
	 * @param asRowMatrix Indicates if matrix should be looked as row or column matrix.
	 */
	public MatrixVectorView(IVector vector, boolean asRowMatrix) {
		this.original = vector;
		this.asRowMatrix = asRowMatrix;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowsCount() {
		if (asRowMatrix) {
			return 1;
			
		}
		else {
			return original.getDimension();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColsCount() {
		if (asRowMatrix) {
			return original.getDimension();
		}
		else {
			return 1;	
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(int row, int col) {
		if (asRowMatrix) {
			if (row != 0) {
				throw new IllegalArgumentException();
			}
			return original.get(col);
		}
		else {
			if (col != 0) {
				throw new IllegalArgumentException();
			}
			return original.get(row);
			
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix set(int row, int col, double value) {
		if (asRowMatrix) {
			if (row != 0) {
				throw new IllegalArgumentException();
			}
			original.set(col, value);
		}
		else {
			if (col != 0) {
				throw new IllegalArgumentException();
			}
			original.set(row, value);
		}
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix copy() {
		IMatrix copy = newInstance(this.getRowsCount(), this.getColsCount());
		for (int i = 0, rows = this.getRowsCount(); i < rows; i++) {
			for (int j = 0, cols = this.getColsCount(); j < cols; j++) {
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
		return LinAlgDefaults.defaultMatrix(rows, cols);
	}
}
