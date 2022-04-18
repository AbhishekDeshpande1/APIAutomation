package com.ciox.chartFinder.api;


import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.automation.AutomationLibrary.api.RestAssuredLibrary;
import com.ciox.healthsource.DateTimeHelper;
import com.ciox.utils.FreemarkerTemplateUtil;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author pradeep deivasigamani
 *
 */
public class APIHelper {
	
	private static String requestAccessToken = StringUtils.EMPTY;
	private FreemarkerTemplateUtil ftl = new FreemarkerTemplateUtil();
	private RestAssuredLibrary restAssuredLibrary = null;
	private static RequestSpecification requestSpec = null;
	private static Response response = null;
	
	public APIHelper (RestAssuredLibrary restAssuredLib) {
		this.restAssuredLibrary = restAssuredLib;
	}
	
	public RestAssuredLibrary getRestAssuredLibrary() {
		return restAssuredLibrary;
	}
	
	public RequestSpecification getRequest() {
		return requestSpec;
	}
	
	public void resetRequestSpec() {
		requestSpec = null;
		response = null;
	}

	public void setRequest(RequestSpecification request) {
		APIHelper.requestSpec = request;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		APIHelper.response = response;
	}
	
	public void setAccessToken(String accessToken) {
		requestAccessToken = "Bearer "+ accessToken;
	}
	
	public String getAccessToken() {
		return requestAccessToken;
	}
	
	public String encode(String stringToEncode) throws UnsupportedEncodingException {
		return Base64.getEncoder().encodeToString(stringToEncode.getBytes("utf-8"));
	}
	
	public String getJSONString_HSLogin(String filePath, String templateName, Map<String, String> mapTestData) throws Throwable {
		
		Map<String, Object> mapReplaceData = new HashMap<String, Object>();
		mapReplaceData.put("userId", encode(mapTestData.get("username")));
		mapReplaceData.put("password", encode(mapTestData.get("password")));
		return ftl.replaceDataPlaceholders(filePath, templateName, mapReplaceData);
	}
	
	public String getJSONString_HSLoggingRequest(String filePath, String templateName, Map<String, String> mapTestData) throws Throwable {
		
		Map<String, String> mapReplaceData = new HashMap<String, String>();
		mapReplaceData = setHSRequestMap(mapTestData);
		mapReplaceData.put("loggingUserAction", mapTestData.get("requestAction"));
		return ftl.replaceDataPlaceholders(filePath, templateName, mapReplaceData);
	}
	
