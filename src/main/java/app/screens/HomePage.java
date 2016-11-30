package app.screens;

import java.util.List;

import app.util.MobileDriver;
import app.variables.Variables;
import io.appium.java_client.MobileElement;

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
	
	/**
	 * Select the role based on the given parameter
	 * @param roleName
	 */
	public void selectRole(String roleName) {
		
		// Click on SingUp With Email
		click(Variables.homescreen_btn_SignUpEmail);
		
		// If role is Provider than tap on Find a Care Job other wise tap on Caregiver 
		if (roleName.equalsIgnoreCase("provider")) {
			click(Variables.homescreen_link_Provider);
		} else if (roleName.equalsIgnoreCase("seeker")) {
			click(Variables.homescreen_link_Seeker);
		}
	}
	
	/**
	 * Select given care service
	 * 
	 * It will accept name of care as a parameter
	 * @param nameOfCare
	 * @return	Return Enrollment Page Object
	 */
	public EnrollmentPage selectTypeOfCare(String nameOfCare){
		
		// Get all types of care elements
		List<MobileElement> l1 	= super.getAllVisibleElements(Variables.homescreen_link_AllTypesOfCare);
		List<String> l2			= super.getAllVisibleElementsText(Variables.homescreen_link_AllTypesOfCare);
		
		// Get index of the given Care
		int index = l2.indexOf(nameOfCare.trim());
		
		// Tap on selected care
		click(l1.get(index));
		
		// Return Enrollment Page Object
		return new EnrollmentPage(mDriver);
	}
}
