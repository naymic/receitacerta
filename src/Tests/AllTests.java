package Tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestGenericReflection.class,TestReflectionDAO.class, TestReflectionDAORelation.class, TestJSON.class,   TestController.class })
public class AllTests {

}