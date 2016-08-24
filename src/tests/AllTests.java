package tests;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import db.Config;

@RunWith(Suite.class)
@SuiteClasses({TestGenericReflection.class, TestReflectionDAO.class, TestReflectionDAORelation.class, TestReflectionDAORelationList.class, TestGson.class, TestJSON.class,TestCrudController.class, TestLoginController.class, TestViewMethods.class, TestStringUtils.class, TestCrypt.class,TestConverter.class,})
public class AllTests {
	
	@Before
	public void initTestDatabase(){
		Config.getInstance().setTestDB(true);
	}
	
}


