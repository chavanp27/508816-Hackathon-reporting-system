package com.cts.ora.report.fetch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.ora.report.domain.model.EventInfo;


@Repository
public interface EventInfoRepository extends JpaRepository<EventInfo, String> {

	@Query(value="select * from ora_outreach_event_info where period between :startPeriod and :endPeriod",nativeQuery=true)
	List<EventInfo> getEventsByPeriod(@Param("startPeriod") Integer startPeriod,@Param("endPeriod")Integer endPeriod);
	
	@Query(value="select * from ora_outreach_event_info where period between :startPeriod and :endPeriod and locId in :locIds",nativeQuery=true)
	List<EventInfo> getEventsByPeriodLocation(@Param("startPeriod")Integer startPeriod,@Param("endPeriod")Integer endPeriod,@Param("locIds")List<Integer> locIds);
}
