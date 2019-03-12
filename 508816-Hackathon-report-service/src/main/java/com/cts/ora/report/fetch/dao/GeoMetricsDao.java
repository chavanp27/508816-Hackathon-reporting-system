package com.cts.ora.report.fetch.dao;

import java.util.List;

import com.cts.ora.report.domain.model.GeoMetrics;
import com.cts.ora.report.fetch.vo.ParticipationMetricsDetails;

public interface GeoMetricsDao {

	public List<GeoMetrics> getGeoMetricsForUser(Integer startPeriod,Integer endPeriod,List<Integer> locIds,Long ascId);
	public List<GeoMetrics> getGeoMetrics(Integer startPeriod,Integer endPeriod,List<Integer> locIds);
	public List<ParticipationMetricsDetails> getGeoMetricsDetails(Integer startPeriod, Integer endPeriod, List<Integer> locIds,Long ascId);
}
