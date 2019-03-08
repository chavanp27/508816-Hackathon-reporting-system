package com.cts.ora.report.fetch.service;

import java.util.List;

import com.cts.ora.report.fetch.vo.FetchRequest;
import com.cts.ora.report.fetch.vo.ParticipationMetrics;

public interface ParticipationMetricsService {

	/**
	 * @param request
	 * @return
	 */
	public List<ParticipationMetrics> getGeographyMetrics(FetchRequest request);
	
	/**
	 * @param request
	 * @return
	 */
	public List<ParticipationMetrics>  getBUMetrics(FetchRequest request);
	
	/**
	 * @param request
	 * @return
	 */
	public List<ParticipationMetrics>  getFocusAreaMetrics(FetchRequest request);
}
