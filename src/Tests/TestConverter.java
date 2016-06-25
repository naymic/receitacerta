package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Converter.GenericConverter;
import Interfaces.IModelConverter;
import Interfaces.ISimpleConverter;
import Model.IngredienteTipo;
import Reflection.GenericReflection;

public class TestConverter {

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
	
	
}
