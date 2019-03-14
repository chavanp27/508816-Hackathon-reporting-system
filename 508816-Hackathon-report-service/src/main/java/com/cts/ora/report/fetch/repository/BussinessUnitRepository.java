package com.cts.ora.report.fetch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.ora.report.domain.model.BusinessUnit;


@Repository
public interface BussinessUnitRepository extends JpaRepository<BusinessUnit, Integer> {

	@Query(value="Select bu_id from ora_ref_assc_bu where name in :bu",nativeQuery=true)
	List<Integer> findByName(@Param("bu")List<String> bu);


}