	public Map<String, String> setHSRequestMap(Map<String, String> mapTestData) throws Throwable {
		
		Map<String, String> mapReplaceData = new HashMap<String, String>();
		mapReplaceData.put("eRequestId", mapTestData.get("eRequestId"));
		mapReplaceData.put("siteId", mapTestData.get("site_id"));
		mapReplaceData.put("siteName", mapTestData.get("site_name"));
		mapReplaceData.put("createdBy", mapTestData.get("createdBy"));

		String requestType = mapTestData.get("request_type");
		if (requestType.equalsIgnoreCase("Continuity of Care")) 
			requestType = "Concare";
		mapReplaceData.put("requestTypeName", requestType);
		
		if (requestType.equalsIgnoreCase("DDS")) {
			mapReplaceData.put("ddsPoBox", "\"" + mapTestData.get("search_dds_pobox") + "\"");
			mapReplaceData.put("ddsState", "\"" + mapTestData.get("search_dds_ship_state").substring(0, 2) + "\"");
		} else {
			mapReplaceData.put("ddsPoBox", "null");
			mapReplaceData.put("ddsState", "null");
		}
		String isPatientRequest = requestType.equalsIgnoreCase("Patient") ? "true" : "false";
		mapReplaceData.put("isPatientRequest", isPatientRequest);
		
		String patientFirstName = mapTestData.get("patient_firstname") + RandomStringUtils.randomAlphabetic(5);
		String patientLastName = mapTestData.get("patient_lastname") + RandomStringUtils.randomAlphabetic(5);
		mapReplaceData.put("patientFirstName", patientFirstName);
		mapReplaceData.put("patientLastName", patientLastName);
		if (isPatientRequest.equalsIgnoreCase("true")) {
			mapReplaceData.put("requestorName", patientLastName + ", " + patientFirstName);
			mapReplaceData.put("billName", patientLastName + ", " + patientFirstName);
			mapReplaceData.put("shipName", patientLastName + ", " + patientFirstName);
			mapReplaceData.put("requestorID", "null");
		} else {
			mapReplaceData.put("requestorName", mapTestData.get("requester_name"));
			mapReplaceData.put("requestorID", mapTestData.get("requester_id"));
			mapReplaceData.put("billName", mapTestData.get("bill_name"));
			mapReplaceData.put("shipName", mapTestData.get("ship_name"));
		}
		
		mapReplaceData.put("majorClassCode", mapTestData.get("major_class_code"));
		mapReplaceData.put("requestorType", mapTestData.get("requester_type"));
		mapReplaceData.put("requesterDeliveryMethod", mapTestData.get("delivery_method_type"));

		mapReplaceData.put("billAddress1", mapTestData.get("bill_address1"));
		
		String billAddr2 = mapTestData.get("bill_address2");
		if (StringUtils.isEmpty(billAddr2))
			billAddr2 = "";
		mapReplaceData.put("billAddress2", billAddr2);
		
		String billAddr3 = mapTestData.get("bill_address3");
		if (StringUtils.isEmpty(billAddr3))
			billAddr3 = "";
		mapReplaceData.put("billAddress3", billAddr3);

		String billAddr4 = mapTestData.get("bill_address4");
		if (StringUtils.isEmpty(billAddr4))
			billAddr4 = "";
		mapReplaceData.put("billAddress4", billAddr4);

		mapReplaceData.put("billCity", mapTestData.get("bill_city"));
		mapReplaceData.put("billState", mapTestData.get("bill_state"));
		mapReplaceData.put("billCountry", mapTestData.get("bill_country"));
		mapReplaceData.put("billZip", mapTestData.get("bill_zip"));
		mapReplaceData.put("billCompleteAddress", "");
		
		mapReplaceData.put("shipAddress1", mapTestData.get("ship_address1"));
		
		String shipAddr2 = mapTestData.get("ship_address2");
		if (StringUtils.isEmpty(shipAddr2))
			shipAddr2 = "";
		mapReplaceData.put("shipAddress2", shipAddr2);
		
		String shipAddr3 = mapTestData.get("ship_address3");
		if (StringUtils.isEmpty(shipAddr3))
			shipAddr3 = "";
		mapReplaceData.put("shipAddress3", shipAddr3);

		String shipAddr4 = mapTestData.get("ship_address4");
		if (StringUtils.isEmpty(shipAddr4))
			shipAddr4 = "";
		mapReplaceData.put("shipAddress4", shipAddr4);

		mapReplaceData.put("shipCity", mapTestData.get("ship_city"));
		mapReplaceData.put("shipState", mapTestData.get("ship_state"));
		mapReplaceData.put("shipCountry", mapTestData.get("ship_country"));
		mapReplaceData.put("shipZip", mapTestData.get("ship_zip"));
		mapReplaceData.put("shipCompleteAddress", "");
		
		mapReplaceData.put("deliveryType", mapTestData.get("delivery_method_type"));
		mapReplaceData.put("deliveryMethodCode", mapTestData.get("delivery_method_code"));
		
		String shipAsBill = mapTestData.get("ship_same_bill");
		mapReplaceData.put("sameAsBillingAddress", shipAsBill);
		shipAsBill = shipAsBill.equals("true") ? "Y" : "N";
		mapReplaceData.put("isSameAsBilling", shipAsBill);
		
		mapReplaceData.put("shipAttentionOf", mapTestData.get("attention_to"));
		mapReplaceData.put("shipRequestedBy", mapTestData.get("requested_by"));
		String certRequired = mapTestData.get("certification_required").equals("true") ? "Y" : "N";
		mapReplaceData.put("certRequired", certRequired);
		
		mapReplaceData.put("patientDob", mapTestData.get("patient_dob"));
		mapReplaceData.put("serviceStartDate", mapTestData.get("date_range_from"));
		mapReplaceData.put("serviceEndDate", mapTestData.get("date_range_to"));
		
		mapReplaceData.put("mainReasonCode", mapTestData.get("primary_reason_code"));
		mapReplaceData.put("invoiceTypeCode", mapTestData.get("invoice_type_code"));
		String refineReasonCode = mapTestData.get("secondary_reason_code");
		if (StringUtils.isEmpty(refineReasonCode))
			refineReasonCode = "null";
		else
			refineReasonCode = "\"" + refineReasonCode + "\"";
		mapReplaceData.put("refineReasonCode", refineReasonCode);
		
		mapReplaceData.put("mainReason", mapTestData.get("primary_reason"));
		String refineReason = mapTestData.get("secondary_reason");
		if (StringUtils.isEmpty(refineReason))
			refineReason = "null";
		else
			refineReason = "\"" + refineReason + "\"";
		mapReplaceData.put("refineReason", refineReason);
		
		String dueDaysString = mapTestData.get("due_no_days_complete");
		int dueDays = 0;
		String dueDate = StringUtils.EMPTY;
		if (StringUtils.isEmpty(dueDaysString)) {
			dueDaysString = "";
			dueDate = "";
		} else {
			dueDays = Integer.parseInt(dueDaysString);
			dueDate = DateTimeHelper.getDateByAddDays(dueDays);
		}
		mapReplaceData.put("dueDays", dueDaysString);
		mapReplaceData.put("dueDate", dueDate);
		
		mapReplaceData.put("isStatRequest", mapTestData.get("concare_stat"));
		mapReplaceData.put("midnight", mapTestData.get("two_midnight_rule"));
		mapReplaceData.put("dateReceived", DateTimeHelper.getCurrentDate());
		mapReplaceData.put("requestLetterDate", DateTimeHelper.getCurrentDate());
		return mapReplaceData;
	}
	
