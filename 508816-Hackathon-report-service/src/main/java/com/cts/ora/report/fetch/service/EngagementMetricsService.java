package com.cts.ora.report.fetch.service;

import java.util.List;

import com.cts.ora.report.fetch.vo.EngagementMetrics;
import com.cts.ora.report.fetch.vo.FetchRequest;

public interface EngagementMetricsService {

	/**
	 * @param request
	 * @return
	 */
	public List<EngagementMetrics> getGeographyMetrics(FetchRequest request);
	
	/**
	 * @param request
	 * @return
	 */
	public List<EngagementMetrics>  getBUMetrics(FetchRequest request);
	
	/**
	 * @param request
	 * @return
	 */
	public List<EngagementMetrics>  getFocusAreaMetrics(FetchRequest request);
}
