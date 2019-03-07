package com.cts.ora.report.dataload.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cts.ora.report.common.util.JSONConverter;
import com.cts.ora.report.common.util.ORAMessageUtil;
import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.dataload.service.ORADataLoadService;
import com.cts.ora.report.dataload.vo.ORADataLoadRequest;
import com.cts.ora.report.file.vo.FileUploadResponse;
import com.cts.ora.report.filter.FilterResponse;

@RestController
@RequestMapping("/api")
public class ORADataLoadController {

	Logger logger = LoggerFactory.getLogger(ORADataLoadController.class);
	
	@Autowired
	ORADataLoadService service;
	
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
	
	@PostMapping("/file/upload")
	@ResponseBody
	public ORAResponse uploadFile(@RequestParam("file") MultipartFile file,
            						RedirectAttributes redirectAttributes){
		logger.info("Into uploadFile");
		FileUploadResponse resp=null;
		try {
			StopWatch watch = new StopWatch();
			watch.start();
			
			if (file.isEmpty()) {
	            ORAMessageUtil.setMessage(resp, "Please select a valid file", "0001", "FAILURE");
	            return resp;
	        }
			resp = service.uploadFile(file);
            
			watch.stop();
			logger.info("out of uploadFile");
			logger.info("UploadFile Time taken(seconds)=="+watch.getTotalTimeSeconds());
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
