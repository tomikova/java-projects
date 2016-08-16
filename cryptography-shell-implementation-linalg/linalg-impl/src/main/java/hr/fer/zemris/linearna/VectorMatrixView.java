package hr.fer.zemris.linearna;

/**
 * Class models vector view on given matrix.
 * @author Tomislav
 *
 */
public class VectorMatrixView extends AbstractVector {
	
	/**
	 * Vector dimension.
	 */
	private int dimension;
	/**
	 * Indicates is orginal matrix row or colum matrix.
	 */
	private boolean rowMatrix;
	/**
	 * Original matrix.
	 */
	private IMatrix original;
	
	/**
	 * Constructor with one parameter.
	 * @param original Original matrix.
	 * @throws IllegalArgumentException If given matrix is not row or column matrix.
	 */
	public VectorMatrixView(IMatrix original) {
		int rows = original.getRowsCount();
		int cols = original.getColsCount();
		this.original = original;
		if (rows >= 1 && cols == 1) {
			this.rowMatrix = true;
			this.dimension = rows;
		}
		else if (cols >= 1 && rows == 1) {
			this.rowMatrix = false;
			this.dimension = cols;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(int index) {
		if (rowMatrix) {
			return original.get(index, 0);
		}
		else {
			return original.get(0, index);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector set(int index, double value) {
		if (rowMatrix) {
			original.set(index, 0, value);
		}
		else {
			original.set(0, index, value);
		}
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getDimension() {
		return dimension;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector copy() {
		IVector copy = newInstance(dimension);
		for (int i = 0; i < dimension; i++) {
			copy.set(i, this.get(i));
		}
		return copy;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector newInstance(int dimension) {
		return LinAlgDefaults.defaultVector(dimension);
	}	
}
