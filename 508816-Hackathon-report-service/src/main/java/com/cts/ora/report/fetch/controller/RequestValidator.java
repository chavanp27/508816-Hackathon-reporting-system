package com.cts.ora.report.fetch.controller;

import org.springframework.stereotype.Component;

import com.cts.ora.report.fetch.vo.FetchRequest;
import com.cts.ora.report.fetch.vo.FetchResponse;



@Component
public class RequestValidator {

	public FetchResponse validateFetchRequest(FetchRequest request) {
		FetchResponse res=new FetchResponse();
		
		if(null!= request && null!=request.getStartPeriod() && null!=request.getEndPeriod() && (null!=request.getGeography() || null!= request.getBu())) {
			res.setStatus("SUCCESS");
			res.setResponseText("Request processed successfully");
			res.setResponseCode("0000");
			return res;
		}
		res.setStatus("FAILURE");
		res.setResponseText("Incorrect request parameters");
		res.setResponseCode("1111");
		return res;
		
	}
}
