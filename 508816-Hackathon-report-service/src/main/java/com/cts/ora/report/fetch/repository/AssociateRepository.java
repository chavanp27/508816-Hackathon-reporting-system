package com.cts.ora.report.fetch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.ora.report.domain.model.Associate;

@Repository
public interface AssociateRepository extends JpaRepository<Associate, Long> {

	@Query(value="select * from ora_outreach_associate where firstVolunteerDate between :startDate and :endDate",nativeQuery=true)
	List<Associate> getFirstTimeVolunteers(String startDate,String endDate);
}
