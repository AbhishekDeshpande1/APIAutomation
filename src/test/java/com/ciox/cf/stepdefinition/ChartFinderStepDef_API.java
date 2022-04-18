package com.ciox.cf.stepdefinition;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.ciox.cf.utility.EnvironmentConfig;
import com.ciox.cf.utility.PageFactory;
import com.ciox.healthsource.api.APIConfig;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ChartFinderStepDef_API extends PageFactory {
	
	private final static Logger log = Logger.getLogger(ChartFinderStepDef_API.class.getName());
	
	
	@Given("User will generate token using valid credentials")
	public void user_will_generate_token_using_valid_credentials() throws Throwable {
		
		log.info("Request for token.");
		
    	getAPIHelper().getRestAssuredLibrary().resetRestAssured();
		getAPIHelper().resetRequestSpec();
		getAPIHelper().setRequest(getAPIHelper().getRestAssuredLibrary()
		.setRequestSpec_BaseURI(EnvironmentConfig.getURL("apiBaseURI")));
		
		getAPIHelper().setResponse(getAPIHelper().getRestAssuredLibrary()
		.postRequestCall(getAPIHelper().getRequest(), APIConfig.getResourcePath("chartFinderToken")));
		

	}

	@When("User created request and upload PDF and Non PDF file")
	public void user_created_request_and_upload_pdf_and_non_pdf_file() throws Throwable {
		
		getAPIHelper().setRequest(getAPIHelper().getRestAssuredLibrary()
		.setRequestSpec_BaseURI(EnvironmentConfig.getURL("apiBaseURI")));
			
		getAPIHelper().setResponse(getAPIHelper().getRestAssuredLibrary()
		.postRequestCall(getAPIHelper().getRequest(), APIConfig.getResourcePath("uploadAttach")));
				

	}

	@Then("Non PDF an PDF file should upload successfully")
	public void non_pdf_an_pdf_file_should_upload_successfully() {
	}

	@When("User created request and upload one PDF and multiple Non PDF file")
	public void user_created_request_and_upload_one_pdf_and_multiple_non_pdf_file() {
	}

	@Then("multiple Non PDF and PDF file should upload successfully")
	public void multiple_non_pdf_and_pdf_file_should_upload_successfully() {
	}

	@When("User created request and upload one PDF file only")
	public void user_created_request_and_upload_one_pdf_file_only() {
	}

	@Then("It should  accept only PDF file")
	public void it_should_accept_only_pdf_file() {
	}

	@When("User created request and upload files and enter invalid chartId")
	public void user_created_request_and_upload_files_and_enter_invalid_chart_id() {
	
	}

	@Then("Error message should display for invalid chartid")
	public void error_message_should_display_for_invalid_chartid() {

	}

	@When("User created request and upload files and enter invalid chart status")
	public void user_created_request_and_upload_files_and_enter_invalid_chart_status() {

	}

	@Then("Error message should display for invalid chart status")
	public void error_message_should_display_for_invalid_chart_status() {

	}

	@When("User created request and upload files and enter different chart id")
	public void user_created_request_and_upload_files_and_enter_different_chart_id() {

	}

	@Then("Success message should display and file should upload successfully")
	public void success_message_should_display_and_file_should_upload_successfully() {
	
	}

	

	
}
