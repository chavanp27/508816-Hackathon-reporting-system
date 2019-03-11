package com.cts.ora.report.fetch.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.DateTimeParser;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cts.ora.report.common.util.ORAUtil;
import com.cts.ora.report.domain.model.Associate;
import com.cts.ora.report.domain.model.EventInfo;
import com.cts.ora.report.domain.model.Location;
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
		List<EventInfo> prevMetricData=null;
		if(ServiceHelper.isRequestForAllGeo(request)) {
			metricsData=eventInfoRepository.getEventsByPeriod(request.getStartPeriod(), request.getEndPeriod());
			prevMetricData=eventInfoRepository.getEventsByPeriod(ORAUtil.decrementPeriod(request.getStartPeriod()), request.getStartPeriod());
		}else {
			metricsData=eventInfoRepository.getEventsByPeriodLocation(request.getStartPeriod(), request.getEndPeriod(), locationService.getLocationIds(request));
			prevMetricData=eventInfoRepository.getEventsByPeriodLocation(ORAUtil.decrementPeriod(request.getStartPeriod()), request.getStartPeriod(), locationService.getLocationIds(request));
			}
		return calculateAcquisitionMetricForGeo(prevMetricData,metricsData,request);
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

	private List<AcquisitionMetrics> calculateAcquisitionMetricForGeo(List<EventInfo> prevMetricData, List<EventInfo> metricsData, FetchRequest request) {
		List<AcquisitionMetrics> am=new ArrayList<>();
		metricsData.forEach(d->d.setVolunteers(d.getVolunteers().stream().filter(v->isFisrtVolunteer(v,request)).collect(Collectors.toSet())));
		Map<Pair<Integer, Location>,List<EventInfo>> prevEventMap=prevMetricData.stream().collect(Collectors.groupingBy(e->{return new Pair<>(e.getPeriod(),e.getLocation());}));
		Map<Pair<Integer, Location>,List<EventInfo>> eventMap=metricsData.stream().collect(Collectors.groupingBy(e->{return new Pair<>(e.getPeriod(),e.getLocation());}));
		for (Pair<Integer, Location> key : eventMap.keySet()) {
			List<Associate> prevassc=null;
			List<Associate> assc=eventMap.get(key).stream().map(e->e.getVolunteers()).flatMap(Collection :: stream).collect(Collectors.toList());
			AcquisitionMetrics m=new AcquisitionMetrics();
			m.setLocation(key.getValue1());
			m.setPeriod(key.getValue0());
			m.setNoNewVolunteer(assc.size());
			Pair<Integer, Location> prev=new Pair<Integer, Location>(ORAUtil.decrementPeriod(key.getValue0()), key.getValue1());
			if(eventMap.containsKey(prev)) {
				prevassc=eventMap.get(prev).stream().map(e->e.getVolunteers()).flatMap(Collection :: stream).collect(Collectors.toList());
				prevassc.retainAll(assc);
			}else {
				prevassc=prevEventMap.get(prev).stream().map(e->e.getVolunteers()).flatMap(Collection :: stream).collect(Collectors.toList());
				prevassc.retainAll(assc);
			}
			m.setPercenatgeVolunAsLast((prevassc.size()/assc.size())*100);
			am.add(m);
			
		}
	
		return am;
	}

	@Override
	public List<AcquisitionMetrics> getBUMetrics(FetchRequest request) {
		List<EventInfo> metricsData=eventInfoRepository.getEventsByPeriod(request.getStartPeriod(), request.getEndPeriod());
		List<EventInfo> prevMetricData=eventInfoRepository.getEventsByPeriod(ORAUtil.decrementPeriod(request.getStartPeriod()), request.getStartPeriod());
		return calculateAcquisitionMetricForBu(prevMetricData,metricsData,request);
	}

	private List<AcquisitionMetrics> calculateAcquisitionMetricForBu(List<EventInfo> prevMetricData,
			List<EventInfo> metricsData, FetchRequest request) {
		metricsData.forEach(d->d.setVolunteers(d.getVolunteers().stream().filter(v->isFisrtVolunteer(v,request)&& request.getBu().contains(v.getBu().getName())).collect(Collectors.toSet())));
		Map<Integer, List<EventInfo>> eventMap=metricsData.stream().collect(Collectors.groupingBy(EventInfo :: getPeriod));
		Map<Integer,Map<String,List<EventInfo>>> buMap=getBUMap(eventMap);
		for (Integer key : eventMap.keySet()) {
			List<Associate> prevassc=null;
			List<Associate> assc=eventMap.get(key).stream().map(e->e.getVolunteers()).flatMap(Collection :: stream).collect(Collectors.toList());
			
		}
		return null;
	}

	private Map<Integer, Map<String, List<EventInfo>>> getBUMap(Map<Integer, List<EventInfo>> eventMap) {
		String prevBU=null;
		Map<Integer, Map<String, List<EventInfo>>> buMap= new HashMap<>();
		for(Entry<Integer, List<EventInfo>> e:eventMap.entrySet()) {
			Map<String, List<EventInfo>> tMap = new HashMap<>();
			for(EventInfo l: e.getValue()) {
				for(Associate a:l.getVolunteers()) {
					List<EventInfo> lList=tMap.computeIfAbsent(a.getBu().getName(), k -> new ArrayList<>());
					if(null==prevBU || !a.getBu().getName().equals(prevBU)) {
						lList.add(l);
					}
					prevBU=a.getBu().getName();
				}
				prevBU=null;
			}
			buMap.put(e.getKey(),tMap);
		}
		return buMap;
	}

	@Override
	public List<AcquisitionMetrics> getFocusAreaMetrics(FetchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
