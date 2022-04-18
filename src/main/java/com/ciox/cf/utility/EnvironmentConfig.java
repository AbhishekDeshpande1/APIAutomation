package com.ciox.cf.utility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class EnvironmentConfig {
	private static String JSON_FILE_NAME="Environment.json";

	public static String getURL() throws IOException
	{
		String environment = System.getProperty("environment");
		System.out.println("environment:"+environment);
		File jsonFile = new File(JSON_FILE_NAME);
		String jsonString = FileUtils.readFileToString(jsonFile);
		JsonElement jelement = new JsonParser().parse(jsonString);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonObject jobject2 = jobject.getAsJsonObject(environment);
		return jobject2.get("URL").getAsString();
	}
	
	public static String getURL(String appName) throws IOException
	{
		String strURL;
		String environment = System.getProperty("environment");
		File jsonFile = new File(JSON_FILE_NAME);
		String jsonString = FileUtils.readFileToString(jsonFile);
		JsonElement jelement = new JsonParser().parse(jsonString);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonObject jobject2 = jobject.getAsJsonObject(environment);
		switch (appName.toUpperCase()) {
		case "AUDAPRO":
			strURL = jobject2.get("AudaURL").getAsString();
			break;
		case "CORPWEB":
			strURL = jobject2.get("CorpwebURL").getAsString();
			break;
		case "HSADMIN":
			strURL = jobject2.get("hsadminURL").getAsString();
			break;
		case "APIBASEURI":
			strURL = jobject2.get("apiBaseURI").getAsString();
			break;
		default:
			strURL = "";
			break;
		}
		return strURL;
	}
	
	public static String getAudaDocLocation() throws IOException
	{
		String environment = System.getProperty("environment");
		File jsonFile = new File(JSON_FILE_NAME);
		String jsonString = FileUtils.readFileToString(jsonFile);
		JsonElement jelement = new JsonParser().parse(jsonString);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonObject jobject2 = jobject.getAsJsonObject(environment);
		return jobject2.get("AudaDocLoc").getAsString();
	}
	
	
}
