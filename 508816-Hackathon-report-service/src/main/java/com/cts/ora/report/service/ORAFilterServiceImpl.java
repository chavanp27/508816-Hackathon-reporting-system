package com.cts.ora.report.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.dao.ORAFilterDAO;
import com.cts.ora.report.domain.model.BusinessUnit;
import com.cts.ora.report.domain.model.City;
import com.cts.ora.report.domain.model.Country;
import com.cts.ora.report.domain.model.EventCategory;
import com.cts.ora.report.domain.model.PinCode;
import com.cts.ora.report.domain.model.Project;
import com.cts.ora.report.domain.model.ResidenceArea;
import com.cts.ora.report.domain.model.State;
import com.cts.ora.report.domain.model.UserDetail;
import com.cts.ora.report.vo.ORAFilterRequest;

@Component
public class ORAFilterServiceImpl implements ORAFilterService {
	
	Logger logger = LoggerFactory.getLogger(ORAFilterServiceImpl.class);

	@Autowired
	ORAFilterDAO filterDAO;

	@Override
	public List<UserDetail> getUsers() {
		logger.info("Into getUsers");
		return filterDAO.getAllAssociates();
	}
	
	@Override
	public List<BusinessUnit> getBusinessUnits() {
		logger.info("Into getBusinessUnits");
		return filterDAO.getAllBusinessUnits();
	}

	@Override
	public ORAResponse fetchDataLoadLog(ORAFilterRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> getAllProjects() {
		logger.info("Into getAllProjects");
		return filterDAO.getAllProjects();
	}

	@Override
	public List<EventCategory> getAllEventCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Country> getCountryById(List<Integer> cntryId) {
		logger.info("Into getCountryById");
		return filterDAO.getCountryById(cntryId);
	}

	@Override
	public List<State> getStateById(List<Integer> stateId) {
		logger.info("Into getStateById");
		return filterDAO.getStateById(stateId);
	}

	@Override
	public List<City> getCityById(List<Integer> cityId) {
		logger.info("Into getCityById");
		return filterDAO.getCityById(cityId);
	}

	@Override
	public List<ResidenceArea> getAreaById(List<Integer> areaId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PinCode> getPincodeById(List<String> cntryId) {
		// TODO Auto-generated method stub
		return null;
	}

}
