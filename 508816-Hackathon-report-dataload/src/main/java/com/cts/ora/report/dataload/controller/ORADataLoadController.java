package com.cts.ora.report.dataload.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.ora.report.common.util.ORAMessageUtil;
import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.dataload.domain.Associate;
import com.cts.ora.report.dataload.domain.BusinessUnit;
import com.cts.ora.report.dataload.service.ORADataLoadService;
import com.cts.ora.report.dataload.vo.ORADataLoadRequest;

@RestController
@RequestMapping("/api")
public class ORADataLoadController {

	Logger logger = LoggerFactory.getLogger(ORADataLoadController.class);
	
	@Autowired
	ORADataLoadService service;
	
	@GetMapping("/associates/get")
	@ResponseBody
	public List<Associate> getAssociates(){
		logger.info("In getAssociates");
		return service.getAssociates();
		
	}
	
	@GetMapping("/bu/get")
	@ResponseBody
	public List<BusinessUnit> getBusinessUnits(){
		logger.info("In getBusinessUnits");
		return service.getBusinessUnits();
		
	}
	
	@PostMapping("/data/load")
	@ResponseBody
	public ORAResponse loadAssociateData(@RequestBody ORADataLoadRequest request){
		logger.info("Into loadAssociateData");
		ORAResponse resp=null;
		try {
			StopWatch watch = new StopWatch();
			watch.start();
			resp = service.loadIncomingFiles(request);
			watch.stop();
			logger.info("out of loadAssociateData");
			logger.info("Data Load Time taken(seconds)=="+watch.getTotalTimeSeconds());
		} catch (Exception e) {
			logger.error(e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		return resp;
	}
	
	@PostMapping("/file/upload")
	@ResponseBody
	public ORAResponse uploadFile(@RequestBody ORADataLoadRequest request){
		logger.info("Into fetchDataLoadLog");
		ORAResponse resp=null;
		try {
			StopWatch watch = new StopWatch();
			watch.start();
			resp = service.loadIncomingFiles(request);
			watch.stop();
			logger.info("out of fetchDataLoadLog");
			logger.info("FetchDataLoadLog Time taken(seconds)=="+watch.getTotalTimeSeconds());
		} catch (Exception e) {
			logger.error(e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		return resp;
	}
	
	@PostMapping("/data/load/log")
	@ResponseBody
	public ORAResponse fetchDataLoadLog(@RequestBody ORADataLoadRequest request){
		logger.info("Into fetchDataLoadLog");
		ORAResponse resp=null;
		try {
			StopWatch watch = new StopWatch();
			watch.start();
			resp = service.fetchDataLoadLog(request);
			watch.stop();
			logger.info("out of fetchDataLoadLog");
			logger.info("FetchDataLoadLog Time taken(seconds)=="+watch.getTotalTimeSeconds());
		} catch (Exception e) {
			logger.error(e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		return resp;
	}
	
	
}
