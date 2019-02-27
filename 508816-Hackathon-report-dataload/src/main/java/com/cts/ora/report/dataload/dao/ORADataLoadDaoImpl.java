package com.cts.ora.report.dataload.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cts.ora.report.dataload.domain.Associate;
import com.cts.ora.report.dataload.repository.AssociateRepository;

@Component
public class ORADataLoadDaoImpl implements ORADataLoadDao {
	
	Logger logger = LoggerFactory.getLogger(ORADataLoadDaoImpl.class);
	
	@PersistenceUnit
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	@Autowired
	private AssociateRepository ascRepo;
	
	//@PersistenceContext	
	//private EntityManager em;
	
	//@Autowired private SessionFactory sessionFactory;
	

	@Override
	public void saveAssociates(List<Associate> associates) {
		logger.info("Into saveAssociates:"+associates);
		 em = emf.createEntityManager();
		try {
			
			//utx.begin();
			em.getTransaction().begin();
			for(Associate a:associates) {
				em.persist(a.getBu());
				em.persist(a);
			}
		    //utx.commit();
			em.getTransaction().commit();
			em.close();
			
		}catch(Exception e) {
			try {
				//utx.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	    
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
		logger.info("Into geAllAssociates");
		List<Associate> associates = null;
		 em = emf.createEntityManager();
		associates = (List<Associate>) em.createNativeQuery("select * from ora_outreach_associate").getResultList();
						 /*.createQuery("from ORA_OUTREACH_ASSOCIATE", Associate.class)
					     .setFirstResult(0)
					     .getResultList();*/
		
		//associates = ascRepo.findAllAssociates();
		logger.info("Out of geAllAssociates");
		return associates;
	}

}
