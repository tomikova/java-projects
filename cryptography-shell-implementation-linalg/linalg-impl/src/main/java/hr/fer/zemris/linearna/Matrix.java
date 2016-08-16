package hr.fer.zemris.linearna;

/**
 * Class provides operations performed on matrices. It extends AbstractMatrix and its giving
 * implementations of some operations.
 * @author Tomislav
 *
 */

public class Matrix extends AbstractMatrix {
	
	/**
	 * Matrix elements.
	 */
	protected double[][] elements;
	/**
	 * Matrix rows number.
	 */
	protected int rows;
	/**
	 * Matrix colums number.
	 */
	protected int cols;
	
	/**
	 * Constructor with two parameters.
	 * @param rows Matrix rows number.
	 * @param cols Matrix  columns number.
	 */
	public Matrix(int rows, int cols) {
		this(rows, cols, new double [rows][cols], true);
	}
	
	/**
	 * Constructor with four parameters.
	 * @param rows Matrix rows number.
	 * @param cols Matrix colums number.
	 * @param elements Array of matrix elements.
	 * @param useGiven Indicates should given array of elements be copied or not.
	 */
	public Matrix(int rows, int cols, double[][] elements, boolean useGiven) {
		this.rows = rows;
		this.cols = cols;
		if (useGiven) {
			this.elements = elements;
		}
		else {
			this.elements = new double[rows][cols];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					this.elements[i][j] = elements[i][j];
				}
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowsCount() {
		return rows;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColsCount() {
		return cols;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(int row, int col) {
		return elements[row][col];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix set(int row, int col, double value) {
		elements[row][col] = value;
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix copy() {
		IMatrix copy = newInstance(rows, cols);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				copy.set(i, j, elements[i][j]);
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
	
	/**
	 * Method for parsing matrices from given string.
	 * @param text Matrx in text form.
	 * @return Created matrix.
	 * @throws IncompatibleOperandException If there is a mismatch in matrix dimensions.
	 */
	public static IMatrix parseSimple(String text) {
		String[] vectors = text.split("\\|");
		int rows = vectors.length;
		int dimension;
		double elements [][];
		if (rows != 0) {
			String[] vectorElements = vectors[0].split("\\s++");
			dimension = vectorElements.length;
			if (dimension != 0) {
				elements = new double[rows][dimension];
			}
			else {
				elements = new double[0][0];
			}
			for (int i = 0; i < rows; i++) {
				vectorElements = vectors[i].trim().split("\\s++");
				if (vectorElements.length != dimension) {
					throw new IncompatibleOperandException();
				}
				else {
					for (int j = 0; j < dimension; j++) {
						elements[i][j] = Double.parseDouble(vectorElements[j]);
					}
				}
			}
		}
		else {
			dimension = 0;
			elements = new double[0][0];
		}
		return new Matrix(rows, dimension, elements, true);
	}

}
