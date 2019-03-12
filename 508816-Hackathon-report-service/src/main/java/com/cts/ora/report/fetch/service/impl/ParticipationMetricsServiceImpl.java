package com.cts.ora.report.fetch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.ora.report.common.util.ORAUtil;
import com.cts.ora.report.constants.ORAConstants;
import com.cts.ora.report.domain.model.BuMetrics;
import com.cts.ora.report.domain.model.GeoMetrics;
import com.cts.ora.report.domain.model.ORAUser;
import com.cts.ora.report.fetch.dao.BuMetricsDao;
import com.cts.ora.report.fetch.dao.FocusAreaMetricsDao;
import com.cts.ora.report.fetch.dao.GeoMetricsDao;
import com.cts.ora.report.fetch.repository.BuMetricsRepository;
import com.cts.ora.report.fetch.repository.GeoMetricsRepository;
import com.cts.ora.report.fetch.repository.LocationRepository;
import com.cts.ora.report.fetch.repository.ORAUserRepository;
import com.cts.ora.report.fetch.service.LocationService;
import com.cts.ora.report.fetch.service.ParticipationMetricsService;
import com.cts.ora.report.fetch.vo.FetchRequest;
import com.cts.ora.report.fetch.vo.FocusAreaMetrics;
import com.cts.ora.report.fetch.vo.ParticipationMetrics;
import com.cts.ora.report.fetch.vo.ParticipationMetricsDetails;
@Service
public class ParticipationMetricsServiceImpl implements ParticipationMetricsService {

	@Autowired
	GeoMetricsRepository geoMetricsRepository;
	@Autowired
	GeoMetricsDao geoMerticsDao;
	@Autowired
	BuMetricsRepository buMetricsRepository;
	
	@Autowired
	LocationService locationService;
	
	@Autowired
	ORAUserRepository oRAUserRepository;
	@Autowired
	BuMetricsDao buMetricsDao;
	@Autowired
	FocusAreaMetricsDao focusAreaMetricsDao;
	
	@Override
	public List<ParticipationMetrics>  getGeographyMetrics(FetchRequest request) {
		List<ParticipationMetrics> metrics=null;
		List<GeoMetrics> metricsData=null;
		ServiceHelper.calculateStartPeriod(request);
		ORAUser loggedInUsr=oRAUserRepository.getLoggedInUser(request.getAscId());
		if(!loggedInUsr.getRole().getRoleName().equals("PMO") && !loggedInUsr.getRole().getRoleName().equals("Admin")) {
			if(ServiceHelper.isRequestForAllGeo(request)) {
				metricsData=geoMerticsDao.getGeoMetricsForUser(request.getStartPeriod(), request.getEndPeriod(), new ArrayList<>(), request.getAscId());
			}else {
				metricsData=geoMerticsDao.getGeoMetricsForUser(request.getStartPeriod(), request.getEndPeriod(), locationService.getLocationIds(request), request.getAscId());
			}
		}else {
			if(ServiceHelper.isRequestForAllGeo(request)) {
				metricsData=geoMerticsDao.getGeoMetrics(request.getStartPeriod(), request.getEndPeriod(),new ArrayList<>());
			}else {
				metricsData=geoMerticsDao.getGeoMetrics(request.getStartPeriod(), request.getEndPeriod(), locationService.getLocationIds(request));
			}
		}
		metrics=calculateParticipationMetricsForGeo(metricsData);
		return metrics;
	}

	
	


