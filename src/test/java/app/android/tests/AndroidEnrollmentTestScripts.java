package app.android.tests;

import org.testng.annotations.Test;

import app.screens.HomePage;
import app.tests.BaseTest;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import org.testng.ITestContext;

public class AndroidEnrollmentTestScripts extends BaseTest {

	@BeforeMethod
	public void initializeDriver(ITestContext context, Method m) {
		super.initializeDriver(context, m, true, true);
	}

	@Test
	public void android_seekerEnrollment() throws InterruptedException {
		// Open Care.com app
		HomePage homePage = new HomePage(d);
		// Select environment
		homePage.selectStack();
	}
}
