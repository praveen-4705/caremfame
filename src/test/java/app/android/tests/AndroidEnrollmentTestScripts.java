package app.android.tests;

import org.testng.annotations.Test;

import app.screens.EnrollmentPage;
import app.screens.HomePage;
import app.tests.BaseTest;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import org.testng.ITestContext;

public class AndroidEnrollmentTestScripts extends BaseTest {

	@BeforeMethod
	public void initializeDriver(ITestContext context, Method m) {
		super.initializeDriver(context, m, false, true);
	}

	@Test
	public void Android_BabySitterEnrollment() throws InterruptedException {
		// Open Care.com app
		HomePage homePage = new HomePage(d);
		// Select environment
		homePage.selectStack();
		// Select role
		homePage.selectRole("provider");
		// Select type Of care
		EnrollmentPage enrollmentPage = homePage.selectTypeOfCare("Babysitter");
		// Complete Enrollment
		enrollmentPage.completeEnrollment("provider");
	}
}
