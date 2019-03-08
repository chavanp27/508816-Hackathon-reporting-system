package com.cts.ora.report.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.ora.report.common.util.ORAMessageUtil;
import com.cts.ora.report.common.vo.ORARequest;
import com.cts.ora.report.common.vo.ORAResponse;

@RestController
@RequestMapping("/api/user")
public class ORAAdminController {
	
	Logger logger = LoggerFactory.getLogger(ORAAdminController.class);
	
	@PostMapping("/data/load/log")
	@ResponseBody
	public ORAResponse fetchDataLoadLog(@RequestBody ORARequest request){
		logger.info("Into fetchDataLoadLog");
		ORAResponse resp=null;
		try {
			StopWatch watch = new StopWatch();
			watch.start();
			//resp = service.fetchDataLoadLog(request);
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
