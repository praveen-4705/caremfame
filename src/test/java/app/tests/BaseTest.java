package app.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import app.util.MobileDriver;
import app.util.Screenshot;
import app.variables.Variables;

/**
 * This class is super class for all the test scripts file
 * 
 * It contains all the methods related to test scripts like loading test data
 * 
 * @author praveen
 *
 */
public class BaseTest {
	
	protected MobileDriver d;
	
	/** Flag to run the tests in local */
	private Boolean localFlag = false;
	
	/** Flag to run the tests on mobile sites */
	private Boolean appFlag = false;
	
	/**
	 * Load Environment Properties before running suite
	 * @throws IOException
	 */
	@BeforeSuite(alwaysRun = true)
	public void parseData() throws IOException {

		// Load Environment Properties
		parseEnvironmentProperties();
	}
	
	/**
	 * Initializes a remote or local driver based on the flags
	 * @param context  						 Test context which contains all the information for a given test run
	 * @param m								 Test case being run
	 * @param isLocal						 Local flag
	 * @param isMobile						 Mobile flag
	 * @throws MalformedURLException
	 */
	public void initializeDriver(ITestContext context, Method m, Boolean isLocal, Boolean isAndroid) {
		// Set local flag
		this.localFlag = isLocal;
		
		// Initialize driver
		this.d = new MobileDriver(m.getName(), context.getName(), isLocal, isAndroid);
		d.getDriver().resetApp();
	}
	
	/**
	 * Read Stack properties from file
	 * @throws IOException
	 */
	public void parseEnvironmentProperties() throws IOException {

		// Create properties file object
		Properties properties = new Properties();

		// Initialize input stream
		InputStream inputStream;

		// Generate File name
		String fileName = "care"+Variables.environmentName.toLowerCase() + ".properties";

		// Get file path
		inputStream = new FileInputStream("./src/main/resources/app/properties/" + fileName);

		// Load property file
		properties.load(inputStream);

		Variables.environmentProperties = properties;
	}
	
	/**
	 * Sets results of tests on Saucelabs, and then quits the driver
	 * @param result			Result of a test
	 * @throws IOException
	 */
	@AfterMethod(alwaysRun=true)
	public void tearDown(ITestResult result, Method m) throws IOException {
		
		if(!localFlag) {
			RemoteWebDriver driver = (RemoteWebDriver) d.getDriver();
			String sauceSessionId = driver.getSessionId().toString();
			
			// if test passed, add passed information to job, else add failed
			if (result.isSuccess()) d.getSauceClient().jobPassed(sauceSessionId); 
			else d.getSauceClient().jobFailed(sauceSessionId);
		}
		
		if (localFlag) {
			// Take screenshot
			Screenshot.GetScreenshot("./test-output/", result.getName(), d.getDriver());
		}
		
		// Send report
//		EmailTestReport.sendTestReport();
		
		// Close the driver
		d.getDriver().quit();
	}
}