	private List<ParticipationMetrics> calculateParticipationMetricsForGeo(List<GeoMetrics> metricsData) {
		
		List<ParticipationMetrics> pm=new ArrayList<>();
		for (GeoMetrics gm : metricsData) {
			ParticipationMetrics m=new ParticipationMetrics();
			m.setLocation(gm.getLocId().getLocId());
			m.setPeriod(ORAUtil.getDisplayPeriod(gm.getPeriod().toString()));
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
		ServiceHelper.calculateStartPeriod(request);
		ORAUser loggedInUsr=oRAUserRepository.getLoggedInUser(request.getAscId());
		if(!loggedInUsr.getRole().getRoleName().equals("PMO") && !loggedInUsr.getRole().getRoleName().equals("Admin")) {
			if(ServiceHelper.isRequestForAllGeo(request)) {
				metricsData=buMetricsDao.getBuMetricsForUser(request.getStartPeriod(), request.getEndPeriod(), new ArrayList<>(), request.getAscId());
			}else {
				metricsData=buMetricsDao.getBuMetricsForUser(request.getStartPeriod(), request.getEndPeriod(), locationService.getLocationIds(request), request.getAscId());
			}
		}else {
			if(ServiceHelper.isRequestForAllGeo(request)) {
				metricsData=buMetricsDao.getBuMetrics(request.getStartPeriod(), request.getEndPeriod(),new ArrayList<>());
			}else {
				metricsData=buMetricsDao.getBuMetrics(request.getStartPeriod(), request.getEndPeriod(), locationService.getLocationIds(request));
			}
		}
		metrics=calculateParticipationMetricsForBu(metricsData);
		return metrics;
	}

	private List<ParticipationMetrics> calculateParticipationMetricsForBu(List<BuMetrics> metricsData) {
		List<ParticipationMetrics> pm=new ArrayList<>();
		for (BuMetrics gm : metricsData) {
			ParticipationMetrics m=new ParticipationMetrics();
			m.setBussinessUnit(gm.getBuId().getName());
			m.setPeriod(ORAUtil.getDisplayPeriod(gm.getPeriod().toString()));
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
		List<ParticipationMetrics> metrics=null;
		List<FocusAreaMetrics> metricsData=null;
		ServiceHelper.calculateStartPeriod(request);
		ORAUser loggedInUsr=oRAUserRepository.getLoggedInUser(request.getAscId());
		if(!loggedInUsr.getRole().getRoleName().equals("PMO") && !loggedInUsr.getRole().getRoleName().equals("Admin")) {
			if(ServiceHelper.isRequestForAllGeo(request)) {
				metricsData=focusAreaMetricsDao.getFAMetricsForUser(request.getStartPeriod(), request.getEndPeriod(), new ArrayList<>(), request.getAscId());
			}else {
				metricsData=focusAreaMetricsDao.getFAMetricsForUser(request.getStartPeriod(), request.getEndPeriod(), locationService.getLocationIds(request), request.getAscId());
			}
		}else {
			if(ServiceHelper.isRequestForAllGeo(request)) {
				metricsData=focusAreaMetricsDao.getFAMetrics(request.getStartPeriod(), request.getEndPeriod(),new ArrayList<>());
			}else {
				metricsData=focusAreaMetricsDao.getFAMetrics(request.getStartPeriod(), request.getEndPeriod(), locationService.getLocationIds(request));
			}
		}
		metrics=calculateParticipationMetricsForFA(metricsData);
		return metrics;
	}

	
	private List<ParticipationMetrics> calculateParticipationMetricsForFA(List<FocusAreaMetrics> metricsData) {
		
		List<ParticipationMetrics> pm=new ArrayList<>();
		for (FocusAreaMetrics gm : metricsData) {
			ParticipationMetrics m=new ParticipationMetrics();
			m.setProject(gm.getProject().getTitle());
			m.setCategory(gm.getCategory().getTitle());
			m.setPeriod(ORAUtil.getDisplayPeriod(gm.getPeriod().toString()));
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
	public List<ParticipationMetricsDetails> getGeographyMetricsDetails(FetchRequest request) {
		List<ParticipationMetricsDetails> details=null;
		ORAUser loggedInUsr=oRAUserRepository.getLoggedInUser(request.getAscId());
		if(!loggedInUsr.getRole().getRoleName().equals("PMO") && !loggedInUsr.getRole().getRoleName().equals("Admin")) {
			if(ServiceHelper.isRequestForAllGeo(request)) {
				details=geoMerticsDao.getGeoMetricsDetails(request.getStartPeriod(), request.getEndPeriod(), new ArrayList<>(), request.getAscId());
			}else {
				details=geoMerticsDao.getGeoMetricsDetails(request.getStartPeriod(), request.getEndPeriod(), locationService.getLocationIds(request), request.getAscId());
			}
		}else {
			if(ServiceHelper.isRequestForAllGeo(request)) {
				details=geoMerticsDao.getGeoMetricsDetails(request.getStartPeriod(), request.getEndPeriod(), new ArrayList<>(), null);
			}else {
				details=geoMerticsDao.getGeoMetricsDetails(request.getStartPeriod(), request.getEndPeriod(), locationService.getLocationIds(request), null);
			}
		}
		return details;
	}

}
