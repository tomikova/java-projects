package hr.fer.zemris.java.fractals;

/**
 * Class represents polynomial in rooted form.
 * @author Tomislav
 *
 */

public class ComplexRootedPolynomial {

	/**
	 * Polynomial roots.
	 */
	private Complex[] roots;

	/**
	 * Constructor with one parameter.
	 * @param roots Polynomial roots.
	 */
	public ComplexRootedPolynomial(Complex ...roots) {
		this.roots = roots;
	}

	/**
	 * Method calculates value of polynomial in given point.
	 * @param z Point for polynomial value calculation.
	 * @return Value of polynomial in given point.
	 */
	public Complex apply(Complex z) {
		Complex result = null;
		if (roots.length > 0) {
			result = z.sub(roots[0]);
		}
		for (int i = 1; i < roots.length; i++) {
			result = result.multiply(z.sub(roots[i]));
		}
		return result;
	}

	/**
	 * Method transforms rooted polynomial to complex polynomial form.
	 * @return Complex poynomial.
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial polynomial = null;
		if (roots.length > 0) {
			polynomial = new ComplexPolynomial(roots[0], Complex.ONE);
		}
		for (int i = 1; i < roots.length; i++) {
			polynomial = polynomial.multiply(new ComplexPolynomial(roots[i], Complex.ONE));
		}
		return polynomial;
	}

	/**
	 * Method returns rooted polynomial in string form.
	 * @return Rooted polynomial in string form.
	 */
	@Override
	public String toString() {
		String output = "";
		for (int i = 0; i < roots.length; i++) {
			Complex root = roots[i].negate();
			output += "(z" + ((root.getRe() > 0 || (root.getRe() == 0 && root.getIm() >= 0)) ? "+" : "") 
					+ root.toString() + ")" + (i < roots.length-1 ? "*" : "");
		}
		return output;
	}
	
	/**
	 * Method finds index of closest root for given complex number z that is within treshold.
	 * @param z Complex number for comparison.
	 * @param treshold Maximum difference between root and z complex number.
	 * @return Index of closest root for given complex number z.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		double diff = -1;
		int index = -1;
		for (int i = 0; i < roots.length; i++) {
			double absRe = Math.abs(roots[i].getRe() - z.getRe());
			if (absRe < treshold) {
				double absIm = Math.abs(roots[i].getIm() - z.getIm());
				if (absIm < treshold) {
					if (diff < 0 || absRe+absIm < diff) {
						diff = absRe+absIm;
						index = i;
					}
				}
			}
		}
		return index;
	}
}
