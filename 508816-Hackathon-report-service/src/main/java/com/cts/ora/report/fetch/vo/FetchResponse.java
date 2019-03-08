package com.cts.ora.report.fetch.vo;

import java.util.List;

import com.cts.ora.report.common.vo.ORAResponse;

import lombok.Data;

@Data
public class FetchResponse extends ORAResponse {

	public FetchResponse(String status, String responseText, String responseCode) {
		super(status, responseText, responseCode);
	}
	
	private List<ParticipationMetrics> participationMetrics;
	
	private List<EngagementMetrics> engagementMetrics;

		
	

}
