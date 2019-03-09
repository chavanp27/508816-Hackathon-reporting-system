package com.cts.ora.report.dataload.service;

import org.springframework.web.multipart.MultipartFile;

import com.cts.ora.report.common.vo.ORAResponse;
import com.cts.ora.report.dataload.vo.ORADataLoadRequest;
import com.cts.ora.report.file.vo.FileUploadResponse;

public interface ORADataLoadService {
	
	ORAResponse loadIncomingFiles(ORADataLoadRequest request);
	
	ORAResponse loadMasterDataFiles(ORADataLoadRequest request);
	
	FileUploadResponse uploadFile(MultipartFile file);
	


}
