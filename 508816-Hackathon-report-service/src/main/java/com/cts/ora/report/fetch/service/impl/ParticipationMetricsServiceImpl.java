package com.cts.ora.report.fetch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.ora.report.domain.model.BuMetrics;
import com.cts.ora.report.domain.model.GeoMetrics;
import com.cts.ora.report.fetch.repository.BuMetricsRepository;
import com.cts.ora.report.fetch.repository.GeoMetricsRepository;
import com.cts.ora.report.fetch.repository.LocationRepository;
import com.cts.ora.report.fetch.service.LocationService;
import com.cts.ora.report.fetch.service.ParticipationMetricsService;
import com.cts.ora.report.fetch.vo.FetchRequest;
import com.cts.ora.report.fetch.vo.ParticipationMetrics;
@Service
public class ParticipationMetricsServiceImpl implements ParticipationMetricsService {

	@Autowired
	GeoMetricsRepository geoMetricsRepository;
	
	@Autowired
	BuMetricsRepository buMetricsRepository;
	
	@Autowired
	LocationService locationService;
	
	@Override
	public List<ParticipationMetrics>  getGeographyMetrics(FetchRequest request) {
		List<ParticipationMetrics> metrics=null;
		List<GeoMetrics> metricsData=null;
		if(ServiceHelper.isRequestForAllGeo(request)) {
			metricsData=geoMetricsRepository.getGeoMetricsForPeriod(request.getStartPeriod(), request.getEndPeriod());
		}else {
			metricsData=geoMetricsRepository.getGeoMetricsForPeriodLocation(request.getStartPeriod(), request.getEndPeriod(), locationService.getLocationIds(request));
		}
		metrics=calculateParticipationMetricsForGeo(metricsData);
		return metrics;
	}

	
	private List<ParticipationMetrics> calculateParticipationMetricsForGeo(List<GeoMetrics> metricsData) {
		List<ParticipationMetrics> pm=new ArrayList<>();
		for (GeoMetrics gm : metricsData) {
			ParticipationMetrics m=new ParticipationMetrics();
			m.setLocation(gm.getLocId().getLocId());
			m.setPeriod(gm.getPeriod());
			m.setHeadCount(gm.getHeadCount());
			m.setUniqueVolunteers(gm.getUniqueVolunteers());
			m.setTotalEvents(gm.getTotalEvents());
			m.setTotalHours(Integer.sum(gm.getVolunteerHours(), gm.getTravelHours()));
			m.setCoverage(Integer.divideUnsigned(gm.getUniqueVolunteers(), gm.getHeadCount()));
			m.setAvgHoursPerAssc(Integer.divideUnsigned(gm.getVolunteerHours(), gm.getHeadCount()));
			m.setAvgHoursPerVolunteer(Integer.divideUnsigned(gm.getVolunteerHours(),gm.getUniqueVolunteers()));
			m.setAvgHoursPerEvnt(Integer.divideUnsigned(gm.getVolunteerHours(), gm.getTotalEvents()));
			//m.setAvgVolunHrsPerVolunPerEvnt(avgVolunHrsPerVolunPerEvnt);
			
			pm.add(m);
		}
		return pm;
	}

	
	@Override
	public List<ParticipationMetrics>  getBUMetrics(FetchRequest request) {
		List<ParticipationMetrics> metrics=null;
		List<BuMetrics> metricsData=null;
		if(ServiceHelper.isRequestForAllGeo(request)) {
			metricsData=buMetricsRepository.getBuMetricsForPeriod(request.getStartPeriod(), request.getEndPeriod());
		}else {
			metricsData=buMetricsRepository.getBuMetricsForPeriodLocation(request.getStartPeriod(), request.getEndPeriod(), locationService.getLocationIds(request));
		}
		metrics=calculateParticipationMetricsForBu(metricsData);
		return metrics;
	}

	private List<ParticipationMetrics> calculateParticipationMetricsForBu(List<BuMetrics> metricsData) {
		List<ParticipationMetrics> pm=new ArrayList<>();
		for (BuMetrics gm : metricsData) {
			ParticipationMetrics m=new ParticipationMetrics();
			m.setBussinessUnit(gm.getBuId().getName());
			m.setPeriod(gm.getPeriod());
			m.setHeadCount(gm.getHeadCount());
			m.setUniqueVolunteers(gm.getUniqueVolunteers());
			m.setTotalEvents(gm.getTotalEvents());
			m.setTotalHours(Integer.sum(gm.getVolunteerHours(), gm.getTravelHours()));
			m.setCoverage(Integer.divideUnsigned(gm.getUniqueVolunteers(), gm.getHeadCount()));
			m.setAvgHoursPerAssc(Integer.divideUnsigned(gm.getVolunteerHours(), gm.getHeadCount()));
			m.setAvgHoursPerVolunteer(Integer.divideUnsigned(gm.getVolunteerHours(),gm.getUniqueVolunteers()));
			m.setAvgHoursPerEvnt(Integer.divideUnsigned(gm.getVolunteerHours(), gm.getTotalEvents()));
			//m.setAvgVolunHrsPerVolunPerEvnt(avgVolunHrsPerVolunPerEvnt);
			
			pm.add(m);
		}
		return pm;
	}

	@Override
	public List<ParticipationMetrics>  getFocusAreaMetrics(FetchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
