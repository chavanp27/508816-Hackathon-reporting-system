package com.cts.ora.report.dataload.service;

import java.util.List;

import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.dataload.domain.Associate;
import com.cts.ora.report.dataload.domain.BusinessUnit;
import com.cts.ora.report.dataload.vo.ORADataLoadRequest;

public interface ORADataLoadService {
	
	List<Associate> getAssociates();
	
	List<BusinessUnit> getBusinessUnits();
	
	ORAResponse loadIncomingFiles(ORADataLoadRequest request);
	
	ORAResponse fetchDataLoadLog(ORADataLoadRequest request);
	


}
