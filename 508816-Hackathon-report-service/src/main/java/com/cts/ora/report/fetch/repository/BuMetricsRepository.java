package com.cts.ora.report.fetch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.ora.report.domain.model.BuMetrics;



@Repository
public interface BuMetricsRepository extends JpaRepository<BuMetrics, Integer> {

	@Query(value="select * from ora_bu_metrics where period between :startPeriod and :endPeriod",nativeQuery=true)
	public List<BuMetrics> getBuMetricsForPeriod(Integer startPeriod,Integer endPeriod);
	
	@Query(value="select * from ora_bu_metrics where period between :startPeriod and :endPeriod and loc_id in :locIds",nativeQuery=true)
	public List<BuMetrics> getBuMetricsForPeriodLocation(Integer startPeriod,Integer endPeriod,List<Integer> locIds);
}
