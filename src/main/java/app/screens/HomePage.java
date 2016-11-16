package app.screens;

import app.util.MobileDriver;
import app.variables.Variables;

/**
 * This class loaded with all the home page related methods and variables
 * 
 * @author praveen
 *
 */
public class HomePage extends BasePage {
	
	/**
	 * Home page constructor
	 * @param mDriver ({@link MobileDriver})
	 */
	public HomePage(MobileDriver mDriver) {
		super(mDriver);
	}
	
	/**
	 * Select Environment
	 */
	public void selectStack() {

		// Tap on Settings icon
		click(Variables.homescreen_icon_Settings);

		// Tap on HTTP
		click(Variables.homescreen_link_HTTP);

		// Get index of stack
		int index = getAllVisibleElementsText(Variables.homescreen_link_StackName).indexOf(mDriver.getEnvironment());
		
		// Select stack
		click(getAllVisibleElements(Variables.homescreen_link_StackName).get(index));
	}
}
