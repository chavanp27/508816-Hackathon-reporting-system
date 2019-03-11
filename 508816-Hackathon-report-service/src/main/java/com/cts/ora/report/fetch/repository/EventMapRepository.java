package com.cts.ora.report.fetch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.ora.report.domain.model.AssociateEventMap;

public interface EventMapRepository extends JpaRepository<AssociateEventMap, Long> {

	@Query(value="select * from ORA_OUTREACH_ASSOCIATE_EVENT_MAP where asc_id=:ascId",nativeQuery=true)
	List<String> getEventsForUser(Long ascId);
	
}
