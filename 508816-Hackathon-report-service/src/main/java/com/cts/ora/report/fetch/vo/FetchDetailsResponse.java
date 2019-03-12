package com.cts.ora.report.fetch.vo;

import java.util.List;

import com.cts.ora.report.common.vo.ORAResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class FetchDetailsResponse extends ORAResponse {

	
	private List<ParticipationMetricsDetails> participationMetrics;
	
	
	

}
