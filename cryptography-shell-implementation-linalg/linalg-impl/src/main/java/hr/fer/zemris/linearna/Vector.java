package hr.fer.zemris.linearna;

/**
 * Class provides operations performed on vectors. It extends AbstractVector and its giving
 * implementations of some operations.
 * @author Tomislav
 *
 */
public class Vector extends AbstractVector {

	/**
	 * Vector elements.
	 */
	private double [] elements;
	/**
	 * Vector dimension.
	 */
	private int dimension;
	/**
	 * Indicates if vector is only for reading.
	 */
	private boolean readOnly;
	
	public Vector(double[] elements) {
		this(false, false, elements);
	}
	
	/**
	 * Constructor with three parameters.
	 * @param useGiven Indicates should given elements array be copied or not.
	 * @param readOnly Indicates if vector is only for reading.
	 * @param elements
	 */
	public Vector(boolean useGiven, boolean readOnly, double[] elements) {
		this.dimension = elements.length;
		if (useGiven) {
			this.elements = elements;
		}
		else {
			this.elements = new double[elements.length];
			for (int i = 0; i < elements.length; i++) {
				this.elements[i] = elements[i];
			}
		}
		this.readOnly = readOnly;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(int index) {
		return elements[index];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IVector set(int index, double value) {
		if (readOnly) {
			throw new UnmodifiableObjectException();
		}
		elements[index] = value;
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
			copy.set(i, elements[i]);
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
	
	/**
	 * Method for parsing vectors from given string.
	 * @param text Vector in text form.
	 * @return Created vector.
	 */
	public static Vector parseSimple(String text) {
		String[] split = text.split("\\s++");
		double[] elements = new double[split.length];
		for (int i = 0; i < split.length; i++) {
			elements[i] = Double.parseDouble(split[i]);
		}
		return new Vector(elements);
	}
	
}
