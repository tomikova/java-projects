package hr.fer.zemris.java.student0036461026.hw12;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;

/**
 * Factory class for matrices and vectors.
 * @author Tomislav
 *
 */
public class LinAlgDefaults {

	/**
	 * Method creates new matrix.
	 * @param rows Matrix rows number.
	 * @param cols Matrix columns number.
	 * @return New matrix.
	 */
	public static IMatrix defaultMatrix(int rows, int cols) {
		throw new UnsupportedOperationException("Operation not supported");
	}

	/**
	 * Method creates new vector.
	 * @param dimension Vector dimension.
	 * @return New vector.
	 */
	public static IVector defaultVector(int dimension) {
		double[] elements = new double[dimension];
		return new Vector(elements);
	}
}
