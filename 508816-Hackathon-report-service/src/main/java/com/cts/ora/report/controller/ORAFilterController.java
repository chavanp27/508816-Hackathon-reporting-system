package com.cts.ora.report.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.ora.report.common.util.ORAMessageUtil;
import com.cts.ora.report.filter.FilterResponse;
import com.cts.ora.report.service.ORAFilterService;
import com.cts.ora.report.vo.ORAFilterRequest;

@RestController
@RequestMapping("/api/filter")
public class ORAFilterController {
	
	Logger logger = LoggerFactory.getLogger(ORAFilterController.class);
	
	@Autowired
	ORAFilterService service;
	
	@GetMapping("/users/get")
	@ResponseBody
	public FilterResponse getORAUsers(){
		logger.info("In getORAUsers");
		FilterResponse resp=new FilterResponse();
		try {
			resp.setUsers(service.getUsers());
			ORAMessageUtil.setSuccessMessage(resp);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		logger.info("Out of getORAUsers");
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
		logger.info("Out of getBusinessUnits");
		return resp;
	}
	
	@PostMapping("/projects/get")
	@ResponseBody
	public FilterResponse getProjects(@RequestBody ORAFilterRequest req){
		logger.info("In getProjects");
		FilterResponse resp=new FilterResponse();
		try {
			resp.setProjects(service.getAllProjects());
			logger.info("resp::"+resp.getProjects());
			ORAMessageUtil.setSuccessMessage(resp);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		logger.info("Out of getProjects");
		return resp;
	}
	
	@PostMapping("/country/get")
	@ResponseBody
	public FilterResponse getCountry(@RequestBody ORAFilterRequest req){
		logger.info("In getCountry");
		FilterResponse resp=new FilterResponse();
		try {
			resp.setCountries(service.getCountryById(req.getCountryIds()));
			logger.info("resp::"+resp.getCountries());
			ORAMessageUtil.setSuccessMessage(resp);
			
		} catch (Exception e) {
			logger.error("Exception in getCountry"+e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		logger.info("Out of getCountry");
		return resp;
	}


}
