package com.cts.ora.report.fetch.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.ora.report.domain.model.Associate;
import com.cts.ora.report.domain.model.AssociateEventMap;
import com.cts.ora.report.domain.model.BusinessUnit;
import com.cts.ora.report.domain.model.EventInfo;
import com.cts.ora.report.domain.model.Location;
import com.cts.ora.report.domain.model.ORAUser;
import com.cts.ora.report.fetch.repository.BussinessUnitRepository;
import com.cts.ora.report.fetch.repository.EventInfoRepository;
import com.cts.ora.report.fetch.repository.EventMapRepository;
import com.cts.ora.report.fetch.repository.ORAUserRepository;
import com.cts.ora.report.fetch.service.EngagementMetricsService;
import com.cts.ora.report.fetch.service.LocationService;
import com.cts.ora.report.fetch.vo.EngagementMetrics;
import com.cts.ora.report.fetch.vo.FetchRequest;

@Service
public class EngagementMetricsServiceImpl implements EngagementMetricsService {

	static Logger logger = LoggerFactory.getLogger(EngagementMetricsServiceImpl.class);
	@Autowired
	EventInfoRepository eventInfoRepository;
	
	@Autowired
	LocationService locationService;
	
	@Autowired
	ORAUserRepository oRAUserRepository;
	
	@Autowired
	EventMapRepository eventMapRepository;
	
	@Autowired
	BussinessUnitRepository bussinessUnitRepository;
	@Override
	public List<EngagementMetrics> getGeographyMetrics(FetchRequest request) {
		List<EngagementMetrics> metrics=null;
		List<AssociateEventMap> metricsData=null;
		ServiceHelper.calculateStartPeriod(request);
		if(ServiceHelper.isRequestForAllGeo(request)) {
			metricsData=eventMapRepository.getAsscEventForPeriod(request.getStartPeriod(), request.getEndPeriod());
		}else {
			metricsData=eventMapRepository.getAsscEventForPeriodLoc(request.getStartPeriod(), request.getEndPeriod(), locationService.getLocationIds(request));
			
		}
		ORAUser loggedInUsr=oRAUserRepository.getLoggedInUser(request.getAscId());
		if(!loggedInUsr.getRole().getRoleName().equals("PMO") && !loggedInUsr.getRole().getRoleName().equals("Admin")) {
			List<String> events=eventMapRepository.getEventsForUser(request.getAscId());
			metricsData=metricsData.stream().filter(m->events.contains(m.getEvent().getEventId())).collect(Collectors.toList());
		}
		metrics=calculateMetricsForGeography(metricsData);
		return metrics;
	}
	
	private List<EngagementMetrics> calculateMetricsForGeography(List<AssociateEventMap> metricsData) {
		Map<Pair<Integer, Location>,List<AssociateEventMap>> eventMap=metricsData.stream().collect(Collectors.groupingBy(e->{return new Pair<Integer,Location>(e.getEvent().getPeriod(),e.getEvent().getLocation());}));
		List<EngagementMetrics> el=new ArrayList<>();
		for (Pair<Integer, Location> key : eventMap.keySet()) {
			List<Associate> asscList=eventMap.get(key).stream().map(e->e.getAsc()).collect(Collectors.toList());
			logger.info("Associate List :"+asscList);
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
			m.setPeriod(key.getValue0());
			m.setLocation(key.getValue1());
			m.setOneTimersCount(oneTimers);
			m.setTwoToFiveTimersCount(twoToFiveTimers);
			m.setFivePlusTimersCount(fivePlusTimers);
			
			el.add(m);
		}
		
		return el;
	}

	
	@Override
	public List<EngagementMetrics> getBUMetrics(FetchRequest request) {
		List<AssociateEventMap> metricsData=null;
		ServiceHelper.calculateStartPeriod(request);
		if(ServiceHelper.isRequestForAllBU(request)) {
			metricsData=eventMapRepository.getAsscEventForPeriod(request.getStartPeriod(), request.getEndPeriod());
		}else {
			metricsData=eventMapRepository.getAsscEventForPeriodLoc(request.getStartPeriod(), request.getEndPeriod(), bussinessUnitRepository.findByName(request.getBu()));
			
		}
		ORAUser loggedInUsr=oRAUserRepository.getLoggedInUser(request.getAscId());
		if(!loggedInUsr.getRole().getRoleName().equals("PMO") && !loggedInUsr.getRole().getRoleName().equals("Admin")) {
			List<String> events=eventMapRepository.getEventsForUser(request.getAscId());
			metricsData=metricsData.stream().filter(m->events.contains(m.getEvent().getEventId())).collect(Collectors.toList());
		}
		List<EngagementMetrics> metrics=calculateMetricsForBU(metricsData);
		return metrics;
	}

	private List<EngagementMetrics> calculateMetricsForBU(List<AssociateEventMap> metricsData) {
		Map<Pair<Integer, BusinessUnit>,List<AssociateEventMap>> eventMap=metricsData.stream().collect(Collectors.groupingBy(e->{return new Pair<Integer,BusinessUnit>(e.getEvent().getPeriod(),e.getAsc().getBu());}));
		List<EngagementMetrics> el=new ArrayList<>();
		for (Pair<Integer, BusinessUnit> key : eventMap.keySet()) {
			List<Associate> asscList=eventMap.get(key).stream().map(e->e.getAsc()).collect(Collectors.toList());
			logger.info("Associate List :"+asscList);
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
			m.setPeriod(key.getValue0());
			m.setBussinessUnit(key.getValue1());
			m.setOneTimersCount(oneTimers);
			m.setTwoToFiveTimersCount(twoToFiveTimers);
			m.setFivePlusTimersCount(fivePlusTimers);
			
			el.add(m);
		}
		return el;
	}

	@Override
	public List<EngagementMetrics> getFocusAreaMetrics(FetchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
