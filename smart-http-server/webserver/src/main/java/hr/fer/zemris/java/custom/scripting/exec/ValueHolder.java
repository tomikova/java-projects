package hr.fer.zemris.java.custom.scripting.exec;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Class holds original value of object (Double or Integer), double value of object 
 * and type of object which can be INTEGER or DOUBLE.
 * @author Tomislav
 *
 */
public class ValueHolder {

	/**
	 * Type enumeration, indicating is number Double or Integer.
	 * @author Tomislav
	 *
	 */
	public enum Type {
	/**
	 * indicating number is Double
	 */
	DOUBLE, 
	/**
	 * Indicating number is Integer
	 */
	INTEGER};

	/**
	 * Original value of object (Double or Integer).
	 */
	private Object value;
	/**
	 * double value of object.
	 */
	private double doubleValue;
	/**
	 * Type of object.
	 */
	private Type type;

	/**
	 * Constructor with one parameter.
	 * @param argument Object which will be stored as Double or Integer.
	 * Supported types are String, Double, Integer and null object.
	 * @throws IllegalArgumentException If object type is not supported.
	 * If String object can not be parsed as Integer or Double object.
	 */
	public ValueHolder(Object argument) {
		if (argument instanceof String) {
			try {
				if (((String) argument).contains(".") || ((String) argument).contains("E") ||
						((String) argument).contains("e")) {
					DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
					symbols.setDecimalSeparator('.');
					DecimalFormat formatter = new DecimalFormat("#.####", symbols);
					formatter.setMaximumFractionDigits(340);

					this.value = (Double)formatter.parse(
							formatter.format(Double.parseDouble((String)argument))).doubleValue();
					this.doubleValue = ((Double)value).doubleValue();
					this.type = Type.DOUBLE;
				}
				else {
					this.value = Integer.parseInt((String) argument);
					this.doubleValue = ((Integer)value).doubleValue();
					this.type = Type.INTEGER;
				}
			}catch(Exception ex) {
				throw new IllegalArgumentException("Type not supported");
			}
		}
		else if (argument instanceof Double) {
			this.value = argument;
			this.doubleValue = ((Double) argument).doubleValue();
			this.type = Type.DOUBLE;
		}
		else if (argument instanceof Integer) {
			this.value = argument;
			this.doubleValue = ((Integer) argument).doubleValue();
			this.type = Type.INTEGER;
		}
		else if (argument == null){
			this.value = Integer.valueOf(0);
			this.doubleValue = 0.0;
			this.type = Type.INTEGER;
		}
		else {
			throw new IllegalArgumentException("Type not supported");
		}
	}

	/**
	 * Method retrieves object stored as Integer or Double.
	 * @return Object stored as Integer or Double.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Method retrieves double value of object.
	 * @return double value of object.
	 */
	public double getDoubleValue() {
		return doubleValue;
	}

	/**
	 * Method retrieves object type.
	 * @return Object type.
	 */
	public Type getType() {
		return type;
	}
}
