package com.cts.ora.report.filter;

import java.util.List;

import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.domain.model.BusinessUnit;
import com.cts.ora.report.domain.model.Country;
import com.cts.ora.report.domain.model.Project;
import com.cts.ora.report.domain.model.State;
import com.cts.ora.report.domain.model.UserDetail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString(callSuper=true)
public class FilterResponse extends ORAResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<UserDetail> users;
	
	List<BusinessUnit> busUnits;
	
	List<Project> projects;
	
	List<Country> countries;
	
	List<State> states;

}
