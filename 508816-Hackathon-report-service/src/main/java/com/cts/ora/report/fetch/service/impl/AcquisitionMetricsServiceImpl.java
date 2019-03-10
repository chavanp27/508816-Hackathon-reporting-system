package com.cts.ora.report.fetch.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.DateTimeParser;
import org.springframework.stereotype.Service;

import com.cts.ora.report.common.util.ORAUtil;
import com.cts.ora.report.domain.model.Associate;
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
		return calculateAcquisitionMetricForGeo(metricsData,request);
	}

	private boolean isFisrtVolunteer(Associate v, FetchRequest request) {
		try {
			Date start=new SimpleDateFormat("yyyy-MM-dd").parse(ORAUtil.getDateForPeriod(request.getStartPeriod()));
			Date end=new SimpleDateFormat("yyyy-MM-dd").parse(ORAUtil.getDateForPeriod(request.getEndPeriod()));
			return v.getFirstVolunteerDate().after(start) && v.getFirstVolunteerDate().before(end);
		} catch (ParseException e) {
		}
		return false;
	}

	private List<AcquisitionMetrics> calculateAcquisitionMetricForGeo(List<EventInfo> metricsData, FetchRequest request) {
		List<Associate> associates=metricsData.stream().map(
				m->m.getVolunteers().stream().filter(
						v->isFisrtVolunteer(v,request)).collect(Collectors.toList())).flatMap(Collection::stream).collect(Collectors.toList());
	
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
