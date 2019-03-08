package com.cts.ora.report.fetch.service.impl;

import com.cts.ora.report.fetch.vo.FetchRequest;

public class ServiceHelper {
	
	public static boolean isRequestForAllGeo(FetchRequest request) {
		return request.getGeography().getCountries().contains(-1) && request.getGeography().getStates().contains(-1) 
				&& request.getGeography().getCity().contains(-1) && request.getGeography().getArea().contains(-1);
	}
}
