package com.cts.ora.report.fetch.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.cts.ora.report.domain.model.GeoMetrics;

public class GeoMetricsDaoImpl implements GeoMetricsDao {

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	@Override
	public List<GeoMetrics> getGeoMetricsForUser(Integer startPeriod, Integer endPeriod, List<Integer> locIds,
			Long ascId) {
		String sql ="";
		return null;
	}

}
