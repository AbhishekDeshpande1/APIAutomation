package com.ciox.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import io.cucumber.junit.Cucumber;
@RunWith(Cucumber.class)
@CucumberOptions(
		strict = true,
		monochrome = true, 
		features = {"features/API/chartFinderAPI.feature"},
		glue = {"com.ciox.cf.stepdefinition"},
		plugin = {"pretty", "html:target/cucumber-html-report","json:target/cucumber-html-report/API.json"}
)
public class APISuitRunner  {
   
}
