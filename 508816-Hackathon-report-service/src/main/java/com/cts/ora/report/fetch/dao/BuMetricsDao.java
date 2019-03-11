package com.cts.ora.report.fetch.dao;

import java.util.List;

import com.cts.ora.report.domain.model.BuMetrics;

public interface BuMetricsDao {

	public List<BuMetrics> getBuMetricsForUser(Integer startPeriod,Integer endPeriod,List<Integer> locIds,Long ascId);
}
