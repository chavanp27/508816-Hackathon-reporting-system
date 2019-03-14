package com.cts.ora.report.fetch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.ora.report.domain.model.Location;


@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

	@Query(value="Select loc_id from ora_ref_location where cntry_id in :countryId and state_id in :stateId and city_id in :cityId and res_area_id in :resAreaId and code_id in :pinCodes",nativeQuery=true)
	public List<Integer> getLocationId(@Param("countryId") List<Integer> countryId,@Param("stateId")List<Integer> stateId,@Param("cityId")List<Integer> cityId,@Param("resAreaId") List<Integer> resAreaId,@Param("pinCodes") List<Integer> pinCodes);
	
	@Query(value="Select loc_id from ora_ref_location where cntry_id in :countryId",nativeQuery=true)
	public List<Integer> getLocationIdsForCountry(@Param("countryId")List<Integer> countryId);
	
	@Query(value="Select loc_id from ora_ref_location where state_id in :stateId",nativeQuery=true)
	public List<Integer> getLocationIdsForState(@Param("stateId")List<Integer> stateId);
	
	@Query(value="Select loc_id from ora_ref_location where city_id in :cityId",nativeQuery=true)
	public List<Integer> getLocationIdsForCity(@Param("cityId") List<Integer> cityId);
	
	@Query(value="Select loc_id from ora_ref_location where res_area_id in :resAreaId",nativeQuery=true)
	public List<Integer> getLocationIdsForArea(@Param("resAreaId") List<Integer> resAreaId);
	
	@Query(value="SELECT loc_id from ora_ref_location where code_id in :pinCodes",nativeQuery=true)
	public List<Integer> getLocationsForPin(@Param("pinCodes") List<Integer> pinCodes);
}
