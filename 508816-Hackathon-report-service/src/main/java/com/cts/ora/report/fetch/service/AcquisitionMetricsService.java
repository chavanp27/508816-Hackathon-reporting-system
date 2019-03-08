package com.cts.ora.report.fetch.service;

import java.util.List;

import com.cts.ora.report.fetch.vo.AcquisitionMetrics;
import com.cts.ora.report.fetch.vo.FetchRequest;

public interface AcquisitionMetricsService {

	/**
	 * @param request
	 * @return
	 */
	public List<AcquisitionMetrics> getGeographyMetrics(FetchRequest request);
	
	/**
	 * @param request
	 * @return
	 */
	public List<AcquisitionMetrics>  getBUMetrics(FetchRequest request);
	
	/**
	 * @param request
	 * @return
	 */
	public List<AcquisitionMetrics>  getFocusAreaMetrics(FetchRequest request);
}
