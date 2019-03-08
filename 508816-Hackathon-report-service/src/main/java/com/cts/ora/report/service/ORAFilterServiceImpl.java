package com.cts.ora.report.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cts.ora.report.domain.model.Associate;
import com.cts.ora.report.domain.model.BusinessUnit;

@Component
public class ORAFilterServiceImpl implements ORAFilterService {
	
	Logger logger = LoggerFactory.getLogger(ORAFilterServiceImpl.class);

	@Override
	public List<Associate> getAssociates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BusinessUnit> getBusinessUnits() {
		// TODO Auto-generated method stub
		return null;
	}

}
