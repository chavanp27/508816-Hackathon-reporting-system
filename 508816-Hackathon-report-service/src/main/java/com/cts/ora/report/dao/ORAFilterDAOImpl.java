package com.cts.ora.report.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cts.ora.report.common.vo.ORARequest;
import com.cts.ora.report.domain.model.Associate;
import com.cts.ora.report.domain.model.BusinessUnit;
import com.cts.ora.report.domain.model.City;
import com.cts.ora.report.domain.model.Country;
import com.cts.ora.report.domain.model.EventCategory;
import com.cts.ora.report.domain.model.IncomingFile;
import com.cts.ora.report.domain.model.PinCode;
import com.cts.ora.report.domain.model.Project;
import com.cts.ora.report.domain.model.ResidenceArea;
import com.cts.ora.report.domain.model.State;
import com.cts.ora.report.domain.model.UserDetail;
import com.cts.ora.report.vo.UserConfig;

@Component
public class ORAFilterDAOImpl implements ORAFilterDAO {
	
	Logger logger = LoggerFactory.getLogger(ORAFilterDAOImpl.class);
	
	@PersistenceUnit
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	@Override
	public UserConfig getUserConfig(ORARequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDetail> getAllAssociates() {
		logger.info("Into getAllAssociates::");
		UserDetail udtl=null;
		List<UserDetail> userDetails = null;
		
		String sql = "SELECT a.asc_id,a.asc_name, a.is_poc, "
				+ "case when u.role_id=1 then 1 else 0 end AS is_admin, "
				+ "case wh"
				+ "en u.role_id=3 then 1 else 0 end AS is_pmo,"
				+ "case when rd.role_name is null AND a.is_poc=1 then 'Event POC' else rd.role_name end as role_name "
				+ "FROM ora_outreach_associate a "
				+ "left join ora_ui_user u ON a.asc_id=u.emp_id AND u.status='ACTIVE' "
				+ "left join ora_ui_role_def rd ON rd.role_id=u.role_id AND u.role_id IN (2,3) "
				+ "WHERE a.is_volunteer=1";
		try {
			em = emf.createEntityManager();
			userDetails =  em.createNativeQuery(sql,"UserDetailsMapping")
								.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getAllAssociates:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of getAllAssociates");
		return userDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Associate> getAssociateById(List<Long> ascIdLst) {
		logger.info("Into getAssociateById::");
		List<Associate> associates=null;
		try {
			em = emf.createEntityManager();
			String sql = "SELECT a.ASC_NAME,a.asc_id,a.created_date,a.designation,"
								+ "a.is_poc,a.is_volunteer,a.bu_id AS asc_bId, a.first_volunteer_date  "
								+ "FROM ora_outreach_associate a inner join ora_ref_assc_bu b on a.bu_id=b.bu_id "
								+ "WHERE a.asc_id IN (:ascIdLst)";
			associates =   em.createNativeQuery(sql,"AssociateMapping")
								.setParameter("ascIdLst", ascIdLst).getResultList();
			
		} catch (Exception e) {
			logger.error("Error in getAssociateById:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of getAssociateById");
		return associates;
	}

	@Override
	public List<BusinessUnit> getAllBusinessUnits() {
		logger.info("Into geAllBusinessUnits");
		List<BusinessUnit> buList;
		try {
			buList = null;
			em = emf.createEntityManager();
			buList = em.createQuery("SELECT b FROM BusinessUnit b", BusinessUnit.class).getResultList();
		} catch (Exception e) {
			logger.error("Error in getAllBusinessUnits:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of geAllBusinessUnits");
		return buList;
	}

	@Override
	public List<Project> getAllProjects() {
		logger.info("Into getAllProjects");
		List<Project> projectLst=null;
		try {
			em = emf.createEntityManager();
			projectLst = em.createQuery("SELECT p FROM Project p WHERE p.status='A'", Project.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getAllProjects:"+e);
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of getAllProjects");
		return projectLst;
	}
	
	

	@Override
	public List<EventCategory> getCategoriesByProject(List<Integer> projectIds) {
		logger.info("Into getAllEventCategories");
		List<EventCategory> eventCategoryLst=null;
		try {
			em = emf.createEntityManager();
			if(projectIds!=null && projectIds.size()==1 && projectIds.get(0)==-1){
				//Get All
				eventCategoryLst = em.createQuery("SELECT e FROM EventCategory e, Project p "
													+ "WHERE e.project=p AND p.status='A'", EventCategory.class)
									 .setParameter("projectIds", projectIds).getResultList();
			}else{
				//Get by Id
				eventCategoryLst = em.createQuery("SELECT e FROM EventCategory e, Project p "
										+ "WHERE p.proj_id IN (:projectIds) AND e.project=p AND p.status='A'"
							, EventCategory.class).setParameter("projectIds", projectIds).getResultList();
			}
			
		} catch (Exception e) {
			logger.error("Error in getAllEventCategories:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of getAllEventCategories");
		return eventCategoryLst;
	}

	@Override
	public List<Country> getCountryById(List<Integer> cntryId) {
		logger.info("Into getCountryById:: "+cntryId);
		List<Country> countryLst=null;
		try {
			em = emf.createEntityManager();
			if(cntryId!=null && cntryId.size()==1 && cntryId.get(0)==-1){
				//Get All
				countryLst = em.createQuery("SELECT c FROM Country c", Country.class).getResultList();
			}else{
				//Get by Id
				countryLst = em.createQuery("SELECT c FROM Country c WHERE c.cntryId IN (:cntryId)", Country.class)
							.setParameter("cntryId", cntryId).getResultList();
			}
			
		} catch (Exception e) {
			logger.error("Error in getCountryById:"+e.getMessage());
			//throw new ORAException("FETCH_PROJECT_FAILURE", "Save geogrpahy data failure", e);
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of getCountryById");
		return countryLst;
	}

	@Override
	public List<State> getStateById(List<Integer> cntryIds) {
		logger.info("Into getStateById:: "+cntryIds);
		List<State> stateLst=null;
		try {
			em = emf.createEntityManager();
			if(cntryIds!=null && cntryIds.size()==1 && cntryIds.get(0)==-1){
				//Get All
				stateLst = em.createQuery("SELECT s FROM State s", State.class).getResultList();
			}else{
				//Get by Id
				stateLst = em.createQuery("SELECT s FROM State s, Country c WHERE c.cntryId IN (:cntryIds) AND s.country=c", State.class)
							.setParameter("cntryIds", cntryIds).getResultList();
			}
		} catch (Exception e) {
			logger.error("Error in getStateById:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of getStateById");
		return stateLst;
	}

	@Override
	public List<City> getCityById(List<Integer> stateIds) {
		logger.info("Into getCityById:: "+stateIds);
		List<City> stateLst=null;
		try {
			em = emf.createEntityManager();
			if(stateIds!=null && stateIds.size()==1 && stateIds.get(0)==-1){
				//Get All
				stateLst = em.createQuery("SELECT c FROM City c", City.class).getResultList();
			}else{
				//Get by Id
				stateLst = em.createQuery("SELECT c FROM State s, City c WHERE s.stateId IN (:stateIds) AND c.state=s", City.class)
							.setParameter("stateIds", stateIds).getResultList();
			}
		} catch (Exception e) {
			logger.error("Error in getCityById:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of getCityById");
		return stateLst;
	}

	@Override
	public List<ResidenceArea> getAreaById(List<Integer> cityIds) {
		logger.info("Into getAreaById:: "+cityIds);
		List<ResidenceArea> areaLst=null;
		try {
			em = emf.createEntityManager();
			if(cityIds!=null && cityIds.size()==1 && cityIds.get(0)==-1){
				//Get All
				areaLst = em.createQuery("SELECT r FROM ResidenceArea r", ResidenceArea.class).getResultList();
			}else{
				//Get by Id
				areaLst = em.createQuery("SELECT r FROM ResidenceArea r, City c WHERE c.cityId IN (:cityIds) AND r.city=c", ResidenceArea.class)
							.setParameter("cityIds", cityIds).getResultList();
			}
		} catch (Exception e) {
			logger.error("Error in getAreaById:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of getAreaById");
		return areaLst;
	}

	@Override
	public List<PinCode> getPincodeById(List<Integer> areaIds) {
		logger.info("Into getPincodeById:: "+areaIds);
		List<PinCode> pinCodeaLst=null;
		try {
			em = emf.createEntityManager();
			if(areaIds!=null && areaIds.size()==1 && areaIds.get(0)==-1){
				//Get All
				pinCodeaLst = em.createQuery("SELECT p FROM PinCode p", PinCode.class).getResultList();
			}else{
				//Get by Id
				pinCodeaLst = em.createQuery("SELECT p FROM ResidenceArea r, PinCode p WHERE r.areaId IN (:areaIds) AND p.area=r", PinCode.class)
							.setParameter("areaIds", areaIds).getResultList();
			}
		} catch (Exception e) {
			logger.error("Error in getPincodeById:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of getPincodeById");
		return pinCodeaLst;
	}

	@Override
	public String getInboundFileLocation(Long fileId) {
		logger.info("Into geAllBusinessUnits");
		String fileLocation=null;
		try {
			em = emf.createEntityManager();
			fileLocation = em.createQuery("SELECT i.fileLoc FROM IncomingFile i WHERE i.inboundId=:fileId", String.class)
								.setParameter("fileId", fileId).getSingleResult();
		} catch (Exception e) {
			logger.error("Error in getAllBusinessUnits:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of geAllBusinessUnits");
		return fileLocation;
	}

	@Override
	public List<IncomingFile> getDataLoadHistory() {
		logger.info("Into getDataLoadHistory");
		List<IncomingFile> dataLoadList=null;
		try {
			em = emf.createEntityManager();
			dataLoadList = em.createQuery("SELECT f FROM IncomingFile f", IncomingFile.class).getResultList();
		} catch (Exception e) {
			logger.error("Error in getDataLoadHistory:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of getDataLoadHistory");
		return dataLoadList;
	}

}
