package com.cts.ora.report.dataload.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cts.ora.report.common.util.JSONConverter;
import com.cts.ora.report.common.util.ORAMessageUtil;
import com.cts.ora.report.common.util.ORAUtil;
import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.constants.ORADataLoadConstants;
import com.cts.ora.report.dataload.dao.ORADataLoadDao;
import com.cts.ora.report.dataload.vo.ORADataLoadRequest;
import com.cts.ora.report.domain.model.Associate;
import com.cts.ora.report.domain.model.AssociateEventMap;
import com.cts.ora.report.domain.model.BusinessUnit;
import com.cts.ora.report.domain.model.City;
import com.cts.ora.report.domain.model.Country;
import com.cts.ora.report.domain.model.EventCategory;
import com.cts.ora.report.domain.model.EventInfo;
import com.cts.ora.report.domain.model.IncomingFile;
import com.cts.ora.report.domain.model.Location;
import com.cts.ora.report.domain.model.PinCode;
import com.cts.ora.report.domain.model.Project;
import com.cts.ora.report.domain.model.ResidenceArea;
import com.cts.ora.report.domain.model.State;
import com.cts.ora.report.exception.ORAException;
import com.cts.ora.report.file.vo.FileUploadResponse;
import com.cts.ora.report.file.vo.ORAFile;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class ORADataLoadServiceImpl implements ORADataLoadService {
	
	Logger logger = LoggerFactory.getLogger(ORADataLoadServiceImpl.class);
	
	@Value("${ora.common.file.loc}")
	private String UPLOAD_COMMON_PATH;
	
	@Autowired
	ORADataLoadDao oraDataLoadDao;
	
	@Override
	public ORAResponse loadIncomingFiles(ORADataLoadRequest request){
		logger.info("Into loadIncomingFiles");
		ORAResponse response = new ORAResponse();
		
		try {
			//Populate ora_sys_incoming_files table - ASync call
			List<ORAFile> uploadFiles = Stream.of(request.getAscFile(),request.getEventSummaryFile()
													,request.getEventDetailFile()).collect(Collectors.toList());
			createIncomingFileEntry(uploadFiles);
			
			//Load Associate Data
			updateIncomingFileStatus(request.getAscFile().getFileId(), loadAssociateData(request.getAscFile()));
			//Event Summary File
			updateIncomingFileStatus(request.getEventSummaryFile().getFileId(), loadEventSummaryData(request.getEventSummaryFile()));
			//Event Detail File
			updateIncomingFileStatus(request.getEventDetailFile().getFileId(), loadEventDetailData(request.getEventDetailFile()));
			
			ORAMessageUtil.setSuccessMessage(response);
		}catch(ORAException e){
			logger.error("Exception in loadAssociateData->"+e);
			ORAMessageUtil.setFailureMessage(response);
		} 
		catch (Exception e) {
			ORAMessageUtil.setFailureMessage(response);
		}
		logger.info("out of loadIncomingFiles");
		return response;
	}
	
	private void createIncomingFileEntry(List<ORAFile> uploadFiles){
		logger.info("Into updateIncomingTable"+uploadFiles);
		List<IncomingFile> incomingFiles=new ArrayList<>();
		IncomingFile incFile = null;
		for(ORAFile file:uploadFiles){
			if(isUploadFileValid(file)){
				incFile = new IncomingFile();
				incFile.setFileLoc(file.getFileLoc());
				incFile.setFileName(file.getFileLoc().substring(file.getFileLoc().lastIndexOf("\\")+1));
				file.setFileName(file.getFileLoc().substring(file.getFileLoc().lastIndexOf("\\")+1));
				incFile.setStatus(ORADataLoadConstants.NEW);
				incFile.setRepType(oraDataLoadDao.getReportTypeByName(file.getFileType()));
				incomingFiles.add(incFile);
			}
		}
		oraDataLoadDao.createIncomingFiles(incomingFiles);
		logger.info("incomingFiles saved:"+incomingFiles);
		
		incomingFiles.stream().forEach(i->{
			uploadFiles.stream().filter(f->f.getFileName().equals(i.getFileName()))
								.findFirst().get().setFileId(i.getInboundId());
		});
		logger.info("out of updateIncomingTable");
	}
	
	private void updateIncomingFileStatus(Long incomingFileId, Integer statusCode){
		logger.info("Into updateIncomingFileStatus:"+statusCode);
		logger.info("IncomingFileId:"+incomingFileId);
		logger.info("statusCode:"+statusCode);
		String status = null;
		if(statusCode>0){
			status=ORADataLoadConstants.DATALOAD_COMPLETE;
		}else{
			//Failed
			status=ORADataLoadConstants.DATALOAD_FAIL;
		}
		if(incomingFileId!=null){
			oraDataLoadDao.updateIncomingFileStatus(incomingFileId, status);
		}
		logger.info("out of updateIncomingFileStatus");
	}
	
	private boolean isUploadFileValid(ORAFile file){
		return (file!=null && null!=file.getFileLoc())?true:false;
	}
	
	private void updateDataLoadStatus(ORAFile file, Integer statusCode){
		//If statusCode=-1. then data load failed. update in ora_sys_dataload_log
		logger.info("Into updateDataLoadStatus:"+statusCode);
		
		logger.info("out of updateDataLoadStatus");
		
	}
	
	private Integer loadMasterData(ORAFile masterFile,Long ascId) {
		logger.info("Into loadMasterData");
		Integer statusCode = -1;
		try{
			if(masterFile!=null && masterFile.getFileLoc()!=null && !"".equals(masterFile.getFileLoc())){
				statusCode = populateMasterData(masterFile.getFileLoc(), ascId);
			}
		}catch(ORAException e){
			logger.error("Exception in loadMasterData->"+e);
			
		}catch (Exception e) {
			logger.error("Exception in loadMasterData->"+e);
		}
				
		logger.info("Out of loadMasterData");
		return statusCode;
	}

	
	private Integer loadAssociateData(ORAFile ascFile) {
		logger.info("Into loadAssociateData");
		Integer statusCode = -1;
		List<Associate> ascLst=null;
		try{
			if(ascFile!=null && ascFile.getFileLoc()!=null && !"".equals(ascFile.getFileLoc())){
				ascLst = parseAssociateInputFile(ascFile.getFileLoc());
				oraDataLoadDao.saveAssociates(ascLst);
				statusCode = 1;
			}
				
		}catch(ORAException e){
			logger.error("Exception in loadAssociateData->"+e);
			
		}catch (Exception e) {
			logger.error("Exception in loadAssociateData->"+e);
		}
				
		logger.info("Out of loadAssociateData");
		return statusCode;
	}
	
	private Integer loadEventSummaryData(ORAFile eventSummaryFile) {
		logger.info("Into loadEventSummaryData");
		Integer statusCode = -1;
		List<EventInfo> eventInfoList=null;
		try{
			if(eventSummaryFile!=null && eventSummaryFile.getFileLoc()!=null && !"".equals(eventSummaryFile.getFileLoc())){
				eventInfoList = parseEventSummaryInputFile(eventSummaryFile.getFileLoc());
				oraDataLoadDao.saveEventInfo(eventInfoList);
				statusCode = 1;
			}
				
		}catch(ORAException e){
			logger.error("Exception in loadEventSummaryData->"+e);
			
		}catch (Exception e) {
			logger.error("Exception in loadEventSummaryData->"+e);
		}
				
		logger.info("Out of loadEventSummaryData");
		return statusCode;
	}
	
	private Integer loadEventDetailData(ORAFile eventDetailFile) {
		logger.info("Into loadEventDetailData");
		Integer statusCode = -1;
		List<AssociateEventMap> ascEventInfo=null;
		try{
			if(eventDetailFile!=null && eventDetailFile.getFileLoc()!=null && !"".equals(eventDetailFile.getFileLoc())){
				ascEventInfo = parseEventDetailFile(eventDetailFile.getFileLoc());
				oraDataLoadDao.saveAssociateEventInfo(ascEventInfo);
				statusCode = 1;
			}
		}catch(ORAException e){
			logger.error("Exception in loadEventDetailData->"+e);
			
		}catch (Exception e) {
			logger.error("Exception in loadEventDetailData->"+e);
		}
				
		logger.info("Out of loadEventDetailData");
		return statusCode;
	}
	
	@Override
	public ORAResponse loadMasterDataFiles(ORADataLoadRequest request) {
		logger.info("Into loadMasterDataFiles");
		ORAResponse response = new ORAResponse();
		
		try {
			//Populate ora_sys_incoming_files table - ASync call
			List<ORAFile> uploadFiles = Stream.of(request.getMasterDataFile()).collect(Collectors.toList());
			createIncomingFileEntry(uploadFiles);
			
			//Load Master Data
			updateIncomingFileStatus(request.getMasterDataFile().getFileId(), 
										loadMasterData(request.getMasterDataFile(),request.getAscId()));
			ORAMessageUtil.setSuccessMessage(response);
		}catch(ORAException e){
			logger.error("Exception in loadMasterDataFiles->"+e);
			ORAMessageUtil.setFailureMessage(response);
		} 
		catch (Exception e) {
			ORAMessageUtil.setFailureMessage(response);
		}
		logger.info("out of loadMasterDataFiles");
		return response;
	}
	
	private Integer populateMasterData(String filePath, Long ascId){
		logger.info("Into populateMasterData");
		Integer status = -1;
		XSSFWorkbook  wb=null;
		
		try {
			wb = new XSSFWorkbook(filePath);
			int n1 = populateProjectCategoryData(wb.getSheet("ProjectCategory"),ascId);
			int n2 = populateGeographyData(wb.getSheet("Geography"),ascId);
			
			if(n1==1 && n2 ==1){
				status =1;
			}
			}catch(Exception e){
				e.printStackTrace();
				logger.error("Error during populateMasterData:"+e.getMessage());
				throw new ORAException();
			}finally {
				if(wb!=null){
					try {
						wb.close();
					} catch (IOException e) {
						logger.error("Error during closing Excel WB:"+e.getMessage());
					}
				}
			}
		logger.info("Out of populateMasterData");
		return status;
	}
	
	private int populateGeographyData(XSSFSheet sheet,Long ascId)
					throws JsonProcessingException{
		logger.info("In populateGeographyData");
		
		List<Location> locationLst = null;
		List<Country> countryLst = null;
		List<State> stateLst = null;
		List<City> cityLst = null;
		List<ResidenceArea> areaLst = null;
		List<PinCode> pinCodeLst = null;
		Map<String,List> geoMap = new HashMap<>();
		
		Country cntry = null;
		State state = null;
		City city = null;
		ResidenceArea area = null;
		PinCode code = null;
		
		if(sheet!=null){
			int rows = sheet.getPhysicalNumberOfRows();
			logger.info("Geography Sheet has " + rows+ " row(s).");
			
			locationLst = oraDataLoadDao.getLocationById(-1L);
			logger.info("locationLst:"+JSONConverter.toString(locationLst));
			countryLst = locationLst.stream().map(l->l.getCountryId()).distinct().collect(Collectors.toList());
			logger.info("countryLst:"+JSONConverter.toString(countryLst));
			stateLst = locationLst.stream().map(l->l.getSId()).distinct().collect(Collectors.toList());
			logger.info("stateLst:"+JSONConverter.toString(stateLst));
			cityLst = locationLst.stream().map(l->l.getCId()).distinct().collect(Collectors.toList());
			logger.info("cityLst:"+JSONConverter.toString(cityLst));
			areaLst = locationLst.stream().map(l->l.getResAreaId()).distinct().collect(Collectors.toList());
			logger.info("areaLst:"+JSONConverter.toString(areaLst));
			pinCodeLst = locationLst.stream().map(l->l.getCodeId()).distinct().collect(Collectors.toList());
			logger.info("pinCodeLst:"+JSONConverter.toString(pinCodeLst));
			
			for (int r = 1; r < rows; r++) {
				XSSFRow row = sheet.getRow(r);
				if (row != null) {
					String countryName = isValidCell(row.getCell(0))?row.getCell(0).getRichStringCellValue().getString():null;
					String stateName = isValidCell(row.getCell(1))?row.getCell(1).getRichStringCellValue().getString():null;
					String cityName = isValidCell(row.getCell(2))?row.getCell(2).getRichStringCellValue().getString():null;
					String areaName = isValidCell(row.getCell(3))?row.getCell(3).getRichStringCellValue().getString():null;
					String pinNum = isValidCell(row.getCell(4))?row.getCell(4).getRawValue():"-1";
					
					cntry = new Country();
					cntry.setName(countryName);
					if(countryLst.contains(cntry)){
						cntry = countryLst.get(countryLst.indexOf(cntry));
						cntry.setUpdate(Boolean.TRUE);
					}else{
						cntry.setPersist(Boolean.TRUE);
						cntry.setCreatedBy(ascId);
						countryLst.add(cntry);
					}
					
					state = new State();
					state.setName(stateName);
					if(stateLst.contains(state)){
						state = stateLst.get(stateLst.indexOf(state));
						state.setUpdate(Boolean.TRUE);
					}else{
						state.setPersist(Boolean.TRUE);
						state.setCreatedBy(ascId);
						stateLst.add(state);
						state.setCountry(cntry);
					}
					
					city = new City();
					city.setName(cityName);
					if(cityLst.contains(city)){
						city = cityLst.get(cityLst.indexOf(city));
						city.setUpdate(Boolean.TRUE);
						
					}else{
						city.setPersist(Boolean.TRUE);
						city.setCreatedBy(ascId);
						cityLst.add(city);
						city.setState(state);
					}
					
					area = new ResidenceArea();
					area.setName(areaName);
					if(areaLst.contains(area)){
						area = areaLst.get(areaLst.indexOf(area));
						area.setUpdate(Boolean.TRUE);
						
					}else{
						area.setPersist(Boolean.TRUE);
						area.setCreatedBy(ascId);
						area.setCity(city);
						areaLst.add(area);
					}
					
					code = new PinCode();
					code.setName(pinNum);
					if(pinCodeLst.contains(code)){
						code = pinCodeLst.get(pinCodeLst.indexOf(code));
						code.setUpdate(Boolean.TRUE);
						
					}else{
						code.setPersist(Boolean.TRUE);
						code.setCreatedBy(ascId);
						code.setArea(area);
						pinCodeLst.add(code);
					}
				}
			}		
			geoMap.put("COUNTRY", countryLst);
			geoMap.put("STATE", stateLst);
			geoMap.put("CITY", cityLst);
			geoMap.put("AREA", areaLst);
			geoMap.put("CODE", pinCodeLst);
			
			//Save Geo data
			oraDataLoadDao.saveLocation(geoMap);
			logger.info("Out of populateGeographyData");
			return 1;
		}else{
			return -1;
		}
				
	}
	
	private int populateProjectCategoryData(XSSFSheet sheet, Long ascId) 
						throws JsonProcessingException{
		logger.info("In populateProjectCategoryData");
		List<Project> projLst = null;
		List<EventCategory> eventCatLst = null;
		
		Project p= null;
		EventCategory c= null;
		if(sheet!=null){
			int rows = sheet.getPhysicalNumberOfRows();
			logger.info("ProjectCategory Sheet has " + rows+ " row(s).");
			
			projLst = oraDataLoadDao.getAllProjects();
			logger.info("projLst:"+JSONConverter.toString(projLst));
			eventCatLst = oraDataLoadDao.getAllEventCategories();
			logger.info("eventCatLst:"+JSONConverter.toString(eventCatLst));
			
			for (int r = 1; r < rows; r++) {
				XSSFRow row = sheet.getRow(r);
				if (row != null) {
					String projectName = isValidCell(row.getCell(0))?row.getCell(0).getRichStringCellValue().getString():null;
					String categoryName = isValidCell(row.getCell(1))?row.getCell(1).getRichStringCellValue().getString():null;
					p = isProjExists(projLst,projectName);
					c = isCategoryExists(eventCatLst,categoryName);
					
					if(p==null) {
						p = new Project();
						p.setTitle(projectName);
						p.setDescription(projectName);
						p.setCreatedBy(ascId);
						p.setStatus("A");
						p.setPersist(Boolean.TRUE);
						if(c==null){
							c = new EventCategory();
							c.setTitle(categoryName);
							c.setDescription(projectName+" - "+categoryName);
							c.setCreatedBy(ascId);
							c.setStatus("A");
							c.setPersist(Boolean.TRUE);
							eventCatLst.add(c);
						}else{
							c.setUpdate(Boolean.TRUE);
						}
						projLst.add(p);
					}else{
						if(c==null){
							c = new EventCategory();
							c.setTitle(categoryName);
							c.setDescription(projectName+" - "+categoryName);
							c.setCreatedBy(ascId);
							c.setPersist(Boolean.TRUE);
							c.setStatus("A");
							eventCatLst.add(c);
						}
					}
					c.setProject(p);
				}
			}
			//Save Project & Category data
			oraDataLoadDao.saveProjectAndCategoryInfo(projLst,eventCatLst);
			logger.info("Out of populateProjectCategoryData");
			return 1;
		}else{
			return -1;
		}
	}
	
	
	private Project isProjExists(List<Project> projLst, String projectName){
		Project p =new Project();
		p.setTitle(projectName);
		if(projLst!=null && projLst.contains(p)){
			return projLst.get(projLst.indexOf(p));
		}else{
			return null;
		}
	}
	
	private EventCategory isCategoryExists(List<EventCategory> eventCatLst,String categoryName){
		EventCategory p =new EventCategory();
		p.setTitle(categoryName);
		if(eventCatLst!=null && eventCatLst.contains(p)){
			return eventCatLst.get(eventCatLst.indexOf(p));
		}else{
			return null;
		}
	}
	
	private List<Associate> parseAssociateInputFile(String filePath){
		logger.info("Loading Associate Data");
		List<Associate> ascLst=new ArrayList<>();
		Associate a=null;
		List<Associate> existingAssociates=null;
		List<BusinessUnit> buList = null;
		XSSFWorkbook  wb=null;
		
		try {
			wb = new XSSFWorkbook(filePath);
			XSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			logger.info("Associate Sheet has " + rows+ " row(s).");
			if(rows>0){
				//Fetch existing employees
				existingAssociates = oraDataLoadDao.getAllAssociates();
				logger.info("existingAssociates:"+JSONConverter.toString(existingAssociates));
			}
			if(existingAssociates!=null ){
				buList = oraDataLoadDao.getAllBusinessUnits();
			}
			
			for (int r = 1; r < rows; r++) {
				XSSFRow row = sheet.getRow(r);
				if (row != null) {
					String empId = isValidCell(row.getCell(0))?row.getCell(0).getRawValue():"-1";
					String buName = isValidCell(row.getCell(4))?row.getCell(4).getRichStringCellValue().getString():null;
					if(isAssociateExists(existingAssociates,Integer.parseInt(empId))) {
								//&& noChangeInDept(existingAssociates,Long.parseLong(empId),buName)){
						continue;
					}
					String name = isValidCell(row.getCell(1))?row.getCell(1).getRichStringCellValue().getString():null;
					String designation = isValidCell(row.getCell(2))?row.getCell(2).getRichStringCellValue().getString():null;
					//String loc = isValidCell(row.getCell(3))?row.getCell(3).getRichStringCellValue().getString():null;

					a = new Associate();
					a.setId(Long.parseLong(empId+""));
					a.setAscName(name);
					a.setDesignation(designation);
					
					BusinessUnit bu = new BusinessUnit();
					bu.setName(buName);
					bu.setDescription(buName);
					if (buList != null) {
						if (buList.indexOf(bu)> -1) {
							a.setBuExists(Boolean.TRUE);
							bu = buList.get(buList.indexOf(bu));
						} else {
							buList.add(bu);
						}

					}else {
						buList = new ArrayList<>();
						buList.add(bu);
					}
					
					a.setBu(bu);
					a.setIsPOC(Boolean.FALSE);
					a.setIsVolunteer(Boolean.FALSE);
					
					ascLst.add(a);
				}
			}
			}catch(Exception e){
				e.printStackTrace();
				throw new ORAException();
			}finally {
				if(wb!=null){
					try {
						wb.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		return ascLst;
	}
	
	private String getCellValue(Cell cell){
		DataFormatter df = new DataFormatter();
		return df.formatCellValue(isValidCell(cell)?cell:null);
	}
	
	private List<EventInfo> parseEventSummaryInputFile(String filePath){
		logger.info("Into parseEventSummaryInputFile");
		List<EventInfo> eventInfoList=new ArrayList<>();
		EventInfo evntInfo=null;
		Set<Long> associateLst=new HashSet();
		List<Project> projLst = null;
		List<EventCategory> eventCatLst = null;
		List<Associate> ascLst = null;
		
		XSSFWorkbook  wb=null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
		
		try {
			wb = new XSSFWorkbook(filePath);
			XSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			logger.info("Event Summary Sheet has " + rows+ " row(s).");
			
			List<Project> allProjLst = oraDataLoadDao.getAllProjects();
			//logger.info("allProjLst:"+JSONConverter.toString(allProjLst));
			eventCatLst = oraDataLoadDao.getAllEventCategories();
			//logger.info("eventCatLst:"+JSONConverter.toString(eventCatLst));
			
			for (int r = 1; r < rows; r++) {
				XSSFRow row = sheet.getRow(r);
				if (row != null) {
					
					String isApproved = isValidCell(row.getCell(17))?row.getCell(17).getRichStringCellValue().getString():null;
					if(!isApproved.equalsIgnoreCase("Approved")){
						continue;
					}
					String eventId = getCellValue(row.getCell(0));
					String baseLoc = getCellValue(row.getCell(2));
					String beneficiaryName = getCellValue(row.getCell(3));
					String eventAddr = getCellValue(row.getCell(4));
					String projectName = getCellValue(row.getCell(6));
					String catName = getCellValue(row.getCell(7));
					
					String eventName = getCellValue(row.getCell(8));
					//String desc = getCellValue(row.getCell(9));
					String eventDate = getCellValue(row.getCell(10)); //sdf.format(row.getCell(10).getDateCellValue());
					logger.info("eventDate:"+eventDate);
					boolean isWeekend = ORAUtil.isWeekend(sdf.parse(eventDate));
					
					String totVolCount = getCellValue(row.getCell(11));
					String totVolHours = getCellValue(row.getCell(12));
					String totTravelHours = getCellValue(row.getCell(13));
					String totEventHours = getCellValue(row.getCell(14));
					String totLivesImpact = getCellValue(row.getCell(15));
					
					String pocs = getCellValue(row.getCell(18));
					List<String> empIdLst = Arrays.asList(pocs.split(";"));
					
					associateLst.addAll(empIdLst.stream().map(id->Long.parseLong(id)).collect(Collectors.toSet()));
					
					logger.info("eventId:"+eventId);
					logger.info("isWeekend:"+isWeekend);
					logger.info("empIdLst:"+empIdLst);
					
					Location loc = oraDataLoadDao.getLocationBasedOnPinCode(eventAddr.substring(eventAddr.lastIndexOf("-")+1));
					logger.info("loc:"+loc);
					
					Project proj = allProjLst.stream().filter(p->p.getTitle().equals(projectName)).findFirst().get();
					EventCategory cat = eventCatLst.stream().filter(c->c.getTitle().equals(catName)).findFirst().get();
					
					evntInfo = new EventInfo();
					evntInfo.setEventId(eventId);
					evntInfo.setEventName(eventName);
					evntInfo.setProject(proj);
					evntInfo.setCategory(cat);
					evntInfo.setBeneficiaryName(beneficiaryName);
					//evntInfo.setDescription(desc);
					evntInfo.setOnWeekend(isWeekend);
					evntInfo.setEventDate(sdf.parse(eventDate));
					evntInfo.setBaseLoc(baseLoc);
					evntInfo.setLocation(loc);
					evntInfo.setEventAddress(eventAddr);
					evntInfo.setTotalVolunteersCount(Integer.parseInt(totVolCount));
					evntInfo.setTotalVolunteerHrs(Float.parseFloat(totVolHours));
					evntInfo.setTotalTravelHrs(Float.parseFloat(totTravelHours));
					evntInfo.setTotalEventHrs(Float.parseFloat(totEventHours));
					evntInfo.setLivesImpacted(Integer.parseInt(totLivesImpact));
					evntInfo.setPoc(empIdLst.stream().map(a-> {Associate asc = new Associate();
											asc.setId(Long.parseLong(a)); return asc;})
											.collect(Collectors.toSet()));
					
					//calc Period from eventDate
					evntInfo.setPeriod(ORAUtil.getPeriod(sdf.parse(eventDate)));
					eventInfoList.add(evntInfo);
				}
			}
			ascLst = oraDataLoadDao.getAssociateById(associateLst.stream().collect(Collectors.toList()));
			logger.info("ascLst::"+ascLst);
			
			for(EventInfo e:eventInfoList){
				Set<Associate> updatedAsc = new HashSet<>();
				for(Associate a:e.getPoc()){
					if(ascLst!=null && ascLst.indexOf(a)>-1){
						a = ascLst.get(ascLst.indexOf(a));
						a.setIsPOC(Boolean.TRUE);
						a.setIsVolunteer(Boolean.TRUE);
						if(a.getFirstVolunteerDate()==null) {
							a.setFirstVolunteerDate(e.getEventDate());
						}
						updatedAsc.add(a);
					}else{
						a=null;
					}
				}
				e.setPoc(updatedAsc);
			}
			
			logger.info("eventInfoList::"+JSONConverter.toString(eventInfoList));
			}catch(Exception e){
				e.printStackTrace();
				logger.error("Error in parseEventSummaryInputFile:"+e);
				throw new ORAException();
			}finally {
				if(wb!=null){
					try {
						wb.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		logger.info("Out of parseEventSummaryInputFile");
		return eventInfoList;
	}
	
	private List<AssociateEventMap> parseEventDetailFile(String filePath){
		logger.info("Into parseEventDetailFile");
		List<EventInfo> eventInfoList=new ArrayList<>();
		EventInfo evntInfo=null;
		Set<Long> associateLst=new HashSet();
		List<Associate> ascLst = null;
		Associate asc = null;
		
		List<AssociateEventMap> ascEventLst = new ArrayList<>();
		Set<String> eventtIds = new HashSet<>();
		AssociateEventMap ascEventInfo = null;
		
		XSSFWorkbook  wb=null;
		
		try {
			wb = new XSSFWorkbook(filePath);
			XSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			logger.info("Event Detail Sheet has " + rows+ " row(s).");
			
			for (int r = 1; r < rows; r++) {
				XSSFRow row = sheet.getRow(r);
				if (row != null) {
					
					String isApproved = isValidCell(row.getCell(13))?row.getCell(13).getRichStringCellValue().getString():null;
					if(!isApproved.equalsIgnoreCase("Approved")){
						continue;
					}
					
					String eventId = getCellValue(row.getCell(0));
					logger.info("eventId:"+eventId);
					eventtIds.add(eventId);
					String empId = getCellValue(row.getCell(7));
					associateLst.add(Long.parseLong(empId));
					
					String volHours = getCellValue(row.getCell(9));
					String travelHours = getCellValue(row.getCell(10));
					String livesImpactCount = getCellValue(row.getCell(11));
					
					ascEventInfo = new AssociateEventMap();
					evntInfo = new EventInfo();
					evntInfo.setEventId(eventId);
					ascEventInfo.setEvent(evntInfo);
					
					asc = new Associate();
					asc.setId(Long.parseLong(empId));
					ascEventInfo.setAsc(asc);
					
					ascEventInfo.setVolHours(Float.parseFloat(volHours));
					ascEventInfo.setTravelHours(Float.parseFloat(travelHours));
					ascEventInfo.setLivesImpactedCount(Integer.parseInt(livesImpactCount));
					ascEventLst.add(ascEventInfo);
				}
			}
			logger.info("eventtIds::"+eventtIds);
			
			eventInfoList = oraDataLoadDao.getEventById(eventtIds.stream().collect(Collectors.toList()));
			logger.info("eventInfoList::"+eventInfoList);
			
			ascLst = oraDataLoadDao.getAssociateById(associateLst.stream().collect(Collectors.toList()));
			logger.info("ascLst::"+ascLst);
			
			for (AssociateEventMap aEvnt : ascEventLst) {

				if (eventInfoList != null && eventInfoList.indexOf(aEvnt.getEvent()) > -1) {
					aEvnt.setEvent(eventInfoList.get(eventInfoList.indexOf(aEvnt.getEvent())));
				}else{
					logger.info("eventInfoList NotFound::");
				}
				
				if (ascLst != null && ascLst.indexOf(aEvnt.getAsc()) > -1) {
					aEvnt.setAsc(ascLst.get(ascLst.indexOf(aEvnt.getAsc())));
				}else{
					logger.info("ascLst NotFound::");
				}
			}
			
			logger.info("ascEventLst***2::"+JSONConverter.toString(ascEventLst));
			}catch(Exception e){
				e.printStackTrace();
				logger.error("Error in parseEventDetailFile:"+e);
				throw new ORAException();
			}finally {
				if(wb!=null){
					try {
						wb.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		logger.info("Out of parseEventDetailFile");
		return ascEventLst;
	}
	
	private boolean isAssociateExists(List<Associate> existingAssociates,Integer empId){
		if(existingAssociates!=null && existingAssociates.size()>0){
			return existingAssociates.stream().map(a->a.getId()).filter(id->(empId>0 && id.equals(empId))).count()>0?true:false;
		}else{
			return false;
		}
	}
	
	private boolean noChangeInDept(List<Associate> existingAssociates,Long empId,String buName){
		if(existingAssociates!=null && existingAssociates.size()>0){
			logger.info("noChangeInDept->"+buName);
			return existingAssociates.stream().filter(e->e.getId().equals(empId))
					.findFirst().map(a->a.getBu()).filter(bu->(!bu.getName().equals(buName))).isPresent()?true:false;
		}else{
			return false;
		}
	}
	
	private boolean isValidCell(Cell cell){
		return (cell!=null && !cell.getCellType().equals(CellType.BLANK));
	}
	
	/*private Object getCellValue(Cell cell){
		Object value= null;
		switch (cell.getCellType()) {
        case STRING:
        	value =cell.getRichStringCellValue().getString();
            break;
        case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                System.out.println(cell.getDateCellValue());
            } else {
                System.out.println(cell.getNumericCellValue());
            }
            break;
        case BOOLEAN:
            System.out.println(cell.getBooleanCellValue());
            break;
        case FORMULA:
            System.out.println(cell.getCellFormula());
            break;
        case BLANK:
            System.out.println();
            break;
        default:
            System.out.println();
    }
		return value;
	}*/
	
	public static void main(String[] args){
		//String fp = "C:\\Users\\hp\\Desktop\\Cognizant FSE\\Input Data\\Associate Details.xlsx";
		String evtInfoFp = "C:\\Users\\hp\\Desktop\\Cognizant FSE\\Input Data\\OutReach Event Information.xlsx";
		String masterDataFp = "C:\\Users\\hp\\Desktop\\Cognizant FSE\\Input Data\\ProjectCategory_Master.xlsx";
		//String evtInfoFp = "/Volumes/DATA/test/fse_input/OutReach Event Information.xlsx";
		//String fp = "/Volumes/DATA/test/fse_input/AssociateDetails.xlsx";
		//String evtInfoFp = "/Volumes/DATA/test/fse_input/OutReach Event Information.xlsx";
		//String entSummFp = "/Volumes/DATA/test/fse_input/Outreach Events Summary.xlsx";
		
		ORADataLoadServiceImpl srv = new ORADataLoadServiceImpl();
		srv.parseEventSummaryInputFile(masterDataFp);
	}


	@Override
	public List<Associate> getAssociates() {
		logger.info("Into getAssociates");
		return oraDataLoadDao.getAllAssociates();
	}
	
	@Override
	public List<BusinessUnit> getBusinessUnits() {
		logger.info("Into getBusinessUnits");
		return oraDataLoadDao.getAllBusinessUnits();
	}

	@Override
	public ORAResponse fetchDataLoadLog(ORADataLoadRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileUploadResponse uploadFile(MultipartFile file) {
		logger.info("Into uploadFile");
		FileUploadResponse resp =new FileUploadResponse();
		ORAFile uploadedFile = new ORAFile();
		
		try {
			// Get the file and save it
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOAD_COMMON_PATH + file.getOriginalFilename());
			Files.write(path, bytes);
			
			//Entry into ora_sys_incoming_files
			//Get the FIle ID and set it in resp
			//uploadedFile.setFileId(fileId);
			uploadedFile.setFileLoc(UPLOAD_COMMON_PATH + file.getOriginalFilename());
			resp.setUploadFile(uploadedFile);
			
			ORAMessageUtil.setSuccessMessage(resp);
		} catch (IOException e) {
			logger.error(e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
        
        logger.info("Out of uploadFile");
        return resp;
        
	}

}
