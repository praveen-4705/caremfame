package app.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import app.util.MobileDriver;
import app.variables.Variables;
import io.appium.java_client.MobileElement;

/**
 * This class is super class for all other classes
 * 
 * All the Utility methods, common methods will be placed here
 * 
 * @author praveen
 *
 */

public class BasePage {

	/** Driver Reference */
	protected MobileDriver mDriver;

	/**
	 * Base Page constructor, sets the driver
	 * 
	 * @param mDriver
	 *            driver created by BaseTest class
	 */
	public BasePage(MobileDriver mDriver) {
		this.mDriver = mDriver;
	}

	/**
	 * Locate element on the page
	 * 
	 * @param by Unique identifier of the element
	 * @return Returns the element or throws an exception
	 */
	public MobileElement locateElement(By by) {

		try {
			// Get the element by Unique identifier of the element
			MobileElement w = mDriver.getDriver().findElement(by);

			// Return WebElement
			return w;
		} catch (NoSuchElementException e) {

			// Shown Element not found message if the element is not found
			throw new NoSuchElementException("Element not found with given element path - " + by);
		}
	}
	
	/**
	 * Locate element on the screen using Accessibility Id
	 * @param name	Name of the element
	 * @return	Return Mobile Element
	 */
	public MobileElement locateElementByAccessibilityId(String name) {

		try {

			// Get the element by Accessibility Id
			MobileElement w = mDriver.getDriver().findElementByAccessibilityId(name);

			// Return Mobile Element
			return w;

		} catch (NoSuchElementException e) {

			// Shown Element not found message if the element is not found
			throw new NoSuchElementException("Element not found with given element name - " + name);
		}
	}
	
	/**
	 * Checks whether an element is present on the page or not
	 * @param name  Unique identifier of the element
	 * @return  Returns true if the element is found, returns false otherwise
	 */
	public boolean verifyPresenceOfElement(Object name) {

		// Get type of object
		String objectType = name.getClass().getName();

		try {
			
			// If element type is accessibility id than try to locate element using accessibility
			if (objectType.contains("String")) {
				
				mDriver.getDriver().findElementByAccessibilityId((String) name);
			} else if (objectType.contains("")) {
				
				mDriver.getDriver().findElement((By) name);
			}

		} catch (NoSuchElementException e) {
			// Return false if element is not found
			return false;
		} catch (Exception e) {
			// Return false if element is not found
			return false;
		}

		// Return true if element is found
		return true;
	}
	
	/**
	 * Write the String in the field
	 * @param by	Unique identifier of the element
	 * @param s		String to be written into the field
	 */
	public void type(By by, String s) {
		// Locate the element
		MobileElement w = locateElement(by);
		
		// Type the given string to the field
		type(w, s);
	}
	
	/**
	 * Clears the field and then writes in it
	 * @param w		Element that will be written into
	 * @param s		String to be written into the field
	 */
	public void type(MobileElement w, String s) {
		// Clear the field
		w.clear();
		
		// Write the string in it
		w.sendKeys(s);
	}
	
	/**
	 * Get Inner text of given element
	 * @param m
	 * @return	Return element text
	 */
	public String getTextOfElement(MobileElement m){
		
		// Return element inner text
		return m.getText().trim();
	}
	
	/**
	 * Get value from text box
	 * @param m
	 * @return	Return string value
	 */
	public String getTextFromTextBox(MobileElement m){
		
		// Return text box value
		return m.getAttribute("value").trim();
	}
	
	/**
	 * Tap on element
	 * @param m
	 */
	public void click(MobileElement m) {

		// CLick on element
		m.click();
	}
	
	/**
	 * Tap on element
	 * @param by
	 */
	public void click(By by) {
		// Locate element
		MobileElement m = locateElement(by);

		// Tap on element
		click(m);
	}
	
	/**
	 * Get List of visible elements
	 * @param by
	 * @return	Returns Visible element list
	 */
	public List<MobileElement> getAllVisibleElements(By by) {
		
		// Store all elements
		List<MobileElement> elementList 	 = mDriver.getDriver().findElements(by);
		List<MobileElement> visiableElements = new ArrayList<MobileElement>();
		
		// Iterate through list
		for (int i = 0; i < elementList.size(); i++) {
			
			// Check visibility
			if (elementList.get(i).isDisplayed()) {
				visiableElements.add(elementList.get(i));
			}
		}
		
		// Return visible element list 
		return visiableElements;
	}
	
	/**
	 * Get all visible elements text
	 * @param by
	 * @return	Return String element list
	 */
	public List<String> getAllVisibleElementsText(By by) {

		// Store elements
		List<MobileElement> visibleElementList = getAllVisibleElements(by);
		List<String> elementText = new ArrayList<String>();

		// Iterate through list
		for (int i = 0; i < visibleElementList.size(); i++) {

			// Add text to list
			elementText.add(getTextOfElement(visibleElementList.get(i)));
		}

		// Return list
		return elementText;
	}
	
	/**
	 * Load Environment properties
	 * @return	Return Property file object
	 */
	public Properties loadEnvironmentProperties() {
		Properties properties = Variables.environmentProperties;
		return properties;
	}
}
