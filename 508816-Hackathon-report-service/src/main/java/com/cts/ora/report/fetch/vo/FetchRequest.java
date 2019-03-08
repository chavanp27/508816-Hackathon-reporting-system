package com.cts.ora.report.fetch.vo;

import java.util.List;

import com.cts.ora.report.common.vo.ORARequest;

import lombok.Data;

public @Data class FetchRequest extends ORARequest {

	private Integer startPeriod;
	
	private Integer endPeriod;
	
	private Geography geography;

	private List<String> bu;
	
	
}
