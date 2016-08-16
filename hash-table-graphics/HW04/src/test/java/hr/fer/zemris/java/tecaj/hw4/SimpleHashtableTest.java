package hr.fer.zemris.java.tecaj.hw4;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import hr.fer.zemris.java.tecaj.hw4.collections.SimpleHashtable;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class for testing work of a hash table.
 * @author Tomislav
 *
 */

public class SimpleHashtableTest {
	
	@Test
	public void sizeTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Assert.assertEquals(3, test.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void putNullAsKeyTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put(null, Integer.valueOf(3));
	}
	
	@Test
	public void isEmptyTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Assert.assertFalse(test.isEmpty());
	}
	
	@Test
	public void getTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Assert.assertEquals(4, test.get("Tomislav"));
	}
	
	@Test
	public void getKeyDoesntExistTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Assert.assertEquals(null, test.get("Petar"));
	}
	
	@Test
	public void updateTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		test.put("Marija", Integer.valueOf(5));
		Assert.assertEquals(5, test.get("Marija"));
	}
	
	@Test
	public void containsKeyExistTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Assert.assertTrue(test.containsKey("Tomislav"));
	}
	
	@Test
	public void containsKeyDoesntExistTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Assert.assertFalse(test.containsKey("Marko"));
	}
	
	@Test
	public void containsValueExistTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Assert.assertTrue(test.containsValue(2));
	}
	
	@Test
	public void containsValueDoesntExistTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Assert.assertFalse(test.containsValue(11));
	}
	
	@Test
	public void removeSizeAndGetKeyTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Assert.assertEquals(3,test.size());
		Assert.assertEquals(7,test.get("Marija"));
		test.remove("Marija");
		Assert.assertEquals(2,test.size());
		Assert.assertEquals(null,test.get("Marija"));
	}
	
	@Test
	public void removeKeyDoesntExistTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Assert.assertEquals(3, test.size());
		test.remove("Petar");
		Assert.assertEquals(3, test.size());
	}
	
	@Test
	public void toStringTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Assert.assertEquals("[Tomislav=4, Marija=7, Ivana=2]",test.toString());
	}
	
	@Test
	public void toStringAfterKeyRemovedTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		test.remove("Marija");
		Assert.assertEquals("[Tomislav=4, Ivana=2]",test.toString());
	}
	
	@Test
	public void toStringAfteClearTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		test.clear();
		Assert.assertEquals("[]",test.toString());
	}
	
	@Test
	public void clearTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Assert.assertFalse(test.isEmpty());
		test.clear();
		Assert.assertEquals(0,test.size());
		Assert.assertTrue(test.isEmpty());
	}
	
	@Test
	public void clearAndPutTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		test.clear();
		test.put("Josip", Integer.valueOf(3));
		Assert.assertEquals(1,test.size());
		Assert.assertFalse(test.isEmpty());
		Assert.assertEquals(3,test.get("Josip"));
		Assert.assertEquals(null,test.get("Tomislav"));
	}
	
	@Test
	public void iteratorSimpleTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		String output = "";
		for(SimpleHashtable.TableEntry pair : test) {
			output += pair.getKey().toString()+pair.getValue().toString();
		}
		Assert.assertEquals("Tomislav4Marija7Ivana2",output);
	}
	
	@Test
	public void iteratorNestedSimpleTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		String output = "";
		for(SimpleHashtable.TableEntry pair1 : test) {
			output += pair1.getKey().toString()+pair1.getValue().toString();
			for(SimpleHashtable.TableEntry pair2 : test) {
				output += pair2.getKey().toString()+pair2.getValue().toString();
			}
		}
		Assert.assertEquals("Marija7Marija7Ivana2Ivana2Marija7Ivana2",output);
	}
	
	@Test
	public void iteratorRemoveTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Assert.assertEquals(3,test.size());
		Iterator<SimpleHashtable.TableEntry> iter = test.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry pair = iter.next();
			if(pair.getKey().equals("Marija")) {
				iter.remove();
			}
		}
		Assert.assertEquals(2,test.size());
		Assert.assertFalse(test.containsKey("Marija"));
	}
	
	@Test
	public void iteratorRemoveAllTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Assert.assertEquals(3,test.size());
		Iterator<SimpleHashtable.TableEntry> iter = test.iterator();
		while(iter.hasNext()) {
			iter.next();
			iter.remove();
		}
		Assert.assertEquals(0,test.size());
		Assert.assertEquals("[]",test.toString());
	}
	
	@Test(expected=IllegalStateException.class)
	public void iteratorRemoveTwiceInARowTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Iterator<SimpleHashtable.TableEntry> iter = test.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry pair = iter.next();
			if(pair.getKey().equals("Marija")) {
				iter.remove();
				iter.remove();
			}
		}
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void iteratorOutsideModificationTest() {
		SimpleHashtable test = new SimpleHashtable(2);
		test.put("Ivana", Integer.valueOf(2));
		test.put("Marija", Integer.valueOf(7));
		test.put("Tomislav", Integer.valueOf(4));
		Iterator<SimpleHashtable.TableEntry> iter = test.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry pair = iter.next();
			if(pair.getKey().equals("Marija")) {
				test.remove("Marija");
			}
		}
	}

}
