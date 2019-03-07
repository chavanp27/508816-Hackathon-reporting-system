package com.cts.ora.report.filter;

import java.util.List;

import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.domain.model.Associate;
import com.cts.ora.report.domain.model.BusinessUnit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class FilterResponse extends ORAResponse {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Associate> associates;
	
	List<BusinessUnit> busUnits;

}
