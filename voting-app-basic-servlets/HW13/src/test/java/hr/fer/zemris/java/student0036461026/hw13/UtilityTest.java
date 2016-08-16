package hr.fer.zemris.java.student0036461026.hw13;

import hr.fer.zemris.java.student0036461026.hw13.glasanje.Utility;
import hr.fer.zemris.java.student0036461026.hw13.glasanje.Utility.Bend;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit testovi za klasu Utility.
 * @author Tomislav
 *
 */
public class UtilityTest {
	
	/**
	 * Datoteka s definicijama bendova.
	 */
	static final String datotekaBendovi = "web/aplikacija2/WEB-INF/glasanje-definicija.txt";
	/**
	 * Datoteka s definicijama glasova za bendove
	 */
	static final String datotekaGlasovi = "web/aplikacija2/WEB-INF/glasanje-rezultati.txt";
	
	@Test
	public void ucitajBendoveTest() {
		Map<Integer, Bend> bendovi = Utility.ucitajBendove(datotekaBendovi);
		Assert.assertEquals(7, bendovi.size());
		Bend bend = bendovi.get(1);
		Assert.assertEquals(0, bend.getBrGlasova());
	}
	
	@Test
	public void ucitajBendoveParamsTest() {
		Map<Integer, Bend> bendovi = Utility.ucitajBendove(datotekaBendovi);
		Bend bend = bendovi.get(1);
		Assert.assertEquals(1, bend.getId());
		Assert.assertEquals("The Beatles", bend.getName());
		Assert.assertEquals(0, bend.getBrGlasova());
		bend = bendovi.get(7);
		Assert.assertEquals(7, bend.getId());
		Assert.assertEquals("The Mamas And The Papas", bend.getName());
		Assert.assertEquals(0, bend.getBrGlasova());
	}
	
	@Test
	public void ucitajBendoveIGlasoveTest() {
		Map<Integer, Bend> bendovi = Utility.ucitajBendoveIGlasove(datotekaBendovi,datotekaGlasovi);
		Assert.assertEquals(7, bendovi.size());
		Bend bend = bendovi.get(1);
		Assert.assertTrue(bend.getBrGlasova() > 0);
	}
	
	@Test
	public void ucitajBendoveIGlasoveParamsTest() {
		Map<Integer, Bend> bendovi = Utility.ucitajBendoveIGlasove(datotekaBendovi,datotekaGlasovi);
		Bend bend = bendovi.get(2);
		Assert.assertEquals(2, bend.getId());
		Assert.assertEquals("The Platters", bend.getName());
		Assert.assertTrue(bend.getBrGlasova() > 0);
		bend = bendovi.get(7);
		Assert.assertEquals(7, bend.getId());
		Assert.assertEquals("The Mamas And The Papas", bend.getName());
		Assert.assertTrue(bend.getBrGlasova() > 0);
	}
	
	@Test
	public void ucitajBendoveNullKaoPathTest() {
		Map<Integer, Bend> bendovi = Utility.ucitajBendove(null);
		Assert.assertEquals(bendovi, null);
	}
	
	@Test
	public void ucitajBendoveNepostojeciPathTest() {
		Map<Integer, Bend> bendovi = Utility.ucitajBendove("ovo nije dobar path");
		Assert.assertEquals(bendovi, null);
	}
	
	@Test
	public void BendClassTest() {
		Bend bend = new Bend(5, "Iron Maiden", "Link");
		Assert.assertEquals(5, bend.getId());
		Assert.assertEquals("Iron Maiden", bend.getName());
		Assert.assertEquals("Link", bend.getLink());
		Assert.assertEquals(0, bend.getBrGlasova());
	}
}
