package com.cts.ora.report.fetch.vo;

import java.util.List;

import lombok.Data;

public @Data class Geography {

	private List<Integer> countries;
	private List<Integer> states;
	private List<Integer> city;
	private List<String> baseLocation;
	private List<Integer> area;
	private List<Integer> pin;
	
	
	
	
}
