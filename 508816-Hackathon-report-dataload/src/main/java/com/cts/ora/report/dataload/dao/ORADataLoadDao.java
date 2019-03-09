package com.cts.ora.report.dataload.dao;

import java.util.List;
import java.util.Map;

import com.cts.ora.report.domain.model.Associate;
import com.cts.ora.report.domain.model.AssociateEventMap;
import com.cts.ora.report.domain.model.BusinessUnit;
import com.cts.ora.report.domain.model.Country;
import com.cts.ora.report.domain.model.EventCategory;
import com.cts.ora.report.domain.model.EventInfo;
import com.cts.ora.report.domain.model.IncomingFile;
import com.cts.ora.report.domain.model.Location;
import com.cts.ora.report.domain.model.Project;
import com.cts.ora.report.domain.model.ReportType;

public interface ORADataLoadDao {
	
	List<Associate> getAllAssociates();
	
	List<Associate> getAssociateById(List<Long> ascIdLst);
	
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
	
	List<EventInfo> getEventById(List<String> eventIds);
	
	void saveEventInfo(List<EventInfo> eventInfoList);
	
	void saveAssociateEventInfo(List<AssociateEventMap> ascEventInfo);
	
	String getFileLocationById(Long fileId, String boundType);

}
