package com.cts.ora.report.file.vo;

import com.cts.ora.report.common.vo.ORAResponse;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileUploadResponse extends ORAResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6569298563606122317L;
	private ORAFile uploadFile;

}
