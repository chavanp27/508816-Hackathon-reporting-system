package com.cts.ora.report.common.util;

import com.cts.ora.report.common.vo.ORAResponse;

public class ORAMessageUtil {

	public static void setSuccessMessage(ORAResponse response){
		response.setResponseCode("0000");
		response.setStatus("SUCCESS");
		response.setResponseText("Request Processed Successfully");
	}
	
	public static void setFailureMessage(ORAResponse response){
		response.setResponseCode("0001");
		response.setStatus("FAILURE");
		response.setResponseText("Failed to process request: Please Contact Support");
	}
	
	public static void setMessage(ORAResponse response, String responseText, 
				String responseCode, String responseStatus){
		response.setResponseCode(responseCode);
		response.setStatus(responseStatus);
		response.setResponseText(responseText);
	}
}
