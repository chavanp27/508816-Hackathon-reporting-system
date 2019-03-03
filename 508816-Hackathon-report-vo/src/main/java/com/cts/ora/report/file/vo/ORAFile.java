package com.cts.ora.report.file.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ORAFile {
	
	private Long fileId;
	private String fileName;
	private String fileLoc;
	private String fileType;

}
