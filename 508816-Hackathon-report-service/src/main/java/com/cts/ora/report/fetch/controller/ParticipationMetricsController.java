package com.cts.ora.report.fetch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.cts.ora.report.fetch.service.ParticipationMetricsService;
import com.cts.ora.report.fetch.vo.FetchDetailsResponse;
import com.cts.ora.report.fetch.vo.FetchRequest;
import com.cts.ora.report.fetch.vo.FetchResponse;

@RestController
@RequestMapping(path="/api/v1/participation-metrics")
public class ParticipationMetricsController {

	@Autowired
	public RequestValidator validator;
	
	@Autowired
	public ParticipationMetricsService metricsService;
	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(path="/fetch",method=RequestMethod.POST,produces="application/json")
	public FetchResponse fetchMetrics(@RequestBody FetchRequest request) {
		FetchResponse res=validator.validateFetchRequest(request);
		if("SUCCESS".equals(res.getStatus())) {
			if(null!=request.getGeography()) {
				res.setParticipationMetrics(metricsService.getGeographyMetrics(request));
			}else if(null!=request.getBu()) {
				res.setParticipationMetrics(metricsService.getBUMetrics(request));
			}else {
				res.setParticipationMetrics(metricsService.getBUMetrics(request));
			}
		}
		return res;
		
	}
	
	@RequestMapping(path="/fetch/details",method=RequestMethod.POST,produces="application/json")
	public FetchDetailsResponse fetchMetricsDetails(@RequestBody FetchRequest request) {
		FetchDetailsResponse res=new FetchDetailsResponse();
		res.setStatus("SUCCESS");
		res.setResponseText("Request processed successfully");
		res.setResponseCode("0000");
		if(null!=request.getGeography()) {
			res.setParticipationMetrics(metricsService.getGeographyMetricsDetails(request));
		}
		return res;
		
	}
	
}
