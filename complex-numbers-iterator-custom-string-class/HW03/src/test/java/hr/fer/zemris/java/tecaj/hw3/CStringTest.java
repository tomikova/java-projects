package hr.fer.zemris.java.tecaj.hw3;

import org.junit.Test;
import org.junit.Assert;

public class CStringTest {
	
	@Test
	public void constructorTest_1() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data,2,4);
		Assert.assertEquals("greb",cString.toString());
	}
	
	@Test(expected=CStringException.class)
	public void cStringExceptionTest_1() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		new CString(data,2,7);
	}
	
	@Test
	public void constructorTest_2() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		Assert.assertEquals("Zagreb",cString.toString());
	}
	
	@Test
	public void constructorTest_3() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(new CString(data,2,4));
		Assert.assertEquals("greb",cString.toString());
	}
	
	@Test
	public void constructorTest_4() {
		CString cString = new CString("Zagreb");
		Assert.assertEquals("Zagreb",cString.toString());
	}
	
	@Test
	public void lengthTest() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data,2,4);
		Assert.assertEquals(4,cString.length());
	}
	
	@Test
	public void charAtTest() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data,2,4);
		Assert.assertEquals('e',cString.charAt(2));
	}
	
	@Test(expected=CStringException.class)
	public void cStringExceptionTest_2() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		cString.charAt(6);
	}
	
	@Test(expected=CStringException.class)
	public void cStringExceptionTest_3() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		cString.charAt(-1);
	}
	
	@Test
	public void toCharArrayTest() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data,2,3);
		char[] expectedData = new char[]{'g','r','e'};
		Assert.assertArrayEquals(expectedData, cString.toCharArray());
	}
	
	@Test
	public void toStringTest() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data,2,3);
		Assert.assertEquals("gre", cString.toString());
	}
	
	@Test
	public void indexOfTest_1() {
		char[] data = new char[]{'Z','a','g','r','e','b','g'};
		CString cString = new CString(data);
		Assert.assertEquals(2, cString.indexOf('g'));
	}
	
	@Test
	public void indexOfTest_2() {
		char[] data = new char[]{'Z','a','g','r','e','b','g'};
		CString cString = new CString(data,3,4);
		Assert.assertEquals(3, cString.indexOf('g'));
	}
	
	@Test
	public void indexOfTest_3() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		Assert.assertEquals(-1, cString.indexOf('A'));
	}
	
	@Test
	public void startsWithTest_1() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		CString startString = new CString("Zag");
		Assert.assertTrue(cString.startsWith(startString));
	}
	
	@Test
	public void startsWithTest_2() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		CString startString = new CString("gre");
		Assert.assertFalse(cString.startsWith(startString));
	}
	
	@Test
	public void startsWithTest_3() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		CString startString = new CString("Zagreb1");
		Assert.assertFalse(cString.startsWith(startString));
	}
	
	@Test
	public void endsWithTest_1() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		CString endString = new CString("eb");
		Assert.assertTrue(cString.endsWith(endString));
	}
	
	@Test
	public void endsWithTest_2() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		CString endString = new CString("Zagreb");
		Assert.assertTrue(cString.endsWith(endString));
	}
	
	@Test
	public void endsWithTest_3() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		CString endString = new CString("Zag");
		Assert.assertFalse(cString.endsWith(endString));
	}
	
	@Test
	public void containsTest_1() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		CString subString = new CString("gre");
		Assert.assertTrue(cString.contains(subString));
	}
	
	@Test
	public void containsTest_2() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		CString subString = new CString("Zagreb");
		Assert.assertTrue(cString.contains(subString));
	}
	
	@Test
	public void containsTest_3() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		CString subString = new CString("Aeb");
		Assert.assertFalse(cString.contains(subString));
	}
	
	@Test
	public void containsTest_4() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		CString subString = new CString("Zagreb2");
		Assert.assertFalse(cString.contains(subString));
	}
	
	@Test
	public void substringTest_1() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		Assert.assertEquals("agr",cString.substring(1, 4).toString());
	}
	
	@Test
	public void substringTest_2() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		Assert.assertEquals("Zagreb",cString.substring(0, cString.length()).toString());
	}
	
	@Test
	public void substringTest_3() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		Assert.assertEquals("b",cString.substring(5, cString.length()).toString());
	}
	
	@Test
	public void substringTest_4() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		Assert.assertEquals("",cString.substring(0, 0).toString());
	}
	
	@Test(expected=CStringException.class)
	public void cStringExceptionTest_4() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		cString.substring(0, 7).toString();
	}
	
	@Test(expected=CStringException.class)
	public void cStringExceptionTest_5() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		cString.substring(5, 3).toString();
	}
	
	@Test
	public void leftTest_1() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		Assert.assertEquals("Zag",cString.left(3).toString());
	}
	
	@Test
	public void leftTest_2() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		Assert.assertEquals("Zagreb",cString.left(6).toString());
	}
	
	@Test
	public void leftTest_3() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		Assert.assertEquals("",cString.left(0).toString());
	}
	
	@Test(expected=CStringException.class)
	public void cStringExceptionTest_6() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		cString.left(7).toString();
	}
	
	@Test(expected=CStringException.class)
	public void cStringExceptionTest_7() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		cString.left(-5).toString();
	}
	
	@Test
	public void rightTest_1() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		Assert.assertEquals("reb",cString.right(3).toString());
	}
	
	@Test
	public void rightTest_2() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		Assert.assertEquals("Zagreb",cString.right(6).toString());
	}
	
	@Test
	public void rightTest_3() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		Assert.assertEquals("",cString.right(0).toString());
	}
	
	@Test
	public void addTest_1() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		Assert.assertEquals("ZagrebSplit",cString.add(new CString("Split")).toString());
	}
	
	@Test
	public void addTest_2() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		CString test = cString.add(new CString("Split")).add(new CString("Rijeka"));
		Assert.assertEquals("ZagrebSplitRijeka",test.toString());
	}
	
	@Test
	public void replaceAllCharTest_1() {
		char[] data = new char[]{'Z','a','g','r','e','b'};
		CString cString = new CString(data);
		Assert.assertEquals("Aagreb",cString.replaceAll('Z', 'A').toString());
	}
	
	@Test
	public void replaceAllCharTest_2() {
		char[] data = new char[]{'Z','Z','Z','Z','Z','Z'};
		CString cString = new CString(data);
		Assert.assertEquals("111111",cString.replaceAll('Z', '1').toString());
	}
	
	@Test
	public void replaceAllStringTest_1() {
		CString cString = new CString("ZigZagZigZagZigZagZig");
		CString oldStr = new CString("Zag");
		CString newStr = new CString("AE");
		Assert.assertEquals("ZigAEZigAEZigAEZig",cString.replaceAll(oldStr, newStr).toString());
	}
	
	@Test
	public void replaceAllStringTest_2() {
		CString cString = new CString("ababab");
		CString oldStr = new CString("ab");
		CString newStr = new CString("abab");
		Assert.assertEquals("abababababab",cString.replaceAll(oldStr, newStr).toString());
	}
	
	@Test
	public void replaceAllStringTest_3() {
		CString cString = new CString("AAAAAAA");
		CString oldStr = new CString("A");
		CString newStr = new CString("B");
		Assert.assertEquals("BBBBBBB",cString.replaceAll(oldStr, newStr).toString());
	}

}
