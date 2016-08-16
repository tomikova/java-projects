package hr.fer.zemris.java.student0036461026.hw12;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Class provides operations performed on vectors.
 * @author Tomislav
 *
 */
public abstract class AbstractVector implements IVector {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract double get(int index);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract IVector set(int index, double value);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract int getDimension();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract IVector copy();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract IVector newInstance(int dimension);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector add(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		for (int i = 0, dim = this.getDimension(); i < dim; i++) {
			this.set(i, this.get(i) + other.get(i));
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector copyPart(int n) {
		IVector copy = this.newInstance(n);
		int len = this.getDimension() < copy.getDimension() ? this.getDimension() : copy.getDimension();
		for (int i = 0; i < len; i++) {
			copy.set(i, this.get(i));
		}
		return copy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double cosine(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		double cosine = this.scalarProduct(other) /(this.norm()*other.norm());
		return cosine;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector nAdd(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		IVector newV = this.copy();
		for (int i = 0, dim = newV.getDimension(); i < dim; i++) {
			newV.set(i, newV.get(i) + other.get(i));
		}
		return newV;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector nFromHomogeneus() {
		double last = this.get(this.getDimension()-1);
		if (Double.compare(last, 0) == 0) {
			throw new IncompatibleOperandException();
		}
		IVector newV = this.copyPart(this.getDimension()-1);
		for (int i = 0, dim = newV.getDimension(); i < dim; i++) {
			newV.set(i, newV.get(i)/last);
		}
		return newV;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector nNormalize() {
		IVector newV = this.copy();
		double norm = newV.norm();
		for (int i = 0, dim = newV.getDimension(); i < dim; i++) {
			newV.set(i, newV.get(i)/norm);
		}
		return newV;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double norm() {
		double norm = 0;
		for (int i = 0, dim = this.getDimension(); i < dim; i++) {
			norm += Math.pow(this.get(i), 2);
		}
		norm = Math.sqrt(norm);
		return norm;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector normalize() {
		double norm = this.norm();
		if (Double.compare(norm, 0) == 0) {
			throw new UnsupportedOperationException("Nul-vector can't be normalized.");
		}
		for (int i = 0, dim = this.getDimension(); i < dim; i++) {
			this.set(i, this.get(i)/norm);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector nScalarMultiply(double byValue) {
		IVector newV = this.copy();
		for (int i = 0, dim = newV.getDimension(); i < dim; i++) {
			newV.set(i, newV.get(i)*byValue);
		}
		return newV;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector nSub(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		IVector newV = this.copy();
		for (int i = 0, dim = newV.getDimension(); i < dim; i++) {
			newV.set(i, newV.get(i) - other.get(i));
		}
		return newV;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector nVectorProduct(IVector other)
			throws IncompatibleOperandException {
		if (this.getDimension() != 3) {
			throw new IncompatibleOperandException();
		}
		if (other.getDimension() != 3) {
			throw new IncompatibleOperandException();
		}
		IVector newV = this.newInstance(3);
		newV.set(0, this.get(1)*other.get(2)-this.get(2)*other.get(1));
		newV.set(1, this.get(2)*other.get(0)-this.get(0)*other.get(2));
		newV.set(2, this.get(0)*other.get(1)-this.get(1)*other.get(0));
		return newV;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector scalarMultiply(double byValue) {
		for (int i = 0, dim = this.getDimension(); i < dim; i++) {
			this.set(i, this.get(i)*byValue);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double scalarProduct(IVector other)
			throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		int scalarProduct = 0;
		for (int i = 0, dim = this.getDimension(); i < dim; i++) {
			scalarProduct += this.get(i) * other.get(i);
		}
		return scalarProduct;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector sub(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		for (int i = 0, dim = this.getDimension(); i < dim; i++) {
			this.set(i, this.get(i) - other.get(i));
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double[] toArray() {
		double[] vectorArray = new double[this.getDimension()];
		for (int i = 0, dim = this.getDimension(); i < dim; i++) {
			vectorArray[i] = this.get(i);
		}
		return vectorArray;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix toColumnMatrix(boolean liveView) {
		throw new UnsupportedOperationException("Operation not supported");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMatrix toRowMatrix(boolean liveView) {
		throw new UnsupportedOperationException("Operation not supported");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return toString(3);
	}

	/**
	 * Method returns formatted vector output.
	 * @param n Number of decimal spaces for vector numbers.
	 * @return Formatted vector.
	 */
	public String toString(int n) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
		symbols.setDecimalSeparator('.');
		DecimalFormat formatter = new DecimalFormat("#.#",symbols);
		formatter.setMinimumFractionDigits(n);
		formatter.setMaximumFractionDigits(n);

		String output = "";
		int dim = this.getDimension();
		for( int i = 0; i < dim; i++) {
			if (i != dim -1) {
				if (i == 0) {
					output += "[" + formatter.format(this.get(i)) + ", ";
				}
				else {
					output += formatter.format(this.get(i)) + ", ";
				}
			}
			else if (i == 0) {
				output += "[" + formatter.format(this.get(i)) + "]";
			}
			else {
				output += formatter.format(this.get(i)) + "]";
			}
		}

		return output;
	}

}
