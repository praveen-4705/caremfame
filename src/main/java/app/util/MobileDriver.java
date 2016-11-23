package app.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.saucerest.SauceREST;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;

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

public class MobileDriver implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

	private AppiumDriver<MobileElement> driver;

	/** Name of the test case. */
	private String testName;

	/** Name of the test defined in the TestNG file. */
	private String testArea;
	
	/** Saucelabs Session ID of the job. Unique for each job. */
	private ThreadLocal<String> sessionId = new ThreadLocal<String>();
	
	/** Environment being tested; either defined in Variables file or Jenkins.	 */
	private String environment;
	
	/** Browser capabilities being used. For list of capabilities available, 
	 */
	private DesiredCapabilities capabilities = new DesiredCapabilities();
	
	/** Saucelabs Authenticator. Uses Saucelabs Username and API Key. */
	private SauceOnDemandAuthentication sauceAuth;
    
	/** Saucelabs REST Client. Uses Saucelabs Username and API Key. */
	private SauceREST sauceClient;
	
	/** Saucelabs username being used to run the job; either defined in Variables file or Jenkins. */
	private String sauceUsername;
	
	/** Saucelabs API Key being used to run the job; either defined in Variables file or Jenkins. */
	private String sauceAccessKey;
	
	/** List of tags getting uploaded to Saucelabs at the end of the test. */
	private ArrayList<String> tags = new ArrayList<String>();
	
	/** */
	private Properties environmentProperties;

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
			
			// Set user info for Saucelabs
			if (System.getenv("SAUCE_USER_NAME") == null) sauceUsername = Variables.sauceUsername;
		   	else sauceUsername = System.getenv("SAUCE_USER_NAME");
			    	
		 	if (System.getenv("SAUCE_API_KEY") == null) sauceAccessKey = Variables.sauceAccessKey;
		 	else sauceAccessKey = System.getenv("SAUCE_API_KEY");
					
		 	// Create Saucelabs authenticator and REST client
		 	this.sauceAuth 	= new SauceOnDemandAuthentication(sauceUsername, sauceAccessKey);
	    	this.sauceClient = new SauceREST(sauceUsername, sauceAccessKey);
	    	
	    	capabilities.setCapability("platformName", "iOS");
	        capabilities.setCapability("deviceName", "iPhone 6");
	        capabilities.setCapability("platformVersion", "9.2");
//	        capabilities.setCapability("app", "https://s3-us-west-2.amazonaws.com/onthecheck/Care.app.zip");
	        capabilities.setCapability("app", "sauce-storage:Care.zip");
	        capabilities.setCapability("browserName", "");
	        capabilities.setCapability("deviceOrientation", "portrait");
	        capabilities.setCapability("appiumVersion", "1.5.3");
	        
	    	// Add the environment tag
	    	tags.add(this.environment);
	    	// Add the device type
//	    	tags.add(this.deviceType);
	    	// Add the test area
	    	tags.add(this.testArea);
	    	
	    	// Set the test name, and the tags
			capabilities.setCapability("name", testName);
			capabilities.setCapability("tags", tags);
	        
			try {
				
				// Set the driver
				driver = new IOSDriver<MobileElement>(
						new URL("http://" + this.getAuthentication().getUsername() + ":"
								+ this.getAuthentication().getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
						capabilities);
				
			} catch (MalformedURLException e) {
				
				e.printStackTrace();
			}
			
			// Get the session ID from Saucelabs, and set the local value
			setSessionId(((RemoteWebDriver) getDriver()).getSessionId().toString());	
			
			// Print the Session ID
	    	String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s", ((RemoteWebDriver) getDriver()).getSessionId().toString(), this.getTestName());
	    	System.out.println(message);
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
	
	public String getSauceUsername() {
		return sauceUsername;
	}

	public void setSauceUsername(String sauceUsername) {
		this.sauceUsername = sauceUsername;
	}

	public String getSauceAccessKey() {
		return sauceAccessKey;
	}

	public void setSauceAccessKey(String sauceAccessKey) {
		this.sauceAccessKey = sauceAccessKey;
	}

	@Override
	public SauceOnDemandAuthentication getAuthentication() {
		return sauceAuth;
	}

	public void setAuthentication(SauceOnDemandAuthentication sauceAuth) {
		this.sauceAuth = sauceAuth;
	}
	
	public SauceREST getSauceClient() {
		return sauceClient;
	}

	public void setSauceClient(SauceREST sauceClient) {
		this.sauceClient = sauceClient;
	}

	@Override
	public String getSessionId() {
		return sessionId.get();
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId.set(sessionId);
	}

	public void setSessionId(ThreadLocal<String> sessionId) {
		this.sessionId = sessionId;
	}
}
