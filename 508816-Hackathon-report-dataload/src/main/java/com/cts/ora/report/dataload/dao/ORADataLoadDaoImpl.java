package com.cts.ora.report.dataload.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cts.ora.report.dataload.domain.Associate;

@Component
public class ORADataLoadDaoImpl implements ORADataLoadDao {
	
	Logger logger = LoggerFactory.getLogger(ORADataLoadDaoImpl.class);
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@PersistenceContext	
	private EntityManager em;
	
	//@Autowired private SessionFactory sessionFactory;
	

	@Override
	public void saveAssociates(List<Associate> associates) {
		logger.info("Into saveAssociates:"+associates);
		
		for(Associate a:associates) {
			em.persist(a);
		}
	    em.getTransaction().commit();
	    
		logger.info("Out of saveAssociates");
		
	}


	@Override
	public void saveEventInfo() {
		logger.info("Into saveEventInfo");
		
		//entityManager.persist(eventInfo);
		
		logger.info("Out of saveEventInfo");
		
	}


	@Override
	public List<Associate> geAllAssociates() {
		logger.info("Into geAlltAssociates");
		List<Associate> associates = null;
		associates = em.createQuery("from ORA_OUTREACH_ASSOCIATE", Associate.class)
					     .setFirstResult(0)
					     .getResultList();
		logger.info("Out of geAlltAssociates");
		return associates;
	}

}
