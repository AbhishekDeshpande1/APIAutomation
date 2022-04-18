package com.automation.AutomationLibrary.api;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author pradeep deivasigamani
 *
 */
public class RestAssuredLibraryImpl implements RestAssuredLibrary {

	public void resetRestAssured() {
		RestAssured.reset();
	}
	
	public Response getRequestCall(RequestSpecification requestSpec, String path) {
		return requestSpec.get(path);
	}
	
	public Response postRequestCall(RequestSpecification requestSpec, String path) {
		return requestSpec.post(path);
	}
	
	public RequestSpecification setRequestSpec_BaseURI(String baseURI) {
		RestAssured.baseURI = baseURI;
		return RestAssured.given();
	}

	public RequestSpecification setRequestSpec_Body(RequestSpecification requestSpec, String body) {
		return requestSpec.body(body);
	}

	public RequestSpecification setRequestSpec_Body(RequestSpecification requestSpec, File file) {
		return requestSpec.body(file);
	}
	
	public RequestSpecification setRequestSpec_ContentType(RequestSpecification requestSpec, String contentType) {
		return requestSpec.contentType(contentType);
	}
	
	public RequestSpecification setRequestSpec_Header(RequestSpecification requestSpec, String contentType, String contentValue) {
		return requestSpec.header(contentType, contentValue);
	}
	
	public RequestSpecification setRequestSpec_QueryParameter(RequestSpecification requestSpec, String key, String value) {
		return requestSpec.queryParam(key, value);
	}

	public RequestSpecification setRequestSpec_PathParameter(RequestSpecification requestSpec, String key, String value) {
		return requestSpec.pathParam(key, value);
	}

	public RequestSpecification setRequestSpec_Multipart(RequestSpecification requestSpec, String controlName, File file, String mimeType) {
		return requestSpec.multiPart(controlName, file, mimeType);
	}

	public int getResponse_StatusCode(Response response) {
		return response.getStatusCode();
	}

	public String getResponse_StatusLine(Response response) {
		return response.getStatusLine();
	}

	public String getResponse_BodyAsString(Response response) {
		return response.getBody().asString();
	}
	
	public String getResponse_ContentType(Response response) {
		return response.getContentType();
	}

	public double getResponse_Time(Response response) {
		return response.getTime();
	}

	public String getResponse_Headers(Response response) {
		return response.getHeaders().toString();
	}

	public String getResponse_Header(Response response, String name) {
		return response.getHeader(name).toString();
	}

	public String getResponse_JSONString(Response response, String jsonKey) {
		return response.jsonPath().get(jsonKey).toString();
	}

}
