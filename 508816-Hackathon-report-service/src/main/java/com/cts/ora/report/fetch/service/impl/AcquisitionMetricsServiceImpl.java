package com.cts.ora.report.fetch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.ora.report.domain.model.EventInfo;
import com.cts.ora.report.fetch.repository.AssociateRepository;
import com.cts.ora.report.fetch.repository.EventInfoRepository;
import com.cts.ora.report.fetch.service.AcquisitionMetricsService;
import com.cts.ora.report.fetch.service.LocationService;
import com.cts.ora.report.fetch.vo.AcquisitionMetrics;
import com.cts.ora.report.fetch.vo.FetchRequest;

@Service
public class AcquisitionMetricsServiceImpl implements AcquisitionMetricsService {

	@Autowired
	private AssociateRepository assosiateRepository;
	@Autowired
	private EventInfoRepository eventInfoRepository;
	@Autowired
	private LocationService locationService;
	
	@Override
	public List<AcquisitionMetrics> getGeographyMetrics(FetchRequest request) {
		List<EventInfo> metricsData=null;
		if(ServiceHelper.isRequestForAllGeo(request)) {
			metricsData=eventInfoRepository.getEventsByPeriod(request.getStartPeriod(), request.getEndPeriod());
		}else {
			metricsData=eventInfoRepository.getEventsByPeriodLocation(request.getStartPeriod(), request.getEndPeriod(), locationService.getLocationIds(request));
		}
		return null;
	}

	@Override
	public List<AcquisitionMetrics> getBUMetrics(FetchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AcquisitionMetrics> getFocusAreaMetrics(FetchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
