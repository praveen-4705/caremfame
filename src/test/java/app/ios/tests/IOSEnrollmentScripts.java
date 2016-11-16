package app.ios.tests;

import org.testng.annotations.Test;

import app.screens.HomePage;
import app.tests.BaseTest;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;

public class IOSEnrollmentScripts extends BaseTest {

	@BeforeMethod
	public void initializeDriver(ITestContext context, Method m) {
		super.initializeDriver(context, m, true, false);
	}

	@Test
	public void iOS_seekerEnrollment() {
		// Open Care.com app
		HomePage homePage = new HomePage(d);
	}
}
