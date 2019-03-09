package com.cts.ora.report.dataload.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cts.ora.report.common.util.ORAMessageUtil;
import com.cts.ora.report.dataload.vo.ORAFileResponse;

@Component
public class FileServiceImpl implements FileService {
	
	Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Value("${ora.common.file.loc}")
	private String UPLOAD_COMMON_PATH;
	
	@Override
	public ORAFileResponse saveFile(MultipartFile file) {
		logger.info("Into saveFile");
		ORAFileResponse resp = new ORAFileResponse();
		String fileName = null;
		
        try {
        	Path fileStorageLocation = Paths.get(UPLOAD_COMMON_PATH+UUID.randomUUID().toString());
    		
            // Normalize file name
        	fileName = StringUtils.cleanPath(file.getOriginalFilename());
            
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
            	ORAMessageUtil.setMessage(resp, "Sorry! Filename contains invalid path sequence ", "FAILURE", "0001");
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.createDirectory(fileStorageLocation);
            
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Location::"+targetLocation.toAbsolutePath().toString());
            
            resp.setLoc(targetLocation.toAbsolutePath().toString());
            ORAMessageUtil.setSuccessMessage(resp);
        } catch (IOException ex) {
        	ex.printStackTrace();
        	ORAMessageUtil.setMessage(resp, "Could not store file " + fileName + ". Please try again!", "FAILURE", "0001");
        }
        logger.info("Out of saveFile");
        return resp;
    }
	
	
	@Override
	public Resource loadFileAsResource(String fileId) {
		logger.info("Into loadFileAsResource");
		 Resource resource = null;
		 
       /* try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }*/
        
		  logger.info("Out of loadFileAsResource");
        return resource;
    }

}
