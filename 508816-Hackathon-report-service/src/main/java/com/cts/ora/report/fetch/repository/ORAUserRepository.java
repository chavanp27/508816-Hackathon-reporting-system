package com.cts.ora.report.fetch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.ora.report.domain.model.ORAUser;

public interface ORAUserRepository extends JpaRepository<ORAUser, Long>{

	@Query(value="select * from ora_ui_user u join ora_outreach_associate a on u.emp_id=a.asc_id having a.asc_id=:id",nativeQuery=true)
	ORAUser getLoggedInUser(Long id);
}
