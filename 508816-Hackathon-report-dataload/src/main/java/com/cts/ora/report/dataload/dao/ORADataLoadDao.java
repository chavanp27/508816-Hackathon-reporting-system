package com.cts.ora.report.dataload.dao;

import java.util.List;

import com.cts.ora.report.dataload.domain.Associate;
import com.cts.ora.report.dataload.domain.BusinessUnit;

public interface ORADataLoadDao {
	
	List<Associate> getAllAssociates();
	
	List<BusinessUnit> getAllBusinessUnits();
	
	void saveAssociates(List<Associate> associates);
	
	void saveEventInfo();

}
