package com.cts.ora.report.fetch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.ora.report.domain.model.GeoMetrics;



@Repository
public interface GeoMetricsRepository extends JpaRepository<GeoMetrics, Integer> {

	@Query(value="select * from ora_geo_metrics where period between :startPeriod and :endPeriod",nativeQuery=true)
	public List<GeoMetrics> getGeoMetricsForPeriod(Integer startPeriod,Integer endPeriod);
	
	@Query(value="select * from ora_geo_metrics where period between :startPeriod and :endPeriod and loc_id in :locIds",nativeQuery=true)
	public List<GeoMetrics> getGeoMetricsForPeriodLocation(Integer startPeriod,Integer endPeriod,List<Integer> locIds);
}
