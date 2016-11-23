package app.screens;

import app.util.MobileDriver;
import app.variables.Variables;

public class EnrollmentPage extends BasePage {
	
	/**
	 * Enrollment Page constructor
	 * @param mDriver
	 */
	public EnrollmentPage(MobileDriver mDriver) {
		super(mDriver);
	}
	
	/**
	 * Fill Enrollment Details
	 * @param typeOfEnrollment
	 */
	public void fillEnrollmentForm(String typeOfEnrollment){
		
		// Fill all the details
		type(Variables.enrollmentscreen_tb_FirstName, "FirstNamePro"+randomAlphabeticString(8));
		type(Variables.enrollmentscreen_tb_LastName, "LastNamePro"+randomAlphabeticString(8));
		type(Variables.enrollmentscreen_tb_EmailId, generateEmail(typeOfEnrollment));
		type(Variables.enrollmentscreen_tb_Password, Variables.defaultPassword);
		type(Variables.enrollmentscreen_tb_PhoneNumber, randomNumericString(10));
		type(Variables.enrollmentscreen_tb_Birthday, "08/08/1985");		
	}
	
	/**
	 * Complete Enrollment
	 * @param typeOfEnrollment
	 */
	public void completeEnrollment(String typeOfEnrollment){
		
		// Fill form details
		fillEnrollmentForm(typeOfEnrollment);
		
		// Tap on Join now button
		click(Variables.enrollmentscreen_btn_JoinNow);
		
		// Tap on YES button
		click(Variables.enrollmentscreen_popup_Yes);
	}
}
