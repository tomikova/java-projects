package hr.fer.zemris.linearna;

/**
 * Class models transpose view of original matrix.
 * @author Tomislav
 *
 */
public class MatrixTransposeView extends AbstractMatrix {

	/**
	 * Original matrix.
	 */
	private IMatrix original;
	
	/**
	 * Constructor with one parameter.
	 * @param original Original matrix.
	 */
	public MatrixTransposeView(IMatrix original) {
		this.original = original;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowsCount() {
		return original.getColsCount();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColsCount() {
		return original.getRowsCount();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(int row, int col) {
		return original.get(col, row);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix set(int row, int col, double value) {
		original.set(col, row, value);
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
		return original.newInstance(rows, cols);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double[][] toArray() {
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		double[][] array = new double[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				array[i][j] = this.get(i, j);
			}
		}
		return array;
	}
}
