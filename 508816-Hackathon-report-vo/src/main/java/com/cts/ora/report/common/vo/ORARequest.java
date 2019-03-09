package com.cts.ora.report.common.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ORARequest implements Serializable {
	
	private Long ascId;
	
	private Long requestTimeStamp;
	
	private int periodFrom;
	
	private int periodTo;
	
	private String periodType;

}
