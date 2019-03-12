package com.cts.ora.report.fetch.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.cts.ora.report.fetch.vo.FocusAreaMetrics;

public class FocusAreaMetricsDaoImpl implements FocusAreaMetricsDao {

	Logger logger=LoggerFactory.getLogger(FocusAreaMetricsDaoImpl.class);

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	private EntityManager em;
	@Override
	public List<FocusAreaMetrics> getFAMetricsForUser(Integer startPeriod, Integer endPeriod, List<Integer> locIds,
			Long ascId) {
		List<FocusAreaMetrics> metricsDate=null;
		try {
			String sql ="select period,category, project,sum(eventt) as headCount,count(assc) as uniqueVolunteers,sum(vh) as volunteerHours,sum(th) as travelHours,eventt as totalEvents,on_weekend as isWeekend" + 
					"from (select E.asc_id as assc,EC.cat_id as category,EP.proj_id as project,count(E.event_id) as eventt,sum(E.vol_hours) as vh,sum(E.travel_hours) as th,EI.period ,EI.on_weekend " + 
					"from ora_outreach_associate_event_map as E inner join ora_outreach_event_info as EI " + 
					"on E.event_id=EI.event_id inner join ora_outreach_ref_event_cat EC on EI.cat_id= EC.cat_id "
					+ "inner join ora_outreach_ref_project EP on EI.proj_id= EP.proj_id group by assc, category,project, on_weekend having E.asc_id=:ascId " + 
					") as t group by period,category,project having period between :startPeriod and : endPeriod ";
			TypedQuery<FocusAreaMetrics> query=em.createNamedQuery(sql, FocusAreaMetrics.class).setParameter("startPeriod", startPeriod)
					.setParameter("endPeriod", endPeriod).setParameter("ascId", ascId);
			if(!CollectionUtils.isEmpty(locIds)) {
				sql=sql+ "and location in :locIds";
				query.setParameter("locIds", locIds);
			}
			em = emf.createEntityManager();
			metricsDate=query.getResultList();
		} catch (Exception e) {
			logger.error("Error in getFAMetricsForUser:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		return metricsDate;
	}

	@Override
	public List<FocusAreaMetrics> getFAMetrics(Integer startPeriod, Integer endPeriod, List<Integer> locIds) {
		List<FocusAreaMetrics> metricsDate=null;
		try {
			String sql ="select period,category, project,sum(eventt) as headCount,count(assc) as uniqueVolunteers,sum(vh) as volunteerHours,sum(th) as travelHours,eventt as totalEvents,on_weekend as isWeekend" + 
					"from (select E.asc_id as assc,EC.cat_id as category,EP.proj_id as project,count(E.event_id) as eventt,sum(E.vol_hours) as vh,sum(E.travel_hours) as th,EI.period ,EI.on_weekend " + 
					"from ora_outreach_associate_event_map as E inner join ora_outreach_event_info as EI " + 
					"on E.event_id=EI.event_id inner join ora_outreach_ref_event_cat EC on EI.cat_id= EC.cat_id "
					+ "inner join ora_outreach_ref_project EP on EI.proj_id= EP.proj_id group by assc, category,project, on_weekend " + 
					") as t group by period,category,project having period between :startPeriod and : endPeriod ";
			TypedQuery<FocusAreaMetrics> query=em.createNamedQuery(sql, FocusAreaMetrics.class).setParameter("startPeriod", startPeriod)
					.setParameter("endPeriod", endPeriod);
			if(!CollectionUtils.isEmpty(locIds)) {
				sql=sql+ "and location in :locIds";
				query.setParameter("locIds", locIds);
			}
			em = emf.createEntityManager();
			metricsDate=query.getResultList();
		} catch (Exception e) {
			logger.error("Error in getFAMetrics:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		return metricsDate;
	}

}
