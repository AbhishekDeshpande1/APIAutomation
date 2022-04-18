package com.ciox.runner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.automation.AutomationLibrary.AutomationLibrary;
import com.automation.AutomationLibrary.api.RestAssuredLibrary;
import com.automation.AutomationLibrary.ui.BrowserLibrary;
import com.automation.AutomationLibrary.ui.ElementLibrary;
import com.automation.AutomationLibrary.ui.PageLibrary;
import com.automation.AutomationLibrary.ui.WebdriverService;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * @author sushilmore
 *
 */
public class BaseRunner extends AbstractTestNGCucumberTests {

	private static Hashtable<Long, AutomationLibrary> automationLibraries = new Hashtable<Long, AutomationLibrary>();
	private static Hashtable<Long, String> roles = new Hashtable<Long, String>();
	private String scenarioName;
	private String scenarioID;
	private int iterationNumber;
	private static Map<String, Integer> startScenaioDetails = new HashMap<String, Integer>();
	private static Map<String, Integer> endScenaioDetails = new HashMap<String, Integer>();

	public BaseRunner() {
		loadConfig();
		loadLog4J();
	}

	private void loadConfig() {
		Properties prop = System.getProperties();
		String propFileName = "config.properties";
		try {
			InputStream inputStream = new FileInputStream(propFileName);
			prop.load(inputStream);
		} catch (IOException ex) {
			System.out.println("property file '" + propFileName + "' not found in the classpath");
		}
	}

	private void loadLog4J() {
		Properties prop = System.getProperties();
		String propFileName = "Log4j.properties";
		try {
			InputStream inputStream = new FileInputStream(propFileName);
			PropertyConfigurator.configure(inputStream);
		} catch (IOException ex) {
			System.out.println("property file '" + propFileName + "' not found in the classpath");
		}
	}

	@BeforeSuite(alwaysRun = true)
	public void setup() {
		System.out.println("Inside Before Suite");
	}

	@AfterSuite(alwaysRun = true)
	public void quit() throws InterruptedException {
		System.out.println("Inside After Suite");
	}

	@AfterMethod(alwaysRun = true)
	public void tearDownr(ITestResult result) throws IOException {
		System.out.println("Inside after Method");
	}

	@BeforeTest
	public void initializeUILibrary() {
		AutomationLibrary automationLibrary = new AutomationLibrary();
		automationLibrary.initializeUILibrary();
		addAutomationLibrary(automationLibrary);
	}

	@AfterTest
	public void destroyUILibrary() {
		getAutomationLibrary().destroyUILibrary();
	}

	private void addAutomationLibrary(AutomationLibrary automationLibrary) {
		long threadID = Thread.currentThread().getId();
		automationLibraries.put(new Long(threadID), automationLibrary);
	}

	protected AutomationLibrary getAutomationLibrary() {
		long threadID = Thread.currentThread().getId();
		return automationLibraries.get(new Long(threadID));
	}

	protected WebdriverService getWebDriverService() {
		long threadID = Thread.currentThread().getId();
		return automationLibraries.get(new Long(threadID)).getWebDriverService();
	}

	protected BrowserLibrary getBrowserLibrary() {
		long threadID = Thread.currentThread().getId();
		return automationLibraries.get(new Long(threadID)).getBrowserLibrary();
	}

	protected PageLibrary getPageLibrary() {
		long threadID = Thread.currentThread().getId();
		return automationLibraries.get(new Long(threadID)).getPageLibrary();
	}

	protected ElementLibrary getElementLibrary() {
		long threadID = Thread.currentThread().getId();
		return automationLibraries.get(new Long(threadID)).getElementLibrary();
	}
	
	protected RestAssuredLibrary getRestAssuredLibrary() {
		long threadID = Thread.currentThread().getId();
		return automationLibraries.get(new Long(threadID)).getRestAssuredLibrary();
	}

	public void setRoleName(String roleName) {
		long threadID = Thread.currentThread().getId();
		roles.put(new Long(threadID), roleName);
	}

	public String getRoleName() {
		long threadID = Thread.currentThread().getId();
		return roles.get(new Long(threadID));
	}

	public void startScenario(Scenario scenario) {
		this.scenarioName = scenario.getName();

		Collection<String> tagNames = scenario.getSourceTagNames();
		boolean isIDFound = false;
		for (String tagName : tagNames) {
			if (tagName.startsWith("@ID")) {
				tagName = tagName.substring(4, (tagName.length()));
				this.scenarioID = tagName;
				isIDFound = true;
				break;
			}
		}
		if (!isIDFound) {
			this.scenarioID = "NA";
		}
		setIterationsStart();
	}

	public void endScenario(Scenario scenario) {

		setIterationsComplete();
	}

	private void setIterationsStart() {
		if (startScenaioDetails.containsKey(scenarioID)) {
			// System.out.println("Inside Start");
			Integer iterationNumber = startScenaioDetails.get(scenarioID);
			Integer endIterationNumber = endScenaioDetails.get(scenarioID) == null ? 0
					: endScenaioDetails.get(scenarioID);
			// System.out.println("iterationNumber:"+iterationNumber);
			// System.out.println("endIterationNumber:"+endIterationNumber);
			if (iterationNumber <= endIterationNumber) {
				iterationNumber = iterationNumber + 1;
			}

			startScenaioDetails.put(scenarioID, iterationNumber);
		} else {
			startScenaioDetails.put(scenarioID, 1);
		}
	}

	private void setIterationsComplete() {
		if (endScenaioDetails.containsKey(scenarioID)) {
			// System.out.println("Inside End");
			Integer iterationNumber = startScenaioDetails.get(scenarioID);
			// System.out.println("iterationNumber:"+iterationNumber);
			Integer endIterationNumber = endScenaioDetails.get(scenarioID) == null ? 0
					: endScenaioDetails.get(scenarioID);
			// System.out.println("endIterationNumber:"+endIterationNumber);
			if (endIterationNumber < iterationNumber) {
				endIterationNumber = endIterationNumber + 1;
			}

			// iterationNumber = iterationNumber+1;
			endScenaioDetails.put(scenarioID, endIterationNumber);
		} else {
			endScenaioDetails.put(scenarioID, 1);
		}
	}

	public String getScenarioName() {
		return this.scenarioName;
	}

	public String getScenarioID() {
		return this.scenarioID;
	}

	public int getIterationNumber() {
		if (this.scenarioID == null) {
			return 1;
		} else {
			return startScenaioDetails.get(this.scenarioID);
		}
	}

	public void updateIterationNumber(String scenarioID, int iterationNumber) {
		startScenaioDetails.put(scenarioID, new Integer(iterationNumber));
	}

}
