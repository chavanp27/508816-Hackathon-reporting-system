package com.cts.ora.report.fetch.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.cts.ora.report.domain.model.BuMetrics;

public class BuMetricsDaoImpl implements BuMetricsDao {

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	@Override
	public List<BuMetrics> getBuMetricsForUser(Integer startPeriod, Integer endPeriod, List<Integer> locIds,
			Long ascId) {
		String sql ="";
		return null;
	}

}
