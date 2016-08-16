package hr.fer.zemris.java.student0036461026.hw06;

import hr.fer.zemris.java.custom.scripting.exec.EmptyStackException;
import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueHolder;
import hr.fer.zemris.java.custom.scripting.exec.ValueHolder.Type;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

import org.junit.Assert;
import org.junit.Test;

/**
 * Multistack tests.
 * @author Tomislav
 * 
 */
public class MultistackTest
{
	@Test
    public void pushIntegerTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
        Assert.assertEquals(2000,multistack.peek("year").getValue());
    }
	
	@Test
    public void pushMultipleIntegerTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("year", new ValueWrapper(Integer.valueOf(2003)));
		multistack.push("year", new ValueWrapper(Integer.valueOf(2011)));
		multistack.push("year", new ValueWrapper(Integer.valueOf(2020)));
        Assert.assertEquals(2020,multistack.peek("year").getValue());
    }
	
	@Test
    public void pushMultipleTypesTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper(Integer.valueOf(50)));
		multistack.push("price", new ValueWrapper(Double.valueOf(89.982)));
		multistack.push("price", new ValueWrapper("23.2e-2"));
		multistack.push("price", new ValueWrapper("3.2E-2"));
		multistack.push("price", new ValueWrapper("2.6"));
		multistack.push("price", new ValueWrapper("something"));
		Assert.assertEquals("something",multistack.pop("price").getValue());
        Assert.assertEquals("2.6",multistack.pop("price").getValue());
        Assert.assertEquals("3.2E-2",multistack.pop("price").getValue());
        Assert.assertEquals("23.2e-2",multistack.pop("price").getValue());
        Assert.assertEquals(89.982,multistack.pop("price").getValue());
        Assert.assertEquals(50,multistack.pop("price").getValue());
    }
	
	@Test
    public void NullAsMapKeyTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push(null, new ValueWrapper("5"));
		multistack.peek(null).increment("233.3E-2");
		Assert.assertEquals(7.333,(double) multistack.pop(null).getValue(),1e-6);
    }
	
	@Test
    public void pushStringDoublePerformCalculationTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper("232.2e-3"));
		multistack.peek("price").increment("233.3E-2");
		Assert.assertEquals(2.5652,(double) multistack.pop("price").getValue(),1e-6);
    }
	
	@Test
    public void pushStringIntegerPerformCalculationTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper(Integer.valueOf(5)));
		multistack.peek("price").increment("233.3E-2");
		Assert.assertEquals(7.333,(double) multistack.pop("price").getValue(),1e-6);
    }
	
	@Test
    public void pushDoubleIntegerPerformCalculationTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper(Integer.valueOf(5)));
		multistack.peek("price").increment(Double.valueOf(5.0));
		Assert.assertEquals(10.0,(double) multistack.pop("price").getValue(),1e-6);
    }
	
	@Test
    public void pushIntegerPerformCalculationTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper(Integer.valueOf(5)));
		multistack.peek("price").increment(Integer.valueOf(3));
		Assert.assertEquals(8,multistack.pop("price").getValue());
    }
	
	@Test
    public void operationDecrementTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper(Double.valueOf(5.44)));
		multistack.peek("price").decrement(Integer.valueOf(3));
		Assert.assertEquals(2.44,(double)multistack.pop("price").getValue(),1e-6);
		multistack.push("price", new ValueWrapper(Integer.valueOf(7)));
		multistack.peek("price").decrement(Integer.valueOf(3));
		Assert.assertEquals(4,multistack.pop("price").getValue());
    }
	
	@Test
    public void operationMultiplyTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper("5.44"));
		multistack.peek("price").multiply(Integer.valueOf(2));
		Assert.assertEquals(10.88,(double)multistack.pop("price").getValue(),1e-6);
    }
	
	@Test
    public void operationDivideIntegerTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper("11"));
		multistack.peek("price").divide(Integer.valueOf(3));
		Assert.assertEquals(3,multistack.pop("price").getValue());
    }
	
	@Test
    public void operationDivideDoubleTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper("11"));
		multistack.peek("price").divide(Double.valueOf(2));
		Assert.assertEquals(5.5,(double)multistack.pop("price").getValue(),1e-6);
    }
	
	@Test
    public void operationWithNullTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper(null));
		multistack.peek("price").increment(Double.valueOf(20));
		multistack.peek("price").divide(Double.valueOf(4));
		Assert.assertEquals(5.0,(double)multistack.pop("price").getValue(),1e-6);
    }
	
	@Test
    public void numCompareIntegerNullTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper(null));
		Assert.assertEquals(0,multistack.peek("price").numCompare(Integer.valueOf(0)));
    }
	
	@Test
    public void numCompareIntegerIntegerTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper("17"));
		Assert.assertEquals(1,multistack.peek("price").numCompare(Integer.valueOf(5)));
    }
	
	@Test
    public void numCompareIntegerDoubleTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper("5.3"));
		Assert.assertEquals(1,multistack.peek("price").numCompare(Integer.valueOf(0)));
    }
	
	@Test
    public void numCompareDoubleDoubleTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper("5.3"));
		Assert.assertEquals(-1,multistack.peek("price").numCompare("14.8963"));
    }
	
	@Test
    public void numCompareDoubleDoubleEqualTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper("5.5764"));
		Assert.assertEquals(0,multistack.peek("price").numCompare(Double.valueOf(5.5764)));
    }
	
	@Test
    public void numCompareDoubleDoubleSmallNumberTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper("5.3e-35"));
		Assert.assertEquals(1,multistack.peek("price").numCompare("14.8963e-37"));
    }
	
	@Test(expected=IllegalArgumentException.class)
    public void valueWrapperTypeNotSupportedTest()
    {
		new ValueWrapper(Long.valueOf(5));
    }
	
	@Test
    public void setAndGetValueTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper(Integer.valueOf(5)));
		multistack.peek("price").multiply(Integer.valueOf(2));
		Assert.assertEquals(10,multistack.peek("price").getValue());
		multistack.peek("price").setValue("someText");
		Assert.assertEquals("someText",multistack.peek("price").getValue());
    }
	
	@Test
    public void pushTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		Assert.assertTrue(multistack.isEmpty("price"));
		multistack.push("price", new ValueWrapper(Integer.valueOf(5)));
		Assert.assertFalse(multistack.isEmpty("price"));
		multistack.push("price", new ValueWrapper(Integer.valueOf(7)));
		multistack.push("price", new ValueWrapper(Double.valueOf(38)));
		multistack.push("price", new ValueWrapper(Integer.valueOf(6)));
		Assert.assertEquals(6,multistack.peek("price").getValue());
		Assert.assertFalse(multistack.isEmpty("price"));
    }
	
	@Test
    public void popTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper(Integer.valueOf(5)));
		multistack.push("price", new ValueWrapper(Integer.valueOf(7)));
		multistack.push("price", new ValueWrapper(Double.valueOf(38)));
		multistack.push("price", new ValueWrapper(Integer.valueOf(6)));
		multistack.pop("price");
		multistack.pop("price");
		Assert.assertEquals(7,multistack.peek("price").getValue());
		Assert.assertFalse(multistack.isEmpty("price"));
		multistack.pop("price");
		multistack.pop("price");
		Assert.assertTrue(multistack.isEmpty("price"));
    }
	
	@Test(expected=EmptyStackException.class)
    public void popEmptyStackTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("price", new ValueWrapper(Integer.valueOf(5)));
		multistack.push("price", new ValueWrapper(Integer.valueOf(7)));
		multistack.push("price", new ValueWrapper(Double.valueOf(38)));
		multistack.push("price", new ValueWrapper(Integer.valueOf(6)));
		multistack.pop("price");
		multistack.pop("price");
		multistack.peek("price");
		multistack.pop("price");
		multistack.pop("price");
		multistack.pop("price");
    }
	
	@Test(expected=EmptyStackException.class)
    public void peekKeyDoesntExistTest()
    {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.peek("price");
    }
	
	@Test(expected=IllegalArgumentException.class)
    public void valueHolderTypeNotSupported()
    {
		new ValueHolder(Long.valueOf(5));
    }
	
	@Test(expected=IllegalArgumentException.class)
    public void valueHolderStringNotNumberTest()
    {
		new ValueHolder("78abc");
    }
	
	@Test
    public void valueHolderGettersTest()
    {
		ValueHolder valueHolder = new ValueHolder("78");
		Assert.assertEquals(78,valueHolder.getValue());
		Assert.assertEquals(78.0,valueHolder.getDoubleValue(),1e-6);
		Assert.assertEquals(Type.INTEGER,valueHolder.getType());
    }
	
	@Test
    public void valueHolderGettersNullTest()
    {
		ValueHolder valueHolder = new ValueHolder(null);
		Assert.assertEquals(0,valueHolder.getValue());
		Assert.assertEquals(0.0,valueHolder.getDoubleValue(),1e-6);
		Assert.assertEquals(Type.INTEGER,valueHolder.getType());
    }
}
