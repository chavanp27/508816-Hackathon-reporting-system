package com.cts.ora.report.service;

import java.util.List;

import com.cts.ora.report.domain.model.Associate;
import com.cts.ora.report.domain.model.BusinessUnit;

public interface ORAFilterService {
	
	List<Associate> getAssociates();
	
	List<BusinessUnit> getBusinessUnits();

}
