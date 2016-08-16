package hr.fer.zemris.java.student0036461026.hw07;

import hr.fer.zemris.java.tecaj.hw07.crypto.Crypto;

import org.junit.Assert;
import org.junit.Test;

/**
 * HexToBytes and bytesToHex methods tests.
 * @author Tomislav
 *
 */

public class HexToBytesTest {
	
	@Test
    public void hexToBytesTest1()
    {
		byte[] array = Crypto.hexToBytes("5a2217e3ee213ef1ffdee3a192e2ac7e");
		
		String result = Crypto.bytesToHex(array);
		
		Assert.assertEquals("5a2217e3ee213ef1ffdee3a192e2ac7e", result);
    }
	
	@Test
    public void hexToBytesTest2()
    {
		byte[] array = new byte[]{5, 8, 9, 100};
		
		String convert = Crypto.bytesToHex(array);
		
		byte[] newArray = Crypto.hexToBytes(convert);
		
		Assert.assertEquals(array[0],newArray[0]);
		Assert.assertEquals(array[1],newArray[1]);
		Assert.assertEquals(array[2],newArray[2]);
		Assert.assertEquals(array[3],newArray[3]);
    }
	
	
	@Test
    public void hexToBytesTest3()
    {
		byte[] array = Crypto.hexToBytes("060A1F");
		
		Assert.assertEquals(6,array[0]);
		Assert.assertEquals(10,array[1]);
		Assert.assertEquals(31,array[2]);
    }
	
	@Test
    public void hexToBytesTest4()
    {
		byte[] array = new byte[]{4, 10, 12, 32};
		
		String convert = Crypto.bytesToHex(array);
		
		Assert.assertEquals("040A0C20", convert.toUpperCase());
    }
}