	public String getJSONString_HSPullListContinueRequest(String filePath, String templateName, Map<String, String> mapTestData) throws Throwable {
		
		Map<String, String> mapReplaceData = new HashMap<String, String>();
		mapReplaceData = setHSPullListContinueRequest(mapTestData);
		return ftl.replaceDataPlaceholders(filePath, templateName, mapReplaceData);
	}
	
	public Map<String, String> setHSPullListContinueRequest(Map<String, String> mapTestData) throws Throwable {
		
		Map<String, String> mapReplaceData = new HashMap<String, String>();
		mapReplaceData.put("eRequestId", mapTestData.get("eRequestId"));
		mapReplaceData.put("siteId", mapTestData.get("site_id"));
		mapReplaceData.put("siteName", mapTestData.get("site_name"));
		mapReplaceData.put("createdBy", mapTestData.get("createdBy"));
		mapReplaceData.put("requestorName", mapTestData.get("requester_name"));
		mapReplaceData.put("requestorID", mapTestData.get("requester_id"));
		mapReplaceData.put("majorClassCode", mapTestData.get("major_class_code"));
		mapReplaceData.put("requestorType", mapTestData.get("requester_type"));
		mapReplaceData.put("requesterDeliveryMethod", mapTestData.get("delivery_method_type"));
		mapReplaceData.put("billName", mapTestData.get("bill_name"));
		mapReplaceData.put("billAddress1", mapTestData.get("bill_address1"));
		
		String billAddr2 = mapTestData.get("bill_address2");
		if (StringUtils.isEmpty(billAddr2))
			billAddr2 = "";
		mapReplaceData.put("billAddress2", billAddr2);
		
		String billAddr3 = mapTestData.get("bill_address3");
		if (StringUtils.isEmpty(billAddr3))
			billAddr3 = "";
		mapReplaceData.put("billAddress3", billAddr3);

		String billAddr4 = mapTestData.get("bill_address4");
		if (StringUtils.isEmpty(billAddr4))
			billAddr4 = "";
		mapReplaceData.put("billAddress4", billAddr4);

		mapReplaceData.put("billCity", mapTestData.get("bill_city"));
		mapReplaceData.put("billState", mapTestData.get("bill_state"));
		mapReplaceData.put("billCountry", mapTestData.get("bill_country"));
		mapReplaceData.put("billZip", mapTestData.get("bill_zip"));
		mapReplaceData.put("billCompleteAddress", "");
		
		mapReplaceData.put("shipName", mapTestData.get("ship_name"));
		mapReplaceData.put("shipAddress1", mapTestData.get("ship_address1"));
		
		String shipAddr2 = mapTestData.get("ship_address2");
		if (StringUtils.isEmpty(shipAddr2))
			shipAddr2 = "";
		mapReplaceData.put("shipAddress2", shipAddr2);
		
		String shipAddr3 = mapTestData.get("ship_address3");
		if (StringUtils.isEmpty(shipAddr3))
			shipAddr3 = "";
		mapReplaceData.put("shipAddress3", shipAddr3);

		String shipAddr4 = mapTestData.get("ship_address4");
		if (StringUtils.isEmpty(shipAddr4))
			shipAddr4 = "";
		mapReplaceData.put("shipAddress4", shipAddr4);

		mapReplaceData.put("shipCity", mapTestData.get("ship_city"));
		mapReplaceData.put("shipState", mapTestData.get("ship_state"));
		mapReplaceData.put("shipCountry", mapTestData.get("ship_country"));
		mapReplaceData.put("shipZip", mapTestData.get("ship_zip"));
		mapReplaceData.put("shipCompleteAddress", "");
		
		mapReplaceData.put("deliveryType", mapTestData.get("delivery_method_type"));
		mapReplaceData.put("deliveryMethodCode", mapTestData.get("delivery_method_code"));
		
		String shipAsBill = mapTestData.get("ship_same_bill");
		mapReplaceData.put("sameAsBillingAddress", shipAsBill);
		shipAsBill = shipAsBill.equals("true") ? "Y" : "N";
		mapReplaceData.put("isSameAsBilling", shipAsBill);
		
		mapReplaceData.put("shipAttentionOf", mapTestData.get("attention_to"));
		mapReplaceData.put("shipRequestedBy", mapTestData.get("requested_by"));
		String certRequired = mapTestData.get("certification_required").equals("true") ? "Y" : "N";
		mapReplaceData.put("certRequired", certRequired);
		
		mapReplaceData.put("mainReasonCode", mapTestData.get("primary_reason_code"));
		mapReplaceData.put("invoiceTypeCode", mapTestData.get("invoice_type_code"));
		String refineReasonCode = mapTestData.get("secondary_reason_code");
		if (StringUtils.isEmpty(refineReasonCode))
			refineReasonCode = "null";
		else
			refineReasonCode = "\"" + refineReasonCode + "\"";
		mapReplaceData.put("refineReasonCode", refineReasonCode);
		
		mapReplaceData.put("mainReason", mapTestData.get("primary_reason"));
		String refineReason = mapTestData.get("secondary_reason");
		if (StringUtils.isEmpty(refineReason))
			refineReason = "null";
		else
			refineReason = "\"" + refineReason + "\"";
		mapReplaceData.put("refineReason", refineReason);
		
		String dueDaysString = mapTestData.get("due_no_days_complete");
		int dueDays = 0;
		String dueDate = StringUtils.EMPTY;
		if (StringUtils.isEmpty(dueDaysString)) {
			dueDaysString = "";
			dueDate = "";
		} else {
			dueDays = Integer.parseInt(dueDaysString);
			dueDate = DateTimeHelper.getDateByAddDays(dueDays);
		}
		mapReplaceData.put("dueDays", dueDaysString);
		mapReplaceData.put("dueDate", dueDate);
		
		mapReplaceData.put("midnight", mapTestData.get("two_midnight_rule"));
		mapReplaceData.put("dateReceived", DateTimeHelper.getCurrentDate());
		mapReplaceData.put("requestLetterDate", DateTimeHelper.getCurrentDate());
		return mapReplaceData;
	}
	
