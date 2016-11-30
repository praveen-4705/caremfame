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
	
	public static final String deviceName 		= "Android GoogleAPI Emulator";
	public static final String version	 		= "5.0";
	public static final String platformName 	= "Android";
	public static final String apkName			= "base";
	public static final String appName			= "Care";
	public static final String defaultServer	= "127.0.0.1";
	public static final String defaultPort		= "4723";
	public static final String environmentName	= "uat2";
	public static final String iOS_Vesion		= "9.3";
	public static final String iOS_deviceName	= "iPhone 6s Plus";
	
	public static Properties environmentProperties	= new Properties();
	public static final String sauceUsername		= "Lavanya";
	public static final String sauceAccessKey		= "d617f080-4d9f-47bc-b943-67e5379da209";
	
	/* ***********************
	 * Android App variables
	 ************************/
	
	public static final String defaultPassword		= "letmein1";
	
	/* *******************
	 * Home Page Elements
	 ********************/
	
	public static final By homescreen_link_App			= By.id("android:id/text1");
	public static final By homescreen_icon_Settings		= By.id("settings_icon_buidType");
	public static final By homescreen_link_HTTP			= By.id("button_http");
	public static final By homescreen_link_StackName	= By.id("txtView_selectBuild_row");		
	public static final By homescreen_btn_SignUpEmail	= By.id("sign_up_email");
	public static final By homescreen_link_Provider		= By.id("provider_enrollment_layout");
	public static final By homescreen_link_Seeker		= By.id("seeker_enrollment_layout");
	public static final By homescreen_link_AllTypesOfCare = By.id("providerServiceTitle");
	
	/*
	 * Enrollment Page Elements
	 */
	public static final By enrollmentscreen_tb_FirstName	= By.id("first_name");
	public static final By enrollmentscreen_tb_LastName		= By.id("last_name");
	public static final By enrollmentscreen_tb_EmailId		= By.id("email");
	public static final By enrollmentscreen_tb_Password		= By.id("password");
	public static final By enrollmentscreen_tb_PhoneNumber	= By.id("phone_no");
	public static final By enrollmentscreen_tb_Birthday		= By.id("birth_day");
	public static final By enrollmentscreen_btn_JoinNow		= By.id("join_now_button");
	public static final By enrollmentscreen_popup_Yes		= By.id("yes");
	
	public static final By enrollmentscreen_link_Name		= Element.getId("");
}
