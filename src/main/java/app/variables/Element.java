package app.variables;

import org.openqa.selenium.By;

/**
 * Contains utilities for idenfying the elements path
 * 
 * @author praveen
 *
 */
public class Element {
	
	/**
	 * Locate element using @ID
	 * @param elementPath
	 * @return
	 */
	public static By getId(String elementPath){
		
		// Return Element path
		return By.id(elementPath);
	}
	
	/**
	 * Locate element using @CSS
	 * 
	 * @param elementPath
	 * @return
	 */
	public static By getCss(String elementPath){
		
		// Return element path
		return By.className(elementPath);
	}
}
