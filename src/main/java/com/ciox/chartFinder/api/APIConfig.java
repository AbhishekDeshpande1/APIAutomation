package com.ciox.chartFinder.api;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author pradeep deivasigamani
 *
 */
public class APIConfig {
	
	private static String JSON_FILE_NAME = "APIConfig.json";
	private static JSONParser jsonParser = new JSONParser();
	private static FileReader jsonFileReader = null; 
	private static JSONObject jsonObject = null;
	
	public static void loadAPIConfig() throws IOException, ParseException {
		jsonFileReader = new FileReader(new File(JSON_FILE_NAME)); 
		jsonObject = (JSONObject) jsonParser.parse(jsonFileReader);
	}
	
	public static String getResourcePath(String key) throws IOException, ParseException {
		if (jsonObject == null)
			loadAPIConfig();
		return (String)jsonObject.get(key);
	}

}
