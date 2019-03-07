package com.cts.ora.report.dataload.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cts.ora.report.dataload.repository.AssociateRepository;
import com.cts.ora.report.domain.model.Associate;
import com.cts.ora.report.domain.model.BusinessUnit;
import com.cts.ora.report.domain.model.City;
import com.cts.ora.report.domain.model.Country;
import com.cts.ora.report.domain.model.EventCategory;
import com.cts.ora.report.domain.model.EventInfo;
import com.cts.ora.report.domain.model.IncomingFile;
import com.cts.ora.report.domain.model.Location;
import com.cts.ora.report.domain.model.PinCode;
import com.cts.ora.report.domain.model.Project;
import com.cts.ora.report.domain.model.ReportType;
import com.cts.ora.report.domain.model.ResidenceArea;
import com.cts.ora.report.domain.model.State;
import com.cts.ora.report.exception.ORAException;

@Repository
@org.springframework.transaction.annotation.Transactional
public class ORADataLoadDaoImpl implements ORADataLoadDao {
	
	Logger logger = LoggerFactory.getLogger(ORADataLoadDaoImpl.class);
	
	@PersistenceUnit
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	@Autowired
	private AssociateRepository ascRepo;
	
	//@Autowired private SessionFactory sessionFactory;
	

	@Override
	public void saveAssociates(List<Associate> associates) {
		logger.info("Into saveAssociates:"+associates);
		 
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			List<BusinessUnit> bus = associates.stream().filter(a->!a.isBuExists()).map(a->a.getBu()).distinct().collect(Collectors.toList());
			for(BusinessUnit bu:bus){
				em.persist(bu);
			}
			for(Associate a:associates) {
				em.persist(a);
			}
		    em.getTransaction().commit();
		}catch(Exception e) {
			logger.error("Error during saveAssociates:"+e.getMessage());
			try {
				em.getTransaction().rollback();
				throw new ORAException("DATA_SAVE_FAILURE", "Associate Data not saved", e);
			} catch (Exception e1) {
				logger.error("Error during rollback");
			}
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of saveAssociates");
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
		} catch (Exception e) {
			logger.error(e.getMessage());
			//throw new ORAException("0001","Error during getAllAssociates",e);
			return null;
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
			//throw new ORAException("FETCH_BU_FAILURE", "Fetch BU failure", e);
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
	public ReportType getReportTypeByName(String name) {
		logger.info("Into getReportTypeByName");
		ReportType reportType=null;
		
		try {
			em = emf.createEntityManager();
			// buList = em.createNativeQuery("select * from ora_ref_assc_bu",BusinessUnit.class).getResultList();
			reportType = em.createQuery("SELECT r FROM ReportType r WHERE r.reportType = :repType", ReportType.class)
							.setParameter("repType", name).getSingleResult();
		} catch (Exception e) {
			logger.error("Error in getReportTypeByName:"+e);
			//throw new ORAException();
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of getReportTypeByName");
		return reportType;
	}


	@Override
	public List<IncomingFile> createIncomingFiles(List<IncomingFile> incomingFiles) {
		logger.info("Into updateIncomingFiles:"+incomingFiles);
		
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			for(IncomingFile file:incomingFiles){
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
			IncomingFile file = em.find(IncomingFile.class, new Long(fileId));
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

	@Override
	public List<Project> getAllProjects() {
		logger.info("Into getAllProjects");
		List<Project> projectLst=null;
		try {
			em = emf.createEntityManager();
			projectLst = em.createQuery("SELECT p FROM Project p", Project.class).getResultList();
		} catch (Exception e) {
			logger.error("Error in getAllProjects:"+e.getMessage());
			//throw new ORAException("FETCH_PROJECT_FAILURE", "Fetch Proj failure", e);
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
	public List<EventCategory> getAllEventCategories() {
		logger.info("Into getAllEventCategories");
		List<EventCategory> eventCategoryLst=null;
		try {
			em = emf.createEntityManager();
			eventCategoryLst = em.createQuery("SELECT e FROM EventCategory e", EventCategory.class).getResultList();
		} catch (Exception e) {
			logger.error("Error in getAllEventCategories:"+e.getMessage());
			//throw new ORAException("FETCH_CATEGORY_FAILURE", "Fetch Category failure", e);
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
	public void saveProjectAndCategoryInfo(List<Project> projLst, List<EventCategory> eventCatLst) {
		logger.info("Into saveProjectAndCategoryInfo");

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			projLst.stream().forEach(p->{
				if(p.isPersist()){
					em.persist(p);
				}else if(p.isUpdate()){
					em.merge(p);
				}
			});
			
			eventCatLst.stream().forEach(e->{
				if(e.isPersist()){
					em.persist(e);
				}else if(e.isUpdate()){
					em.merge(e);
				}
			});
		    em.getTransaction().commit();
		}catch(Exception e) {
			logger.error("Error during saveAssociates:"+e.getMessage());
			try {
				em.getTransaction().rollback();
				throw new ORAException("PROJECT_CATEGORY_LOAD_FAILURE", "Project/Category not saved", e);
			} catch (Exception e1) {
				logger.error("Error during rollback");
			}finally {
				if(em!=null){
					em.close();
				}
			}
		}
		
		logger.info("Out of saveProjectAndCategoryInfo");
	}

	@Override
	public List<Location> getLocationById(Long locId) {
		logger.info("Into getLocationById:: "+locId);
		List<Location> locLst=null;
		try {
			em = emf.createEntityManager();
			if(locId>-1){
				//Get by Id
				locLst = em.createQuery("SELECT l FROM Location l WHERE l.locId = :locId", Location.class)
							.setParameter("locId", locId).getResultList();
			}else{
				//Get All
				locLst = em.createQuery("SELECT l FROM Location l", Location.class).getResultList();
			}
			
		} catch (Exception e) {
			logger.error("Error in getLocationById:"+e.getMessage());
			//throw new ORAException("FETCH_PROJECT_FAILURE", "Save geogrpahy data failure", e);
			return null;
		}finally {
			if(em!=null){
				em.close();
			}
		}
		logger.info("Out of getLocationById");
		return locLst;
	}
	
	@Override
	public List<Associate> getAssociateById(List<Long> ascIdLst) {
		logger.info("Into getAssociateById::");
		List<Associate> associates=null;
		try {
			em = emf.createEntityManager();
			String sql = "SELECT a.ASC_NAME,a.asc_id,a.created_date,a.designation,"
								+ "a.is_poc,a.is_volunteer,a.bu_id AS asc_bId  "
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
	public List<Country> getCountryById(Long cntryId) {
		logger.info("Into getCountryById:: "+cntryId);
		List<Country> countryLst=null;
		try {
			em = emf.createEntityManager();
			if(cntryId>-1){
				//Get by Id
				countryLst = em.createQuery("SELECT c FROM Country c WHERE c.locId = :cntryId", Country.class)
							.setParameter("cntryId", cntryId).getResultList();
			}else{
				//Get All
				countryLst = em.createQuery("SELECT c FROM Country c", Country.class).getResultList();
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
	public Location getLocationBasedOnPinCode(String pinNum) {
		logger.info("Into getLocationBasedOnPinCode:: "+pinNum);
		Location location=null;
		try {
			em = emf.createEntityManager();
			if(pinNum!=null){
				location = em.createQuery("SELECT l FROM Location l, PinCode p WHERE l.codeId=p.codeId AND p.name = :pinNum", Location.class)
							.setParameter("pinNum", pinNum).getSingleResult();
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
		return location;
	}
	
	@Override
	public void saveLocation(Map<String,List> geoMap) {
		logger.info("Into saveLocation:"+geoMap);
		
		List<Location> locLst = new ArrayList<>();
		
		List<Country> countryLst = geoMap.get("COUNTRY");
		List<State> stateLst = geoMap.get("STATE");
		List<City> cityLst = geoMap.get("CITY");
		List<ResidenceArea> areaLst = geoMap.get("AREA");
		List<PinCode> pinCodeLst = geoMap.get("CODE");
		
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			countryLst.stream().forEach(c->{
				if(c.isPersist()){
					em.persist(c);
					em.refresh(c);
				}else if(c.isUpdate()){
					em.merge(c);
				}
			});
			
			stateLst.stream().forEach(c->{
				if(c.isPersist()){
					em.persist(c);
					em.refresh(c);
				}else if(c.isUpdate()){
					em.merge(c);
				}
			});
			
			cityLst.stream().forEach(c->{
				if(c.isPersist()){
					em.persist(c);
					em.refresh(c);
				}else if(c.isUpdate()){
					em.merge(c);
				}
			});
			
			areaLst.stream().forEach(a->{
				if(a.isPersist()){
					em.persist(a);
					em.refresh(a);
				}else if(a.isUpdate()){
					em.merge(a);
				}
			});
			
			pinCodeLst.stream().forEach(c->{
				if(c.isPersist()){
					em.persist(c);
					em.refresh(c);
				}else if(c.isUpdate()){
					em.merge(c);
				}
			});
			
			pinCodeLst.stream().forEach(p->{
				if(p.isPersist()){
					Location loc = new Location();
					loc.setCodeId(p);
					loc.setResAreaId(p.getArea());
					loc.setCId(p.getArea().getCity());
					loc.setSId(p.getArea().getCity().getState());
					loc.setCountryId(p.getArea().getCity().getState().getCountry());
					em.persist(loc);
				}
			});
		    em.getTransaction().commit();		
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Error during saveLocation:"+e);
			try {
				em.getTransaction().rollback();
				throw new ORAException("GEOGRAPHY_DATA_LOAD_FAILURE", "Project/Category not saved", e);
			} catch (Exception e1) {
				logger.error("Error during rollback");
			}
		}finally {
			if(em!=null){
				em.close();
			}
		}
		
		logger.info("Out of saveLocation");
	}


	@Override
	public void saveEventInfo(List<EventInfo> eventInfoList) {
		logger.info("Into saveEventInfo");

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			eventInfoList.stream().forEach(e->{
				e.getPoc().stream().forEach(a->{
					em.merge(a);
				});
				em.persist(e);
			});

		    em.getTransaction().commit();
		}catch(Exception e) {
			logger.error("Error during saveEventInfo:"+e.getMessage());
			try {
				em.getTransaction().rollback();
				throw new ORAException("EVENT)SUMM_LOAD_FAILURE", "Project/Category not saved", e);
			} catch (Exception e1) {
				logger.error("Error during rollback");
			}finally {
				if(em!=null){
					em.close();
				}
			}
		}
		
		logger.info("Out of saveEventInfo");
		
	}
	
	


}
