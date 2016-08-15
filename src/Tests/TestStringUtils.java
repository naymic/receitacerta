package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Utils.StringUtils;

public class TestStringUtils extends TestCases {


	
	
	@Test
	public void testReturnJustNumbers() {
		
		String test =StringUtils.justNumbers("jkl 90890 890  klh _)+) kjçl890");
		assertEquals("90890890890", test);
	}
	
	@Test
	public void testGetCommaString(){
		String[] test = "jka, kas j, jakfdlj , kjaf kljasfl".split(",");
		String test1 = StringUtils.getCommaString(test);
		assertEquals("jka, kas j, jakfdlj , kjaf kljasfl", test1);
	}
	
	@Test
	public void testGetStringVetctor(){
		String test = "jka, kas j, jakfdlj , kjaf kljasfl";
		String[] vect = test.split(",");
		String[] test1 = StringUtils.getStringVector(test);
		assertEquals(vect, test1);
	}
	
	@Test
	public void testStmtString(){
		String test = "jka,kasj,jakfdlj,kjafkljasfl";
		String[] vect = test.split(",");
		String test1 = StringUtils.getPrepStmtColumns(vect, " AND");
		assertEquals(" jka=? AND kasj=? AND jakfdlj=? AND kjafkljasfl=?", test1);
	}
	 
	


}
