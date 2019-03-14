package com.cts.ora.report.fetch.service.impl;

import com.cts.ora.report.common.util.ORAUtil;
import com.cts.ora.report.constants.ORAConstants;
import com.cts.ora.report.fetch.vo.FetchRequest;

public class ServiceHelper {
	
	public static boolean isRequestForAllGeo(FetchRequest request) {
		return request.getGeography().getCountries().contains(-1) && request.getGeography().getStates().contains(-1) 
				&& request.getGeography().getCity().contains(-1) && request.getGeography().getArea().contains(-1)&& request.getGeography().getPin().contains(-1);
	}
	
	public static boolean isRequestForAllBU(FetchRequest request) {
		return request.getBu().contains("-1");
	}
	public static void calculateStartPeriod(FetchRequest request) {
		if(ORAConstants.QUARTER_TYPE.equals(request.getPeriodType())) {
			request.setStartPeriod(ORAUtil.decrementPeriodBy(request.getStartPeriod(), -2));
		}else if(ORAConstants.HALF_YEAR_TYPE.equals(request.getPeriodType())) {
			request.setStartPeriod(ORAUtil.decrementPeriodBy(request.getStartPeriod(), -5));
		}
	}
}
