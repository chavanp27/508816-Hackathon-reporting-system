package com.cts.ora.report.dataload.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.cts.ora.report.common.util.JSONConverter;
import com.cts.ora.report.common.util.ORAMessageUtil;
import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.dataload.dao.ORADataLoadDao;
import com.cts.ora.report.dataload.domain.Associate;
import com.cts.ora.report.dataload.domain.BusinessUnit;
import com.cts.ora.report.dataload.vo.ORADataLoadRequest;
import com.cts.ora.report.exception.ORAException;
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
			//populateIncomingTable();
			
			//Associate Data
			updateDataLoadStatus(request.getAscFile(),loadAssociateData(request.getAscFile()));
			//Event Summary File
			updateDataLoadStatus(request.getEventSummaryFile(),loadAssociateData(request.getEventSummaryFile()));
			//Event Detail File
			updateDataLoadStatus(request.getEventDetailFile(),loadAssociateData(request.getEventDetailFile()));
			
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
				existingAssociates = oraDataLoadDao.geAllAssociates();
				logger.info("existingAssociates==" + existingAssociates);
			}
			if(existingAssociates!=null ){
				buList = oraDataLoadDao.geAllBusinessUnits();
				logger.info("buList==" + buList);
			}
			
			for (int r = 1; r < rows; r++) {
				XSSFRow row = sheet.getRow(r);
				if (row != null) {
					String empId = isValidCell(row.getCell(0))?row.getCell(0).getRawValue():"-1";
					String buName = isValidCell(row.getCell(4))?row.getCell(4).getRichStringCellValue().getString():null;
					if(isAssociateExists(existingAssociates,Long.parseLong(empId)) 
								&& noChangeInDept(existingAssociates,Long.parseLong(empId),buName)){
						continue;
					}
					String name = isValidCell(row.getCell(1))?row.getCell(1).getRichStringCellValue().getString():null;
					String designation = isValidCell(row.getCell(2))?row.getCell(2).getRichStringCellValue().getString():null;
					//String loc = isValidCell(row.getCell(3))?row.getCell(3).getRichStringCellValue().getString():null;

					a = new Associate();
					a.setId(Integer.parseInt(empId+""));
					a.setName(name);
					a.setDesignation(designation);
					
					BusinessUnit bu = new BusinessUnit();
					bu.setName(buName);
					if(buList!=null && buList.contains(bu)){
						a.setBuExists(Boolean.TRUE);
						bu = buList.get(buList.indexOf(bu));
					}else{
						bu.setDescription(buName);
						buList.add(bu);
					}
					a.setBu(bu);
					a.setIsPOC(Boolean.FALSE);
					a.setIsVolunteer(Boolean.FALSE);
					
					ascLst.add(a);
				}
			}
			logger.info("Out of parseAssociateInputFile"+JSONConverter.toString(ascLst));
			
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
		logger.info("Input data=="+ascLst);
		return ascLst;
	}
	
	private boolean isAssociateExists(List<Associate> existingAssociates,Long empId){
		if(existingAssociates!=null && existingAssociates.size()>0){
			return existingAssociates.stream().map(a->a.getId()).filter(id->(empId>0 && id.longValue()==empId.longValue())).count()>0?true:false;
		}else{
			return false;
		}
	}
	
	private boolean noChangeInDept(List<Associate> existingAssociates,Long empId,String buName){
		if(existingAssociates!=null && existingAssociates.size()>0){
			return existingAssociates.stream().filter(e->e.getId().equals(empId))
								.findFirst().map(a->a.getBu()).filter(bu->(bu!=null && bu.getName().equals(buName))).isPresent()?true:false;
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
		
		ORADataLoadServiceImpl srv = new ORADataLoadServiceImpl();
		srv.parseAssociateInputFile(fp);
	}


	@Override
	public List<Associate> getAssociates() {
		logger.info("Into getAssociates");
		return oraDataLoadDao.geAllAssociates();
	}

	@Override
	public ORAResponse fetchDataLoadLog(ORADataLoadRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
