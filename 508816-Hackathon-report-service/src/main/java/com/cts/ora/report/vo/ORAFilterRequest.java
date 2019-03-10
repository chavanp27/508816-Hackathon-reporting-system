package com.cts.ora.report.vo;

import java.util.List;

import com.cts.ora.report.common.vo.ORARequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString(exclude={"serialVersionUID"},callSuper=true)
public class ORAFilterRequest extends ORARequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Integer> projectIds;
	private List<Integer> countryIds;
	private List<Integer> stateIds;
	private List<Integer> cityIds;
	private List<String> areaIds;

}
