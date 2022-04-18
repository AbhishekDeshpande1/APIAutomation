package com.automation.AutomationLibrary.api;

import java.io.File;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public interface RestAssuredLibrary {
	
	public void resetRestAssured();
	public Response getRequestCall(RequestSpecification requestSpec, String path);
	public Response postRequestCall(RequestSpecification requestSpec, String path);
	public RequestSpecification setRequestSpec_BaseURI(String baseURI);
	public RequestSpecification setRequestSpec_Body(RequestSpecification requestSpec, String body);
	public RequestSpecification setRequestSpec_Body(RequestSpecification requestSpec, File file);
	public RequestSpecification setRequestSpec_ContentType(RequestSpecification requestSpec, String contentType);
	public RequestSpecification setRequestSpec_Header(RequestSpecification requestSpec, String contentType, String contentValue);
	public RequestSpecification setRequestSpec_QueryParameter(RequestSpecification requestSpec, String key, String value);
	public RequestSpecification setRequestSpec_PathParameter(RequestSpecification requestSpec, String key, String value);
	public RequestSpecification setRequestSpec_Multipart(RequestSpecification requestSpec, String controlName, File file, String mimeType);
	public int getResponse_StatusCode(Response response);
	public String getResponse_StatusLine(Response response);
	public String getResponse_BodyAsString(Response response);
	public String getResponse_ContentType(Response response);
	public double getResponse_Time(Response response);
	public String getResponse_Headers(Response response);
	public String getResponse_Header(Response response, String name);
	public String getResponse_JSONString(Response response, String jsonKey);
}
