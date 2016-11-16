package app.variables;

import java.util.Properties;

import org.openqa.selenium.By;

/**
 * 
 * This is object repository, all the Web elements, default values values will be placed here
 * 
 * While giving the names for the WebElements we have to follow some naming standards and the naming standards are
 * 
 * devicename_pagename_linktype_ElementName Ex: android_homepage_icon_Setting
 * 
 * @author praveen
 *
 */
public class Variables {
	
	/* **********************
	 * Default Configurations
	 ************************/
	
	public static final String deviceName 		= "Samsung S6";
	public static final String platformName 	= "Android";
	public static final String apkName			= "base";
	public static final String appName			= "Care";
	public static final String defaultServer	= "127.0.0.1";
	public static final String defaultPort		= "4723";
	public static final String environmentName	= "uat2";
	public static final String iOS_Vesion		= "9.1";
	public static final String iOS_deviceName	= "iPhone 6s Plus";
	
	public static Properties environmentProperties	= new Properties();
	
	/* ***********************
	 * Android App variables
	 ************************/
	
	public static final By homescreen_link_App			= By.id("android:id/text1");
	public static final By homescreen_icon_Settings		= By.id("settings_icon_buidType");
	public static final By homescreen_link_HTTP			= By.id("button_http");
	public static final By homescreen_link_StackName	= By.id("txtView_selectBuild_row");			
}
