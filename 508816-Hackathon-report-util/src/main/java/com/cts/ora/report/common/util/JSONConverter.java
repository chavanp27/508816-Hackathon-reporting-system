package com.cts.ora.report.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONConverter {

	
	public static String toString(Object obj) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		return  mapper.writeValueAsString(obj);
		
	}
}
