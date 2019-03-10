package com.cts.ora.report.service;

import java.util.List;

import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.domain.model.BusinessUnit;
import com.cts.ora.report.domain.model.City;
import com.cts.ora.report.domain.model.Country;
import com.cts.ora.report.domain.model.EventCategory;
import com.cts.ora.report.domain.model.PinCode;
import com.cts.ora.report.domain.model.Project;
import com.cts.ora.report.domain.model.ResidenceArea;
import com.cts.ora.report.domain.model.State;
import com.cts.ora.report.domain.model.UserDetail;
import com.cts.ora.report.vo.ORAFilterRequest;

public interface ORAFilterService {
	
	List<UserDetail> getUsers();
	
	List<BusinessUnit> getBusinessUnits();
	
	ORAResponse fetchDataLoadLog(ORAFilterRequest request);
	
	List<Project> getAllProjects();
	
	List<EventCategory> getAllEventCategories();
	
	List<Country> getCountryById(List<Integer> cntryId);
	List<State> getStateById(List<Integer> stateId);
	List<City> getCityById(List<Integer> cityId);
	List<ResidenceArea> getAreaById(List<Integer> areaId);
	List<PinCode> getPincodeById(List<String> cntryId);

}
