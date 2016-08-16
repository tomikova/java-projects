package hr.fer.zemris.java.fractals;

/**
 * Class models complex polynomial and provides operations on polynomials.
 * @author Tomislav
 *
 */

public class ComplexPolynomial {

	/**
	 * Polynomial factors.
	 */
	private Complex[] factors;

	/**
	 * Constructor with one parameter.
	 * @param factors Array of polynomial factors.
	 */
	public ComplexPolynomial(Complex ...factors) {
		this.factors = new Complex[factors.length];
		for (int i = 0; i < factors.length; i++) {
			if (factors[i] == null) {
				this.factors[i] = new Complex();
			}
			else {
				this.factors[i] = factors[i];
			}
		}
	}
	
	/**
	 * Method return order of polynomial.
	 * @return Order of polynomial.
	 */
	public short order() {
		return (short)(factors.length-1);
	}

	/**
	 * Method for polynomial multiplication.
	 * @param p Polynomial multiplier.
	 * @return Polynomial result of multiplication.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] mulFactors = new Complex[this.order()+p.order()+1];
		for (int i = 0; i <  this.factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				int order = i+j;
				Complex mulResult = this.factors[i].multiply(p.factors[j]);
				if (mulFactors[order] == null) {
					mulFactors[order] = mulResult;
				}
				else {
					mulFactors[order] = mulFactors[order].add(mulResult);
				}
			}
		}
		return new ComplexPolynomial(mulFactors);
	}

	/**
	 * Method for polynomial derivation.
	 * @return Polynomial result of derivation.
	 */
	public ComplexPolynomial derive() {
		int order = factors.length == 1 ? 1 : factors.length-1;
		Complex[] derFactors = new Complex[order];
		if (factors.length == 1) {
			derFactors[0] = new Complex();
		}
		else {
			for (int i = 1; i < factors.length; i++) {
				double re = i * factors[i].getRe();
				double im = i * factors[i].getIm();
				Complex complex = new Complex(re,im);
				derFactors[i-1] = complex;
			}
		}
		return new ComplexPolynomial(derFactors);
	}

	/**
	 * Method calculates value of polynomial in given point.
	 * @param z Point for polynomial value calculation.
	 * @return Value of polynomial in given point.
	 */
	public Complex apply(Complex z) {
		Complex complex = new Complex();
		for (int i = 0; i < factors.length; i++) {
			Complex zValue = z.power(i);
			Complex factor = factors[i];
			Complex mulResult = zValue.multiply(factor);
			complex = complex.add(mulResult);
		}
		return complex;
	}

	/**
	 * Method returns polynomial in string form.
	 * @return Polynomial in string form.
	 */
	@Override
	public String toString() {
		String output = "";
		if (factors.length > 0) {
			String complex = factors[0].toString();
			if (!complex.equals("")) {
				output += "("+complex+")" + (factors.length > 1 ? "+" : "");
			}
		}	
		for (int i = 1; i < factors.length; i++ ) {
			String complex = factors[i].toString();
			if (!complex.equals("")) {
				output += "("+complex+")" + (i == 1 ? "z" : "z^"+i) + (i < factors.length-1 ? "+" : "");
			}
		}
		if (output.equals("")) {
			output += "(0)";
		}
		return output;
	}
}
