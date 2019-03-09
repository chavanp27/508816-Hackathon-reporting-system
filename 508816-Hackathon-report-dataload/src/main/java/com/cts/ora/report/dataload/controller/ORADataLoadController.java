package com.cts.ora.report.dataload.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.ora.report.common.util.JSONConverter;
import com.cts.ora.report.common.util.ORAMessageUtil;
import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.dataload.service.ORADataLoadService;
import com.cts.ora.report.dataload.vo.ORADataLoadRequest;

@RestController
@RequestMapping("/api")
public class ORADataLoadController {

	Logger logger = LoggerFactory.getLogger(ORADataLoadController.class);
	
	@Autowired
	ORADataLoadService service;
	
	@PostMapping("/data/load")
	@ResponseBody
	public ORAResponse loadIncomingData(@RequestBody ORADataLoadRequest request){
		logger.info("Into loadIncomingData");
		ORAResponse resp=null;
		try {
			logger.info("Request=="+JSONConverter.toString(request));
			StopWatch watch = new StopWatch();
			watch.start();
			resp = service.loadIncomingFiles(request);
			watch.stop();
			logger.info("out of loadIncomingData");
			logger.info("Data Load Time taken(seconds)=="+watch.getTotalTimeSeconds());
		} catch (Exception e) {
			logger.error(e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		return resp;
	}
	
	@PostMapping("/masterData/load")
	@ResponseBody
	public ORAResponse loadMasterData(@RequestBody ORADataLoadRequest request){
		logger.info("Into loadMasterData");
		ORAResponse resp=null;
		try {
			logger.info("Request=="+JSONConverter.toString(request));
			StopWatch watch = new StopWatch();
			watch.start();
			resp = service.loadMasterDataFiles(request);
			watch.stop();
			logger.info("out of loadMasterData");
			logger.info("Data Load Time taken(seconds)=="+watch.getTotalTimeSeconds());
		} catch (Exception e) {
			logger.error(e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		return resp;
	}
	
	
}
