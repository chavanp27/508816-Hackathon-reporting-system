package com.cts.ora.report.fetch.controller;

import org.springframework.stereotype.Component;

import com.cts.ora.report.fetch.vo.FetchRequest;
import com.cts.ora.report.fetch.vo.FetchResponse;



@Component
public class RequestValidator {

	public FetchResponse validateFetchRequest(FetchRequest request) {
		if(null!= request && null!=request.getStartPeriod() && null!=request.getEndPeriod() && (null!=request.getGeography() || null!= request.getBu())) {
			return new FetchResponse("SUCCESS","0000","Request processed successfully");
		}
		
		return new FetchResponse("FAILURE","1111","Incorrect request parameters");
		
	}
}
