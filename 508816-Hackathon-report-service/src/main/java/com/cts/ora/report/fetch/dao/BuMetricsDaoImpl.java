package com.cts.ora.report.fetch.dao;

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

import com.cts.ora.report.domain.model.BuMetrics;
import com.cts.ora.report.fetch.vo.ParticipationMetricsDetails;

@Component
public class BuMetricsDaoImpl implements BuMetricsDao {

	Logger logger=LoggerFactory.getLogger(BuMetricsDaoImpl.class);
	@PersistenceUnit
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	@Override
	public List<BuMetrics> getBuMetricsForUser(Integer startPeriod, Integer endPeriod, List<Integer> buIds,
			Long ascId) {
		List<BuMetrics> metricsDate=null;
		try {
			String sql ="select bu_id as id, period,bu_id as bu_id, sum(eventt) as head_count,count(assc) as uni_volunteers,sum(vh) as vol_hours,sum(th) as trav_hours,max(eventt) as total_events,on_weekend as is_weekend " + 
			"from (select E.asc_id as assc,A.bu_id as bu_id,count(E.event_id) as eventt,sum(E.vol_hours) as vh,sum(E.travel_hours) as th,EI.period ,EI.on_weekend " + 
			"from ora_outreach_associate_event_map as E join ora_outreach_associate A on A.asc_id=E.asc_id join ora_outreach_event_info as EI " + 
			"on E.event_id=EI.event_id group by assc, bu_id, period, on_weekend having assc=:ascId" + 
			") as t group by period,bu_id,on_weekend having period between :startPeriod and :endPeriod";
			em = emf.createEntityManager();
			if(!CollectionUtils.isEmpty(buIds)) {
				sql=sql+ " and bu_id in :buIds";
			}
			Query query=em.createNativeQuery(sql, BuMetrics.class).setParameter("startPeriod", startPeriod)
					.setParameter("endPeriod", endPeriod).setParameter("ascId", ascId);
			if(!CollectionUtils.isEmpty(buIds)) {
				query.setParameter("buIds", buIds);
			}
			
			metricsDate=query.getResultList();
		} catch (Exception e) {
			logger.error("Error in getBuMetricsForUser:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		return metricsDate;
	}

	@Override
	public List<BuMetrics> getBuMetrics(Integer startPeriod, Integer endPeriod, List<Integer> buIds) {
		List<BuMetrics> metricsDate=null;
		try {
			String sql ="select bu_id as id, period,bu_id as bu_id, sum(eventt) as head_count,count(assc) as uni_volunteers,sum(vh) as vol_hours,sum(th) as trav_hours,max(eventt) as total_events,on_weekend as is_weekend " + 
					"from (select E.asc_id as assc,A.bu_id as bu_id,count(E.event_id) as eventt,sum(E.vol_hours) as vh,sum(E.travel_hours) as th,EI.period ,EI.on_weekend " + 
					"from ora_outreach_associate_event_map as E join ora_outreach_associate A on A.asc_id=E.asc_id join ora_outreach_event_info as EI " + 
					"on E.event_id=EI.event_id group by assc, bu_id, period, on_weekend " + 
					") as t group by period,bu_id,on_weekend having period between :startPeriod and :endPeriod";
			em = emf.createEntityManager();
			if(!CollectionUtils.isEmpty(buIds)) {
				sql=sql+ " and bu_id in :buIds";
			}
			Query query=em.createNativeQuery(sql, BuMetrics.class).setParameter("startPeriod", startPeriod)
					.setParameter("endPeriod", endPeriod);
			logger.info("BUS ::"+buIds);
			if(!CollectionUtils.isEmpty(buIds)) {
				query.setParameter("buIds", buIds);
			}
			em = emf.createEntityManager();
			metricsDate=query.getResultList();
		} catch (Exception e) {
			logger.error("Error in getBuMetrics:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		return metricsDate;
	}

	public List<ParticipationMetricsDetails> getBuMetricsDetails(Integer startPeriod, Integer endPeriod, List<Integer> buIds,Long ascId){
		List<ParticipationMetricsDetails> metricsDetails=null;
		try {
			String sql ="select E.asc_id as associateId,A.asc_name as associateName,EI.event_name as eventName,EI.event_date as eventDate,"
					+ "AB.name as bussinessUnit,E.vol_hours as volunteerHours,E.travel_hours as travelHours,EI.on_weekend as weekendEvent" + 
					"from ora_outreach_associate_event_map as E join ora_outreach_associate A on A.asc_id=E.asc_id join ora_outreach_assc_bu AB on A.bu_id=AB.bu_id inner join ora_outreach_event_info as EI " + 
					"on E.event_id=EI.event_id " + 
					"where  EI.period between :startPeriod and : endPeriod  ";
			TypedQuery<ParticipationMetricsDetails> query=em.createNamedQuery(sql, ParticipationMetricsDetails.class).setParameter("startPeriod", startPeriod)
					.setParameter("endPeriod", endPeriod);
			if(null!=ascId) {
				sql=sql+"and E.asc_id=:ascId";
				query.setParameter("ascId", ascId);
			}
			if(!CollectionUtils.isEmpty(buIds)) {
				sql=sql+ "and A.bu_ids in :buIds";
				query.setParameter("buIds", buIds);
			}
			em = emf.createEntityManager();
			metricsDetails = query.getResultList();
		} catch (Exception e) {
			logger.error("Error in getGeoMetricsDetails:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		return metricsDetails;
	}
}
