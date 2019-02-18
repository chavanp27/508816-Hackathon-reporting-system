package com.cts.ora.report.dataload.dao;

import java.util.List;

import com.cts.ora.report.dataload.domain.Associate;

public interface ORADataLoadDao {
	
	List<Associate> geAllAssociates();
	
	void saveAssociates(List<Associate> associates);
	
	void saveEventInfo();

}
