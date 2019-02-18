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
import org.springframework.stereotype.Component;

import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.dataload.dao.ORADataLoadDao;
import com.cts.ora.report.dataload.domain.Associate;
import com.cts.ora.report.dataload.vo.ORADataLoadRequest;
import com.cts.ora.report.exception.ORAException;

@Component
public class ORADataLoadServiceImpl implements ORADataLoadService {
	
	Logger logger = LoggerFactory.getLogger(ORADataLoadServiceImpl.class);
	
	@Autowired
	ORADataLoadDao oraDataLoadDao;
	

	@Override
	public ORAResponse loadAssociateData(ORADataLoadRequest request) {
		logger.info("Into loadAssociateData");
		ORAResponse response = new ORAResponse();
		List<Associate> ascLst=null;
		Associate associate = null;	
		
		try{
			ascLst = parseAssociateInputFile(request.getFileLoc());
			oraDataLoadDao.saveAssociates(ascLst);
			
			
		}catch(ORAException e){
			
		}catch (Exception e) {
			logger.error("Exception in loadAssociateData->"+e);
		}
				
		logger.info("Out of loadAssociateData");
		return response;
	}

	
	public List<Associate> parseAssociateInputFile(String filePath){
		logger.info("Loading Associate Data");
		List<Associate> ascLst=new ArrayList<>();
		Associate a=null;
		List<Associate> existingAssociates=null;
		
		XSSFWorkbook  wb=null;
		
		try {
			wb = new XSSFWorkbook(filePath);
			XSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			logger.info("Associate Sheet has " + rows+ " row(s).");
			if(rows>0){
				//Fetch existing employees
				existingAssociates = oraDataLoadDao.geAllAssociates();
			}
			
			for (int r = 0; r < rows; r++) {
				XSSFRow row = sheet.getRow(r);
				if (row != null) {
					double empId = isValidCell(row.getCell(0))?row.getCell(0).getNumericCellValue():-1;
					if(isAssociateExists(existingAssociates,empId)){
						continue;
					}
					String name = isValidCell(row.getCell(1))?row.getCell(1).getRichStringCellValue().getString():null;
					String designation = isValidCell(row.getCell(2))?row.getCell(2).getRichStringCellValue().getString():null;
					//String loc = isValidCell(row.getCell(3))?row.getCell(3).getRichStringCellValue().getString():null;
					String bu = isValidCell(row.getCell(4))?row.getCell(4).getRichStringCellValue().getString():null;
					
					a = new Associate();
					a.setId(Integer.parseInt(empId+""));
					a.setName(name);
					a.setDesignation(designation);
					//a.setBu_id(bu); FK reference
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
	
	private boolean isAssociateExists(List<Associate> existingAssociates,double empId){
		if(existingAssociates!=null){
			return existingAssociates.stream().map(a->a.getId()).filter(id->(id.doubleValue()==empId)).count()>0?true:false;
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
		
		ORADataLoadServiceImpl srv = new ORADataLoadServiceImpl();
		srv.parseAssociateInputFile(fp);
	}


	@Override
	public List<Associate> getAssociates() {
		logger.info("Into getAssociates");
		return oraDataLoadDao.geAllAssociates();
	}
}
