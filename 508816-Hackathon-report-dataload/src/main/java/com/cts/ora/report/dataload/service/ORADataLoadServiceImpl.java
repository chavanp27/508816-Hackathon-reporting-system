package com.cts.ora.report.dataload.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
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
import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.constants.ORADataLoadConstants;
import com.cts.ora.report.dataload.dao.ORADataLoadDao;
import com.cts.ora.report.dataload.domain.Associate;
import com.cts.ora.report.dataload.domain.BusinessUnit;
import com.cts.ora.report.dataload.domain.IncomingFiles;
import com.cts.ora.report.dataload.vo.ORADataLoadRequest;
import com.cts.ora.report.exception.ORAException;
import com.cts.ora.report.file.vo.FileUploadResponse;
import com.cts.ora.report.file.vo.ORAFile;

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
			updateIncomingFileStatus(request.getEventSummaryFile().getFileId(), loadAssociateData(request.getAscFile()));
			//Event Detail File
			updateIncomingFileStatus(request.getEventDetailFile().getFileId(), loadAssociateData(request.getAscFile()));
			
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
		List<IncomingFiles> incomingFiles=new ArrayList<>();
		IncomingFiles incFile = null;
		for(ORAFile file:uploadFiles){
			if(isUploadFileValid(file)){
				incFile = new IncomingFiles();
				incFile.setFileLoc(file.getFileLoc());
				incFile.setFileName(file.getFileLoc().substring(file.getFileLoc().lastIndexOf("\\")+1));
				file.setFileName(file.getFileLoc().substring(file.getFileLoc().lastIndexOf("\\")+1));
				incFile.setStatus(ORADataLoadConstants.NEW);
				incFile.setRepType(oraDataLoadDao.getReportTypeByName("ASSOCIATE_INFO"));
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
		logger.info("Into loadAssociateData");
		Integer statusCode = -1;
		List<Associate> ascLst=null;
		try{
			if(eventSummaryFile!=null && eventSummaryFile.getFileLoc()!=null && !"".equals(eventSummaryFile.getFileLoc())){
				ascLst = parseAssociateInputFile(eventSummaryFile.getFileLoc());
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
	
	private Integer loadEventDetailData(ORAFile eventDetailFile) {
		logger.info("Into loadAssociateData");
		Integer statusCode = -1;
		List<Associate> ascLst=null;
		try{
			if(eventDetailFile!=null && eventDetailFile.getFileLoc()!=null && !"".equals(eventDetailFile.getFileLoc())){
				ascLst = parseAssociateInputFile(eventDetailFile.getFileLoc());
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
				//logger.info("buList:"+JSONConverter.toString(buList));
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
					a.setId(Integer.parseInt(empId+""));
					a.setAscName(name);
					a.setDesignation(designation);
					
					BusinessUnit bu = new BusinessUnit();
					bu.setName(buName);
					bu.setDescription(buName);
					//logger.info("BULst:"+JSONConverter.toString(buList));
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
			//logger.info("Out of parseAssociateInputFile"+JSONConverter.toString(ascLst));
			
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
	
	private Object getCellValue(Cell cell){
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
	}
	
	public static void main(String[] args){
		String fp = "C:\\Users\\hp\\Desktop\\Cognizant FSE\\Input Data\\Associate Details.xlsx";
		//String fp = "/Volumes/DATA/test/fse_input/AssociateDetails.xlsx";
		//String evtInfoFp = "/Volumes/DATA/test/fse_input/OutReach Event Information.xlsx";
		//String entSummFp = "/Volumes/DATA/test/fse_input/Outreach Events Summary.xlsx";
		
		ORADataLoadServiceImpl srv = new ORADataLoadServiceImpl();
		srv.parseAssociateInputFile(fp);
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
