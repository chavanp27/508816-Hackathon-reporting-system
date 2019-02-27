package com.cts.ora.report.dataload.dao;

import java.util.List;

import com.cts.ora.report.dataload.domain.Associate;
import com.cts.ora.report.dataload.domain.BusinessUnit;

public interface ORADataLoadDao {
	
	List<Associate> geAllAssociates();
	
	List<BusinessUnit> geAllBusinessUnits();
	
	void saveAssociates(List<Associate> associates);
	
	void saveEventInfo();

}
