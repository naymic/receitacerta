package tests;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import converters.GenericConverter;
import db.Config;
import interfaces.IExtendedConverter;
import interfaces.ISimpleConverter;
import model.IngredienteTipo;
import model.Ingredientes;
import reflection.GenericReflection;

public class TestConverter extends TestCases{
	
	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
	@Test
	public void testGenericSimpleConverter() {
		try {
			Double d = (Double) GenericConverter.convert(Double.class, new String("10.45"));
			assertEquals(Double.class, d.getClass());
			
			//Set actual date
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
			Date date = (Date)GenericConverter.convert(Date.class, "2014-08-06");
			assertEquals(2014, date.getYear()+1900);
			assertEquals(8, date.getMonth()+1);
			assertEquals(6, date.getDate());
			
			String s = (String)GenericConverter.convert(String.class, date);
			assertEquals("06/07/114", s);
			
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
