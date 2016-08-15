package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Converter.GenericConverter;
import Interfaces.IExtendedConverter;
import Interfaces.ISimpleConverter;
import Model.IngredienteTipo;
import Model.Ingredientes;
import Reflection.GenericReflection;

public class TestConverter extends TestCases{

	@Test
	public void testGenericSimpleConverter() {
		try {
			Double d = (Double) GenericConverter.convert(Double.class, new String("10.45"));
			assertEquals(Double.class, d.getClass());
			
			
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGenericModelConverter() {
		try {
		
			IngredienteTipo it = (IngredienteTipo) GenericConverter.convert(IngredienteTipo.class, new Integer(1));
			assertEquals(IngredienteTipo.class, it.getClass());		
			assertEquals("fruta", it.dgetNomeTipo());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNullToConverter() {
		try {

			String it = (String) GenericConverter.convert(String.class, null);
			assertEquals(String.class, it.getClass());		
			assertEquals(new String(), it);



		} catch (Exception e) {
			e.addSuppressed(new Exception("String convert problem in testNullToConverter"));
			e.printStackTrace();
		}

		try {


			Integer in = (Integer) GenericConverter.convert(Integer.class, null);
			assertEquals(Integer.class, in.getClass());		
	
		} catch (Exception e) {
			e.addSuppressed(new Exception("Integer convert problem in testNullToConverter"));
			e.printStackTrace();

		}
		
		try {


			Ingredientes i = (Ingredientes) GenericConverter.convert(Ingredientes.class, null);
			assertEquals(Ingredientes.class, i.getClass());		
		} catch (Exception e) {
			e.addSuppressed(new Exception("Model convert problem in testNullToConverter"));
			e.printStackTrace();

		}

	}
}
