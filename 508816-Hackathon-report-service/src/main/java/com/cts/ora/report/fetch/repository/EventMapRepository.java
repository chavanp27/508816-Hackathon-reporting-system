package com.cts.ora.report.fetch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.ora.report.domain.model.AssociateEventMap;
@Repository
public interface EventMapRepository extends JpaRepository<AssociateEventMap, Long> {

	@Query(value="select event_id from ORA_OUTREACH_ASSOCIATE_EVENT_MAP where asc_id=:ascId",nativeQuery=true)
	List<String> getEventsForUser(@Param("ascId")Long ascId);
	
	@Query(value="select am from AssociateEventMap am where am.event.period between :startPeriod and :endPeriod")
	List<AssociateEventMap> getAsscEventForPeriod(@Param("startPeriod") Integer startPeriod,@Param("endPeriod")Integer endPeriod);
	
	@Query(value="select am from AssociateEventMap am where am.event.period between :startPeriod and :endPeriod and am.event.location.locId in :locIds")
	List<AssociateEventMap> getAsscEventForPeriodLoc(@Param("startPeriod") Integer startPeriod,@Param("endPeriod")Integer endPeriod,@Param("locIds")List<Integer> locIds);
	
	@Query(value="select am from AssociateEventMap am where am.event.period between :startPeriod and :endPeriod and am.asc.bu.buId in :buIds")
	List<AssociateEventMap> getAsscEventForPeriodBu(@Param("startPeriod") Integer startPeriod,@Param("endPeriod")Integer endPeriod,@Param("buIds")List<Integer> buIds);
	
}
