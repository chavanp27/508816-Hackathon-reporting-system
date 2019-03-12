package com.cts.ora.report.fetch.dao;

import java.util.List;

import com.cts.ora.report.fetch.vo.FocusAreaMetrics;

public interface FocusAreaMetricsDao {

	public List<FocusAreaMetrics> getFAMetricsForUser(Integer startPeriod,Integer endPeriod,List<Integer> locIds,Long ascId);
	public List<FocusAreaMetrics> getFAMetrics(Integer startPeriod,Integer endPeriod,List<Integer> locIds);
}
