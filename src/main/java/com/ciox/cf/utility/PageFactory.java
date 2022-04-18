package com.ciox.cf.utility;

import com.automation.AutomationLibrary.api.RestAssuredLibrary;
import com.ciox.chartFinder.api.APIHelper;

public class PageFactory  {
	
	private APIHelper apiHelper;
	private RestAssuredLibrary restAssuredLibrary = null;

public APIHelper getAPIHelper() {
		if(apiHelper == null)
			return new APIHelper(restAssuredLibrary);
		else
			return apiHelper;
	}

}
	