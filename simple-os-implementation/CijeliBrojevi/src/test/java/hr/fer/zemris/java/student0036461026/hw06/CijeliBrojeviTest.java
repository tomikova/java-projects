package hr.fer.zemris.java.student0036461026.hw06;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit testovi za CijeliBrojevi.
 * @author Tomislav
 * 
 */
public class CijeliBrojeviTest
{
	@Test
    public void jeNeparanTruePositiveTest()
    {
        Assert.assertTrue( CijeliBrojevi.jeNeparan(11) );
    }
	
	@Test
    public void jeNeparanFalsePositiveTest()
    {
        Assert.assertFalse( CijeliBrojevi.jeNeparan(10) );
    }
	
	@Test
    public void jeNeparanTrueNegativeTest()
    {
        Assert.assertTrue( CijeliBrojevi.jeNeparan(-11) );
    }
	
	@Test
    public void jeNeparanFalseNegativeTest()
    {
        Assert.assertFalse( CijeliBrojevi.jeNeparan(-10) );
    }
	
	@Test
    public void jeParanTruePositiveTest()
    {
        Assert.assertTrue( CijeliBrojevi.jeParan(24) );
    }
	
	@Test
    public void jeParanFalsePositiveTest()
    {
        Assert.assertFalse( CijeliBrojevi.jeParan(31) );
    }
	
	@Test
    public void jeParanTrueNegativeTest()
    {
        Assert.assertTrue( CijeliBrojevi.jeParan(-24) );
    }
	
	@Test
    public void jeParanFalseNegativeTest()
    {
        Assert.assertFalse( CijeliBrojevi.jeParan(-31) );
    }
	
	@Test
    public void jeProstOneTest()
    {
        Assert.assertTrue( CijeliBrojevi.jeProst(1) );
    }
	
	@Test
    public void jeProstTruePositiveTest()
    {
        Assert.assertTrue( CijeliBrojevi.jeProst(7) );
    }
	
	@Test
    public void jeProstTrueNegativeTest()
    {
        Assert.assertTrue( CijeliBrojevi.jeProst(-13) );
    }
	
	@Test
    public void jeProstFalsePositiveTest()
    {
        Assert.assertFalse( CijeliBrojevi.jeProst(12) );
    }
	
	@Test
    public void jeProstFalseNegativeTest()
    {
        Assert.assertFalse( CijeliBrojevi.jeProst(-20) );
    }
	
	@Test(expected=IllegalArgumentException.class)
    public void jeProstZeroExceptionTest()
    {
        CijeliBrojevi.jeProst(0);
    }
}
