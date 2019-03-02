package com.cts.ora.report.dataload.dao;

import java.util.List;

import com.cts.ora.report.dataload.domain.Associate;
import com.cts.ora.report.dataload.domain.BusinessUnit;
import com.cts.ora.report.dataload.domain.IncomingFiles;
import com.cts.ora.report.dataload.domain.ReportType;

public interface ORADataLoadDao {
	
	List<Associate> getAllAssociates();
	
	List<BusinessUnit> getAllBusinessUnits();
	
	void saveAssociates(List<Associate> associates);
	
	ReportType getReportTypeByName(String name);
	
	List<IncomingFiles> createIncomingFiles(List<IncomingFiles> incomingFiles);
	
	void updateIncomingFileStatus(Long fileId, String status);
	
	void saveEventInfo();

}
