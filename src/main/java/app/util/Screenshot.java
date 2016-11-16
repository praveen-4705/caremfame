package app.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * This class is for taking screenshot of mobile screen
 * @author praveen
 *
 */
public class Screenshot {

	/**
	 * Get the screenshot of present Page
	 * 
	 * @param destinationPath
	 * @param screenShotName
	 */
	public static void GetScreenshot(String destinationPath, String screenShotName, WebDriver driver) {
		
		// Take screenshot
		File sourceFile	= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		// Generate file path
		destinationPath = destinationPath+screenShotName+".jpeg";
		
		try {
			// Store screenshot
			FileUtils.copyFile(sourceFile, new File(destinationPath));
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}
