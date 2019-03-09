package com.cts.ora.report.dataload.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.cts.ora.report.dataload.vo.ORAFileResponse;

public interface FileService {
	
	ORAFileResponse saveFile(MultipartFile file);
	
	Resource downloadFile(String fileId, String boundType);

}
