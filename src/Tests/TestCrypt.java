package Tests;import org.junit.Test;
import Interfaces.ISimpleConverter;
import Model.Usuario;
import Reflection.GenericReflection;
import Utils.CryptString;

import static org.junit.Assert.assertEquals;



public class TestCrypt {
	@Test
	public void testSpecificSHAConverter() {
		try {
		
			String needle = CryptString.crypt("testString blib bla");
			assertEquals("89-178-145-180-54-246-129-237-41-80-65-32-180-190-90-225-105-106-230-54", needle);

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testUserPasswordSet(){
		
		Usuario u = new Usuario();
		u.dsetSenha("testString blib bla");
		assertEquals("89-178-145-180-54-246-129-237-41-80-65-32-180-190-90-225-105-106-230-54", u.dgetSenha());
		
	}
}
