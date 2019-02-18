package com.cts.ora.report.dataload.vo;

import com.cts.ora.report.common.vo.ORARequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ORADataLoadRequest extends ORARequest {
	
	private String fileLoc;

}
