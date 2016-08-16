package hr.fer.zemris.linearna;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Class provides operations performed on matrices.
 * @author Tomislav
 *
 */
public abstract class AbstractMatrix implements IMatrix {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract int getRowsCount();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract int getColsCount();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract double get(int row, int col);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract IMatrix set(int row, int col, double value);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract IMatrix copy();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract IMatrix newInstance(int rows, int cols);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix add(IMatrix other) {
		if (this.getRowsCount() != other.getRowsCount() || this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException();
		}
		for (int i = 0, rows = this.getRowsCount(); i < rows; i++) {
			for (int j = 0, cols = this.getColsCount(); j < cols; j++) {
				this.set(i, j, this.get(i, j) + other.get(i, j));
			}
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double determinant() throws IncompatibleOperandException {
		if (this.getRowsCount() != this.getColsCount()) {
			throw new IncompatibleOperandException();
		}	
		int order = this.getRowsCount();
		return calculateDeterminant(this, order);
	}

	/**
	 * Help method for calculating matrix determinant.
	 * Used algorithm is based on matrix minors.
	 * @see {@linktourl http://en.wikipedia.org/wiki/Minor_(linear_algebra)}
	 * @param matrix Matrix.
	 * @param order Order of matrix.
	 * @return Matrix determinant.
	 */
	private double calculateDeterminant(IMatrix matrix, int order) {

		double det = 0; 
		int p, h, k, i, j;
		IMatrix tmp = this.newInstance(matrix.getRowsCount(), matrix.getColsCount());

		if(order == 1) {
			return matrix.get(0, 0);
		} 
		else if(order == 2) {
			det= matrix.get(0, 0)*matrix.get(1, 1)-matrix.get(0, 1)*matrix.get(1, 0);
			return det;
		} 
		else {
			for(p = 0; p < order; p++) {
				h = 0;
				k = 0;
				for(i = 1; i < order; i++) {
					for(j=0; j < order; j++) {
						if(j == p) {
							continue;
						}
						tmp.set(h, k, matrix.get(i, j));
						k++;
						if(k == order-1) {
							h++;
							k = 0;
						}
					}
				}
				det = det + matrix.get(0, p)*Math.pow(-1, p)*calculateDeterminant(tmp,order-1);
			}
			return det;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix makeIdentity() {
		if (this.getRowsCount() != this.getColsCount()) {
			throw new IncompatibleOperandException();
		}
		for (int i = 0, rows = this.getRowsCount(); i < rows; i++) {
			for (int j = 0, cols = this.getColsCount(); j < cols; j++) {
				if (i-j == 0) {
					this.set(i, j, 1);
				}
				else {
					this.set(i, j, 0);
				}
			}
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix nAdd(IMatrix other) {
		if (this.getRowsCount() != other.getRowsCount() || this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException();
		}
		IMatrix newM = this.copy();
		for (int i = 0, rows = newM.getRowsCount(); i < rows; i++) {
			for (int j = 0, cols = newM.getColsCount(); j < cols; j++) {
				newM.set(i, j, newM.get(i, j) + other.get(i, j));
			}
		}
		return newM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix nInvert() {
		if (this.getRowsCount() != this.getColsCount()) {
			throw new IncompatibleOperandException();
		}	

		double determinant = this.determinant();

		if (Math.abs(determinant-0) < 1e-15) {
			throw new UnsupportedOperationException("Inverse of matrix is not possible.");
		}

		int order = this.getRowsCount();

		IMatrix cofactorMatrix = cofactor(order);

		IMatrix cofactorMatrixT = cofactorMatrix.nTranspose(false);

		IMatrix inverse = this.newInstance(order, order);

		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++) {
				inverse.set(i, j, cofactorMatrixT.get(i, j)/determinant);
			}
		}	
		return inverse;
	}

	/**
	 * Method calculates matrix of cofactors of given matrix.
	 * @see {@linktourl http://en.wikipedia.org/wiki/Minor_(linear_algebra)#Matrix_of_cofactors}
	 * @param order Order of matrix.
	 * @return Matrix of cofactors.
	 */
	private IMatrix cofactor(int order) {

		IMatrix pom = this.newInstance(this.getRowsCount(), this.getColsCount());
		IMatrix cofactorMatrix = this.newInstance(this.getRowsCount(), this.getColsCount());

		int p,q,m,n,i,j;
		for (q = 0; q < order; q++) {
			for (p = 0; p < order; p++) {
				m=0;
				n=0;
				for (i = 0; i < order; i++) {
					for (j = 0; j < order; j++) {
						if (i != q && j != p) {
							pom.set(m, n, this.get(i, j));
							if (n <  order-2) {
								n++;
							}
							else {
								n = 0;
								m++;
							}
						}
					}
				}
				cofactorMatrix.set(q, p, Math.pow(-1, q + p)*calculateDeterminant(pom,order-1));
			}
		}
		return cofactorMatrix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix nMultiply(IMatrix other) {
		if (this.getColsCount() != other.getRowsCount()) {
			throw new UnsupportedOperationException("Multiplying matrices not possible.");
		}

		IMatrix newM = this.newInstance(this.getRowsCount(), other.getColsCount());

		int firstDim = this.getRowsCount();
		int secondDim = this.getColsCount();
		int thirdDim = other.getColsCount();

		for (int i = 0; i < firstDim; i++) {
			for (int j = 0; j < thirdDim; j++) {
				for (int k = 0; k < secondDim; k++) {
					newM.set(i, j, newM.get(i, j) + (this.get(i, k)*other.get(k, j)));
				}
			}
		}
		return newM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix nScalarMultiply(double value) {
		IMatrix newM = this.copy();
		for (int i = 0, rows = newM.getRowsCount(); i < rows; i++) {
			for (int j = 0, cols = newM.getColsCount(); j < cols; j++) {
				newM.set(i, j, newM.get(i, j) * value);
			}
		}
		return newM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix nSub(IMatrix other) {
		if (this.getRowsCount() != other.getRowsCount() || this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException();
		}
		IMatrix newM = this.copy();
		for (int i = 0, rows = newM.getRowsCount(); i < rows; i++) {
			for (int j = 0, cols = newM.getColsCount(); j < cols; j++) {
				newM.set(i, j, newM.get(i, j) - other.get(i, j));
			}
		}
		return newM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix nTranspose(boolean liveView) {
		if (liveView) {
			return new MatrixTransposeView(this);
		}
		else {
			IMatrix transMatrix = this.newInstance(this.getColsCount(), this.getRowsCount());
			for (int i = 0, rows = this.getRowsCount(); i < rows; i++) {
				for (int j = 0, cols = this.getColsCount(); j < cols; j++) {
					transMatrix.set(j, i, this.get(i, j));
				}
			}
			return transMatrix;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix scalarMultiply(double value) {
		for (int i = 0, rows = this.getRowsCount(); i < rows; i++) {
			for (int j = 0, cols = this.getColsCount(); j < cols; j++) {
				this.set(i, j, this.get(i, j) * value);
			}
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix sub(IMatrix other) {
		if (this.getRowsCount() != other.getRowsCount() || this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException();
		}
		for (int i = 0, rows = this.getRowsCount(); i < rows; i++) {
			for (int j = 0, cols = this.getColsCount(); j < cols; j++) {
				this.set(i, j, this.get(i, j) - other.get(i, j));
			}
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		if (liveView) {
			return new MatrixSubMatrixView(this,row,col);
		}
		else {
			IMatrix newM = this.newInstance(this.getRowsCount()-1, this.getColsCount()-1);
			int rowNum = 0;
			for (int i = 0, rows = this.getRowsCount()-1; i < rows; i++) {
				if (i == row) {
					rowNum++;
				}
				int colNum = 0;
				for (int j = 0, cols = this.getColsCount()-1; j < cols; j++) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector toVector(boolean liveView) {
		if (liveView) {
			return new VectorMatrixView(this);
		}
		else {
			IMatrix copy = this.copy();
			return new VectorMatrixView(copy);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return toString(3);
	}

	/**
	 * Method returns formatted matrix output.
	 * @param n Number of decimal spaces for matrix numbers.
	 * @return Formatted matrix.
	 */
	public String toString(int n) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
		symbols.setDecimalSeparator('.');
		DecimalFormat formatter = new DecimalFormat("#.#",symbols);
		formatter.setMinimumFractionDigits(n);
		formatter.setMaximumFractionDigits(n);

		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		String output = "";
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (j == cols-1) {
					if (cols == 1) {
						output += "[";
					}
					if (i != rows-1) {
						output += formatter.format(this.get(i, j)) + "]\n";
					}
					else {
						output += formatter.format(this.get(i, j)) + "]";
					}
				}
				else if (j == 0) {
					output += "[" + formatter.format(this.get(i, j)) + ", ";
				}
				else {
					output += formatter.format(this.get(i, j)) + ", ";
				}
			}
		}
		return output;
	}
}
