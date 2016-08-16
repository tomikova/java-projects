package hr.fer.zemris.linearna;

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
		return new Matrix(rows, cols);
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
