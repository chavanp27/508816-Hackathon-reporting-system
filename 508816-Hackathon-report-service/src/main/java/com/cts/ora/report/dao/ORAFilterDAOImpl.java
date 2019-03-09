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
import com.cts.ora.report.domain.model.Location;
import com.cts.ora.report.domain.model.PinCode;
import com.cts.ora.report.domain.model.Project;
import com.cts.ora.report.domain.model.ResidenceArea;
import com.cts.ora.report.domain.model.State;
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

	@Override
	public List<Associate> getAllAssociates() {
		// TODO Auto-generated method stub
		return null;
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
			projectLst = em.createQuery("SELECT p FROM Project p", Project.class).getResultList();
			/*if(projectLst!=null){
				projectLst.stream().forEach(p->{
					logger.info("catgories::"+p.getCategories());
					em.refresh(p.getCategories());
				});
			}*/
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
			eventCategoryLst = em.createQuery("SELECT e FROM EventCategory e", EventCategory.class).getResultList();
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
	public List<Country> getCountryById(List<Long> cntryId) {
		logger.info("Into getCountryById:: "+cntryId);
		List<Country> countryLst=null;
		try {
			em = emf.createEntityManager();
			if(cntryId!=null && cntryId.size()==1 && cntryId.get(0)==-1){
				//Get All
				countryLst = em.createQuery("SELECT c FROM Country c", Country.class).getResultList();
			}else{
				//Get by Id
				countryLst = em.createQuery("SELECT c FROM Country c WHERE c.locId = :cntryId", Country.class)
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
	public List<State> getStateById(List<Long> stateId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> getCityById(List<Long> cityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResidenceArea> getAreaById(List<Long> areaId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PinCode> getPincodeById(List<String> pinNums) {
		logger.info("Into getLocationBasedOnPinCode:: "+pinNums);
		Location location=null;
		try {
			em = emf.createEntityManager();
			if(pinNums!=null){
				location = em.createQuery("SELECT l FROM Location l, PinCode p WHERE l.codeId=p.codeId AND p.name = :pinNum", Location.class)
							.setParameter("pinNum", pinNums).getSingleResult();
			}
			
		} catch (Exception e) {
			logger.error("Error in getLocationBasedOnPinCode:"+e.getMessage());
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of getLocationBasedOnPinCode");
		return null;
	}

	@Override
	public List<IncomingFile> fetchDataLoadLog() {
		// TODO Auto-generated method stub
		return null;
	}

}
