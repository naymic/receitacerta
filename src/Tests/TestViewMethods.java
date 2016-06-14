package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Controller.CRUDController;
import Interfaces.IController;
import Utils.Return;
import View.ViewController;

public class TestViewMethods {

	@Test
	public void testGetController() {
		ViewController vc = new ViewController();
		Return r = new Return();
		Class<?> c = CRUDController.class;
		IController ic = vc.getController(r, "CRUD");
		assertEquals(ic.getClass(), CRUDController.class);
		assertTrue(r.isSuccess());
		
		ic = vc.getController(r, "BliBla");
		assertFalse(r.isSuccess());
		assertEquals(1, r.getSimpleErrors().size());
		
	}
	
	


}
