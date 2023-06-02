package pkg.service.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AnalysisServiceTest.class,
        DishServiceTest.class,
        UserServiceTest.class,
        UserAuthServiceTest.class,
        FileServiceTest.class,
})
public class ServiceTestSuite {
}
