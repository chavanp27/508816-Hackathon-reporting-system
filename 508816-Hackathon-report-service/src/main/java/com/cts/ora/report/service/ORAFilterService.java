package com.cts.ora.report.service;

import java.util.List;

import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.domain.model.BusinessUnit;
import com.cts.ora.report.domain.model.City;
import com.cts.ora.report.domain.model.Country;
import com.cts.ora.report.domain.model.EventCategory;
import com.cts.ora.report.domain.model.IncomingFile;
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
	
	List<EventCategory> getEventCategories(List<Integer> projectIds);
	
	List<Country> getCountryById(List<Integer> cntryId);
	List<State> getStateByCountry(List<Integer> cntryId);
	List<City> getCityByState(List<Integer> stateId);
	List<ResidenceArea> getAreaByCity(List<Integer> cityIds);
	List<PinCode> getPincodeByArea(List<Integer> areaIds);
	
	List<IncomingFile> getDataLoadHistory();

}
