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
import com.cts.ora.report.dataload.domain.IncomingFiles;
import com.cts.ora.report.dataload.domain.ReportType;
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
		    em.getTransaction().commit();
			em.close();
			
		}catch(Exception e) {
			logger.error("Error during saveAssociates:"+e.getMessage());
			try {
				em.getTransaction().rollback();
				throw new ORAException("DATA_SAVE_FAILURE", "Associate Data not saved", e);
			} catch (Exception e1) {
				logger.error("Error during rollback");
			}finally {
				if(em!=null){
					em.close();
				}
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Associate> getAllAssociates() {
		logger.info("Into geAllAssociates");
		List<Associate> associates = null;
		
		 try {
			em = emf.createEntityManager();
			 //associates =   em.createQuery("SELECT a FROM com.cts.ora.report.dataload.domain.Associate a JOIN com.cts.ora.report.dataload.domain.BusinessUnit b ON a.bu=b.buId", Associate.class).getResultList();
				
			 String sql = "select a.ASC_NAME,a.asc_id,a.created_date,a.designation,a.is_poc,a.is_volunteer,a.bu_id AS asc_bId  from ora_outreach_associate a inner join ora_ref_assc_bu b on a.bu_id=b.bu_id";
			 associates =   em.createNativeQuery(sql,"AssociateMapping").getResultList();

			 //associates = ascRepo.findAllAssociates();
			/*CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Associate> cq = cb.createQuery(Associate.class);
			Root<Associate> asc = cq.from(Associate.class);
			cq.select(asc);
			TypedQuery<Associate> q = em.createQuery(cq);
			associates = q.getResultList();*/
			
			 em.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ORAException("0001","Error during getAllAssociates",e);
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of geAllAssociates");
		return associates;
	}
	
	@Override
	public List<BusinessUnit> getAllBusinessUnits() {
		logger.info("Into geAllBusinessUnits");
		List<BusinessUnit> buList;
		try {
			buList = null;
			em = emf.createEntityManager();
			// buList = em.createNativeQuery("select * from ora_ref_assc_bu",BusinessUnit.class).getResultList();
			buList = em.createQuery("SELECT b FROM BusinessUnit b", BusinessUnit.class).getResultList();
		} catch (Exception e) {
			logger.error("Error in getAllBusinessUnits:"+e.getMessage());
			throw new ORAException();
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of geAllBusinessUnits");
		return buList;
	}
	
	@Override
	public ReportType getReportTypeByName(String name) {
		logger.info("Into getReportTypeByName");
		ReportType reportType=null;
		
		try {
			em = emf.createEntityManager();
			// buList = em.createNativeQuery("select * from ora_ref_assc_bu",BusinessUnit.class).getResultList();
			reportType = em.createQuery("SELECT r FROM ReportType r WHERE r.reportType = :repType", ReportType.class)
							.setParameter("repType", name).getSingleResult();
		} catch (Exception e) {
			logger.error("Error in getReportTypeByName:"+e.getMessage());
			throw new ORAException();
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of getReportTypeByName");
		return reportType;
	}


	@Override
	public List<IncomingFiles> createIncomingFiles(List<IncomingFiles> incomingFiles) {
		logger.info("Into updateIncomingFiles:"+incomingFiles);
		
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			for(IncomingFiles file:incomingFiles){
				em.persist(file);
				em.refresh(file);
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			logger.error("Error in updateIncomingFiles:"+e.getMessage());
			throw new ORAException("INCOMING_FILE_SAVE_FAILURE", "Incoming Files not saved", e);
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of updateIncomingFiles");
		return incomingFiles;
	}
	
	@Override
	public void updateIncomingFileStatus(Long fileId, String status) {
		logger.info("Into updateIncomingFileStatus");
		
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			IncomingFiles file = em.find(IncomingFiles.class, new Long(fileId));
			file.setStatus(status);
			em.merge(file);
			em.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in updateIncomingFileStatus:"+e);
			throw new ORAException("INCOMING_FILE_STATUS_FAILURE", "Incoming Files status update failure", e);
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of updateIncomingFileStatus");
	}

}