	public String getJSONString_HSPullListChildRequest(String filePath, String templateName, Map<String, String> mapTestData) throws Throwable {
		
		Map<String, String> mapReplaceData = new HashMap<String, String>();
		mapReplaceData.put("eRequestId", mapTestData.get("eRequestId"));
		mapReplaceData.put("requestorId", mapTestData.get("requester_id"));
		mapReplaceData.put("patientDob", mapTestData.get("patient_dob"));
		mapReplaceData.put("patientFirstName", mapTestData.get("patient_firstname") + RandomStringUtils.randomAlphabetic(5));
		mapReplaceData.put("patientLastName", mapTestData.get("patient_lastname") + RandomStringUtils.randomAlphabetic(5));
		mapReplaceData.put("serviceStartDate", mapTestData.get("date_range_from"));
		mapReplaceData.put("serviceEndDate", mapTestData.get("date_range_to"));
		return ftl.replaceDataPlaceholders(filePath, templateName, mapReplaceData);
	}
	
	public String getJSONString_HSPullListCompleteRequest(String filePath, String templateName, Map<String, String> mapTestData) throws Throwable {
		
		Map<String, String> mapReplaceData = new HashMap<String, String>();
		mapReplaceData.put("eRequestId", mapTestData.get("eRequestId"));
		return ftl.replaceDataPlaceholders(filePath, templateName, mapReplaceData);
	}
	
