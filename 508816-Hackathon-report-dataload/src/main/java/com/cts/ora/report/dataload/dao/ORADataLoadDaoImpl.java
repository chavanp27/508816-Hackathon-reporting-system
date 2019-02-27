package com.cts.ora.report.dataload.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cts.ora.report.dataload.domain.Associate;
import com.cts.ora.report.dataload.domain.BusinessUnit;
import com.cts.ora.report.dataload.repository.AssociateRepository;
import com.cts.ora.report.exception.ORAException;

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
			em.getTransaction().begin();
			List<BusinessUnit> bus = associates.stream().filter(a->!a.isBuExists()).map(a->a.getBu()).distinct().collect(Collectors.toList());
			for(BusinessUnit bu:bus){
				em.persist(bu);
			}
			
			for(Associate a:associates) {
				em.persist(a);
			}
		    //utx.commit();
			em.getTransaction().commit();
			em.close();
			
		}catch(Exception e) {
			try {
				em.getTransaction().rollback();
				throw new ORAException("DATA_SAVE_FAILURE", "Associate Data not saved", e);
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
		associates =  em.createNativeQuery("select * from ora_outreach_associate",Associate.class).getResultList();
						 /*.createQuery("from ORA_OUTREACH_ASSOCIATE", Associate.class)
					     .setFirstResult(0)
					     .getResultList();*/
		
		//associates = ascRepo.findAllAssociates();
		logger.info("Out of geAllAssociates");
		return associates;
	}
	
	@Override
	public List<BusinessUnit> geAllBusinessUnits() {
		logger.info("Into geAllBusinessUnits");
		List<BusinessUnit> buList = null;
		 em = emf.createEntityManager();
		 buList = (List<BusinessUnit>) em.createNativeQuery("select * from ora_ref_assc_bu").getResultList();
		
		//associates = ascRepo.findAllAssociates();
		logger.info("Out of geAllBusinessUnits");
		return buList;
	}

}
