package hr.fer.zemris.java.student0036461026.hw15.test;

import hr.fer.zemris.java.student0036461026.hw15.utility.Utility;
import org.junit.Assert;
import org.junit.Test;

/**
 * Utility class unit tests.
 * @author Tomislav
 *
 */

public class UtilityTest {
	
	@Test
    public void computeHashTest()
    {
		String password = "lozinka";
		
		String hashPassword = Utility.computeHash(password, "SHA-1");
		
		Assert.assertEquals("912c106a14310615dfe86b9b571cbacf77849a6f", hashPassword);
    }
	
	@Test
	public void computeHashNullAsAlgorithmTest()
    {
		String password = "lozinka";
		
		String hashPassword = Utility.computeHash(password, null);
		
		Assert.assertEquals(null, hashPassword);
    }
		
	@Test
    public void hexToBytesTest()
    {
		byte[] array = new byte[]{4, 10, 12, 32};
		
		String convert = Utility.bytesToHex(array);
		
		Assert.assertEquals("040A0C20", convert.toUpperCase());
    }
}
