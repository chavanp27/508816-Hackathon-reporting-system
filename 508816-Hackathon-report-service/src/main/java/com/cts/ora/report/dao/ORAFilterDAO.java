package com.cts.ora.report.dao;

import java.util.List;

import com.cts.ora.report.common.vo.ORARequest;
import com.cts.ora.report.domain.model.Associate;
import com.cts.ora.report.domain.model.BusinessUnit;
import com.cts.ora.report.domain.model.City;
import com.cts.ora.report.domain.model.Country;
import com.cts.ora.report.domain.model.EventCategory;
import com.cts.ora.report.domain.model.IncomingFile;
import com.cts.ora.report.domain.model.PinCode;
import com.cts.ora.report.domain.model.Project;
import com.cts.ora.report.domain.model.ResidenceArea;
import com.cts.ora.report.domain.model.State;
import com.cts.ora.report.vo.UserConfig;

public interface ORAFilterDAO {
	
	UserConfig getUserConfig(ORARequest req);

	List<Associate> getAllAssociates();
	
	List<Associate> getAssociateById(List<Long> ascIdLst);
	
	List<BusinessUnit> getAllBusinessUnits();
	
	List<Project> getAllProjects();
	List<EventCategory> getCategoriesByProject(List<Integer> projectIds);
	
	List<Country> getCountryById(List<Long> cntryId);
	List<State> getStateById(List<Long> stateId);
	List<City> getCityById(List<Long> cityId);
	List<ResidenceArea> getAreaById(List<Long> areaId);
	List<PinCode> getPincodeById(List<String> pinNums);
	
	List<IncomingFile> fetchDataLoadLog();
}
