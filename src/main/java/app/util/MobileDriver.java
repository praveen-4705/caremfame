package app.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;

import app.variables.EnvironmentProperties;
import app.variables.Variables;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

/**
 * This class is for creating the driver and loading the driver properties
 * @author praveen
 *
 */

public class MobileDriver {

	private AppiumDriver<MobileElement> driver;

	/** Name of the test case. */
	private String testName;

	/** Name of the test defined in the TestNG file. */
	private String testArea;
	
	/** Environment being tested; either defined in Variables file or Jenkins.	 */
	private String environment;
	
	/** */
	private Properties environmentProperties;

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
		
		System.out.println(Variables.environmentProperties.getProperty(EnvironmentProperties.ANDROID_APP_VERSION));

		// Set variables
		this.testName = testName;
		this.testArea = testArea;
		
		// Set the Environment from Jenkins, if null, get it from Variables file
    	if (System.getenv("Environment") == null) this.environment = Variables.environmentName;
    	else this.environment = System.getenv("Environment");

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

		// If isLocal is true than create driver for local machine
		if (isLocal) {
			
			// If isAndroid is true than create Android Driver other wise create iOS Driver
			if (isAndroid) {
				
				driver = createAndroidDriver();
			}else {
				
				driver = createiOSDriver();
			}
		} else {
			
			// TO-DO : SauceLabs integration
		}
	}
	
	/**
	 * Create Android driver
	 * @return	Return Android Driver Object
	 */
	public AppiumDriver<MobileElement> createAndroidDriver() {
		
		// Get APK path
		File file = new File("./src/main/resources/app/file/"+Variables.apkName+".apk");
		
		// Add capabilities
		capabilities.setCapability(MobileCapabilityType.APP, file.getAbsolutePath());
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, Variables.deviceName);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Variables.platformName);
		
		// Create Android Driver
		driver = new AndroidDriver<MobileElement>(createServerURL(), capabilities);
		
		// Return driver object
		return driver;
	}
	
	/**
	 * Create IOS Driver
	 * @return Return driver object
	 */
	public AppiumDriver<MobileElement> createiOSDriver(){
		
		// Get APP file path
		File file = new File("./src/main/resources/app/file/"+Variables.appName+".app");
		
		// Add capabilities
		capabilities.setCapability(MobileCapabilityType.APP, file.getAbsolutePath());
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "iOS");
		capabilities.setCapability(MobileCapabilityType.VERSION, Variables.iOS_Vesion);
		capabilities.setCapability(MobileCapabilityType.PLATFORM, "Mac");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, Variables.iOS_deviceName);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
		
		// Create Android Driver
		driver = new IOSDriver<MobileElement>(createServerURL(), capabilities);
		
		// Return iOS driver
		return driver;
	}
	
	/**
	 * Create APPIUM server URL
	 * @return	Return Appium Server URL
	 */
	public URL createServerURL(){
		
		String http 			= "http://";
		String serverAddress	= Variables.defaultServer;
		String port				= Variables.defaultPort;
		URL url = null;
		
		try {
			
			url = new URL(http+serverAddress+":"+port+"/wd/hub");
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Return server URL
		return url;
	}

	public  AppiumDriver<MobileElement> getDriver() {
		return driver;
	}

	public void setDriver(AppiumDriver<MobileElement> driver) {
		this.driver = driver;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestArea() {
		return testArea;
	}

	public void setTestArea(String testArea) {
		this.testArea = testArea;
	}

	public DesiredCapabilities getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(DesiredCapabilities capabilities) {
		this.capabilities = capabilities;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Properties getEnvironmentProperties() {
		return environmentProperties;
	}

	public void setEnvironmentProperties(Properties environmentProperties) {
		this.environmentProperties = environmentProperties;
	}

}
