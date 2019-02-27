package com.cts.ora.report.dataload.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cts.ora.report.dataload.domain.Associate;

@Repository
public interface AssociateRepository extends CrudRepository<Associate, Long> {
	
	@Override
	 public List<Associate> findAll();
	
	@Query("select DISTINCT a from ora_outreach_associate a")
	public List<Associate> findAllAssociates();

}
