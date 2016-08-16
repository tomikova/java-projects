package hr.fer.zemris.java.student0036461026.hw07;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.UnmodifiableObjectException;
import hr.fer.zemris.linearna.Vector;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for AbstractVector, Vector and VectorMatrixView classes.
 * @author Tomislav
 *
 */
public class VectorTest 
{
	@Test
    public void parseSimpleTest()
    {
        IVector vector = Vector.parseSimple("4 5 7.69 8.8774 3");
        Assert.assertEquals("[4.000, 5.000, 7.690, 8.877, 3.000]", vector.toString());
        IVector vector2 = Vector.parseSimple("4.45");
        Assert.assertEquals("[4.450]", vector2.toString());
    }
	
	@Test
    public void parseSimpleCustomRoundingTest()
    {
        Vector vector = Vector.parseSimple("4 5 7.69 8.8774 3");
        Assert.assertEquals("[4.00, 5.00, 7.69, 8.88, 3.00]", vector.toString(2));
    }
	
	@Test
	public void defaultConstructorTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
        Vector vector = new Vector(elements);
        Assert.assertEquals("[5.30, 8.81, 2.00, 100.91]", vector.toString(2));
        elements[0] = 7.2;
        Assert.assertEquals("[5.30, 8.81, 2.00, 100.91]", vector.toString(2));
        vector.set(1, 5);
        Assert.assertEquals("8.81", String.valueOf(elements[1]));
    }
	
	@Test
	public void customConstructorTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
        Vector vector = new Vector(true,false,elements);
        Assert.assertEquals("[5.30, 8.81, 2.00, 100.91]", vector.toString(2));
        elements[0] = 7.2;
        Assert.assertEquals("[7.20, 8.81, 2.00, 100.91]", vector.toString(2));
        vector.set(1, 1.45);
        Assert.assertEquals("1.45", String.valueOf(elements[1]));
    }
	
	@Test(expected=UnmodifiableObjectException.class)
	public void customConstructorReadOnlyTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
        Vector vector = new Vector(true,true,elements);
        Assert.assertEquals("[5.30, 8.81, 2.00, 100.91]", vector.toString(2));
        vector.set(1, 1.45);
    }
	
	@Test
	public void getTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
        Vector vector = new Vector(elements);
        Assert.assertEquals("100.91", String.valueOf(vector.get(3)));
    }
	
	@Test
	public void setTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
        Vector vector = new Vector(elements);
        vector.set(3, 12.52);
        Assert.assertEquals("12.52", String.valueOf(vector.get(3)));
    }
	
	@Test
	public void getDimensionTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
        Vector vector = new Vector(elements);
        Assert.assertEquals(4, vector.getDimension());
    }
	
	@Test
	public void copyTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
        Vector vector = new Vector(elements);
        Vector copy = (Vector)vector.copy();
        vector.set(2, 3);
        Assert.assertEquals("[5.30, 8.81, 3.00, 100.91]", vector.toString(2));
        Assert.assertEquals("[5.30, 8.81, 2.00, 100.91]", copy.toString(2));
    }
	
	@Test
	public void newInstanceTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
        Vector vector = new Vector(elements);
        Vector newV = (Vector)vector.newInstance(25);
        Assert.assertEquals(4, vector.getDimension());
        Assert.assertEquals(25, newV.getDimension());
        Assert.assertEquals("0.0", String.valueOf(newV.get(5)));
    }
	
	@Test
	public void addTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
		double[] elements2 = new double[]{1, 0, 2, 1.03};
        Vector vector = new Vector(elements);
        Vector vector2 = new Vector(elements2);
        Vector vector3 = (Vector)vector.add(vector2);
        Assert.assertEquals("[6.30, 8.81, 4.00, 101.94]", vector.toString(2));
        Assert.assertTrue(vector.equals(vector3));
    }
	
	@Test(expected=IncompatibleOperandException.class)
	public void addDimensionMismatchTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
		double[] elements2 = new double[]{1, 0, 2, 1.03, 5};
        Vector vector = new Vector(elements);
        Vector vector2 = new Vector(elements2);
        vector.add(vector2);
    }
	
	@Test
	public void nAddTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
		double[] elements2 = new double[]{1, 0, 2, 1.03};
        Vector vector = new Vector(elements);
        Vector vector2 = new Vector(elements2);
        Vector vector3 = (Vector)vector.nAdd(vector2);
        
        Assert.assertEquals("[5.30, 8.81, 2.00, 100.91]", vector.toString(2));
        Assert.assertEquals("[6.30, 8.81, 4.00, 101.94]", vector3.toString(2));
        Assert.assertFalse(vector.equals(vector3));
    }
	
	@Test(expected=IncompatibleOperandException.class)
	public void nAddDimensionMismatchTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
		double[] elements2 = new double[]{1, 0, 2, 1.03, 5};
        Vector vector = new Vector(elements);
        Vector vector2 = new Vector(elements2);
        vector.nAdd(vector2);
    }
	
	@Test
	public void subTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
		double[] elements2 = new double[]{1, 0, 2, 1.03};
        Vector vector = new Vector(elements);
        Vector vector2 = new Vector(elements2);
        Vector vector3 = (Vector)vector.sub(vector2);
        Assert.assertEquals("[4.30, 8.81, 0.00, 99.88]", vector.toString(2));
        Assert.assertTrue(vector.equals(vector3));
    }
	
	@Test(expected=IncompatibleOperandException.class)
	public void subDimensionMismatchTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
		double[] elements2 = new double[]{1, 0, 2, 1.03, 5};
        Vector vector = new Vector(elements);
        Vector vector2 = new Vector(elements2);
        vector.sub(vector2);
    }
	
	@Test
	public void nSubTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
		double[] elements2 = new double[]{1, 0, 2, 1.03};
        Vector vector = new Vector(elements);
        Vector vector2 = new Vector(elements2);
        Vector vector3 = (Vector)vector.nSub(vector2);
        
        Assert.assertEquals("[5.30, 8.81, 2.00, 100.91]", vector.toString(2));
        Assert.assertEquals("[4.30, 8.81, 0.00, 99.88]", vector3.toString(2));
        Assert.assertFalse(vector.equals(vector3));
    }
	
	@Test(expected=IncompatibleOperandException.class)
	public void nSubDimensionMismatchTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
		double[] elements2 = new double[]{1, 0, 2, 1.03, 5};
        Vector vector = new Vector(elements);
        Vector vector2 = new Vector(elements2);
        vector.nSub(vector2);
    }
	
	@Test
	public void copyPartTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
        Vector vector = new Vector(elements);
        Vector vector2 = (Vector)vector.copyPart(2);
        
        Assert.assertEquals("[5.30, 8.81]", vector2.toString(2));
    }
	
	@Test
	public void normTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 100.91};
        Vector vector = new Vector(elements);
        double norm = vector.norm();
        Assert.assertEquals(101.452127,norm,1e-6);
    }
	
	@Test
	public void normalizeTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 10.5};
        Vector vector = new Vector(elements);
        vector.normalize();
        Assert.assertEquals("[0.357, 0.594, 0.135, 0.708]", vector.toString(3));
    }
	
	@Test(expected=UnsupportedOperationException.class)
	public void normalizeNulTest()
    {
		double[] elements = new double[]{0, 0, 0, 0};
        Vector vector = new Vector(elements);
        vector.normalize();
    }
	
	@Test
	public void nNormalizeTest()
    {
		double[] elements = new double[]{5.3, 8.81, 2, 10.5};
        Vector vector = new Vector(elements);
        Vector vector2 = (Vector)vector.nNormalize();
        
        Assert.assertEquals("[5.300, 8.810, 2.000, 10.500]", vector.toString(3));
        Assert.assertEquals("[0.357, 0.594, 0.135, 0.708]", vector2.toString(3));
        Assert.assertFalse(vector.equals(vector2));
    }
	
	@Test
	public void nFromHomogeneusTest()
	{
		double[] elements = new double[]{5.3, 8.81, 2, 10.5};
        Vector vector = new Vector(elements);
        Vector vector2 = (Vector)vector.nFromHomogeneus();
        
        Assert.assertEquals("[0.505, 0.839, 0.190]", vector2.toString(3));
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void nFromHomogeneusLastIsZeroTest()
	{
		double[] elements = new double[]{5.3, 8.81, 2, 10.5, 0};
        Vector vector = new Vector(elements);
        vector.nFromHomogeneus();
	}
	
	@Test
	public void scalarMultiplyTest()
	{
		double[] elements = new double[]{5.3, 8.81, 2, 10.5};
        Vector vector = new Vector(elements);
        vector.scalarMultiply(2);
        
        Assert.assertEquals("[10.600, 17.620, 4.000, 21.000]", vector.toString(3));
	}
	
	@Test
	public void nScalarMultiplyTest()
	{
		double[] elements = new double[]{5.3, 8.81, 2, 10.5};
        Vector vector = new Vector(elements);
        Vector vector2 = (Vector)vector.nScalarMultiply(2);
        
        Assert.assertEquals("[5.300, 8.810, 2.000, 10.500]", vector.toString(3));
        Assert.assertEquals("[10.600, 17.620, 4.000, 21.000]", vector2.toString(3));
        Assert.assertFalse(vector.equals(vector2));
	}
	
	@Test
	public void nVectorProductTest()
	{
		double[] elements = new double[]{5.3, 8.81, 2};
		double[] elements2 = new double[]{1, 0, 2};
        Vector vector = new Vector(elements);
        Vector vector2 = new Vector(elements2);
        
        Vector vector3 = (Vector)vector.nVectorProduct(vector2);
        
        Assert.assertEquals("[17.620, -8.600, -8.810]", vector3.toString(3));
	}
	
	
	@Test(expected=IncompatibleOperandException.class)
	public void nVectorProductDimensionNotThreeTest()
	{
		double[] elements = new double[]{5.3, 8.81, 2, 4};
		double[] elements2 = new double[]{1, 0, 2};
        Vector vector = new Vector(elements);
        Vector vector2 = new Vector(elements2);
        
        vector.nVectorProduct(vector2);   
	}
	
	@Test
	public void scalarProductTest()
	{
		double[] elements = new double[]{1, 1, 2};
		double[] elements2 = new double[]{1, 5, 3};
        Vector vector = new Vector(elements);
        Vector vector2 = new Vector(elements2);
        
        double product = vector.scalarProduct(vector2);
        
        Assert.assertEquals(12.000, product, 1e-3);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void scalarProductDimensionMismatchTest()
	{
		double[] elements = new double[]{1, 1, 2, 5};
		double[] elements2 = new double[]{1, 5, 3};
        Vector vector = new Vector(elements);
        Vector vector2 = new Vector(elements2);
        
        vector.scalarProduct(vector2);
	}
	
	@Test
	public void cosineTest()
	{
		double[] elements = new double[]{1, 1, 2};
		double[] elements2 = new double[]{1, 5, 3};
        Vector vector = new Vector(elements);
        Vector vector2 = new Vector(elements2);
        
        double cosine = vector.cosine(vector2);
        
        Assert.assertEquals(0.828079, cosine, 1e-6);
	}
	
	@Test(expected=IncompatibleOperandException.class)
	public void cosineDimensionMismatchTest()
	{
		double[] elements = new double[]{1, 1, 2};
		double[] elements2 = new double[]{1, 5, 3, 4};
        Vector vector = new Vector(elements);
        Vector vector2 = new Vector(elements2);
        
       vector.cosine(vector2);
	}
	
	@Test
	public void toArrayTest()
	{
		double[] elements = new double[]{1.2, 1.5, 8.25};
        Vector vector = new Vector(elements);
        double[] array = vector.toArray();
        
        Assert.assertEquals(1.200, array[0], 1e-4);
        Assert.assertEquals(1.500, array[1], 1e-4);
        Assert.assertEquals(8.250, array[2], 1e-4);
	}
	
	@Test
	public void toColumnMatrixTest()
	{
		double[] elements = new double[]{1.2, 1.5, 8.25};
        Vector vector = new Vector(elements);
        IMatrix matrix = vector.toColumnMatrix(false);
        
        Assert.assertEquals("[1.200]\n[1.500]\n[8.250]", matrix.toString());
        Assert.assertEquals(1.5, matrix.get(1, 0), 1e-2);
        matrix.set(1, 0, 7);
        Assert.assertEquals(7.0, matrix.get(1, 0), 1e-2);
        
        IMatrix copy = matrix.copy();
        Assert.assertEquals("[1.200]\n[7.000]\n[8.250]", copy.toString());
        Assert.assertFalse(matrix.equals(copy));
        IMatrix newInstance = matrix.newInstance(2, 2);
        Assert.assertEquals("[0.000, 0.000]\n[0.000, 0.000]", newInstance.toString());
        
	}
	
	@Test
	public void toRowMatrixTest()
	{
		double[] elements = new double[]{1.2, 1.5, 8.25};
        Vector vector = new Vector(elements);
        IMatrix matrix = vector.toRowMatrix(false);
        
        Assert.assertEquals("[1.200, 1.500, 8.250]", matrix.toString());
        Assert.assertEquals(8.25, matrix.get(0, 2), 1e-2);
        matrix.set(0, 2, 11);
        Assert.assertEquals(11.0, matrix.get(0, 2), 1e-2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void toRowMatrixGetRowNotZeroTest()
	{
		double[] elements = new double[]{1.2, 1.5, 8.25};
        Vector vector = new Vector(elements);
        IMatrix matrix = vector.toRowMatrix(false);
        matrix.get(4, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void toRowMatrixSetRowNotZeroTest()
	{
		double[] elements = new double[]{1.2, 1.5, 8.25};
        Vector vector = new Vector(elements);
        IMatrix matrix = vector.toRowMatrix(false);
        matrix.set(5, 2, 3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void toColumnMatrixGetColumnNotZeroTest()
	{
		double[] elements = new double[]{1.2, 1.5, 8.25};
        Vector vector = new Vector(elements);
        IMatrix matrix = vector.toColumnMatrix(false);
        matrix.get(1, 5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void toColumnMatrixSetColumnNotZeroTest()
	{
		double[] elements = new double[]{1.2, 1.5, 8.25};
        Vector vector = new Vector(elements);
        IMatrix matrix = vector.toColumnMatrix(false);
        matrix.set(1, 2, 3);
	}
}