	public String getJSONString_HSLoggingOpsRequest(String filePath, String templateName, Map<String, String> mapTestData) throws Throwable {
		
		Map<String, String> mapReplaceData = new HashMap<String, String>();
		mapReplaceData = setHSRequestMap(mapTestData);
		String loggingOpsUserActionVal = mapTestData.get("requestAction");
		switch (loggingOpsUserActionVal) {
		case "Logging_OnHold":
			mapReplaceData.put("placeOnHoldReason", "\"" + mapTestData.get("api_reasoncode_logginghold") + "\"");
			mapReplaceData.put("exceptionReason", "null");
			mapReplaceData.put("pendReason", "null");
			mapReplaceData.put("reasonDesc", mapTestData.get("api_reasondesc_logginghold"));
			mapReplaceData.put("reasonId", mapTestData.get("api_reasonid_logginghold"));
			break;
		case "Logging_Exception":
			mapReplaceData.put("placeOnHoldReason", "null");
			mapReplaceData.put("exceptionReason", "\"" + mapTestData.get("api_reasoncode_loggingexception") + "\"");
			mapReplaceData.put("pendReason", "null");
			mapReplaceData.put("reasonDesc", mapTestData.get("api_reasondesc_loggingexception"));
			mapReplaceData.put("reasonId", mapTestData.get("api_reasonid_loggingexception"));
			break;
		default:
			break;
		}
		mapReplaceData.put("loggingOpsUserAction", loggingOpsUserActionVal);
		return ftl.replaceDataPlaceholders(filePath, templateName, mapReplaceData);
	}
	
	public String getJSONString_HSFulfillOpsRequest(String filePath, String templateName, Map<String, String> mapTestData) throws Throwable {
		
		Map<String, String> mapReplaceData = new HashMap<String, String>();
		mapReplaceData = setHSRequestMap(mapTestData);
		String fulfillOpsUserActionVal = mapTestData.get("requestAction");
		switch (fulfillOpsUserActionVal) {
		case "Fulfillment_OnHold":
			mapReplaceData.put("placeOnHoldReason", "\"" + mapTestData.get("api_reasoncode_fulfillhold") + "\"");
			mapReplaceData.put("exceptionReason", "null");
			mapReplaceData.put("pendReason", "null");
			mapReplaceData.put("reasonDesc", mapTestData.get("api_reasondesc_fulfillhold"));
			mapReplaceData.put("reasonId", mapTestData.get("api_reasonid_fulfillhold"));
			break;
		case "Fulfillment_Exception":
			mapReplaceData.put("placeOnHoldReason", "null");
			mapReplaceData.put("exceptionReason", "\"" + mapTestData.get("api_reasoncode_fulfillexception") + "\"");
			mapReplaceData.put("pendReason", "null");
			mapReplaceData.put("reasonDesc", mapTestData.get("api_reasondesc_fulfillexception"));
			mapReplaceData.put("reasonId", mapTestData.get("api_reasonid_fulfillexception"));
			break;
		case "Fulfillment_Pend":
			mapReplaceData.put("placeOnHoldReason", "null");
			mapReplaceData.put("exceptionReason", "null");
			mapReplaceData.put("pendReason", "\"" + mapTestData.get("api_reasoncode_fulfillpend") + "\"");
			mapReplaceData.put("reasonDesc", mapTestData.get("api_reasondesc_fulfillpend"));
			mapReplaceData.put("reasonId", mapTestData.get("api_reasonid_fulfillpend"));
			break;
		default:
			break;
		}
		mapReplaceData.put("fulfillOpsUserAction", fulfillOpsUserActionVal);
		return ftl.replaceDataPlaceholders(filePath, templateName, mapReplaceData);
	}

}
