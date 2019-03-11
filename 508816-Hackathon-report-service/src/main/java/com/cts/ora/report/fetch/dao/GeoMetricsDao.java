package com.cts.ora.report.fetch.dao;

import java.util.List;

import com.cts.ora.report.domain.model.GeoMetrics;

public interface GeoMetricsDao {

	public List<GeoMetrics> getGeoMetricsForUser(Integer startPeriod,Integer endPeriod,List<Integer> locIds,Long ascId);
}
