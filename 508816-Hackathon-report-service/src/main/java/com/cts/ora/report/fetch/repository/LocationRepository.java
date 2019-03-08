package com.cts.ora.report.fetch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.ora.report.domain.model.Location;


@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

	@Query(value="Select loc_id from ora_ref_location where cntryId in :countryId and stateId in :stateId and cityId in :cityId and resAreaId in :resAreaId and codeId in :pinCodes",nativeQuery=true)
	public List<Integer> getLocationId(List<Integer> countryId,List<Integer> stateId,List<Integer> cityId,List<Integer> resAreaId,List<Integer> pinCodes);
	
	@Query(value="Select loc_id from ora_ref_location where cntryId in :countryId",nativeQuery=true)
	public List<Integer> getLocationIdsForCountry(List<Integer> countryId);
	
	@Query(value="Select loc_id from ora_ref_location where state_id in :stateId",nativeQuery=true)
	public List<Integer> getLocationIdsForState(List<Integer> stateId);
	
	@Query(value="Select loc_id from ora_ref_location where city_id in :cityId",nativeQuery=true)
	public List<Integer> getLocationIdsForCity(List<Integer> cityId);
	
	@Query(value="Select loc_id from ora_ref_location where resAreaId in :resAreaId",nativeQuery=true)
	public List<Integer> getLocationIdsForArea(List<Integer> resAreaId);
	
	@Query(value="SELECT loc_id from ora_ref_location where codeId in :pinCodes")
	public List<Integer> getLocationsForPin(List<Integer> pinCodes);
}
