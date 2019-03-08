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
	LocationRepository locationRepository;
	
	@Override
	public List<ParticipationMetrics>  getGeographyMetrics(FetchRequest request) {
		List<ParticipationMetrics> metrics=null;
		List<GeoMetrics> metricsData=null;
		if(isRequestForAllGeo(request)) {
			metricsData=geoMetricsRepository.getGeoMetricsForPeriod(request.getStartPeriod(), request.getEndPeriod());
		}else {
			metricsData=geoMetricsRepository.getGeoMetricsForPeriodLocation(request.getStartPeriod(), request.getEndPeriod(), getLocation(request));
		}
		metrics=calculateParticipationMetricsForGeo(metricsData);
		return metrics;
	}

	private List<Integer> getLocation(FetchRequest request) {
		if(!request.getGeography().getPin().contains(-1)) {
			return locationRepository.getLocationsForPin(request.getGeography().getPin());
		}else if(!request.getGeography().getArea().contains(-1)) {
			return locationRepository.getLocationIdsForArea(request.getGeography().getArea());
		}else if(!request.getGeography().getCity().contains(-1)) {
			return locationRepository.getLocationIdsForCity(request.getGeography().getCity());
		}else if( !request.getGeography().getStates().contains(-1)) {
			return locationRepository.getLocationIdsForState(request.getGeography().getStates());
		}else if(!request.getGeography().getCountries().contains(-1)) {
			return locationRepository.getLocationIdsForCountry(request.getGeography().getCountries());
		}else {
			return locationRepository.getLocationId(request.getGeography().getCountries(), request.getGeography().getStates(), request.getGeography().getCity(), request.getGeography().getArea(),request.getGeography().getPin());
		}
	}

	private List<ParticipationMetrics> calculateParticipationMetricsForGeo(List<GeoMetrics> metricsData) {
		List<ParticipationMetrics> pm=new ArrayList<>();
		for (GeoMetrics gm : metricsData) {
			ParticipationMetrics m=new ParticipationMetrics();
			m.setLocation(gm.getLocId());
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

	private boolean isRequestForAllGeo(FetchRequest request) {
		return request.getGeography().getCountries().contains(-1) && request.getGeography().getStates().contains(-1) 
				&& request.getGeography().getCity().contains(-1) && request.getGeography().getArea().contains(-1);
	}

	@Override
	public List<ParticipationMetrics>  getBUMetrics(FetchRequest request) {
		List<ParticipationMetrics> metrics=null;
		List<BuMetrics> metricsData=null;
		if(isRequestForAllGeo(request)) {
			metricsData=buMetricsRepository.getBuMetricsForPeriod(request.getStartPeriod(), request.getEndPeriod());
		}else {
			metricsData=buMetricsRepository.getBuMetricsForPeriodLocation(request.getStartPeriod(), request.getEndPeriod(), getLocation(request));
		}
		metrics=calculateParticipationMetricsForBu(metricsData);
		return metrics;
	}

	private List<ParticipationMetrics> calculateParticipationMetricsForBu(List<BuMetrics> metricsData) {
		List<ParticipationMetrics> pm=new ArrayList<>();
		for (BuMetrics gm : metricsData) {
			ParticipationMetrics m=new ParticipationMetrics();
			m.setBussinessUnit(gm.getBuId());
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
