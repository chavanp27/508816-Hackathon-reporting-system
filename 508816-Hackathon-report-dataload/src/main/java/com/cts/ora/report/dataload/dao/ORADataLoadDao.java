package com.cts.ora.report.dataload.dao;

import java.util.List;
import java.util.Map;

import com.cts.ora.report.dataload.domain.Associate;
import com.cts.ora.report.dataload.domain.BusinessUnit;
import com.cts.ora.report.dataload.domain.Country;
import com.cts.ora.report.dataload.domain.EventCategory;
import com.cts.ora.report.dataload.domain.IncomingFile;
import com.cts.ora.report.dataload.domain.Location;
import com.cts.ora.report.dataload.domain.PinCode;
import com.cts.ora.report.dataload.domain.Project;
import com.cts.ora.report.dataload.domain.ReportType;

public interface ORADataLoadDao {
	
	List<Associate> getAllAssociates();
	
	List<BusinessUnit> getAllBusinessUnits();
	
	void saveAssociates(List<Associate> associates);
	
	ReportType getReportTypeByName(String name);
	
	List<IncomingFile> createIncomingFiles(List<IncomingFile> incomingFiles);
	
	void updateIncomingFileStatus(Long fileId, String status);
	
	List<Project> getAllProjects();
	
	List<EventCategory> getAllEventCategories();
	
	void saveProjectAndCategoryInfo(List<Project> projLst,List<EventCategory> eventCatLst);
	
	void saveLocation(Map<String,List> geoMap);
	
	List<Location> getLocationById(Long locId);
	List<Country> getCountryById(Long cntryId);
	Location getLocationBasedOnPinCode(String pinNum);
	
	/*List<Country> getCountryById(Long cntryId);
	List<State> getStateById(Long stateId);
	List<City> getCityById(Long cityId);
	List<ResidenceArea> getAreaById(Long areaId);
	List<PinCode> getPinCodeById(Long pinCodeId	);*/
	
	//void saveProjectCategoryMap(List<Event> projects);
	
	//void saveCategory(Long fileId, String status);
	
	void saveEventInfo();

}
