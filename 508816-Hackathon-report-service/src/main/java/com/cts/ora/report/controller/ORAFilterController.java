package com.cts.ora.report.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.ora.report.common.util.ORAMessageUtil;
import com.cts.ora.report.filter.FilterResponse;
import com.cts.ora.report.service.ORAFilterService;

@RestController
@RequestMapping("/api/filter")
public class ORAFilterController {
	
	Logger logger = LoggerFactory.getLogger(ORAFilterController.class);
	
	@Autowired
	ORAFilterService service;
	
	@GetMapping("/associates/get")
	@ResponseBody
	public FilterResponse getAssociates(){
		logger.info("In getAssociates");
		FilterResponse resp=new FilterResponse();
		try {
			resp.setAssociates(service.getAssociates());
			ORAMessageUtil.setSuccessMessage(resp);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		return resp;
	}
	
	@GetMapping("/bu/get")
	@ResponseBody
	public FilterResponse getBusinessUnits(){
		logger.info("In getBusinessUnits");
		FilterResponse resp=new FilterResponse();
		try {
			resp.setBusUnits(service.getBusinessUnits());
			ORAMessageUtil.setSuccessMessage(resp);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		return resp;
	}
	

}
