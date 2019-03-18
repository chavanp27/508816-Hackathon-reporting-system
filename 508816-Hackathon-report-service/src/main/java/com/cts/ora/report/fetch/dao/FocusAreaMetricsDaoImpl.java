package com.cts.ora.report.fetch.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.cts.ora.report.domain.model.EventCategory;
import com.cts.ora.report.domain.model.Project;
import com.cts.ora.report.fetch.vo.FocusAreaMetrics;

@Component
public class FocusAreaMetricsDaoImpl implements FocusAreaMetricsDao {

	Logger logger=LoggerFactory.getLogger(FocusAreaMetricsDaoImpl.class);

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	private EntityManager em;
	@Override
	public List<FocusAreaMetrics> getFAMetricsForUser(Integer startPeriod, Integer endPeriod, List<Integer> projects,List<Integer> categories,
			Long ascId) {
		List<FocusAreaMetrics> metricsDate=null;
		try {
			String sql ="select period,category, project,sum(eventt) as headCount,count(assc) as uniqueVolunteers,sum(vh) as volunteerHours,sum(th) as travelHours,max(eventt) as totalEvents,on_weekend as isWeekend " + 
					"from (select E.asc_id as assc,EC.cat_id as category,EP.proj_id as project,count(E.event_id) as eventt,sum(E.vol_hours) as vh,sum(E.travel_hours) as th,EI.period ,EI.on_weekend " + 
					"from ora_outreach_associate_event_map as E inner join ora_outreach_event_info as EI " + 
					"on E.event_id=EI.event_id inner join ora_outreach_ref_event_cat EC on EI.cat_id= EC.cat_id "
					+ "inner join ora_outreach_ref_project EP on EI.proj_id= EP.proj_id group by assc,period, category,project, on_weekend having E.asc_id=:ascId " + 
					") as t group by  period,category,project,isWeekend having period between :startPeriod and : endPeriod ";
			if(!CollectionUtils.isEmpty(projects)) {
				sql=sql+ "and project in :projects";
			}
			if(!CollectionUtils.isEmpty(categories)) {
				sql=sql+ "and category in :categories";
			}
			em = emf.createEntityManager();
			Query query=em.createNativeQuery(sql).setParameter("startPeriod", startPeriod)
					.setParameter("endPeriod", endPeriod);
			if(!CollectionUtils.isEmpty(projects)) {
				query.setParameter("projects", projects);
			}
			if(!CollectionUtils.isEmpty(categories)) {
				query.setParameter("categories", categories);
			}
			List<Object[]> data=query.getResultList();
			data.forEach(d->{
				FocusAreaMetrics m=new FocusAreaMetrics();
				m.setPeriod((Integer)d[0]);
				EventCategory cat=new EventCategory();
				cat.setCat_id((Integer)d[1]);
				m.setCategory(cat);
				Project p=new Project();
				p.setProj_id((Integer)d[2]);
				m.setProject(p);
				m.setHeadCount(((BigDecimal)d[3]).intValue());
				m.setUniqueVolunteers(((BigInteger)d[4]).intValue());
				m.setVolunteerHours(((Double)d[5]).intValue());
				m.setTravelHours(((Double)d[6]).intValue());
				m.setTotalEvents(((BigInteger)d[7]).intValue());
				m.setIsWeekend(((Integer)d[8]) == 0?Boolean.FALSE:Boolean.TRUE);
				logger.info("Metrics :: "+m);
				metricsDate.add(m);
			});
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
	public List<FocusAreaMetrics> getFAMetrics(Integer startPeriod, Integer endPeriod, List<Integer> projects,List<Integer> categories) {
		List<FocusAreaMetrics> metricsDate=new ArrayList<>();
		try {
			String sql ="select period,category, project,sum(eventt) as headCount,count(assc) as uniqueVolunteers,sum(vh) as volunteerHours,sum(th) as travelHours,max(eventt) as totalEvents,on_weekend as isWeekend " + 
					"from (select E.asc_id as assc,EC.cat_id as category,EP.proj_id as project,count(E.event_id) as eventt,sum(E.vol_hours) as vh,sum(E.travel_hours) as th,EI.period ,EI.on_weekend " + 
					"from ora_outreach_associate_event_map as E inner join ora_outreach_event_info as EI " + 
					"on E.event_id=EI.event_id inner join ora_outreach_ref_event_cat EC on EI.cat_id= EC.cat_id "
					+ "inner join ora_outreach_ref_projects EP on EI.proj_id= EP.proj_id group by assc,period, category,project, on_weekend " + 
					") as t group by period,category,project,isWeekend having period between :startPeriod and :endPeriod ";
			if(!CollectionUtils.isEmpty(projects)) {
				sql=sql+ "and project in :projects";
			}
			if(!CollectionUtils.isEmpty(categories)) {
				sql=sql+ "and category in :categories";
			}
			em = emf.createEntityManager();
			Query query=em.createNativeQuery(sql).setParameter("startPeriod", startPeriod)
					.setParameter("endPeriod", endPeriod);
			if(!CollectionUtils.isEmpty(projects)) {
				query.setParameter("projects", projects);
			}
			if(!CollectionUtils.isEmpty(categories)) {
				query.setParameter("categories", categories);
			}
			List<Object[]> data=query.getResultList();
			data.forEach(d->{
				FocusAreaMetrics m=new FocusAreaMetrics();
				m.setPeriod((Integer)d[0]);
				EventCategory cat=new EventCategory();
				cat.setCat_id((Integer)d[1]);
				m.setCategory(cat);
				Project p=new Project();
				p.setProj_id((Integer)d[2]);
				m.setProject(p);
				m.setHeadCount(((BigDecimal)d[3]).intValue());
				m.setUniqueVolunteers(((BigInteger)d[4]).intValue());
				m.setVolunteerHours(((Double)d[5]).intValue());
				m.setTravelHours(((Double)d[6]).intValue());
				m.setTotalEvents(((BigInteger)d[7]).intValue());
				m.setIsWeekend(((Integer)d[8]) == 0?Boolean.FALSE:Boolean.TRUE);
				logger.info("Metrics :: "+m);
				metricsDate.add(m);
			});
		} catch (Exception e) {
			logger.error("Error in getFAMetrics:"+e);
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		return metricsDate;
	}

}
