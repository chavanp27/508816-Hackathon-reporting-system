package com.cts.ora.report.fetch.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.ora.report.domain.model.Associate;
import com.cts.ora.report.domain.model.EventInfo;
import com.cts.ora.report.fetch.repository.EventInfoRepository;
import com.cts.ora.report.fetch.repository.LocationRepository;
import com.cts.ora.report.fetch.service.EngagementMetricsService;
import com.cts.ora.report.fetch.vo.EngagementMetrics;
import com.cts.ora.report.fetch.vo.FetchRequest;

@Service
public class EngagementMetricsServiceImpl implements EngagementMetricsService {

	@Autowired
	EventInfoRepository eventInfoRepository;
	
	@Autowired
	LocationRepository locationRepository;
	
	@Override
	public List<EngagementMetrics> getGeographyMetrics(FetchRequest request) {
		List<EngagementMetrics> metrics=null;
		List<EventInfo> metricsData=null;
		if(isRequestForAllGeo(request)) {
			metricsData=eventInfoRepository.getEventsByPeriod(request.getStartPeriod(), request.getEndPeriod());
		}else {
			metricsData=eventInfoRepository.getEventsByPeriodLocation(request.getStartPeriod(), request.getEndPeriod(), getLocation(request));
		}
		metrics=calculateMetricsForGeography(metricsData);
		return metrics;
	}
	
	private List<EngagementMetrics> calculateMetricsForGeography(List<EventInfo> metricsData) {
		List<Associate> asscList=metricsData.stream().map(m->m.getVolunteers()).flatMap(Collection::stream).collect(Collectors.toList());
		int oneTimers=0;
		int twoToFiveTimers=0;
		int fivePlusTimers=0;
		for (Associate a : asscList) {
			if(Collections.frequency(asscList, a) == 1)	{
				oneTimers++;
			}else if(Collections.frequency(asscList, a) > 1 && Collections.frequency(asscList, a) < 6) {
				twoToFiveTimers++;
			}else {
				fivePlusTimers++;
			}
		}
		EngagementMetrics m=new EngagementMetrics();
		m.setOneTimersCount(oneTimers);
		m.setTwoToFiveTimersCount(twoToFiveTimers);
		m.setFivePlusTimersCount(fivePlusTimers);
		List<EngagementMetrics> el=new ArrayList<>();
		el.add(m);
		return el;
	}

	private boolean isRequestForAllGeo(FetchRequest request) {
		return request.getGeography().getCountries().contains(-1) && request.getGeography().getStates().contains(-1) 
				&& request.getGeography().getCity().contains(-1) && request.getGeography().getArea().contains(-1);
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
	@Override
	public List<EngagementMetrics> getBUMetrics(FetchRequest request) {
		List<EventInfo> metricsData=eventInfoRepository.getEventsByPeriod(request.getStartPeriod(), request.getEndPeriod());
		List<EngagementMetrics> metrics=calculateMetricsForBU(metricsData,request.getBu());
		return metrics;
	}

	private List<EngagementMetrics> calculateMetricsForBU(List<EventInfo> metricsData, List<String> list) {
		List<Associate> asscList=metricsData.stream().map(m->m.getVolunteers()).flatMap(Collection::stream).filter(a->list.contains(a.getBu().getName())).collect(Collectors.toList());
		int oneTimers=0;
		int twoToFiveTimers=0;
		int fivePlusTimers=0;
		for (Associate a : asscList) {
			if(Collections.frequency(asscList, a) == 1)	{
				oneTimers++;
			}else if(Collections.frequency(asscList, a) > 1 && Collections.frequency(asscList, a) < 6) {
				twoToFiveTimers++;
			}else {
				fivePlusTimers++;
			}
		}
		EngagementMetrics m=new EngagementMetrics();
		m.setOneTimersCount(oneTimers);
		m.setTwoToFiveTimersCount(twoToFiveTimers);
		m.setFivePlusTimersCount(fivePlusTimers);
		List<EngagementMetrics> el=new ArrayList<>();
		el.add(m);
		return el;
	}

	@Override
	public List<EngagementMetrics> getFocusAreaMetrics(FetchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
