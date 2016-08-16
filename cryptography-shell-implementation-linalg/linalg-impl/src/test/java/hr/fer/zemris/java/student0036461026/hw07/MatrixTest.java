package hr.fer.zemris.java.student0036461026.hw07;

/**
 * Unit tests for AbstractMatrix, Matrix, MatrixSubMatrixView, MatrixTransposeView and
 * MatrixVectorView classes.
 */
import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Matrix;

import org.junit.Assert;
import org.junit.Test;

public class MatrixTest {
	
	@Test
    public void parseSimpleTest()
    {
        Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
        Assert.assertEquals("[4.00, 2.00, 4.00]\n[5.20, 3.00, 7.70]", matrix.toString(2));
    }

	@Test(expected=IncompatibleOperandException.class)
    public void parseSimpleDimensionMismatchTest()
    {
      Matrix.parseSimple("4 2 4 | 5.2 3 7.7 5");
    }
	
	@Test
    public void defaultConstructorTest()
    {
        Matrix matrix = new Matrix (3,3);
        Assert.assertEquals("[0.00, 0.00, 0.00]\n[0.00, 0.00, 0.00]\n[0.00, 0.00, 0.00]", matrix.toString(2));
    }
	
	@Test
    public void customConstructorTest()
    {
		double[][] elements = new double[][]{{4,7,8},{5.3,2,8.5},{9,5.8,3}};
        Matrix matrix = new Matrix (3,3,elements,false);
        Assert.assertEquals("[4.00, 7.00, 8.00]\n[5.30, 2.00, 8.50]\n[9.00, 5.80, 3.00]", matrix.toString(2));
        
        elements[0][0] = 5;
        Assert.assertEquals("[4.00, 7.00, 8.00]\n[5.30, 2.00, 8.50]\n[9.00, 5.80, 3.00]", matrix.toString(2));
        
        matrix.set(0, 0, 7);
        Assert.assertEquals("[7.00, 7.00, 8.00]\n[5.30, 2.00, 8.50]\n[9.00, 5.80, 3.00]", matrix.toString(2));
        Assert.assertEquals(5.0,elements[0][0],1e-2);
        
        Matrix matrix2 = new Matrix (3,3,elements,true);
        Assert.assertEquals("[5.00, 7.00, 8.00]\n[5.30, 2.00, 8.50]\n[9.00, 5.80, 3.00]", matrix2.toString(2));
        elements[1][1] = 4.3;
        Assert.assertEquals("[5.00, 7.00, 8.00]\n[5.30, 4.30, 8.50]\n[9.00, 5.80, 3.00]", matrix2.toString(2));
        matrix2.set(2, 0, 1.12);
        Assert.assertEquals(1.12,elements[2][0],1e-3);
    }
	
	
	@Test
    public void getRowsCountTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
        Assert.assertEquals(2, matrix.getRowsCount());
    }
	
	@Test
    public void getColsCountTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
        Assert.assertEquals(3, matrix.getColsCount());
    }
	
	@Test
    public void getTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
        Assert.assertEquals(5.2,matrix.get(1, 0),1e-2);
    }
	
	@Test
    public void setTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
		matrix.set(0, 2, 100.13);
        Assert.assertEquals(100.13,matrix.get(0, 2),1e-2);
    }
	
	@Test
    public void copyTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
		Matrix matrix2 = (Matrix) matrix.copy();
		Assert.assertEquals("[4.00, 2.00, 4.00]\n[5.20, 3.00, 7.70]", matrix2.toString(2));
		Assert.assertFalse(matrix.equals(matrix2));
    }
	
	@Test
    public void newInstanceTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
		Matrix matrix2 = (Matrix) matrix.newInstance(2, 3);
		Assert.assertEquals("[0.00, 0.00, 0.00]\n[0.00, 0.00, 0.00]", matrix2.toString(2));
    }
	
	@Test
    public void addTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
		double[][] elements = new double[][]{{1, 2, 3.2},{4, 2.11, 3.3}};
		Matrix matrix2 = new Matrix(2,3,elements,false);
		matrix.add(matrix2);
		Assert.assertEquals("[5.00, 4.00, 7.20]\n[9.20, 5.11, 11.00]", matrix.toString(2));
    }
	
	@Test(expected=IncompatibleOperandException.class)
    public void addDimensionMismatchRowTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 7 | 5.2 3 7.7 3");
		double[][] elements = new double[][]{{1, 2, 3.2},{4, 2.11, 3.3}};
		Matrix matrix2 = new Matrix(2,3,elements,false);
		matrix.add(matrix2);
    }
	
	@Test(expected=IncompatibleOperandException.class)
    public void addDimensionMismatchColTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 | 5.2 3 | 5 9");
		double[][] elements = new double[][]{{1, 2, 3.2},{4, 2.11, 3.3}};
		Matrix matrix2 = new Matrix(2,3,elements,false);
		matrix.add(matrix2);
    }
	
	@Test
    public void nAddTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
		double[][] elements = new double[][]{{1, 2, 3.2},{4, 2.11, 3.3}};
		Matrix matrix2 = new Matrix(2,3,elements,false);
		Matrix matrix3 = (Matrix) matrix.nAdd(matrix2);
		
		Assert.assertEquals("[4.00, 2.00, 4.00]\n[5.20, 3.00, 7.70]", matrix.toString(2));
		Assert.assertEquals("[5.00, 4.00, 7.20]\n[9.20, 5.11, 11.00]", matrix3.toString(2));
		Assert.assertFalse(matrix.equals(matrix3));
    }
	
	@Test(expected=IncompatibleOperandException.class)
    public void nAddDimensionMismatchTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 7 | 5.2 3 7.7 3");
		double[][] elements = new double[][]{{1, 2, 3.2},{4, 2.11, 3.3}};
		Matrix matrix2 = new Matrix(2,3,elements,false);
		matrix.nAdd(matrix2);
    }
	
	@Test
    public void subTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
		double[][] elements = new double[][]{{1, 2, 3.2},{4, 2.11, 3.3}};
		Matrix matrix2 = new Matrix(2,3,elements,false);
		matrix.sub(matrix2);
		Assert.assertEquals("[3.00, 0.00, 0.80]\n[1.20, 0.89, 4.40]", matrix.toString(2));
    }
	
	@Test(expected=IncompatibleOperandException.class)
    public void subDimensionMismatchTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 7 | 5.2 3 7.7 3");
		double[][] elements = new double[][]{{1, 2, 3.2},{4, 2.11, 3.3}};
		Matrix matrix2 = new Matrix(2,3,elements,false);
		matrix.sub(matrix2);
    }
	
	@Test
    public void nSubTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
		double[][] elements = new double[][]{{1, 2, 3.2},{4, 2.11, 3.3}};
		Matrix matrix2 = new Matrix(2,3,elements,false);
		Matrix matrix3 = (Matrix) matrix.nSub(matrix2);
		
		Assert.assertEquals("[4.00, 2.00, 4.00]\n[5.20, 3.00, 7.70]", matrix.toString(2));
		Assert.assertEquals("[3.00, 0.00, 0.80]\n[1.20, 0.89, 4.40]", matrix3.toString(2));
		Assert.assertFalse(matrix.equals(matrix3));
    }
	
	@Test(expected=IncompatibleOperandException.class)
    public void nSubDimensionMismatchTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 7 | 5.2 3 7.7 3");
		double[][] elements = new double[][]{{1, 2, 3.2},{4, 2.11, 3.3}};
		Matrix matrix2 = new Matrix(2,3,elements,false);
		matrix.nSub(matrix2);
    }
	
	@Test
    public void determinantSimpleTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 | 5.2 3");
		double determinant = matrix.determinant();
		Assert.assertEquals(1.6,determinant,1e-5);
		
		Matrix matrix2 = (Matrix)Matrix.parseSimple("4");
		determinant = matrix2.determinant();
		Assert.assertEquals(4.0,determinant,1e-6);
    }
	
	@Test
    public void determinantHardTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 5.2 9.5 | 5.2 3 7.7 3.5 7.3 | 2 5 7 2.8 3.88 "
				+ "| 78.2 35.2 5 7.2 5.8 | 2.1 3.33 18.27 30.2 1.13");	
		double determinant = matrix.determinant();
		Assert.assertEquals(-355523.834008,determinant,1e-6);
    }
	
	@Test(expected=IncompatibleOperandException.class)
    public void determinantDimensionMismatchTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 5 | 5.2 3 3");
		matrix.determinant();
    }
	
	@Test
    public void makeIdentityTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7 | 5 4 6");
		matrix.makeIdentity();
		Assert.assertEquals("[1.00, 0.00, 0.00]\n[0.00, 1.00, 0.00]\n[0.00, 0.00, 1.00]", matrix.toString(2));
    }
	
	@Test(expected=IncompatibleOperandException.class)
    public void makeIdentityDimensionMismatchTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
		matrix.makeIdentity();
    }
	
	@Test
    public void scalarMultiplyTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
		matrix.scalarMultiply(2);
		Assert.assertEquals("[8.00, 4.00, 8.00]\n[10.40, 6.00, 15.40]", matrix.toString(2));
    }
	
	@Test
    public void nScalarMultiplyTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
		Matrix matrix2 = (Matrix) matrix.nScalarMultiply(2);
		Assert.assertEquals("[4.00, 2.00, 4.00]\n[5.20, 3.00, 7.70]", matrix.toString(2));
		Assert.assertEquals("[8.00, 4.00, 8.00]\n[10.40, 6.00, 15.40]", matrix2.toString(2));
		Assert.assertFalse(matrix.equals(matrix2));
    }
	
	@Test
    public void nMultiplyTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
		double[][] elements = new double[][]{{1, 2},{4, 2.11}, {3, 5.5}};
		Matrix matrix2 = new Matrix(3,2,elements,false);
		Matrix matrix3 = (Matrix) matrix.nMultiply(matrix2);
		Assert.assertEquals("[24.00, 34.22]\n[40.30, 59.08]", matrix3.toString(2));
    }
	
	@Test
    public void nTransposeLiveViewTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
		IMatrix matrix2 = (IMatrix) matrix.nTranspose(true);
		Assert.assertEquals("[4.00, 2.00, 4.00]\n[5.20, 3.00, 7.70]", matrix.toString(2));
		Assert.assertEquals("[4.000, 5.200]\n[2.000, 3.000]\n[4.000, 7.700]", matrix2.toString());
		matrix2.set(1, 0, 5.5);
		Assert.assertEquals("[4.000, 5.200]\n[5.500, 3.000]\n[4.000, 7.700]", matrix2.toString());
		Assert.assertEquals("[4.00, 5.50, 4.00]\n[5.20, 3.00, 7.70]", matrix.toString(2));
    }
	
	@Test
    public void nTransposeTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
		IMatrix matrix2 = (IMatrix) matrix.nTranspose(false);
		Assert.assertEquals("[4.00, 2.00, 4.00]\n[5.20, 3.00, 7.70]", matrix.toString(2));
		Assert.assertEquals("[4.000, 5.200]\n[2.000, 3.000]\n[4.000, 7.700]", matrix2.toString());
		matrix2.set(1, 0, 5.5);
		Assert.assertEquals("[4.000, 5.200]\n[5.500, 3.000]\n[4.000, 7.700]", matrix2.toString());
		Assert.assertEquals("[4.00, 2.00, 4.00]\n[5.20, 3.00, 7.70]", matrix.toString(2));
    }
	
	@Test
    public void nInvertTwoDimTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 | 5.2 3");
		Matrix invert = (Matrix) matrix.nInvert();
		Assert.assertEquals("[1.8750, -1.2500]\n[-3.2500, 2.5000]", invert.toString(4));
    }
	
	@Test
    public void nInvertThreeDimTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7 | 3 5 9");
		Matrix invert = (Matrix) matrix.nInvert();
		Assert.assertEquals("[0.4528, -0.0787, -0.1339]\n[0.9331, -0.9449, 0.3937]\n[-0.6693, 0.5512, -0.0630]", invert.toString(4));
    }
	
	@Test(expected=UnsupportedOperationException.class) 
    public void nInvertNotExistTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("0 0 | 0 0");
		matrix.nInvert();
    }
	
	@Test(expected=IncompatibleOperandException.class) 
    public void nInvertDimensionMismatchTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
		matrix.nInvert();
    }
	
	@Test
    public void toArrayTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");
		double[][] array = matrix.toArray();
		Assert.assertEquals(4.000, array[0][0], 1e-4);
        Assert.assertEquals(2.000, array[0][1], 1e-4);
        Assert.assertEquals(4.000, array[0][2], 1e-4);
        Assert.assertEquals(5.200, array[1][0], 1e-4);
        Assert.assertEquals(3.000, array[1][1], 1e-4);
        Assert.assertEquals(7.700, array[1][2], 1e-4);
    }
	
	@Test
    public void toVectorTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("5.2 3 7.7");
		IVector vector = matrix.toVector(true);
		
		Assert.assertEquals("[5.200, 3.000, 7.700]",vector.toString());
		vector.set(0, 8);
		Assert.assertEquals("[8.000, 3.000, 7.700]",vector.toString());
		Assert.assertEquals(3,vector.getDimension());
		
		IVector other = vector.copy();
		Assert.assertEquals("[8.000, 3.000, 7.700]",other.toString());
		other.set(1, 8);
		Assert.assertEquals("[8.000, 8.000, 7.700]",other.toString());
		Assert.assertEquals("[8.000, 3.000, 7.700]",matrix.toString());
		
		Matrix matrix2 = (Matrix)Matrix.parseSimple("5.2 | 3 | 7.7");
		IVector vector2 = matrix2.toVector(false);
		Assert.assertEquals("[5.200, 3.000, 7.700]",vector2.toString());
		vector2.set(0, 8);
		Assert.assertEquals("[8.000, 3.000, 7.700]",vector2.toString());
		Assert.assertEquals("[5.200]\n[3.000]\n[7.700]",matrix2.toString());
    }
	
	@Test(expected=IllegalArgumentException.class) 
    public void toVectorDimesionMismatchTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");;
		matrix.toVector(true);
    }
	
	@Test
    public void subMatrixTest()
    {
		Matrix matrix = (Matrix)Matrix.parseSimple("4 2 4 | 5.2 3 7.7");;
		
		IMatrix subMatrix =  matrix.subMatrix(0, 1, true);
		Assert.assertEquals("[4.000, 2.000, 4.000]\n[5.200, 3.000, 7.700]",matrix.toString());
		Assert.assertEquals("[5.200, 7.700]",subMatrix.toString());
		subMatrix.set(0, 1, 4);
		Assert.assertEquals("[4.000, 2.000, 4.000]\n[5.200, 3.000, 4.000]",matrix.toString());
		Assert.assertEquals("[5.200, 4.000]",subMatrix.toString());
		IMatrix copy = subMatrix.copy();
		Assert.assertEquals("[5.200, 4.000]",copy.toString());
		copy.set(0, 1, 2);
		Assert.assertEquals("[4.000, 2.000, 4.000]\n[5.200, 3.000, 4.000]",matrix.toString());
		Assert.assertEquals("[5.200, 4.000]",subMatrix.toString());
		Assert.assertEquals("[5.200, 2.000]",copy.toString());
		Assert.assertEquals(1,subMatrix.getRowsCount());
		Assert.assertEquals(2,subMatrix.getColsCount());
		
		IMatrix subMatrix2 =  matrix.subMatrix(0, 1, false);
		Assert.assertEquals("[5.200, 4.000]",subMatrix2.toString());
		subMatrix2.set(0, 1, 3);
		Assert.assertEquals("[4.000, 2.000, 4.000]\n[5.200, 3.000, 4.000]",matrix.toString());
		Assert.assertEquals("[5.200, 3.000]",subMatrix2.toString());
		
    }
	
	@Test
    public void subMatrixViewTest()
    {
		double[][] elements = new double[][]{{4,7,8},{5.3,2,8.5},{9,5.8,3}};
        Matrix matrix = new Matrix (3,3,elements,false);
        
        IMatrix subMatrix =  matrix.subMatrix(0, 1, true);
        Assert.assertEquals("[5.300, 8.500]\n[9.000, 3.000]", subMatrix.toString());
        
        IMatrix subSubMatrix = subMatrix.subMatrix(1, 1, true);
        Assert.assertEquals("[5.300]", subSubMatrix.toString());
        subSubMatrix.set(0, 0, 2);
        Assert.assertEquals("[2.000]", subSubMatrix.toString());
        Assert.assertEquals("[2.000, 8.500]\n[9.000, 3.000]", subMatrix.toString());
        
        IMatrix subSubMatrix2 = subMatrix.subMatrix(0, 0, false);
        Assert.assertEquals("[3.000]", subSubMatrix2.toString());
        subSubMatrix2.set(0, 0, 7);
        Assert.assertEquals("[7.000]", subSubMatrix2.toString());
        Assert.assertEquals("[2.000, 8.500]\n[9.000, 3.000]", subMatrix.toString());
        
        double[][] subArray = subMatrix.toArray();
        Assert.assertEquals(2.000, subArray[0][0], 1e-4);
        Assert.assertEquals(8.500, subArray[0][1], 1e-4);
        Assert.assertEquals(9.000, subArray[1][0], 1e-4);
        Assert.assertEquals(3.000, subArray[1][1], 1e-4);
    }
	
	@Test
    public void transposeViewTest()
    {
		double[][] elements = new double[][]{{4,7,8},{5.3,2,8.5},{9,5.8,3}};
        Matrix matrix = new Matrix (3,3,elements,false);
        
        IMatrix trans =  matrix.nTranspose(true);
        Assert.assertEquals("[4.000, 5.300, 9.000]\n[7.000, 2.000, 5.800]\n[8.000, 8.500, 3.000]", trans.toString());
        
        IMatrix copy = trans.copy();
        Assert.assertEquals("[4.000, 5.300, 9.000]\n[7.000, 2.000, 5.800]\n[8.000, 8.500, 3.000]", copy.toString());
        Assert.assertFalse(trans.equals(copy));
        
        double[][] array = trans.toArray();
        Assert.assertEquals(4.000, array[0][0], 1e-4);
        Assert.assertEquals(5.300, array[0][1], 1e-4);
        Assert.assertEquals(9.000, array[0][2], 1e-4);
        Assert.assertEquals(7.000, array[1][0], 1e-4);
        Assert.assertEquals(2.000, array[1][1], 1e-4);
        Assert.assertEquals(5.800, array[1][2], 1e-4);
        Assert.assertEquals(8.000, array[2][0], 1e-4);
        Assert.assertEquals(8.500, array[2][1], 1e-4);
        Assert.assertEquals(3.000, array[2][2], 1e-4);
        
    }
	
}
