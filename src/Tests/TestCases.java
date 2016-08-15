package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;


public class TestCases {

	@Before
	public  void activateDebug(){
		Debug.getInstance().setDebugTrue();
	};
	

}
