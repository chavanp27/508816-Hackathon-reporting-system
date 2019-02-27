package com.cts.ora.report.dataload.vo;

import com.cts.ora.report.common.vo.ORARequest;
import com.cts.ora.report.file.vo.ORAFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ORADataLoadRequest extends ORARequest {
	
	private ORAFile ascFile;
	private ORAFile eventSummaryFile;
	private ORAFile eventDetailFile;

}
