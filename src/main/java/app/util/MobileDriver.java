package app.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import app.variables.Variables;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class MobileDriver {

	private static AppiumDriver<MobileElement> driver;

	/** Name of the test case. */
	private String testName;

	/** Name of the test defined in the TestNG file. */
	private String testArea;

	/**
	 * App capabilities being used. For list of capabilities available,
	 */
	private DesiredCapabilities capabilities = new DesiredCapabilities();

	/**
	 * 
	 * Mobile Driver Constructor. Creates a local or remote driver using the
	 * given parameters.
	 * 
	 * @param testName
	 *            Name of the test case.
	 * @param testArea
	 *            Name of the test defined in the TestNG file
	 * @param isLocal
	 *            Local Flag. Sets type of the driver. (Local or Remote)
	 * @param isMobile
	 *            Type of the Web Application being tested
	 * @param isAndroid
	 *            Type of the Native Application being tested
	 * @param isiOS
	 *            Type of the Native Application being tested
	 */
	public MobileDriver(String testName, String testArea, Boolean isLocal, Boolean isAndroid) {

		// Set variables
		this.testName = testName;
		this.testArea = testArea;

		// Create Mobile driver
		createMobileDriver(isLocal, isAndroid);
	}

	/**
	 * Create Mobile App Driver
	 * 
	 * @param isLocal
	 * @param isAndroid
	 */
	private void createMobileDriver(Boolean isLocal, Boolean isAndroid) {

		if (isLocal) {
			
			if (isAndroid) {
				
				driver	= createAndroidDriver();
			}

		} else {

		}

	}
	
	/**
	 * Create Android driver
	 * @return	Return Android Driver Object
	 */
	public AppiumDriver<MobileElement> createAndroidDriver() {
		
		// Add capabilities
		capabilities.setCapability(MobileCapabilityType.APP, "./src/test/java/com/apkfile/"+Variables.apkName+".apk");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, Variables.deviceName);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Variables.platformName);

		try {
			
			// Create Android Driver
			driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		
		// Return driver object
		return driver;
	}

}
