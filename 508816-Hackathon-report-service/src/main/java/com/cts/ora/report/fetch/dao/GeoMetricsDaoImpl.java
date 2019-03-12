package com.cts.ora.report.fetch.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.cts.ora.report.domain.model.GeoMetrics;
import com.cts.ora.report.fetch.vo.ParticipationMetricsDetails;


public class GeoMetricsDaoImpl implements GeoMetricsDao {
	
	Logger logger=LoggerFactory.getLogger(GeoMetricsDaoImpl.class);

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	@Override
	public List<GeoMetrics> getGeoMetricsForUser(Integer startPeriod, Integer endPeriod, List<Integer> locIds,
			Long ascId) {
		List<GeoMetrics> metricsDate=null;
		try {
			String sql ="select period,location as locId, sum(eventt) as headCount,count(assc) as uniqueVolunteers,sum(vh) as volunteerHours,sum(th) as travelHours,eventt as totalEvents,on_weekend as isWeekend" + 
					"from (select E.asc_id as assc,EI.LOC_ID as location,count(E.event_id) as eventt,sum(E.vol_hours) as vh,sum(E.travel_hours) as th,EI.period ,EI.on_weekend " + 
					"from ora_outreach_associate_event_map as E inner join ora_outreach_event_info as EI " + 
					"on E.event_id=EI.event_id group by assc, location, on_weekend having E.asc_id=:ascId " + 
					") as t group by period,location having period between :startPeriod and : endPeriod ";
			TypedQuery<GeoMetrics> query=em.createNamedQuery(sql, GeoMetrics.class).setParameter("startPeriod", startPeriod)
					.setParameter("endPeriod", endPeriod).setParameter("ascId", ascId);
			if(!CollectionUtils.isEmpty(locIds)) {
				sql=sql+ "and location in :locIds";
				query.setParameter("locIds", locIds);
			}
			em = emf.createEntityManager();
			metricsDate=query.getResultList();
		} catch (Exception e) {
			logger.error("Error in getGeoMetricsForUser:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		return metricsDate;
	}

	@Override
	public List<GeoMetrics> getGeoMetrics(Integer startPeriod, Integer endPeriod, List<Integer> locIds) {
		List<GeoMetrics> metricsDate=null;
		try {
			String sql ="select period,location as locId, sum(eventt) as headCount,count(assc) as uniqueVolunteers,sum(vh) as volunteerHours,sum(th) as travelHours,eventt as totalEvents,on_weekend as isWeekend" + 
					"from (select E.asc_id as assc,EI.LOC_ID as location,count(E.event_id) as eventt,sum(E.vol_hours) as vh,sum(E.travel_hours) as th,EI.period ,EI.on_weekend " + 
					"from ora_outreach_associate_event_map as E inner join ora_outreach_event_info as EI " + 
					"on E.event_id=EI.event_id group by assc, location, on_weekend " + 
					") as t group by period,location having period between :startPeriod and : endPeriod ";
			TypedQuery<GeoMetrics> query=em.createNamedQuery(sql, GeoMetrics.class).setParameter("startPeriod", startPeriod)
					.setParameter("endPeriod", endPeriod);
			if(!CollectionUtils.isEmpty(locIds)) {
				sql=sql+ "and location in :locIds";
				query.setParameter("locIds", locIds);
			}
			em = emf.createEntityManager();
			metricsDate=query.getResultList();
		} catch (Exception e) {
			logger.error("Error in getGeoMetricsForUser:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		return metricsDate;
	}
	
	
	public List<ParticipationMetricsDetails> getGeoMetricsDetails(Integer startPeriod, Integer endPeriod, List<Integer> locIds,Long ascId){
		List<ParticipationMetricsDetails> metricsDetails=null;
		try {
			String sql ="select E.asc_id as associateId,A.asc_name as associateName,EI.event_name as eventName,EI.event_date as eventDate,C.cntry_name as country,E.vol_hours as volunteerHours,E.travel_hours as travelHours,EI.on_weekend as weekendEvent" + 
					"from ora_outreach_associate_event_map as E join ora_outreach_associate A on A.asc_id=E.asc_id inner join ora_outreach_event_info as EI " + 
					"on E.event_id=EI.event_id join ora_ref_location as L on L.loc_id=EI.loc_id join ora_ref_geo_country as C on C.cntry_id=L.cntry_id " + 
					"where  period between :startPeriod and : endPeriod  ";
			TypedQuery<ParticipationMetricsDetails> query=em.createNamedQuery(sql, ParticipationMetricsDetails.class).setParameter("startPeriod", startPeriod)
					.setParameter("endPeriod", endPeriod);
			if(null!=ascId) {
				sql=sql+"and E.asc_id=:ascId";
				query.setParameter("ascId", ascId);
			}
			if(!CollectionUtils.isEmpty(locIds)) {
				sql=sql+ "and location in :locIds";
				query.setParameter("locIds", locIds);
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
