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
	
	@PostMapping("/categories/get")
	@ResponseBody
	public FilterResponse getEventCategories(@RequestBody ORAFilterRequest req){
		logger.info("In getEventCategories");
		FilterResponse resp=new FilterResponse();
		try {
			resp.setCategories(service.getEventCategories(req.getProjectIds()));
			logger.info("resp::"+resp.getCategories());
			ORAMessageUtil.setSuccessMessage(resp);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		logger.info("Out of getEventCategories");
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
	
	@PostMapping("/stateByCntry/get")
	@ResponseBody
	public FilterResponse getStateByCountry(@RequestBody ORAFilterRequest req){
		logger.info("In getStateByCountry");
		FilterResponse resp=new FilterResponse();
		try {
			resp.setStates(service.getStateByCountry(req.getCountryIds()));
			logger.info("resp::"+resp.getStates());
			ORAMessageUtil.setSuccessMessage(resp);
			
		} catch (Exception e) {
			logger.error("Exception in getStateByCountry"+e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		logger.info("Out of getStateByCountry");
		return resp;
	}

	@PostMapping("/cityByState/get")
	@ResponseBody
	public FilterResponse getCityByState(@RequestBody ORAFilterRequest req){
		logger.info("In getCityByState");
		FilterResponse resp=new FilterResponse();
		try {
			resp.setCities(service.getCityByState(req.getStateIds()));
			logger.info("resp::"+resp.getCities());
			ORAMessageUtil.setSuccessMessage(resp);
			
		} catch (Exception e) {
			logger.error("Exception in getCityByState"+e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		logger.info("Out of getCityByState");
		return resp;
	}
	
	@PostMapping("/areaByCity/get")
	@ResponseBody
	public FilterResponse getAreaByCity(@RequestBody ORAFilterRequest req){
		logger.info("In getAreaByCity");
		FilterResponse resp=new FilterResponse();
		try {
			resp.setAreas(service.getAreaByCity(req.getCityIds()));
			logger.info("resp::"+resp.getAreas());
			ORAMessageUtil.setSuccessMessage(resp);
			
		} catch (Exception e) {
			logger.error("Exception in getAreaByCity"+e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		logger.info("Out of getAreaByCity");
		return resp;
	}
	
	@PostMapping("/pincodeByArea/get")
	@ResponseBody
	public FilterResponse getPincodeByArea(@RequestBody ORAFilterRequest req){
		logger.info("In getPincodeByArea");
		FilterResponse resp=new FilterResponse();
		try {
			resp.setPinCodes(service.getPincodeByArea(req.getAreaIds()));
			logger.info("resp::"+resp.getPinCodes());
			ORAMessageUtil.setSuccessMessage(resp);
			
		} catch (Exception e) {
			logger.error("Exception in getPincodeByArea"+e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		logger.info("Out of getPincodeByArea");
		return resp;
	}

}
