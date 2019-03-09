package com.cts.ora.report.dataload.vo;

import com.cts.ora.report.common.vo.ORAResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString(callSuper=true)
public class ORAFileResponse extends ORAResponse{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String loc;

}
