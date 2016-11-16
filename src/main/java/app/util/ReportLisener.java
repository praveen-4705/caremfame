package app.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentXReporter;

import app.variables.Variables;

/**
 * This class mainly used for reporting using the Extent Reports
 * @author praveen
 *
 */
public class ReportLisener implements IReporter {
	private ExtentHtmlReporter htmlReporter;
	private ExtentXReporter extentx;
	private ExtentReports extent;

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

		String date = new SimpleDateFormat("dd-MM-yy HH:mm:ss:SSSSSSS").format(new Date());
		htmlReporter = new ExtentHtmlReporter(outputDirectory + File.separator + suites.get(0).getName() +"_"+ date + ".html");
		
		htmlReporter.config().setChartVisibilityOnOpen(true);

		// Report configurations
		htmlReporter.config()
				.setJS("$(document).ready(function() { var link = document.createElement('link');link.type = 'image/x-icon';link.rel = 'shortcut icon';link.href = 'https://s.cdn-care.com/img/favicon.ico?v=160921';document.getElementsByTagName('head')[0].appendChild(link);  });"
						+ "$(document).ready(function() { var a = document.getElementsByClassName('brand-logo')[0]; a.innerHTML='Care.com'; a.href = 'http://www.care.com';});");
		htmlReporter.config().setDocumentTitle("Care QA Automation Report");
		htmlReporter.config().setReportName("QA Automation Report");
		// extentx.config().setServerUrl("http://localhost:1337/");
		extent = new ExtentReports();

		// server-url must be supplied otherwise images will not be uploaded
		// not setting a url for tests that add screen-shots wil result in a
		// IOException
		//
		extent.attachReporter(htmlReporter);
		// htmlReporter.config();

		// Put Failures on top of the report
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();
			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();
				buildTestNodes(context.getFailedTests(), Status.FAIL);
			}
		}

		// Skipped
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();
			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();
				buildTestNodes(context.getSkippedTests(), Status.SKIP);
			}
		}

		// Passed
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();
			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();
				buildTestNodes(context.getPassedTests(), Status.PASS);
			}
		}

		for (String s : Reporter.getOutput()) {
			extent.setTestRunnerOutput(s);
		}

		extent.flush();
		extent.close();
	}

	private void buildTestNodes(IResultMap tests, Status status) {
		ExtentTest test;

		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				String testName = result.getMethod().getMethodName();
				System.out.println(result.getMethod().getMethodName());
				test = extent.createTest(testName).assignAuthor(Variables.environmentName);

				// test.getTest().setStartedTime(getTime(result.getStartMillis()));
				// test.getTest().setEndedTime(getTime(result.getEndMillis()));

				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				String message = "Test " + status.toString().toLowerCase() + "ed";

				if (result.getThrowable() != null) {
					// test.log(status,
					// ExceptionUtils.getStackTrace(result.getThrowable()));
					test.log(status, "Test " + status.toString().toLowerCase() + "ed");
					try {
						test.addScreenCaptureFromPath(result.getName() + ".jpeg");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					test.log(status, "Test " + status.toString().toLowerCase() + "ed");

					try {
						test.addScreenCaptureFromPath(result.getName() + ".jpeg");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
}
