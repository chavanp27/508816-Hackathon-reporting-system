package com.cts.ora.report.dataload.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.ora.report.dataload.domain.Associate;
import com.cts.ora.report.dataload.service.ORADataLoadService;
import com.cts.ora.report.dataload.vo.ORADataLoadRequest;


@RestController
@RequestMapping("/api")
public class ORADataLoadController {

	Logger logger = LoggerFactory.getLogger(ORADataLoadController.class);
	
	@Autowired
	ORADataLoadService service;
	
	@GetMapping("/hello")
	@ResponseBody
	public List<Associate> helloWorld(){
		logger.info("Hello in controller2");
		return service.getAssociates();
		
	}
	
	@PostMapping("/associate/load")
	@ResponseBody
	public String loadAssociateData(@RequestBody ORADataLoadRequest request){
		logger.info("Hello in controller");
		service.loadAssociateData(request);
		return "Data Laod complete";
	}
	
	
}
